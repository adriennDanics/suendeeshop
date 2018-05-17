package com.codecool.shop.dao.implementation.JDBC;

import com.codecool.shop.config.loadConfigJDBC;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.model.Product;

import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.*;

public class ShoppingCartDaoJDBC implements ShoppingCartDao {
    private static Properties properties = loadConfigJDBC.getProperties();
    private static final String DATABASE = "jdbc:postgresql://" + properties.getProperty("url") + "/" + properties.getProperty("database");
    private static final String USER = properties.getProperty("user");
    private static final String PASSWORD = properties.getProperty("password");

    static String DEFAULT_STRING = "NONE";
    public static String name =DEFAULT_STRING;
    public static String billing_address =DEFAULT_STRING;
    public static String shipping_address =DEFAULT_STRING;
    public static String phone_number =DEFAULT_STRING;
    public static String email_address =DEFAULT_STRING;
    private int orderNumber;
    private int userId;
    private Random random = new Random();

    private int checkNumber;

    public ShoppingCartDaoJDBC(int userId){
        this.orderNumber = random.nextInt(10000);
        this.userId = userId;
    }

    @Override
    public void add(Product product) {
        try(Connection con = getConnection()) {
            String query = "INSERT INTO shoppingcart(user_id, product_id, order_number) VALUES (?,?,?);";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, userId);
            statement.setInt(2, product.getId());
            statement.setInt(3, orderNumber);
            statement.execute();
            ++checkNumber;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void remove(int id) {
        try(Connection con = getConnection()) {
            String query = "DELETE FROM shoppingcart WHERE id = (SELECT id FROM shoppingcart WHERE product_id = ? AND order_number = ? LIMIT 1) AND user_id = ?;";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, id);
            statement.setInt(2, orderNumber);
            statement.setInt(3, userId);
            statement.execute();
            --checkNumber;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Map<Product, Integer> getAll() {
        ProductDao productDao = ProductDaoJDBC.getInstance();
        Map<Product, Integer> shoppingCartMap = new HashMap<Product, Integer>();

        try(Connection con = getConnection()) {
            String query = "SELECT product_id FROM shoppingcart WHERE order_number = ? AND user_id = ?;";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, orderNumber);
            statement.setInt(2, userId);
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
        return checkNumber;
    }

    @Override
    public void clear() {
        checkNumber = 0;
        try(Connection con = getConnection()) {
            String query = "DELETE FROM shoppingcart WHERE order_number = ? AND user_id = ?;";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, orderNumber);
            statement.setInt(2, userId);
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
