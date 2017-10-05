/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.tbdr.service;

import com.nus.tbdr.entity.Drugs;
import com.nus.tbdr.exception.TBDRException;
import java.util.List;

/**
 *
 * @author Dineli
 */
public interface IDrug {
    
     public Integer save(Drugs drugObject) throws TBDRException;
    
     public void isExist(String name) throws TBDRException;
     
     public List<Drugs> fetchAllDrugs();
     
     public boolean update(int recordId, String drugName) throws TBDRException;
     
     public int delete(int recordId);
     
     public Drugs findByName(String name);
}
