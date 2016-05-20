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
import Dreamhome.Negocio.Empleado;
import Dreamhome.Negocio.Supervisor;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author cetecom
 */
public class SupervisorJpaController implements Serializable {

    public SupervisorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Supervisor supervisor) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado empleado = supervisor.getEmpleado();
            if (empleado != null) {
                empleado = em.getReference(empleado.getClass(), empleado.getIdEmpleado());
                supervisor.setEmpleado(empleado);
            }
            em.persist(supervisor);
            if (empleado != null) {
                Supervisor oldSupervisorSupervisorIdOfEmpleado = empleado.getSupervisorSupervisorId();
                if (oldSupervisorSupervisorIdOfEmpleado != null) {
                    oldSupervisorSupervisorIdOfEmpleado.setEmpleado(null);
                    oldSupervisorSupervisorIdOfEmpleado = em.merge(oldSupervisorSupervisorIdOfEmpleado);
                }
                empleado.setSupervisorSupervisorId(supervisor);
                empleado = em.merge(empleado);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSupervisor(supervisor.getSupervisorId()) != null) {
                throw new PreexistingEntityException("Supervisor " + supervisor + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Supervisor supervisor) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Supervisor persistentSupervisor = em.find(Supervisor.class, supervisor.getSupervisorId());
            Empleado empleadoOld = persistentSupervisor.getEmpleado();
            Empleado empleadoNew = supervisor.getEmpleado();
            List<String> illegalOrphanMessages = null;
            if (empleadoOld != null && !empleadoOld.equals(empleadoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Empleado " + empleadoOld + " since its supervisorSupervisorId field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (empleadoNew != null) {
                empleadoNew = em.getReference(empleadoNew.getClass(), empleadoNew.getIdEmpleado());
                supervisor.setEmpleado(empleadoNew);
            }
            supervisor = em.merge(supervisor);
            if (empleadoNew != null && !empleadoNew.equals(empleadoOld)) {
                Supervisor oldSupervisorSupervisorIdOfEmpleado = empleadoNew.getSupervisorSupervisorId();
                if (oldSupervisorSupervisorIdOfEmpleado != null) {
                    oldSupervisorSupervisorIdOfEmpleado.setEmpleado(null);
                    oldSupervisorSupervisorIdOfEmpleado = em.merge(oldSupervisorSupervisorIdOfEmpleado);
                }
                empleadoNew.setSupervisorSupervisorId(supervisor);
                empleadoNew = em.merge(empleadoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = supervisor.getSupervisorId();
                if (findSupervisor(id) == null) {
                    throw new NonexistentEntityException("The supervisor with id " + id + " no longer exists.");
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
            Supervisor supervisor;
            try {
                supervisor = em.getReference(Supervisor.class, id);
                supervisor.getSupervisorId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The supervisor with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Empleado empleadoOrphanCheck = supervisor.getEmpleado();
            if (empleadoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Supervisor (" + supervisor + ") cannot be destroyed since the Empleado " + empleadoOrphanCheck + " in its empleado field has a non-nullable supervisorSupervisorId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(supervisor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Supervisor> findSupervisorEntities() {
        return findSupervisorEntities(true, -1, -1);
    }

    public List<Supervisor> findSupervisorEntities(int maxResults, int firstResult) {
        return findSupervisorEntities(false, maxResults, firstResult);
    }

    private List<Supervisor> findSupervisorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Supervisor.class));
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

    public Supervisor findSupervisor(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Supervisor.class, id);
        } finally {
            em.close();
        }
    }

    public int getSupervisorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Supervisor> rt = cq.from(Supervisor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
