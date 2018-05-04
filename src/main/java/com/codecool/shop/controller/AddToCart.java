package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.dao.implementation.ProductDaoJDBC;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.ShoppingCartDaoJDBC;
import com.codecool.shop.dao.implementation.ShoppingCartDaoMem;
import com.codecool.shop.model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Random;

@WebServlet(urlPatterns = {"/add"})
public class AddToCart extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        int idInt = Integer.parseInt(id);
        HttpSession session = req.getSession(true);
        ShoppingCartDao shoppingCart;
        if(session.isNew()){
            Random r = new Random();
            int orderNumber = r.nextInt((1000 - 1) + 1) + 1;
            session.setAttribute("order_number", orderNumber);
            shoppingCart = new ShoppingCartDaoJDBC(orderNumber);
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
        ProductDao productDataStore = ProductDaoJDBC.getInstance();
        Product addThisToCart = productDataStore.find(idInt);
        shoppingCart.add(addThisToCart);
        String whereFrom = req.getHeader("referer");
        resp.sendRedirect(whereFrom);
    }
}