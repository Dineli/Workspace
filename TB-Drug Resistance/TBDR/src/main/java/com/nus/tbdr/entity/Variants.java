/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.tbdr.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

/**
 *
 * @author Dineli
 */
@Entity
@Table(name = "variants", catalog = "tbdr", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Variants.findAll", query = "SELECT v FROM Variants v"),
    @NamedQuery(name = "Variants.findById", query = "SELECT v FROM Variants v WHERE v.id = :id"),
    @NamedQuery(name = "Variants.findByVarPositionGenomeStart", query = "SELECT v FROM Variants v WHERE v.varPositionGenomeStart = :varPositionGenomeStart"),
    @NamedQuery(name = "Variants.findByVarPositionGenomeStop", query = "SELECT v FROM Variants v WHERE v.varPositionGenomeStop = :varPositionGenomeStop"),
    @NamedQuery(name = "Variants.findByVarType", query = "SELECT v FROM Variants v WHERE v.varType = :varType"),
    @NamedQuery(name = "Variants.findByNumber", query = "SELECT v FROM Variants v WHERE v.number = :number"),
    @NamedQuery(name = "Variants.findByWtBase", query = "SELECT v FROM Variants v WHERE v.wtBase = :wtBase"),
    @NamedQuery(name = "Variants.findByVarBase", query = "SELECT v FROM Variants v WHERE v.varBase = :varBase"),
    @NamedQuery(name = "Variants.findByRegion", query = "SELECT v FROM Variants v WHERE v.region = :region"),
    @NamedQuery(name = "Variants.findByGeneId", query = "SELECT v FROM Variants v WHERE v.geneId = :geneId"),
    @NamedQuery(name = "Variants.findByGeneName", query = "SELECT v FROM Variants v WHERE v.geneName = :geneName"),
    @NamedQuery(name = "Variants.findByGeneStart", query = "SELECT v FROM Variants v WHERE v.geneStart = :geneStart"),
    @NamedQuery(name = "Variants.findByGeneStop", query = "SELECT v FROM Variants v WHERE v.geneStop = :geneStop"),
    @NamedQuery(name = "Variants.findByGeneLength", query = "SELECT v FROM Variants v WHERE v.geneLength = :geneLength"),
    @NamedQuery(name = "Variants.findByDir", query = "SELECT v FROM Variants v WHERE v.dir = :dir"),
    @NamedQuery(name = "Variants.findByWtAa", query = "SELECT v FROM Variants v WHERE v.wtAa = :wtAa"),
    @NamedQuery(name = "Variants.findByCodonNr", query = "SELECT v FROM Variants v WHERE v.codonNr = :codonNr"),
    @NamedQuery(name = "Variants.findByCodonNrEColi", query = "SELECT v FROM Variants v WHERE v.codonNrEColi = :codonNrEColi"),
    @NamedQuery(name = "Variants.findByVarAa", query = "SELECT v FROM Variants v WHERE v.varAa = :varAa"),
    @NamedQuery(name = "Variants.findByAaChange", query = "SELECT v FROM Variants v WHERE v.aaChange = :aaChange"),
    @NamedQuery(name = "Variants.findByCodonChange", query = "SELECT v FROM Variants v WHERE v.codonChange = :codonChange"),
    @NamedQuery(name = "Variants.findByVarPositionGeneStart", query = "SELECT v FROM Variants v WHERE v.varPositionGeneStart = :varPositionGeneStart"),
    @NamedQuery(name = "Variants.findByVarPositionGeneStop", query = "SELECT v FROM Variants v WHERE v.varPositionGeneStop = :varPositionGeneStop"),
    @NamedQuery(name = "Variants.findByRemarks", query = "SELECT v FROM Variants v WHERE v.remarks = :remarks")})
public class Variants implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "var_position_genome_start")
    private Long varPositionGenomeStart;
    @Basic(optional = false)
    @NotNull
    @Column(name = "var_position_genome_stop")
    private Long varPositionGenomeStop;
    @Size(max = 5)
    @Column(name = "var_type")
    private String varType;
    @Column(name = "number")
    private Long number;
    @Size(max = 6)
    @Column(name = "wt_base")
    private String wtBase;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "var_base")
    private String varBase;
    @Size(max = 15)
    @Column(name = "region")
    private String region;
    @Size(max = 15)
    @Column(name = "gene_id")
    private String geneId;
    @Size(max = 15)
    @Column(name = "gene_name")
    private String geneName;
    @Column(name = "gene_start")
    private Long geneStart;
    @Column(name = "gene_stop")
    private Long geneStop;
    @Column(name = "gene_length")
    private Long geneLength;
    @Size(max = 3)
    @Column(name = "dir")
    private String dir;
    @Size(max = 5)
    @Column(name = "wt_aa")
    private String wtAa;
    @Column(name = "codon_nr")
    private Long codonNr;
    @Column(name = "codon_nr_e_coli")
    private Long codonNrEColi;
    @Size(max = 10)
    @Column(name = "var_aa")
    private String varAa;
    @Size(max = 20)
    @Column(name = "aa_change")
    private String aaChange;
    @Size(max = 15)
    @Column(name = "codon_change")
    private String codonChange;
    @Column(name = "var_position_gene_start")
    private Long varPositionGeneStart;
    @Column(name = "var_position_gene_stop")
    private Long varPositionGeneStop;
    @Size(max = 50)
    @Column(name = "remarks")
    private String remarks;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "variantId", cascade = CascadeType.ALL)
    private Set<DrugResistance> drugResistanceList = new HashSet<>();

    public Variants() {
    }

    public Variants(Integer id) {
        this.id = id;
    }

    public Variants(Integer id, Long varPositionGenomeStart, Long varPositionGenomeStop, String varBase) {
        this.id = id;
        this.varPositionGenomeStart = varPositionGenomeStart;
        this.varPositionGenomeStop = varPositionGenomeStop;
        this.varBase = varBase;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getVarPositionGenomeStart() {
        return varPositionGenomeStart;
    }

    public void setVarPositionGenomeStart(Long varPositionGenomeStart) {
        this.varPositionGenomeStart = varPositionGenomeStart;
    }

    public Long getVarPositionGenomeStop() {
        return varPositionGenomeStop;
    }

    public void setVarPositionGenomeStop(Long varPositionGenomeStop) {
        this.varPositionGenomeStop = varPositionGenomeStop;
    }

    public String getVarType() {
        return varType;
    }

    public void setVarType(String varType) {
        this.varType = varType;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getWtBase() {
        return wtBase;
    }

    public void setWtBase(String wtBase) {
        this.wtBase = wtBase;
    }

    public String getVarBase() {
        return varBase;
    }

    public void setVarBase(String varBase) {
        this.varBase = varBase;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getGeneId() {
        return geneId;
    }

    public void setGeneId(String geneId) {
        this.geneId = geneId;
    }

    public String getGeneName() {
        return geneName;
    }

    public void setGeneName(String geneName) {
        this.geneName = geneName;
    }

    public Long getGeneStart() {
        return geneStart;
    }

    public void setGeneStart(Long geneStart) {
        this.geneStart = geneStart;
    }

    public Long getGeneStop() {
        return geneStop;
    }

    public void setGeneStop(Long geneStop) {
        this.geneStop = geneStop;
    }

    public Long getGeneLength() {
        return geneLength;
    }

    public void setGeneLength(Long geneLength) {
        this.geneLength = geneLength;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getWtAa() {
        return wtAa;
    }

    public void setWtAa(String wtAa) {
        this.wtAa = wtAa;
    }

    public Long getCodonNr() {
        return codonNr;
    }

    public void setCodonNr(Long codonNr) {
        this.codonNr = codonNr;
    }

    public Long getCodonNrEColi() {
        return codonNrEColi;
    }

    public void setCodonNrEColi(Long codonNrEColi) {
        this.codonNrEColi = codonNrEColi;
    }

    public String getVarAa() {
        return varAa;
    }

    public void setVarAa(String varAa) {
        this.varAa = varAa;
    }

    public String getAaChange() {
        return aaChange;
    }

    public void setAaChange(String aaChange) {
        this.aaChange = aaChange;
    }

    public String getCodonChange() {
        return codonChange;
    }

    public void setCodonChange(String codonChange) {
        this.codonChange = codonChange;
    }

    public Long getVarPositionGeneStart() {
        return varPositionGeneStart;
    }

    public void setVarPositionGeneStart(Long varPositionGeneStart) {
        this.varPositionGeneStart = varPositionGeneStart;
    }

    public Long getVarPositionGeneStop() {
        return varPositionGeneStop;
    }

    public void setVarPositionGeneStop(Long varPositionGeneStop) {
        this.varPositionGeneStop = varPositionGeneStop;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Set<DrugResistance> getDrugResistanceList() {
        return drugResistanceList;
    }

    public void setDrugResistanceList(Set<DrugResistance> drugResistanceList) {
        this.drugResistanceList = drugResistanceList;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((varPositionGenomeStart == null) ? 0 : varPositionGenomeStart.hashCode());
        result = prime * result + ((varPositionGenomeStop == null) ? 0 : varPositionGenomeStop.hashCode());
        result = prime * result + ((varBase == null) ? 0 : varBase.hashCode());
        return result;
    }

    /* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Variants)) {
            return false;
        }
        Variants other = (Variants) obj;
        if (varBase == null) {
            if (other.varBase != null) {
                return false;
            }
        } else if (!varBase.equals(other.varBase)) {
            return false;
        }
        if (varPositionGenomeStart == null) {
            if (other.varPositionGenomeStart != null) {
                return false;
            }
        } else if (!varPositionGenomeStart.equals(other.varPositionGenomeStart)) {
            return false;
        }
        if (varPositionGenomeStop == null) {
            if (other.varPositionGenomeStop != null) {
                return false;
            }
        } else if (!varPositionGenomeStop.equals(other.varPositionGenomeStop)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nus.tbdr.entity.Variants[ id=" + id + " ]";
    }

}
