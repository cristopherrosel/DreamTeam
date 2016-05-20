/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dreamhome.Negocio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author cetecom
 */
@Entity
@Table(name = "CLIENTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"),
    @NamedQuery(name = "Cliente.findByClienteId", query = "SELECT c FROM Cliente c WHERE c.clienteId = :clienteId"),
    @NamedQuery(name = "Cliente.findByIdCliente", query = "SELECT c FROM Cliente c WHERE c.idCliente = :idCliente"),
    @NamedQuery(name = "Cliente.findByNombre", query = "SELECT c FROM Cliente c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Cliente.findByTelefono", query = "SELECT c FROM Cliente c WHERE c.telefono = :telefono"),
    @NamedQuery(name = "Cliente.findByTipoInmb", query = "SELECT c FROM Cliente c WHERE c.tipoInmb = :tipoInmb"),
    @NamedQuery(name = "Cliente.findByTiempoArrien", query = "SELECT c FROM Cliente c WHERE c.tiempoArrien = :tiempoArrien")})
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "CLIENTE_ID")
    private BigDecimal clienteId;
    @Basic(optional = false)
    @Column(name = "ID_CLIENTE")
    private BigInteger idCliente;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "TELEFONO")
    private BigInteger telefono;
    @Column(name = "TIPO_INMB")
    private String tipoInmb;
    @Column(name = "TIEMPO_ARRIEN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tiempoArrien;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clienteClienteId")
    private Collection<Contrato> contratoCollection;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "clienteClienteId")
    private Registro registro;

    public Cliente() {
    }

    public Cliente(BigDecimal clienteId) {
        this.clienteId = clienteId;
    }

    public Cliente(BigDecimal clienteId, BigInteger idCliente) {
        this.clienteId = clienteId;
        this.idCliente = idCliente;
    }

    public BigDecimal getClienteId() {
        return clienteId;
    }

    public void setClienteId(BigDecimal clienteId) {
        this.clienteId = clienteId;
    }

    public BigInteger getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(BigInteger idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigInteger getTelefono() {
        return telefono;
    }

    public void setTelefono(BigInteger telefono) {
        this.telefono = telefono;
    }

    public String getTipoInmb() {
        return tipoInmb;
    }

    public void setTipoInmb(String tipoInmb) {
        this.tipoInmb = tipoInmb;
    }

    public Date getTiempoArrien() {
        return tiempoArrien;
    }

    public void setTiempoArrien(Date tiempoArrien) {
        this.tiempoArrien = tiempoArrien;
    }

    @XmlTransient
    public Collection<Contrato> getContratoCollection() {
        return contratoCollection;
    }

    public void setContratoCollection(Collection<Contrato> contratoCollection) {
        this.contratoCollection = contratoCollection;
    }

    public Registro getRegistro() {
        return registro;
    }

    public void setRegistro(Registro registro) {
        this.registro = registro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clienteId != null ? clienteId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.clienteId == null && other.clienteId != null) || (this.clienteId != null && !this.clienteId.equals(other.clienteId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Dreamhome.Negocio.Cliente[ clienteId=" + clienteId + " ]";
    }
    
}
