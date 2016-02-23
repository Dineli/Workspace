/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.mtc.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
 * @author EPHAADK
 */
@Entity
@Table(name = "locations", catalog = "mtc_db", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Locations.findAll", query = "SELECT l FROM Locations l"),
    @NamedQuery(name = "Locations.findById", query = "SELECT l FROM Locations l WHERE l.id = :id"),
    @NamedQuery(name = "Locations.findByCity", query = "SELECT l FROM Locations l WHERE l.city = :city"),
    @NamedQuery(name = "Locations.findByLongitude", query = "SELECT l FROM Locations l WHERE l.longitude = :longitude"),
    @NamedQuery(name = "Locations.findByLatitude", query = "SELECT l FROM Locations l WHERE l.latitude = :latitude")})
public class Locations implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 25)
    @Column(name = "city")
    private String city;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "longitude")
    private BigDecimal longitude;
    @Basic(optional = false)
    @NotNull
    @Column(name = "latitude")
    private BigDecimal latitude;
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Countrys countryId;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "locationId")
//    private Collection<Samples> samplesCollection;

    public Locations() {
    }

    public Locations(Integer id) {
        this.id = id;
    }

    public Locations(Integer id, BigDecimal longitude, BigDecimal latitude) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public Countrys getCountryId() {
        return countryId;
    }

    public void setCountryId(Countrys countryId) {
        this.countryId = countryId;
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
        if (!(object instanceof Locations)) {
            return false;
        }
        Locations other = (Locations) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nus.mtc.entity.Locations[ id=" + id + " ]";
    }

}
