/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.pgdb.service.impl;

import com.nus.pgdb.dao.ISampleSequenceDAO;
import com.nus.pgdb.dao.impl.SampleSequenceDAOImpl;
import com.nus.pgdb.entity.SamplesSequenceFiles;
import com.nus.pgdb.entity.SequenceFiles;
import com.nus.pgdb.service.ISampleSequence;
import java.util.List;

/**
 *
 * @author Dineli
 */
public class SampleSequenceServiceImpl implements ISampleSequence{
    
    private final ISampleSequenceDAO iSampleSequenceDAO = new SampleSequenceDAOImpl();

    @Override
    public int persistSequenceDataService(SequenceFiles sequenceFileObj) {
        return iSampleSequenceDAO.persistSequenceData(sequenceFileObj);
    }

    @Override
    public boolean persistSampleSequenceDataService(SamplesSequenceFiles samplesSequenceObj) {
        return iSampleSequenceDAO.persistSampleSequenceData(samplesSequenceObj);
    }

    @Override
    public List<SamplesSequenceFiles> fetchSequenceFilesBySampleIdService(String sampleId, int userId, boolean sharedProject) {
        return iSampleSequenceDAO.fetchSequenceFilesBySampleId(sampleId, userId, sharedProject);
    }

    @Override
    public boolean deleteSampleSequenceDataService(int recordId) {
        return iSampleSequenceDAO.deleteSampleSequenceData(recordId);
    }
    
}
