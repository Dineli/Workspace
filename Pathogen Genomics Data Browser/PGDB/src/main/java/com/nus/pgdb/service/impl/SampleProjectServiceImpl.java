/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.pgdb.service.impl;

import com.nus.pgdb.dao.ISampleProjectDAO;
import com.nus.pgdb.dao.impl.SampleProjectDAOImpl;
import com.nus.pgdb.entity.Organisms;
import com.nus.pgdb.entity.ProjectSamples;
import com.nus.pgdb.entity.Samples;
import com.nus.pgdb.service.ISampleProject;
import java.util.List;

/**
 *
 * @author Dineli
 */
public class SampleProjectServiceImpl implements ISampleProject {

    private final ISampleProjectDAO iSampleProjectDAO = new SampleProjectDAOImpl();

    @Override
    public List<ProjectSamples> getSamplesbyProject(int projectId) {
        return iSampleProjectDAO.getSamplesbyProject(projectId);
    }

    @Override
    public Samples getSampleObjByIdService(String sampleId) {
        return iSampleProjectDAO.getSampleObjById(sampleId);
    }

    @Override
    public List<Organisms> fetchAllOrganismService() {
        return iSampleProjectDAO.fetchAllOrganisms();
    }

    @Override
    public String persistSampleDataService(Samples sampleObj) {
        return iSampleProjectDAO.persistSampleData(sampleObj);
    }

    @Override
    public Organisms getOrganismObjByIdService(int organismId, String organismName) {
        return iSampleProjectDAO.getOrganismObjById(organismId, organismName);
    }

    @Override
    public boolean persistSampleProjectService(ProjectSamples sampleProjectObj) {
        return iSampleProjectDAO.persistSampleProject(sampleProjectObj);
    }

    @Override
    public String getLastSampleRecordService() {
        return iSampleProjectDAO.getLastSampleRecord();
    }

    @Override
    public boolean updateSampleService(String recordId, String sampleName, Organisms SampleOrganism) {
        return iSampleProjectDAO.updateSample(recordId, sampleName, SampleOrganism);
    }

    @Override
    public boolean deleteSampleService(String recordId) {
        return iSampleProjectDAO.deleteSample(recordId);
    }

    @Override
    public boolean isSmapleNameExistsService(int userId, String sampleName) {
        return iSampleProjectDAO.isSmapleNameExists(userId, sampleName);
    }

    @Override
    public Samples getSampleIdByNameService(String sampleName) {
        return iSampleProjectDAO.getSampleIdByName(sampleName);
    }

    @Override
    public Samples getSampleService(int userId, String sampleName) {
        return iSampleProjectDAO.getSample(userId, sampleName);
    }

}
