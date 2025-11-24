package com.zentry.zentrystore.infrastructure.email;

public interface EmailService {

    void sendWelcomeEmail(String to, String username);

    void sendPasswordResetEmail(String to, String resetToken);

    void sendEmailVerification(String to, String verificationToken);

    void sendNotificationEmail(String to, String subject, String content);

    void sendMessageNotification(String to, String senderName, String messagePreview);

    void sendRatingNotification(String to, String raterName, Integer rating);
}