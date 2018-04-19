package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.ShoppingCart;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.ShoppingCartMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/suppliers"})
public class ProductControllerBySupplier extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
        ShoppingCart shoppingCart = new ShoppingCartMem();

//        Map params = new HashMap<>();
//        params.put("category", productCategoryDataStore.find(1));
//        params.put("products", productDataStore.getBy(productCategoryDataStore.find(1)));

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
//        context.setVariables(params);
        context.setVariable("recipient", "World");
        context.setVariable("suppliers", supplierDataStore.getAll());
        context.setVariable("amazon", productDataStore.getBy(supplierDataStore.find(1)));
        context.setVariable("lenovo", productDataStore.getBy(supplierDataStore.find(2)));
        context.setVariable("asus", productDataStore.getBy(supplierDataStore.find(3)));
        context.setVariable("apple", productDataStore.getBy(supplierDataStore.find(4)));
        context.setVariable("samsung", productDataStore.getBy(supplierDataStore.find(5)));
        context.setVariable("dji", productDataStore.getBy(supplierDataStore.find(6)));
        context.setVariable("cart", shoppingCart.length());
        engine.process("product/suppliers.html", context, resp.getWriter());
    }

}
