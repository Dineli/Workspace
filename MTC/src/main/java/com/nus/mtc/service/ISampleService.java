/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.mtc.service;

import com.nus.mtc.entity.Accessions;
import com.nus.mtc.entity.Samples;
import java.util.List;

/**
 *
 * @author Dineli
 */
public interface ISampleService {

    public List<Samples> getUniqueSampleDataGroupByStudy();

    public List<Object[]> getSampleDataByStudyId(String studyId);
    
    public List<Object[]> getSampleDataByLocationId(int locationId);
    
    public List<Accessions> getAccessionDataBySampleId(String sampleId);
}
