package com.ms.credit.dto;

import java.util.Date;

public class NotificationDTO {
    private Long creditScore;
    private String emailId;

    // Constructors
    public NotificationDTO() {}

    public NotificationDTO(Long creditScore, String emailId) {
        this.creditScore = creditScore;
        this.emailId = emailId;
    }



    public Long getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(Long creditScore) {
        this.creditScore = creditScore;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

}
