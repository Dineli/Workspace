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
import javax.persistence.Lob;
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
@Table(name = "users", catalog = "pgdb", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u"),
    @NamedQuery(name = "Users.findById", query = "SELECT u FROM Users u WHERE u.id = :id"),
    @NamedQuery(name = "Users.findByName", query = "SELECT u FROM Users u WHERE u.name = :name"),
    @NamedQuery(name = "Users.findByUserName", query = "SELECT u FROM Users u WHERE u.userName = :userName"),
    @NamedQuery(name = "Users.findByEncryptedPassword", query = "SELECT u FROM Users u WHERE u.encryptedPassword = :encryptedPassword"),
    @NamedQuery(name = "Users.findByIsAdmin", query = "SELECT u FROM Users u WHERE u.isAdmin = :isAdmin")})
public class Users implements Serializable {

    @Lob
    @Column(name = "encrypted_password")
    private byte[] encryptedPassword;
    @Lob
    @Column(name = "salt")
    private byte[] salt;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectOwnerId", orphanRemoval = true)
    private Collection<SharedUserProjects> sharedUserProjectsCollection;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sharedUserId")
//    private Collection<SharedUserProjects> sharedUserProjectsCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId", orphanRemoval = true)
    private Collection<UserProjects> userProjectsCollection;

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
    @Size(min = 1, max = 10)
    @Column(name = "user_name")
    private String userName;
    @Column(name = "is_admin")
    private Boolean isAdmin;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId", orphanRemoval = true)
    private Collection<PipelineJobs> pipelineJobsCollection;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "dummy_password")
    private String dummyPassword;
    @Column(name = "is_pw_reset")
    private Boolean isPwReset;

    public Users() {
    }

    public Users(Integer id) {
        this.id = id;
    }

    public Users(Integer id, String name, String userName, byte[] encryptedPassword) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.encryptedPassword = encryptedPassword;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(byte[] encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }


    public String getDummyPassword() {
        return dummyPassword;
    }

    public void setDummyPassword(String dummyPassword) {
        this.dummyPassword = dummyPassword;
    }

    public Boolean getIsPwReset() {
        return isPwReset;
    }

    public void setIsPwReset(Boolean isPwReset) {
        this.isPwReset = isPwReset;
    }

    @XmlTransient
    public Collection<PipelineJobs> getPipelineJobsCollection() {
        return pipelineJobsCollection;
    }

    public void setPipelineJobsCollection(Collection<PipelineJobs> pipelineJobsCollection) {
        this.pipelineJobsCollection = pipelineJobsCollection;
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
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nus.pgdb.entity.Users[ id=" + id + " ]";
    }


    @XmlTransient
    public Collection<UserProjects> getUserProjectsCollection() {
        return userProjectsCollection;
    }

    public void setUserProjectsCollection(Collection<UserProjects> userProjectsCollection) {
        this.userProjectsCollection = userProjectsCollection;
    }

   
    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    @XmlTransient
    public Collection<SharedUserProjects> getSharedUserProjectsCollection() {
        return sharedUserProjectsCollection;
    }

    public void setSharedUserProjectsCollection(Collection<SharedUserProjects> sharedUserProjectsCollection) {
        this.sharedUserProjectsCollection = sharedUserProjectsCollection;
    }

//    @XmlTransient
//    public Collection<SharedUserProjects> getSharedUserProjectsCollection1() {
//        return sharedUserProjectsCollection1;
//    }
//
//    public void setSharedUserProjectsCollection1(Collection<SharedUserProjects> sharedUserProjectsCollection1) {
//        this.sharedUserProjectsCollection1 = sharedUserProjectsCollection1;
//    }

}
