package bai07_nhom02.Demo.Controller;

import bai07_nhom02.Demo.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/send-email")
    public String sendEmail() {
        try {
            emailService.sendSimpleEmail("khanhtrinh123oki@gmail.com", "Test Subject", "This is a test email.");
            return "Email sent successfully!";
        } catch (Exception e) {
            return "Error sending email: " + e.getMessage();
        }
    }
}

