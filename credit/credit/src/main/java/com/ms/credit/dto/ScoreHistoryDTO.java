package com.ms.credit.dto;


import java.util.List;

public class ScoreHistoryDTO {
    private int userId;
    private List<CreditScoreDetails> scores;

    public static class CreditScoreDetails {
        private Long score;
        private String date;  // The date when the score was recorded

        public CreditScoreDetails() {
        }

        public CreditScoreDetails(Long score, String date) {
            this.score = score;
            this.date = date;
        }

        // Getters
        public Long getScore() {
            return score;
        }

        public String getDate() {
            return date;
        }

        // Setters
        public void setScore(Long score) {
            this.score = score;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }

    // Default constructor
    public ScoreHistoryDTO() {
    }

    // Parameterized constructor
    public ScoreHistoryDTO(int userId, List<CreditScoreDetails> scores) {
        this.userId = userId;
        this.scores = scores;
    }

    // Getters
    public int getUserId() {
        return userId;
    }

    public List<CreditScoreDetails> getScores() {
        return scores;
    }

    // Setters
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setScores(List<CreditScoreDetails> scores) {
        this.scores = scores;
    }
}

