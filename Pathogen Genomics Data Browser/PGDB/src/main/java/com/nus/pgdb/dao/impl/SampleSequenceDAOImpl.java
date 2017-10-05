/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.pgdb.dao.impl;

import com.nus.pgdb.dao.ISampleSequenceDAO;
import com.nus.pgdb.entity.SamplesSequenceFiles;
import com.nus.pgdb.entity.SequenceFiles;
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
public class SampleSequenceDAOImpl implements ISampleSequenceDAO {

    private SessionFactory sessionFactory = null;
    private static final Logger LOGGER = LoggerFactory.getLogger(SampleSequenceDAOImpl.class);

    public SampleSequenceDAOImpl() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public int persistSequenceData(SequenceFiles sequenceFileObj) {
        LOGGER.info("Saving fastq file data to database....");
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        int seqId = 0;
        try {
            transaction = session.beginTransaction();
            seqId = (Integer) session.save(sequenceFileObj);
            transaction.commit();
            if (seqId > 0) {
                LOGGER.info("Data successfully saved ");
            }
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error("Error occured while saving fastq file data to database " + e.getMessage());
        } finally {
            session.close();
        }
        return seqId;
    }

    @Override
    public boolean persistSampleSequenceData(SamplesSequenceFiles samplesSequenceObj) {
        LOGGER.info("Saving sample sequence data to database....");
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Integer sampleSeqId = null;
        boolean status = false;
        try {
            transaction = session.beginTransaction();
            sampleSeqId = (Integer) session.save(samplesSequenceObj);
            transaction.commit();
            if (sampleSeqId > 0) {
                LOGGER.info("Data successfully saved ");
                status = true;
            }
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error("Error occured while saving sample sequence data to database " + e.getMessage());
        } finally {
            session.close();
        }
        return status;
    }

    @Override
    public List<SamplesSequenceFiles> fetchSequenceFilesBySampleId(String sampleId, int userId, boolean sharedProject) {
        LOGGER.info("Fetching sample sequence data for sampleId " + sampleId + " ....");
        Session session = sessionFactory.openSession();
        List<SamplesSequenceFiles> sampleSeqList = null;
        Query query = null;
        //if it's a sharedProject userId is not involved in building the query
        try {
            if (sharedProject) {
                query = session.getNamedQuery("SamplesSequenceFiles.findBySampleId").setString("sampleId", sampleId);
            } else {
                query = session.createQuery("SELECT ssf FROM Users u, UserProjects up, ProjectSamples ps, SamplesSequenceFiles ssf, SequenceFiles sf WHERE u.id = up.userId.id AND ps.projectId.id = up.projectId.id AND ps.sampleId.id = ssf.sampleId.id AND ssf.sequenceFileId.id = sf.id AND u.id = :userId AND ssf.sampleId.id = :sampleId").setInteger("userId", userId).setString("sampleId", sampleId);
            }
            if (!query.list().isEmpty()) {
                sampleSeqList = query.list();
                LOGGER.info("sampleSeqList list size: " + sampleSeqList.size());
            }
        } catch (Exception exception) {
            LOGGER.error("Error occured while fetching sample sequence for sample id: " + sampleId);
            exception.getStackTrace();
        } finally {
            session.close();
        }
        return sampleSeqList;

    }

    @Override
    public boolean deleteSampleSequenceData(int recordId) {
        LOGGER.info("Deleting sample sequence data....");
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        boolean status = false;
        try {
            transaction = session.beginTransaction();
            Serializable id = recordId;
            Object persistentInstance = session.load(SequenceFiles.class, id);
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
            LOGGER.error("Error occured while deleting sample sequence data " + e.getMessage());
        } finally {
            session.close();
        }
        return status;
    }

//    public SamplesSequenceFiles getObject(int recordId) {
//        SamplesSequenceFiles sampleSeqObj = null;
//        Session session = sessionFactory.openSession();
//        try {
//            Query query = session.getNamedQuery("SamplesSequenceFiles.findById").setInteger("id", recordId);
//            if (!query.list().isEmpty()) {
//                sampleSeqObj = (SamplesSequenceFiles) query.list().get(0);
////                LOGGER.info("sampleSeqList list size: " + sampleSeqList.size());
//            }
//        } catch (Exception exception) {
//            LOGGER.error("Error occured while fetching sample sequence object for recordId" + recordId);
//            exception.getStackTrace();
//        } finally {
//            session.close();
//        }
//        return sampleSeqObj;
//    }
}
