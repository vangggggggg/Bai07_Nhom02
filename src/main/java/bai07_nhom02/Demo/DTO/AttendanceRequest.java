package bai07_nhom02.Demo.DTO;

import java.time.LocalDate;

public class AttendanceRequest {
    private Long studentId;
    private LocalDate date;
    private boolean present;

    // Getters và Setters
    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }
}

