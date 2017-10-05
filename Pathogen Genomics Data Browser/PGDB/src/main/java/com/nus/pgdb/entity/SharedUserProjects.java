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
@Table(name = "shared_user_projects", catalog = "pgdb", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SharedUserProjects.findAll", query = "SELECT s FROM SharedUserProjects s"), 
    @NamedQuery(name = "SharedUserProjects.findById", query = "SELECT s FROM SharedUserProjects s WHERE s.id = :id")})
public class SharedUserProjects implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "shared_project_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Projects sharedProjectId;
    @JoinColumn(name = "project_owner_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Users projectOwnerId;
    @JoinColumn(name = "shared_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Users sharedUserId;

    public SharedUserProjects() {
    }

    public SharedUserProjects(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Projects getSharedProjectId() {
        return sharedProjectId;
    }

    public void setSharedProjectId(Projects sharedProjectId) {
        this.sharedProjectId = sharedProjectId;
    }

    public Users getProjectOwnerId() {
        return projectOwnerId;
    }

    public void setProjectOwnerId(Users projectOwnerId) {
        this.projectOwnerId = projectOwnerId;
    }

    public Users getSharedUserId() {
        return sharedUserId;
    }

    public void setSharedUserId(Users sharedUserId) {
        this.sharedUserId = sharedUserId;
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
        if (!(object instanceof SharedUserProjects)) {
            return false;
        }
        SharedUserProjects other = (SharedUserProjects) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nus.pgdb.entity.SharedUserProjects[ id=" + id + " ]";
    }

}
