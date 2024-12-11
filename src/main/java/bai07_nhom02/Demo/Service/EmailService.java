package bai07_nhom02.Demo.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    // Gửi email đơn giản
    public void sendSimpleEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom("todreamscompany@gmail.com");  // Địa chỉ email gửi
        emailSender.send(message);
    }

    // Gửi email với đính kèm (MimeMessage)
    public void sendEmailWithAttachment(String to, String subject, String text, String attachmentPath) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);  // true để cho phép đính kèm
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);
        helper.setFrom("todreamscompany@gmail.com");

        // Đính kèm file
        helper.addAttachment("Invoice", new java.io.File(attachmentPath));

        emailSender.send(message);
    }
}
