/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dreamhome.Negocio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author cetecom
 */
@Entity
@Table(name = "PROPIETARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Propietario.findAll", query = "SELECT p FROM Propietario p"),
    @NamedQuery(name = "Propietario.findByIdPropiet1", query = "SELECT p FROM Propietario p WHERE p.idPropiet1 = :idPropiet1"),
    @NamedQuery(name = "Propietario.findByDiraccion", query = "SELECT p FROM Propietario p WHERE p.diraccion = :diraccion"),
    @NamedQuery(name = "Propietario.findByTelefono", query = "SELECT p FROM Propietario p WHERE p.telefono = :telefono")})
public class Propietario implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PROPIET1")
    private BigDecimal idPropiet1;
    @Column(name = "DIRACCION")
    private String diraccion;
    @Column(name = "TELEFONO")
    private BigInteger telefono;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "propietarioIdPropiet1")
    private Collection<Inmueble> inmuebleCollection;
    @JoinColumn(name = "EMPRESARIAL_EMPRESARIAL_ID", referencedColumnName = "EMPRESARIAL_ID")
    @OneToOne(optional = false)
    private Empresarial empresarialEmpresarialId;
    @JoinColumn(name = "PRIVADO_ID_PROPIET", referencedColumnName = "ID_PROPIET")
    @OneToOne(optional = false)
    private Privado privadoIdPropiet;

    public Propietario() {
    }

    public Propietario(BigDecimal idPropiet1) {
        this.idPropiet1 = idPropiet1;
    }

    public BigDecimal getIdPropiet1() {
        return idPropiet1;
    }

    public void setIdPropiet1(BigDecimal idPropiet1) {
        this.idPropiet1 = idPropiet1;
    }

    public String getDiraccion() {
        return diraccion;
    }

    public void setDiraccion(String diraccion) {
        this.diraccion = diraccion;
    }

    public BigInteger getTelefono() {
        return telefono;
    }

    public void setTelefono(BigInteger telefono) {
        this.telefono = telefono;
    }

    @XmlTransient
    public Collection<Inmueble> getInmuebleCollection() {
        return inmuebleCollection;
    }

    public void setInmuebleCollection(Collection<Inmueble> inmuebleCollection) {
        this.inmuebleCollection = inmuebleCollection;
    }

    public Empresarial getEmpresarialEmpresarialId() {
        return empresarialEmpresarialId;
    }

    public void setEmpresarialEmpresarialId(Empresarial empresarialEmpresarialId) {
        this.empresarialEmpresarialId = empresarialEmpresarialId;
    }

    public Privado getPrivadoIdPropiet() {
        return privadoIdPropiet;
    }

    public void setPrivadoIdPropiet(Privado privadoIdPropiet) {
        this.privadoIdPropiet = privadoIdPropiet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPropiet1 != null ? idPropiet1.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Propietario)) {
            return false;
        }
        Propietario other = (Propietario) object;
        if ((this.idPropiet1 == null && other.idPropiet1 != null) || (this.idPropiet1 != null && !this.idPropiet1.equals(other.idPropiet1))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Dreamhome.Negocio.Propietario[ idPropiet1=" + idPropiet1 + " ]";
    }
    
}
