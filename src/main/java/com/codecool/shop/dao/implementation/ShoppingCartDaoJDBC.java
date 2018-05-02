package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.model.Product;

import java.sql.*;
import java.util.Map;

public class ShoppingCartDaoJDBC implements ShoppingCartDao {
    @Override
    public void add(Product product) {
        //TODO
    }

    @Override
    public void remove(int id) {
        //TODO
    }

    @Override
    public Map<Product, Integer> getAll() {
        return null;
        //TODO
    }

    @Override
    public int length() {
        return 0;
        //TODO
    }

    @Override
    public void clear() {
        //TODO
    }
}
