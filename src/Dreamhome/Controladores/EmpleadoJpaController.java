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
import Dreamhome.Negocio.Asistente;
import Dreamhome.Negocio.Director;
import Dreamhome.Negocio.Empleado;
import Dreamhome.Negocio.Registro;
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
public class EmpleadoJpaController implements Serializable {

    public EmpleadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empleado empleado) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Asistente asistenteAsistenteIdOrphanCheck = empleado.getAsistenteAsistenteId();
        if (asistenteAsistenteIdOrphanCheck != null) {
            Empleado oldEmpleadoOfAsistenteAsistenteId = asistenteAsistenteIdOrphanCheck.getEmpleado();
            if (oldEmpleadoOfAsistenteAsistenteId != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Asistente " + asistenteAsistenteIdOrphanCheck + " already has an item of type Empleado whose asistenteAsistenteId column cannot be null. Please make another selection for the asistenteAsistenteId field.");
            }
        }
        Director directorIdEmpleadoOrphanCheck = empleado.getDirectorIdEmpleado();
        if (directorIdEmpleadoOrphanCheck != null) {
            Empleado oldEmpleadoOfDirectorIdEmpleado = directorIdEmpleadoOrphanCheck.getEmpleado();
            if (oldEmpleadoOfDirectorIdEmpleado != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Director " + directorIdEmpleadoOrphanCheck + " already has an item of type Empleado whose directorIdEmpleado column cannot be null. Please make another selection for the directorIdEmpleado field.");
            }
        }
        Registro registroRegistroIdOrphanCheck = empleado.getRegistroRegistroId();
        if (registroRegistroIdOrphanCheck != null) {
            Empleado oldEmpleadoOfRegistroRegistroId = registroRegistroIdOrphanCheck.getEmpleado();
            if (oldEmpleadoOfRegistroRegistroId != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Registro " + registroRegistroIdOrphanCheck + " already has an item of type Empleado whose registroRegistroId column cannot be null. Please make another selection for the registroRegistroId field.");
            }
        }
        Supervisor supervisorSupervisorIdOrphanCheck = empleado.getSupervisorSupervisorId();
        if (supervisorSupervisorIdOrphanCheck != null) {
            Empleado oldEmpleadoOfSupervisorSupervisorId = supervisorSupervisorIdOrphanCheck.getEmpleado();
            if (oldEmpleadoOfSupervisorSupervisorId != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Supervisor " + supervisorSupervisorIdOrphanCheck + " already has an item of type Empleado whose supervisorSupervisorId column cannot be null. Please make another selection for the supervisorSupervisorId field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Asistente asistenteAsistenteId = empleado.getAsistenteAsistenteId();
            if (asistenteAsistenteId != null) {
                asistenteAsistenteId = em.getReference(asistenteAsistenteId.getClass(), asistenteAsistenteId.getAsistenteId());
                empleado.setAsistenteAsistenteId(asistenteAsistenteId);
            }
            Director directorIdEmpleado = empleado.getDirectorIdEmpleado();
            if (directorIdEmpleado != null) {
                directorIdEmpleado = em.getReference(directorIdEmpleado.getClass(), directorIdEmpleado.getIdEmpleado());
                empleado.setDirectorIdEmpleado(directorIdEmpleado);
            }
            Registro registroRegistroId = empleado.getRegistroRegistroId();
            if (registroRegistroId != null) {
                registroRegistroId = em.getReference(registroRegistroId.getClass(), registroRegistroId.getRegistroId());
                empleado.setRegistroRegistroId(registroRegistroId);
            }
            Supervisor supervisorSupervisorId = empleado.getSupervisorSupervisorId();
            if (supervisorSupervisorId != null) {
                supervisorSupervisorId = em.getReference(supervisorSupervisorId.getClass(), supervisorSupervisorId.getSupervisorId());
                empleado.setSupervisorSupervisorId(supervisorSupervisorId);
            }
            em.persist(empleado);
            if (asistenteAsistenteId != null) {
                asistenteAsistenteId.setEmpleado(empleado);
                asistenteAsistenteId = em.merge(asistenteAsistenteId);
            }
            if (directorIdEmpleado != null) {
                directorIdEmpleado.setEmpleado(empleado);
                directorIdEmpleado = em.merge(directorIdEmpleado);
            }
            if (registroRegistroId != null) {
                registroRegistroId.setEmpleado(empleado);
                registroRegistroId = em.merge(registroRegistroId);
            }
            if (supervisorSupervisorId != null) {
                supervisorSupervisorId.setEmpleado(empleado);
                supervisorSupervisorId = em.merge(supervisorSupervisorId);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEmpleado(empleado.getIdEmpleado()) != null) {
                throw new PreexistingEntityException("Empleado " + empleado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empleado empleado) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado persistentEmpleado = em.find(Empleado.class, empleado.getIdEmpleado());
            Asistente asistenteAsistenteIdOld = persistentEmpleado.getAsistenteAsistenteId();
            Asistente asistenteAsistenteIdNew = empleado.getAsistenteAsistenteId();
            Director directorIdEmpleadoOld = persistentEmpleado.getDirectorIdEmpleado();
            Director directorIdEmpleadoNew = empleado.getDirectorIdEmpleado();
            Registro registroRegistroIdOld = persistentEmpleado.getRegistroRegistroId();
            Registro registroRegistroIdNew = empleado.getRegistroRegistroId();
            Supervisor supervisorSupervisorIdOld = persistentEmpleado.getSupervisorSupervisorId();
            Supervisor supervisorSupervisorIdNew = empleado.getSupervisorSupervisorId();
            List<String> illegalOrphanMessages = null;
            if (asistenteAsistenteIdNew != null && !asistenteAsistenteIdNew.equals(asistenteAsistenteIdOld)) {
                Empleado oldEmpleadoOfAsistenteAsistenteId = asistenteAsistenteIdNew.getEmpleado();
                if (oldEmpleadoOfAsistenteAsistenteId != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Asistente " + asistenteAsistenteIdNew + " already has an item of type Empleado whose asistenteAsistenteId column cannot be null. Please make another selection for the asistenteAsistenteId field.");
                }
            }
            if (directorIdEmpleadoNew != null && !directorIdEmpleadoNew.equals(directorIdEmpleadoOld)) {
                Empleado oldEmpleadoOfDirectorIdEmpleado = directorIdEmpleadoNew.getEmpleado();
                if (oldEmpleadoOfDirectorIdEmpleado != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Director " + directorIdEmpleadoNew + " already has an item of type Empleado whose directorIdEmpleado column cannot be null. Please make another selection for the directorIdEmpleado field.");
                }
            }
            if (registroRegistroIdNew != null && !registroRegistroIdNew.equals(registroRegistroIdOld)) {
                Empleado oldEmpleadoOfRegistroRegistroId = registroRegistroIdNew.getEmpleado();
                if (oldEmpleadoOfRegistroRegistroId != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Registro " + registroRegistroIdNew + " already has an item of type Empleado whose registroRegistroId column cannot be null. Please make another selection for the registroRegistroId field.");
                }
            }
            if (supervisorSupervisorIdNew != null && !supervisorSupervisorIdNew.equals(supervisorSupervisorIdOld)) {
                Empleado oldEmpleadoOfSupervisorSupervisorId = supervisorSupervisorIdNew.getEmpleado();
                if (oldEmpleadoOfSupervisorSupervisorId != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Supervisor " + supervisorSupervisorIdNew + " already has an item of type Empleado whose supervisorSupervisorId column cannot be null. Please make another selection for the supervisorSupervisorId field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (asistenteAsistenteIdNew != null) {
                asistenteAsistenteIdNew = em.getReference(asistenteAsistenteIdNew.getClass(), asistenteAsistenteIdNew.getAsistenteId());
                empleado.setAsistenteAsistenteId(asistenteAsistenteIdNew);
            }
            if (directorIdEmpleadoNew != null) {
                directorIdEmpleadoNew = em.getReference(directorIdEmpleadoNew.getClass(), directorIdEmpleadoNew.getIdEmpleado());
                empleado.setDirectorIdEmpleado(directorIdEmpleadoNew);
            }
            if (registroRegistroIdNew != null) {
                registroRegistroIdNew = em.getReference(registroRegistroIdNew.getClass(), registroRegistroIdNew.getRegistroId());
                empleado.setRegistroRegistroId(registroRegistroIdNew);
            }
            if (supervisorSupervisorIdNew != null) {
                supervisorSupervisorIdNew = em.getReference(supervisorSupervisorIdNew.getClass(), supervisorSupervisorIdNew.getSupervisorId());
                empleado.setSupervisorSupervisorId(supervisorSupervisorIdNew);
            }
            empleado = em.merge(empleado);
            if (asistenteAsistenteIdOld != null && !asistenteAsistenteIdOld.equals(asistenteAsistenteIdNew)) {
                asistenteAsistenteIdOld.setEmpleado(null);
                asistenteAsistenteIdOld = em.merge(asistenteAsistenteIdOld);
            }
            if (asistenteAsistenteIdNew != null && !asistenteAsistenteIdNew.equals(asistenteAsistenteIdOld)) {
                asistenteAsistenteIdNew.setEmpleado(empleado);
                asistenteAsistenteIdNew = em.merge(asistenteAsistenteIdNew);
            }
            if (directorIdEmpleadoOld != null && !directorIdEmpleadoOld.equals(directorIdEmpleadoNew)) {
                directorIdEmpleadoOld.setEmpleado(null);
                directorIdEmpleadoOld = em.merge(directorIdEmpleadoOld);
            }
            if (directorIdEmpleadoNew != null && !directorIdEmpleadoNew.equals(directorIdEmpleadoOld)) {
                directorIdEmpleadoNew.setEmpleado(empleado);
                directorIdEmpleadoNew = em.merge(directorIdEmpleadoNew);
            }
            if (registroRegistroIdOld != null && !registroRegistroIdOld.equals(registroRegistroIdNew)) {
                registroRegistroIdOld.setEmpleado(null);
                registroRegistroIdOld = em.merge(registroRegistroIdOld);
            }
            if (registroRegistroIdNew != null && !registroRegistroIdNew.equals(registroRegistroIdOld)) {
                registroRegistroIdNew.setEmpleado(empleado);
                registroRegistroIdNew = em.merge(registroRegistroIdNew);
            }
            if (supervisorSupervisorIdOld != null && !supervisorSupervisorIdOld.equals(supervisorSupervisorIdNew)) {
                supervisorSupervisorIdOld.setEmpleado(null);
                supervisorSupervisorIdOld = em.merge(supervisorSupervisorIdOld);
            }
            if (supervisorSupervisorIdNew != null && !supervisorSupervisorIdNew.equals(supervisorSupervisorIdOld)) {
                supervisorSupervisorIdNew.setEmpleado(empleado);
                supervisorSupervisorIdNew = em.merge(supervisorSupervisorIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = empleado.getIdEmpleado();
                if (findEmpleado(id) == null) {
                    throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.");
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
            Empleado empleado;
            try {
                empleado = em.getReference(Empleado.class, id);
                empleado.getIdEmpleado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.", enfe);
            }
            Asistente asistenteAsistenteId = empleado.getAsistenteAsistenteId();
            if (asistenteAsistenteId != null) {
                asistenteAsistenteId.setEmpleado(null);
                asistenteAsistenteId = em.merge(asistenteAsistenteId);
            }
            Director directorIdEmpleado = empleado.getDirectorIdEmpleado();
            if (directorIdEmpleado != null) {
                directorIdEmpleado.setEmpleado(null);
                directorIdEmpleado = em.merge(directorIdEmpleado);
            }
            Registro registroRegistroId = empleado.getRegistroRegistroId();
            if (registroRegistroId != null) {
                registroRegistroId.setEmpleado(null);
                registroRegistroId = em.merge(registroRegistroId);
            }
            Supervisor supervisorSupervisorId = empleado.getSupervisorSupervisorId();
            if (supervisorSupervisorId != null) {
                supervisorSupervisorId.setEmpleado(null);
                supervisorSupervisorId = em.merge(supervisorSupervisorId);
            }
            em.remove(empleado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Empleado> findEmpleadoEntities() {
        return findEmpleadoEntities(true, -1, -1);
    }

    public List<Empleado> findEmpleadoEntities(int maxResults, int firstResult) {
        return findEmpleadoEntities(false, maxResults, firstResult);
    }

    private List<Empleado> findEmpleadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empleado.class));
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

    public Empleado findEmpleado(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empleado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpleadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empleado> rt = cq.from(Empleado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
