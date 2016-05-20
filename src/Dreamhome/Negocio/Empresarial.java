/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dreamhome.Negocio;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cetecom
 */
@Entity
@Table(name = "EMPRESARIAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Empresarial.findAll", query = "SELECT e FROM Empresarial e"),
    @NamedQuery(name = "Empresarial.findByEmpresarialId", query = "SELECT e FROM Empresarial e WHERE e.empresarialId = :empresarialId"),
    @NamedQuery(name = "Empresarial.findByTipoEmpres", query = "SELECT e FROM Empresarial e WHERE e.tipoEmpres = :tipoEmpres"),
    @NamedQuery(name = "Empresarial.findByNomContact", query = "SELECT e FROM Empresarial e WHERE e.nomContact = :nomContact")})
public class Empresarial implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "EMPRESARIAL_ID")
    private BigDecimal empresarialId;
    @Column(name = "TIPO_EMPRES")
    private String tipoEmpres;
    @Column(name = "NOM_CONTACT")
    private String nomContact;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "empresarialEmpresarialId")
    private Propietario propietario;

    public Empresarial() {
    }

    public Empresarial(BigDecimal empresarialId) {
        this.empresarialId = empresarialId;
    }

    public BigDecimal getEmpresarialId() {
        return empresarialId;
    }

    public void setEmpresarialId(BigDecimal empresarialId) {
        this.empresarialId = empresarialId;
    }

    public String getTipoEmpres() {
        return tipoEmpres;
    }

    public void setTipoEmpres(String tipoEmpres) {
        this.tipoEmpres = tipoEmpres;
    }

    public String getNomContact() {
        return nomContact;
    }

    public void setNomContact(String nomContact) {
        this.nomContact = nomContact;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (empresarialId != null ? empresarialId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empresarial)) {
            return false;
        }
        Empresarial other = (Empresarial) object;
        if ((this.empresarialId == null && other.empresarialId != null) || (this.empresarialId != null && !this.empresarialId.equals(other.empresarialId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Dreamhome.Negocio.Empresarial[ empresarialId=" + empresarialId + " ]";
    }
    
}
