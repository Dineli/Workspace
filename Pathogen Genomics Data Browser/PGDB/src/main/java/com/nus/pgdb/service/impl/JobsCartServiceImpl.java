/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.pgdb.service.impl;

import com.nus.pgdb.dao.IJobsCartDAO;
import com.nus.pgdb.dao.impl.JobsCartDAOImpl;
import com.nus.pgdb.entity.JobsCart;
import com.nus.pgdb.service.IJobsCart;
import java.util.List;

/**
 *
 * @author Dineli
 */
public class JobsCartServiceImpl implements IJobsCart {

    private final IJobsCartDAO ijcdao = new JobsCartDAOImpl();

    @Override
    public boolean persistJobsCartDetailService(JobsCart jobsCartObj) {
        return ijcdao.persistJobsCartDetails(jobsCartObj);
    }

    @Override
    public long fetchCartSizeService(int userId) {
        return ijcdao.fetchCartSize(userId);
    }

    @Override
    public boolean deleteCartItemService(int recordId, int userId) {
        return ijcdao.deleteCartItem(recordId, userId);
    }

    @Override
    public boolean isExistsService(int userId, String sampleId) {
        return ijcdao.isExists(userId, sampleId);
    }

    @Override
    public List<JobsCart> fetchCartItemsService(int userId) {
        return ijcdao.fetchCartItems(userId);
    }

}
