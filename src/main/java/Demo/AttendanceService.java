package Demo;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmailService emailService;

    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }

    public List<Attendance> getAttendanceByStudent(Long studentId) {
        return attendanceRepository.findByStudentId(studentId);
    }

    public List<Attendance> getAttendanceByDate(LocalDate date) {
        return attendanceRepository.findByDate(date);
    }

    public List<Student> getStudentsWithExcessiveAbsences() {
        return attendanceRepository.findStudentsWithExcessiveAbsences();
    }

    public void notifyParentsForAbsence() {
        List<Student> students = getStudentsWithExcessiveAbsences();
        for (Student student : students) {
            String subject = "Cảnh báo vắng học";
            String body = "Sinh viên " + student.getName() + " đã vắng mặt quá 3 buổi liên tiếp.";
            emailService.sendEmail(student.getParentEmail(), subject, body);
        }
    }

    public void markAttendance(Long studentId, LocalDate date, boolean present) {
        Attendance attendance = new Attendance();
        attendance.setStudent(new Student(studentId));
        attendance.setDate(date);
        attendance.setPresent(present);
        attendanceRepository.save(attendance);
    }
}

