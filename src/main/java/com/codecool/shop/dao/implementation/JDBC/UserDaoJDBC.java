package com.codecool.shop.dao.implementation.JDBC;

import com.codecool.shop.config.loadConfigJDBC;
import com.codecool.shop.dao.UserDAO;
import com.codecool.shop.model.User;

import java.sql.*;
import java.util.Properties;


public class UserDaoJDBC implements UserDAO{
    private static Properties properties = loadConfigJDBC.getProperties();
    private static final String DATABASE = "jdbc:postgresql://" + properties.getProperty("url") + "/" + properties.getProperty("database");
    private static final String USER = properties.getProperty("user");
    private static final String PASSWORD = properties.getProperty("password");
    private static UserDaoJDBC instance = null;

    public static UserDaoJDBC getInstance() {
        if (instance == null) {
            instance = new UserDaoJDBC();
        }
        return instance;
    }

    @Override
    public void add(User user) {
        try(Connection con = getConnection()) {
            String query = "INSERT INTO users(user_name, email, password, salt) VALUES (?,?,?,?);";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getSalt());
            statement.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void remove(int id) {
        try(Connection con = getConnection()) {
            String query = "DELETE FROM users WHERE id = ?;";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, id);
            statement.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public User find(String userName) {
        int id = 0;
        String password = null;
        String email = null;
        String salt = null;

        try(Connection con = getConnection()) {
            String query = "SELECT * FROM users WHERE user_name = ?;";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, userName);
            ResultSet results = statement.executeQuery();

            while (results.next()){
                id = results.getInt("id");
                email = results.getString("email");
                password = results.getString("password");
                salt = results.getString("salt");

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        User newSupplier = new User(userName, password, email, salt);
        newSupplier.setId(id);
        return newSupplier;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DATABASE,
                USER,
                PASSWORD
        );
    }
}
