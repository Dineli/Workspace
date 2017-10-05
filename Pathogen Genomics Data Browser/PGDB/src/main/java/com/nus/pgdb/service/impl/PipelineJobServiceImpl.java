/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.pgdb.service.impl;

import com.nus.pgdb.dao.IPipelineJobDAO;
import com.nus.pgdb.dao.impl.PipelineJobDAOImpl;
import com.nus.pgdb.entity.PipelineJobs;
import com.nus.pgdb.entity.PipelineJobsHistory;
import com.nus.pgdb.service.IPipelineJob;
import java.util.List;

/**
 *
 * @author Dineli
 */
public class PipelineJobServiceImpl implements IPipelineJob {

    private final IPipelineJobDAO ipjdao = new PipelineJobDAOImpl();

    @Override
    public boolean persistJobDetailService(PipelineJobs jobDetails) {
        return ipjdao.persistJobDetails(jobDetails);
    }

    @Override
    public List<PipelineJobs> fetchPipelineDataByUserId(int userId) {
        return ipjdao.fetchPipelineDataByUserId(userId);
    }

    @Override
    public List<PipelineJobs> fetchActiveJobs(int userId) {
        return ipjdao.fetchCurrentlyRunningJobs(userId);
    }

    @Override
    public void updateJobStatus(int jobId, String status) {
        ipjdao.updateJobStatus(jobId, status);
    }

    @Override
    public boolean deleteJobService(int recordId) {
        return ipjdao.deleteJob(recordId);
    }

    @Override
    public boolean persistJobHistoryService(PipelineJobsHistory pipelineJobsHistory) {
        return ipjdao.persistJobHistory(pipelineJobsHistory);
    }

}
