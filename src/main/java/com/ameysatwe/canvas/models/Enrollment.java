package com.ameysatwe.canvas.models;

import jakarta.persistence.*;
@Entity
@Table(name = "enrollments")
@NamedQueries({
        @NamedQuery(
                name = "Enrollment.findByCourse",
                query = "SELECT e FROM Enrollment e WHERE e.course.id = :courseId"
        ),
        @NamedQuery(
                name = "Enrollment.findByUser",
                query = "SELECT e FROM Enrollment e WHERE e.user.id = :userId"
        ),
        @NamedQuery(
                name = "Enrollment.countByCourse",
                query = "SELECT COUNT(e) FROM Enrollment e WHERE e.course.id = :courseId"
        )
})
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // The user (student) enrolled in the course

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course; // The course the user is enrolled in

    @Column(nullable = false)
    private String status; // ACTIVE, COMPLETED, DROPPED

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
