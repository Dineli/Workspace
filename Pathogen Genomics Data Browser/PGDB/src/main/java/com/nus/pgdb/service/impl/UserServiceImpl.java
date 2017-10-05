/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.pgdb.service.impl;

import com.nus.pgdb.dao.IUserDAO;
import com.nus.pgdb.dao.impl.UsersDAOImpl;
import com.nus.pgdb.entity.UserProjects;
import com.nus.pgdb.entity.Users;
import com.nus.pgdb.service.IUser;
import java.util.List;

/**
 *
 * @author Dineli
 */
public class UserServiceImpl implements IUser {

    private final IUserDAO iudao = new UsersDAOImpl();

    @Override
    public Users getUserObjById(int userId) {
        return iudao.getUserObjById(userId);
    }

    @Override
    public Users getUserDataByUsernameService(String userName) {
        return iudao.getUserDataByUsername(userName);
    }

    @Override
    public boolean persistUserDataService(Users userObj) {
        return iudao.persistUserData(userObj);
    }

    @Override
    public List<Users> fetchAllUsersService() {
        return iudao.fetchAllUsers();
    }

    @Override
    public boolean deleteUserService(int recordId) {
        return iudao.deleteUser(recordId);
    }

    @Override
    public boolean userNameExistsService(String userName) {
        return iudao.isUserNameExists(userName);
    }

    @Override
    public boolean updateUserService(int recordId, byte[] encPassword, byte[] saltValue, boolean firstTimeResetting) {
        return iudao.updateUser(recordId, encPassword, saltValue, firstTimeResetting);
    }

    @Override
    public boolean persistUserProjectDataService(UserProjects userProjectObj) {
        return iudao.persistUserProjectData(userProjectObj);
    }

    @Override
    public List<Users> fetchAllUsersWithNoSharedProjectService(int userId, int projectId) {
        return iudao.fetchAllUsersWithNoSharedProject(userId, projectId);
    }

    @Override
    public boolean updateUserService(int recordId, String name, String email, String userName, boolean userType) {
        return iudao.updateUser(recordId, name, email, userName, userType);
    }

    @Override
    public Users getUserByEmailService(String email) {
        return iudao.getUserByEmail(email);
    }

    @Override
    public Users getUserDataByTokenService(String userToken) {
        return iudao.getUserDataByToken(userToken);
    }

    @Override
    public boolean validateUserUpdateService(String existingDBValue, String newValue, String valueType) {
        return iudao.validateUserUpdate(existingDBValue, newValue, valueType);
    }

}
