/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.tbdr.service;

import com.nus.tbdr.entity.DataSources;
import com.nus.tbdr.exception.TBDRException;
import java.util.List;

/**
 *
 * @author Dineli
 */
public interface IDataSource {

    public int save(DataSources dataSources) throws TBDRException;

    public List<DataSources> fetchAllDataSources();

    public void isExist(String name) throws TBDRException;

    public boolean update(DataSources dataSourceObj) throws TBDRException;

    public int delete(int recordId) throws TBDRException;

    public DataSources fetchDSbyId(int datasourceId) throws TBDRException;

}
