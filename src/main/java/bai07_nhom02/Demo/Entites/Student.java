package bai07_nhom02.Demo.Entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String parentEmail;

    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = true)
    private Parent parent;


    public Student(Long studentId) {
        this.id = studentId;
    }

    // Getters and Setters
}

