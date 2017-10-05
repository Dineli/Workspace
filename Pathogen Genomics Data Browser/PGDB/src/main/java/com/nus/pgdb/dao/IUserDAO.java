/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.pgdb.dao;

import com.nus.pgdb.entity.UserProjects;
import com.nus.pgdb.entity.Users;
import java.util.List;

/**
 *
 * @author Dineli
 */
public interface IUserDAO {

    Users getUserObjById(int userId);

    Users getUserDataByUsername(String userName);

    boolean persistUserData(Users userObj);
    
    List<Users> fetchAllUsers();
    
    List<Users> fetchAllUsersWithNoSharedProject(int userId, int projectId);
    
    boolean deleteUser(int recordId);
    
    boolean isUserNameExists(String userName);
        
    boolean updateUser(int recordId, byte[] encPassword, byte[] saltValue, boolean firstTimeResetting); //for password reset
    
    boolean updateUser(int recordId, String name, String email, String userName, boolean userType); //for basic user details
    
    boolean persistUserProjectData(UserProjects userProjectObj);
    
    boolean validateUserUpdate(String existingDBValue, String newValue, String valueType);
    
    Users getUserByEmail(String email);
    
    Users getUserDataByToken(String userToken); //for forgot password function

}
