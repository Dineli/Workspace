/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.pgdb.service;

import com.nus.pgdb.entity.JobsCart;
import java.util.List;

/**
 *
 * @author Dineli
 */
public interface IJobsCart {

    boolean persistJobsCartDetailService(JobsCart jobsCartObj);

    long fetchCartSizeService(int userId);

    boolean deleteCartItemService(int recordId, int userId);
    
    boolean isExistsService(int userId, String sampleId);
    
    public List<JobsCart> fetchCartItemsService(int userId);
}
