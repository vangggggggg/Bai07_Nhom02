package bai07_nhom02.Demo.Service;

import bai07_nhom02.Demo.Entites.Student;
import bai07_nhom02.Demo.Repo.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    // Lấy tất cả sinh viên
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Lấy thông tin sinh viên theo ID
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    // Lấy sinh viên theo email
    public Student getStudentByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    // Tạo mới sinh viên
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    // Cập nhật thông tin sinh viên
    public Student updateStudent(Long id, Student studentDetails) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sinh viên không tồn tại với ID: " + id));

        student.setName(studentDetails.getName());
        student.setEmail(studentDetails.getEmail());
        student.setParentEmail(studentDetails.getParentEmail());
        return studentRepository.save(student);
    }

    // Xóa sinh viên theo ID
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }


}

