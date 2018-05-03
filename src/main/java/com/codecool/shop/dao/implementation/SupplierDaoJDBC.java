package com.codecool.shop.dao.implementation;

import com.codecool.shop.config.loadConfigJDBC;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class SupplierDaoJDBC implements SupplierDao{

    private static Properties properties = loadConfigJDBC.getProperties();
    private static final String DATABASE = "jdbc:postgresql://" + properties.getProperty("url") + "/" + properties.getProperty("database");
    private static final String USER = properties.getProperty("user");
    private static final String PASSWORD = properties.getProperty("password");
    private static SupplierDaoJDBC instance = null;


    public static SupplierDaoJDBC getInstance() {
        if (instance == null) {
            instance = new SupplierDaoJDBC();
        }
        return instance;
    }

    @Override
    public Supplier find(int id) {
        String name = null;
        String description = null;

        try(Connection con = getConnection()) {
            String query = "SELECT * FROM suppliers WHERE id = ?;";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet results = statement.executeQuery();

            while (results.next()){
                name = results.getString("name");
                description = results.getString("description");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return new Supplier(name, description);
    }

    @Override
    public void add(Supplier supplier) {
        try(Connection con = getConnection()) {
            String query = "INSERT INTO suppliers(name, description) VALUES (?,?);";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, supplier.getName());
            statement.setString(2, supplier.getDescription());
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
    public List<Supplier> getAll() {
        List<Supplier> suppliers = new ArrayList<>();

        try(Connection con = getConnection()) {
            String query = "SELECT id FROM suppliers WHERE name IS NOT NULL ;";
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet results = statement.executeQuery();

            while (results.next()){
                suppliers.add(this.find(results.getInt("id")));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return suppliers;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DATABASE,
                USER,
                PASSWORD
        );
    }
}
