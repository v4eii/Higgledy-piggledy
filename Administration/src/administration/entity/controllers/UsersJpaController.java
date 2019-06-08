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
import administration.entity.Users;
import administration.entity.controllers.exceptions.IllegalOrphanException;
import administration.entity.controllers.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author мвидео
 */
public class UsersJpaController implements Serializable {

    public UsersJpaController(EntityManagerFactory emf)
    {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(Users users)
    {
        if (users.getGeneralTradeCollection() == null)
        {
            users.setGeneralTradeCollection(new ArrayList<GeneralTrade>());
        }
        if (users.getGeneralCafeCollection() == null)
        {
            users.setGeneralCafeCollection(new ArrayList<GeneralCafe>());
        }
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<GeneralTrade> attachedGeneralTradeCollection = new ArrayList<GeneralTrade>();
            for (GeneralTrade generalTradeCollectionGeneralTradeToAttach : users.getGeneralTradeCollection())
            {
                generalTradeCollectionGeneralTradeToAttach = em.getReference(generalTradeCollectionGeneralTradeToAttach.getClass(), generalTradeCollectionGeneralTradeToAttach.getIdUnion());
                attachedGeneralTradeCollection.add(generalTradeCollectionGeneralTradeToAttach);
            }
            users.setGeneralTradeCollection(attachedGeneralTradeCollection);
            Collection<GeneralCafe> attachedGeneralCafeCollection = new ArrayList<GeneralCafe>();
            for (GeneralCafe generalCafeCollectionGeneralCafeToAttach : users.getGeneralCafeCollection())
            {
                generalCafeCollectionGeneralCafeToAttach = em.getReference(generalCafeCollectionGeneralCafeToAttach.getClass(), generalCafeCollectionGeneralCafeToAttach.getIdUnion());
                attachedGeneralCafeCollection.add(generalCafeCollectionGeneralCafeToAttach);
            }
            users.setGeneralCafeCollection(attachedGeneralCafeCollection);
            em.persist(users);
            for (GeneralTrade generalTradeCollectionGeneralTrade : users.getGeneralTradeCollection())
            {
                Users oldIdUserOfGeneralTradeCollectionGeneralTrade = generalTradeCollectionGeneralTrade.getIdUser();
                generalTradeCollectionGeneralTrade.setIdUser(users);
                generalTradeCollectionGeneralTrade = em.merge(generalTradeCollectionGeneralTrade);
                if (oldIdUserOfGeneralTradeCollectionGeneralTrade != null)
                {
                    oldIdUserOfGeneralTradeCollectionGeneralTrade.getGeneralTradeCollection().remove(generalTradeCollectionGeneralTrade);
                    oldIdUserOfGeneralTradeCollectionGeneralTrade = em.merge(oldIdUserOfGeneralTradeCollectionGeneralTrade);
                }
            }
            for (GeneralCafe generalCafeCollectionGeneralCafe : users.getGeneralCafeCollection())
            {
                Users oldIdUserOfGeneralCafeCollectionGeneralCafe = generalCafeCollectionGeneralCafe.getIdUser();
                generalCafeCollectionGeneralCafe.setIdUser(users);
                generalCafeCollectionGeneralCafe = em.merge(generalCafeCollectionGeneralCafe);
                if (oldIdUserOfGeneralCafeCollectionGeneralCafe != null)
                {
                    oldIdUserOfGeneralCafeCollectionGeneralCafe.getGeneralCafeCollection().remove(generalCafeCollectionGeneralCafe);
                    oldIdUserOfGeneralCafeCollectionGeneralCafe = em.merge(oldIdUserOfGeneralCafeCollectionGeneralCafe);
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

    public void edit(Users users) throws IllegalOrphanException, NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Users persistentUsers = em.find(Users.class, users.getIdUser());
            Collection<GeneralTrade> generalTradeCollectionOld = persistentUsers.getGeneralTradeCollection();
            Collection<GeneralTrade> generalTradeCollectionNew = users.getGeneralTradeCollection();
            Collection<GeneralCafe> generalCafeCollectionOld = persistentUsers.getGeneralCafeCollection();
            Collection<GeneralCafe> generalCafeCollectionNew = users.getGeneralCafeCollection();
            List<String> illegalOrphanMessages = null;
            for (GeneralTrade generalTradeCollectionOldGeneralTrade : generalTradeCollectionOld)
            {
                if (!generalTradeCollectionNew.contains(generalTradeCollectionOldGeneralTrade))
                {
                    if (illegalOrphanMessages == null)
                    {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain GeneralTrade " + generalTradeCollectionOldGeneralTrade + " since its idUser field is not nullable.");
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
                    illegalOrphanMessages.add("You must retain GeneralCafe " + generalCafeCollectionOldGeneralCafe + " since its idUser field is not nullable.");
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
            users.setGeneralTradeCollection(generalTradeCollectionNew);
            Collection<GeneralCafe> attachedGeneralCafeCollectionNew = new ArrayList<GeneralCafe>();
            for (GeneralCafe generalCafeCollectionNewGeneralCafeToAttach : generalCafeCollectionNew)
            {
                generalCafeCollectionNewGeneralCafeToAttach = em.getReference(generalCafeCollectionNewGeneralCafeToAttach.getClass(), generalCafeCollectionNewGeneralCafeToAttach.getIdUnion());
                attachedGeneralCafeCollectionNew.add(generalCafeCollectionNewGeneralCafeToAttach);
            }
            generalCafeCollectionNew = attachedGeneralCafeCollectionNew;
            users.setGeneralCafeCollection(generalCafeCollectionNew);
            users = em.merge(users);
            for (GeneralTrade generalTradeCollectionNewGeneralTrade : generalTradeCollectionNew)
            {
                if (!generalTradeCollectionOld.contains(generalTradeCollectionNewGeneralTrade))
                {
                    Users oldIdUserOfGeneralTradeCollectionNewGeneralTrade = generalTradeCollectionNewGeneralTrade.getIdUser();
                    generalTradeCollectionNewGeneralTrade.setIdUser(users);
                    generalTradeCollectionNewGeneralTrade = em.merge(generalTradeCollectionNewGeneralTrade);
                    if (oldIdUserOfGeneralTradeCollectionNewGeneralTrade != null && !oldIdUserOfGeneralTradeCollectionNewGeneralTrade.equals(users))
                    {
                        oldIdUserOfGeneralTradeCollectionNewGeneralTrade.getGeneralTradeCollection().remove(generalTradeCollectionNewGeneralTrade);
                        oldIdUserOfGeneralTradeCollectionNewGeneralTrade = em.merge(oldIdUserOfGeneralTradeCollectionNewGeneralTrade);
                    }
                }
            }
            for (GeneralCafe generalCafeCollectionNewGeneralCafe : generalCafeCollectionNew)
            {
                if (!generalCafeCollectionOld.contains(generalCafeCollectionNewGeneralCafe))
                {
                    Users oldIdUserOfGeneralCafeCollectionNewGeneralCafe = generalCafeCollectionNewGeneralCafe.getIdUser();
                    generalCafeCollectionNewGeneralCafe.setIdUser(users);
                    generalCafeCollectionNewGeneralCafe = em.merge(generalCafeCollectionNewGeneralCafe);
                    if (oldIdUserOfGeneralCafeCollectionNewGeneralCafe != null && !oldIdUserOfGeneralCafeCollectionNewGeneralCafe.equals(users))
                    {
                        oldIdUserOfGeneralCafeCollectionNewGeneralCafe.getGeneralCafeCollection().remove(generalCafeCollectionNewGeneralCafe);
                        oldIdUserOfGeneralCafeCollectionNewGeneralCafe = em.merge(oldIdUserOfGeneralCafeCollectionNewGeneralCafe);
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
                Integer id = users.getIdUser();
                if (findUsers(id) == null)
                {
                    throw new NonexistentEntityException("The users with id " + id + " no longer exists.");
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
            Users users;
            try
            {
                users = em.getReference(Users.class, id);
                users.getIdUser();
            }
            catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The users with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<GeneralTrade> generalTradeCollectionOrphanCheck = users.getGeneralTradeCollection();
            for (GeneralTrade generalTradeCollectionOrphanCheckGeneralTrade : generalTradeCollectionOrphanCheck)
            {
                if (illegalOrphanMessages == null)
                {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Users (" + users + ") cannot be destroyed since the GeneralTrade " + generalTradeCollectionOrphanCheckGeneralTrade + " in its generalTradeCollection field has a non-nullable idUser field.");
            }
            Collection<GeneralCafe> generalCafeCollectionOrphanCheck = users.getGeneralCafeCollection();
            for (GeneralCafe generalCafeCollectionOrphanCheckGeneralCafe : generalCafeCollectionOrphanCheck)
            {
                if (illegalOrphanMessages == null)
                {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Users (" + users + ") cannot be destroyed since the GeneralCafe " + generalCafeCollectionOrphanCheckGeneralCafe + " in its generalCafeCollection field has a non-nullable idUser field.");
            }
            if (illegalOrphanMessages != null)
            {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(users);
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

    public List<Users> findUsersEntities()
    {
        return findUsersEntities(true, -1, -1);
    }

    public List<Users> findUsersEntities(int maxResults, int firstResult)
    {
        return findUsersEntities(false, maxResults, firstResult);
    }

    private List<Users> findUsersEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Users.class));
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

    public Users findUsers(Integer id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(Users.class, id);
        }
        finally
        {
            em.close();
        }
    }

    public int getUsersCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Users> rt = cq.from(Users.class);
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
