/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.tbdr.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Dineli
 */
@Entity
@Table(name = "drug_resistance", catalog = "tbdr", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DrugResistance.findAll", query = "SELECT d FROM DrugResistance d"),
    @NamedQuery(name = "DrugResistance.findById", query = "SELECT d FROM DrugResistance d WHERE d.id = :id"),
    @NamedQuery(name = "DrugResistance.findByReferencePmid", query = "SELECT d FROM DrugResistance d WHERE d.referencePmid = :referencePmid"),
    @NamedQuery(name = "DrugResistance.findByHighConfidence", query = "SELECT d FROM DrugResistance d WHERE d.highConfidence = :highConfidence"),
    @NamedQuery(name = "DrugResistance.findByVariantId", query = "SELECT d FROM DrugResistance d WHERE d.variantId.id = :variantId")
})
public class DrugResistance implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "reference_pmid")
    private Long referencePmid;
    @Column(name = "high_confidence")
    private Boolean highConfidence;
    @JoinColumn(name = "data_source_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private DataSources dataSourceId;
    @JoinColumn(name = "drug_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Drugs drugId;
    @JoinColumn(name = "variant_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Variants variantId;

    public DrugResistance() {
    }

    public DrugResistance(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getReferencePmid() {
        return referencePmid;
    }

    public void setReferencePmid(Long referencePmid) {
        this.referencePmid = referencePmid;
    }

    public Boolean getHighConfidence() {
        return highConfidence;
    }

    public void setHighConfidence(Boolean highConfidence) {
        this.highConfidence = highConfidence;
    }

    public DataSources getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(DataSources dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public Drugs getDrugId() {
        return drugId;
    }

    public void setDrugId(Drugs drugId) {
        this.drugId = drugId;
    }

    public Variants getVariantId() {
        return variantId;
    }

    public void setVariantId(Variants variantId) {
        this.variantId = variantId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((drugId == null) ? 0 : drugId.hashCode());
        result = prime * result + ((variantId == null) ? 0 : variantId.hashCode());
        result = prime * result + ((dataSourceId == null) ? 0 : dataSourceId.hashCode());
        return result;
    }

    /* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof DrugResistance)) {
            return false;
        }
        DrugResistance other = (DrugResistance) obj;
        if (drugId == null) {
            if (other.drugId != null) {
                return false;
            }
        } else if (!drugId.equals(other.drugId)) {
            return false;
        }
        if (variantId == null) {
            if (other.variantId != null) {
                return false;
            }
        } else if (!variantId.equals(other.variantId)) {
            return false;
        }
        if (dataSourceId == null) {
            if (other.dataSourceId != null) {
                return false;
            }
        } else if (!dataSourceId.equals(other.dataSourceId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nus.tbdr.entity.DrugResistance[ id=" + id + " ]";
    }

}
