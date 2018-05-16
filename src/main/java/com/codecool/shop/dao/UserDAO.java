package com.codecool.shop.dao;

import com.codecool.shop.model.User;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface UserDAO {
    void add(User supplier);
    User find(int id);
    void remove(int id);
}
