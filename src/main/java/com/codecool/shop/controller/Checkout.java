package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.dao.implementation.ShoppingCartMem;
import com.codecool.shop.model.Product;
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

@WebServlet(urlPatterns = {"/item"})
public class Checkout extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        ShoppingCartDao shoppingCartDao;
        if(session.isNew()){
            shoppingCartDao = new ShoppingCartMem();
            session.setAttribute("cart", shoppingCartDao);
        } else {
            shoppingCartDao = (ShoppingCartMem) session.getAttribute("cart");
        }
        String origin = req.getHeader("referer");
        if(origin != null){
            if (origin.equals("http://localhost:8080/card")) {
                shoppingCartDao.clear();
            }
        }

        Map<Product, Integer> products = shoppingCartDao.getAll();
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
        context.setVariable("shoppingCart", shoppingCartDao);
        engine.process("product/item.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ShoppingCartMem shoppingCart = new ShoppingCartMem();
        shoppingCart.name = req.getParameter("name");
        shoppingCart.billing_address = req.getParameter("billing_address");
        shoppingCart.shipping_address = req.getParameter("shipping_address");
        shoppingCart.phone_number = req.getParameter("phone_number");
        shoppingCart.email_address = req.getParameter("email_address");
        if (req.getParameter("pay_with_card") != null) {
            resp.sendRedirect("/card");
        } else {
            resp.sendRedirect("/");
            shoppingCart.clear();
        }
    }
}