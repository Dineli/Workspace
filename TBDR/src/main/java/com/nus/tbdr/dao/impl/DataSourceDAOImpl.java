/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.tbdr.dao.impl;

import com.nus.tbdr.entity.persistence.HibernateUtil;
import com.nus.tbdr.dao.IDataSourceDAO;
import com.nus.tbdr.entity.DataSources;
import com.nus.tbdr.exception.TBDRException;
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
public class DataSourceDAOImpl implements IDataSourceDAO {

    private SessionFactory sessionFactory = null;
    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceDAOImpl.class);

    public DataSourceDAOImpl() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public int save(DataSources dataSources) {
        LOGGER.info("Saving datasource to database");
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        int dataSourceId = 0;
        try {
            transaction = session.beginTransaction();
            dataSourceId = (Integer) session.save(dataSources);
            if (dataSourceId > 0) {
                LOGGER.info("Record successfully saved in datasource table" + dataSourceId);
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error("Error occured while saving datasource to database " + e.getMessage());
        } finally {
            session.close();
        }
        return dataSourceId;
    }

    @Override
    public List<DataSources> fetchAllDataSources() {
        LOGGER.info("Fetching all datasources");
        Session session = sessionFactory.openSession();
        List<DataSources> dataSourceList = null;
        try {
            dataSourceList = session.getNamedQuery("DataSources.findAll").list();
            if (dataSourceList.size() > 0) {
                LOGGER.info("Datasources list size: " + dataSourceList.size());
            }
        } catch (Exception exception) {
            LOGGER.error("Error occured while fetching datasources");
            exception.getStackTrace();
        } finally {
            session.close();
        }
        return dataSourceList;
    }

    @Override
    public boolean isExist(String dsName) {
        LOGGER.info("Checking if the data source already exists");
        Session session = sessionFactory.openSession();
        boolean isDrugNameExist = false;
        try {
            Query query = session.getNamedQuery("DataSources.findByName").setString("name", dsName);
            if (!query.list().isEmpty()) {
                isDrugNameExist = true;
                LOGGER.info("Data sources already exists.");
            }
        } catch (Exception exception) {
            LOGGER.error("Error occured while retirieving data sources");
            exception.getStackTrace();
        } finally {
            session.close();
        }
        return isDrugNameExist;
    }

    @Override
    public boolean update(DataSources dataSourceObj) {
        LOGGER.info("Updating data source for dsId: " + dataSourceObj.getId());
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        boolean status = false;
        try {
            transaction = session.beginTransaction();
            session.merge(dataSourceObj);
            transaction.commit();
            status = true;
            LOGGER.info("Data source updated successfully");
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error("Error occured while updating data source to database " + e.getMessage());
        } finally {
            session.close();
        }
        return status;
    }

    @Override
    public int delete(int recordId) {
        LOGGER.info("Deleting datasource for dsId : " + recordId);
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        int result = 0;
        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("DELETE FROM DataSources ds WHERE ds.id= :dataSourceId");
            query.setParameter("dataSourceId", recordId);
            result = query.executeUpdate();
            transaction.commit();
            if (result > 0) {
                LOGGER.info("Record successfully deleted");
            }
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error("Error occured while deleting datasource data " + e.getMessage());
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public DataSources fetchDSbyId(int datasourceId) throws TBDRException {
        LOGGER.info("Fetching data source object by Id");
        Session session = sessionFactory.openSession();
        DataSources dataSourceObj = new DataSources();
        try {
            Query query = session.getNamedQuery("DataSources.findById").setInteger("id", datasourceId);
            if (!query.list().isEmpty()) {
                dataSourceObj = (DataSources) query.list().get(0);
                LOGGER.info("Data sources obj retrieved successfully");
            }
        } catch (Exception exception) {
            LOGGER.error("Error occured while fetching data source obj by Id");
            exception.getStackTrace();
        } finally {
            session.close();
        }
        return dataSourceObj;
    }

}
