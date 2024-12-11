package bai07_nhom02.Demo.Repo;

import bai07_nhom02.Demo.Entites.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByEmail(String email);
}
