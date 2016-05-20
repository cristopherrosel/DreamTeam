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
@Table(name = "SUPERVISOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Supervisor.findAll", query = "SELECT s FROM Supervisor s"),
    @NamedQuery(name = "Supervisor.findBySupervisorId", query = "SELECT s FROM Supervisor s WHERE s.supervisorId = :supervisorId")})
public class Supervisor implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "SUPERVISOR_ID")
    private BigDecimal supervisorId;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "supervisorSupervisorId")
    private Empleado empleado;

    public Supervisor() {
    }

    public Supervisor(BigDecimal supervisorId) {
        this.supervisorId = supervisorId;
    }

    public BigDecimal getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(BigDecimal supervisorId) {
        this.supervisorId = supervisorId;
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
        hash += (supervisorId != null ? supervisorId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Supervisor)) {
            return false;
        }
        Supervisor other = (Supervisor) object;
        if ((this.supervisorId == null && other.supervisorId != null) || (this.supervisorId != null && !this.supervisorId.equals(other.supervisorId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Dreamhome.Negocio.Supervisor[ supervisorId=" + supervisorId + " ]";
    }
    
}
