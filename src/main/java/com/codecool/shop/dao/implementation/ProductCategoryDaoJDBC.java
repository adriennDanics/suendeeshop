package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.config.loadConfigJDBC;
import com.sun.org.apache.regexp.internal.RE;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ProductCategoryDaoJDBC implements ProductCategoryDao {

    private static Properties properties = loadConfigJDBC.getProperties();
    private static final String DATABASE = "jdbc:postgresql://" + properties.getProperty("url") + "/" + properties.getProperty("database");
    private static final String USER = properties.getProperty("user");
    private static final String PASSWORD = properties.getProperty("password");

    private List<ProductCategory> productCategories = new ArrayList<>();
    private static ProductCategoryDaoJDBC instance = null;


    public static ProductCategoryDaoJDBC getInstance() {
        if (instance == null) {
            instance = new ProductCategoryDaoJDBC();
        }
        return instance;
    }

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

        String name = null;
        String department = null;
        String description = null;

        try(Connection con = getConnection()) {
            String query = "SELECT * FROM productcategories WHERE id = ?;";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet results = statement.executeQuery();

            while (results.next()){
                name = results.getString("name");
                department = results.getString("department");
                description = results.getString("description");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        ProductCategory newCategory = new ProductCategory(name, department, description);
        newCategory.setId(id);
        return  newCategory;
    }

    @Override
    public List<ProductCategory> getAll() {
        try(Connection con = getConnection()) {
            String query = "SELECT id FROM productcategories WHERE name IS NOT NULL ;";
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet results = statement.executeQuery();

            while (results.next()){
                Integer currentId = results.getInt("id");
                ProductCategory categoryToAdd = this.find(currentId);
                productCategories.add(categoryToAdd);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return productCategories;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DATABASE,
                USER,
                PASSWORD
        );
    }


}
