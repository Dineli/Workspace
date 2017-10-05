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
@Table(name = "accessions")
@NamedQueries({
    @NamedQuery(name = "Accessions.findAll", query = "SELECT a FROM Accessions a"),
    @NamedQuery(name = "Accessions.findBySampleId", query = "SELECT a FROM Accessions a WHERE a.sampleId.id = :sampleId"),
    @NamedQuery(name = "Accessions.findById", query = "SELECT a FROM Accessions a WHERE a.id = :id"),
    @NamedQuery(name = "Accessions.findByUrl1", query = "SELECT a FROM Accessions a WHERE a.url1 = :url1"),
    @NamedQuery(name = "Accessions.findByUrl2", query = "SELECT a FROM Accessions a WHERE a.url2 = :url2")})
public class Accessions implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "url_1")
    private String url1;
    @Size(max = 80)
    @Column(name = "url_2")
    private String url2;
    @JoinColumn(name = "sample_replication_id", referencedColumnName = "id")
    @ManyToOne
    private SampleReplications sampleReplicationId;
    @JoinColumn(name = "sample_id", referencedColumnName = "id")
    @ManyToOne
    private Samples sampleId;

    public Accessions() {
    }

    public Accessions(String id) {
        this.id = id;
    }

    public Accessions(String id, String url1) {
        this.id = id;
        this.url1 = url1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl1() {
        return url1;
    }

    public void setUrl1(String url1) {
        this.url1 = url1;
    }

    public String getUrl2() {
        return url2;
    }

    public void setUrl2(String url2) {
        this.url2 = url2;
    }

    public SampleReplications getSampleReplicationId() {
        return sampleReplicationId;
    }

    public void setSampleReplicationId(SampleReplications sampleReplicationId) {
        this.sampleReplicationId = sampleReplicationId;
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
        if (!(object instanceof Accessions)) {
            return false;
        }
        Accessions other = (Accessions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nus.mtc.entity.Accessions[ id=" + id + " ]";
    }
    
}
