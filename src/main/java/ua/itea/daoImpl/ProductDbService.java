package ua.itea.daoImpl;

import org.springframework.stereotype.Service;
import ua.itea.dao.ProductDao;
import ua.itea.model.Product;
import ua.itea.utils.DbConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ProductDbService implements ProductDao {
    public static final Logger LOG = Logger.getLogger(ProductDbService.class.getName());
    private DbConnector db;
    private final String SELECT_PRODUCTS = "SELECT * FROM products";
    private final String SELECT_PRODUCTS_BY_CATEGORY_ID = "SELECT p.id, p.name, p.description, p.price, " +
            "p2c.category_id, c.name FROM products p JOIN product2category p2c on p2c.product_id = p.id JOIN categories " +
            "c on c.id = p2c.category_id WHERE c.id = ?";

    private final String SELECT_PRODUCT_BY_ID = "SELECT p.id, p.name, p.description, p.price, " +
            "p2c.category_id, c.name FROM products p JOIN product2category p2c on p2c.product_id = p.id JOIN categories " +
            "c on c.id = p2c.category_id WHERE p.id = ?";

    public ProductDbService() {
        db = DbConnector.getInstance();
    }

    @Override
    public List<Product> getProducts() {
        List<Product> result = new ArrayList();
        Connection conn = db.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        LOG.info("Starting query");
        try {
            statement = conn.prepareStatement(SELECT_PRODUCTS);
            rs = statement.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt(1));
                product.setName(rs.getString(2));
                product.setDescription(rs.getString(3).substring(0, 30) + "...");
                product.setPrice(rs.getInt(4));
                result.add(product);
            }
            LOG.info("Query success");
        } catch (SQLException sqlException) {
            LOG.log(Level.SEVERE, "DB error " + sqlException.getMessage(), sqlException);
            sqlException.printStackTrace();
            try {
                rs.close();
            } catch (SQLException s1) {
            }
            try {
                statement.close();
            } catch (SQLException s1) {
            }
            try {
                conn.close();
            } catch (SQLException s1) {
            }
        }
        return result;
    }

    @Override
    public List<Product> getProductsByCategoryId(String categoryId) {
        List<Product> result = new ArrayList();
        Connection conn = db.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        LOG.info("Starting query");
        try {
            if (categoryId != null && !categoryId.isEmpty()) {
                statement = conn.prepareStatement(SELECT_PRODUCTS_BY_CATEGORY_ID);
                statement.setString(1, categoryId);
            } else {
                statement = conn.prepareStatement(SELECT_PRODUCTS);
            }
            rs = statement.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt(1));
                product.setName(rs.getString(2));
                product.setDescription(rs.getString(3).substring(0, 30) + "...");
                product.setPrice(rs.getInt(4));
                result.add(product);
            }
            LOG.info("Query success");
        } catch (SQLException sqlException) {
            LOG.log(Level.SEVERE, "DB error " + sqlException.getMessage(), sqlException);
            sqlException.printStackTrace();
            try {
                rs.close();
            } catch (SQLException s1) {
            }
            try {
                statement.close();
            } catch (SQLException s1) {
            }
            try {
                conn.close();
            } catch (SQLException s1) {
            }
        }
        return result;
    }

    @Override
    public Product getProductById(String id) {
        Product result = null;
        Connection conn = db.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        LOG.info("Starting query");
        try {
            statement = conn.prepareStatement(SELECT_PRODUCT_BY_ID);
            statement.setString(1, id);
            rs = statement.executeQuery();
            while (rs.next()) {
                result = new Product();
                result.setId(rs.getInt(1));
                result.setName(rs.getString(2));
                result.setDescription(rs.getString(3));
                result.setPrice(rs.getInt(4));
            }

            LOG.info("Query success");
        } catch (SQLException sqlException) {
            LOG.log(Level.SEVERE, "DB error " + sqlException.getMessage(), sqlException);
            sqlException.printStackTrace();
            try {
                rs.close();
            } catch (SQLException s1) {
            }
            try {
                statement.close();
            } catch (SQLException s1) {
            }
            try {
                conn.close();
            } catch (SQLException s1) {
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(new ProductDbService().getProductById("2"));

    }
}
