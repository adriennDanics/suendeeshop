package com.codecool.shop.dao;

import com.codecool.shop.model.Product;

import java.util.List;
import java.util.Map;

public interface ShoppingCartDao {

    void add(Product product);
    void remove(int id);
    void clear();
    Map<Product, Integer> getAll();

    int length();

}
