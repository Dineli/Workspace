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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "sequence_files", catalog = "pgdb", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SequenceFiles.findAll", query = "SELECT s FROM SequenceFiles s"),
    @NamedQuery(name = "SequenceFiles.findById", query = "SELECT s FROM SequenceFiles s WHERE s.id = :id"),
    @NamedQuery(name = "SequenceFiles.findByFilePath", query = "SELECT s FROM SequenceFiles s WHERE s.filePath = :filePath"),
    @NamedQuery(name = "SequenceFiles.findByCreatedDate", query = "SELECT s FROM SequenceFiles s WHERE s.createdDate = :createdDate")})
public class SequenceFiles implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "file_path")
    private String filePath;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sequenceFileId", orphanRemoval = true)
    private Collection<SamplesSequenceFiles> samplesSequenceFilesCollection;

    public SequenceFiles() {
    }

    public SequenceFiles(Integer id) {
        this.id = id;
    }

    public SequenceFiles(Integer id, String filePath, Date createdDate) {
        this.id = id;
        this.filePath = filePath;
        this.createdDate = createdDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SequenceFiles)) {
            return false;
        }
        SequenceFiles other = (SequenceFiles) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nus.pgdb.entity.SequenceFiles[ id=" + id + " ]";
    }
    
}
