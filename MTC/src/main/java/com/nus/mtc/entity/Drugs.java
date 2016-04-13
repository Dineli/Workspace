/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.mtc.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Dineli
 */
@Entity
@Table(name = "drugs")
@NamedQueries({
    @NamedQuery(name = "Drugs.getAllDrugNames", query = "SELECT d.drugAbbreviation FROM Drugs d"),
    @NamedQuery(name = "Drugs.findById", query = "SELECT d FROM Drugs d WHERE d.id = :id"),
    @NamedQuery(name = "Drugs.findByDrugAbbreviation", query = "SELECT d FROM Drugs d WHERE d.drugAbbreviation = :drugAbbreviation"),
    @NamedQuery(name = "Drugs.findByDrugName", query = "SELECT d FROM Drugs d WHERE d.drugName = :drugName")})
public class Drugs implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "drug_abbreviation")
    private String drugAbbreviation;
    @Size(max = 25)
    @Column(name = "drug_name")
    private String drugName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "drugId")
    private Collection<DrugResistanceData> drugResistanceDataCollection;

    public Drugs() {
    }

    public Drugs(Integer id) {
        this.id = id;
    }

    public Drugs(Integer id, String drugAbbreviation) {
        this.id = id;
        this.drugAbbreviation = drugAbbreviation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDrugAbbreviation() {
        return drugAbbreviation;
    }

    public void setDrugAbbreviation(String drugAbbreviation) {
        this.drugAbbreviation = drugAbbreviation;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public Collection<DrugResistanceData> getDrugResistanceDataCollection() {
        return drugResistanceDataCollection;
    }

    public void setDrugResistanceDataCollection(Collection<DrugResistanceData> drugResistanceDataCollection) {
        this.drugResistanceDataCollection = drugResistanceDataCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Drugs)) {
            return false;
        }
        Drugs other = (Drugs) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nus.mtc.entity.Drugs[ id=" + id + " ]";
    }
    
}
