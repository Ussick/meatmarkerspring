package ua.itea.dao;

import ua.itea.model.Product;

import java.util.List;

public interface ProductDao {

    List<Product> getProducts();
    List<Product> getProductsByCategoryId(String categoryId);
    Product getProductById(String id);
}
