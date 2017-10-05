/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.tbdr.dao.impl;

import com.nus.tbdr.dao.IDrugResistanceDAO;
import com.nus.tbdr.entity.DrugResistance;
import com.nus.tbdr.entity.persistence.HibernateUtil;
import com.nus.tbdr.exception.TBDRException;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Dineli
 */
public class DrugResistanceDAOImpl implements IDrugResistanceDAO {

    private SessionFactory sessionFactory = null;
    private static final Logger LOGGER = LoggerFactory.getLogger(DrugResistanceDAOImpl.class);

    public DrugResistanceDAOImpl() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public boolean save(List<DrugResistance> drugResList) throws TBDRException {
        LOGGER.info("Preparing to save data to DrugResistance");
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        boolean status = false;
        try {
            for (DrugResistance obj : drugResList) {
                session.save(obj);
                status = true;
            }
            transaction.commit();
            //if it's empty that means the records are already exists
            if (drugResList.isEmpty()) {
                status = true;
            }
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error("Error occured while saving DrugResistance data to database " + e.getMessage());
            e.getMessage();
        } finally {
            session.close();
        }
        return status;
    }

    @Override
    public List<DrugResistance> fetchAllData() throws TBDRException {
        LOGGER.info("Fetching all drug resistance records");
        Session session = sessionFactory.openSession();
        List<DrugResistance> drList = null;
        try {
            drList = session.getNamedQuery("DrugResistance.findAll").list();
            if (drList.size() > 0) {
                LOGGER.info("Drug resistance size: " + drList.size());
            }
        } catch (Exception exception) {
            LOGGER.error("Error occured while fetching drug resistance records");
            exception.getMessage();
        } finally {
            session.close();
        }
        return drList;
    }

    @Override
    public boolean isExist(int variantId, int drugId, int dataSourceId) throws TBDRException {
        LOGGER.info("Checking drugRes record already exists");
        Session session = sessionFactory.openSession();
        boolean isRecordExist = false;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT id FROM drug_resistance WHERE variant_id=:variantId AND drug_id=:drugId AND data_source_id=:dataSourceId ");
        try {
            Query query = session.createSQLQuery(sb.toString()).setInteger("variantId", variantId).setInteger("drugId", drugId).setInteger("dataSourceId", dataSourceId);
            if (!query.list().isEmpty()) {
                isRecordExist = true;
                LOGGER.info("drug resistance record already exists.");
            } else {
                LOGGER.info("unique drug resistance record.");
            }
        } catch (Exception exception) {
            LOGGER.error("Error occured while retirieving drugRes record");
            exception.getMessage();
        } finally {
            session.close();
        }
        return isRecordExist;
    }

    @Override
    public List<DrugResistance> fetchByVariantId(int variantId) throws TBDRException {
        LOGGER.info("Fetching dr data related to variant");
        Session session = sessionFactory.openSession();
        List<DrugResistance> drDataList = null;
        try {
            drDataList = session.getNamedQuery("DrugResistance.findByVariantId").setInteger("variantId", variantId).list();
            LOGGER.info("dr data list size: " + drDataList.size());
        } catch (Exception exception) {
            LOGGER.error("Error occured while retirieving dr data related to variant");
            exception.getMessage();
        } finally {
            session.close();
        }
        return drDataList;
    }

    @Override
    public void updateNullValuesIfExists() throws TBDRException {
        LOGGER.info("Updating null values in highConfidence column if exists");
        Session session = sessionFactory.openSession();
        List<DrugResistance> checkList = null;
        Transaction transaction = null;
        StringBuilder sb = new StringBuilder();
        StringBuilder sb1 = new StringBuilder();
        sb.append("SELECT * FROM drug_resistance WHERE high_confidence IS NULL");
        try {
            transaction = session.beginTransaction();
            checkList = session.createSQLQuery(sb.toString()).list();
            if (checkList.size() > 0) {
                sb1.append("UPDATE drug_resistance SET high_confidence = 0 WHERE high_confidence IS NULL");
                SQLQuery sqlQuery = session.createSQLQuery(sb1.toString());
                sqlQuery.executeUpdate();
                transaction.commit();
                LOGGER.info("Null values updated successfully in drug_resistance(highConfidence)");
            } else {
                LOGGER.info("No values to update in drug_resistance(highConfidence)");
            }
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error("Error occured while updating null values in highConfidence");
        } finally {
            session.close();
        }
    }

}
