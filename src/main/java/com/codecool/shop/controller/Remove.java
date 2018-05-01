package com.codecool.shop.controller;

import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.dao.implementation.ShoppingCartMem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/remove"})
public class Remove extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        int idInt = Integer.parseInt(id);
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
        shoppingCartDao.remove(idInt);
        String whereFrom = req.getHeader("referer");;
        resp.sendRedirect(whereFrom);
    }
}