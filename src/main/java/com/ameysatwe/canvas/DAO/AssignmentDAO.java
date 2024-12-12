package com.ameysatwe.canvas.DAO;

import com.ameysatwe.canvas.models.Assignment;
import com.ameysatwe.canvas.models.Course;

import java.util.List;

public interface AssignmentDAO {
    void save(Assignment assignment);

    List<Assignment> getAssignmentsByCourseId(Course course);
}
