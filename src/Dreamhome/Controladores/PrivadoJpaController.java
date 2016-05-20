/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dreamhome.Controladores;

import Dreamhome.Controladores.exceptions.IllegalOrphanException;
import Dreamhome.Controladores.exceptions.NonexistentEntityException;
import Dreamhome.Controladores.exceptions.PreexistingEntityException;
import Dreamhome.Negocio.Privado;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
public class PrivadoJpaController implements Serializable {

    public PrivadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Privado privado) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Propietario propietario = privado.getPropietario();
            if (propietario != null) {
                propietario = em.getReference(propietario.getClass(), propietario.getIdPropiet1());
                privado.setPropietario(propietario);
            }
            em.persist(privado);
            if (propietario != null) {
                Privado oldPrivadoIdPropietOfPropietario = propietario.getPrivadoIdPropiet();
                if (oldPrivadoIdPropietOfPropietario != null) {
                    oldPrivadoIdPropietOfPropietario.setPropietario(null);
                    oldPrivadoIdPropietOfPropietario = em.merge(oldPrivadoIdPropietOfPropietario);
                }
                propietario.setPrivadoIdPropiet(privado);
                propietario = em.merge(propietario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPrivado(privado.getIdPropiet()) != null) {
                throw new PreexistingEntityException("Privado " + privado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Privado privado) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Privado persistentPrivado = em.find(Privado.class, privado.getIdPropiet());
            Propietario propietarioOld = persistentPrivado.getPropietario();
            Propietario propietarioNew = privado.getPropietario();
            List<String> illegalOrphanMessages = null;
            if (propietarioOld != null && !propietarioOld.equals(propietarioNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Propietario " + propietarioOld + " since its privadoIdPropiet field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (propietarioNew != null) {
                propietarioNew = em.getReference(propietarioNew.getClass(), propietarioNew.getIdPropiet1());
                privado.setPropietario(propietarioNew);
            }
            privado = em.merge(privado);
            if (propietarioNew != null && !propietarioNew.equals(propietarioOld)) {
                Privado oldPrivadoIdPropietOfPropietario = propietarioNew.getPrivadoIdPropiet();
                if (oldPrivadoIdPropietOfPropietario != null) {
                    oldPrivadoIdPropietOfPropietario.setPropietario(null);
                    oldPrivadoIdPropietOfPropietario = em.merge(oldPrivadoIdPropietOfPropietario);
                }
                propietarioNew.setPrivadoIdPropiet(privado);
                propietarioNew = em.merge(propietarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = privado.getIdPropiet();
                if (findPrivado(id) == null) {
                    throw new NonexistentEntityException("The privado with id " + id + " no longer exists.");
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
            Privado privado;
            try {
                privado = em.getReference(Privado.class, id);
                privado.getIdPropiet();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The privado with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Propietario propietarioOrphanCheck = privado.getPropietario();
            if (propietarioOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Privado (" + privado + ") cannot be destroyed since the Propietario " + propietarioOrphanCheck + " in its propietario field has a non-nullable privadoIdPropiet field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(privado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Privado> findPrivadoEntities() {
        return findPrivadoEntities(true, -1, -1);
    }

    public List<Privado> findPrivadoEntities(int maxResults, int firstResult) {
        return findPrivadoEntities(false, maxResults, firstResult);
    }

    private List<Privado> findPrivadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Privado.class));
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

    public Privado findPrivado(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Privado.class, id);
        } finally {
            em.close();
        }
    }

    public int getPrivadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Privado> rt = cq.from(Privado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
