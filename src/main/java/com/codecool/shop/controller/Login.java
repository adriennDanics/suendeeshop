package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.UserDAO;
import com.codecool.shop.dao.implementation.JDBC.ShoppingCartDaoJDBC;
import com.codecool.shop.dao.implementation.JDBC.UserDaoJDBC;
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


@WebServlet(urlPatterns = {"/login"})
public class Login extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("recipient", "World");
        String origin = req.getHeader("referer");
        if(origin != null){
            if (origin.equals("http://localhost:8080/login")) {
                context.setVariable("message", "Wrong username or password");
            }else{
                context.setVariable("message", "");
            }
        }
        engine.process("product/login.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            UserDAO findUser = UserDaoJDBC.getInstance();
            User loginUser = findUser.find(req.getParameter("name"));
            HttpSession session = req.getSession(true);
            boolean rightPassword = false;
            rightPassword = loginUser.passwordMatch(req.getParameter("password"), loginUser.getPassword(), loginUser.getSalt());
            if(rightPassword){
                session.setAttribute("user", loginUser.getId());
                session.setAttribute("cart", new ShoppingCartDaoJDBC(loginUser.getId()));
                resp.sendRedirect("/");
            } else {
                resp.sendRedirect("/login");
            }
        } catch (NullPointerException ex){
            resp.sendRedirect("/login");
        }
    }
}