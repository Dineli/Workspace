/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.pgdb.dao;

import com.nus.pgdb.entity.Organisms;
import com.nus.pgdb.entity.ProjectSamples;
import com.nus.pgdb.entity.Samples;
import java.util.List;

/**
 *
 * @author Dineli
 */
public interface ISampleProjectDAO {

    List<ProjectSamples> getSamplesbyProject(int projectId);

    Samples getSampleObjById(String sampleId);

    List<Organisms> fetchAllOrganisms();

    String persistSampleData(Samples sampleObj);

    boolean persistSampleProject(ProjectSamples sampleProjectObj);

    Organisms getOrganismObjById(int organismId, String organismName);

    String getLastSampleRecord();

    boolean updateSample(String recordId, String sampleName, Organisms SampleOrganism);

    boolean deleteSample(String recordId);

    boolean isSmapleNameExists(int userId, String sampleName);
    
    Samples getSampleIdByName(String sampleName);
    
    Samples getSample(int userId, String sampleName);

}
