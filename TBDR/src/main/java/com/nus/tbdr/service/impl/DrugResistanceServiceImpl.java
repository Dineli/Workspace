/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.tbdr.service.impl;

import com.nus.tbdr.dao.IDrugResistanceDAO;
import com.nus.tbdr.dao.impl.DrugResistanceDAOImpl;
import com.nus.tbdr.entity.DrugResistance;
import com.nus.tbdr.exception.TBDRException;
import com.nus.tbdr.service.IDrugResistance;
import java.util.List;

/**
 *
 * @author Dineli
 */
public class DrugResistanceServiceImpl implements IDrugResistance {

    private final IDrugResistanceDAO iDrugResistanceDAO = new DrugResistanceDAOImpl();

    @Override
    public boolean save(List<DrugResistance> drugResList) throws TBDRException {
        return iDrugResistanceDAO.save(drugResList);
    }

    @Override
    public List<DrugResistance> fetchAllData() throws TBDRException {
        return iDrugResistanceDAO.fetchAllData();
    }

    @Override
    public boolean isExist(int variantId, int drugId, int dataSourceId) throws TBDRException {
        return iDrugResistanceDAO.isExist(variantId, drugId, dataSourceId);
    }

    @Override
    public List<DrugResistance> fetchByVariantId(int variantId) throws TBDRException {
        return iDrugResistanceDAO.fetchByVariantId(variantId);
    }

    @Override
    public void updateNullValues() throws TBDRException {
        iDrugResistanceDAO.updateNullValuesIfExists();
    }

}
