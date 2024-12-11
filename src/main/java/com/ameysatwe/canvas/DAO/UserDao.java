package com.ameysatwe.canvas.DAO;

import com.ameysatwe.canvas.models.Role;
import com.ameysatwe.canvas.models.Student;
import com.ameysatwe.canvas.models.User;

import java.util.List;
import java.util.Optional;
public interface UserDao {
    List<User> findByRole(Role role);
    Optional<User> findByEmail(String email);
    List<User> findAllTAs();
    void save(Student user);
    Optional<User> findById(Long id);
    void deleteById(Long id);

    List<User> findAllUsers();

    List<User> findUnapprovedUsers();
    void approveUser(Long userId);

}
