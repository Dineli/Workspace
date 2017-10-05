/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.pgdb.dao;

import com.nus.pgdb.entity.JobsCart;
import java.util.List;

/**
 *
 * @author Dineli
 */
public interface IJobsCartDAO {

    boolean persistJobsCartDetails(JobsCart jobsCartObj);

    boolean isExists(int userId, String sampleId);

    long fetchCartSize(int userId);

    boolean deleteCartItem(int recordId, int userId);
    
    List<JobsCart> fetchCartItems(int userId);

}
