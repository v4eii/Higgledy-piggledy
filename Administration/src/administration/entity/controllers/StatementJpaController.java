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
import administration.entity.Street;
import administration.entity.GeneralTrade;
import java.util.ArrayList;
import java.util.Collection;
import administration.entity.GeneralCafe;
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
        if (statement.getGeneralTradeCollection() == null)
        {
            statement.setGeneralTradeCollection(new ArrayList<GeneralTrade>());
        }
        if (statement.getGeneralCafeCollection() == null)
        {
            statement.setGeneralCafeCollection(new ArrayList<GeneralCafe>());
        }
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Street idAdr = statement.getIdAdr();
            if (idAdr != null)
            {
                idAdr = em.getReference(idAdr.getClass(), idAdr.getIdAdr());
                statement.setIdAdr(idAdr);
            }
            Collection<GeneralTrade> attachedGeneralTradeCollection = new ArrayList<GeneralTrade>();
            for (GeneralTrade generalTradeCollectionGeneralTradeToAttach : statement.getGeneralTradeCollection())
            {
                generalTradeCollectionGeneralTradeToAttach = em.getReference(generalTradeCollectionGeneralTradeToAttach.getClass(), generalTradeCollectionGeneralTradeToAttach.getIdUnion());
                attachedGeneralTradeCollection.add(generalTradeCollectionGeneralTradeToAttach);
            }
            statement.setGeneralTradeCollection(attachedGeneralTradeCollection);
            Collection<GeneralCafe> attachedGeneralCafeCollection = new ArrayList<GeneralCafe>();
            for (GeneralCafe generalCafeCollectionGeneralCafeToAttach : statement.getGeneralCafeCollection())
            {
                generalCafeCollectionGeneralCafeToAttach = em.getReference(generalCafeCollectionGeneralCafeToAttach.getClass(), generalCafeCollectionGeneralCafeToAttach.getIdUnion());
                attachedGeneralCafeCollection.add(generalCafeCollectionGeneralCafeToAttach);
            }
            statement.setGeneralCafeCollection(attachedGeneralCafeCollection);
            em.persist(statement);
            if (idAdr != null)
            {
                idAdr.getStatementCollection().add(statement);
                idAdr = em.merge(idAdr);
            }
            for (GeneralTrade generalTradeCollectionGeneralTrade : statement.getGeneralTradeCollection())
            {
                Statement oldIdStOfGeneralTradeCollectionGeneralTrade = generalTradeCollectionGeneralTrade.getIdSt();
                generalTradeCollectionGeneralTrade.setIdSt(statement);
                generalTradeCollectionGeneralTrade = em.merge(generalTradeCollectionGeneralTrade);
                if (oldIdStOfGeneralTradeCollectionGeneralTrade != null)
                {
                    oldIdStOfGeneralTradeCollectionGeneralTrade.getGeneralTradeCollection().remove(generalTradeCollectionGeneralTrade);
                    oldIdStOfGeneralTradeCollectionGeneralTrade = em.merge(oldIdStOfGeneralTradeCollectionGeneralTrade);
                }
            }
            for (GeneralCafe generalCafeCollectionGeneralCafe : statement.getGeneralCafeCollection())
            {
                Statement oldIdStOfGeneralCafeCollectionGeneralCafe = generalCafeCollectionGeneralCafe.getIdSt();
                generalCafeCollectionGeneralCafe.setIdSt(statement);
                generalCafeCollectionGeneralCafe = em.merge(generalCafeCollectionGeneralCafe);
                if (oldIdStOfGeneralCafeCollectionGeneralCafe != null)
                {
                    oldIdStOfGeneralCafeCollectionGeneralCafe.getGeneralCafeCollection().remove(generalCafeCollectionGeneralCafe);
                    oldIdStOfGeneralCafeCollectionGeneralCafe = em.merge(oldIdStOfGeneralCafeCollectionGeneralCafe);
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
            Street idAdrOld = persistentStatement.getIdAdr();
            Street idAdrNew = statement.getIdAdr();
            Collection<GeneralTrade> generalTradeCollectionOld = persistentStatement.getGeneralTradeCollection();
            Collection<GeneralTrade> generalTradeCollectionNew = statement.getGeneralTradeCollection();
            Collection<GeneralCafe> generalCafeCollectionOld = persistentStatement.getGeneralCafeCollection();
            Collection<GeneralCafe> generalCafeCollectionNew = statement.getGeneralCafeCollection();
            List<String> illegalOrphanMessages = null;
            for (GeneralTrade generalTradeCollectionOldGeneralTrade : generalTradeCollectionOld)
            {
                if (!generalTradeCollectionNew.contains(generalTradeCollectionOldGeneralTrade))
                {
                    if (illegalOrphanMessages == null)
                    {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain GeneralTrade " + generalTradeCollectionOldGeneralTrade + " since its idSt field is not nullable.");
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
                    illegalOrphanMessages.add("You must retain GeneralCafe " + generalCafeCollectionOldGeneralCafe + " since its idSt field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null)
            {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idAdrNew != null)
            {
                idAdrNew = em.getReference(idAdrNew.getClass(), idAdrNew.getIdAdr());
                statement.setIdAdr(idAdrNew);
            }
            Collection<GeneralTrade> attachedGeneralTradeCollectionNew = new ArrayList<GeneralTrade>();
            for (GeneralTrade generalTradeCollectionNewGeneralTradeToAttach : generalTradeCollectionNew)
            {
                generalTradeCollectionNewGeneralTradeToAttach = em.getReference(generalTradeCollectionNewGeneralTradeToAttach.getClass(), generalTradeCollectionNewGeneralTradeToAttach.getIdUnion());
                attachedGeneralTradeCollectionNew.add(generalTradeCollectionNewGeneralTradeToAttach);
            }
            generalTradeCollectionNew = attachedGeneralTradeCollectionNew;
            statement.setGeneralTradeCollection(generalTradeCollectionNew);
            Collection<GeneralCafe> attachedGeneralCafeCollectionNew = new ArrayList<GeneralCafe>();
            for (GeneralCafe generalCafeCollectionNewGeneralCafeToAttach : generalCafeCollectionNew)
            {
                generalCafeCollectionNewGeneralCafeToAttach = em.getReference(generalCafeCollectionNewGeneralCafeToAttach.getClass(), generalCafeCollectionNewGeneralCafeToAttach.getIdUnion());
                attachedGeneralCafeCollectionNew.add(generalCafeCollectionNewGeneralCafeToAttach);
            }
            generalCafeCollectionNew = attachedGeneralCafeCollectionNew;
            statement.setGeneralCafeCollection(generalCafeCollectionNew);
            statement = em.merge(statement);
            if (idAdrOld != null && !idAdrOld.equals(idAdrNew))
            {
                idAdrOld.getStatementCollection().remove(statement);
                idAdrOld = em.merge(idAdrOld);
            }
            if (idAdrNew != null && !idAdrNew.equals(idAdrOld))
            {
                idAdrNew.getStatementCollection().add(statement);
                idAdrNew = em.merge(idAdrNew);
            }
            for (GeneralTrade generalTradeCollectionNewGeneralTrade : generalTradeCollectionNew)
            {
                if (!generalTradeCollectionOld.contains(generalTradeCollectionNewGeneralTrade))
                {
                    Statement oldIdStOfGeneralTradeCollectionNewGeneralTrade = generalTradeCollectionNewGeneralTrade.getIdSt();
                    generalTradeCollectionNewGeneralTrade.setIdSt(statement);
                    generalTradeCollectionNewGeneralTrade = em.merge(generalTradeCollectionNewGeneralTrade);
                    if (oldIdStOfGeneralTradeCollectionNewGeneralTrade != null && !oldIdStOfGeneralTradeCollectionNewGeneralTrade.equals(statement))
                    {
                        oldIdStOfGeneralTradeCollectionNewGeneralTrade.getGeneralTradeCollection().remove(generalTradeCollectionNewGeneralTrade);
                        oldIdStOfGeneralTradeCollectionNewGeneralTrade = em.merge(oldIdStOfGeneralTradeCollectionNewGeneralTrade);
                    }
                }
            }
            for (GeneralCafe generalCafeCollectionNewGeneralCafe : generalCafeCollectionNew)
            {
                if (!generalCafeCollectionOld.contains(generalCafeCollectionNewGeneralCafe))
                {
                    Statement oldIdStOfGeneralCafeCollectionNewGeneralCafe = generalCafeCollectionNewGeneralCafe.getIdSt();
                    generalCafeCollectionNewGeneralCafe.setIdSt(statement);
                    generalCafeCollectionNewGeneralCafe = em.merge(generalCafeCollectionNewGeneralCafe);
                    if (oldIdStOfGeneralCafeCollectionNewGeneralCafe != null && !oldIdStOfGeneralCafeCollectionNewGeneralCafe.equals(statement))
                    {
                        oldIdStOfGeneralCafeCollectionNewGeneralCafe.getGeneralCafeCollection().remove(generalCafeCollectionNewGeneralCafe);
                        oldIdStOfGeneralCafeCollectionNewGeneralCafe = em.merge(oldIdStOfGeneralCafeCollectionNewGeneralCafe);
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
            Collection<GeneralTrade> generalTradeCollectionOrphanCheck = statement.getGeneralTradeCollection();
            for (GeneralTrade generalTradeCollectionOrphanCheckGeneralTrade : generalTradeCollectionOrphanCheck)
            {
                if (illegalOrphanMessages == null)
                {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Statement (" + statement + ") cannot be destroyed since the GeneralTrade " + generalTradeCollectionOrphanCheckGeneralTrade + " in its generalTradeCollection field has a non-nullable idSt field.");
            }
            Collection<GeneralCafe> generalCafeCollectionOrphanCheck = statement.getGeneralCafeCollection();
            for (GeneralCafe generalCafeCollectionOrphanCheckGeneralCafe : generalCafeCollectionOrphanCheck)
            {
                if (illegalOrphanMessages == null)
                {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Statement (" + statement + ") cannot be destroyed since the GeneralCafe " + generalCafeCollectionOrphanCheckGeneralCafe + " in its generalCafeCollection field has a non-nullable idSt field.");
            }
            if (illegalOrphanMessages != null)
            {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Street idAdr = statement.getIdAdr();
            if (idAdr != null)
            {
                idAdr.getStatementCollection().remove(statement);
                idAdr = em.merge(idAdr);
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
