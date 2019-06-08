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
import administration.entity.General;
import java.util.ArrayList;
import java.util.Collection;
import administration.entity.General2;
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
        if (specialization.getGeneralCollection() == null)
        {
            specialization.setGeneralCollection(new ArrayList<General>());
        }
        if (specialization.getGeneral2Collection() == null)
        {
            specialization.setGeneral2Collection(new ArrayList<General2>());
        }
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<General> attachedGeneralCollection = new ArrayList<General>();
            for (General generalCollectionGeneralToAttach : specialization.getGeneralCollection())
            {
                generalCollectionGeneralToAttach = em.getReference(generalCollectionGeneralToAttach.getClass(), generalCollectionGeneralToAttach.getIdUnion());
                attachedGeneralCollection.add(generalCollectionGeneralToAttach);
            }
            specialization.setGeneralCollection(attachedGeneralCollection);
            Collection<General2> attachedGeneral2Collection = new ArrayList<General2>();
            for (General2 general2CollectionGeneral2ToAttach : specialization.getGeneral2Collection())
            {
                general2CollectionGeneral2ToAttach = em.getReference(general2CollectionGeneral2ToAttach.getClass(), general2CollectionGeneral2ToAttach.getIdUnion());
                attachedGeneral2Collection.add(general2CollectionGeneral2ToAttach);
            }
            specialization.setGeneral2Collection(attachedGeneral2Collection);
            em.persist(specialization);
            for (General generalCollectionGeneral : specialization.getGeneralCollection())
            {
                Specialization oldIdSpecOfGeneralCollectionGeneral = generalCollectionGeneral.getIdSpec();
                generalCollectionGeneral.setIdSpec(specialization);
                generalCollectionGeneral = em.merge(generalCollectionGeneral);
                if (oldIdSpecOfGeneralCollectionGeneral != null)
                {
                    oldIdSpecOfGeneralCollectionGeneral.getGeneralCollection().remove(generalCollectionGeneral);
                    oldIdSpecOfGeneralCollectionGeneral = em.merge(oldIdSpecOfGeneralCollectionGeneral);
                }
            }
            for (General2 general2CollectionGeneral2 : specialization.getGeneral2Collection())
            {
                Specialization oldIdSpecOfGeneral2CollectionGeneral2 = general2CollectionGeneral2.getIdSpec();
                general2CollectionGeneral2.setIdSpec(specialization);
                general2CollectionGeneral2 = em.merge(general2CollectionGeneral2);
                if (oldIdSpecOfGeneral2CollectionGeneral2 != null)
                {
                    oldIdSpecOfGeneral2CollectionGeneral2.getGeneral2Collection().remove(general2CollectionGeneral2);
                    oldIdSpecOfGeneral2CollectionGeneral2 = em.merge(oldIdSpecOfGeneral2CollectionGeneral2);
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
            Collection<General> generalCollectionOld = persistentSpecialization.getGeneralCollection();
            Collection<General> generalCollectionNew = specialization.getGeneralCollection();
            Collection<General2> general2CollectionOld = persistentSpecialization.getGeneral2Collection();
            Collection<General2> general2CollectionNew = specialization.getGeneral2Collection();
            List<String> illegalOrphanMessages = null;
            for (General generalCollectionOldGeneral : generalCollectionOld)
            {
                if (!generalCollectionNew.contains(generalCollectionOldGeneral))
                {
                    if (illegalOrphanMessages == null)
                    {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain General " + generalCollectionOldGeneral + " since its idSpec field is not nullable.");
                }
            }
            for (General2 general2CollectionOldGeneral2 : general2CollectionOld)
            {
                if (!general2CollectionNew.contains(general2CollectionOldGeneral2))
                {
                    if (illegalOrphanMessages == null)
                    {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain General2 " + general2CollectionOldGeneral2 + " since its idSpec field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null)
            {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<General> attachedGeneralCollectionNew = new ArrayList<General>();
            for (General generalCollectionNewGeneralToAttach : generalCollectionNew)
            {
                generalCollectionNewGeneralToAttach = em.getReference(generalCollectionNewGeneralToAttach.getClass(), generalCollectionNewGeneralToAttach.getIdUnion());
                attachedGeneralCollectionNew.add(generalCollectionNewGeneralToAttach);
            }
            generalCollectionNew = attachedGeneralCollectionNew;
            specialization.setGeneralCollection(generalCollectionNew);
            Collection<General2> attachedGeneral2CollectionNew = new ArrayList<General2>();
            for (General2 general2CollectionNewGeneral2ToAttach : general2CollectionNew)
            {
                general2CollectionNewGeneral2ToAttach = em.getReference(general2CollectionNewGeneral2ToAttach.getClass(), general2CollectionNewGeneral2ToAttach.getIdUnion());
                attachedGeneral2CollectionNew.add(general2CollectionNewGeneral2ToAttach);
            }
            general2CollectionNew = attachedGeneral2CollectionNew;
            specialization.setGeneral2Collection(general2CollectionNew);
            specialization = em.merge(specialization);
            for (General generalCollectionNewGeneral : generalCollectionNew)
            {
                if (!generalCollectionOld.contains(generalCollectionNewGeneral))
                {
                    Specialization oldIdSpecOfGeneralCollectionNewGeneral = generalCollectionNewGeneral.getIdSpec();
                    generalCollectionNewGeneral.setIdSpec(specialization);
                    generalCollectionNewGeneral = em.merge(generalCollectionNewGeneral);
                    if (oldIdSpecOfGeneralCollectionNewGeneral != null && !oldIdSpecOfGeneralCollectionNewGeneral.equals(specialization))
                    {
                        oldIdSpecOfGeneralCollectionNewGeneral.getGeneralCollection().remove(generalCollectionNewGeneral);
                        oldIdSpecOfGeneralCollectionNewGeneral = em.merge(oldIdSpecOfGeneralCollectionNewGeneral);
                    }
                }
            }
            for (General2 general2CollectionNewGeneral2 : general2CollectionNew)
            {
                if (!general2CollectionOld.contains(general2CollectionNewGeneral2))
                {
                    Specialization oldIdSpecOfGeneral2CollectionNewGeneral2 = general2CollectionNewGeneral2.getIdSpec();
                    general2CollectionNewGeneral2.setIdSpec(specialization);
                    general2CollectionNewGeneral2 = em.merge(general2CollectionNewGeneral2);
                    if (oldIdSpecOfGeneral2CollectionNewGeneral2 != null && !oldIdSpecOfGeneral2CollectionNewGeneral2.equals(specialization))
                    {
                        oldIdSpecOfGeneral2CollectionNewGeneral2.getGeneral2Collection().remove(general2CollectionNewGeneral2);
                        oldIdSpecOfGeneral2CollectionNewGeneral2 = em.merge(oldIdSpecOfGeneral2CollectionNewGeneral2);
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
            Collection<General> generalCollectionOrphanCheck = specialization.getGeneralCollection();
            for (General generalCollectionOrphanCheckGeneral : generalCollectionOrphanCheck)
            {
                if (illegalOrphanMessages == null)
                {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Specialization (" + specialization + ") cannot be destroyed since the General " + generalCollectionOrphanCheckGeneral + " in its generalCollection field has a non-nullable idSpec field.");
            }
            Collection<General2> general2CollectionOrphanCheck = specialization.getGeneral2Collection();
            for (General2 general2CollectionOrphanCheckGeneral2 : general2CollectionOrphanCheck)
            {
                if (illegalOrphanMessages == null)
                {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Specialization (" + specialization + ") cannot be destroyed since the General2 " + general2CollectionOrphanCheckGeneral2 + " in its general2Collection field has a non-nullable idSpec field.");
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
