package ua.itea.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.itea.dao.ProductDao;


@Controller
public class ProductController {

    private ProductDao productDao;

    @Autowired
    public void setProductDbService(ProductDao productDao) {
        this.productDao = productDao;
    }

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public String getAllProducts(ModelMap modelMap) {
        modelMap.addAttribute("productList", productDao.getProducts());
        return "products";
    }

    @RequestMapping(value = "/products", params = {"id"}, method = RequestMethod.GET)
    public String getProductById(@RequestParam(name = "id") String id, ModelMap modelMap) {
        modelMap.addAttribute("product", productDao.getProductById(id));
        return "product";
    }

    @RequestMapping(value = "/products", params = {"category"}, method = RequestMethod.GET)
    public String getProductsByCategory(@RequestParam(name = "category") String category, ModelMap modelMap) {
        modelMap.addAttribute("productList", productDao.getProductsByCategoryId(category));
        return "products";
    }
}
