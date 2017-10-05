/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.pgdb.dao;

import com.nus.pgdb.entity.PipelineJobs;
import com.nus.pgdb.entity.PipelineJobsHistory;
import java.util.List;

/**
 *
 * @author Dineli
 */
public interface IPipelineJobDAO {

    boolean persistJobDetails(PipelineJobs jobDetails);

    List<PipelineJobs> fetchPipelineDataByUserId(int userId);

    List<PipelineJobs> fetchCurrentlyRunningJobs(int userId);
    
    void updateJobStatus(int jobId, String status);
    
    boolean deleteJob (int recordId);
    
    boolean persistJobHistory(PipelineJobsHistory pipelineJobsHistory);

}
