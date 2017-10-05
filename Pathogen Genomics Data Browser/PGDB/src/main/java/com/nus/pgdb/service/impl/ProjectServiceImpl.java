/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.pgdb.service.impl;

import com.nus.pgdb.dao.IProjectDAO;
import com.nus.pgdb.dao.impl.ProjectDAOImpl;
import com.nus.pgdb.entity.Projects;
import com.nus.pgdb.entity.SharedUserProjects;
import com.nus.pgdb.service.IProject;
import java.util.List;

/**
 *
 * @author Dineli
 */
public class ProjectServiceImpl implements IProject {

    private final IProjectDAO ipdao = new ProjectDAOImpl();

    @Override
    public int persistProjectDataService(Projects projectObj) {
        return ipdao.persistProjectData(projectObj);
    }

    @Override
    public Projects getProjectObjByIdService(int projectId) {
        return ipdao.getProjectObjById(projectId);
    }

    @Override
    public List<Projects> fetchAllProjectsByUserIdService(int userId) {
        return ipdao.fetchAllProjectsByUserId(userId);
    }

    @Override
    public boolean updateProjectService(int recordId, String name, String description) {
        return ipdao.updateProject(recordId, name, description);
    }

    @Override
    public boolean deleteProjectService(int recordId) {
        return ipdao.deleteProject(recordId);
    }

    @Override
    public boolean persistProjectShareService(SharedUserProjects sharedUserProjectObj) {
        return ipdao.persistProjectShare(sharedUserProjectObj);
    }

    @Override
    public List<SharedUserProjects> fetchUsersWithSharedProjectsService(int sharedProjectId, int projectOwnerId) {
        return ipdao.fetchUsersWithSharedProjects(sharedProjectId, projectOwnerId);
    }

    @Override
    public boolean removeSharedProjectService(int recordId) {
        return ipdao.removeSharedProject(recordId);
    }

    @Override
    public List<SharedUserProjects> fetchAllSharedProjectsByUserIdService(int loggedUserId) {
        return ipdao.fetchAllSharedProjectsByUserId(loggedUserId);
    }

    @Override
    public List<Object[]> fetchAllProjects4NonAdminUsersService() {
        return ipdao.fetchAllProjects4NonAdminUsers();
    }

    @Override
    public boolean isProjectWithFilesService(int userId, int projectId) {
        return ipdao.isProjectWithFiles(userId, projectId);
    }

}
