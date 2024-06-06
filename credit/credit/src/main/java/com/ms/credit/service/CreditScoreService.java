package com.ms.credit.service;

// Import necessary Spring and Java classes.
import org.springframework.scheduling.annotation.Scheduled;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ms.credit.dto.CreditScoreDTO;
import com.ms.credit.dto.NotificationDTO;
import com.ms.credit.dto.ScoreHistoryDTO;
import com.ms.credit.entity.CreditScore;
import com.ms.credit.repository.CreditScoreRepository;
import com.ms.credit.client.UserManagementClient;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Provides business logic related to credit scores, handling interactions with persistence, caching, and messaging systems.
 */
@Service
public class CreditScoreService {

    @Autowired
    private CreditScoreRepository creditScoreRepository;
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    private static final Logger logger = LogManager.getLogger(CreditScoreService.class);
    private static final String CREDIT_SCORE_UPDATE = "credit-score-updates";
    @Autowired
    private UserManagementClient userManagementClient;

    /**
     * Retrieves the latest credit score by user ID from Redis cache or database, then sends an update to Kafka.
     */
    public CreditScoreDTO getCreditScoreByEmailId(int userId) {
        String emailId = userManagementClient.getUserDetails(userId).block();  // Synchronous call to get user details.
        CreditScore creditScore = (CreditScore) redisTemplate.opsForValue().get(emailId);
        if (creditScore == null) {
            logger.info("Fetching from DB");
            creditScore = creditScoreRepository.findTopByEmailIdOrderByDateDesc(emailId);
        } else {
            logger.info("Fetched from Redis");
        }
        sendToKafka(creditScore.getScore(), emailId);
        return convertToDTO(creditScore);
    }

    /**
     * Sends credit score updates to a Kafka topic.
     */
    private void sendToKafka(Long score, String emailId) {
        NotificationDTO message = new NotificationDTO(score, emailId);
        kafkaTemplate.send(CREDIT_SCORE_UPDATE, message);
    }

    /**
     * Retrieves the latest credit score for a user by ID from the database.
     */
    public CreditScoreDTO getCreditScoreByUserId(int userId) {
        CreditScore creditScore = creditScoreRepository.findTopByUserIdOrderByDateDesc(userId);
        return convertToDTO(creditScore);
    }

    /**
     * Calculates a new credit score, saves it to the database and cache.
     */
    public CreditScoreDTO calculateCreditScore(CreditScoreDTO creditScoreDTO) {
        String emailId = userManagementClient.getUserDetails(creditScoreDTO.getUserId()).block();
        redisTemplate.opsForValue().set(emailId, convertToEntity(creditScoreDTO));
        CreditScore creditScore = creditScoreRepository.save(convertToEntity(creditScoreDTO));
        return convertToDTO(creditScore);
    }

    /**
     * Updates an existing credit score in the database.
     */
    public CreditScoreDTO updateCreditScore(int userId, CreditScoreDTO creditScoreDTO) {
        CreditScore existingScore = creditScoreRepository.findTopByUserIdOrderByDateDesc(userId);
        if (existingScore == null) {
            throw new IllegalArgumentException("Credit score not found");
        }
        existingScore.setScore(creditScoreDTO.getScore());
        existingScore.setDate(creditScoreDTO.getDate());
        existingScore = creditScoreRepository.save(existingScore);
        return convertToDTO(existingScore);
    }

    /**
     * Deletes a credit score by user ID.
     */
    public void deleteCreditScoreByUserId(int userId) {
        creditScoreRepository.deleteByUserId(userId);
    }

    /**
     * Retrieves the credit score history for a user, transforming each score into a detailed DTO.
     */
    public List<ScoreHistoryDTO> getCreditScoreHistoryByUserId(int userId) {
        List<CreditScore> scoreHistory = creditScoreRepository.findByUserId(userId);
        return scoreHistory.stream().map(this::convertToScoreHistoryDTO).collect(Collectors.toList());
    }

    /**
     * Handles batch processing of credit scores, useful for bulk operations.
     */
    public List<CreditScoreDTO> calculateBatchCreditScores(List<CreditScoreDTO> creditScoresDTOs) {
        List<CreditScore> scores = creditScoresDTOs.stream().map(this::convertToEntity).collect(Collectors.toList());
        creditScoreRepository.saveAll(scores);
        return scores.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    /**
     * Calculates the average credit score of all entries in the database.
     */
    public double getAverageCreditScore() {
        return creditScoreRepository.findAll().stream().mapToLong(CreditScore::getScore).average().orElse(Double.NaN);
    }

    /**
     * Refreshes all credit scores, potentially recalculating or updating them.
     */
    public void refreshCreditScores() {
        List<CreditScore> scores = creditScoreRepository.findAll();
        scores.forEach(score -> score.setScore(score.getScore() + 1));  // Example update logic.
        creditScoreRepository.saveAll(scores);
    }

    /**
     * Converts a CreditScore entity to a CreditScoreDTO.
     */
    private CreditScoreDTO convertToDTO(CreditScore creditScore) {
        return new CreditScoreDTO(creditScore.getUserId(), creditScore.getScore(), creditScore.getDate());
    }

    /**
     * Converts a CreditScoreDTO to a CreditScore entity.
     */
    private CreditScore convertToEntity(CreditScoreDTO creditScoreDTO) {
        CreditScore creditScore = new CreditScore();
        creditScore.setUserId(creditScoreDTO.getUserId());
        creditScore.setScore(creditScoreDTO.getScore());
        creditScore.setDate(creditScoreDTO.getDate());
        return creditScore;
    }

    /**
     * Converts a CreditScore entity into a ScoreHistoryDTO.
     */
    private ScoreHistoryDTO convertToScoreHistoryDTO(CreditScore creditScore) {
        ScoreHistoryDTO scoreHistoryDTO = new ScoreHistoryDTO();
        scoreHistoryDTO.setUserId(creditScore.getUserId());
        ScoreHistoryDTO.CreditScoreDetails details = new ScoreHistoryDTO.CreditScoreDetails();
        details.setScore(creditScore.getScore());
        details.setDate(creditScore.getDate());
        scoreHistoryDTO.setScores(List.of(details));
        return scoreHistoryDTO;
    }
    
    /**
     * Fetches credit scores that were last updated more than 24 hours ago.
     */
    public List<CreditScoreDTO> fetchScoresUpdatedMoreThan24HoursAgo() {
        LocalDateTime twentyFourHoursAgo = LocalDateTime.now().minusHours(24);
        List<CreditScore> scores = creditScoreRepository.findByLastUpdatedBefore(twentyFourHoursAgo);
        return scores.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
}
