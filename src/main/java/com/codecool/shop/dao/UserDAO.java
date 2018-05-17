package com.codecool.shop.dao;

import com.codecool.shop.model.User;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface UserDAO {
    void add(User supplier);
    User find(String userName);
    void remove(int id);
}
