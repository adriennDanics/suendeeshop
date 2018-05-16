package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.JDBC.ProductDaoJDBC;
import com.codecool.shop.dao.implementation.JDBC.ShoppingCartDaoJDBC;
import com.codecool.shop.dao.implementation.JDBC.SupplierDaoJDBC;
import com.codecool.shop.model.User;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Random;

@WebServlet(urlPatterns = {"/suppliers"})
public class ProductControllerBySupplier extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoJDBC.getInstance();
        SupplierDao supplierDataStore = SupplierDaoJDBC.getInstance();
        HttpSession session = req.getSession(true);
        ShoppingCartDao shoppingCart;
        User user;
        if(session.isNew()){
            shoppingCart = new ShoppingCartDaoJDBC(0);
            session.setAttribute("cart", shoppingCart);
            session.setAttribute("user", "Register");
        } else {
            shoppingCart = (ShoppingCartDaoJDBC) session.getAttribute("cart");

        }
        String origin = req.getHeader("referer");
        if(origin != null){
            if (origin.equals("http://localhost:8080/card")) {
                shoppingCart.clear();
            }
        }

//        Map params = new HashMap<>();
//        params.put("category", productCategoryDataStore.find(1));
//        params.put("products", productDataStore.getBy(productCategoryDataStore.find(1)));

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
//        context.setVariables(params);
        context.setVariable("recipient", "World");
        context.setVariable("suppliers", supplierDataStore.getAll());
        context.setVariable("nature", productDataStore.getBy(supplierDataStore.find(1)));
        context.setVariable("higherPower", productDataStore.getBy(supplierDataStore.find(2)));
        context.setVariable("innerVoice", productDataStore.getBy(supplierDataStore.find(3)));
        context.setVariable("fate", productDataStore.getBy(supplierDataStore.find(4)));
        context.setVariable("cart", shoppingCart.length());
        context.setVariable("user", session.getAttribute("user"));
        engine.process("product/suppliers.html", context, resp.getWriter());
    }

}
