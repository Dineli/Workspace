/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.pgdb.service;

import com.nus.pgdb.entity.SamplesSequenceFiles;
import com.nus.pgdb.entity.SequenceFiles;
import java.util.List;

/**
 *
 * @author Dineli
 */
public interface ISampleSequence {
    
    int persistSequenceDataService(SequenceFiles sequenceFileObj);

    boolean persistSampleSequenceDataService(SamplesSequenceFiles samplesSequenceObj);
    
    List<SamplesSequenceFiles> fetchSequenceFilesBySampleIdService(String sampleId, int userId, boolean sharedProject);
    
    boolean deleteSampleSequenceDataService(int recordId);
    
}
