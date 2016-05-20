/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dreamhome.Controladores;

import Dreamhome.Controladores.exceptions.NonexistentEntityException;
import Dreamhome.Controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Dreamhome.Negocio.Cliente;
import Dreamhome.Negocio.Contrato;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author cetecom
 */
public class ContratoJpaController implements Serializable {

    public ContratoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Contrato contrato) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente clienteClienteId = contrato.getClienteClienteId();
            if (clienteClienteId != null) {
                clienteClienteId = em.getReference(clienteClienteId.getClass(), clienteClienteId.getClienteId());
                contrato.setClienteClienteId(clienteClienteId);
            }
            em.persist(contrato);
            if (clienteClienteId != null) {
                clienteClienteId.getContratoCollection().add(contrato);
                clienteClienteId = em.merge(clienteClienteId);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findContrato(contrato.getIdContrato()) != null) {
                throw new PreexistingEntityException("Contrato " + contrato + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Contrato contrato) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Contrato persistentContrato = em.find(Contrato.class, contrato.getIdContrato());
            Cliente clienteClienteIdOld = persistentContrato.getClienteClienteId();
            Cliente clienteClienteIdNew = contrato.getClienteClienteId();
            if (clienteClienteIdNew != null) {
                clienteClienteIdNew = em.getReference(clienteClienteIdNew.getClass(), clienteClienteIdNew.getClienteId());
                contrato.setClienteClienteId(clienteClienteIdNew);
            }
            contrato = em.merge(contrato);
            if (clienteClienteIdOld != null && !clienteClienteIdOld.equals(clienteClienteIdNew)) {
                clienteClienteIdOld.getContratoCollection().remove(contrato);
                clienteClienteIdOld = em.merge(clienteClienteIdOld);
            }
            if (clienteClienteIdNew != null && !clienteClienteIdNew.equals(clienteClienteIdOld)) {
                clienteClienteIdNew.getContratoCollection().add(contrato);
                clienteClienteIdNew = em.merge(clienteClienteIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = contrato.getIdContrato();
                if (findContrato(id) == null) {
                    throw new NonexistentEntityException("The contrato with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BigDecimal id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Contrato contrato;
            try {
                contrato = em.getReference(Contrato.class, id);
                contrato.getIdContrato();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contrato with id " + id + " no longer exists.", enfe);
            }
            Cliente clienteClienteId = contrato.getClienteClienteId();
            if (clienteClienteId != null) {
                clienteClienteId.getContratoCollection().remove(contrato);
                clienteClienteId = em.merge(clienteClienteId);
            }
            em.remove(contrato);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Contrato> findContratoEntities() {
        return findContratoEntities(true, -1, -1);
    }

    public List<Contrato> findContratoEntities(int maxResults, int firstResult) {
        return findContratoEntities(false, maxResults, firstResult);
    }

    private List<Contrato> findContratoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Contrato.class));
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

    public Contrato findContrato(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Contrato.class, id);
        } finally {
            em.close();
        }
    }

    public int getContratoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Contrato> rt = cq.from(Contrato.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
