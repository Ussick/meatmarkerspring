package ua.itea.daoImpl;

import ua.itea.dao.IDao;
import ua.itea.dao.ProductDao;
import ua.itea.dao.UserDao;

public class DaoFactory implements IDao {

    private static IDao iDao;

    public static IDao getInstance() {
        if (iDao == null) {
            iDao = new DaoFactory();
        }
        return iDao;
    }

    @Override
    public UserDao getUserDAO() {
        return new UserDbSevice();
    }

    @Override
    public ProductDao getProductDAO() {
        return new ProductDbService();
    }
}
