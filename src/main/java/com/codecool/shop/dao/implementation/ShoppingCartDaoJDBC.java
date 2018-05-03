package com.codecool.shop.dao.implementation;

import com.codecool.shop.config.loadConfigJDBC;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.sun.javafx.collections.MappingChange;

import java.sql.*;
import java.util.*;

public class ShoppingCartDaoJDBC implements ShoppingCartDao {
    private static Properties properties = loadConfigJDBC.getProperties();
    private static final String DATABASE = "jdbc:postgresql://" + properties.getProperty("url") + "/" + properties.getProperty("database");
    private static final String USER = properties.getProperty("user");
    private static final String PASSWORD = properties.getProperty("password");
    private Map<Product, Integer> shoppingCartMap = new HashMap<>();


    @Override
    public void add(Product product) {
        try(Connection con = getConnection()) {
            String query = "INSERT INTO shoppingcart(user_id, product_id) VALUES (1,?);";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, product.getId());
            statement.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void remove(int id) {
        try(Connection con = getConnection()) {
            String query = "DELETE FROM shoppingcart WHERE id = (SELECT id FROM shoppingcart WHERE product_id = ? LIMIT 1);";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, id);
            statement.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Map<Product, Integer> getAll() {
        ProductDao productDao = ProductDaoJDBC.getInstance();

        try(Connection con = getConnection()) {
            String query = "SELECT product_id FROM shoppingcart WHERE 'user' = 'user';";
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet results = statement.executeQuery();

            while (results.next()){
                Product currentProduct = productDao.find(results.getInt("product_id"));
                if (shoppingCartMap.containsKey(currentProduct)) {
                    Integer value = shoppingCartMap.get(currentProduct);
                    shoppingCartMap.put(currentProduct, ++value);
                } else {
                    shoppingCartMap.put(currentProduct, 1);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return shoppingCartMap;
    }

    @Override
    public int length() {
        return shoppingCartMap.size();
    }

    @Override
    public void clear() {
        shoppingCartMap.clear();
        try(Connection con = getConnection()) {
            String query = "DELETE FROM shoppingcart WHERE 'user' = 'user';";
            PreparedStatement statement = con.prepareStatement(query);
            statement.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DATABASE,
                USER,
                PASSWORD
        );
    }
}
