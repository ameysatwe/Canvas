package com.ameysatwe.canvas.models;


import jakarta.persistence.*;

import java.util.List;

@Entity
@DiscriminatorValue("STUDENT")
public class Student extends User{
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Enrollment> enrollments;

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }
}
