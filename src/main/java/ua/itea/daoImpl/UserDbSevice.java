package ua.itea.daoImpl;

import org.springframework.stereotype.Service;
import ua.itea.dao.UserDao;
import ua.itea.model.User;
import ua.itea.utils.DbConnector;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


@Service
public class UserDbSevice implements UserDao {
    public static final Logger LOG = Logger.getLogger(UserDbSevice.class.getName());
    public static final String SELECT_USER = "SELECT name FROM users WHERE login=? AND password=?";
    public static final String INSERT_USER =
            "INSERT INTO users (login, name, password, gender, region, comment) VALUES(?,?,?,?,?,?)";
    private DbConnector db;

    public UserDbSevice() {
        db = DbConnector.getInstance();
    }

    public String checkLogin(String login, String password) {
        String name = null;
        Connection conn = db.getConnection();
        PreparedStatement ps = null;
        LOG.info("Starting query");
        try {
            ps = conn.prepareStatement(SELECT_USER);
            ps.setString(1, login);
            ps.setString(2, getSaltedHashedPassword(password));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                name = rs.getString(1);
            }
            LOG.info("Query success");
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "DB error " + e.getMessage(), e);
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {

            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {

            }
        }
        return name;
    }

    public void addUser(User user) {
        Connection conn = db.getConnection();
        PreparedStatement ps = null;
        LOG.info("Starting query");
        try {
            ps = conn.prepareStatement(INSERT_USER);
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getName());
            ps.setString(3, getSaltedHashedPassword(user.getPassword()));
            ps.setString(4, user.getGender().toUpperCase());
            ps.setString(5, user.getRegion());
            ps.setString(6, user.getComment());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "DB error " + e.getMessage(), e);
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {

            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {

            }
        }
    }

    private String getSaltedHashedPassword(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String passphrase = "myspecialspice" + password;
        md.update(passphrase.getBytes(StandardCharsets.UTF_8));
        return String.format("%064x", new BigInteger(1, md.digest()));
    }
}
