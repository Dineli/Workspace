/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.pgdb.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Dineli
 */
@Entity
@Table(name = "pipeline_jobs_history", catalog = "pgdb", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PipelineJobsHistory.findAll", query = "SELECT p FROM PipelineJobsHistory p"),
    @NamedQuery(name = "PipelineJobsHistory.findById", query = "SELECT p FROM PipelineJobsHistory p WHERE p.id = :id"),
    @NamedQuery(name = "PipelineJobsHistory.findByName", query = "SELECT p FROM PipelineJobsHistory p WHERE p.name = :name"),
    @NamedQuery(name = "PipelineJobsHistory.findByType", query = "SELECT p FROM PipelineJobsHistory p WHERE p.type = :type"),
    @NamedQuery(name = "PipelineJobsHistory.findByCreatedDate", query = "SELECT p FROM PipelineJobsHistory p WHERE p.createdDate = :createdDate")})
public class PipelineJobsHistory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "type")
    private String type;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    public PipelineJobsHistory() {
    }

    public PipelineJobsHistory(Integer id) {
        this.id = id;
    }

    public PipelineJobsHistory(Integer id, String name, String type, Date createdDate) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.createdDate = createdDate;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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
        if (!(object instanceof PipelineJobsHistory)) {
            return false;
        }
        PipelineJobsHistory other = (PipelineJobsHistory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nus.pgdb.entity.PipelineJobsHistory[ id=" + id + " ]";
    }
    
}
