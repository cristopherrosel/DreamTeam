/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dreamhome.Negocio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "INMUEBLE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Inmueble.findAll", query = "SELECT i FROM Inmueble i"),
    @NamedQuery(name = "Inmueble.findByIdInmueble", query = "SELECT i FROM Inmueble i WHERE i.idInmueble = :idInmueble"),
    @NamedQuery(name = "Inmueble.findByNumInmueble", query = "SELECT i FROM Inmueble i WHERE i.numInmueble = :numInmueble"),
    @NamedQuery(name = "Inmueble.findByCalle", query = "SELECT i FROM Inmueble i WHERE i.calle = :calle"),
    @NamedQuery(name = "Inmueble.findByCodPost", query = "SELECT i FROM Inmueble i WHERE i.codPost = :codPost"),
    @NamedQuery(name = "Inmueble.findByTipo", query = "SELECT i FROM Inmueble i WHERE i.tipo = :tipo"),
    @NamedQuery(name = "Inmueble.findByNumHabit", query = "SELECT i FROM Inmueble i WHERE i.numHabit = :numHabit"),
    @NamedQuery(name = "Inmueble.findByValorArriendo", query = "SELECT i FROM Inmueble i WHERE i.valorArriendo = :valorArriendo"),
    @NamedQuery(name = "Inmueble.findByIdPriedad", query = "SELECT i FROM Inmueble i WHERE i.idPriedad = :idPriedad")})
public class Inmueble implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_INMUEBLE")
    private BigDecimal idInmueble;
    @Column(name = "NUM_INMUEBLE")
    private BigInteger numInmueble;
    @Column(name = "CALLE")
    private String calle;
    @Column(name = "COD_POST")
    private BigInteger codPost;
    @Column(name = "TIPO")
    private String tipo;
    @Column(name = "NUM_HABIT")
    private BigInteger numHabit;
    @Column(name = "VALOR_ARRIENDO")
    private BigInteger valorArriendo;
    @Column(name = "ID_PRIEDAD")
    private BigInteger idPriedad;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "inmuebleIdInmueble")
    private Anuncio anuncio;
    @JoinColumn(name = "PROPIETARIO_ID_PROPIET1", referencedColumnName = "ID_PROPIET1")
    @ManyToOne(optional = false)
    private Propietario propietarioIdPropiet1;

    public Inmueble() {
    }

    public Inmueble(BigDecimal idInmueble) {
        this.idInmueble = idInmueble;
    }

    public BigDecimal getIdInmueble() {
        return idInmueble;
    }

    public void setIdInmueble(BigDecimal idInmueble) {
        this.idInmueble = idInmueble;
    }

    public BigInteger getNumInmueble() {
        return numInmueble;
    }

    public void setNumInmueble(BigInteger numInmueble) {
        this.numInmueble = numInmueble;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public BigInteger getCodPost() {
        return codPost;
    }

    public void setCodPost(BigInteger codPost) {
        this.codPost = codPost;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigInteger getNumHabit() {
        return numHabit;
    }

    public void setNumHabit(BigInteger numHabit) {
        this.numHabit = numHabit;
    }

    public BigInteger getValorArriendo() {
        return valorArriendo;
    }

    public void setValorArriendo(BigInteger valorArriendo) {
        this.valorArriendo = valorArriendo;
    }

    public BigInteger getIdPriedad() {
        return idPriedad;
    }

    public void setIdPriedad(BigInteger idPriedad) {
        this.idPriedad = idPriedad;
    }

    public Anuncio getAnuncio() {
        return anuncio;
    }

    public void setAnuncio(Anuncio anuncio) {
        this.anuncio = anuncio;
    }

    public Propietario getPropietarioIdPropiet1() {
        return propietarioIdPropiet1;
    }

    public void setPropietarioIdPropiet1(Propietario propietarioIdPropiet1) {
        this.propietarioIdPropiet1 = propietarioIdPropiet1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idInmueble != null ? idInmueble.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Inmueble)) {
            return false;
        }
        Inmueble other = (Inmueble) object;
        if ((this.idInmueble == null && other.idInmueble != null) || (this.idInmueble != null && !this.idInmueble.equals(other.idInmueble))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Dreamhome.Negocio.Inmueble[ idInmueble=" + idInmueble + " ]";
    }
    
}
