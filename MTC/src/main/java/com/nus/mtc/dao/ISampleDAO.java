/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.mtc.dao;

import com.nus.mtc.entity.Samples;
import java.util.List;

/**
 *
 * @author EPHAADK
 */
public interface ISampleDAO {
    public List<Samples> fetchUniqueSampleDataGroupByStudy();
    public List<Samples> fetchSampleDataByStudyId(String studyId);
}
