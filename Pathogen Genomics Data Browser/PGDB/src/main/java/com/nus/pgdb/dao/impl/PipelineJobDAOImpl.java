/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.pgdb.dao.impl;

import com.nus.pgdb.dao.IPipelineJobDAO;
import com.nus.pgdb.entity.PipelineJobs;
import com.nus.pgdb.entity.PipelineJobsHistory;
import com.nus.pgdb.entity.persistence.HibernateUtil;
import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Dineli
 */
public class PipelineJobDAOImpl implements IPipelineJobDAO {

    private SessionFactory sessionFactory = null;
    private static final Logger LOGGER = LoggerFactory.getLogger(PipelineJobDAOImpl.class);

    public PipelineJobDAOImpl() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public boolean persistJobDetails(PipelineJobs jobDetails) {
        LOGGER.info("Saving job data to database....");
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Integer jobId = null;
        boolean status = false;
        try {
            transaction = session.beginTransaction();
            jobId = (Integer) session.save(jobDetails);
            transaction.commit();
            if (jobId > 0) {
                LOGGER.info("Job data successfully saved ");
                status = true;
            }
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error("Error occured while saving job data to database " + e.getMessage());
        } finally {
            session.close();
        }
        return status;
    }

    @Override
    public List<PipelineJobs> fetchPipelineDataByUserId(int userId) {
        LOGGER.info("Fetching pipeline job data for userId : " + userId + " ....");
        Session session = sessionFactory.openSession();
        List<PipelineJobs> jobList = null;
        try {
            jobList = session.getNamedQuery("PipelineJobs.findAllByUserId").setInteger("userId", userId).list();
            if (jobList.size() > 0) {
                LOGGER.info("jobList size: " + jobList.size());
            }
        } catch (Exception exception) {
            LOGGER.error("Error occured while fetching pipeline job data");
            exception.getMessage();
        } finally {
            session.close();
        }
        return jobList;
    }

    @Override
    public List<PipelineJobs> fetchCurrentlyRunningJobs(int userId) {
        LOGGER.info("Fetching currently running jobs for userId : " + userId + " ....");
        Session session = sessionFactory.openSession();
        List<PipelineJobs> activeJobList = null;
        try {
            activeJobList = session.getNamedQuery("PipelineJobs.findActiveJobsByUserId").setInteger("userId", userId).list();
            if (activeJobList.size() > 0) {
                LOGGER.info("activeJobList size: " + activeJobList.size());
            } else {
                LOGGER.info("Currently no jobs are active");
            }
        } catch (Exception exception) {
            LOGGER.error("Error occured while fetching currently running jobs");
            exception.getMessage();
        } finally {
            session.close();
        }
        return activeJobList;
    }

    @Override
    public void updateJobStatus(int jobId, String status) {
        LOGGER.info("Updating job status for jobId " + jobId + " ....");
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            PipelineJobs job = (PipelineJobs) session.get(PipelineJobs.class, jobId);
            job.setStatus(status);
            session.update(job);
            transaction.commit();
            LOGGER.info("Job status updated successfully");
        } catch (HibernateException exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error("Error occured while updating job status to database");
            exception.getMessage();
        } finally {
            session.close();
        }
    }

    @Override
    public boolean deleteJob(int recordId) {
        LOGGER.info("Deleting job for recordId " + recordId + " ....");
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        boolean status = false;
        try {
            transaction = session.beginTransaction();
            Serializable id = recordId;
            Object persistentInstance = session.load(PipelineJobs.class, id);
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
            LOGGER.error("Error occured while deleting job " + e.getMessage());
        } finally {
            session.close();
        }
        return status;
    }

    @Override
    public boolean persistJobHistory(PipelineJobsHistory pipelineJobsHistory) {
        LOGGER.info("Saving job history data to database....");
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Integer jobHistoryId = null;
        boolean status = false;
        try {
            transaction = session.beginTransaction();
            jobHistoryId = (Integer) session.save(pipelineJobsHistory);
            transaction.commit();
            if (jobHistoryId > 0) {
                LOGGER.info("Job history data successfully saved ");
                status = true;
            }
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error("Error occured while saving job history data to database " + e.getMessage());
        } finally {
            session.close();
        }
        return status;
    }

}
