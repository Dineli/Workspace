/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.pgdb.dao;

import com.nus.pgdb.entity.SamplesSequenceFiles;
import com.nus.pgdb.entity.SequenceFiles;
import java.util.List;

/**
 *
 * @author Dineli
 */
public interface ISampleSequenceDAO {

    int persistSequenceData(SequenceFiles sequenceFileObj);

    boolean persistSampleSequenceData(SamplesSequenceFiles samplesSequenceObj);

    List<SamplesSequenceFiles> fetchSequenceFilesBySampleId(String sampleId, int userId, boolean sharedProject);

    boolean deleteSampleSequenceData(int recordId);

}
