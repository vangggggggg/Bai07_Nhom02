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

    @Query("SELECT a.date FROM Attendance a WHERE a.student.id = :studentId")
    List<Object[]> findByStudentIdDate(@Param("studentId") Long studentId);

    // Lấy danh sách điểm danh theo ngày cụ thể
    List<Attendance> findByDate(LocalDate date);

    @Query("SELECT s.id, s.name, p.email, p.name, " +
            "COUNT(CASE WHEN a.status = 'Absent' THEN 1 END) AS absenceCount, " +
            "COUNT(CASE WHEN a.status = 'Present' THEN 1 END) AS presenceCount " +
            "FROM Student s " +
            "JOIN s.parent p " +
            "JOIN Attendance a ON a.student.id = s.id " +
            "GROUP BY s.id, s.name, p.email, p.name " +
            "HAVING COUNT(CASE WHEN a.status = 'Absent' THEN 1 END) >= :threshold"
    )
    List<Object[]> findStudentsWithHighAbsence(@Param("threshold") int threshold);



    @Query("SELECT a.course.id, COUNT(a.id) AS absentCount " +
            "FROM Attendance a WHERE a.status = 'Absent' " +
            "GROUP BY a.course.id ORDER BY absentCount DESC")
    List<Object[]> findCoursesWithHighAbsence();
}
