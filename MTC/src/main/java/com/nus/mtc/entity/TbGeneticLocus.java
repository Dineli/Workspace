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
@Table(name = "tb_genetic_locus")
@NamedQueries({
    @NamedQuery(name = "TbGeneticLocus.getAllLocusNames", query = "SELECT t.locusName FROM TbGeneticLocus t"),
    @NamedQuery(name = "TbGeneticLocus.findById", query = "SELECT t FROM TbGeneticLocus t WHERE t.id = :id"),
    @NamedQuery(name = "TbGeneticLocus.findByLocusName", query = "SELECT t FROM TbGeneticLocus t WHERE t.locusName = :locusName"),
    @NamedQuery(name = "TbGeneticLocus.findByLocusTag", query = "SELECT t FROM TbGeneticLocus t WHERE t.locusTag = :locusTag"),
    @NamedQuery(name = "TbGeneticLocus.findByUrl", query = "SELECT t FROM TbGeneticLocus t WHERE t.url = :url")})
public class TbGeneticLocus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "locus_name")
    private String locusName;
    @Size(max = 25)
    @Column(name = "locus_tag")
    private String locusTag;
    @Size(max = 100)
    @Column(name = "url")
    private String url;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbGeneticLocusId")
    private Collection<DrugResistanceData> drugResistanceDataCollection;

    public TbGeneticLocus() {
    }

    public TbGeneticLocus(Integer id) {
        this.id = id;
    }

    public TbGeneticLocus(Integer id, String locusName) {
        this.id = id;
        this.locusName = locusName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocusName() {
        return locusName;
    }

    public void setLocusName(String locusName) {
        this.locusName = locusName;
    }

    public String getLocusTag() {
        return locusTag;
    }

    public void setLocusTag(String locusTag) {
        this.locusTag = locusTag;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
        if (!(object instanceof TbGeneticLocus)) {
            return false;
        }
        TbGeneticLocus other = (TbGeneticLocus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nus.mtc.entity.TbGeneticLocus[ id=" + id + " ]";
    }
    
}
