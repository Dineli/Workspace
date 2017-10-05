/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.pgdb.dao;

import com.nus.pgdb.entity.Projects;
import com.nus.pgdb.entity.SharedUserProjects;
import java.util.List;

/**
 *
 * @author Dineli
 */
public interface IProjectDAO {
    
    int persistProjectData(Projects projectObj);
    
    boolean updateProject(int recordId, String name, String description);
    
    boolean deleteProject(int recordId);
    
    Projects getProjectObjById(int projectId);
    
    List<Projects> fetchAllProjectsByUserId(int userId);
    
    //methods related to project sahring
    boolean persistProjectShare(SharedUserProjects sharedUserProjectObj);
    
    List<SharedUserProjects> fetchUsersWithSharedProjects(int sharedProjectId, int projectOwnerId);
    
    boolean removeSharedProject(int recordId);
    
    List<SharedUserProjects> fetchAllSharedProjectsByUserId(int loggedUserId);
    
    List<Object[]> fetchAllProjects4NonAdminUsers();
    
    boolean isProjectWithFiles(int userId, int projectId);
    
}
