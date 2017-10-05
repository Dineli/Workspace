/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.pgdb.dao.impl;

import com.nus.pgdb.dao.ISampleProjectDAO;
import com.nus.pgdb.entity.Organisms;
import com.nus.pgdb.entity.ProjectSamples;
import com.nus.pgdb.entity.Samples;
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
public class SampleProjectDAOImpl implements ISampleProjectDAO {

    private SessionFactory sessionFactory = null;
    private static final Logger LOGGER = LoggerFactory.getLogger(SampleProjectDAOImpl.class);

    public SampleProjectDAOImpl() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public List<ProjectSamples> getSamplesbyProject(int projectId) {
        LOGGER.info("Fetching samples for project id: " + projectId);
        Session session = sessionFactory.openSession();
        List<ProjectSamples> prjSampleList = null;
        try {
            Query query = session.createQuery("SELECT ps FROM ProjectSamples ps, Samples s WHERE s.id = ps.sampleId.id AND ps.projectId.id= :projectId").setInteger("projectId", projectId);
            if (!query.list().isEmpty()) {
                prjSampleList = query.list();
                LOGGER.info("Projectsample list size: " + prjSampleList.size());
            }
        } catch (Exception exception) {
            LOGGER.error("Error occured while fetching samples for project id: " + projectId + "-" + exception.getMessage());
            exception.getMessage();
        } finally {
            session.close();
        }
        return prjSampleList;
    }

    @Override
    public Samples getSampleObjById(String sampleId) {
        LOGGER.info("Fetch sampleObj for sampleId: " + sampleId);
        Session session = sessionFactory.openSession();
        Samples sampleObj = null;
        try {
            Query query = session.getNamedQuery("Samples.findById").setString("id", sampleId);
            if (!query.list().isEmpty()) {
                sampleObj = (Samples) query.list().get(0);
                LOGGER.info("Sample obj sccessfully fetched for sampleId: " + sampleId);
            }
        } catch (Exception exception) {
            LOGGER.error("Error occured while fetching sampleObj");
            exception.getMessage();
        } finally {
            session.close();
        }
        return sampleObj;
    }

    @Override
    public List<Organisms> fetchAllOrganisms() {
        LOGGER.info("Fetch all organisms");
        Session session = sessionFactory.openSession();
        List<Organisms> orgList = null;
        try {
            orgList = session.getNamedQuery("Organisms.findAll").list();
            if (orgList.size() > 0) {
                LOGGER.info("organismsList: " + orgList.size());
            }
        } catch (Exception exception) {
            LOGGER.error("Error occured while fetching organisms");
            exception.getMessage();
        } finally {
            session.close();
        }
        return orgList;
    }

    @Override
    public String persistSampleData(Samples sampleObj) {
        LOGGER.info("Saving sample data to database....");
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        String sampleId = null;
        try {
            transaction = session.beginTransaction();
            sampleId = (String) session.save(sampleObj);
            transaction.commit();
            if (sampleId != null) {
                sampleId = sampleObj.getId();
                LOGGER.info("Data successfully saved ");
            }
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error("Error occured while saving sample data to database " + e.getMessage());
        } finally {
            session.close();
        }
        return sampleId;
    }

    @Override
    public Organisms getOrganismObjById(int organismId, String organismName) {
        LOGGER.info("Fetch organismObj for organismId: " + organismId);
        Session session = sessionFactory.openSession();
        Organisms OrganismObj = null;
        Query query = null;
        try {
            if (organismId > 0) {
                query = session.getNamedQuery("Organisms.findById").setInteger("id", organismId);
            } else if (!organismName.isEmpty()) {
                query = session.getNamedQuery("Organisms.findByName").setString("name", organismName);
            }
            if (!query.list().isEmpty()) {
                OrganismObj = (Organisms) query.list().get(0);
                LOGGER.info("Organisms obj successfully fetched for organismId: " + organismId);
            }
        } catch (Exception exception) {
            LOGGER.error("Error occured while fetching OrganismObj");
            exception.getMessage();
        } finally {
            session.close();
        }
        return OrganismObj;
    }

    @Override
    public boolean persistSampleProject(ProjectSamples sampleProjectObj) {
        LOGGER.info("Saving sample-project data to database....");
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Integer sampleProjectId = null;
        boolean status = false;
        try {
            transaction = session.beginTransaction();
            sampleProjectId = (Integer) session.save(sampleProjectObj);
            transaction.commit();
            if (sampleProjectId > 0) {
                LOGGER.info("sample-project data successfully saved ");
                status = true;
            }
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error("Error occured while saving sample-project data to database ");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return status;
    }

    @Override
    public String getLastSampleRecord() {
        LOGGER.info("Fetch last record from samples table");
        Session session = sessionFactory.openSession();
        String sampleID = null;
        try {
            Query query = session.createQuery("SELECT s.id FROM Samples s ORDER BY s.id DESC");
            if (query != null) {
                sampleID = (String) query.setMaxResults(1).list().get(0);
                LOGGER.info("last sample recordId: " + sampleID);
            }
        } catch (Exception exception) {
            LOGGER.error("Error occured while fetching last record from samples table");
            exception.getMessage();
        } finally {
            session.close();
        }
        return sampleID;
    }

    @Override
    public boolean updateSample(String recordId, String sampleName, Organisms SampleOrganism) {
        LOGGER.info("Updating sample data for recordId: " + recordId);
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        boolean status = false;
        try {
            transaction = session.beginTransaction();
            Samples sampleObj = (Samples) session.get(Samples.class, recordId);
            sampleObj.setName(sampleName);
            sampleObj.setOrganismId(SampleOrganism);
            session.update(sampleObj);
            transaction.commit();
            status = true;
            LOGGER.info("Sample updated successfully");
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error("Error occured while updating sample " + e.getMessage());
        } finally {
            session.close();
        }
        return status;
    }

    @Override
    public boolean deleteSample(String recordId) {
        LOGGER.info("Deleting sample for id: " + recordId + " ....");
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        boolean status = false;
        try {
            transaction = session.beginTransaction();
            Serializable id = recordId;
            Object persistentInstance = session.load(Samples.class, id);
            if (persistentInstance != null) {
                session.delete(persistentInstance);
                LOGGER.info("Sample successfully deleted");
                status = true;
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error("Error occured while deleting sample " + e.getMessage());
            e.getMessage();
        } finally {
            session.close();
        }
        return status;
    }

    @Override
    public boolean isSmapleNameExists(int userId, String sampleName) {
        LOGGER.info("Checking if the sample name exists for userId:" + userId + " ...");
        Session session = sessionFactory.openSession();
        boolean isRecordExists = false;
        try {
            Query query = session.createQuery("SELECT s FROM UserProjects up, ProjectSamples ps, Samples s WHERE up.projectId.id = ps.projectId.id AND ps.sampleId.id=s.id AND up.userId.id = :userId AND s.name=:sampleName").setInteger("userId", userId).setString("sampleName", sampleName);
            if ((null != query.list()) && (query.list().size() > 0)) {
                isRecordExists = true;
                LOGGER.info("Sample name already exists.");
            }
        } catch (Exception exception) {
            LOGGER.error("Error occured while checking sample name exists for userId:" + userId + " ...");
            exception.getMessage();
        } finally {
            session.close();
        }
        return isRecordExists;
    }

    @Override
    public Samples getSampleIdByName(String sampleName) {
        LOGGER.info("Fetch sampleId for sampleName: " + sampleName);
        Session session = sessionFactory.openSession();
        Samples sampleObj = null;
        try {
            Query query = session.getNamedQuery("Samples.findByName").setString("name", sampleName);
            if (!query.list().isEmpty()) {
                sampleObj = (Samples) query.list().get(0);
                LOGGER.info("Sample obj sccessfully fetched for sampleName: " + sampleName);
            }
        } catch (Exception exception) {
            LOGGER.error("Error occured while fetching sampleObj for sampleName");
            exception.getMessage();
        } finally {
            session.close();
        }
        return sampleObj;
    }

    @Override
    public Samples getSample(int userId, String sampleName) {
        LOGGER.info("Fetch sampleId for sampleName and userId ");
        Session session = sessionFactory.openSession();
        Samples sampleObj = null;
        try {
            Query query = session.createQuery("SELECT s FROM Samples s, Users u, ProjectSamples ps, UserProjects up WHERE up.userId.id = u.id AND ps.sampleId.id = s.id AND ps.projectId.id = up.projectId.id AND s.name = :sampleName AND u.id = :userId").setInteger("userId", userId).setString("sampleName", sampleName);
            if (!query.list().isEmpty()) {
                sampleObj = (Samples) query.list().get(0);
                LOGGER.info("Sample obj sccessfully fetched");
            }
        } catch (Exception exception) {
            LOGGER.error("Error occured while fetching sampleObj for sampleName and userId");
            exception.getMessage();
        } finally {
            session.close();
        }
        return sampleObj;
    }

}
