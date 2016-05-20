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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "PERIODICO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Periodico.findAll", query = "SELECT p FROM Periodico p"),
    @NamedQuery(name = "Periodico.findByIdPeriodico", query = "SELECT p FROM Periodico p WHERE p.idPeriodico = :idPeriodico"),
    @NamedQuery(name = "Periodico.findByNombre", query = "SELECT p FROM Periodico p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Periodico.findByDireccion", query = "SELECT p FROM Periodico p WHERE p.direccion = :direccion"),
    @NamedQuery(name = "Periodico.findByTelefono", query = "SELECT p FROM Periodico p WHERE p.telefono = :telefono"),
    @NamedQuery(name = "Periodico.findByNomContacto", query = "SELECT p FROM Periodico p WHERE p.nomContacto = :nomContacto")})
public class Periodico implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PERIODICO")
    private BigDecimal idPeriodico;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "DIRECCION")
    private String direccion;
    @Column(name = "TELEFONO")
    private BigInteger telefono;
    @Column(name = "NOM_CONTACTO")
    private String nomContacto;
    @JoinColumn(name = "ANUNCIO_ID_ANUNCIO", referencedColumnName = "ID_ANUNCIO")
    @OneToOne(optional = false)
    private Anuncio anuncioIdAnuncio;

    public Periodico() {
    }

    public Periodico(BigDecimal idPeriodico) {
        this.idPeriodico = idPeriodico;
    }

    public BigDecimal getIdPeriodico() {
        return idPeriodico;
    }

    public void setIdPeriodico(BigDecimal idPeriodico) {
        this.idPeriodico = idPeriodico;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public BigInteger getTelefono() {
        return telefono;
    }

    public void setTelefono(BigInteger telefono) {
        this.telefono = telefono;
    }

    public String getNomContacto() {
        return nomContacto;
    }

    public void setNomContacto(String nomContacto) {
        this.nomContacto = nomContacto;
    }

    public Anuncio getAnuncioIdAnuncio() {
        return anuncioIdAnuncio;
    }

    public void setAnuncioIdAnuncio(Anuncio anuncioIdAnuncio) {
        this.anuncioIdAnuncio = anuncioIdAnuncio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPeriodico != null ? idPeriodico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Periodico)) {
            return false;
        }
        Periodico other = (Periodico) object;
        if ((this.idPeriodico == null && other.idPeriodico != null) || (this.idPeriodico != null && !this.idPeriodico.equals(other.idPeriodico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Dreamhome.Negocio.Periodico[ idPeriodico=" + idPeriodico + " ]";
    }
    
}
