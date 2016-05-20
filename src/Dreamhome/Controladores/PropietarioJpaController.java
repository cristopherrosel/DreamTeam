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
import Dreamhome.Negocio.Empresarial;
import Dreamhome.Negocio.Privado;
import Dreamhome.Negocio.Inmueble;
import Dreamhome.Negocio.Propietario;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author cetecom
 */
public class PropietarioJpaController implements Serializable {

    public PropietarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Propietario propietario) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (propietario.getInmuebleCollection() == null) {
            propietario.setInmuebleCollection(new ArrayList<Inmueble>());
        }
        List<String> illegalOrphanMessages = null;
        Empresarial empresarialEmpresarialIdOrphanCheck = propietario.getEmpresarialEmpresarialId();
        if (empresarialEmpresarialIdOrphanCheck != null) {
            Propietario oldPropietarioOfEmpresarialEmpresarialId = empresarialEmpresarialIdOrphanCheck.getPropietario();
            if (oldPropietarioOfEmpresarialEmpresarialId != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Empresarial " + empresarialEmpresarialIdOrphanCheck + " already has an item of type Propietario whose empresarialEmpresarialId column cannot be null. Please make another selection for the empresarialEmpresarialId field.");
            }
        }
        Privado privadoIdPropietOrphanCheck = propietario.getPrivadoIdPropiet();
        if (privadoIdPropietOrphanCheck != null) {
            Propietario oldPropietarioOfPrivadoIdPropiet = privadoIdPropietOrphanCheck.getPropietario();
            if (oldPropietarioOfPrivadoIdPropiet != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Privado " + privadoIdPropietOrphanCheck + " already has an item of type Propietario whose privadoIdPropiet column cannot be null. Please make another selection for the privadoIdPropiet field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresarial empresarialEmpresarialId = propietario.getEmpresarialEmpresarialId();
            if (empresarialEmpresarialId != null) {
                empresarialEmpresarialId = em.getReference(empresarialEmpresarialId.getClass(), empresarialEmpresarialId.getEmpresarialId());
                propietario.setEmpresarialEmpresarialId(empresarialEmpresarialId);
            }
            Privado privadoIdPropiet = propietario.getPrivadoIdPropiet();
            if (privadoIdPropiet != null) {
                privadoIdPropiet = em.getReference(privadoIdPropiet.getClass(), privadoIdPropiet.getIdPropiet());
                propietario.setPrivadoIdPropiet(privadoIdPropiet);
            }
            Collection<Inmueble> attachedInmuebleCollection = new ArrayList<Inmueble>();
            for (Inmueble inmuebleCollectionInmuebleToAttach : propietario.getInmuebleCollection()) {
                inmuebleCollectionInmuebleToAttach = em.getReference(inmuebleCollectionInmuebleToAttach.getClass(), inmuebleCollectionInmuebleToAttach.getIdInmueble());
                attachedInmuebleCollection.add(inmuebleCollectionInmuebleToAttach);
            }
            propietario.setInmuebleCollection(attachedInmuebleCollection);
            em.persist(propietario);
            if (empresarialEmpresarialId != null) {
                empresarialEmpresarialId.setPropietario(propietario);
                empresarialEmpresarialId = em.merge(empresarialEmpresarialId);
            }
            if (privadoIdPropiet != null) {
                privadoIdPropiet.setPropietario(propietario);
                privadoIdPropiet = em.merge(privadoIdPropiet);
            }
            for (Inmueble inmuebleCollectionInmueble : propietario.getInmuebleCollection()) {
                Propietario oldPropietarioIdPropiet1OfInmuebleCollectionInmueble = inmuebleCollectionInmueble.getPropietarioIdPropiet1();
                inmuebleCollectionInmueble.setPropietarioIdPropiet1(propietario);
                inmuebleCollectionInmueble = em.merge(inmuebleCollectionInmueble);
                if (oldPropietarioIdPropiet1OfInmuebleCollectionInmueble != null) {
                    oldPropietarioIdPropiet1OfInmuebleCollectionInmueble.getInmuebleCollection().remove(inmuebleCollectionInmueble);
                    oldPropietarioIdPropiet1OfInmuebleCollectionInmueble = em.merge(oldPropietarioIdPropiet1OfInmuebleCollectionInmueble);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPropietario(propietario.getIdPropiet1()) != null) {
                throw new PreexistingEntityException("Propietario " + propietario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Propietario propietario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Propietario persistentPropietario = em.find(Propietario.class, propietario.getIdPropiet1());
            Empresarial empresarialEmpresarialIdOld = persistentPropietario.getEmpresarialEmpresarialId();
            Empresarial empresarialEmpresarialIdNew = propietario.getEmpresarialEmpresarialId();
            Privado privadoIdPropietOld = persistentPropietario.getPrivadoIdPropiet();
            Privado privadoIdPropietNew = propietario.getPrivadoIdPropiet();
            Collection<Inmueble> inmuebleCollectionOld = persistentPropietario.getInmuebleCollection();
            Collection<Inmueble> inmuebleCollectionNew = propietario.getInmuebleCollection();
            List<String> illegalOrphanMessages = null;
            if (empresarialEmpresarialIdNew != null && !empresarialEmpresarialIdNew.equals(empresarialEmpresarialIdOld)) {
                Propietario oldPropietarioOfEmpresarialEmpresarialId = empresarialEmpresarialIdNew.getPropietario();
                if (oldPropietarioOfEmpresarialEmpresarialId != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Empresarial " + empresarialEmpresarialIdNew + " already has an item of type Propietario whose empresarialEmpresarialId column cannot be null. Please make another selection for the empresarialEmpresarialId field.");
                }
            }
            if (privadoIdPropietNew != null && !privadoIdPropietNew.equals(privadoIdPropietOld)) {
                Propietario oldPropietarioOfPrivadoIdPropiet = privadoIdPropietNew.getPropietario();
                if (oldPropietarioOfPrivadoIdPropiet != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Privado " + privadoIdPropietNew + " already has an item of type Propietario whose privadoIdPropiet column cannot be null. Please make another selection for the privadoIdPropiet field.");
                }
            }
            for (Inmueble inmuebleCollectionOldInmueble : inmuebleCollectionOld) {
                if (!inmuebleCollectionNew.contains(inmuebleCollectionOldInmueble)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Inmueble " + inmuebleCollectionOldInmueble + " since its propietarioIdPropiet1 field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (empresarialEmpresarialIdNew != null) {
                empresarialEmpresarialIdNew = em.getReference(empresarialEmpresarialIdNew.getClass(), empresarialEmpresarialIdNew.getEmpresarialId());
                propietario.setEmpresarialEmpresarialId(empresarialEmpresarialIdNew);
            }
            if (privadoIdPropietNew != null) {
                privadoIdPropietNew = em.getReference(privadoIdPropietNew.getClass(), privadoIdPropietNew.getIdPropiet());
                propietario.setPrivadoIdPropiet(privadoIdPropietNew);
            }
            Collection<Inmueble> attachedInmuebleCollectionNew = new ArrayList<Inmueble>();
            for (Inmueble inmuebleCollectionNewInmuebleToAttach : inmuebleCollectionNew) {
                inmuebleCollectionNewInmuebleToAttach = em.getReference(inmuebleCollectionNewInmuebleToAttach.getClass(), inmuebleCollectionNewInmuebleToAttach.getIdInmueble());
                attachedInmuebleCollectionNew.add(inmuebleCollectionNewInmuebleToAttach);
            }
            inmuebleCollectionNew = attachedInmuebleCollectionNew;
            propietario.setInmuebleCollection(inmuebleCollectionNew);
            propietario = em.merge(propietario);
            if (empresarialEmpresarialIdOld != null && !empresarialEmpresarialIdOld.equals(empresarialEmpresarialIdNew)) {
                empresarialEmpresarialIdOld.setPropietario(null);
                empresarialEmpresarialIdOld = em.merge(empresarialEmpresarialIdOld);
            }
            if (empresarialEmpresarialIdNew != null && !empresarialEmpresarialIdNew.equals(empresarialEmpresarialIdOld)) {
                empresarialEmpresarialIdNew.setPropietario(propietario);
                empresarialEmpresarialIdNew = em.merge(empresarialEmpresarialIdNew);
            }
            if (privadoIdPropietOld != null && !privadoIdPropietOld.equals(privadoIdPropietNew)) {
                privadoIdPropietOld.setPropietario(null);
                privadoIdPropietOld = em.merge(privadoIdPropietOld);
            }
            if (privadoIdPropietNew != null && !privadoIdPropietNew.equals(privadoIdPropietOld)) {
                privadoIdPropietNew.setPropietario(propietario);
                privadoIdPropietNew = em.merge(privadoIdPropietNew);
            }
            for (Inmueble inmuebleCollectionNewInmueble : inmuebleCollectionNew) {
                if (!inmuebleCollectionOld.contains(inmuebleCollectionNewInmueble)) {
                    Propietario oldPropietarioIdPropiet1OfInmuebleCollectionNewInmueble = inmuebleCollectionNewInmueble.getPropietarioIdPropiet1();
                    inmuebleCollectionNewInmueble.setPropietarioIdPropiet1(propietario);
                    inmuebleCollectionNewInmueble = em.merge(inmuebleCollectionNewInmueble);
                    if (oldPropietarioIdPropiet1OfInmuebleCollectionNewInmueble != null && !oldPropietarioIdPropiet1OfInmuebleCollectionNewInmueble.equals(propietario)) {
                        oldPropietarioIdPropiet1OfInmuebleCollectionNewInmueble.getInmuebleCollection().remove(inmuebleCollectionNewInmueble);
                        oldPropietarioIdPropiet1OfInmuebleCollectionNewInmueble = em.merge(oldPropietarioIdPropiet1OfInmuebleCollectionNewInmueble);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = propietario.getIdPropiet1();
                if (findPropietario(id) == null) {
                    throw new NonexistentEntityException("The propietario with id " + id + " no longer exists.");
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
            Propietario propietario;
            try {
                propietario = em.getReference(Propietario.class, id);
                propietario.getIdPropiet1();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The propietario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Inmueble> inmuebleCollectionOrphanCheck = propietario.getInmuebleCollection();
            for (Inmueble inmuebleCollectionOrphanCheckInmueble : inmuebleCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Propietario (" + propietario + ") cannot be destroyed since the Inmueble " + inmuebleCollectionOrphanCheckInmueble + " in its inmuebleCollection field has a non-nullable propietarioIdPropiet1 field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Empresarial empresarialEmpresarialId = propietario.getEmpresarialEmpresarialId();
            if (empresarialEmpresarialId != null) {
                empresarialEmpresarialId.setPropietario(null);
                empresarialEmpresarialId = em.merge(empresarialEmpresarialId);
            }
            Privado privadoIdPropiet = propietario.getPrivadoIdPropiet();
            if (privadoIdPropiet != null) {
                privadoIdPropiet.setPropietario(null);
                privadoIdPropiet = em.merge(privadoIdPropiet);
            }
            em.remove(propietario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Propietario> findPropietarioEntities() {
        return findPropietarioEntities(true, -1, -1);
    }

    public List<Propietario> findPropietarioEntities(int maxResults, int firstResult) {
        return findPropietarioEntities(false, maxResults, firstResult);
    }

    private List<Propietario> findPropietarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Propietario.class));
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

    public Propietario findPropietario(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Propietario.class, id);
        } finally {
            em.close();
        }
    }

    public int getPropietarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Propietario> rt = cq.from(Propietario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
