/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.tbdr.service.impl;

import com.nus.tbdr.dao.IDrugDAO;
import com.nus.tbdr.dao.impl.DrugDAOImpl;
import com.nus.tbdr.entity.Drugs;
import com.nus.tbdr.exception.InvalidArgumentException;
import com.nus.tbdr.exception.RecordExistException;
import com.nus.tbdr.exception.TBDRException;
import com.nus.tbdr.service.IDrug;
import java.util.List;

/**
 *
 * @author Dineli
 */
public class DrugServiceImpl implements IDrug {

    private final IDrugDAO iDrugDAO = new DrugDAOImpl();

    @Override
    public Integer save(Drugs drugObject) throws TBDRException{
        isExist(drugObject.getName());
        return iDrugDAO.save(drugObject);
    }

    @Override
    public void isExist(String name) throws TBDRException{
        if (name == null || name.isEmpty()) {
            throw new InvalidArgumentException("name", null);
        }
        if (iDrugDAO.isExist(name)) {
            throw new RecordExistException();
        }
    }

    @Override
    public List<Drugs> fetchAllDrugs() {
        return iDrugDAO.fetchAllDrugs();
    }

    @Override
    public boolean update(int recordId, String drugName) throws TBDRException{
        isExist(drugName);
        return iDrugDAO.update(recordId, drugName);
    }

    @Override
    public int delete(int recordId) {
        return iDrugDAO.delete(recordId);
    }

    @Override
    public Drugs findByName(String name) {
        return iDrugDAO.findByName(name);
    }

}
