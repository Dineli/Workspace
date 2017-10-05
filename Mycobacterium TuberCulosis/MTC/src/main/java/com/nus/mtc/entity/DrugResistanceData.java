/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.mtc.entity;

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
import javax.validation.constraints.Size;

/**
 *
 * @author Dineli
 */
@Entity
@Table(name = "drug_resistance_data")
@NamedQueries({
    @NamedQuery(name = "DrugResistanceData.findAll", query = "SELECT d FROM DrugResistanceData d"),
    @NamedQuery(name = "DrugResistanceData.findById", query = "SELECT d FROM DrugResistanceData d WHERE d.id = :id"),
    @NamedQuery(name = "DrugResistanceData.findByDescription", query = "SELECT d FROM DrugResistanceData d WHERE d.description = :description"),
    @NamedQuery(name = "DrugResistanceData.findByStartChrCoord", query = "SELECT d FROM DrugResistanceData d WHERE d.startChrCoord = :startChrCoord"),
    @NamedQuery(name = "DrugResistanceData.findByStopChrCoord", query = "SELECT d FROM DrugResistanceData d WHERE d.stopChrCoord = :stopChrCoord"),
    @NamedQuery(name = "DrugResistanceData.findByStartGeneCoord", query = "SELECT d FROM DrugResistanceData d WHERE d.startGeneCoord = :startGeneCoord"),
    @NamedQuery(name = "DrugResistanceData.findByStopGeneCoord", query = "SELECT d FROM DrugResistanceData d WHERE d.stopGeneCoord = :stopGeneCoord"),
    @NamedQuery(name = "DrugResistanceData.findByNucleotideRef", query = "SELECT d FROM DrugResistanceData d WHERE d.nucleotideRef = :nucleotideRef"),
    @NamedQuery(name = "DrugResistanceData.findByNucleotideAlt", query = "SELECT d FROM DrugResistanceData d WHERE d.nucleotideAlt = :nucleotideAlt"),
    @NamedQuery(name = "DrugResistanceData.findByStartCodonNo", query = "SELECT d FROM DrugResistanceData d WHERE d.startCodonNo = :startCodonNo"),
    @NamedQuery(name = "DrugResistanceData.findByStopCodonNo", query = "SELECT d FROM DrugResistanceData d WHERE d.stopCodonNo = :stopCodonNo"),
    @NamedQuery(name = "DrugResistanceData.findByCodonRef", query = "SELECT d FROM DrugResistanceData d WHERE d.codonRef = :codonRef"),
    @NamedQuery(name = "DrugResistanceData.findByCodonAlt", query = "SELECT d FROM DrugResistanceData d WHERE d.codonAlt = :codonAlt"),
    @NamedQuery(name = "DrugResistanceData.findByAminoRef", query = "SELECT d FROM DrugResistanceData d WHERE d.aminoRef = :aminoRef"),
    @NamedQuery(name = "DrugResistanceData.findByAminoAlt", query = "SELECT d FROM DrugResistanceData d WHERE d.aminoAlt = :aminoAlt"),
    @NamedQuery(name = "DrugResistanceData.findByTbdreamdb", query = "SELECT d FROM DrugResistanceData d WHERE d.tbdreamdb = :tbdreamdb"),
    @NamedQuery(name = "DrugResistanceData.findByTbproflr", query = "SELECT d FROM DrugResistanceData d WHERE d.tbproflr = :tbproflr"),
    @NamedQuery(name = "DrugResistanceData.findByKvarq", query = "SELECT d FROM DrugResistanceData d WHERE d.kvarq = :kvarq"),
    @NamedQuery(name = "DrugResistanceData.findByHc", query = "SELECT d FROM DrugResistanceData d WHERE d.hc = :hc"),
    @NamedQuery(name = "DrugResistanceData.findByIsValidated", query = "SELECT d FROM DrugResistanceData d WHERE d.isValidated = :isValidated"),
    @NamedQuery(name = "DrugResistanceData.findByReferences", query = "SELECT d FROM DrugResistanceData d WHERE d.references = :references"),
    @NamedQuery(name = "DrugResistanceData.findByDrugName", query = "SELECT dr FROM DrugResistanceData dr JOIN dr.drugId d WHERE d.drugAbbreviation = :drugAbbreviation"),
    @NamedQuery(name = "DrugResistanceData.findByLocusName", query = "SELECT dr FROM DrugResistanceData dr JOIN dr.tbGeneticLocusId tb WHERE tb.locusName = :locusName")
})
public class DrugResistanceData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 120)
    @Column(name = "description")
    private String description;
    @Column(name = "start_chr_coord")
    private Integer startChrCoord;
    @Column(name = "stop_chr_coord")
    private Integer stopChrCoord;
    @Column(name = "start_gene_coord")
    private Integer startGeneCoord;
    @Column(name = "stop_gene_coord")
    private Integer stopGeneCoord;
    @Size(max = 4)
    @Column(name = "nucleotide_ref")
    private String nucleotideRef;
    @Size(max = 5)
    @Column(name = "nucleotide_alt")
    private String nucleotideAlt;
    @Column(name = "start_codon_no")
    private Integer startCodonNo;
    @Column(name = "stop_codon_no")
    private Integer stopCodonNo;
    @Size(max = 3)
    @Column(name = "codon_ref")
    private String codonRef;
    @Size(max = 11)
    @Column(name = "codon_alt")
    private String codonAlt;
    @Size(max = 5)
    @Column(name = "amino_ref")
    private String aminoRef;
    @Size(max = 5)
    @Column(name = "amino_alt")
    private String aminoAlt;
    @Column(name = "tbdreamdb")
    private Boolean tbdreamdb;
    @Column(name = "tbproflr")
    private Boolean tbproflr;
    @Column(name = "kvarq")
    private Boolean kvarq;
    @Column(name = "hc")
    private Boolean hc;
    @Column(name = "isValidated")
    private Boolean isValidated;
    @Size(max = 350)
    @Column(name = "references")
    private String references;
    @JoinColumn(name = "tb_genetic_locus_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TbGeneticLocus tbGeneticLocusId;
    @JoinColumn(name = "drug_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Drugs drugId;

    public DrugResistanceData() {
    }

    public DrugResistanceData(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStartChrCoord() {
        return startChrCoord;
    }

    public void setStartChrCoord(Integer startChrCoord) {
        this.startChrCoord = startChrCoord;
    }

    public Integer getStopChrCoord() {
        return stopChrCoord;
    }

    public void setStopChrCoord(Integer stopChrCoord) {
        this.stopChrCoord = stopChrCoord;
    }

    public Integer getStartGeneCoord() {
        return startGeneCoord;
    }

    public void setStartGeneCoord(Integer startGeneCoord) {
        this.startGeneCoord = startGeneCoord;
    }

    public Integer getStopGeneCoord() {
        return stopGeneCoord;
    }

    public void setStopGeneCoord(Integer stopGeneCoord) {
        this.stopGeneCoord = stopGeneCoord;
    }

    public String getNucleotideRef() {
        return nucleotideRef;
    }

    public void setNucleotideRef(String nucleotideRef) {
        this.nucleotideRef = nucleotideRef;
    }

    public String getNucleotideAlt() {
        return nucleotideAlt;
    }

    public void setNucleotideAlt(String nucleotideAlt) {
        this.nucleotideAlt = nucleotideAlt;
    }

    public Integer getStartCodonNo() {
        return startCodonNo;
    }

    public void setStartCodonNo(Integer startCodonNo) {
        this.startCodonNo = startCodonNo;
    }

    public Integer getStopCodonNo() {
        return stopCodonNo;
    }

    public void setStopCodonNo(Integer stopCodonNo) {
        this.stopCodonNo = stopCodonNo;
    }

    public String getCodonRef() {
        return codonRef;
    }

    public void setCodonRef(String codonRef) {
        this.codonRef = codonRef;
    }

    public String getCodonAlt() {
        return codonAlt;
    }

    public void setCodonAlt(String codonAlt) {
        this.codonAlt = codonAlt;
    }

    public String getAminoRef() {
        return aminoRef;
    }

    public void setAminoRef(String aminoRef) {
        this.aminoRef = aminoRef;
    }

    public String getAminoAlt() {
        return aminoAlt;
    }

    public void setAminoAlt(String aminoAlt) {
        this.aminoAlt = aminoAlt;
    }

    public Boolean getTbdreamdb() {
        return tbdreamdb;
    }

    public void setTbdreamdb(Boolean tbdreamdb) {
        this.tbdreamdb = tbdreamdb;
    }

    public Boolean getTbproflr() {
        return tbproflr;
    }

    public void setTbproflr(Boolean tbproflr) {
        this.tbproflr = tbproflr;
    }

    public Boolean getKvarq() {
        return kvarq;
    }

    public void setKvarq(Boolean kvarq) {
        this.kvarq = kvarq;
    }

    public Boolean getHc() {
        return hc;
    }

    public void setHc(Boolean hc) {
        this.hc = hc;
    }

    public Boolean getIsValidated() {
        return isValidated;
    }

    public void setIsValidated(Boolean isValidated) {
        this.isValidated = isValidated;
    }

    public String getReferences() {
        return references;
    }

    public void setReferences(String references) {
        this.references = references;
    }

    public TbGeneticLocus getTbGeneticLocusId() {
        return tbGeneticLocusId;
    }

    public void setTbGeneticLocusId(TbGeneticLocus tbGeneticLocusId) {
        this.tbGeneticLocusId = tbGeneticLocusId;
    }

    public Drugs getDrugId() {
        return drugId;
    }

    public void setDrugId(Drugs drugId) {
        this.drugId = drugId;
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
        if (!(object instanceof DrugResistanceData)) {
            return false;
        }
        DrugResistanceData other = (DrugResistanceData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nus.mtc.entity.DrugResistanceData[ id=" + id + " ]";
    }
    
}
