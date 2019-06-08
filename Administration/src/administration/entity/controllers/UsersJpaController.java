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
        if (users.getGeneralCollection() == null)
        {
            users.setGeneralCollection(new ArrayList<General>());
        }
        if (users.getGeneral2Collection() == null)
        {
            users.setGeneral2Collection(new ArrayList<General2>());
        }
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<General> attachedGeneralCollection = new ArrayList<General>();
            for (General generalCollectionGeneralToAttach : users.getGeneralCollection())
            {
                generalCollectionGeneralToAttach = em.getReference(generalCollectionGeneralToAttach.getClass(), generalCollectionGeneralToAttach.getIdUnion());
                attachedGeneralCollection.add(generalCollectionGeneralToAttach);
            }
            users.setGeneralCollection(attachedGeneralCollection);
            Collection<General2> attachedGeneral2Collection = new ArrayList<General2>();
            for (General2 general2CollectionGeneral2ToAttach : users.getGeneral2Collection())
            {
                general2CollectionGeneral2ToAttach = em.getReference(general2CollectionGeneral2ToAttach.getClass(), general2CollectionGeneral2ToAttach.getIdUnion());
                attachedGeneral2Collection.add(general2CollectionGeneral2ToAttach);
            }
            users.setGeneral2Collection(attachedGeneral2Collection);
            em.persist(users);
            for (General generalCollectionGeneral : users.getGeneralCollection())
            {
                Users oldIdUserOfGeneralCollectionGeneral = generalCollectionGeneral.getIdUser();
                generalCollectionGeneral.setIdUser(users);
                generalCollectionGeneral = em.merge(generalCollectionGeneral);
                if (oldIdUserOfGeneralCollectionGeneral != null)
                {
                    oldIdUserOfGeneralCollectionGeneral.getGeneralCollection().remove(generalCollectionGeneral);
                    oldIdUserOfGeneralCollectionGeneral = em.merge(oldIdUserOfGeneralCollectionGeneral);
                }
            }
            for (General2 general2CollectionGeneral2 : users.getGeneral2Collection())
            {
                Users oldIdUserOfGeneral2CollectionGeneral2 = general2CollectionGeneral2.getIdUser();
                general2CollectionGeneral2.setIdUser(users);
                general2CollectionGeneral2 = em.merge(general2CollectionGeneral2);
                if (oldIdUserOfGeneral2CollectionGeneral2 != null)
                {
                    oldIdUserOfGeneral2CollectionGeneral2.getGeneral2Collection().remove(general2CollectionGeneral2);
                    oldIdUserOfGeneral2CollectionGeneral2 = em.merge(oldIdUserOfGeneral2CollectionGeneral2);
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
            Collection<General> generalCollectionOld = persistentUsers.getGeneralCollection();
            Collection<General> generalCollectionNew = users.getGeneralCollection();
            Collection<General2> general2CollectionOld = persistentUsers.getGeneral2Collection();
            Collection<General2> general2CollectionNew = users.getGeneral2Collection();
            List<String> illegalOrphanMessages = null;
            for (General generalCollectionOldGeneral : generalCollectionOld)
            {
                if (!generalCollectionNew.contains(generalCollectionOldGeneral))
                {
                    if (illegalOrphanMessages == null)
                    {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain General " + generalCollectionOldGeneral + " since its idUser field is not nullable.");
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
                    illegalOrphanMessages.add("You must retain General2 " + general2CollectionOldGeneral2 + " since its idUser field is not nullable.");
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
            users.setGeneralCollection(generalCollectionNew);
            Collection<General2> attachedGeneral2CollectionNew = new ArrayList<General2>();
            for (General2 general2CollectionNewGeneral2ToAttach : general2CollectionNew)
            {
                general2CollectionNewGeneral2ToAttach = em.getReference(general2CollectionNewGeneral2ToAttach.getClass(), general2CollectionNewGeneral2ToAttach.getIdUnion());
                attachedGeneral2CollectionNew.add(general2CollectionNewGeneral2ToAttach);
            }
            general2CollectionNew = attachedGeneral2CollectionNew;
            users.setGeneral2Collection(general2CollectionNew);
            users = em.merge(users);
            for (General generalCollectionNewGeneral : generalCollectionNew)
            {
                if (!generalCollectionOld.contains(generalCollectionNewGeneral))
                {
                    Users oldIdUserOfGeneralCollectionNewGeneral = generalCollectionNewGeneral.getIdUser();
                    generalCollectionNewGeneral.setIdUser(users);
                    generalCollectionNewGeneral = em.merge(generalCollectionNewGeneral);
                    if (oldIdUserOfGeneralCollectionNewGeneral != null && !oldIdUserOfGeneralCollectionNewGeneral.equals(users))
                    {
                        oldIdUserOfGeneralCollectionNewGeneral.getGeneralCollection().remove(generalCollectionNewGeneral);
                        oldIdUserOfGeneralCollectionNewGeneral = em.merge(oldIdUserOfGeneralCollectionNewGeneral);
                    }
                }
            }
            for (General2 general2CollectionNewGeneral2 : general2CollectionNew)
            {
                if (!general2CollectionOld.contains(general2CollectionNewGeneral2))
                {
                    Users oldIdUserOfGeneral2CollectionNewGeneral2 = general2CollectionNewGeneral2.getIdUser();
                    general2CollectionNewGeneral2.setIdUser(users);
                    general2CollectionNewGeneral2 = em.merge(general2CollectionNewGeneral2);
                    if (oldIdUserOfGeneral2CollectionNewGeneral2 != null && !oldIdUserOfGeneral2CollectionNewGeneral2.equals(users))
                    {
                        oldIdUserOfGeneral2CollectionNewGeneral2.getGeneral2Collection().remove(general2CollectionNewGeneral2);
                        oldIdUserOfGeneral2CollectionNewGeneral2 = em.merge(oldIdUserOfGeneral2CollectionNewGeneral2);
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
            Collection<General> generalCollectionOrphanCheck = users.getGeneralCollection();
            for (General generalCollectionOrphanCheckGeneral : generalCollectionOrphanCheck)
            {
                if (illegalOrphanMessages == null)
                {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Users (" + users + ") cannot be destroyed since the General " + generalCollectionOrphanCheckGeneral + " in its generalCollection field has a non-nullable idUser field.");
            }
            Collection<General2> general2CollectionOrphanCheck = users.getGeneral2Collection();
            for (General2 general2CollectionOrphanCheckGeneral2 : general2CollectionOrphanCheck)
            {
                if (illegalOrphanMessages == null)
                {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Users (" + users + ") cannot be destroyed since the General2 " + general2CollectionOrphanCheckGeneral2 + " in its general2Collection field has a non-nullable idUser field.");
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
