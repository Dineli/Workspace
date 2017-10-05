/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.tbdr.dao;

import com.nus.tbdr.entity.Drugs;
import java.util.List;

/**
 *
 * @author Dineli
 */
public interface IDrugDAO {
    
    public Integer save(Drugs drugObject);
    
    public boolean isExist(String name);
    
    public List<Drugs> fetchAllDrugs();
    
    public boolean update(int recordId, String drugName);
    
    public int delete(int recordId);
    
    public Drugs findByName(String name);
    
}
