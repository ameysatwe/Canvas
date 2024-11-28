package com.ameysatwe.canvas.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role; // STUDENT, INSTRUCTOR, ADMIN, TA

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Enrollment> enrollments;

    @OneToMany(mappedBy = "ta", cascade = CascadeType.ALL)
    private List<Course> assignedCourses; // Courses assigned to this TA

    @OneToMany(mappedBy = "grader", cascade = CascadeType.ALL)
    private List<Submission> gradedSubmissions; // Submissions graded by this TA

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public List<Course> getAssignedCourses() {
        return assignedCourses;
    }

    public void setAssignedCourses(List<Course> assignedCourses) {
        this.assignedCourses = assignedCourses;
    }

    public List<Submission> getGradedSubmissions() {
        return gradedSubmissions;
    }

    public void setGradedSubmissions(List<Submission> gradedSubmissions) {
        this.gradedSubmissions = gradedSubmissions;
    }
}

enum Role {
    STUDENT,
    INSTRUCTOR,
    ADMIN,
    TA
}
