/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.pgdb.service;

import com.nus.pgdb.entity.UserProjects;
import com.nus.pgdb.entity.Users;
import java.util.List;

/**
 *
 * @author Dineli
 */
public interface IUser {

    Users getUserObjById(int userId);

    Users getUserDataByUsernameService(String userName);

    boolean persistUserDataService(Users userObj);

    List<Users> fetchAllUsersService();

    List<Users> fetchAllUsersWithNoSharedProjectService(int userId, int projectId);

    boolean deleteUserService(int recordId);

    boolean userNameExistsService(String userName);

    boolean updateUserService(int recordId, byte[] encPassword, byte[] saltValue, boolean firstTimeResetting);

    boolean updateUserService(int recordId, String name, String email, String userName, boolean userType);

    boolean persistUserProjectDataService(UserProjects userProjectObj);
    
    boolean validateUserUpdateService(String existingDBValue, String newValue, String valueType);

    Users getUserByEmailService(String email);

    Users getUserDataByTokenService(String userToken);

}
