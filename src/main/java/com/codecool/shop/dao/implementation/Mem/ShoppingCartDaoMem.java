package com.codecool.shop.dao.implementation.Mem;

import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.model.Product;

import java.util.*;

public class ShoppingCartDaoMem implements ShoppingCartDao {
    private Map<Product, Integer> shoppingCartMap = new HashMap<>();
    private int DEFAULTVALUE = 1;
    private int checkNumber = shoppingCartMap.size();
    static String DEFAULT_STRING = "NONE";
    public static String name =DEFAULT_STRING;
    public static String billing_address =DEFAULT_STRING;
    public static String shipping_address =DEFAULT_STRING;
    public static String phone_number =DEFAULT_STRING;
    public static String email_address =DEFAULT_STRING;



    @Override
    public int length(){
        return checkNumber;
    }

    public Map<Product, Integer> getAll() {
        return shoppingCartMap;
    }

    @Override
    public void add(Product product) {
        if (shoppingCartMap.containsKey(product)) {
            Integer value = shoppingCartMap.get(product);
            shoppingCartMap.put(product, ++value);
            ++checkNumber;
        } else {
            shoppingCartMap.put(product, DEFAULTVALUE);
            ++checkNumber;
        }
    }


    @Override
    public void remove(int id) {
        for (Product cartItem : shoppingCartMap.keySet()) {
            if (cartItem.getId() == id) {
                Integer value = shoppingCartMap.get(cartItem);
                if (value > 1) {
                    shoppingCartMap.put(cartItem, --value);
                    --checkNumber;
                } else {
                    shoppingCartMap.remove(cartItem);
                    --checkNumber;
                }
            }
        }
    }

    @Override
    public void clear() {
        shoppingCartMap.clear();
        checkNumber = 0;
    }
}
