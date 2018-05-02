package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.sql.*;
import java.util.List;

public class ProductDaoJDBC implements ProductDao {
    @Override
    public void add(Product product) {
        //TODO
    }

    @Override
    public void remove(int id) {
        //TODO
    }

    @Override
    public Product find(int id) {
        return null;
        //TODO
    }

    @Override
    public List<Product> getAll() {
        return null;
        //TODO
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        return null;
        //TODO
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        return null;
        //TODO
    }
}
