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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cetecom
 */
@Entity
@Table(name = "SUCURSAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sucursal.findAll", query = "SELECT s FROM Sucursal s"),
    @NamedQuery(name = "Sucursal.findByIdSucursal", query = "SELECT s FROM Sucursal s WHERE s.idSucursal = :idSucursal"),
    @NamedQuery(name = "Sucursal.findByCalle", query = "SELECT s FROM Sucursal s WHERE s.calle = :calle"),
    @NamedQuery(name = "Sucursal.findByCiudad", query = "SELECT s FROM Sucursal s WHERE s.ciudad = :ciudad"),
    @NamedQuery(name = "Sucursal.findByCodPostal", query = "SELECT s FROM Sucursal s WHERE s.codPostal = :codPostal"),
    @NamedQuery(name = "Sucursal.findByTelefono", query = "SELECT s FROM Sucursal s WHERE s.telefono = :telefono"),
    @NamedQuery(name = "Sucursal.findBySucursalId", query = "SELECT s FROM Sucursal s WHERE s.sucursalId = :sucursalId"),
    @NamedQuery(name = "Sucursal.findByEmpleadoEmpleadoId", query = "SELECT s FROM Sucursal s WHERE s.empleadoEmpleadoId = :empleadoEmpleadoId")})
public class Sucursal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "ID_SUCURSAL")
    private BigInteger idSucursal;
    @Column(name = "CALLE")
    private String calle;
    @Column(name = "CIUDAD")
    private String ciudad;
    @Column(name = "COD_POSTAL")
    private BigInteger codPostal;
    @Column(name = "TELEFONO")
    private BigInteger telefono;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "SUCURSAL_ID")
    private BigDecimal sucursalId;
    @Basic(optional = false)
    @Column(name = "EMPLEADO_EMPLEADO_ID")
    private BigInteger empleadoEmpleadoId;

    public Sucursal() {
    }

    public Sucursal(BigDecimal sucursalId) {
        this.sucursalId = sucursalId;
    }

    public Sucursal(BigDecimal sucursalId, BigInteger empleadoEmpleadoId) {
        this.sucursalId = sucursalId;
        this.empleadoEmpleadoId = empleadoEmpleadoId;
    }

    public BigInteger getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(BigInteger idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public BigInteger getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(BigInteger codPostal) {
        this.codPostal = codPostal;
    }

    public BigInteger getTelefono() {
        return telefono;
    }

    public void setTelefono(BigInteger telefono) {
        this.telefono = telefono;
    }

    public BigDecimal getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(BigDecimal sucursalId) {
        this.sucursalId = sucursalId;
    }

    public BigInteger getEmpleadoEmpleadoId() {
        return empleadoEmpleadoId;
    }

    public void setEmpleadoEmpleadoId(BigInteger empleadoEmpleadoId) {
        this.empleadoEmpleadoId = empleadoEmpleadoId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sucursalId != null ? sucursalId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sucursal)) {
            return false;
        }
        Sucursal other = (Sucursal) object;
        if ((this.sucursalId == null && other.sucursalId != null) || (this.sucursalId != null && !this.sucursalId.equals(other.sucursalId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Dreamhome.Negocio.Sucursal[ sucursalId=" + sucursalId + " ]";
    }
    
}
