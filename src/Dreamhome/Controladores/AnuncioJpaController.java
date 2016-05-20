/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dreamhome.Controladores;

import Dreamhome.Controladores.exceptions.IllegalOrphanException;
import Dreamhome.Controladores.exceptions.NonexistentEntityException;
import Dreamhome.Controladores.exceptions.PreexistingEntityException;
import Dreamhome.Negocio.Anuncio;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Dreamhome.Negocio.Inmueble;
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
public class AnuncioJpaController implements Serializable {

    public AnuncioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Anuncio anuncio) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Inmueble inmuebleIdInmuebleOrphanCheck = anuncio.getInmuebleIdInmueble();
        if (inmuebleIdInmuebleOrphanCheck != null) {
            Anuncio oldAnuncioOfInmuebleIdInmueble = inmuebleIdInmuebleOrphanCheck.getAnuncio();
            if (oldAnuncioOfInmuebleIdInmueble != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Inmueble " + inmuebleIdInmuebleOrphanCheck + " already has an item of type Anuncio whose inmuebleIdInmueble column cannot be null. Please make another selection for the inmuebleIdInmueble field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Inmueble inmuebleIdInmueble = anuncio.getInmuebleIdInmueble();
            if (inmuebleIdInmueble != null) {
                inmuebleIdInmueble = em.getReference(inmuebleIdInmueble.getClass(), inmuebleIdInmueble.getIdInmueble());
                anuncio.setInmuebleIdInmueble(inmuebleIdInmueble);
            }
            Periodico periodico = anuncio.getPeriodico();
            if (periodico != null) {
                periodico = em.getReference(periodico.getClass(), periodico.getIdPeriodico());
                anuncio.setPeriodico(periodico);
            }
            em.persist(anuncio);
            if (inmuebleIdInmueble != null) {
                inmuebleIdInmueble.setAnuncio(anuncio);
                inmuebleIdInmueble = em.merge(inmuebleIdInmueble);
            }
            if (periodico != null) {
                Anuncio oldAnuncioIdAnuncioOfPeriodico = periodico.getAnuncioIdAnuncio();
                if (oldAnuncioIdAnuncioOfPeriodico != null) {
                    oldAnuncioIdAnuncioOfPeriodico.setPeriodico(null);
                    oldAnuncioIdAnuncioOfPeriodico = em.merge(oldAnuncioIdAnuncioOfPeriodico);
                }
                periodico.setAnuncioIdAnuncio(anuncio);
                periodico = em.merge(periodico);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAnuncio(anuncio.getIdAnuncio()) != null) {
                throw new PreexistingEntityException("Anuncio " + anuncio + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Anuncio anuncio) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Anuncio persistentAnuncio = em.find(Anuncio.class, anuncio.getIdAnuncio());
            Inmueble inmuebleIdInmuebleOld = persistentAnuncio.getInmuebleIdInmueble();
            Inmueble inmuebleIdInmuebleNew = anuncio.getInmuebleIdInmueble();
            Periodico periodicoOld = persistentAnuncio.getPeriodico();
            Periodico periodicoNew = anuncio.getPeriodico();
            List<String> illegalOrphanMessages = null;
            if (inmuebleIdInmuebleNew != null && !inmuebleIdInmuebleNew.equals(inmuebleIdInmuebleOld)) {
                Anuncio oldAnuncioOfInmuebleIdInmueble = inmuebleIdInmuebleNew.getAnuncio();
                if (oldAnuncioOfInmuebleIdInmueble != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Inmueble " + inmuebleIdInmuebleNew + " already has an item of type Anuncio whose inmuebleIdInmueble column cannot be null. Please make another selection for the inmuebleIdInmueble field.");
                }
            }
            if (periodicoOld != null && !periodicoOld.equals(periodicoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Periodico " + periodicoOld + " since its anuncioIdAnuncio field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (inmuebleIdInmuebleNew != null) {
                inmuebleIdInmuebleNew = em.getReference(inmuebleIdInmuebleNew.getClass(), inmuebleIdInmuebleNew.getIdInmueble());
                anuncio.setInmuebleIdInmueble(inmuebleIdInmuebleNew);
            }
            if (periodicoNew != null) {
                periodicoNew = em.getReference(periodicoNew.getClass(), periodicoNew.getIdPeriodico());
                anuncio.setPeriodico(periodicoNew);
            }
            anuncio = em.merge(anuncio);
            if (inmuebleIdInmuebleOld != null && !inmuebleIdInmuebleOld.equals(inmuebleIdInmuebleNew)) {
                inmuebleIdInmuebleOld.setAnuncio(null);
                inmuebleIdInmuebleOld = em.merge(inmuebleIdInmuebleOld);
            }
            if (inmuebleIdInmuebleNew != null && !inmuebleIdInmuebleNew.equals(inmuebleIdInmuebleOld)) {
                inmuebleIdInmuebleNew.setAnuncio(anuncio);
                inmuebleIdInmuebleNew = em.merge(inmuebleIdInmuebleNew);
            }
            if (periodicoNew != null && !periodicoNew.equals(periodicoOld)) {
                Anuncio oldAnuncioIdAnuncioOfPeriodico = periodicoNew.getAnuncioIdAnuncio();
                if (oldAnuncioIdAnuncioOfPeriodico != null) {
                    oldAnuncioIdAnuncioOfPeriodico.setPeriodico(null);
                    oldAnuncioIdAnuncioOfPeriodico = em.merge(oldAnuncioIdAnuncioOfPeriodico);
                }
                periodicoNew.setAnuncioIdAnuncio(anuncio);
                periodicoNew = em.merge(periodicoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = anuncio.getIdAnuncio();
                if (findAnuncio(id) == null) {
                    throw new NonexistentEntityException("The anuncio with id " + id + " no longer exists.");
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
            Anuncio anuncio;
            try {
                anuncio = em.getReference(Anuncio.class, id);
                anuncio.getIdAnuncio();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The anuncio with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Periodico periodicoOrphanCheck = anuncio.getPeriodico();
            if (periodicoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Anuncio (" + anuncio + ") cannot be destroyed since the Periodico " + periodicoOrphanCheck + " in its periodico field has a non-nullable anuncioIdAnuncio field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Inmueble inmuebleIdInmueble = anuncio.getInmuebleIdInmueble();
            if (inmuebleIdInmueble != null) {
                inmuebleIdInmueble.setAnuncio(null);
                inmuebleIdInmueble = em.merge(inmuebleIdInmueble);
            }
            em.remove(anuncio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Anuncio> findAnuncioEntities() {
        return findAnuncioEntities(true, -1, -1);
    }

    public List<Anuncio> findAnuncioEntities(int maxResults, int firstResult) {
        return findAnuncioEntities(false, maxResults, firstResult);
    }

    private List<Anuncio> findAnuncioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Anuncio.class));
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

    public Anuncio findAnuncio(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Anuncio.class, id);
        } finally {
            em.close();
        }
    }

    public int getAnuncioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Anuncio> rt = cq.from(Anuncio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
