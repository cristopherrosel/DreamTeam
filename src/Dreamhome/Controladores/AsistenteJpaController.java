/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dreamhome.Controladores;

import Dreamhome.Controladores.exceptions.IllegalOrphanException;
import Dreamhome.Controladores.exceptions.NonexistentEntityException;
import Dreamhome.Controladores.exceptions.PreexistingEntityException;
import Dreamhome.Negocio.Asistente;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Dreamhome.Negocio.Empleado;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author cetecom
 */
public class AsistenteJpaController implements Serializable {

    public AsistenteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Asistente asistente) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado empleado = asistente.getEmpleado();
            if (empleado != null) {
                empleado = em.getReference(empleado.getClass(), empleado.getIdEmpleado());
                asistente.setEmpleado(empleado);
            }
            em.persist(asistente);
            if (empleado != null) {
                Asistente oldAsistenteAsistenteIdOfEmpleado = empleado.getAsistenteAsistenteId();
                if (oldAsistenteAsistenteIdOfEmpleado != null) {
                    oldAsistenteAsistenteIdOfEmpleado.setEmpleado(null);
                    oldAsistenteAsistenteIdOfEmpleado = em.merge(oldAsistenteAsistenteIdOfEmpleado);
                }
                empleado.setAsistenteAsistenteId(asistente);
                empleado = em.merge(empleado);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAsistente(asistente.getAsistenteId()) != null) {
                throw new PreexistingEntityException("Asistente " + asistente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Asistente asistente) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Asistente persistentAsistente = em.find(Asistente.class, asistente.getAsistenteId());
            Empleado empleadoOld = persistentAsistente.getEmpleado();
            Empleado empleadoNew = asistente.getEmpleado();
            List<String> illegalOrphanMessages = null;
            if (empleadoOld != null && !empleadoOld.equals(empleadoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Empleado " + empleadoOld + " since its asistenteAsistenteId field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (empleadoNew != null) {
                empleadoNew = em.getReference(empleadoNew.getClass(), empleadoNew.getIdEmpleado());
                asistente.setEmpleado(empleadoNew);
            }
            asistente = em.merge(asistente);
            if (empleadoNew != null && !empleadoNew.equals(empleadoOld)) {
                Asistente oldAsistenteAsistenteIdOfEmpleado = empleadoNew.getAsistenteAsistenteId();
                if (oldAsistenteAsistenteIdOfEmpleado != null) {
                    oldAsistenteAsistenteIdOfEmpleado.setEmpleado(null);
                    oldAsistenteAsistenteIdOfEmpleado = em.merge(oldAsistenteAsistenteIdOfEmpleado);
                }
                empleadoNew.setAsistenteAsistenteId(asistente);
                empleadoNew = em.merge(empleadoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = asistente.getAsistenteId();
                if (findAsistente(id) == null) {
                    throw new NonexistentEntityException("The asistente with id " + id + " no longer exists.");
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
            Asistente asistente;
            try {
                asistente = em.getReference(Asistente.class, id);
                asistente.getAsistenteId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The asistente with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Empleado empleadoOrphanCheck = asistente.getEmpleado();
            if (empleadoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Asistente (" + asistente + ") cannot be destroyed since the Empleado " + empleadoOrphanCheck + " in its empleado field has a non-nullable asistenteAsistenteId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(asistente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Asistente> findAsistenteEntities() {
        return findAsistenteEntities(true, -1, -1);
    }

    public List<Asistente> findAsistenteEntities(int maxResults, int firstResult) {
        return findAsistenteEntities(false, maxResults, firstResult);
    }

    private List<Asistente> findAsistenteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Asistente.class));
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

    public Asistente findAsistente(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Asistente.class, id);
        } finally {
            em.close();
        }
    }

    public int getAsistenteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Asistente> rt = cq.from(Asistente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
