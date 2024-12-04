package com.ameysatwe.canvas.models;


import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
@DiscriminatorValue("TA")
public class TA extends User{
    @OneToMany(mappedBy = "ta", cascade = CascadeType.ALL)
    private List<Course> assignedCourses;

    @OneToMany(mappedBy = "grader", cascade = CascadeType.ALL)
    private List<Submission> gradedSubmissions;


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
