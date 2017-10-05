/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.tbdr.service.impl;

import com.nus.tbdr.dao.IVariantDAO;
import com.nus.tbdr.dao.impl.VariantDAOImpl;
import com.nus.tbdr.entity.Variants;
import com.nus.tbdr.exception.TBDRException;
import com.nus.tbdr.service.IVariant;
import java.util.List;

/**
 *
 * @author Dineli
 */
public class VariantServiceImpl implements IVariant {

    private final IVariantDAO iVariantDAO = new VariantDAOImpl();

    @Override
    public boolean save(List<Variants> variantList) throws TBDRException {
        return iVariantDAO.save(variantList);
    }

    @Override
    public boolean isExist(long genomeStart, long genomeStop, String varBase) throws TBDRException {
        return iVariantDAO.isExist(genomeStart, genomeStop, varBase);
    }

    @Override
    public List<Variants> fetchAllData() throws TBDRException {
        return iVariantDAO.fetchAllData();
    }

    @Override
    public List<String> fetchGeneNames() throws TBDRException {
        return iVariantDAO.fetchGeneNames();
    }

    @Override
    public List<Object[]> searchQueryBuilder(String drugId, String dataSourceId, String geneName) throws TBDRException {
        return iVariantDAO.searchQueryBuilder(drugId, dataSourceId, geneName);
    }

    @Override
    public List<Object[]> fetchAll() throws TBDRException {
        return iVariantDAO.fetchAll();
    }

    @Override
    public List<Object[]> searchQueryBuilder(int selection1, String value1, String joinCondition1, int selection2, String value2, String joinCondition2, int selection3, String value3, String joinCondition3, int selection4, String value4, String joinCondition4, int selection5, String value5) throws TBDRException {
        return iVariantDAO.searchQueryBuilder(selection1, value1, joinCondition1, selection2, value2, joinCondition2, selection3, value3, joinCondition3, selection4, value4, joinCondition4, selection5, value5);
    }

}
