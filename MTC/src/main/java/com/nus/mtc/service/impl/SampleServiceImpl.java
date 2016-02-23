/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.mtc.service.impl;

import com.nus.mtc.dao.ISampleDAO;
import com.nus.mtc.dao.impl.SampleDAOImpl;
import com.nus.mtc.entity.Samples;
import com.nus.mtc.service.ISampleService;
import java.util.List;

/**
 *
 * @author EPHAADK
 */
public class SampleServiceImpl implements ISampleService {

    private ISampleDAO iSampleDAO = new SampleDAOImpl();

    @Override
    public List<Samples> getUniqueSampleDataGroupByCountry() {
        return iSampleDAO.fetchUniqueSampleDataGroupByCountry();
    }

    @Override
    public List<Samples> getSampleDataByStudyId(int studyId) {
        return iSampleDAO.fetchSampleDataByStudyId(studyId);
    }

}
