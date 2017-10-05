/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.mtc.entity;

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
 * @author EPHAADK
 */
@Entity
@Table(name = "samples")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Samples.findAll", query = "SELECT s FROM Samples s"),
    @NamedQuery(name = "Samples.findById", query = "SELECT s FROM Samples s WHERE s.id = :id")})
public class Samples implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private String id;
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Locations locationId;
    @JoinColumn(name = "study_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Studys studyId;

    public Samples() {
    }

    public Samples(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Locations getLocationId() {
        return locationId;
    }

    public void setLocationId(Locations locationId) {
        this.locationId = locationId;
    }

    public Studys getStudyId() {
        return studyId;
    }

    public void setStudyId(Studys studyId) {
        this.studyId = studyId;
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
        if (!(object instanceof Samples)) {
            return false;
        }
        Samples other = (Samples) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nus.mtc.entity.Samples[ id=" + id + " ]";
    }

}
