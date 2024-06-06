package com.ms.credit.controller;

// Import necessary Spring MVC annotations and components.
import org.springframework.web.bind.annotation.*;
import com.ms.credit.dto.CreditScoreDTO;
import com.ms.credit.dto.ScoreHistoryDTO;
import com.ms.credit.service.CreditScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 * CreditScoreController handles all web requests related to credit score operations.
 * It routes requests to the appropriate service layer methods and returns responses.
 */
@RestController  // Marks the class as a REST controller, meaning it's ready to handle HTTP requests.
@RequestMapping("/score")  // Sets the base path for all methods in this controller.
public class CreditScoreController {

    // Automatically injects an instance of CreditScoreService.
    @Autowired
    private CreditScoreService creditScoreService;

    // Handles GET requests for retrieving a user's credit score by user ID.
    @GetMapping("/{userId}")
    public CreditScoreDTO getCreditScore(@PathVariable int userId) {
        return creditScoreService.getCreditScoreByUserId(userId);
    }

    // Handles POST requests to calculate a new credit score based on provided data.
    @PostMapping("/calculate")
    public CreditScoreDTO calculateCreditScore(@RequestBody CreditScoreDTO creditScoreDTO) {
        return creditScoreService.calculateCreditScore(creditScoreDTO);
    }

    // Handles PUT requests to update an existing credit score for a specified user ID.
    @PutMapping("/{userId}")
    public CreditScoreDTO updateCreditScore(@PathVariable int userId, @RequestBody CreditScoreDTO creditScoreDTO) {
        return creditScoreService.updateCreditScore(userId, creditScoreDTO);
    }

    // Handles DELETE requests to remove a credit score record for a specified user ID.
    @DeleteMapping("/{userId}")
    public void deleteCreditScore(@PathVariable int userId) {
        creditScoreService.deleteCreditScoreByUserId(userId);
    }

    // Handles GET requests to fetch the credit score history for a specified user ID.
    @GetMapping("/history/{userId}")
    public List<ScoreHistoryDTO> getCreditScoreHistory(@PathVariable int userId) {
        return creditScoreService.getCreditScoreHistoryByUserId(userId);
    }

    // Handles POST requests to calculate credit scores in batch from a list of CreditScoreDTO objects.
    @PostMapping("/batch")
    public List<CreditScoreDTO> calculateBatchCreditScores(@RequestBody List<CreditScoreDTO> creditScores) {
        return creditScoreService.calculateBatchCreditScores(creditScores);
    }

    // Handles GET requests to compute the average credit score of all records.
    @GetMapping("/average")
    public double getAverageCreditScore() {
        return creditScoreService.getAverageCreditScore();
    }

    // Handles PUT requests to refresh all credit scores, possibly recalculating or updating them.
    @PutMapping("/refresh")
    public void refreshCreditScores() {
        creditScoreService.refreshCreditScores();
    }
}
