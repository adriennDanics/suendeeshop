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
            String query = "INSERT INTO users(user_name, email, password) VALUES (?,?,?);";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
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
    public User find(int id) {
        String name = null;
        String password = null;
        String email = null;

        try(Connection con = getConnection()) {
            String query = "SELECT * FROM users WHERE id = ?;";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet results = statement.executeQuery();

            while (results.next()){
                name = results.getString("name");
                email = results.getString("email");
                password = results.getString("description");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        User newSupplier = new User(name, email, password);
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
