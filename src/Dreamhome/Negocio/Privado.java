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
@Table(name = "PRIVADO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Privado.findAll", query = "SELECT p FROM Privado p"),
    @NamedQuery(name = "Privado.findByIdPropiet", query = "SELECT p FROM Privado p WHERE p.idPropiet = :idPropiet"),
    @NamedQuery(name = "Privado.findByNombre", query = "SELECT p FROM Privado p WHERE p.nombre = :nombre")})
public class Privado implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PROPIET")
    private BigDecimal idPropiet;
    @Column(name = "NOMBRE")
    private String nombre;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "privadoIdPropiet")
    private Propietario propietario;

    public Privado() {
    }

    public Privado(BigDecimal idPropiet) {
        this.idPropiet = idPropiet;
    }

    public BigDecimal getIdPropiet() {
        return idPropiet;
    }

    public void setIdPropiet(BigDecimal idPropiet) {
        this.idPropiet = idPropiet;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
        hash += (idPropiet != null ? idPropiet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Privado)) {
            return false;
        }
        Privado other = (Privado) object;
        if ((this.idPropiet == null && other.idPropiet != null) || (this.idPropiet != null && !this.idPropiet.equals(other.idPropiet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Dreamhome.Negocio.Privado[ idPropiet=" + idPropiet + " ]";
    }
    
}
