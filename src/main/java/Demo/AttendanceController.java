package Demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private UdpNotificationService udpNotificationService;

    // Lấy danh sách tất cả các bản ghi điểm danh
    @GetMapping
    public List<Attendance> getAllAttendance() {
        return attendanceService.getAllAttendance();
    }

    // Lấy danh sách điểm danh của một sinh viên
    @GetMapping("/student/{studentId}")
    public List<Attendance> getAttendanceByStudent(@PathVariable Long studentId) {
        return attendanceService.getAttendanceByStudent(studentId);
    }

    // Lấy danh sách điểm danh theo ngày
    @GetMapping("/date/{date}")
    public List<Attendance> getAttendanceByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return attendanceService.getAttendanceByDate(date);
    }

    // Lấy danh sách sinh viên vắng mặt quá 3 buổi
    @GetMapping("/excessive-absences")
    public List<Student> getStudentsWithExcessiveAbsences() {
        return attendanceService.getStudentsWithExcessiveAbsences();
    }

    // Gửi email cảnh báo đến phụ huynh cho sinh viên vắng mặt quá 3 buổi
    @PostMapping("/notify-parents")
    public ResponseEntity<String> notifyParents() {
        attendanceService.notifyParentsForAbsence();
        return ResponseEntity.ok("Đã gửi thông báo đến phụ huynh.");
    }

    // Điểm danh sinh viên (cập nhật trạng thái có mặt/vắng mặt)
    @PostMapping("/mark")
    public ResponseEntity<String> markAttendance(@RequestBody AttendanceRequest request) {
        attendanceService.markAttendance(request.getStudentId(), request.getDate(), request.isPresent());
        return ResponseEntity.ok("Điểm danh thành công.");
    }

    @PostMapping("/notify-udp")
    public ResponseEntity<String> sendUdpNotification(@RequestBody UdpNotificationRequest request) {
        udpNotificationService.sendUdpNotification(request.getMessage(), request.getIpAddress(), request.getPort());
        return ResponseEntity.ok("Thông báo UDP đã được gửi.");
    }
}

