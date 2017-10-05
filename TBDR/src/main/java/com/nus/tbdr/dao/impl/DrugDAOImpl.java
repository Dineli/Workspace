/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.tbdr.dao.impl;

import com.nus.tbdr.dao.IDrugDAO;
import com.nus.tbdr.entity.Drugs;
import com.nus.tbdr.entity.persistence.HibernateUtil;
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
public class DrugDAOImpl implements IDrugDAO {

    private SessionFactory sessionFactory = null;
    private static final Logger LOGGER = LoggerFactory.getLogger(DrugDAOImpl.class);

    public DrugDAOImpl() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public Integer save(Drugs drugObject) {
        LOGGER.info("Saving drug data to database");
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Integer drugId = null;
        try {
            transaction = session.beginTransaction();
            drugId = (Integer) session.save(drugObject);
            transaction.commit();
            if (drugId > 0) {
                LOGGER.info("Record successfully saved in drug table" + drugId);
            }
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error("Error occured while saving drug data to database " + e.getMessage());
        } finally {
            session.close();
        }
        return drugId;
    }

    @Override
    public boolean isExist(String drugName) {
        LOGGER.info("Checking the drug name already exists");
        Session session = sessionFactory.openSession();
        boolean isDrugNameExist = false;
        try {
            Query query = session.getNamedQuery("Drugs.findByName").setString("name", drugName);
            if (!query.list().isEmpty()) {
                isDrugNameExist = true;
                LOGGER.info("Drug name already exists.");
            }
        } catch (Exception exception) {
            LOGGER.error("Error occured while retirieving drug name");
            exception.getStackTrace();
        } finally {
            session.close();
        }
        return isDrugNameExist;
    }

    @Override
    public List<Drugs> fetchAllDrugs() {
        LOGGER.info("Fetching all drugs");
        Session session = sessionFactory.openSession();
        List<Drugs> drugList = null;
        try {
            drugList = session.getNamedQuery("Drugs.findAll").list();
            if (drugList.size() > 0) {
                LOGGER.info("Drug list size: " + drugList.size());
            }
        } catch (Exception exception) {
            LOGGER.error("Error occured while fetching drug names");
            exception.getStackTrace();
        } finally {
            session.close();
        }
        return drugList;
    }

    @Override
    public boolean update(int recordId, String drugName) {
        LOGGER.info("Updating drug name for drugId: " + recordId);
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        boolean status = false;
        try {
            transaction = session.beginTransaction();
            Drugs drugObj = (Drugs) session.get(Drugs.class, recordId);
            drugObj.setName(drugName);
            session.update(drugObj);
            transaction.commit();
            status = true;
            LOGGER.info("Drug name updated successfully");
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error("Error occured while updating drug name to database " + e.getMessage());
        } finally {
            session.close();
        }
        return status;
    }

    @Override
    public int delete(int recordId) {
        LOGGER.info("Deleting drug data for drugId : " + recordId);
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        int result = 0;
        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("DELETE FROM Drugs d WHERE d.id= :drugId");
            query.setParameter("drugId", recordId);
            result = query.executeUpdate();
            transaction.commit();
            if (result > 0) {
                LOGGER.info("Record successfully deleted");
            }
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error("Error occured while deleting drug data " + e.getMessage());
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public Drugs findByName(String drugName) {
        LOGGER.info("Fetching drug by name");
        Session session = sessionFactory.openSession();
        List<Drugs> drugList = null;
        Drugs drugObj = new Drugs();
        try {
            drugList = session.getNamedQuery("Drugs.findByName").setParameter("name", drugName).list();
            if (drugList.size() > 0) {
                drugObj = drugList.get(0);
            }
        } catch (Exception exception) {
            LOGGER.error("Error occured while fetching drug by name");
            exception.getStackTrace();
        } finally {
            session.close();
        }
        return drugObj;
    }
}
