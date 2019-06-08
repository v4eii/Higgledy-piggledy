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
import administration.entity.Statement;
import administration.entity.controllers.exceptions.IllegalOrphanException;
import administration.entity.controllers.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author мвидео
 */
public class StatementJpaController implements Serializable {

    public StatementJpaController(EntityManagerFactory emf)
    {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(Statement statement)
    {
        if (statement.getGeneralCollection() == null)
        {
            statement.setGeneralCollection(new ArrayList<General>());
        }
        if (statement.getGeneral2Collection() == null)
        {
            statement.setGeneral2Collection(new ArrayList<General2>());
        }
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<General> attachedGeneralCollection = new ArrayList<General>();
            for (General generalCollectionGeneralToAttach : statement.getGeneralCollection())
            {
                generalCollectionGeneralToAttach = em.getReference(generalCollectionGeneralToAttach.getClass(), generalCollectionGeneralToAttach.getIdUnion());
                attachedGeneralCollection.add(generalCollectionGeneralToAttach);
            }
            statement.setGeneralCollection(attachedGeneralCollection);
            Collection<General2> attachedGeneral2Collection = new ArrayList<General2>();
            for (General2 general2CollectionGeneral2ToAttach : statement.getGeneral2Collection())
            {
                general2CollectionGeneral2ToAttach = em.getReference(general2CollectionGeneral2ToAttach.getClass(), general2CollectionGeneral2ToAttach.getIdUnion());
                attachedGeneral2Collection.add(general2CollectionGeneral2ToAttach);
            }
            statement.setGeneral2Collection(attachedGeneral2Collection);
            em.persist(statement);
            for (General generalCollectionGeneral : statement.getGeneralCollection())
            {
                Statement oldIdStOfGeneralCollectionGeneral = generalCollectionGeneral.getIdSt();
                generalCollectionGeneral.setIdSt(statement);
                generalCollectionGeneral = em.merge(generalCollectionGeneral);
                if (oldIdStOfGeneralCollectionGeneral != null)
                {
                    oldIdStOfGeneralCollectionGeneral.getGeneralCollection().remove(generalCollectionGeneral);
                    oldIdStOfGeneralCollectionGeneral = em.merge(oldIdStOfGeneralCollectionGeneral);
                }
            }
            for (General2 general2CollectionGeneral2 : statement.getGeneral2Collection())
            {
                Statement oldIdStOfGeneral2CollectionGeneral2 = general2CollectionGeneral2.getIdSt();
                general2CollectionGeneral2.setIdSt(statement);
                general2CollectionGeneral2 = em.merge(general2CollectionGeneral2);
                if (oldIdStOfGeneral2CollectionGeneral2 != null)
                {
                    oldIdStOfGeneral2CollectionGeneral2.getGeneral2Collection().remove(general2CollectionGeneral2);
                    oldIdStOfGeneral2CollectionGeneral2 = em.merge(oldIdStOfGeneral2CollectionGeneral2);
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

    public void edit(Statement statement) throws IllegalOrphanException, NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Statement persistentStatement = em.find(Statement.class, statement.getIdSt());
            Collection<General> generalCollectionOld = persistentStatement.getGeneralCollection();
            Collection<General> generalCollectionNew = statement.getGeneralCollection();
            Collection<General2> general2CollectionOld = persistentStatement.getGeneral2Collection();
            Collection<General2> general2CollectionNew = statement.getGeneral2Collection();
            List<String> illegalOrphanMessages = null;
            for (General generalCollectionOldGeneral : generalCollectionOld)
            {
                if (!generalCollectionNew.contains(generalCollectionOldGeneral))
                {
                    if (illegalOrphanMessages == null)
                    {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain General " + generalCollectionOldGeneral + " since its idSt field is not nullable.");
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
                    illegalOrphanMessages.add("You must retain General2 " + general2CollectionOldGeneral2 + " since its idSt field is not nullable.");
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
            statement.setGeneralCollection(generalCollectionNew);
            Collection<General2> attachedGeneral2CollectionNew = new ArrayList<General2>();
            for (General2 general2CollectionNewGeneral2ToAttach : general2CollectionNew)
            {
                general2CollectionNewGeneral2ToAttach = em.getReference(general2CollectionNewGeneral2ToAttach.getClass(), general2CollectionNewGeneral2ToAttach.getIdUnion());
                attachedGeneral2CollectionNew.add(general2CollectionNewGeneral2ToAttach);
            }
            general2CollectionNew = attachedGeneral2CollectionNew;
            statement.setGeneral2Collection(general2CollectionNew);
            statement = em.merge(statement);
            for (General generalCollectionNewGeneral : generalCollectionNew)
            {
                if (!generalCollectionOld.contains(generalCollectionNewGeneral))
                {
                    Statement oldIdStOfGeneralCollectionNewGeneral = generalCollectionNewGeneral.getIdSt();
                    generalCollectionNewGeneral.setIdSt(statement);
                    generalCollectionNewGeneral = em.merge(generalCollectionNewGeneral);
                    if (oldIdStOfGeneralCollectionNewGeneral != null && !oldIdStOfGeneralCollectionNewGeneral.equals(statement))
                    {
                        oldIdStOfGeneralCollectionNewGeneral.getGeneralCollection().remove(generalCollectionNewGeneral);
                        oldIdStOfGeneralCollectionNewGeneral = em.merge(oldIdStOfGeneralCollectionNewGeneral);
                    }
                }
            }
            for (General2 general2CollectionNewGeneral2 : general2CollectionNew)
            {
                if (!general2CollectionOld.contains(general2CollectionNewGeneral2))
                {
                    Statement oldIdStOfGeneral2CollectionNewGeneral2 = general2CollectionNewGeneral2.getIdSt();
                    general2CollectionNewGeneral2.setIdSt(statement);
                    general2CollectionNewGeneral2 = em.merge(general2CollectionNewGeneral2);
                    if (oldIdStOfGeneral2CollectionNewGeneral2 != null && !oldIdStOfGeneral2CollectionNewGeneral2.equals(statement))
                    {
                        oldIdStOfGeneral2CollectionNewGeneral2.getGeneral2Collection().remove(general2CollectionNewGeneral2);
                        oldIdStOfGeneral2CollectionNewGeneral2 = em.merge(oldIdStOfGeneral2CollectionNewGeneral2);
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
                Integer id = statement.getIdSt();
                if (findStatement(id) == null)
                {
                    throw new NonexistentEntityException("The statement with id " + id + " no longer exists.");
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
            Statement statement;
            try
            {
                statement = em.getReference(Statement.class, id);
                statement.getIdSt();
            }
            catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The statement with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<General> generalCollectionOrphanCheck = statement.getGeneralCollection();
            for (General generalCollectionOrphanCheckGeneral : generalCollectionOrphanCheck)
            {
                if (illegalOrphanMessages == null)
                {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Statement (" + statement + ") cannot be destroyed since the General " + generalCollectionOrphanCheckGeneral + " in its generalCollection field has a non-nullable idSt field.");
            }
            Collection<General2> general2CollectionOrphanCheck = statement.getGeneral2Collection();
            for (General2 general2CollectionOrphanCheckGeneral2 : general2CollectionOrphanCheck)
            {
                if (illegalOrphanMessages == null)
                {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Statement (" + statement + ") cannot be destroyed since the General2 " + general2CollectionOrphanCheckGeneral2 + " in its general2Collection field has a non-nullable idSt field.");
            }
            if (illegalOrphanMessages != null)
            {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(statement);
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

    public List<Statement> findStatementEntities()
    {
        return findStatementEntities(true, -1, -1);
    }

    public List<Statement> findStatementEntities(int maxResults, int firstResult)
    {
        return findStatementEntities(false, maxResults, firstResult);
    }

    private List<Statement> findStatementEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Statement.class));
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

    public Statement findStatement(Integer id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(Statement.class, id);
        }
        finally
        {
            em.close();
        }
    }

    public int getStatementCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Statement> rt = cq.from(Statement.class);
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
