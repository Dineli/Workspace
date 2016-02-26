/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.mtc.dao.impl;

import com.nus.mtc.dao.ISampleDAO;
import com.nus.mtc.entity.Samples;
import com.nus.mtc.entity.persistence.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author EPHAADK
 */
public class SampleDAOImpl implements ISampleDAO {

    private SessionFactory sessionFactory = null;

    public SampleDAOImpl() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public List<Samples> fetchUniqueSampleDataGroupByStudy() {
        Session session = sessionFactory.openSession();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s1");
        sb.append(" FROM Studys s, Samples s1");
        sb.append(" WHERE s.id = s1.studyId ");
        sb.append(" GROUP BY s.id  ");
        List<Samples> sampleList = session.createQuery(sb.toString()).list();
        session.close();
        System.out.println("---------------------- Found " + sampleList.size() + " Studys");

        return sampleList;

    }

    @Override
    public List<Samples> fetchSampleDataByStudyId(String studyId) {
        Session session = sessionFactory.openSession();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s1");
        sb.append(" FROM Studys s,Samples s1");
        sb.append(" WHERE s.id = s1.studyId AND s.id=?");
        List<Samples> sampleList = session.createQuery(sb.toString()).setParameter(0, studyId).list();
        session.close();
        System.out.println("----------xxxx------------ Found " + sampleList.size() + " Samples");
        return sampleList;
    }

}
