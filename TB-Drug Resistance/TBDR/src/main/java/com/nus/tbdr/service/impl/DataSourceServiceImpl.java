/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.tbdr.service.impl;

import com.nus.tbdr.dao.IDataSourceDAO;
import com.nus.tbdr.dao.impl.DataSourceDAOImpl;
import com.nus.tbdr.entity.DataSources;
import com.nus.tbdr.exception.InvalidArgumentException;
import com.nus.tbdr.exception.RecordExistException;
import com.nus.tbdr.exception.TBDRException;
import com.nus.tbdr.service.IDataSource;
import java.util.List;

/**
 *
 * @author Dineli
 */
public class DataSourceServiceImpl implements IDataSource {

    private final IDataSourceDAO iDataSourceDAO = new DataSourceDAOImpl();

    @Override
    public int save(DataSources dataSources) throws TBDRException {
        isExist(dataSources.getName());
        return iDataSourceDAO.save(dataSources);
    }

    @Override
    public List<DataSources> fetchAllDataSources() {
        return iDataSourceDAO.fetchAllDataSources();
    }

    @Override
    public void isExist(String name) throws TBDRException {
        if (name == null || name.isEmpty()) {
            throw new InvalidArgumentException("name", null);
        }
        if (iDataSourceDAO.isExist(name)) {
            throw new RecordExistException();
        }
    }

    @Override
    public boolean update(DataSources dataSourceObj) throws TBDRException{
//        isExist(dataSourceObj.getName());
        return iDataSourceDAO.update(dataSourceObj);
    }

    @Override
    public int delete(int recordId) throws TBDRException {
        return iDataSourceDAO.delete(recordId);
    }

    @Override
    public DataSources fetchDSbyId(int datasourceId) throws TBDRException {
        return iDataSourceDAO.fetchDSbyId(datasourceId);
    }

}
