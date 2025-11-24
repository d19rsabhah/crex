package com.example.crex.service.signature;

public interface EmailService {
    void sendEmail(String to, String subject, String message);
}