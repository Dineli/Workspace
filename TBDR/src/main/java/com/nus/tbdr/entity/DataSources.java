/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.tbdr.entity;

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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Dineli
 */
@Entity
@Table(name = "data_sources", catalog = "tbdr", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DataSources.findAll", query = "SELECT d FROM DataSources d"),
    @NamedQuery(name = "DataSources.findById", query = "SELECT d FROM DataSources d WHERE d.id = :id"),
    @NamedQuery(name = "DataSources.findByName", query = "SELECT d FROM DataSources d WHERE d.name = :name"),
    @NamedQuery(name = "DataSources.findByDescription", query = "SELECT d FROM DataSources d WHERE d.description = :description")})
public class DataSources implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dataSourceId")
//    private Collection<DrugResistance> drugResistanceCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "name")
    private String name;
    @Size(max = 30)
    @Column(name = "description")
    private String description;

    public DataSources() {
    }

    public DataSources(Integer id) {
        this.id = id;
    }

    public DataSources(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        if (!(object instanceof DataSources)) {
            return false;
        }
        DataSources other = (DataSources) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nus.tbdr.entity.DataSources[ id=" + id + " ]";
    }

//    @XmlTransient
//    public Collection<DrugResistance> getDrugResistanceCollection() {
//        return drugResistanceCollection;
//    }
//
//    public void setDrugResistanceCollection(Collection<DrugResistance> drugResistanceCollection) {
//        this.drugResistanceCollection = drugResistanceCollection;
//    }
    
}
