package bai07_nhom02.Demo.Controller;

import bai07_nhom02.Demo.Service.AttendanceService;
import bai07_nhom02.Demo.Service.EmailService;
import bai07_nhom02.Demo.Service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("/send-email")
    public String sendEmail() {
        try {
            // Execute the query to get students with high absence
            List<Object[]> result = attendanceService.getStudentsWithHighAbsence(3);

            for (Object[] row : result) {
                // Extract student and parent information
                Long studentId = (Long) row[0];
                String studentName = (String) row[1];
                String parentEmail = (String) row[2];
                String parentName = (String) row[3];
                Long absenceCount = (Long) row[4];

                // If the student has enough absences, send email
                if (absenceCount >= 3) {
                    // Create a list of absent dates for this student
                    List<String> absentDates = new ArrayList<>();
                    List<Object[]> result3 = attendanceService.getDateAbsent(2L);

// Define the date format (e.g., "yyyy-MM-dd")
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                    for (Object[] row3 : result3) {
                        LocalDate date = (LocalDate) row3[0]; // Assuming the date is in the first position
                        absentDates.add(date.format(formatter)); // Convert LocalDate to String
                    }

                    // Average score could be calculated or retrieved for the student
                    double averageScore = 6.5;  // Replace with actual logic to get score

                    // Ensure there are at least 3 absences
                    if (absenceCount < 3) {
                        return "Không đủ điều kiện gửi thông báo.";
                    }

                    // Prepare the email content
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
                            studentName, absenceCount, absentDatesStr, averageScore
                    );

                    // Send the email
                    emailService.sendSimpleEmail(parentEmail, emailSubject, emailBody);
                }
            }

            return "Email sent successfully!";
        } catch (Exception e) {
            return "Error sending email: " + e.getMessage();
        }
    }
}

