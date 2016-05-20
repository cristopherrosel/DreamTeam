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
import Dreamhome.Negocio.Inmueble;
import Dreamhome.Negocio.Propietario;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author cetecom
 */
public class InmuebleJpaController implements Serializable {

    public InmuebleJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Inmueble inmueble) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Anuncio anuncio = inmueble.getAnuncio();
            if (anuncio != null) {
                anuncio = em.getReference(anuncio.getClass(), anuncio.getIdAnuncio());
                inmueble.setAnuncio(anuncio);
            }
            Propietario propietarioIdPropiet1 = inmueble.getPropietarioIdPropiet1();
            if (propietarioIdPropiet1 != null) {
                propietarioIdPropiet1 = em.getReference(propietarioIdPropiet1.getClass(), propietarioIdPropiet1.getIdPropiet1());
                inmueble.setPropietarioIdPropiet1(propietarioIdPropiet1);
            }
            em.persist(inmueble);
            if (anuncio != null) {
                Inmueble oldInmuebleIdInmuebleOfAnuncio = anuncio.getInmuebleIdInmueble();
                if (oldInmuebleIdInmuebleOfAnuncio != null) {
                    oldInmuebleIdInmuebleOfAnuncio.setAnuncio(null);
                    oldInmuebleIdInmuebleOfAnuncio = em.merge(oldInmuebleIdInmuebleOfAnuncio);
                }
                anuncio.setInmuebleIdInmueble(inmueble);
                anuncio = em.merge(anuncio);
            }
            if (propietarioIdPropiet1 != null) {
                propietarioIdPropiet1.getInmuebleCollection().add(inmueble);
                propietarioIdPropiet1 = em.merge(propietarioIdPropiet1);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findInmueble(inmueble.getIdInmueble()) != null) {
                throw new PreexistingEntityException("Inmueble " + inmueble + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Inmueble inmueble) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Inmueble persistentInmueble = em.find(Inmueble.class, inmueble.getIdInmueble());
            Anuncio anuncioOld = persistentInmueble.getAnuncio();
            Anuncio anuncioNew = inmueble.getAnuncio();
            Propietario propietarioIdPropiet1Old = persistentInmueble.getPropietarioIdPropiet1();
            Propietario propietarioIdPropiet1New = inmueble.getPropietarioIdPropiet1();
            List<String> illegalOrphanMessages = null;
            if (anuncioOld != null && !anuncioOld.equals(anuncioNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Anuncio " + anuncioOld + " since its inmuebleIdInmueble field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (anuncioNew != null) {
                anuncioNew = em.getReference(anuncioNew.getClass(), anuncioNew.getIdAnuncio());
                inmueble.setAnuncio(anuncioNew);
            }
            if (propietarioIdPropiet1New != null) {
                propietarioIdPropiet1New = em.getReference(propietarioIdPropiet1New.getClass(), propietarioIdPropiet1New.getIdPropiet1());
                inmueble.setPropietarioIdPropiet1(propietarioIdPropiet1New);
            }
            inmueble = em.merge(inmueble);
            if (anuncioNew != null && !anuncioNew.equals(anuncioOld)) {
                Inmueble oldInmuebleIdInmuebleOfAnuncio = anuncioNew.getInmuebleIdInmueble();
                if (oldInmuebleIdInmuebleOfAnuncio != null) {
                    oldInmuebleIdInmuebleOfAnuncio.setAnuncio(null);
                    oldInmuebleIdInmuebleOfAnuncio = em.merge(oldInmuebleIdInmuebleOfAnuncio);
                }
                anuncioNew.setInmuebleIdInmueble(inmueble);
                anuncioNew = em.merge(anuncioNew);
            }
            if (propietarioIdPropiet1Old != null && !propietarioIdPropiet1Old.equals(propietarioIdPropiet1New)) {
                propietarioIdPropiet1Old.getInmuebleCollection().remove(inmueble);
                propietarioIdPropiet1Old = em.merge(propietarioIdPropiet1Old);
            }
            if (propietarioIdPropiet1New != null && !propietarioIdPropiet1New.equals(propietarioIdPropiet1Old)) {
                propietarioIdPropiet1New.getInmuebleCollection().add(inmueble);
                propietarioIdPropiet1New = em.merge(propietarioIdPropiet1New);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = inmueble.getIdInmueble();
                if (findInmueble(id) == null) {
                    throw new NonexistentEntityException("The inmueble with id " + id + " no longer exists.");
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
            Inmueble inmueble;
            try {
                inmueble = em.getReference(Inmueble.class, id);
                inmueble.getIdInmueble();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The inmueble with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Anuncio anuncioOrphanCheck = inmueble.getAnuncio();
            if (anuncioOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Inmueble (" + inmueble + ") cannot be destroyed since the Anuncio " + anuncioOrphanCheck + " in its anuncio field has a non-nullable inmuebleIdInmueble field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Propietario propietarioIdPropiet1 = inmueble.getPropietarioIdPropiet1();
            if (propietarioIdPropiet1 != null) {
                propietarioIdPropiet1.getInmuebleCollection().remove(inmueble);
                propietarioIdPropiet1 = em.merge(propietarioIdPropiet1);
            }
            em.remove(inmueble);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Inmueble> findInmuebleEntities() {
        return findInmuebleEntities(true, -1, -1);
    }

    public List<Inmueble> findInmuebleEntities(int maxResults, int firstResult) {
        return findInmuebleEntities(false, maxResults, firstResult);
    }

    private List<Inmueble> findInmuebleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Inmueble.class));
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

    public Inmueble findInmueble(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Inmueble.class, id);
        } finally {
            em.close();
        }
    }

    public int getInmuebleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Inmueble> rt = cq.from(Inmueble.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
