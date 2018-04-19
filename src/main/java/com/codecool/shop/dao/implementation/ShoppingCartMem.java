package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ShoppingCart;
import com.codecool.shop.model.Product;

import java.util.*;

public class ShoppingCartMem implements ShoppingCart {
    private static List<Product> shoppingCartList = new ArrayList<>();
    private static Map<Product, Integer> shoppingCartMap = new HashMap<>();
    private static int DEFAULTVALUE = 1;
    private static int checkNumber = shoppingCartList.size();
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
        ListIterator<Product> iterator = shoppingCartList.listIterator();
        while (iterator.hasNext()) {
            Product productToCheck = iterator.next();
            if (productToCheck.getId() == (id)){
                Integer value = shoppingCartMap.get(productToCheck);
                if(value > 1) {
                    shoppingCartMap.put(productToCheck, --value);
                    --checkNumber;
                    break;
                } else {
                    shoppingCartMap.remove(productToCheck);
                    shoppingCartList.remove(productToCheck);
                    --checkNumber;
                    break;
                }

            }
        }
    }

    @Override
    public void clear() {
        shoppingCartList.clear();
        shoppingCartMap.clear();
        checkNumber = 0;
    }
}
