/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.mtc.dao.impl;

import com.nus.mtc.dao.IDrugDAO;
import com.nus.mtc.entity.DrugResistanceData;
import com.nus.mtc.entity.persistence.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Dineli
 */
public class DrugDAOImpl implements IDrugDAO {

    private SessionFactory sessionFactory = null;
    private static final Logger logger = LoggerFactory.getLogger(DrugDAOImpl.class);

    public DrugDAOImpl() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public List<DrugResistanceData> fetchAllDrugsData(boolean showAllData, String drugName, String locusName) {
        logger.info("Fetching all drug resistance data");
        Session session = sessionFactory.openSession();
        List<DrugResistanceData> drugResistanceList = null;
        try {
            if (showAllData) {
                logger.info("Search all drugs");
                drugResistanceList = session.getNamedQuery("DrugResistanceData.findAll").list();
                logger.info("Drug list size: " + drugResistanceList.size());
            } else if (!"".equals(drugName)) {
                logger.info("Search by drug name");
                drugResistanceList = session.getNamedQuery("DrugResistanceData.findByDrugName").setString("drugAbbreviation", drugName).list();
                logger.info("Drug list size: " + drugResistanceList.size());
            } else if (!"".equals(locusName)) {
                logger.info("Search by locus name");
                drugResistanceList = session.getNamedQuery("DrugResistanceData.findByLocusName").setString("locusName", locusName).list();
                logger.info("Drug list size: " + drugResistanceList.size());
            }
        } catch (Exception exception) {
            logger.error("Error occured while retirieving all drug resistance data");
            exception.getStackTrace();
        } finally {
            session.close();
        }
        return drugResistanceList;
    }

    @Override
    public List<String> fetchAllDrugNames() {
        logger.info("Fetching all drug names");
        Session session = sessionFactory.openSession();
        List<String> drugList = null;
        try {
            drugList = session.getNamedQuery("Drugs.getAllDrugNames").list();
            logger.info("Drug name list size: " + drugList.size());
        } catch (Exception exception) {
            logger.error("Error occured while retirieving all drug names");
            exception.getStackTrace();
        } finally {
            session.close();
        }
        return drugList;
    }

    @Override
    public List<String> fetchAllLocusNames() {
        logger.info("Fetching all locus names");
        Session session = sessionFactory.openSession();
        List<String> locusList = null;
        try {
            locusList = session.getNamedQuery("TbGeneticLocus.getAllLocusNames").list();
            logger.info("Locus name list size: " + locusList.size());
        } catch (Exception exception) {
            logger.error("Error occured while retirieving all locus names");
            exception.getStackTrace();
        } finally {
            session.close();
        }
        return locusList;
    }

}
