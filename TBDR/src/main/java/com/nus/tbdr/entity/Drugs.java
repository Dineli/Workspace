/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.tbdr.entity;

import java.io.Serializable;
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

/**
 *
 * @author Dineli
 */
@Entity
@Table(name = "drugs")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Drugs.findAll", query = "SELECT d FROM Drugs d"),
    @NamedQuery(name = "Drugs.findById", query = "SELECT d FROM Drugs d WHERE d.id = :id"),
    @NamedQuery(name = "Drugs.findByName", query = "SELECT d FROM Drugs d WHERE d.name = :name")})
public class Drugs implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "drugId")
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

    public Drugs() {
    }

    public Drugs(Integer id) {
        this.id = id;
    }

    public Drugs(Integer id, String name) {
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
        return "com.nus.tbdr.entity.Drugs[ id=" + id + " ]";
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
