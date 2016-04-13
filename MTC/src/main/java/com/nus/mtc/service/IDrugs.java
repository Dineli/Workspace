/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.mtc.service;

import com.nus.mtc.entity.DrugResistanceData;
import java.util.List;

/**
 *
 * @author Dineli
 */
public interface IDrugs {

    public List<DrugResistanceData> getAllDrugsData(boolean showAllData, String drugName, String locusName);

    public List<String> getAllDrugNames();

    public List<String> getAllLocusNames();

}
