/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.pgdb.entity;

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
@Table(name = "projects", catalog = "pgdb", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Projects.findAll", query = "SELECT p FROM Projects p"),
    @NamedQuery(name = "Projects.findById", query = "SELECT p FROM Projects p WHERE p.id = :id"),
    @NamedQuery(name = "Projects.findByName", query = "SELECT p FROM Projects p WHERE p.name = :name"),
    @NamedQuery(name = "Projects.findByDescription", query = "SELECT p FROM Projects p WHERE p.description = :description")})
public class Projects implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sharedProjectId", orphanRemoval = true)
    private Collection<SharedUserProjects> sharedUserProjectsCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectId", orphanRemoval = true)
    private Collection<UserProjects> userProjectsCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectId", orphanRemoval = true)
    private Collection<ProjectSamples> projectSamplesCollection;

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
    @Size(max = 500)
    @Column(name = "description")
    private String description;

    public Projects() {
    }

    public Projects(Integer id) {
        this.id = id;
    }

    public Projects(Integer id, String name) {
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
        if (!(object instanceof Projects)) {
            return false;
        }
        Projects other = (Projects) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nus.pgdb.entity.Projects[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<ProjectSamples> getProjectSamplesCollection() {
        return projectSamplesCollection;
    }

    public void setProjectSamplesCollection(Collection<ProjectSamples> projectSamplesCollection) {
        this.projectSamplesCollection = projectSamplesCollection;
    }

    @XmlTransient
    public Collection<UserProjects> getUserProjectsCollection() {
        return userProjectsCollection;
    }

    public void setUserProjectsCollection(Collection<UserProjects> userProjectsCollection) {
        this.userProjectsCollection = userProjectsCollection;
    }

    @XmlTransient
    public Collection<SharedUserProjects> getSharedUserProjectsCollection() {
        return sharedUserProjectsCollection;
    }

    public void setSharedUserProjectsCollection(Collection<SharedUserProjects> sharedUserProjectsCollection) {
        this.sharedUserProjectsCollection = sharedUserProjectsCollection;
    }
    
}
