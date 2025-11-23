package com.zentry.zentrystore.infrastructure.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    private final JavaMailSender mailSender;
    private final String fromEmail = "noreply@zentrystore.com"; // Configurable

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendWelcomeEmail(String to, String username) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject("¡Bienvenido a ZentryStore!");
            message.setText(String.format(
                    "Hola %s,\n\n" +
                            "¡Bienvenido a ZentryStore! Tu cuenta ha sido creada exitosamente.\n\n" +
                            "Ahora puedes empezar a comprar y vender productos.\n\n" +
                            "Saludos,\n" +
                            "El equipo de ZentryStore",
                    username
            ));

            mailSender.send(message);
            logger.info("Welcome email sent to: {}", to);
        } catch (Exception e) {
            logger.error("Error sending welcome email to {}: {}", to, e.getMessage());
        }
    }

    @Override
    public void sendPasswordResetEmail(String to, String resetToken) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject("Restablecer contraseña - ZentryStore");
            message.setText(String.format(
                    "Has solicitado restablecer tu contraseña.\n\n" +
                            "Usa el siguiente token para restablecer tu contraseña:\n%s\n\n" +
                            "Este token expirará en 1 hora.\n\n" +
                            "Si no solicitaste esto, ignora este email.\n\n" +
                            "Saludos,\n" +
                            "El equipo de ZentryStore",
                    resetToken
            ));

            mailSender.send(message);
            logger.info("Password reset email sent to: {}", to);
        } catch (Exception e) {
            logger.error("Error sending password reset email to {}: {}", to, e.getMessage());
        }
    }

    @Override
    public void sendEmailVerification(String to, String verificationToken) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject("Verifica tu email - ZentryStore");
            message.setText(String.format(
                    "Por favor verifica tu dirección de email.\n\n" +
                            "Usa el siguiente token:\n%s\n\n" +
                            "Este token expirará en 24 horas.\n\n" +
                            "Saludos,\n" +
                            "El equipo de ZentryStore",
                    verificationToken
            ));

            mailSender.send(message);
            logger.info("Email verification sent to: {}", to);
        } catch (Exception e) {
            logger.error("Error sending verification email to {}: {}", to, e.getMessage());
        }
    }

    @Override
    public void sendNotificationEmail(String to, String subject, String content) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(content);

            mailSender.send(message);
            logger.info("Notification email sent to: {}", to);
        } catch (Exception e) {
            logger.error("Error sending notification email to {}: {}", to, e.getMessage());
        }
    }

    @Override
    public void sendMessageNotification(String to, String senderName, String messagePreview) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject("Nuevo mensaje de " + senderName);
            message.setText(String.format(
                    "Has recibido un nuevo mensaje de %s:\n\n" +
                            "%s\n\n" +
                            "Ingresa a ZentryStore para responder.\n\n" +
                            "Saludos,\n" +
                            "El equipo de ZentryStore",
                    senderName,
                    messagePreview
            ));

            mailSender.send(message);
            logger.info("Message notification sent to: {}", to);
        } catch (Exception e) {
            logger.error("Error sending message notification to {}: {}", to, e.getMessage());
        }
    }

    @Override
    public void sendRatingNotification(String to, String raterName, Integer rating) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject("Nueva calificación recibida");
            message.setText(String.format(
                    "%s te ha calificado con %d estrellas.\n\n" +
                            "Ingresa a ZentryStore para ver los detalles.\n\n" +
                            "Saludos,\n" +
                            "El equipo de ZentryStore",
                    raterName,
                    rating
            ));

            mailSender.send(message);
            logger.info("Rating notification sent to: {}", to);
        } catch (Exception e) {
            logger.error("Error sending rating notification to {}: {}", to, e.getMessage());
        }
    }
}
