/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.mtc.service.impl;

import com.nus.mtc.dao.IDrugDAO;
import com.nus.mtc.dao.impl.DrugDAOImpl;
import com.nus.mtc.entity.DrugResistanceData;
import com.nus.mtc.service.IDrugs;
import java.util.List;

/**
 *
 * @author Dineli
 */
public class DrugServiceImpl implements IDrugs {

    private IDrugDAO iDrugDAO = new DrugDAOImpl();

    @Override
    public List<DrugResistanceData> getAllDrugsData(boolean showAllData, String drugName, String locusName) {
        return iDrugDAO.fetchAllDrugsData(showAllData, drugName, locusName);
    }

    @Override
    public List<String> getAllDrugNames() {
        return iDrugDAO.fetchAllDrugNames();
    }

    @Override
    public List<String> getAllLocusNames() {
        return iDrugDAO.fetchAllLocusNames();
    }

}
