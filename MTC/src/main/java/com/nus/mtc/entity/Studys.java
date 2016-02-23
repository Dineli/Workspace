/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.mtc.entity;

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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author EPHAADK
 */
@Entity
@Table(name = "studys", catalog = "mtc_db", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Studys.findAll", query = "SELECT s FROM Studys s"),
    @NamedQuery(name = "Studys.findById", query = "SELECT s FROM Studys s WHERE s.id = :id"),
    @NamedQuery(name = "Studys.findByName", query = "SELECT s FROM Studys s WHERE s.name = :name"),
    @NamedQuery(name = "Studys.findByContactName", query = "SELECT s FROM Studys s WHERE s.contactName = :contactName"),
    @NamedQuery(name = "Studys.findByContactDetails", query = "SELECT s FROM Studys s WHERE s.contactDetails = :contactDetails"),
    @NamedQuery(name = "Studys.findByContactEmail", query = "SELECT s FROM Studys s WHERE s.contactEmail = :contactEmail")})
public class Studys implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 100)
    @Column(name = "name")
    private String name;
    @Size(max = 50)
    @Column(name = "contact_name")
    private String contactName;
    @Size(max = 100)
    @Column(name = "contact_details")
    private String contactDetails;
    @Size(max = 25)
    @Column(name = "contact_email")
    private String contactEmail;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "studyId")
//    private Collection<Samples> samplesCollection;

    public Studys() {
    }

    public Studys(Integer id) {
        this.id = id;
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

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(String contactDetails) {
        this.contactDetails = contactDetails;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

//    @XmlTransient
//    public Collection<Samples> getSamplesCollection() {
//        return samplesCollection;
//    }
//
//    public void setSamplesCollection(Collection<Samples> samplesCollection) {
//        this.samplesCollection = samplesCollection;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Studys)) {
            return false;
        }
        Studys other = (Studys) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nus.mtc.entity.Studys[ id=" + id + " ]";
    }
    
}
