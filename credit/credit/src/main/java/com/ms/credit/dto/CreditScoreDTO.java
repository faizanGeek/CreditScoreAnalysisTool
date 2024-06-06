package com.ms.credit.dto;


public class CreditScoreDTO {
    private int userId;
    private Long score;
    private String date;  // The date when the score was recorded

    // Default constructor
    public CreditScoreDTO() {
    }

    // Parameterized constructor
    public CreditScoreDTO(int userId, Long score, String date) {
        this.userId = userId;
        this.score = score;
        this.date = date;
    }

    // Getters
    public int getUserId() {
        return userId;
    }

    public Long getScore() {
        return score;
    }

    public String getDate() {
        return date;
    }
    

    // Setters
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
