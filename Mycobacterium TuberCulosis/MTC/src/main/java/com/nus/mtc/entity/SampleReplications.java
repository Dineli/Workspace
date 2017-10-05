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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Dineli
 */
@Entity
@Table(name = "sample_replications")
@NamedQueries({
    @NamedQuery(name = "SampleReplications.findAll", query = "SELECT s FROM SampleReplications s"),
    @NamedQuery(name = "SampleReplications.findById", query = "SELECT s FROM SampleReplications s WHERE s.id = :id")})
public class SampleReplications implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "id")
    private String id;
    @JoinColumn(name = "sample_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Samples sampleId;

    public SampleReplications() {
    }

    public SampleReplications(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Samples getSampleId() {
        return sampleId;
    }

    public void setSampleId(Samples sampleId) {
        this.sampleId = sampleId;
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
        if (!(object instanceof SampleReplications)) {
            return false;
        }
        SampleReplications other = (SampleReplications) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nus.mtc.entity.SampleReplications[ id=" + id + " ]";
    }
    
}
