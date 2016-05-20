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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cetecom
 */
@Entity
@Table(name = "CONTRATO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contrato.findAll", query = "SELECT c FROM Contrato c"),
    @NamedQuery(name = "Contrato.findByIdContrato", query = "SELECT c FROM Contrato c WHERE c.idContrato = :idContrato"),
    @NamedQuery(name = "Contrato.findByNumCliente", query = "SELECT c FROM Contrato c WHERE c.numCliente = :numCliente"),
    @NamedQuery(name = "Contrato.findByIdPropiet", query = "SELECT c FROM Contrato c WHERE c.idPropiet = :idPropiet"),
    @NamedQuery(name = "Contrato.findByDuracionCon", query = "SELECT c FROM Contrato c WHERE c.duracionCon = :duracionCon"),
    @NamedQuery(name = "Contrato.findByFechaInic", query = "SELECT c FROM Contrato c WHERE c.fechaInic = :fechaInic"),
    @NamedQuery(name = "Contrato.findByFechaFin", query = "SELECT c FROM Contrato c WHERE c.fechaFin = :fechaFin"),
    @NamedQuery(name = "Contrato.findByModoPago", query = "SELECT c FROM Contrato c WHERE c.modoPago = :modoPago")})
public class Contrato implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_CONTRATO")
    private BigDecimal idContrato;
    @Column(name = "NUM_CLIENTE")
    private BigInteger numCliente;
    @Column(name = "ID_PROPIET")
    private BigInteger idPropiet;
    @Column(name = "DURACION_CON")
    @Temporal(TemporalType.TIMESTAMP)
    private Date duracionCon;
    @Column(name = "FECHA_INIC")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInic;
    @Column(name = "FECHA_FIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;
    @Column(name = "MODO_PAGO")
    private String modoPago;
    @JoinColumn(name = "CLIENTE_CLIENTE_ID", referencedColumnName = "CLIENTE_ID")
    @ManyToOne(optional = false)
    private Cliente clienteClienteId;

    public Contrato() {
    }

    public Contrato(BigDecimal idContrato) {
        this.idContrato = idContrato;
    }

    public BigDecimal getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(BigDecimal idContrato) {
        this.idContrato = idContrato;
    }

    public BigInteger getNumCliente() {
        return numCliente;
    }

    public void setNumCliente(BigInteger numCliente) {
        this.numCliente = numCliente;
    }

    public BigInteger getIdPropiet() {
        return idPropiet;
    }

    public void setIdPropiet(BigInteger idPropiet) {
        this.idPropiet = idPropiet;
    }

    public Date getDuracionCon() {
        return duracionCon;
    }

    public void setDuracionCon(Date duracionCon) {
        this.duracionCon = duracionCon;
    }

    public Date getFechaInic() {
        return fechaInic;
    }

    public void setFechaInic(Date fechaInic) {
        this.fechaInic = fechaInic;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getModoPago() {
        return modoPago;
    }

    public void setModoPago(String modoPago) {
        this.modoPago = modoPago;
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
        hash += (idContrato != null ? idContrato.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contrato)) {
            return false;
        }
        Contrato other = (Contrato) object;
        if ((this.idContrato == null && other.idContrato != null) || (this.idContrato != null && !this.idContrato.equals(other.idContrato))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Dreamhome.Negocio.Contrato[ idContrato=" + idContrato + " ]";
    }
    
}
