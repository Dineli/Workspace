/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.pgdb.entity;

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
@Table(name = "project_samples", catalog = "pgdb", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProjectSamples.findAll", query = "SELECT p FROM ProjectSamples p"),
    @NamedQuery(name = "ProjectSamples.findById", query = "SELECT p FROM ProjectSamples p WHERE p.id = :id")})
public class ProjectSamples implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "sample_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Samples sampleId;
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Projects projectId;

    public ProjectSamples() {
    }

    public ProjectSamples(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Samples getSampleId() {
        return sampleId;
    }

    public void setSampleId(Samples sampleId) {
        this.sampleId = sampleId;
    }

    public Projects getProjectId() {
        return projectId;
    }

    public void setProjectId(Projects projectId) {
        this.projectId = projectId;
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
        if (!(object instanceof ProjectSamples)) {
            return false;
        }
        ProjectSamples other = (ProjectSamples) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nus.pgdb.entity.ProjectSamples[ id=" + id + " ]";
    }
    
}
