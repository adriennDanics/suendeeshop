package com.codecool.shop.dao.implementation.JDBC;

import com.codecool.shop.config.loadConfigJDBC;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class ProductDaoJDBC implements ProductDao {
    private static Properties properties = loadConfigJDBC.getProperties();
    private static final String DATABASE = "jdbc:postgresql://" + properties.getProperty("url") + "/" + properties.getProperty("database");
    private static final String USER = properties.getProperty("user");
    private static final String PASSWORD = properties.getProperty("password");
    private static ProductDaoJDBC instance = null;


    public static ProductDaoJDBC getInstance() {
        if (instance == null) {
            instance = new ProductDaoJDBC();
        }
        return instance;
    }

    @Override
    public void add(Product product) {
        try(Connection con = getConnection()) {
            String query = "INSERT INTO products(name, default_price, description, supplier_id, productcat_id) VALUES (?,?,?,?,?);";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, product.getName());
            statement.setFloat(2, product.getDefaultPrice());
            statement.setString(3, product.getDescription());
            statement.setInt(4, product.getSupplier().getId());
            statement.setInt(5, product.getProductCategory().getId());
            statement.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void remove(int id) {
        try(Connection con = getConnection()) {
            String query = "DELETE FROM products WHERE id = ?;";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, id);
            statement.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Product find(int id) {

        String name = null;
        Float defaultPrice = null;
        String description = null;
        Integer supplierId = null;
        Integer productCategoryId = null;

        try(Connection con = getConnection()) {
            String query = "SELECT * FROM products WHERE id = ?;";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet results = statement.executeQuery();

            while (results.next()){
                name = results.getString("name");
                defaultPrice = results.getFloat("default_price");
                description = results.getString("description");
                supplierId = results.getInt("supplier_id");
                productCategoryId = results.getInt("productcat_id");

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        ProductCategoryDao productCategory = ProductCategoryDaoJDBC.getInstance();
        SupplierDao supplier = SupplierDaoJDBC.getInstance();
        Product newProduct = new Product(name, defaultPrice, "USD", description, productCategory.find(productCategoryId), supplier.find(supplierId));
        newProduct.setId(id);
        return newProduct;
    }

    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();

        try(Connection con = getConnection()) {
            String query = "SELECT id FROM products WHERE name IS NOT NULL ;";
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet results = statement.executeQuery();

            while (results.next()){
                products.add(this.find(results.getInt("id")));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return products;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        List<Product> productsBySupplier = new ArrayList<>();

        try(Connection con = getConnection()) {
            String query = "SELECT id FROM products WHERE name IS NOT NULL AND products.supplier_id = ? ;";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, supplier.getId());
            ResultSet results = statement.executeQuery();

            while (results.next()){
                productsBySupplier.add(this.find(results.getInt("id")));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return productsBySupplier;

    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        List<Product> productsByCategory = new ArrayList<>();

        try(Connection con = getConnection()) {
            String query = "SELECT id FROM products WHERE name IS NOT NULL AND products.productcat_id = ? ;";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, productCategory.getId());
            ResultSet results = statement.executeQuery();

            while (results.next()){
                productsByCategory.add(this.find(results.getInt("id")));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return productsByCategory;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DATABASE,
                USER,
                PASSWORD
        );
    }
}
