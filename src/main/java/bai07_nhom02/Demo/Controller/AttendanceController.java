package bai07_nhom02.Demo.Controller;

import bai07_nhom02.Demo.Entites.Attendance;
import bai07_nhom02.Demo.Repo.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequestMapping("")
@Controller
public class AttendanceController {
    @Autowired
    private AttendanceRepository attendanceRepository;

    @GetMapping("/attendance")
    public String listAttendance(Model model) {
        // Lấy danh sách Attendance từ database
        List<Attendance> attendances = attendanceRepository.findAll();

        // Thống kê số ngày vắng học của từng học sinh
        Map<String, Long> absentStatistics = attendances.stream()
                .filter(a -> "Absent".equalsIgnoreCase(a.getStatus())) // Lọc bản ghi vắng học
                .collect(Collectors.groupingBy(a -> a.getStudent().getName(), Collectors.counting()));

        model.addAttribute("attendances", attendances);
        model.addAttribute("absentStatistics", absentStatistics);
        return "attendance/index";
    }
}
