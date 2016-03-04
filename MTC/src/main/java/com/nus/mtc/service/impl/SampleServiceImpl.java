/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.mtc.service.impl;

import com.nus.mtc.dao.ISampleDAO;
import com.nus.mtc.dao.impl.SampleDAOImpl;
import com.nus.mtc.entity.Accessions;
import com.nus.mtc.entity.Samples;
import com.nus.mtc.service.ISampleService;
import java.util.List;

/**
 *
 * @author Dineli
 */
public class SampleServiceImpl implements ISampleService {

    private ISampleDAO iSampleDAO = new SampleDAOImpl();

    @Override
    public List<Samples> getUniqueSampleDataGroupByStudy() {
        return iSampleDAO.fetchUniqueSampleDataGroupByStudy();
    }

    @Override
    public List<Object[]> getSampleDataByStudyId(String studyId) {
        return iSampleDAO.fetchSampleDataByStudyId(studyId);
    }

    @Override
    public List<Accessions> getAccessionDataBySampleId(String sampleId) {
        return iSampleDAO.fetchAccessionDataBySampleId(sampleId);
    }

    @Override
    public List<Object[]> getSampleDataByLocationId(int locationId) {
      return iSampleDAO.fetchSampleDataByLocationId(locationId);
    }

}
