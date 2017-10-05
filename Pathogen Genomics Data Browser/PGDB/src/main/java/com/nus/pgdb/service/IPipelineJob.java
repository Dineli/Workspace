/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.pgdb.service;

import com.nus.pgdb.entity.PipelineJobs;
import com.nus.pgdb.entity.PipelineJobsHistory;
import java.util.List;

/**
 *
 * @author Dineli
 */
public interface IPipelineJob {
    
    boolean persistJobDetailService(PipelineJobs jobDetails);
    
    List<PipelineJobs> fetchPipelineDataByUserId(int userId);
    
    List<PipelineJobs> fetchActiveJobs(int userId);
    
    void updateJobStatus(int jobId, String status); 
    
    boolean deleteJobService(int recordId);
    
    boolean persistJobHistoryService(PipelineJobsHistory pipelineJobsHistory);
    
}
