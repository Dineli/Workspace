/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.mtc.service;

import com.nus.mtc.entity.Samples;
import java.util.List;

/**
 *
 * @author EPHAADK
 */
public interface ISampleService {
    public List<Samples> getUniqueSampleDataGroupByStudy();
    public List<Samples> getSampleDataByStudyId(String studyId);
}
