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
import Dreamhome.Negocio.Cliente;
import Dreamhome.Negocio.Registro;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author cetecom
 */
public class RegistroJpaController implements Serializable {

    public RegistroJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Registro registro) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Cliente clienteClienteIdOrphanCheck = registro.getClienteClienteId();
        if (clienteClienteIdOrphanCheck != null) {
            Registro oldRegistroOfClienteClienteId = clienteClienteIdOrphanCheck.getRegistro();
            if (oldRegistroOfClienteClienteId != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Cliente " + clienteClienteIdOrphanCheck + " already has an item of type Registro whose clienteClienteId column cannot be null. Please make another selection for the clienteClienteId field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado empleado = registro.getEmpleado();
            if (empleado != null) {
                empleado = em.getReference(empleado.getClass(), empleado.getIdEmpleado());
                registro.setEmpleado(empleado);
            }
            Cliente clienteClienteId = registro.getClienteClienteId();
            if (clienteClienteId != null) {
                clienteClienteId = em.getReference(clienteClienteId.getClass(), clienteClienteId.getClienteId());
                registro.setClienteClienteId(clienteClienteId);
            }
            em.persist(registro);
            if (empleado != null) {
                Registro oldRegistroRegistroIdOfEmpleado = empleado.getRegistroRegistroId();
                if (oldRegistroRegistroIdOfEmpleado != null) {
                    oldRegistroRegistroIdOfEmpleado.setEmpleado(null);
                    oldRegistroRegistroIdOfEmpleado = em.merge(oldRegistroRegistroIdOfEmpleado);
                }
                empleado.setRegistroRegistroId(registro);
                empleado = em.merge(empleado);
            }
            if (clienteClienteId != null) {
                clienteClienteId.setRegistro(registro);
                clienteClienteId = em.merge(clienteClienteId);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRegistro(registro.getRegistroId()) != null) {
                throw new PreexistingEntityException("Registro " + registro + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Registro registro) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Registro persistentRegistro = em.find(Registro.class, registro.getRegistroId());
            Empleado empleadoOld = persistentRegistro.getEmpleado();
            Empleado empleadoNew = registro.getEmpleado();
            Cliente clienteClienteIdOld = persistentRegistro.getClienteClienteId();
            Cliente clienteClienteIdNew = registro.getClienteClienteId();
            List<String> illegalOrphanMessages = null;
            if (empleadoOld != null && !empleadoOld.equals(empleadoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Empleado " + empleadoOld + " since its registroRegistroId field is not nullable.");
            }
            if (clienteClienteIdNew != null && !clienteClienteIdNew.equals(clienteClienteIdOld)) {
                Registro oldRegistroOfClienteClienteId = clienteClienteIdNew.getRegistro();
                if (oldRegistroOfClienteClienteId != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Cliente " + clienteClienteIdNew + " already has an item of type Registro whose clienteClienteId column cannot be null. Please make another selection for the clienteClienteId field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (empleadoNew != null) {
                empleadoNew = em.getReference(empleadoNew.getClass(), empleadoNew.getIdEmpleado());
                registro.setEmpleado(empleadoNew);
            }
            if (clienteClienteIdNew != null) {
                clienteClienteIdNew = em.getReference(clienteClienteIdNew.getClass(), clienteClienteIdNew.getClienteId());
                registro.setClienteClienteId(clienteClienteIdNew);
            }
            registro = em.merge(registro);
            if (empleadoNew != null && !empleadoNew.equals(empleadoOld)) {
                Registro oldRegistroRegistroIdOfEmpleado = empleadoNew.getRegistroRegistroId();
                if (oldRegistroRegistroIdOfEmpleado != null) {
                    oldRegistroRegistroIdOfEmpleado.setEmpleado(null);
                    oldRegistroRegistroIdOfEmpleado = em.merge(oldRegistroRegistroIdOfEmpleado);
                }
                empleadoNew.setRegistroRegistroId(registro);
                empleadoNew = em.merge(empleadoNew);
            }
            if (clienteClienteIdOld != null && !clienteClienteIdOld.equals(clienteClienteIdNew)) {
                clienteClienteIdOld.setRegistro(null);
                clienteClienteIdOld = em.merge(clienteClienteIdOld);
            }
            if (clienteClienteIdNew != null && !clienteClienteIdNew.equals(clienteClienteIdOld)) {
                clienteClienteIdNew.setRegistro(registro);
                clienteClienteIdNew = em.merge(clienteClienteIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = registro.getRegistroId();
                if (findRegistro(id) == null) {
                    throw new NonexistentEntityException("The registro with id " + id + " no longer exists.");
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
            Registro registro;
            try {
                registro = em.getReference(Registro.class, id);
                registro.getRegistroId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The registro with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Empleado empleadoOrphanCheck = registro.getEmpleado();
            if (empleadoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Registro (" + registro + ") cannot be destroyed since the Empleado " + empleadoOrphanCheck + " in its empleado field has a non-nullable registroRegistroId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Cliente clienteClienteId = registro.getClienteClienteId();
            if (clienteClienteId != null) {
                clienteClienteId.setRegistro(null);
                clienteClienteId = em.merge(clienteClienteId);
            }
            em.remove(registro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Registro> findRegistroEntities() {
        return findRegistroEntities(true, -1, -1);
    }

    public List<Registro> findRegistroEntities(int maxResults, int firstResult) {
        return findRegistroEntities(false, maxResults, firstResult);
    }

    private List<Registro> findRegistroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Registro.class));
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

    public Registro findRegistro(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Registro.class, id);
        } finally {
            em.close();
        }
    }

    public int getRegistroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Registro> rt = cq.from(Registro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
