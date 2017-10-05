/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.pgdb.service;

import com.nus.pgdb.entity.Organisms;
import com.nus.pgdb.entity.ProjectSamples;
import com.nus.pgdb.entity.Samples;
import java.util.List;

/**
 *
 * @author Dineli
 */
public interface ISampleProject {

    List<ProjectSamples> getSamplesbyProject(int projectId);

    Samples getSampleObjByIdService(String sampleId);

    List<Organisms> fetchAllOrganismService();

    String persistSampleDataService(Samples sampleObj);

    boolean persistSampleProjectService(ProjectSamples sampleProjectObj);

    Organisms getOrganismObjByIdService(int organismId, String organismName);
    
    String getLastSampleRecordService();
    
    boolean updateSampleService(String recordId, String sampleName, Organisms SampleOrganism);
    
    boolean deleteSampleService(String recordId);
    
    boolean isSmapleNameExistsService(int userId, String sampleName);
    
    Samples getSampleIdByNameService(String sampleName);
    
    Samples getSampleService(int userId, String sampleName);

}
