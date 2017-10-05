/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.tbdr.dao.impl;

import com.nus.tbdr.dao.IVariantDAO;
import com.nus.tbdr.entity.Variants;
import com.nus.tbdr.entity.persistence.HibernateUtil;
import com.nus.tbdr.exception.TBDRException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Dineli
 */
public class VariantDAOImpl implements IVariantDAO {

    private SessionFactory sessionFactory = null;
    private static final Logger LOGGER = LoggerFactory.getLogger(VariantDAOImpl.class);

    public VariantDAOImpl() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public boolean save(List<Variants> variantList) {
        LOGGER.info("Preparing to save data to Variants");
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        boolean status = false;
        try {
            for (Variants obj : variantList) {
                session.merge(obj);
                status = true;
            }
            transaction.commit();
            //if it's empty that means the records are already exists
            if (variantList.isEmpty()) {
                status = true;
            }
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error("Error occured while saving drug data to database " + e.getMessage());
        } finally {
            session.close();
        }
        return status;
    }

    @Override
    public boolean isExist(long genomeStart, long genomeStop, String varBase) throws TBDRException {
        LOGGER.info("Checking the variant record already exists");
        Session session = sessionFactory.openSession();
        boolean isRecordExist = false;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT id FROM variants WHERE var_position_genome_start=:varGenomeStart AND var_position_genome_stop=:varGenomeStop AND var_base=:varbase ");
        try {
            Query query = session.createSQLQuery(sb.toString()).setLong("varGenomeStart", genomeStart).setLong("varGenomeStop", genomeStop).setString("varbase", varBase);
            if (!query.list().isEmpty()) {
                isRecordExist = true;
                LOGGER.info("Variant record already exists.");
            } else {
                LOGGER.info("unique variant record.");
            }
        } catch (Exception exception) {
            LOGGER.error("Error occured while retirieving variant record");
            exception.getMessage();
        } finally {
            session.close();
        }
        return isRecordExist;
    }

    @Override
    public List<Variants> fetchAllData() throws TBDRException {
        LOGGER.info("Fetching all Variant data");
        Session session = sessionFactory.openSession();
        List<Variants> variantList = null;
        try {
            variantList = session.getNamedQuery("Variants.findAll").list();
            if (variantList.size() > 0) {
                LOGGER.info("Variant list size: " + variantList.size());
            }
        } catch (Exception exception) {
            LOGGER.error("Error occured while fetching variant data");
            exception.getMessage();
        } finally {
            session.close();
        }
        return variantList;
    }

    @Override
    public List<String> fetchGeneNames() throws TBDRException {
        LOGGER.info("Fetching unique gene names");
        Session session = sessionFactory.openSession();
        List<String> geneNameList = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT DISTINCT(gene_name) FROM variants ORDER BY gene_name");
        try {
            geneNameList = session.createSQLQuery(sb.toString()).list();
            if (geneNameList.size() > 0) {
                LOGGER.info("geneNameList list size: " + geneNameList.size());
            }
        } catch (Exception exception) {
            LOGGER.error("Error occured while fetching variant data");
            exception.getMessage();
        } finally {
            session.close();
        }
        return geneNameList;
    }

//method used for search feature with multiple selections -> search/base.jsp   
//TODO : need to avoid sql injection    
    @Override
    public List<Object[]> searchQueryBuilder(String drugId, String dataSourceId, String geneName) throws TBDRException {
        List<Object[]> resultList = new ArrayList<>();
//        LOGGER.info("Fetching search results");
//        Session session = sessionFactory.openSession();
//        StringBuilder sb = new StringBuilder();
//        String[] geneArr = geneName.split(",");
//        String geneList = null;
//        switch (geneArr.length) {
//            case 2:
//                geneList = "('" + geneArr[0] + "'" + "," + "'" + geneArr[1] + "')";
//                break;
//            case 1:
//                geneList = "('" + geneArr[0] + "')";
//                break;
//        }
//        try {
//            sb.append("SELECT v.*, dr.drug_id, GROUP_CONCAT(dr.data_source_id),GROUP_CONCAT(dr.high_confidence), dr.reference_pmid  ");
//            sb.append(" FROM variants v JOIN drug_resistance dr ON v.id = dr.variant_id WHERE");
//            if (!"0".equals(drugId) && !"0".equals(dataSourceId) && !"0".equals(geneName)) {
//                sb.append(" dr.drug_id IN (").append(drugId).append(") AND dr.data_source_id IN (").append(dataSourceId).append(") AND v.gene_name IN ").append(geneList).append(" ");
//            } else if (!"0".equals(drugId) && !"0".equals(dataSourceId)) {
//                sb.append(" dr.drug_id IN (").append(drugId).append(") AND dr.data_source_id IN (").append(dataSourceId).append(")");
//            } else if (!"0".equals(drugId) && !"0".equals(geneName)) {
//                sb.append(" dr.drug_id IN (").append(drugId).append(") AND v.gene_name IN ").append(geneList).append(" ");
//            } else if (!"0".equals(dataSourceId) && !"0".equals(geneName)) {
//                sb.append(" dr.data_source_id IN (").append(dataSourceId).append(") AND v.gene_name IN ").append(geneList).append(" ");
//            } else if (!"0".equals(drugId)) {
//                sb.append(" dr.drug_id IN (").append(drugId).append(")");
//            } else if (!"0".equals(dataSourceId)) {
//                sb.append(" dr.data_source_id IN (").append(dataSourceId).append(")");
//            } else {
//                sb.append(" v.gene_name IN ").append(geneList).append("");
//            }
//            sb.append(" GROUP BY dr.variant_id, dr.drug_id");
//            sb.append(" ORDER BY var_position_genome_start");
//            resultList = session.createSQLQuery(sb.toString()).list();
//            if (resultList.size() > 0) {
//                LOGGER.info("resultList size: " + resultList.size());
//            }
//        } catch (Exception exception) {
//            LOGGER.error("Error occured while fetching search results");
//            exception.getMessage();
//        } finally {
//            session.close();
//        }
        return resultList;
    }

    @Override
    public List<Object[]> searchQueryBuilder(int selection1, String value1, String joinCondition1, int selection2, String value2, String joinCondition2, int selection3, String value3, String joinCondition3,
            int selection4, String value4, String joinCondition4, int selection5, String value5) {
        LOGGER.info("Fetching search results");
        Session session = sessionFactory.openSession();
        List<Object[]> resultList = new ArrayList<>();
        StringBuilder finalQueryString = new StringBuilder();
        int count = 0; //to identify upto which select tag is selected

        try {
            finalQueryString.append("SELECT v.*, dr.drug_id, GROUP_CONCAT(dr.data_source_id),GROUP_CONCAT(dr.high_confidence), dr.reference_pmid  ");
            finalQueryString.append(" FROM variants v JOIN drug_resistance dr ON v.id = dr.variant_id");

            if (selection1 != 0) {
                finalQueryString.append(" WHERE");
                finalQueryString.append(generateSQLConditionalClause(selection1, joinCondition1));
                count++;
            }

            if (selection2 != 0) {
                finalQueryString.append(generateSQLConditionalClause(selection2, joinCondition2));
                count++;
            }

            if (selection3 != 0) {
                finalQueryString.append(generateSQLConditionalClause(selection3, joinCondition3));
                count++;
            }

            if (selection4 != 0) {
                finalQueryString.append(generateSQLConditionalClause(selection4, joinCondition4));
                count++;
            }

            if (selection5 != 0) {
                finalQueryString.append(generateSQLConditionalClause(selection5, ""));
                count++;
            }
            finalQueryString.append(" GROUP BY dr.variant_id, dr.drug_id");
            finalQueryString.append(" ORDER BY var_position_genome_start");

            //set parameters according to appropriate select tags
            switch (count) {
                case 1:
                    resultList = session.createSQLQuery(finalQueryString.toString()).setParameter(0, value1).list();
                    break;
                case 2:
                    resultList = session.createSQLQuery(finalQueryString.toString()).setParameter(0, value1).setParameter(1, value2).list();
                    break;
                case 3:
                    resultList = session.createSQLQuery(finalQueryString.toString()).setParameter(0, value1).setParameter(1, value2).setParameter(2, value3).list();
                    break;
                case 4:
                    resultList = session.createSQLQuery(finalQueryString.toString()).setParameter(0, value1).setParameter(1, value2).setParameter(2, value3).setParameter(3, value4).list();
                    break;
                case 5:
                    resultList = session.createSQLQuery(finalQueryString.toString()).setParameter(0, value1).setParameter(1, value2).setParameter(2, value3).setParameter(3, value4).setParameter(4, value5).list();
                    break;
                default:
                    resultList = session.createSQLQuery(finalQueryString.toString()).list();
                    break;
            }
            if (resultList.size() > 0) {
                LOGGER.info("resultList size: " + resultList.size());
            }
        } catch (Exception exception) {
            LOGGER.error("Error occured while fetching search results");
            exception.getMessage();
        } finally {
            session.close();
        }
        return resultList;
    }

    //categoryId : select tag value (drug/gene/ds) condition : SQL conditional operator (AND/OR)
    private StringBuilder generateSQLConditionalClause(int categoryId, String condition) {
        StringBuilder builder = new StringBuilder();
        switch (categoryId) {
            case 1:
                builder.append(" dr.drug_id=? ").append(condition);
                break;
            case 2:
                builder.append(" v.gene_name=? ").append(condition);
                break;
            case 3:
                builder.append(" dr.data_source_id=? ").append(condition);
                break;
        }
        return builder;
    }

    @Override
    public List<Object[]> fetchAll() throws TBDRException {
        LOGGER.info("Fetching all Variant data");
        Session session = sessionFactory.openSession();
        List<Object[]> variantDrList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT v.*, dr.drug_id, GROUP_CONCAT(dr.data_source_id),GROUP_CONCAT(dr.high_confidence), dr.reference_pmid");
        sb.append(" FROM variants v JOIN drug_resistance dr ON v.id = dr.variant_id");
        sb.append(" GROUP BY dr.variant_id, dr.drug_id");
        sb.append(" ORDER BY v.var_position_genome_start");
        try {
            SQLQuery query = session.createSQLQuery(sb.toString());
            variantDrList = query.list();
            if (variantDrList.size() > 0) {
                LOGGER.info("Variant list size: " + variantDrList.size());
            }
        } catch (Exception exception) {
            LOGGER.error("Error occured while fetching variant data");
            exception.getMessage();
        } finally {
            session.close();
        }
        return variantDrList;
    }

}
