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
@Table(name = "EMPLEADO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Empleado.findAll", query = "SELECT e FROM Empleado e"),
    @NamedQuery(name = "Empleado.findByIdEmpleado", query = "SELECT e FROM Empleado e WHERE e.idEmpleado = :idEmpleado"),
    @NamedQuery(name = "Empleado.findByNombre", query = "SELECT e FROM Empleado e WHERE e.nombre = :nombre"),
    @NamedQuery(name = "Empleado.findByDireccion", query = "SELECT e FROM Empleado e WHERE e.direccion = :direccion"),
    @NamedQuery(name = "Empleado.findByCategoria", query = "SELECT e FROM Empleado e WHERE e.categoria = :categoria"),
    @NamedQuery(name = "Empleado.findBySalario", query = "SELECT e FROM Empleado e WHERE e.salario = :salario"),
    @NamedQuery(name = "Empleado.findByNumSucursal", query = "SELECT e FROM Empleado e WHERE e.numSucursal = :numSucursal"),
    @NamedQuery(name = "Empleado.findByIdSucursal", query = "SELECT e FROM Empleado e WHERE e.idSucursal = :idSucursal")})
public class Empleado implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_EMPLEADO")
    private BigDecimal idEmpleado;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "DIRECCION")
    private String direccion;
    @Column(name = "CATEGORIA")
    private String categoria;
    @Column(name = "SALARIO")
    private BigInteger salario;
    @Column(name = "NUM_SUCURSAL")
    private BigInteger numSucursal;
    @Column(name = "ID_SUCURSAL")
    private BigInteger idSucursal;
    @JoinColumn(name = "ASISTENTE_ASISTENTE_ID", referencedColumnName = "ASISTENTE_ID")
    @OneToOne(optional = false)
    private Asistente asistenteAsistenteId;
    @JoinColumn(name = "DIRECTOR_ID_EMPLEADO", referencedColumnName = "ID_EMPLEADO")
    @OneToOne(optional = false)
    private Director directorIdEmpleado;
    @JoinColumn(name = "REGISTRO_REGISTRO_ID", referencedColumnName = "REGISTRO_ID")
    @OneToOne(optional = false)
    private Registro registroRegistroId;
    @JoinColumn(name = "SUPERVISOR_SUPERVISOR_ID", referencedColumnName = "SUPERVISOR_ID")
    @OneToOne(optional = false)
    private Supervisor supervisorSupervisorId;

    public Empleado() {
    }

    public Empleado(BigDecimal idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public BigDecimal getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(BigDecimal idEmpleado) {
        this.idEmpleado = idEmpleado;
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public BigInteger getSalario() {
        return salario;
    }

    public void setSalario(BigInteger salario) {
        this.salario = salario;
    }

    public BigInteger getNumSucursal() {
        return numSucursal;
    }

    public void setNumSucursal(BigInteger numSucursal) {
        this.numSucursal = numSucursal;
    }

    public BigInteger getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(BigInteger idSucursal) {
        this.idSucursal = idSucursal;
    }

    public Asistente getAsistenteAsistenteId() {
        return asistenteAsistenteId;
    }

    public void setAsistenteAsistenteId(Asistente asistenteAsistenteId) {
        this.asistenteAsistenteId = asistenteAsistenteId;
    }

    public Director getDirectorIdEmpleado() {
        return directorIdEmpleado;
    }

    public void setDirectorIdEmpleado(Director directorIdEmpleado) {
        this.directorIdEmpleado = directorIdEmpleado;
    }

    public Registro getRegistroRegistroId() {
        return registroRegistroId;
    }

    public void setRegistroRegistroId(Registro registroRegistroId) {
        this.registroRegistroId = registroRegistroId;
    }

    public Supervisor getSupervisorSupervisorId() {
        return supervisorSupervisorId;
    }

    public void setSupervisorSupervisorId(Supervisor supervisorSupervisorId) {
        this.supervisorSupervisorId = supervisorSupervisorId;
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
        if (!(object instanceof Empleado)) {
            return false;
        }
        Empleado other = (Empleado) object;
        if ((this.idEmpleado == null && other.idEmpleado != null) || (this.idEmpleado != null && !this.idEmpleado.equals(other.idEmpleado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Dreamhome.Negocio.Empleado[ idEmpleado=" + idEmpleado + " ]";
    }
    
}
