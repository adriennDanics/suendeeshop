package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.dao.implementation.JDBC.ShoppingCartDaoJDBC;
import com.codecool.shop.model.Product;
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
import java.util.Map;
import java.util.Random;

@WebServlet(urlPatterns = {"/checkout"})
public class Checkout extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        ShoppingCartDao shoppingCart;
        if(session.isNew()){
            shoppingCart = new ShoppingCartDaoJDBC(0);
            session.setAttribute("cart", shoppingCart);
        } else {
            shoppingCart = (ShoppingCartDaoJDBC) session.getAttribute("cart");
        }
        String origin = req.getHeader("referer");
        if(origin != null){
            if (origin.equals("http://localhost:8080/card")) {
                shoppingCart.clear();
            }
        }

        Map<Product, Integer> products = shoppingCart.getAll();
        float subtotal = 0;
        for (Product key: products.keySet()) {
            subtotal += key.getDefaultPrice()*products.get(key);
        }
//        Map params = new HashMap<>();
//        params.put("category", productCategoryDataStore.find(1));
//        params.put("products", productDataStore.getBy(productCategoryDataStore.find(1)));

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
//        context.setVariables(params);
        context.setVariable("recipient", "World");
        context.setVariable("products", products);
        context.setVariable("subtotal", subtotal);
        context.setVariable("shipping", "$10");
        context.setVariable("total", subtotal+10);
        context.setVariable("shoppingCart", shoppingCart);
        engine.process("product/item.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        ShoppingCartDaoJDBC shoppingCart = (ShoppingCartDaoJDBC) session.getAttribute("cart");
        shoppingCart.name = req.getParameter("name");
        shoppingCart.billing_address = req.getParameter("billing_address");
        shoppingCart.shipping_address = req.getParameter("shipping_address");
        shoppingCart.phone_number = req.getParameter("phone_number");
        shoppingCart.email_address = req.getParameter("email_address");
        if (req.getParameter("pay_with_card") != null) {
            resp.sendRedirect("/card");
        } else {
            shoppingCart.clear();
            System.out.println();
            resp.sendRedirect("/");
        }
    }
}