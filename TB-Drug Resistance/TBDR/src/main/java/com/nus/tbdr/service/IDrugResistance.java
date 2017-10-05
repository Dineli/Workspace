/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.tbdr.service;

import com.nus.tbdr.entity.DrugResistance;
import com.nus.tbdr.exception.TBDRException;
import java.util.List;

/**
 *
 * @author Dineli
 */
public interface IDrugResistance {

    public boolean save(List<DrugResistance> drugResList) throws TBDRException;

    public List<DrugResistance> fetchAllData() throws TBDRException;

    public boolean isExist(int variantId, int drugId, int dataSourceId) throws TBDRException;

    public List<DrugResistance> fetchByVariantId(int variantId) throws TBDRException;

    public void updateNullValues() throws TBDRException;
}
