/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.mtc.dao;

import com.nus.mtc.entity.DrugResistanceData;
import java.util.List;

/**
 *
 * @author Dineli
 */
public interface IDrugDAO {

    public List<DrugResistanceData> fetchAllDrugsData(boolean showAllData, String drugName, String locusName);

    public List<String> fetchAllDrugNames();
    
    public List<String> fetchAllLocusNames();

}
