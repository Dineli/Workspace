/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.mtc.dao.impl;

import com.nus.mtc.dao.ILocationDAO;
import com.nus.mtc.entity.Countrys;
import com.nus.mtc.entity.Locations;
import com.nus.mtc.entity.persistence.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Dineli
 */
public class LocationDAOImpl implements ILocationDAO {

    private SessionFactory sessionFactory = null;
    private static final Logger logger = LoggerFactory.getLogger(SampleDAOImpl.class);

    public LocationDAOImpl() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public List<Locations> fetchAllLocationData() {
        logger.info("Fetching all location data");
        Session session = sessionFactory.openSession();
        StringBuilder sb = new StringBuilder();
        List<Locations> countryList = null;

        sb.append("SELECT l FROM Locations l JOIN l.countryId c ORDER BY c.name ASC ");
        try {
            countryList = session.createQuery(sb.toString()).list();
            logger.info("Country List size " + countryList.size());
        } catch (Exception exception) {
            logger.error("Error occured while retirieving all location data");
            logger.error(exception.getMessage());
        } finally {
            session.close();
        }
        return countryList;
    }

    @Override
    public Locations fetchLocationDataById(int locationId) {
        logger.info("Fetching all location data by ID");
        Session session = sessionFactory.openSession();
        Query query = null;
        Locations location = null;
        try {
            query = session.getNamedQuery("Locations.findById").setInteger("id", locationId);
            location = (Locations) query.uniqueResult();
            logger.info("Country: " + location.getCountryId().getName());
        } catch (Exception exception) {
            logger.error("Error occured while retirieving all location data by ID");
            logger.error(exception.getMessage());
        } finally {
            session.close();
        }
        return location;
    }

    @Override
    public List<Countrys> fetchAllCountrys() {
        logger.info("Fetching all countries");
        Session session = sessionFactory.openSession();
        List<Countrys> countryList = null;
        try {
            countryList = session.getNamedQuery("Countrys.findAll").list();
            logger.info("All country list size " + countryList.size());
        } catch (Exception exception) {
            logger.error("Error occured while retirieving all countries");
            logger.error(exception.getMessage());
        } finally {
            session.close();
        }
        return countryList;
    }

    @Override
    public List<Locations> fetchLocationDataByCountryId(int countryId) {
        logger.info("Fetching country data by ID");
        Session session = sessionFactory.openSession();
        List<Locations> countryList = null;
        try {
            countryList = session.createQuery("SELECT l FROM Locations l JOIN l.countryId c WHERE c.id= :countryId").setInteger("countryId", countryId).list();
            logger.info("Country list size " + countryList.size());
        } catch (Exception exception) {
            logger.error("Error occured while retirieving country data by ID");
            logger.error(exception.getMessage());
        } finally {
            session.close();
        }
        return countryList;
    }

}
