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
@Table(name = "ASISTENTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Asistente.findAll", query = "SELECT a FROM Asistente a"),
    @NamedQuery(name = "Asistente.findByAsistenteId", query = "SELECT a FROM Asistente a WHERE a.asistenteId = :asistenteId")})
public class Asistente implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ASISTENTE_ID")
    private BigDecimal asistenteId;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "asistenteAsistenteId")
    private Empleado empleado;

    public Asistente() {
    }

    public Asistente(BigDecimal asistenteId) {
        this.asistenteId = asistenteId;
    }

    public BigDecimal getAsistenteId() {
        return asistenteId;
    }

    public void setAsistenteId(BigDecimal asistenteId) {
        this.asistenteId = asistenteId;
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
        hash += (asistenteId != null ? asistenteId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Asistente)) {
            return false;
        }
        Asistente other = (Asistente) object;
        if ((this.asistenteId == null && other.asistenteId != null) || (this.asistenteId != null && !this.asistenteId.equals(other.asistenteId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Dreamhome.Negocio.Asistente[ asistenteId=" + asistenteId + " ]";
    }
    
}
