package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ShoppingCart;
import com.codecool.shop.model.Product;

import java.util.*;

public class ShoppingCartMem implements ShoppingCart {
    private static List<Product> shoppingCartList = new ArrayList<>();
    private static Map<Product, Integer> shoppingCartMap = new HashMap<>();
    private static int DEFAULTVALUE = 1;
    private static int checkNumber = shoppingCartList.size();


    @Override
    public int length(){
        return checkNumber;
    }

    public static Map<Product, Integer> getAll() {
        return shoppingCartMap;
    }

    public Product find(int id) {
        //TOdo
        return null;
    }

    @Override
    public void add(Product product) {
        for(int i = 0; i <= checkNumber; i++) {
            if (shoppingCartList.contains(product)) {
                Integer value = shoppingCartMap.get(product);
                shoppingCartMap.put(product, ++value);
                ++checkNumber;
                break;

            } else {
                shoppingCartMap.put(product, DEFAULTVALUE);
                shoppingCartList.add(product);
                ++checkNumber;
                break;
            }
        }
    }

    @Override
    public void remove(int id) {
        /*for(Product productToCheck: shoppingCartList) {
            if (productToCheck.getId() == (id)){
                Integer value = shoppingCartMap.get(productToCheck);
                if(value > 1) {
                    shoppingCartMap.put(productToCheck, value - 1);
                } else {
                    shoppingCartMap.remove(productToCheck);
                    shoppingCartList.remove(productToCheck);
                }
            }
        }*/
        //TODO
    }
}
