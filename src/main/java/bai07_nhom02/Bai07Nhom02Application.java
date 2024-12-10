package bai07_nhom02;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootApplication
public class Bai07Nhom02Application {

    private JavaMailSender emailSender;

    public static void main(String[] args) {
        
        SpringApplication.run(Bai07Nhom02Application.class, args);
    }

    public void SendMail(String DesMail, String URL, String username) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("todream@gmail.com");
        message.setTo(DesMail);
        message.setSubject("Reset Your Password");
        String emailContent = "Hello, " + username + "\n" +
                "You have requested to reset your password.\n" +
                "Click the link below to change your password:" + URL + "\n" +
                "Ignore this email if you remember your password or you have not made this request.\n" +
                "Thank you!";
        message.setText(emailContent);
        emailSender.send(message);
    }
}
