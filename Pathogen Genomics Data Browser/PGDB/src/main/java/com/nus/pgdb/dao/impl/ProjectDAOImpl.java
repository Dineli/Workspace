/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.pgdb.dao.impl;

import com.nus.pgdb.dao.IProjectDAO;
import com.nus.pgdb.entity.Projects;
import com.nus.pgdb.entity.SharedUserProjects;
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
public class ProjectDAOImpl implements IProjectDAO {

    private SessionFactory sessionFactory = null;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectDAOImpl.class);

    public ProjectDAOImpl() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public int persistProjectData(Projects projectObj) {
        LOGGER.info("Saving project data to database....");
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Integer prjId = null;
        try {
            transaction = session.beginTransaction();
            prjId = (Integer) session.save(projectObj);
            transaction.commit();
            session.flush();
            if (prjId > 0) {
                LOGGER.info("Project data successfully saved ");
            }
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error("Error occured while saving project data to database " + e.getMessage());
        } finally {

            session.close();
        }
        return prjId;
    }

    @Override
    public Projects getProjectObjById(int projectId) {
        LOGGER.info("Fetch projectObj for projectId: " + projectId);
        Session session = sessionFactory.openSession();
        Projects projectObj = null;
        try {
            Query query = session.getNamedQuery("Projects.findById").setInteger("id", projectId);
            if (!query.list().isEmpty()) {
                projectObj = (Projects) query.list().get(0);
                LOGGER.info("Project obj successfully fetched for projectId: " + projectId);
            }
        } catch (Exception exception) {
            LOGGER.error("Error occured while fetching projectId");
            exception.getMessage();
        } finally {
            session.flush();
            session.close();
        }
        return projectObj;
    }

    @Override
    public List<Projects> fetchAllProjectsByUserId(int userId) {
        LOGGER.info("Fetching all projects for userId: " + userId + " .....");
        Session session = sessionFactory.openSession();
        List<Projects> prjList = null;
        try {
            prjList = session.createQuery("SELECT p FROM Projects p, UserProjects up WHERE p.id = up.projectId.id AND up.userId.id= :userID").setInteger("userID", userId).list();
            if (prjList.size() > 0) {
                LOGGER.info("projectList size: " + prjList.size());
            }
        } catch (Exception exception) {
            LOGGER.error("Error occured while fetching projects for userId" + userId);
            exception.getMessage();
        } finally {
            session.flush();
            session.close();
        }
        return prjList;
    }

    @Override
    public boolean updateProject(int recordId, String name, String description) {
        LOGGER.info("Updating project data for recordId: " + recordId);
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        boolean status = false;
        try {
            transaction = session.beginTransaction();
            Projects prjObj = (Projects) session.get(Projects.class, recordId);
            prjObj.setName(name);
            prjObj.setDescription(description);
            session.update(prjObj);
            transaction.commit();
            session.flush();
            status = true;
            LOGGER.info("Project updated successfully");
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error("Error occured while updating project " + e.getMessage());
        } finally {
            session.close();
        }
        return status;
    }

    @Override
    public boolean deleteProject(int recordId) {
        LOGGER.info("Deleting project for projectId: " + recordId + " ....");
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        boolean status = false;
        try {
            transaction = session.beginTransaction();
            Serializable id = recordId;
            Object persistentInstance = session.load(Projects.class, id);
            if (persistentInstance != null) {
                session.delete(persistentInstance);
                LOGGER.info("Project successfully deleted");
                status = true;
            }
            transaction.commit();
            session.flush();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error("Error occured while deleting project " + e.getMessage());
            e.getMessage();
        } finally {
            session.close();
        }
        return status;
    }

    @Override
    public boolean persistProjectShare(SharedUserProjects sharedUserProjectObj) {
        LOGGER.info("Saving project sharing data to database....");
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Integer prjShareId = null;
        boolean status = false;
        try {
            transaction = session.beginTransaction();
            prjShareId = (Integer) session.save(sharedUserProjectObj);
            transaction.commit();
            if (prjShareId > 0) {
                LOGGER.info("Project sharing data successfully saved ");
                status = true;
            }
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error("Error occured while saving project sharing data to database " + e.getMessage());
        } finally {
            session.close();
        }
        return status;
    }

    @Override
    public List<SharedUserProjects> fetchUsersWithSharedProjects(int sharedProjectId, int projectOwnerId) {
        LOGGER.info("Fetch all users with shared projects");
        Session session = sessionFactory.openSession();
        List<SharedUserProjects> sharedUserList = null;
        try {
            Query query = session.createQuery("SELECT sup FROM SharedUserProjects sup WHERE sup.sharedProjectId.id = :projectId AND sup.projectOwnerId.id = :ownerId").setInteger("projectId", sharedProjectId).setInteger("ownerId", projectOwnerId);
            if (!query.list().isEmpty()) {
                sharedUserList = query.list();
                LOGGER.info("sharedUserList size: " + sharedUserList.size());
            }
        } catch (Exception exception) {
            LOGGER.error("Error occured while fetching all users with shared projects");
            exception.getMessage();
        } finally {
            session.close();
        }
        return sharedUserList;
    }

    @Override
    public boolean removeSharedProject(int recordId) {
        LOGGER.info("Deleting shared project for recordId: " + recordId + " ...");
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        boolean status = false;
        try {
            transaction = session.beginTransaction();
            Serializable id = recordId;
            Object persistentInstance = session.load(SharedUserProjects.class, id);
            if (persistentInstance != null) {
                session.delete(persistentInstance);
                LOGGER.info("Project sharing record successfully deleted");
                status = true;
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error("Error occured while deleting shared project " + e.getMessage());
            e.getMessage();
        } finally {
            session.close();
        }
        return status;
    }

    @Override
    public List<SharedUserProjects> fetchAllSharedProjectsByUserId(int loggedUserId) {
        LOGGER.info("Fetching all shared projects for userId: " + loggedUserId + " .....");
        Session session = sessionFactory.openSession();
        List<SharedUserProjects> sharedPrjList = null;
        try {
            sharedPrjList = session.createQuery("SELECT sup FROM SharedUserProjects sup WHERE sup.sharedUserId.id= :userID").setInteger("userID", loggedUserId).list();
            if (sharedPrjList.size() > 0) {
                LOGGER.info("sharedPrjList size: " + sharedPrjList.size());
            }
        } catch (Exception exception) {
            LOGGER.error("Error occured while fetching shared projects for userId" + loggedUserId);
            exception.getMessage();
        } finally {
            session.close();
        }
        return sharedPrjList;
    }

    @Override
    public List<Object[]> fetchAllProjects4NonAdminUsers() {
        LOGGER.info("Fetching all projects for non admin users ....");
        Session session = sessionFactory.openSession();
        List<Object[]> allProjectsList = null;
        StringBuilder queryString = new StringBuilder();
        try {
            queryString.append("SELECT u.name AS user_name, IFNULL(p.name, '') AS project_name, IFNULL(s.name, '') AS smaple_name, IFNULL(GROUP_CONCAT(file_path),'') ");
            queryString.append(" FROM users u LEFT JOIN user_projects up ON up.user_id = u.id ");
            queryString.append(" LEFT JOIN projects p ON p.id = up.project_id  ");
            queryString.append(" LEFT JOIN project_samples ps ON p.id = ps.project_id  ");
            queryString.append(" LEFT JOIN samples s ON s.id = ps.sample_id  ");
            queryString.append(" LEFT JOIN samples_sequence_files ssf ON s.id = ssf.sample_id  ");
            queryString.append(" LEFT JOIN sequence_files sf ON sf.id = ssf.sequence_file_id  ");
            queryString.append(" WHERE is_admin = 0  ");
            queryString.append(" GROUP BY u.id , s.id , p.id  ");
            queryString.append(" ORDER BY u.name , s.name  ");
            allProjectsList = session.createSQLQuery(queryString.toString()).list();
            if (allProjectsList.size() > 0) {
                LOGGER.info("allProjectsList size: " + allProjectsList.size());
            }
        } catch (Exception exception) {
            LOGGER.error("Error occured while fetching all projects for non admin users");
            exception.getMessage();
        } finally {
            session.close();
        }
        return allProjectsList;
    }

    @Override
    public boolean isProjectWithFiles(int userId, int projectId) {
        LOGGER.info("Checking if the project has sequence files for projectId: " + projectId + " ....");
        Session session = sessionFactory.openSession();
        boolean isRecordExists = false;
        StringBuilder queryString = new StringBuilder();
        queryString.append("SELECT ss.id FROM  users u, user_projects up, projects p, project_samples ps, samples_sequence_files ss ");
        queryString.append(" WHERE u.id = up.user_id AND p.id = up.project_id AND p.id = ps.project_id AND ps.sample_id = ss.sample_id");
        queryString.append(" AND u.id = :userId AND up.project_id = :projectId");
        try {
            Query query = session.createSQLQuery(queryString.toString()).setParameter("userId", userId).setParameter("projectId", projectId);
            if ((null != query.list()) && (query.list().size() > 0)) {
                isRecordExists = true;
                LOGGER.info("Project is with sequence files.");
            }
        } catch (Exception exception) {
            LOGGER.error("Error occured while checking project with sequence files:" + projectId + " ...");
            exception.getMessage();
        } finally {
            session.close();
        }
        return isRecordExists;
    }

}
