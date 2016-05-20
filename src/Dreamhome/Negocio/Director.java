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
@Table(name = "DIRECTOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Director.findAll", query = "SELECT d FROM Director d"),
    @NamedQuery(name = "Director.findByIdEmpleado", query = "SELECT d FROM Director d WHERE d.idEmpleado = :idEmpleado"),
    @NamedQuery(name = "Director.findByFechaNomb", query = "SELECT d FROM Director d WHERE d.fechaNomb = :fechaNomb"),
    @NamedQuery(name = "Director.findByBono", query = "SELECT d FROM Director d WHERE d.bono = :bono")})
public class Director implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_EMPLEADO")
    private BigDecimal idEmpleado;
    @Column(name = "FECHA_NOMB")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaNomb;
    @Column(name = "BONO")
    private BigInteger bono;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "directorIdEmpleado")
    private Empleado empleado;

    public Director() {
    }

    public Director(BigDecimal idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public BigDecimal getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(BigDecimal idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Date getFechaNomb() {
        return fechaNomb;
    }

    public void setFechaNomb(Date fechaNomb) {
        this.fechaNomb = fechaNomb;
    }

    public BigInteger getBono() {
        return bono;
    }

    public void setBono(BigInteger bono) {
        this.bono = bono;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmpleado != null ? idEmpleado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Director)) {
            return false;
        }
        Director other = (Director) object;
        if ((this.idEmpleado == null && other.idEmpleado != null) || (this.idEmpleado != null && !this.idEmpleado.equals(other.idEmpleado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Dreamhome.Negocio.Director[ idEmpleado=" + idEmpleado + " ]";
    }
    
}
