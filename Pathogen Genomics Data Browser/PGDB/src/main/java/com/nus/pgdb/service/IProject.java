/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.pgdb.service;

import com.nus.pgdb.entity.Projects;
import com.nus.pgdb.entity.SharedUserProjects;
import java.util.List;

/**
 *
 * @author Dineli
 */
public interface IProject {

    int persistProjectDataService(Projects projectObj);

    boolean updateProjectService(int recordId, String name, String description);

    boolean deleteProjectService(int recordId);

    Projects getProjectObjByIdService(int projectId);

    List<Projects> fetchAllProjectsByUserIdService(int userId);

    boolean persistProjectShareService(SharedUserProjects sharedUserProjectObj);

    List<SharedUserProjects> fetchUsersWithSharedProjectsService(int sharedProjectId, int projectOwnerId);

    boolean removeSharedProjectService(int recordId);

    List<SharedUserProjects> fetchAllSharedProjectsByUserIdService(int loggedUserId);

    List<Object[]> fetchAllProjects4NonAdminUsersService();
    
    boolean isProjectWithFilesService(int userId, int projectId);
}
