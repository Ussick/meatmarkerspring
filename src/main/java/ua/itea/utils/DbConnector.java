package ua.itea.utils;

import ua.itea.daoImpl.ProductDbService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DbConnector {
    private static DbConnector instance;
    public static final Logger LOG = Logger.getLogger(DbConnector.class.getName());
    private DbConnector() {
        LOG.info("Starting connection");
        try {
            // The newInstance() call is a work around for some
            // broken Java implementations

            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            // handle the error
        }
    }

    public static DbConnector getInstance() {
        if (instance == null) {
            instance = new DbConnector();
        }
        return instance;
    }

    public Connection getConnection() {
        Connection conn = null;
        try {
            conn =
                    DriverManager.getConnection
                            ("jdbc:mysql://localhost:3306/testIteaWeb", "root", "5B4#n5u%Pz");
            // Do something with the Connection
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        LOG.info("Connection provided");
        return conn;
    }
}
