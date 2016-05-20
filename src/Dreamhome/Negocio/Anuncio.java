/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dreamhome.Negocio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cetecom
 */
@Entity
@Table(name = "ANUNCIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Anuncio.findAll", query = "SELECT a FROM Anuncio a"),
    @NamedQuery(name = "Anuncio.findByIdAnuncio", query = "SELECT a FROM Anuncio a WHERE a.idAnuncio = :idAnuncio"),
    @NamedQuery(name = "Anuncio.findByIdInmueble", query = "SELECT a FROM Anuncio a WHERE a.idInmueble = :idInmueble"),
    @NamedQuery(name = "Anuncio.findByFechaPublic", query = "SELECT a FROM Anuncio a WHERE a.fechaPublic = :fechaPublic"),
    @NamedQuery(name = "Anuncio.findByIdPeriodico", query = "SELECT a FROM Anuncio a WHERE a.idPeriodico = :idPeriodico"),
    @NamedQuery(name = "Anuncio.findByPrecio", query = "SELECT a FROM Anuncio a WHERE a.precio = :precio")})
public class Anuncio implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_ANUNCIO")
    private BigDecimal idAnuncio;
    @Column(name = "ID_INMUEBLE")
    private BigInteger idInmueble;
    @Column(name = "FECHA_PUBLIC")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPublic;
    @Column(name = "ID_PERIODICO")
    private BigInteger idPeriodico;
    @Column(name = "PRECIO")
    private BigInteger precio;
    @JoinColumn(name = "INMUEBLE_ID_INMUEBLE", referencedColumnName = "ID_INMUEBLE")
    @OneToOne(optional = false)
    private Inmueble inmuebleIdInmueble;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "anuncioIdAnuncio")
    private Periodico periodico;

    public Anuncio() {
    }

    public Anuncio(BigDecimal idAnuncio) {
        this.idAnuncio = idAnuncio;
    }

    public BigDecimal getIdAnuncio() {
        return idAnuncio;
    }

    public void setIdAnuncio(BigDecimal idAnuncio) {
        this.idAnuncio = idAnuncio;
    }

    public BigInteger getIdInmueble() {
        return idInmueble;
    }

    public void setIdInmueble(BigInteger idInmueble) {
        this.idInmueble = idInmueble;
    }

    public Date getFechaPublic() {
        return fechaPublic;
    }

    public void setFechaPublic(Date fechaPublic) {
        this.fechaPublic = fechaPublic;
    }

    public BigInteger getIdPeriodico() {
        return idPeriodico;
    }

    public void setIdPeriodico(BigInteger idPeriodico) {
        this.idPeriodico = idPeriodico;
    }

    public BigInteger getPrecio() {
        return precio;
    }

    public void setPrecio(BigInteger precio) {
        this.precio = precio;
    }

    public Inmueble getInmuebleIdInmueble() {
        return inmuebleIdInmueble;
    }

    public void setInmuebleIdInmueble(Inmueble inmuebleIdInmueble) {
        this.inmuebleIdInmueble = inmuebleIdInmueble;
    }

    public Periodico getPeriodico() {
        return periodico;
    }

    public void setPeriodico(Periodico periodico) {
        this.periodico = periodico;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAnuncio != null ? idAnuncio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Anuncio)) {
            return false;
        }
        Anuncio other = (Anuncio) object;
        if ((this.idAnuncio == null && other.idAnuncio != null) || (this.idAnuncio != null && !this.idAnuncio.equals(other.idAnuncio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Dreamhome.Negocio.Anuncio[ idAnuncio=" + idAnuncio + " ]";
    }
    
}
