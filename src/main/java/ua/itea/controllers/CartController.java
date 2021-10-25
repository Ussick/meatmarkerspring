package ua.itea.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.itea.dao.ProductDao;
import ua.itea.model.Product;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

@Controller
public class CartController {

    private ProductDao productDao;

    @Autowired
    public void setProductDbService(ProductDao productDao) {
        this.productDao = productDao;
    }

    @RequestMapping(value = ("/cart"), method = RequestMethod.GET)
    public String getCartView() {
        return "cart";
    }

    @RequestMapping(value = ("/cart"), method = RequestMethod.POST)
    public String operation(@RequestParam(name = "quantity") String quantityString,
                            @RequestParam (name = "id") String productId,
                            @RequestParam (name = "operation") String operation,
                            HttpSession session, ServletResponse resp) throws IOException {

        Integer quantity = Integer.parseInt(quantityString);
        Product product = productDao.getProductById(productId);
        Object productsCartMap = session.getAttribute("productsCartMap");

        if (Objects.equals(operation, "InCart")) {
            if (productId != null && !productId.isEmpty()) {
                if (productsCartMap == null) {
                    productsCartMap = new HashMap<Product, Integer>();
                }
                Integer count = ((HashMap<Product, Integer>) productsCartMap).get(product);
                if (count == null) {
                    ((HashMap<Product, Integer>) productsCartMap).put(product, quantity);
                } else {
                    count = count + quantity;
                    ((HashMap<Product, Integer>) productsCartMap).put(product, count);
                }
            }
        } else {
            if (productId != null && !productId.isEmpty()) {
                if (productsCartMap == null) {
                    return "cart";
                } else {
                    if (((HashMap<Product, Integer>) productsCartMap).get(product) == null) {
                        return "cart";
                    } else {
                        if (quantity > 0) {
                            ((HashMap<Product, Integer>) productsCartMap).put(product, quantity);
                        } else {
                            ((HashMap<Product, Integer>) productsCartMap).remove(product);
                        }
                    }
                }
            }
        }

        int productsCartMapSize=((HashMap<Product, Integer>) productsCartMap).entrySet()
                .stream().mapToInt(x -> x.getValue()).sum();
        int total=((HashMap<Product, Integer>) productsCartMap).entrySet()
                .stream().mapToInt(x -> x.getKey().getPrice() * x.getValue()).sum();
        session.setAttribute("productsCartMap", productsCartMap);
        session.setAttribute("productsCartMapSize",productsCartMapSize);
        session.setAttribute("total",  total);
        resp.getWriter().write(productsCartMapSize+","+total);

        return null;
    }
}
