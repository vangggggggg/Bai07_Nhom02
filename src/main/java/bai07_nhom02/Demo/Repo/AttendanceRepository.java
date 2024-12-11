package bai07_nhom02.Demo.Repo;

import bai07_nhom02.Demo.Entites.Attendance;
import bai07_nhom02.Demo.Entites.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    // Lấy danh sách sinh viên có hơn 3 buổi vắng liên tiếp
    @Query("SELECT a.student FROM Attendance a " +
            "WHERE a.present = false " +
            "GROUP BY a.student " +
            "HAVING COUNT(a) >= 3")
    List<Student> findStudentsWithExcessiveAbsences();

    // Tìm dữ liệu điểm danh theo sinh viên
    List<Attendance> findByStudentId(Long studentId);

    // Lấy danh sách điểm danh theo ngày cụ thể
    List<Attendance> findByDate(LocalDate date);
    @Query("SELECT s.id, s.name, COUNT(a.id) AS absenceCount " +
            "FROM Student s " +
            "JOIN Attendance a ON a.student.id = s.id " +
            "WHERE a.status = 'Absent' " +
            "GROUP BY s.id, s.name " +
            "HAVING COUNT(a.id) >= :threshold")
    List<Object[]> findStudentsWithHighAbsence(@Param("threshold") int threshold);

    @Query("SELECT a.course.id, COUNT(a.id) AS absentCount " +
            "FROM Attendance a WHERE a.status = 'Absent' " +
            "GROUP BY a.course.id ORDER BY absentCount DESC")
    List<Object[]> findCoursesWithHighAbsence();
}
