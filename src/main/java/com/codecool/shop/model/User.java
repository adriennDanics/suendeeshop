package com.codecool.shop.model;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

public class User extends BaseModel {
    private static final Random RANDOM = new SecureRandom();
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;
    private String userName;
    private String password;
    private String email;
    private String salt;

    public User(String userName, String password, String email){
        super(userName);
        this.userName = userName;
        this.email = email;
        this.salt = this.getSalt(30);
        this.password = generateSecurePassword(password, salt);
    }

    public User(String userName, String password, String email, String salt){
        super(userName);
        this.userName = userName;
        this.email = email;
        this.salt = salt;
        this.password = password;
    }

    @Override
    public String getName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getSalt() {
        return salt;
    }

    private String getSalt(int length) {
        StringBuilder returnValue = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);
    }

    private byte[] generateStrongPasswordHash(char[] password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }

    public String generateSecurePassword(String password, String salt) {
        String returnValue = null;
        byte[] securePassword = generateStrongPasswordHash(password.toCharArray(), salt.getBytes());

        returnValue = Base64.getEncoder().encodeToString(securePassword);

        return returnValue;
    }

    public boolean passwordMatch(String providedPassword, String securedPassword, String salt) {
        boolean returnValue = false;

        String newSecurePassword = generateSecurePassword(providedPassword, salt);

        returnValue = newSecurePassword.equalsIgnoreCase(securedPassword);

        return returnValue;
    }

    public String getPassword() {
        return password;
    }
}
