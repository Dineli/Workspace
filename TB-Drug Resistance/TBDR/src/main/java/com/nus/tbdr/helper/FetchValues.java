/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.tbdr.helper;

import com.nus.tbdr.entity.DataSources;
import com.nus.tbdr.entity.Drugs;
import com.nus.tbdr.service.IDataSource;
import com.nus.tbdr.service.IDrug;
import com.nus.tbdr.service.impl.DataSourceServiceImpl;
import com.nus.tbdr.service.impl.DrugServiceImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Dineli
 */
public class FetchValues {

    IDrug iDrug = null;
    IDataSource iDataSource = null;

    public FetchValues() {
        iDrug = new DrugServiceImpl();
        iDataSource = new DataSourceServiceImpl();
    }

    public Map<Integer, Drugs> readDrugName() {
        Map<Integer, Drugs> drugNameMap = new HashMap<>();
        List<Drugs> drugs = iDrug.fetchAllDrugs();

        for (Drugs drugsObj : drugs) {
            drugNameMap.put(drugsObj.getId(), drugsObj);
        }
        return drugNameMap;
    }

    public Map<Integer, DataSources> readDsName() {
        Map<Integer, DataSources> dsMap = new HashMap<>();
        List<DataSources> dataSources = iDataSource.fetchAllDataSources();

        for (DataSources dsObj : dataSources) {
            dsMap.put(dsObj.getId(), dsObj);
        }
        return dsMap;
    }

}
