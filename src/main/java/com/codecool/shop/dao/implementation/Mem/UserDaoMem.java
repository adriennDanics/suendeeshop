package com.codecool.shop.dao.implementation.Mem;

import com.codecool.shop.dao.UserDAO;
import com.codecool.shop.model.User;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoMem implements UserDAO{
    private List<User> data = new ArrayList<>();
    private static UserDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private UserDaoMem() {
    }

    public static UserDaoMem getInstance() {
        if (instance == null) {
            instance = new UserDaoMem();
        }
        return instance;
    }

    @Override
    public void add(User user) {
        user.setId(data.size() + 1);
        data.add(user);
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }

    @Override
    public User find(int id) {
        return data.stream().filter(t-> t.getId() == id).findFirst().orElse(null);
    }

}
