package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ShoppingCart;
import com.codecool.shop.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartMem implements ShoppingCart {
    private static List<Product> shoppingCartList = new ArrayList<Product>();
    private static ShoppingCartMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private ShoppingCartMem() {
    }

    public static ShoppingCartMem getInstance() {
        if (instance == null) {
            instance = new ShoppingCartMem();
        }
        return instance;
    }

    public static List<Product> getAll() {
        return shoppingCartList;
    }

    @Override
    public Product find(int id) {
        return shoppingCartList.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void add(Product product) {
        shoppingCartList.add(product);
    }

    @Override
    public void remove(int id) {
        shoppingCartList.remove(find(id));
    }
}
