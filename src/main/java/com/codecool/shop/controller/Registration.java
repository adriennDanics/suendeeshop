package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.dao.UserDAO;
import com.codecool.shop.dao.implementation.JDBC.ShoppingCartDaoJDBC;
import com.codecool.shop.dao.implementation.JDBC.UserDaoJDBC;
import com.codecool.shop.model.User;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Properties;

@WebServlet(urlPatterns = {"/registration"})
public class Registration extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("recipient", "World");
        engine.process("product/registration.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User newUser = new User(req.getParameter("name"), req.getParameter("password"), req.getParameter("email"));
        UserDaoJDBC addUser = UserDaoJDBC.getInstance();
        addUser.add(newUser);


        // Recipient's email ID needs to be mentioned.
        String to = req.getParameter("email");

        // Sender's email ID needs to be mentioned
        String from = "SueNDeeShop" +
                "@gmail.com";

        // Assuming you are sending email from localhost
        String host = "0.0.0.0";

        // Get system properties
        Properties properties = new Properties();

        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Setup mail server
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        // Get the default Session object.
        Session session = Session.getInstance(properties,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("SueNDeeShop", "codecool123");
                    }
                });

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("Thank you for registrating!");

            // Now set the actual message
            message.setText("Dear " + req.getParameter("name") + "," + "\n \nThank you for choosing to join our fabulous webshop.\n \nWe hope you will enjoy our products. Have a nice day!\nBest Regards,\nSue");

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

        HttpSession httpSession = req.getSession(true);
        ShoppingCartDao shoppingCart = new ShoppingCartDaoJDBC(addUser.getMostRecentUserId());
        httpSession.setAttribute("cart", shoppingCart);

        resp.sendRedirect("/");
    }
}