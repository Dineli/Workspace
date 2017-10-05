/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.mtc.dao.impl;

import com.nus.mtc.dao.ISampleDAO;
import com.nus.mtc.entity.Accessions;
import com.nus.mtc.entity.Samples;
import com.nus.mtc.entity.Studys;
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
public class SampleDAOImpl implements ISampleDAO {

    private SessionFactory sessionFactory = null;
    private static final Logger logger = LoggerFactory.getLogger(SampleDAOImpl.class);

    public SampleDAOImpl() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public List<Samples> fetchUniqueSampleDataGroupByStudy() {
        logger.info("Fetching unique sample data group by study");
        Session session = sessionFactory.openSession();
        List<Samples> sampleList = null;
        StringBuilder sb = new StringBuilder();

        sb.append("SELECT s1");
        sb.append(" FROM Studys s, Samples s1");
        sb.append(" WHERE s.id = s1.studyId ");
        sb.append(" GROUP BY s.id  ");
        try {
            sampleList = session.createQuery(sb.toString()).list();
            logger.info("Sample list size " + sampleList.size());
        } catch (Exception exception) {
            logger.error("Error occured while retirieving sample data group by study");
            logger.error(exception.getMessage());
        } finally {
            session.close();
        }
        return sampleList;
    }

    @Override
    public List<Object[]> fetchSampleDataByStudyId(String studyId) {
        logger.info("Fetching sample data by study ID");
        Session session = sessionFactory.openSession();
        StringBuilder sb = new StringBuilder();
        List<Object[]> sampleList = null;

        sb.append("SELECT {s.*}, {s1.*}, {a.*} FROM studys s, samples s1 LEFT JOIN accessions a ON s1.id=a.sample_id");
        sb.append(" WHERE s.id=s1.study_id AND s.id=?");
        sb.append(" GROUP BY s1.id");
        sb.append(" ORDER BY a.id DESC");
        try {
            sampleList = session.createSQLQuery(sb.toString()).addEntity("s", Studys.class).addEntity("s1", Samples.class)
                    .addEntity("a", Accessions.class).setString(0, studyId).list();
            logger.info("Sample list size " + sampleList.size());
        } catch (Exception exception) {
            logger.error("Error occured while retirieving sample data by study ID");
            logger.error(exception.getMessage());
        } finally {
            session.close();
        }
        return sampleList;
    }

    @Override
    public List<Accessions> fetchAccessionDataBySampleId(String sampleId) {
        logger.info("Fetching accession data by sample ID");
        Session session = sessionFactory.openSession();
        Query query = null;
        List<Accessions> accessionList = null;
        try {
            query = session.getNamedQuery("Accessions.findBySampleId").setString("sampleId", sampleId);
            accessionList = query.list();
            logger.info("Accession list size " + accessionList.size());
        } catch (Exception exception) {
            logger.error("Error occured while retirieving accession data by sample ID");
            logger.error(exception.getMessage());
        } finally {
            session.close();
        }
        return accessionList;
    }

    @Override
    public List<Object[]> fetchSampleDataByLocationId(int locationId) {
        logger.info("Fetching sample data by location ID");
        Session session = sessionFactory.openSession();
        StringBuilder sb = new StringBuilder();
        List<Object[]> sampleList = null;
        try {
            sb.append("SELECT {s.*}, {s1.*}, {a.*} FROM studys s, samples s1 LEFT JOIN accessions a ON s1.id=a.sample_id");
            sb.append(" WHERE s.id=s1.study_id AND s1.location_id=?");
            sb.append(" GROUP BY s1.id");
            sb.append(" ORDER BY a.id DESC");
            sampleList = session.createSQLQuery(sb.toString()).addEntity("s", Studys.class).addEntity("s1", Samples.class)
                    .addEntity("a", Accessions.class).setInteger(0, locationId).list();
            logger.info("Sample list size " + sampleList.size());
        } catch (Exception exception) {
            logger.error("Error occured while retirieving accession data by location ID");
            logger.error(exception.getMessage());
        } finally {
            session.close();
        }
        return sampleList;
    }

}
