/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dreamhome.Controladores;

import Dreamhome.Controladores.exceptions.IllegalOrphanException;
import Dreamhome.Controladores.exceptions.NonexistentEntityException;
import Dreamhome.Controladores.exceptions.PreexistingEntityException;
import Dreamhome.Negocio.Director;
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
public class DirectorJpaController implements Serializable {

    public DirectorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Director director) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado empleado = director.getEmpleado();
            if (empleado != null) {
                empleado = em.getReference(empleado.getClass(), empleado.getIdEmpleado());
                director.setEmpleado(empleado);
            }
            em.persist(director);
            if (empleado != null) {
                Director oldDirectorIdEmpleadoOfEmpleado = empleado.getDirectorIdEmpleado();
                if (oldDirectorIdEmpleadoOfEmpleado != null) {
                    oldDirectorIdEmpleadoOfEmpleado.setEmpleado(null);
                    oldDirectorIdEmpleadoOfEmpleado = em.merge(oldDirectorIdEmpleadoOfEmpleado);
                }
                empleado.setDirectorIdEmpleado(director);
                empleado = em.merge(empleado);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDirector(director.getIdEmpleado()) != null) {
                throw new PreexistingEntityException("Director " + director + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Director director) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Director persistentDirector = em.find(Director.class, director.getIdEmpleado());
            Empleado empleadoOld = persistentDirector.getEmpleado();
            Empleado empleadoNew = director.getEmpleado();
            List<String> illegalOrphanMessages = null;
            if (empleadoOld != null && !empleadoOld.equals(empleadoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Empleado " + empleadoOld + " since its directorIdEmpleado field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (empleadoNew != null) {
                empleadoNew = em.getReference(empleadoNew.getClass(), empleadoNew.getIdEmpleado());
                director.setEmpleado(empleadoNew);
            }
            director = em.merge(director);
            if (empleadoNew != null && !empleadoNew.equals(empleadoOld)) {
                Director oldDirectorIdEmpleadoOfEmpleado = empleadoNew.getDirectorIdEmpleado();
                if (oldDirectorIdEmpleadoOfEmpleado != null) {
                    oldDirectorIdEmpleadoOfEmpleado.setEmpleado(null);
                    oldDirectorIdEmpleadoOfEmpleado = em.merge(oldDirectorIdEmpleadoOfEmpleado);
                }
                empleadoNew.setDirectorIdEmpleado(director);
                empleadoNew = em.merge(empleadoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = director.getIdEmpleado();
                if (findDirector(id) == null) {
                    throw new NonexistentEntityException("The director with id " + id + " no longer exists.");
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
            Director director;
            try {
                director = em.getReference(Director.class, id);
                director.getIdEmpleado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The director with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Empleado empleadoOrphanCheck = director.getEmpleado();
            if (empleadoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Director (" + director + ") cannot be destroyed since the Empleado " + empleadoOrphanCheck + " in its empleado field has a non-nullable directorIdEmpleado field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(director);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Director> findDirectorEntities() {
        return findDirectorEntities(true, -1, -1);
    }

    public List<Director> findDirectorEntities(int maxResults, int firstResult) {
        return findDirectorEntities(false, maxResults, firstResult);
    }

    private List<Director> findDirectorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Director.class));
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

    public Director findDirector(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Director.class, id);
        } finally {
            em.close();
        }
    }

    public int getDirectorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Director> rt = cq.from(Director.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
