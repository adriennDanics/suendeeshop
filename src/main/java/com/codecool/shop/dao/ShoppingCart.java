package com.codecool.shop.dao;

import com.codecool.shop.model.Product;

import java.util.List;

public interface ShoppingCart {

    void add(Product product);
    void remove(int id);

    int length();

}
