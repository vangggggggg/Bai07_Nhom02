package bai07_nhom02.Demo.Controller;

import bai07_nhom02.Demo.DTO.AttendanceRequest;
import bai07_nhom02.Demo.DTO.UdpNotificationRequest;
import bai07_nhom02.Demo.Entites.Attendance;
import bai07_nhom02.Demo.Entites.Student;
import bai07_nhom02.Demo.Service.AttendanceService;
import bai07_nhom02.Demo.Service.StudentService;
import bai07_nhom02.Demo.Service.UdpNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/attendance")
public class RestAttendanceController {

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

    @GetMapping("/students/high-absence")
    public List<Object[]> getStudentsWithHighAbsence(@RequestParam int threshold) {
        return attendanceService.getStudentsWithHighAbsence(threshold);
    }

    @GetMapping("/courses/high-absence")
    public List<Object[]> getCoursesWithHighAbsence() {
        return attendanceService.getCoursesWithHighAbsence();
    }

    @GetMapping("/attendance")
    public String listAttendance(Model model) {
        // Lấy danh sách Attendance từ database
        List<Attendance> attendances = attendanceService.getAllAttendance();

        // Thống kê số ngày vắng học của từng học sinh
        Map<String, Long> absentStatistics = attendances.stream()
                .filter(a -> !a.getPresent()) // Lọc các bản ghi vắng học
                .collect(Collectors.groupingBy(a -> a.getStudent().getName(), Collectors.counting()));

        model.addAttribute("attendances", attendances);
        model.addAttribute("absentStatistics", absentStatistics);
        return "templates/attendance/index";
    }
}

