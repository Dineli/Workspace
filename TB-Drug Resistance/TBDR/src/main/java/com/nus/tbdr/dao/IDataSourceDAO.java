/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.tbdr.dao;

import com.nus.tbdr.entity.DataSources;
import com.nus.tbdr.exception.TBDRException;
import java.util.List;

/**
 *
 * @author Dineli
 */
public interface IDataSourceDAO {

    public int save(DataSources dataSources);

    public List<DataSources> fetchAllDataSources();

    public boolean isExist(String name);
    
    public boolean update(DataSources dataSourceObj);
    
    public int delete(int recordId);
    
    public DataSources fetchDSbyId(int datasourceId) throws TBDRException;
}
