package bai07_nhom02.Demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
}
