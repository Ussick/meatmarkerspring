package ua.itea.dao;

import ua.itea.model.User;

public interface UserDao {

    String checkLogin(String login, String password);

    void addUser(User user);

}
