/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.pgdb.dao.impl;

import com.nus.pgdb.dao.IUserDAO;
import com.nus.pgdb.entity.UserProjects;
import com.nus.pgdb.entity.Users;
import com.nus.pgdb.entity.persistence.HibernateUtil;
import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Dineli
 */
public class UsersDAOImpl implements IUserDAO {

    private SessionFactory sessionFactory = null;
    private static final Logger LOGGER = LoggerFactory.getLogger(UsersDAOImpl.class);

    public UsersDAOImpl() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public Users getUserObjById(int userId) {
        LOGGER.info("Fetch userObj for userId: " + userId);
        Session session = sessionFactory.openSession();
        Users userObj = null;
        try {
            Query query = session.getNamedQuery("Users.findById").setInteger("id", userId);
            if (!query.list().isEmpty()) {
                userObj = (Users) query.list().get(0);
                LOGGER.info("User obj successfully fetched for userId: " + userId);
            }
        } catch (Exception exception) {
            LOGGER.error("Error occured while fetching userId");
            exception.getMessage();
        } finally {
            session.close();
        }
        return userObj;
    }

    @Override
    public Users getUserDataByUsername(String userName) {
        LOGGER.info("Fetch user details for userName: " + userName);
        Session session = sessionFactory.openSession();
        Users userObj = null;
        try {
            Query query = session.getNamedQuery("Users.findByUserName").setString("userName", userName);
            if (!query.list().isEmpty()) {
                userObj = (Users) query.list().get(0);
                LOGGER.info("User data successfully fetched for userName: " + userName);
            }
        } catch (Exception exception) {
            LOGGER.error("Error occured while fetching userId");
            exception.getMessage();
        } finally {
            session.close();
        }
        return userObj;
    }

    @Override
    public boolean persistUserData(Users userObj) {
        LOGGER.info("Saving user data to database....");
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
//        Integer jobId = null;
        boolean status = false;
        try {
            transaction = session.beginTransaction();
//            jobId = (Integer) session.save(userObj);
            session.saveOrUpdate(userObj);
            transaction.commit();
//            if (jobId > 0) {
            LOGGER.info("User data successfully saved ");
            status = true;
//            }
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error("Error occured while saving user data to database " + e.getMessage());
        } finally {
            session.close();
        }
        return status;
    }

    @Override
    public List<Users> fetchAllUsers() {
        LOGGER.info("Fetch all users");
        Session session = sessionFactory.openSession();
        List<Users> userList = null;
        try {
            Query query = session.getNamedQuery("Users.findAll");
            if (!query.list().isEmpty()) {
                userList = query.list();
                LOGGER.info("userList size: " + userList.size());
            }
        } catch (Exception exception) {
            LOGGER.error("Error occured while fetching all users");
            exception.getMessage();
        } finally {
            session.close();
        }
        return userList;
    }

    @Override
    public boolean deleteUser(int recordId) {
        LOGGER.info("Deleting user ....");
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        boolean status = false;
        try {
            transaction = session.beginTransaction();
            Serializable id = recordId;
            Object persistentInstance = session.load(Users.class, id);
            if (persistentInstance != null) {
                session.delete(persistentInstance);
                LOGGER.info("Record successfully deleted");
                status = true;
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            status = false;
            LOGGER.error("Error occured while deleting user data " + e.getMessage());
        } finally {
            session.close();
        }
        return status;
    }

    @Override
    public boolean isUserNameExists(String userName) {
        LOGGER.info("Checking if the user name is already in the system ....");
        Session session = sessionFactory.openSession();
        boolean isRecordExists = false;
        try {
            Query query = session.createQuery("SELECT u FROM Users u WHERE u.userName= :userName").setString("userName", userName);
            if ((null != query.list()) && (query.list().size() > 0)) {
                isRecordExists = true;
                LOGGER.info("User name already exists.");
            }
        } catch (Exception exception) {
            LOGGER.error("Error occured while checking the existence of the user name");
            exception.getMessage();
        } finally {
            session.close();
        }
        return isRecordExists;
    }

    @Override
    public boolean updateUser(int recordId, byte[] encPassword, byte[] saltValue, boolean firstTimeResetting) {
        LOGGER.info("Updating user for userId: " + recordId);
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        boolean status = false;
        try {
            transaction = session.beginTransaction();
            Users userObj = (Users) session.get(Users.class, recordId);
            userObj.setEncryptedPassword(encPassword);
            userObj.setSalt(saltValue);
            if (firstTimeResetting) { //these fields only get updated when pw is reset for the first time
                userObj.setDummyPassword("");
                userObj.setIsPwReset(true);
            }
            session.update(userObj);
            transaction.commit();
            status = true;
            LOGGER.info("User updated successfully");
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error("Error occured while updating user related to reset password function " + e.getMessage());
        } finally {
            session.close();
        }
        return status;
    }

    @Override
    public boolean persistUserProjectData(UserProjects userProjectObj) {
        LOGGER.info("Saving user-project data to database....");
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Integer userProjectId = null;
        boolean status = false;
        try {
            transaction = session.beginTransaction();
            userProjectId = (Integer) session.save(userProjectObj);
            transaction.commit();
            session.flush();
            if (userProjectId > 0) {
                LOGGER.info("User-project data successfully saved ");
                status = true;
            }
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error("Error occured while saving user-project data to database " + e.getMessage());
        } finally {
            session.close();
        }
        return status;
    }

    @Override
    public List<Users> fetchAllUsersWithNoSharedProject(int userId, int projectId) {
        LOGGER.info("Fetch all users except " + userId);
        Session session = sessionFactory.openSession();
        List<Users> userList = null;
        try {
            Query query = session.createQuery("SELECT u FROM Users u WHERE u.id NOT IN (SELECT sup.sharedUserId.id FROM SharedUserProjects sup WHERE sup.sharedProjectId.id = :sharedProjectId) AND u.id <> :userId ORDER BY u.name").setInteger("userId", userId).setInteger("sharedProjectId", projectId);
            if (!query.list().isEmpty()) {
                userList = query.list();
                LOGGER.info("userList size: " + userList.size());
            }
        } catch (Exception exception) {
            LOGGER.error("Error occured while fetching all users except " + userId);
            exception.getMessage();
        } finally {
            session.close();
        }
        return userList;
    }

    @Override
    public boolean updateUser(int recordId, String name, String email, String userName, boolean userType) {
        LOGGER.info("Updating user details for userId: " + recordId);
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        boolean status = false;
        try {
            transaction = session.beginTransaction();
            Users userObj = (Users) session.get(Users.class, recordId);
            userObj.setName(name);
            userObj.setEmail(email);
            userObj.setUserName(userName);
            userObj.setIsAdmin(userType);
            session.update(userObj);
            transaction.commit();
            status = true;
            LOGGER.info("User updated successfully");
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error("Error occured while updating basic user details " + e.getMessage());
        } finally {
            session.close();
        }
        return status;
    }

    @Override
    public Users getUserByEmail(String email) {
        LOGGER.info("Checking if the inserted email exists in the system ....");
        Session session = sessionFactory.openSession();
        Users userObj = null;
        try {
            Query query = session.createQuery("SELECT u FROM Users u WHERE u.email= :userEmail").setString("userEmail", email);
            if ((null != query.list()) && (query.list().size() > 0)) {
                userObj = (Users) query.uniqueResult();
                LOGGER.info("Email already exists.");
            } else {
                LOGGER.error("No such email exists in the system.");
            }
        } catch (Exception exception) {
            LOGGER.error("Error occured while checking the existence of the email");
            exception.getMessage();
        } finally {
            session.close();
        }
        return userObj;
    }

    @Override
    public Users getUserDataByToken(String userToken) {
        LOGGER.info("Fetch user details for token: " + userToken + " ...");
        Session session = sessionFactory.openSession();
        Users userObj = null;
        try {
            Query query = session.createQuery("SELECT u FROM Users u WHERE u.dummyPassword=:token").setString("token", userToken);
            if ((null != query.list()) && (!query.list().isEmpty())) {
                userObj = (Users) query.list().get(0);
                LOGGER.info("User data successfully fetched for token");
            } else {
                LOGGER.error("No token available for the given user");
            }
        } catch (Exception exception) {
            LOGGER.error("Error occured while fetching userId");
            exception.getMessage();
        } finally {
            session.close();
        }
        return userObj;
    }

    @Override
    public boolean validateUserUpdate(String existingDBValue, String newValue, String valueType) {
        LOGGER.info("Validating whether the inserted values existing in the system ....");
        Session session = sessionFactory.openSession();
        boolean isExists = false;
        Query query = null;
        try {
            switch (valueType) {
                case "validateUserName":
                    query = session.createQuery("SELECT u FROM Users u WHERE u.userName != :existingUN AND u.userName = :newUN").setString("existingUN", existingDBValue).setString("newUN", newValue);
                    break;
                case "validateEmail":
                    query = session.createQuery("SELECT u FROM Users u WHERE u.email != :existingEmail AND u.email = :newEmail").setString("existingEmail", existingDBValue).setString("newEmail", newValue);
                    break;
            }
            if ((null != query.list()) && (query.list().size() > 0)) {
                isExists = true;
                LOGGER.error("Record exists.");
            } else {
                LOGGER.info("No record exists.");
            }
        } catch (Exception exception) {
            LOGGER.error("Error occured while checking the existence of the email");
            exception.getMessage();
        } finally {
            session.close();
        }
        return isExists;
    }

}
