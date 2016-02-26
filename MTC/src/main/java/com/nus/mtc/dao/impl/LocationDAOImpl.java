/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.mtc.dao.impl;

import com.nus.mtc.dao.ILocationDAO;
import com.nus.mtc.entity.Locations;
import com.nus.mtc.entity.persistence.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author EPHAADK
 */
public class LocationDAOImpl implements ILocationDAO {

    private SessionFactory sessionFactory = null;

    public LocationDAOImpl() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public List<Locations> fetchAllLocationData() {
        Session session = sessionFactory.openSession();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT l FROM Locations l JOIN l.countryId c");
        List<Locations> countryList = session.createQuery(sb.toString()).list();
        session.close();
        System.out.println("---------------------- Found " + countryList.size() + " countryList");
        return countryList;
    }

    @Override
    public Locations fetchLocationDataById(int locationId) {
        Session session = sessionFactory.openSession();

        Query query = session.getNamedQuery("Locations.findById").setInteger("id", locationId);
        Locations location = (Locations) query.uniqueResult();
        session.close();
        System.out.println("location =" + location.getCountryId().getName() + ", City="
                + location.getCity());
        return location;
    }

}
