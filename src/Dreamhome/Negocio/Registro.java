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
@Table(name = "REGISTRO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Registro.findAll", query = "SELECT r FROM Registro r"),
    @NamedQuery(name = "Registro.findByRegistroId", query = "SELECT r FROM Registro r WHERE r.registroId = :registroId"),
    @NamedQuery(name = "Registro.findByIdRegistro", query = "SELECT r FROM Registro r WHERE r.idRegistro = :idRegistro"),
    @NamedQuery(name = "Registro.findByFechaRegistro", query = "SELECT r FROM Registro r WHERE r.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "Registro.findByIdEmpleado1", query = "SELECT r FROM Registro r WHERE r.idEmpleado1 = :idEmpleado1"),
    @NamedQuery(name = "Registro.findByIdCliente", query = "SELECT r FROM Registro r WHERE r.idCliente = :idCliente")})
public class Registro implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "REGISTRO_ID")
    private BigDecimal registroId;
    @Column(name = "ID_REGISTRO")
    private BigInteger idRegistro;
    @Column(name = "FECHA_REGISTRO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "ID_EMPLEADO1")
    private BigInteger idEmpleado1;
    @Column(name = "ID_CLIENTE")
    private BigInteger idCliente;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "registroRegistroId")
    private Empleado empleado;
    @JoinColumn(name = "CLIENTE_CLIENTE_ID", referencedColumnName = "CLIENTE_ID")
    @OneToOne(optional = false)
    private Cliente clienteClienteId;

    public Registro() {
    }

    public Registro(BigDecimal registroId) {
        this.registroId = registroId;
    }

    public BigDecimal getRegistroId() {
        return registroId;
    }

    public void setRegistroId(BigDecimal registroId) {
        this.registroId = registroId;
    }

    public BigInteger getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(BigInteger idRegistro) {
        this.idRegistro = idRegistro;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public BigInteger getIdEmpleado1() {
        return idEmpleado1;
    }

    public void setIdEmpleado1(BigInteger idEmpleado1) {
        this.idEmpleado1 = idEmpleado1;
    }

    public BigInteger getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(BigInteger idCliente) {
        this.idCliente = idCliente;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Cliente getClienteClienteId() {
        return clienteClienteId;
    }

    public void setClienteClienteId(Cliente clienteClienteId) {
        this.clienteClienteId = clienteClienteId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (registroId != null ? registroId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Registro)) {
            return false;
        }
        Registro other = (Registro) object;
        if ((this.registroId == null && other.registroId != null) || (this.registroId != null && !this.registroId.equals(other.registroId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Dreamhome.Negocio.Registro[ registroId=" + registroId + " ]";
    }
    
}
