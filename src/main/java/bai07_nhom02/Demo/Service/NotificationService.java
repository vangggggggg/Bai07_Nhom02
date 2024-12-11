package bai07_nhom02.Demo.Service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private EmailService emailService;

    public NotificationService(EmailService emailService) {
        this.emailService = emailService;
    }

    public String notifyParents(String studentName, String parentEmail, List<String> absentDates, double averageScore) {
        try {
            // Kiểm tra điều kiện kích hoạt
            int absentCount = absentDates.size();
            if (absentCount < 3) {
                return "Không đủ điều kiện gửi thông báo.";
            }

            // Nội dung email
            String emailSubject = "Thông báo: Sinh viên vắng học quá nhiều";
            String absentDatesStr = String.join(", ", absentDates);
            String emailBody = String.format(
                    "Kính gửi Quý phụ huynh,\n\n" +
                            "Chúng tôi xin thông báo rằng sinh viên %s đã vắng học liên tiếp %d buổi trong các ngày sau: %s.\n" +
                            "Điểm trung bình hiện tại của sinh viên là: %.2f.\n\n" +
                            "Việc vắng học thường xuyên có thể ảnh hưởng đến kết quả học tập. " +
                            "Kính mong Quý phụ huynh quan tâm, nhắc nhở và hỗ trợ sinh viên tham gia học tập đầy đủ hơn.\n\n" +
                            "Trân trọng,\n" +
                            "Ban Giám Hiệu",
                    studentName, absentCount, absentDatesStr, averageScore
            );

            // Gửi email
            emailService.sendSimpleEmail(parentEmail, emailSubject, emailBody);
            return "Thông báo đã được gửi thành công!";
        } catch (Exception e) {
            return "Gửi thông báo thất bại: " + e.getMessage();
        }
    }
}
