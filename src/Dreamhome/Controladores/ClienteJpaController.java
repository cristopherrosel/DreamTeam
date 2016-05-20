/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dreamhome.Controladores;

import Dreamhome.Controladores.exceptions.IllegalOrphanException;
import Dreamhome.Controladores.exceptions.NonexistentEntityException;
import Dreamhome.Controladores.exceptions.PreexistingEntityException;
import Dreamhome.Negocio.Cliente;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Dreamhome.Negocio.Registro;
import Dreamhome.Negocio.Contrato;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author cetecom
 */
public class ClienteJpaController implements Serializable {

    public ClienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) throws PreexistingEntityException, Exception {
        if (cliente.getContratoCollection() == null) {
            cliente.setContratoCollection(new ArrayList<Contrato>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Registro registro = cliente.getRegistro();
            if (registro != null) {
                registro = em.getReference(registro.getClass(), registro.getRegistroId());
                cliente.setRegistro(registro);
            }
            Collection<Contrato> attachedContratoCollection = new ArrayList<Contrato>();
            for (Contrato contratoCollectionContratoToAttach : cliente.getContratoCollection()) {
                contratoCollectionContratoToAttach = em.getReference(contratoCollectionContratoToAttach.getClass(), contratoCollectionContratoToAttach.getIdContrato());
                attachedContratoCollection.add(contratoCollectionContratoToAttach);
            }
            cliente.setContratoCollection(attachedContratoCollection);
            em.persist(cliente);
            if (registro != null) {
                Cliente oldClienteClienteIdOfRegistro = registro.getClienteClienteId();
                if (oldClienteClienteIdOfRegistro != null) {
                    oldClienteClienteIdOfRegistro.setRegistro(null);
                    oldClienteClienteIdOfRegistro = em.merge(oldClienteClienteIdOfRegistro);
                }
                registro.setClienteClienteId(cliente);
                registro = em.merge(registro);
            }
            for (Contrato contratoCollectionContrato : cliente.getContratoCollection()) {
                Cliente oldClienteClienteIdOfContratoCollectionContrato = contratoCollectionContrato.getClienteClienteId();
                contratoCollectionContrato.setClienteClienteId(cliente);
                contratoCollectionContrato = em.merge(contratoCollectionContrato);
                if (oldClienteClienteIdOfContratoCollectionContrato != null) {
                    oldClienteClienteIdOfContratoCollectionContrato.getContratoCollection().remove(contratoCollectionContrato);
                    oldClienteClienteIdOfContratoCollectionContrato = em.merge(oldClienteClienteIdOfContratoCollectionContrato);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCliente(cliente.getClienteId()) != null) {
                throw new PreexistingEntityException("Cliente " + cliente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getClienteId());
            Registro registroOld = persistentCliente.getRegistro();
            Registro registroNew = cliente.getRegistro();
            Collection<Contrato> contratoCollectionOld = persistentCliente.getContratoCollection();
            Collection<Contrato> contratoCollectionNew = cliente.getContratoCollection();
            List<String> illegalOrphanMessages = null;
            if (registroOld != null && !registroOld.equals(registroNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Registro " + registroOld + " since its clienteClienteId field is not nullable.");
            }
            for (Contrato contratoCollectionOldContrato : contratoCollectionOld) {
                if (!contratoCollectionNew.contains(contratoCollectionOldContrato)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Contrato " + contratoCollectionOldContrato + " since its clienteClienteId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (registroNew != null) {
                registroNew = em.getReference(registroNew.getClass(), registroNew.getRegistroId());
                cliente.setRegistro(registroNew);
            }
            Collection<Contrato> attachedContratoCollectionNew = new ArrayList<Contrato>();
            for (Contrato contratoCollectionNewContratoToAttach : contratoCollectionNew) {
                contratoCollectionNewContratoToAttach = em.getReference(contratoCollectionNewContratoToAttach.getClass(), contratoCollectionNewContratoToAttach.getIdContrato());
                attachedContratoCollectionNew.add(contratoCollectionNewContratoToAttach);
            }
            contratoCollectionNew = attachedContratoCollectionNew;
            cliente.setContratoCollection(contratoCollectionNew);
            cliente = em.merge(cliente);
            if (registroNew != null && !registroNew.equals(registroOld)) {
                Cliente oldClienteClienteIdOfRegistro = registroNew.getClienteClienteId();
                if (oldClienteClienteIdOfRegistro != null) {
                    oldClienteClienteIdOfRegistro.setRegistro(null);
                    oldClienteClienteIdOfRegistro = em.merge(oldClienteClienteIdOfRegistro);
                }
                registroNew.setClienteClienteId(cliente);
                registroNew = em.merge(registroNew);
            }
            for (Contrato contratoCollectionNewContrato : contratoCollectionNew) {
                if (!contratoCollectionOld.contains(contratoCollectionNewContrato)) {
                    Cliente oldClienteClienteIdOfContratoCollectionNewContrato = contratoCollectionNewContrato.getClienteClienteId();
                    contratoCollectionNewContrato.setClienteClienteId(cliente);
                    contratoCollectionNewContrato = em.merge(contratoCollectionNewContrato);
                    if (oldClienteClienteIdOfContratoCollectionNewContrato != null && !oldClienteClienteIdOfContratoCollectionNewContrato.equals(cliente)) {
                        oldClienteClienteIdOfContratoCollectionNewContrato.getContratoCollection().remove(contratoCollectionNewContrato);
                        oldClienteClienteIdOfContratoCollectionNewContrato = em.merge(oldClienteClienteIdOfContratoCollectionNewContrato);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = cliente.getClienteId();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BigDecimal id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getClienteId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Registro registroOrphanCheck = cliente.getRegistro();
            if (registroOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the Registro " + registroOrphanCheck + " in its registro field has a non-nullable clienteClienteId field.");
            }
            Collection<Contrato> contratoCollectionOrphanCheck = cliente.getContratoCollection();
            for (Contrato contratoCollectionOrphanCheckContrato : contratoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the Contrato " + contratoCollectionOrphanCheckContrato + " in its contratoCollection field has a non-nullable clienteClienteId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(cliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Cliente findCliente(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
