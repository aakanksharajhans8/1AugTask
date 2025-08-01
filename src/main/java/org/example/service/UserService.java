package org.example.service;

import org.example.dao.UserDAO;
import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    public void createUser(User user) {
        userDAO.save(user);
    }

    public void updateUser(User user) {
        userDAO.update(user);
    }

    public void deleteUser(int id) {
        userDAO.delete(id);
    }

    public User getUser(int id) {
        return userDAO.get(id);
    }

    public List<User> getAllUsers() {
        return userDAO.getAll();
    }
}
