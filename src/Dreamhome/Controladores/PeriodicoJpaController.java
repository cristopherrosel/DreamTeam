/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dreamhome.Controladores;

import Dreamhome.Controladores.exceptions.IllegalOrphanException;
import Dreamhome.Controladores.exceptions.NonexistentEntityException;
import Dreamhome.Controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Dreamhome.Negocio.Anuncio;
import Dreamhome.Negocio.Periodico;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author cetecom
 */
public class PeriodicoJpaController implements Serializable {

    public PeriodicoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Periodico periodico) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Anuncio anuncioIdAnuncioOrphanCheck = periodico.getAnuncioIdAnuncio();
        if (anuncioIdAnuncioOrphanCheck != null) {
            Periodico oldPeriodicoOfAnuncioIdAnuncio = anuncioIdAnuncioOrphanCheck.getPeriodico();
            if (oldPeriodicoOfAnuncioIdAnuncio != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Anuncio " + anuncioIdAnuncioOrphanCheck + " already has an item of type Periodico whose anuncioIdAnuncio column cannot be null. Please make another selection for the anuncioIdAnuncio field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Anuncio anuncioIdAnuncio = periodico.getAnuncioIdAnuncio();
            if (anuncioIdAnuncio != null) {
                anuncioIdAnuncio = em.getReference(anuncioIdAnuncio.getClass(), anuncioIdAnuncio.getIdAnuncio());
                periodico.setAnuncioIdAnuncio(anuncioIdAnuncio);
            }
            em.persist(periodico);
            if (anuncioIdAnuncio != null) {
                anuncioIdAnuncio.setPeriodico(periodico);
                anuncioIdAnuncio = em.merge(anuncioIdAnuncio);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPeriodico(periodico.getIdPeriodico()) != null) {
                throw new PreexistingEntityException("Periodico " + periodico + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Periodico periodico) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Periodico persistentPeriodico = em.find(Periodico.class, periodico.getIdPeriodico());
            Anuncio anuncioIdAnuncioOld = persistentPeriodico.getAnuncioIdAnuncio();
            Anuncio anuncioIdAnuncioNew = periodico.getAnuncioIdAnuncio();
            List<String> illegalOrphanMessages = null;
            if (anuncioIdAnuncioNew != null && !anuncioIdAnuncioNew.equals(anuncioIdAnuncioOld)) {
                Periodico oldPeriodicoOfAnuncioIdAnuncio = anuncioIdAnuncioNew.getPeriodico();
                if (oldPeriodicoOfAnuncioIdAnuncio != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Anuncio " + anuncioIdAnuncioNew + " already has an item of type Periodico whose anuncioIdAnuncio column cannot be null. Please make another selection for the anuncioIdAnuncio field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (anuncioIdAnuncioNew != null) {
                anuncioIdAnuncioNew = em.getReference(anuncioIdAnuncioNew.getClass(), anuncioIdAnuncioNew.getIdAnuncio());
                periodico.setAnuncioIdAnuncio(anuncioIdAnuncioNew);
            }
            periodico = em.merge(periodico);
            if (anuncioIdAnuncioOld != null && !anuncioIdAnuncioOld.equals(anuncioIdAnuncioNew)) {
                anuncioIdAnuncioOld.setPeriodico(null);
                anuncioIdAnuncioOld = em.merge(anuncioIdAnuncioOld);
            }
            if (anuncioIdAnuncioNew != null && !anuncioIdAnuncioNew.equals(anuncioIdAnuncioOld)) {
                anuncioIdAnuncioNew.setPeriodico(periodico);
                anuncioIdAnuncioNew = em.merge(anuncioIdAnuncioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = periodico.getIdPeriodico();
                if (findPeriodico(id) == null) {
                    throw new NonexistentEntityException("The periodico with id " + id + " no longer exists.");
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
            Periodico periodico;
            try {
                periodico = em.getReference(Periodico.class, id);
                periodico.getIdPeriodico();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The periodico with id " + id + " no longer exists.", enfe);
            }
            Anuncio anuncioIdAnuncio = periodico.getAnuncioIdAnuncio();
            if (anuncioIdAnuncio != null) {
                anuncioIdAnuncio.setPeriodico(null);
                anuncioIdAnuncio = em.merge(anuncioIdAnuncio);
            }
            em.remove(periodico);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Periodico> findPeriodicoEntities() {
        return findPeriodicoEntities(true, -1, -1);
    }

    public List<Periodico> findPeriodicoEntities(int maxResults, int firstResult) {
        return findPeriodicoEntities(false, maxResults, firstResult);
    }

    private List<Periodico> findPeriodicoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Periodico.class));
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

    public Periodico findPeriodico(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Periodico.class, id);
        } finally {
            em.close();
        }
    }

    public int getPeriodicoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Periodico> rt = cq.from(Periodico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
