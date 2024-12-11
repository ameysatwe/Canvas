package com.ameysatwe.canvas.services;

import com.ameysatwe.canvas.DAO.UserDao;
import com.ameysatwe.canvas.models.Student;
import com.ameysatwe.canvas.models.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    @Transactional
    public void registerUser(Student user) {
        userDao.save(user);
    }

    public List<User> getAllUsers() {
        return userDao.findAllUsers();
    }


    public List<User> findUnapprovedUsers() {
        return userDao.findUnapprovedUsers();
    }


    public void approveUser(Long userId) {
        userDao.approveUser(userId);
    }

    public void deleteUser(Long userId) {
        userDao.deleteById(userId);
    }
}
