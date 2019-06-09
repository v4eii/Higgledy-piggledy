/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administration.entity.controllers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import administration.entity.GeneralTrade;
import java.util.ArrayList;
import java.util.Collection;
import administration.entity.GeneralCafe;
import administration.entity.Specialization;
import administration.entity.controllers.exceptions.IllegalOrphanException;
import administration.entity.controllers.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author мвидео
 */
public class SpecializationJpaController implements Serializable {

    public SpecializationJpaController(EntityManagerFactory emf)
    {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(Specialization specialization)
    {
        if (specialization.getGeneralTradeCollection() == null)
        {
            specialization.setGeneralTradeCollection(new ArrayList<GeneralTrade>());
        }
        if (specialization.getGeneralCafeCollection() == null)
        {
            specialization.setGeneralCafeCollection(new ArrayList<GeneralCafe>());
        }
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<GeneralTrade> attachedGeneralTradeCollection = new ArrayList<GeneralTrade>();
            for (GeneralTrade generalTradeCollectionGeneralTradeToAttach : specialization.getGeneralTradeCollection())
            {
                generalTradeCollectionGeneralTradeToAttach = em.getReference(generalTradeCollectionGeneralTradeToAttach.getClass(), generalTradeCollectionGeneralTradeToAttach.getIdUnion());
                attachedGeneralTradeCollection.add(generalTradeCollectionGeneralTradeToAttach);
            }
            specialization.setGeneralTradeCollection(attachedGeneralTradeCollection);
            Collection<GeneralCafe> attachedGeneralCafeCollection = new ArrayList<GeneralCafe>();
            for (GeneralCafe generalCafeCollectionGeneralCafeToAttach : specialization.getGeneralCafeCollection())
            {
                generalCafeCollectionGeneralCafeToAttach = em.getReference(generalCafeCollectionGeneralCafeToAttach.getClass(), generalCafeCollectionGeneralCafeToAttach.getIdUnion());
                attachedGeneralCafeCollection.add(generalCafeCollectionGeneralCafeToAttach);
            }
            specialization.setGeneralCafeCollection(attachedGeneralCafeCollection);
            em.persist(specialization);
            for (GeneralTrade generalTradeCollectionGeneralTrade : specialization.getGeneralTradeCollection())
            {
                Specialization oldIdSpecOfGeneralTradeCollectionGeneralTrade = generalTradeCollectionGeneralTrade.getIdSpec();
                generalTradeCollectionGeneralTrade.setIdSpec(specialization);
                generalTradeCollectionGeneralTrade = em.merge(generalTradeCollectionGeneralTrade);
                if (oldIdSpecOfGeneralTradeCollectionGeneralTrade != null)
                {
                    oldIdSpecOfGeneralTradeCollectionGeneralTrade.getGeneralTradeCollection().remove(generalTradeCollectionGeneralTrade);
                    oldIdSpecOfGeneralTradeCollectionGeneralTrade = em.merge(oldIdSpecOfGeneralTradeCollectionGeneralTrade);
                }
            }
            for (GeneralCafe generalCafeCollectionGeneralCafe : specialization.getGeneralCafeCollection())
            {
                Specialization oldIdSpecOfGeneralCafeCollectionGeneralCafe = generalCafeCollectionGeneralCafe.getIdSpec();
                generalCafeCollectionGeneralCafe.setIdSpec(specialization);
                generalCafeCollectionGeneralCafe = em.merge(generalCafeCollectionGeneralCafe);
                if (oldIdSpecOfGeneralCafeCollectionGeneralCafe != null)
                {
                    oldIdSpecOfGeneralCafeCollectionGeneralCafe.getGeneralCafeCollection().remove(generalCafeCollectionGeneralCafe);
                    oldIdSpecOfGeneralCafeCollectionGeneralCafe = em.merge(oldIdSpecOfGeneralCafeCollectionGeneralCafe);
                }
            }
            em.getTransaction().commit();
        }
        finally
        {
            if (em != null)
            {
                em.close();
            }
        }
    }

    public void edit(Specialization specialization) throws IllegalOrphanException, NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Specialization persistentSpecialization = em.find(Specialization.class, specialization.getIdSpec());
            Collection<GeneralTrade> generalTradeCollectionOld = persistentSpecialization.getGeneralTradeCollection();
            Collection<GeneralTrade> generalTradeCollectionNew = specialization.getGeneralTradeCollection();
            Collection<GeneralCafe> generalCafeCollectionOld = persistentSpecialization.getGeneralCafeCollection();
            Collection<GeneralCafe> generalCafeCollectionNew = specialization.getGeneralCafeCollection();
            List<String> illegalOrphanMessages = null;
            for (GeneralTrade generalTradeCollectionOldGeneralTrade : generalTradeCollectionOld)
            {
                if (!generalTradeCollectionNew.contains(generalTradeCollectionOldGeneralTrade))
                {
                    if (illegalOrphanMessages == null)
                    {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain GeneralTrade " + generalTradeCollectionOldGeneralTrade + " since its idSpec field is not nullable.");
                }
            }
            for (GeneralCafe generalCafeCollectionOldGeneralCafe : generalCafeCollectionOld)
            {
                if (!generalCafeCollectionNew.contains(generalCafeCollectionOldGeneralCafe))
                {
                    if (illegalOrphanMessages == null)
                    {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain GeneralCafe " + generalCafeCollectionOldGeneralCafe + " since its idSpec field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null)
            {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<GeneralTrade> attachedGeneralTradeCollectionNew = new ArrayList<GeneralTrade>();
            for (GeneralTrade generalTradeCollectionNewGeneralTradeToAttach : generalTradeCollectionNew)
            {
                generalTradeCollectionNewGeneralTradeToAttach = em.getReference(generalTradeCollectionNewGeneralTradeToAttach.getClass(), generalTradeCollectionNewGeneralTradeToAttach.getIdUnion());
                attachedGeneralTradeCollectionNew.add(generalTradeCollectionNewGeneralTradeToAttach);
            }
            generalTradeCollectionNew = attachedGeneralTradeCollectionNew;
            specialization.setGeneralTradeCollection(generalTradeCollectionNew);
            Collection<GeneralCafe> attachedGeneralCafeCollectionNew = new ArrayList<GeneralCafe>();
            for (GeneralCafe generalCafeCollectionNewGeneralCafeToAttach : generalCafeCollectionNew)
            {
                generalCafeCollectionNewGeneralCafeToAttach = em.getReference(generalCafeCollectionNewGeneralCafeToAttach.getClass(), generalCafeCollectionNewGeneralCafeToAttach.getIdUnion());
                attachedGeneralCafeCollectionNew.add(generalCafeCollectionNewGeneralCafeToAttach);
            }
            generalCafeCollectionNew = attachedGeneralCafeCollectionNew;
            specialization.setGeneralCafeCollection(generalCafeCollectionNew);
            specialization = em.merge(specialization);
            for (GeneralTrade generalTradeCollectionNewGeneralTrade : generalTradeCollectionNew)
            {
                if (!generalTradeCollectionOld.contains(generalTradeCollectionNewGeneralTrade))
                {
                    Specialization oldIdSpecOfGeneralTradeCollectionNewGeneralTrade = generalTradeCollectionNewGeneralTrade.getIdSpec();
                    generalTradeCollectionNewGeneralTrade.setIdSpec(specialization);
                    generalTradeCollectionNewGeneralTrade = em.merge(generalTradeCollectionNewGeneralTrade);
                    if (oldIdSpecOfGeneralTradeCollectionNewGeneralTrade != null && !oldIdSpecOfGeneralTradeCollectionNewGeneralTrade.equals(specialization))
                    {
                        oldIdSpecOfGeneralTradeCollectionNewGeneralTrade.getGeneralTradeCollection().remove(generalTradeCollectionNewGeneralTrade);
                        oldIdSpecOfGeneralTradeCollectionNewGeneralTrade = em.merge(oldIdSpecOfGeneralTradeCollectionNewGeneralTrade);
                    }
                }
            }
            for (GeneralCafe generalCafeCollectionNewGeneralCafe : generalCafeCollectionNew)
            {
                if (!generalCafeCollectionOld.contains(generalCafeCollectionNewGeneralCafe))
                {
                    Specialization oldIdSpecOfGeneralCafeCollectionNewGeneralCafe = generalCafeCollectionNewGeneralCafe.getIdSpec();
                    generalCafeCollectionNewGeneralCafe.setIdSpec(specialization);
                    generalCafeCollectionNewGeneralCafe = em.merge(generalCafeCollectionNewGeneralCafe);
                    if (oldIdSpecOfGeneralCafeCollectionNewGeneralCafe != null && !oldIdSpecOfGeneralCafeCollectionNewGeneralCafe.equals(specialization))
                    {
                        oldIdSpecOfGeneralCafeCollectionNewGeneralCafe.getGeneralCafeCollection().remove(generalCafeCollectionNewGeneralCafe);
                        oldIdSpecOfGeneralCafeCollectionNewGeneralCafe = em.merge(oldIdSpecOfGeneralCafeCollectionNewGeneralCafe);
                    }
                }
            }
            em.getTransaction().commit();
        }
        catch (Exception ex)
        {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0)
            {
                Integer id = specialization.getIdSpec();
                if (findSpecialization(id) == null)
                {
                    throw new NonexistentEntityException("The specialization with id " + id + " no longer exists.");
                }
            }
            throw ex;
        }
        finally
        {
            if (em != null)
            {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Specialization specialization;
            try
            {
                specialization = em.getReference(Specialization.class, id);
                specialization.getIdSpec();
            }
            catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The specialization with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<GeneralTrade> generalTradeCollectionOrphanCheck = specialization.getGeneralTradeCollection();
            for (GeneralTrade generalTradeCollectionOrphanCheckGeneralTrade : generalTradeCollectionOrphanCheck)
            {
                if (illegalOrphanMessages == null)
                {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Specialization (" + specialization + ") cannot be destroyed since the GeneralTrade " + generalTradeCollectionOrphanCheckGeneralTrade + " in its generalTradeCollection field has a non-nullable idSpec field.");
            }
            Collection<GeneralCafe> generalCafeCollectionOrphanCheck = specialization.getGeneralCafeCollection();
            for (GeneralCafe generalCafeCollectionOrphanCheckGeneralCafe : generalCafeCollectionOrphanCheck)
            {
                if (illegalOrphanMessages == null)
                {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Specialization (" + specialization + ") cannot be destroyed since the GeneralCafe " + generalCafeCollectionOrphanCheckGeneralCafe + " in its generalCafeCollection field has a non-nullable idSpec field.");
            }
            if (illegalOrphanMessages != null)
            {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(specialization);
            em.getTransaction().commit();
        }
        finally
        {
            if (em != null)
            {
                em.close();
            }
        }
    }

    public List<Specialization> findSpecializationEntities()
    {
        return findSpecializationEntities(true, -1, -1);
    }

    public List<Specialization> findSpecializationEntities(int maxResults, int firstResult)
    {
        return findSpecializationEntities(false, maxResults, firstResult);
    }

    private List<Specialization> findSpecializationEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Specialization.class));
            Query q = em.createQuery(cq);
            if (!all)
            {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        }
        finally
        {
            em.close();
        }
    }

    public Specialization findSpecialization(Integer id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(Specialization.class, id);
        }
        finally
        {
            em.close();
        }
    }

    public int getSpecializationCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Specialization> rt = cq.from(Specialization.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        }
        finally
        {
            em.close();
        }
    }
    
}
