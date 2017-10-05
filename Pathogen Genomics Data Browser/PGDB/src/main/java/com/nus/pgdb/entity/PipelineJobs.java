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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "pipeline_jobs", catalog = "pgdb", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PipelineJobs.findAllByUserId", query = "SELECT p FROM PipelineJobs p WHERE p.userId.id = :userId ORDER BY p.createdDate DESC"),
    @NamedQuery(name = "PipelineJobs.findActiveJobsByUserId", query = "SELECT p FROM PipelineJobs p WHERE p.userId.id = :userId AND p.status NOT IN (3, -1)"),
    @NamedQuery(name = "PipelineJobs.findById", query = "SELECT p FROM PipelineJobs p WHERE p.id = :id"),
    @NamedQuery(name = "PipelineJobs.findByName", query = "SELECT p FROM PipelineJobs p WHERE p.name = :name"),
    @NamedQuery(name = "PipelineJobs.findByType", query = "SELECT p FROM PipelineJobs p WHERE p.type = :type"),
    @NamedQuery(name = "PipelineJobs.findByFileName", query = "SELECT p FROM PipelineJobs p WHERE p.fileName = :fileName"),
    @NamedQuery(name = "PipelineJobs.findByFilePath", query = "SELECT p FROM PipelineJobs p WHERE p.filePath = :filePath"),
    @NamedQuery(name = "PipelineJobs.findByStatus", query = "SELECT p FROM PipelineJobs p WHERE p.status = :status"),
    @NamedQuery(name = "PipelineJobs.findByCreatedDate", query = "SELECT p FROM PipelineJobs p WHERE p.createdDate = :createdDate")})
public class PipelineJobs implements Serializable {

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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "type")
    private String type;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "file_name")
    private String fileName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "file_path")
    private String filePath;
    @Size(max = 15)
    @Column(name = "status")
    private String status;
    @Basic(optional = false)
    @NotNull
    @Column(name = "input_path")
    private String inputPath;
    @Basic(optional = false)
    @NotNull
    @Column(name = "output_path")
    private String outputPath;
    @Basic(optional = false)
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Users userId;

    public PipelineJobs() {
    }

    public PipelineJobs(Integer id) {
        this.id = id;
    }

    public PipelineJobs(Integer id, String name, String type, String fileName, String filePath, Date createdDate) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.fileName = fileName;
        this.filePath = filePath;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getInputPath() {
        return inputPath;
    }

    public void setInputPath(String inputPath) {
        this.inputPath = inputPath;
    }
    
    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
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
        if (!(object instanceof PipelineJobs)) {
            return false;
        }
        PipelineJobs other = (PipelineJobs) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nus.pgdb.entity.PipelineJobs[ id=" + id + " ]";
    }
    
}
