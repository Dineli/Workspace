/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.pgdb.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Dineli
 */
@Entity
@Table(name = "samples", catalog = "pgdb", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Samples.findAll", query = "SELECT s FROM Samples s"),
    @NamedQuery(name = "Samples.findById", query = "SELECT s FROM Samples s WHERE s.id = :id"),
    @NamedQuery(name = "Samples.findByName", query = "SELECT s FROM Samples s WHERE s.name = :name"),
    @NamedQuery(name = "Samples.findByCreatedDate", query = "SELECT s FROM Samples s WHERE s.createdDate = :createdDate")})
public class Samples implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 7)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sampleId", orphanRemoval = true)
    private Collection<SamplesSequenceFiles> samplesSequenceFilesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sampleId", orphanRemoval = true)
    private Collection<ProjectSamples> projectSamplesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sampleId", orphanRemoval = true)
    private Collection<JobsCart> jobsCartCollection;
    @JoinColumn(name = "organism_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Organisms organismId;

    public Samples() {
    }

    public Samples(String id) {
        this.id = id;
    }

    public Samples(String id, String name, String strain, Date createdDate) {
        this.id = id;
        this.name = name;
        this.createdDate = createdDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @XmlTransient
    public Collection<SamplesSequenceFiles> getSamplesSequenceFilesCollection() {
        return samplesSequenceFilesCollection;
    }

    public void setSamplesSequenceFilesCollection(Collection<SamplesSequenceFiles> samplesSequenceFilesCollection) {
        this.samplesSequenceFilesCollection = samplesSequenceFilesCollection;
    }

    public Organisms getOrganismId() {
        return organismId;
    }

    public void setOrganismId(Organisms organismId) {
        this.organismId = organismId;
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
        return "com.nus.pgdb.entity.Samples[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<ProjectSamples> getProjectSamplesCollection() {
        return projectSamplesCollection;
    }

    public void setProjectSamplesCollection(Collection<ProjectSamples> projectSamplesCollection) {
        this.projectSamplesCollection = projectSamplesCollection;
    }

    @XmlTransient
    public Collection<JobsCart> getJobsCartCollection() {
        return jobsCartCollection;
    }

    public void setJobsCartCollection(Collection<JobsCart> jobsCartCollection) {
        this.jobsCartCollection = jobsCartCollection;
    }
}
