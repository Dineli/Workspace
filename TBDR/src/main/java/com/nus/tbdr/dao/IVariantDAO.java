/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.tbdr.dao;

import com.nus.tbdr.entity.Variants;
import com.nus.tbdr.exception.TBDRException;
import java.util.List;

/**
 *
 * @author Dineli
 */
public interface IVariantDAO {

    public boolean save(List<Variants> variantList) throws TBDRException;

    public boolean isExist(long genomeStart, long genomeStop, String varBase) throws TBDRException;

    public List<Variants> fetchAllData() throws TBDRException;

    public List<String> fetchGeneNames() throws TBDRException;

    public List<Object[]> searchQueryBuilder(String drugId, String dataSourceId, String geneName) throws TBDRException;

    public List<Object[]> fetchAll() throws TBDRException;

    public List<Object[]> searchQueryBuilder(int selection1, String value1, String joinCondition1, int selection2, String value2, String joinCondition2, int selection3, String value3, String joinCondition3,
            int selection4, String value4, String joinCondition4, int selection5, String value5) throws TBDRException;

}
