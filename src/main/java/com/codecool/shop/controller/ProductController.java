package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.JDBC.ProductCategoryDaoJDBC;
import com.codecool.shop.dao.implementation.JDBC.ProductDaoJDBC;
import com.codecool.shop.dao.implementation.JDBC.ShoppingCartDaoJDBC;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Random;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoJDBC.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoJDBC.getInstance();
        HttpSession session = req.getSession(true);
        ShoppingCartDao shoppingCart;
        if(session.isNew()){
            shoppingCart = new ShoppingCartDaoJDBC(0);
            session.setAttribute("cart", shoppingCart);
            session.setAttribute("user", 0);
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
        context.setVariable("categories", productCategoryDataStore.getAll());
        context.setVariable("necessities", productDataStore.getBy(productCategoryDataStore.find(1)));
        context.setVariable("powerUps", productDataStore.getBy(productCategoryDataStore.find(2)));
        context.setVariable("materialGoods", productDataStore.getBy(productCategoryDataStore.find(3)));
        context.setVariable("priceless", productDataStore.getBy(productCategoryDataStore.find(4)));
        context.setVariable("cart", "0");
        context.setVariable("user", session.getAttribute("user"));
        engine.process("product/index.html", context, resp.getWriter());
    }

}
