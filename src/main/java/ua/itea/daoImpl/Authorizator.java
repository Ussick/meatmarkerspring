package ua.itea.daoImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.itea.dao.UserDao;

@Service
public class Authorizator {
    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public String isAuthorized(String login, String password) {
        return userDao.checkLogin(login, password);
    }
}
