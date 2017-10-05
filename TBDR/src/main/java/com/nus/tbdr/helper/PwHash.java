/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.tbdr.helper;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Dineli
 */
public final class PwHash {

    /*
            when genrating hashing for a new password, 
            1. call signup() with username and the desired pw
            2. get the hash value generated for the pw and store it in hashValue.txt
     */
    private static final Map<String, String> MAPVALUES = new HashMap<>();
    private static final String FILENAME = "files/hashValue.txt";
    public static final String SALT = "here-it-is";

    public static void main(String args[]) {
        PwHash demo = new PwHash();
        demo.signup("john", "dummy123");

        // login should succeed.
        if (demo.login("john", "dummy123")) {
            System.out.println("user login successfull.");
        }

        // login should fail because of wrong password.
        if (demo.login("john", "blahblah")) {
            System.out.println("User login successfull.");
        } else {
            System.out.println("user login failed.");
        }
    }

    public void signup(String username, String password) {
        String saltedPassword = SALT + password;
        String hashedPassword = generateHash(saltedPassword);
        MAPVALUES.put(username, hashedPassword);

    }

    public Boolean login(String username, String password) {
        Boolean isAuthenticated = false;
        // remember to use the same SALT value use used while storing password
        // for the first time.
        String saltedPassword = SALT + password;
        String hashedPassword = generateHash(saltedPassword);

        String storedPasswordHash = MAPVALUES.get(username);
//        String storedPasswordHash = readHashPWFromFile();
        if (hashedPassword.equals(storedPasswordHash)) {
            isAuthenticated = true;
        } else {
            isAuthenticated = false;
        }
        return isAuthenticated;
    }

    public static String generateHash(String input) {
        StringBuilder hash = new StringBuilder();
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] hashedBytes = sha.digest(input.getBytes());
            char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
            for (int idx = 0; idx < hashedBytes.length; ++idx) {
                byte b = hashedBytes[idx];
                hash.append(digits[(b & 0xf0) >> 4]);
                hash.append(digits[b & 0x0f]);
            }
        } catch (NoSuchAlgorithmException e) {
            // handle error here.
        }
        return hash.toString();
    }

    public String readHashPWFromFile() {
        String stringHashValue = null;
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            stringHashValue = IOUtils.toString(classLoader.getResourceAsStream(FILENAME));
        } catch (IOException ex) {
            ex.getMessage();
        }
        return stringHashValue;
    }

}
