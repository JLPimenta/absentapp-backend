package com.absentapp.project.domain.core.mail;

public interface IEmailService {
    void send(String to, String subject, String email);
}