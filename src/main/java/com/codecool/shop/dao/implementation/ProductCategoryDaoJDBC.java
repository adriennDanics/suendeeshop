package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.config.loadConfigJDBC;


import java.sql.*;
import java.util.List;
import java.util.Properties;

public class ProductCategoryDaoJDBC implements ProductCategoryDao {

    private static Properties properties = loadConfigJDBC.getProperties();
    private static final String DATABASE = "jdbc:postgresql://" + properties.getProperty("url") + "/" + properties.getProperty("database");
    private static final String USER = properties.getProperty("user");
    private static final String PASSWORD = properties.getProperty("password");

    @Override
    public void add(ProductCategory category) {
        try(Connection con = getConnection()) {
            String query = "INSERT INTO productcategories(name, department, description) VALUES (?,?,?);";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, category.getName());
            statement.setString(2, category.getDepartment());
            statement.setString(3, category.getDescription());
            statement.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void remove(int id) {
        //TODO
    }

    @Override
    public ProductCategory find(int id) {
        return null;
        //TODO
    }

    @Override
    public List<ProductCategory> getAll() {
        return null;
        //TODO
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DATABASE,
                USER,
                PASSWORD
        );
    }


}
