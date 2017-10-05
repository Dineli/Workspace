/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.pgdb.dao.impl;

import com.nus.pgdb.dao.IJobsCartDAO;
import com.nus.pgdb.entity.JobsCart;
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
public class JobsCartDAOImpl implements IJobsCartDAO {

    private SessionFactory sessionFactory = null;
    private static final Logger LOGGER = LoggerFactory.getLogger(JobsCartDAOImpl.class);

    public JobsCartDAOImpl() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public boolean persistJobsCartDetails(JobsCart jobsCartObj) {
        LOGGER.info("Saving cart items to database....");
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Integer jobId = null;
        boolean status = false;
        try {
            transaction = session.beginTransaction();
            jobId = (Integer) session.save(jobsCartObj);
            transaction.commit();
            if (jobId > 0) {
                LOGGER.info("Cart item successfully saved ");
                status = true;
            }
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error("Error occured while saving cart item to database " + e.getMessage());
        } finally {
            session.close();
        }
        return status;
    }

    @Override
    public long fetchCartSize(int userId) {
        LOGGER.info("Fetching cart size for userId: " + userId + " ....");
        Session session = sessionFactory.openSession();
        long count = 0;
        try {
            Query query = session.createQuery("SELECT COUNT(*) FROM JobsCart j WHERE j.userId.id= :userId").setInteger("userId", userId);
            count = (Long) query.uniqueResult();
            LOGGER.info("Current cart size is " + count);
        } catch (Exception exception) {
            LOGGER.error("Error occured while fetching cart size.");
            exception.getMessage();
        } finally {
            session.close();
        }
        return count;
    }

    @Override
    public boolean deleteCartItem(int recordId, int userId) {
        LOGGER.info("Deleting cart items ....");
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        boolean status = false;
        try {
            transaction = session.beginTransaction();
            if (userId > 0) {
                Query query = session.createQuery("DELETE JobsCart j where j.userId.id = :userId").setInteger("userId", userId);
                query.executeUpdate();
                status = true;
                LOGGER.info("Records successfully deleted");
            } else {
                Serializable id = recordId;
                Object persistentInstance = session.load(JobsCart.class, id);
                if (persistentInstance != null) {
                    session.delete(persistentInstance);
                    LOGGER.info("Record successfully deleted");
                    status = true;
                }
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error("Error occured while deleting cart items " + e.getMessage());
        } finally {
            session.close();
        }
        return status;
    }

    @Override
    public boolean isExists(int userId, String sampleId) {
        LOGGER.info("Checking if the cart items already exists for userId " + userId + " ....");
        Session session = sessionFactory.openSession();
        boolean isRecordExists = false;
        try {
            Query query = session.createQuery("SELECT j FROM JobsCart j WHERE j.userId.id= :userId AND j.sampleId.id= :sampleId");
            query.setInteger("userId", userId);
            query.setString("sampleId", sampleId);
            if ((null != query.list()) && (query.list().size() > 0)) {
                isRecordExists = true;
                LOGGER.info("Item already exists.");
            }
        } catch (Exception exception) {
            LOGGER.error("Error occured while checking cart items exists.");
            exception.getMessage();
        } finally {
            session.close();
        }
        return isRecordExists;

    }

    @Override
    public List<JobsCart> fetchCartItems(int userId) {
        LOGGER.info("Fetching cart items ....");
        Session session = sessionFactory.openSession();
        List<JobsCart> cartItemList = null;
        try {
            cartItemList = session.createQuery("SELECT j FROM JobsCart j WHERE j.userId.id= :userId").setInteger("userId", userId).list();
            if ((null != cartItemList) && (cartItemList.size() > 0)) {
                LOGGER.info("cartItemList size: " + cartItemList.size());
            } else {
                LOGGER.info("Cart is empty!");
            }
        } catch (Exception exception) {
            LOGGER.error("Error occured while fetching cart items");
            exception.getMessage();
        } finally {
            session.close();
        }
        return cartItemList;
    }

}
