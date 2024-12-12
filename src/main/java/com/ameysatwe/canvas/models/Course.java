package com.ameysatwe.canvas.models;

import jakarta.persistence.*;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "courses")
@NamedQueries({
        @NamedQuery(
                name = "Course.findByInstructor",
                query = "SELECT c FROM Course c WHERE c.instructor.id = :instructorId"
        ),
        @NamedQuery(
                name = "Course.findByTA",
                query = "SELECT c FROM Course c WHERE c.ta.id = :taId"
        ),
        @NamedQuery(
                name = "Course.findByTitle",
                query = "SELECT c FROM Course c WHERE c.title = :title"
        )
})
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "instructor_id", nullable = false)
    private User instructor;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Enrollment> enrollments;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Assignment> assignments;

    @ManyToOne
    @JoinColumn(name = "ta_id")
    private User ta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getInstructor() {
        return instructor;
    }

    public void setInstructor(User instructor) {
        this.instructor = instructor;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    public User getTa() {
        return ta;
    }

    public void setTa(User ta) {
        this.ta = ta;
    }



}