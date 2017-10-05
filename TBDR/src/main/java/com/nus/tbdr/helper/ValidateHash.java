/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.tbdr.helper;

import static com.nus.tbdr.helper.PwHash.generateHash;
import java.io.IOException;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Dineli
 */
public class ValidateHash {

    private static final String FILENAME = "files/hashValue.txt";
    private static final String SALT = "here-it-is";

    public Boolean loginAuthenticate(String username, String password) {
        Boolean isAuthenticated = false;
        String saltedPassword = SALT + password;
        String hashedPassword = generateHash(saltedPassword);
        String storedPasswordHash = readHashPWFromFile();

        if (hashedPassword.equals(storedPasswordHash)) {
            isAuthenticated = true;
        } else {
            isAuthenticated = false;
        }
        return isAuthenticated;
    }

    private String readHashPWFromFile() {
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
