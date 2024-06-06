package com.ms.credit.dto;


public class FinancialDataDTO {
    private int userId;
    private double creditScore;
    private double transactionAmount;
    private String emailId;
    private String transactionType;  // e.g., credit, debit, loan payment

    // Constructors
    public FinancialDataDTO() {
    }

    public FinancialDataDTO(int userId, double transactionAmount, String transactionDate, String transactionType) {
        this.userId = userId;
        this.transactionAmount = transactionAmount;
      //  this.transactionDate = transactionDate;
        this.transactionType = transactionType;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    
    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
    
    public double getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(double creditScore) {
        this.creditScore = creditScore;
    }


    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}
