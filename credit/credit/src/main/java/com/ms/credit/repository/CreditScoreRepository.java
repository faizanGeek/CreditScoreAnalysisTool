package com.ms.credit.repository;

// Import necessary classes and annotations from Spring Data JPA and other packages.
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ms.credit.entity.CreditScore;
import java.time.LocalDateTime;
import java.util.List;

/**
 * CreditScoreRepository extends JpaRepository to leverage Spring Data JPA functionalities
 * for handling database operations related to the CreditScore entity.
 */
@Repository  // Marks the interface as a Spring-managed repository.
public interface CreditScoreRepository extends JpaRepository<CreditScore, Integer> {

    /**
     * Retrieves a list of CreditScore entities associated with a specific user ID.
     * @param userId The ID of the user whose credit scores are to be retrieved.
     * @return A list of CreditScore entities.
     */
    List<CreditScore> findByUserId(int userId);

    /**
     * Deletes all credit scores associated with a specific user ID.
     * This is typically used when a user is removed from the system or their data is purged.
     * @param userId The ID of the user whose credit scores are to be deleted.
     */
    void deleteByUserId(int userId);
    
    /**
     * Finds the most recent credit score for a specific user, sorted by date in descending order.
     * This is useful for applications where the latest score is frequently accessed.
     * @param userId The ID of the user.
     * @return The latest CreditScore entity.
     */
    CreditScore findTopByUserIdOrderByDateDesc(int userId);

    /**
     * Finds the most recent credit score based on the email ID of the user, sorted by date in descending order.
     * Useful for quick lookups by email when user ID is not readily available.
     * @param emailId The email ID associated with the user's credit score.
     * @return The latest CreditScore entity for the given email.
     */
	CreditScore findTopByEmailIdOrderByDateDesc(String emailId);
	
    /**
     * Retrieves all credit scores that were last updated before a specific date and time.
     * This can be used for batch processing or cleanup of old records.
     * @param dateTime The cutoff LocalDateTime before which all records should be selected.
     * @return A list of CreditScore entities.
     */
	 List<CreditScore> findByLastUpdatedBefore(LocalDateTime dateTime);
}
