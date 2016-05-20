/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dreamhome.Controladores;

import Dreamhome.Controladores.exceptions.IllegalOrphanException;
import Dreamhome.Controladores.exceptions.NonexistentEntityException;
import Dreamhome.Controladores.exceptions.PreexistingEntityException;
import Dreamhome.Negocio.Empresarial;
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
public class EmpresarialJpaController implements Serializable {

    public EmpresarialJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empresarial empresarial) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Propietario propietario = empresarial.getPropietario();
            if (propietario != null) {
                propietario = em.getReference(propietario.getClass(), propietario.getIdPropiet1());
                empresarial.setPropietario(propietario);
            }
            em.persist(empresarial);
            if (propietario != null) {
                Empresarial oldEmpresarialEmpresarialIdOfPropietario = propietario.getEmpresarialEmpresarialId();
                if (oldEmpresarialEmpresarialIdOfPropietario != null) {
                    oldEmpresarialEmpresarialIdOfPropietario.setPropietario(null);
                    oldEmpresarialEmpresarialIdOfPropietario = em.merge(oldEmpresarialEmpresarialIdOfPropietario);
                }
                propietario.setEmpresarialEmpresarialId(empresarial);
                propietario = em.merge(propietario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEmpresarial(empresarial.getEmpresarialId()) != null) {
                throw new PreexistingEntityException("Empresarial " + empresarial + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empresarial empresarial) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresarial persistentEmpresarial = em.find(Empresarial.class, empresarial.getEmpresarialId());
            Propietario propietarioOld = persistentEmpresarial.getPropietario();
            Propietario propietarioNew = empresarial.getPropietario();
            List<String> illegalOrphanMessages = null;
            if (propietarioOld != null && !propietarioOld.equals(propietarioNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Propietario " + propietarioOld + " since its empresarialEmpresarialId field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (propietarioNew != null) {
                propietarioNew = em.getReference(propietarioNew.getClass(), propietarioNew.getIdPropiet1());
                empresarial.setPropietario(propietarioNew);
            }
            empresarial = em.merge(empresarial);
            if (propietarioNew != null && !propietarioNew.equals(propietarioOld)) {
                Empresarial oldEmpresarialEmpresarialIdOfPropietario = propietarioNew.getEmpresarialEmpresarialId();
                if (oldEmpresarialEmpresarialIdOfPropietario != null) {
                    oldEmpresarialEmpresarialIdOfPropietario.setPropietario(null);
                    oldEmpresarialEmpresarialIdOfPropietario = em.merge(oldEmpresarialEmpresarialIdOfPropietario);
                }
                propietarioNew.setEmpresarialEmpresarialId(empresarial);
                propietarioNew = em.merge(propietarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = empresarial.getEmpresarialId();
                if (findEmpresarial(id) == null) {
                    throw new NonexistentEntityException("The empresarial with id " + id + " no longer exists.");
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
            Empresarial empresarial;
            try {
                empresarial = em.getReference(Empresarial.class, id);
                empresarial.getEmpresarialId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empresarial with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Propietario propietarioOrphanCheck = empresarial.getPropietario();
            if (propietarioOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empresarial (" + empresarial + ") cannot be destroyed since the Propietario " + propietarioOrphanCheck + " in its propietario field has a non-nullable empresarialEmpresarialId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(empresarial);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Empresarial> findEmpresarialEntities() {
        return findEmpresarialEntities(true, -1, -1);
    }

    public List<Empresarial> findEmpresarialEntities(int maxResults, int firstResult) {
        return findEmpresarialEntities(false, maxResults, firstResult);
    }

    private List<Empresarial> findEmpresarialEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empresarial.class));
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

    public Empresarial findEmpresarial(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empresarial.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpresarialCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empresarial> rt = cq.from(Empresarial.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
