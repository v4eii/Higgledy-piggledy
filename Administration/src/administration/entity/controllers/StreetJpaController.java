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
import administration.entity.City;
import administration.entity.GeneralTrade;
import java.util.ArrayList;
import java.util.Collection;
import administration.entity.Statement;
import administration.entity.GeneralCafe;
import administration.entity.Street;
import administration.entity.controllers.exceptions.IllegalOrphanException;
import administration.entity.controllers.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author мвидео
 */
public class StreetJpaController implements Serializable {

    public StreetJpaController(EntityManagerFactory emf)
    {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(Street street)
    {
        if (street.getGeneralTradeCollection() == null)
        {
            street.setGeneralTradeCollection(new ArrayList<GeneralTrade>());
        }
        if (street.getStatementCollection() == null)
        {
            street.setStatementCollection(new ArrayList<Statement>());
        }
        if (street.getGeneralCafeCollection() == null)
        {
            street.setGeneralCafeCollection(new ArrayList<GeneralCafe>());
        }
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            City idCity = street.getIdCity();
            if (idCity != null)
            {
                idCity = em.getReference(idCity.getClass(), idCity.getIdCity());
                street.setIdCity(idCity);
            }
            Collection<GeneralTrade> attachedGeneralTradeCollection = new ArrayList<GeneralTrade>();
            for (GeneralTrade generalTradeCollectionGeneralTradeToAttach : street.getGeneralTradeCollection())
            {
                generalTradeCollectionGeneralTradeToAttach = em.getReference(generalTradeCollectionGeneralTradeToAttach.getClass(), generalTradeCollectionGeneralTradeToAttach.getIdUnion());
                attachedGeneralTradeCollection.add(generalTradeCollectionGeneralTradeToAttach);
            }
            street.setGeneralTradeCollection(attachedGeneralTradeCollection);
            Collection<Statement> attachedStatementCollection = new ArrayList<Statement>();
            for (Statement statementCollectionStatementToAttach : street.getStatementCollection())
            {
                statementCollectionStatementToAttach = em.getReference(statementCollectionStatementToAttach.getClass(), statementCollectionStatementToAttach.getIdSt());
                attachedStatementCollection.add(statementCollectionStatementToAttach);
            }
            street.setStatementCollection(attachedStatementCollection);
            Collection<GeneralCafe> attachedGeneralCafeCollection = new ArrayList<GeneralCafe>();
            for (GeneralCafe generalCafeCollectionGeneralCafeToAttach : street.getGeneralCafeCollection())
            {
                generalCafeCollectionGeneralCafeToAttach = em.getReference(generalCafeCollectionGeneralCafeToAttach.getClass(), generalCafeCollectionGeneralCafeToAttach.getIdUnion());
                attachedGeneralCafeCollection.add(generalCafeCollectionGeneralCafeToAttach);
            }
            street.setGeneralCafeCollection(attachedGeneralCafeCollection);
            em.persist(street);
            if (idCity != null)
            {
                idCity.getStreetCollection().add(street);
                idCity = em.merge(idCity);
            }
            for (GeneralTrade generalTradeCollectionGeneralTrade : street.getGeneralTradeCollection())
            {
                Street oldIdAdrOfGeneralTradeCollectionGeneralTrade = generalTradeCollectionGeneralTrade.getIdAdr();
                generalTradeCollectionGeneralTrade.setIdAdr(street);
                generalTradeCollectionGeneralTrade = em.merge(generalTradeCollectionGeneralTrade);
                if (oldIdAdrOfGeneralTradeCollectionGeneralTrade != null)
                {
                    oldIdAdrOfGeneralTradeCollectionGeneralTrade.getGeneralTradeCollection().remove(generalTradeCollectionGeneralTrade);
                    oldIdAdrOfGeneralTradeCollectionGeneralTrade = em.merge(oldIdAdrOfGeneralTradeCollectionGeneralTrade);
                }
            }
            for (Statement statementCollectionStatement : street.getStatementCollection())
            {
                Street oldIdAdrOfStatementCollectionStatement = statementCollectionStatement.getIdAdr();
                statementCollectionStatement.setIdAdr(street);
                statementCollectionStatement = em.merge(statementCollectionStatement);
                if (oldIdAdrOfStatementCollectionStatement != null)
                {
                    oldIdAdrOfStatementCollectionStatement.getStatementCollection().remove(statementCollectionStatement);
                    oldIdAdrOfStatementCollectionStatement = em.merge(oldIdAdrOfStatementCollectionStatement);
                }
            }
            for (GeneralCafe generalCafeCollectionGeneralCafe : street.getGeneralCafeCollection())
            {
                Street oldIdAdrOfGeneralCafeCollectionGeneralCafe = generalCafeCollectionGeneralCafe.getIdAdr();
                generalCafeCollectionGeneralCafe.setIdAdr(street);
                generalCafeCollectionGeneralCafe = em.merge(generalCafeCollectionGeneralCafe);
                if (oldIdAdrOfGeneralCafeCollectionGeneralCafe != null)
                {
                    oldIdAdrOfGeneralCafeCollectionGeneralCafe.getGeneralCafeCollection().remove(generalCafeCollectionGeneralCafe);
                    oldIdAdrOfGeneralCafeCollectionGeneralCafe = em.merge(oldIdAdrOfGeneralCafeCollectionGeneralCafe);
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

    public void edit(Street street) throws IllegalOrphanException, NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Street persistentStreet = em.find(Street.class, street.getIdAdr());
            City idCityOld = persistentStreet.getIdCity();
            City idCityNew = street.getIdCity();
            Collection<GeneralTrade> generalTradeCollectionOld = persistentStreet.getGeneralTradeCollection();
            Collection<GeneralTrade> generalTradeCollectionNew = street.getGeneralTradeCollection();
            Collection<Statement> statementCollectionOld = persistentStreet.getStatementCollection();
            Collection<Statement> statementCollectionNew = street.getStatementCollection();
            Collection<GeneralCafe> generalCafeCollectionOld = persistentStreet.getGeneralCafeCollection();
            Collection<GeneralCafe> generalCafeCollectionNew = street.getGeneralCafeCollection();
            List<String> illegalOrphanMessages = null;
            for (GeneralTrade generalTradeCollectionOldGeneralTrade : generalTradeCollectionOld)
            {
                if (!generalTradeCollectionNew.contains(generalTradeCollectionOldGeneralTrade))
                {
                    if (illegalOrphanMessages == null)
                    {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain GeneralTrade " + generalTradeCollectionOldGeneralTrade + " since its idAdr field is not nullable.");
                }
            }
            for (Statement statementCollectionOldStatement : statementCollectionOld)
            {
                if (!statementCollectionNew.contains(statementCollectionOldStatement))
                {
                    if (illegalOrphanMessages == null)
                    {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Statement " + statementCollectionOldStatement + " since its idAdr field is not nullable.");
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
                    illegalOrphanMessages.add("You must retain GeneralCafe " + generalCafeCollectionOldGeneralCafe + " since its idAdr field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null)
            {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idCityNew != null)
            {
                idCityNew = em.getReference(idCityNew.getClass(), idCityNew.getIdCity());
                street.setIdCity(idCityNew);
            }
            Collection<GeneralTrade> attachedGeneralTradeCollectionNew = new ArrayList<GeneralTrade>();
            for (GeneralTrade generalTradeCollectionNewGeneralTradeToAttach : generalTradeCollectionNew)
            {
                generalTradeCollectionNewGeneralTradeToAttach = em.getReference(generalTradeCollectionNewGeneralTradeToAttach.getClass(), generalTradeCollectionNewGeneralTradeToAttach.getIdUnion());
                attachedGeneralTradeCollectionNew.add(generalTradeCollectionNewGeneralTradeToAttach);
            }
            generalTradeCollectionNew = attachedGeneralTradeCollectionNew;
            street.setGeneralTradeCollection(generalTradeCollectionNew);
            Collection<Statement> attachedStatementCollectionNew = new ArrayList<Statement>();
            for (Statement statementCollectionNewStatementToAttach : statementCollectionNew)
            {
                statementCollectionNewStatementToAttach = em.getReference(statementCollectionNewStatementToAttach.getClass(), statementCollectionNewStatementToAttach.getIdSt());
                attachedStatementCollectionNew.add(statementCollectionNewStatementToAttach);
            }
            statementCollectionNew = attachedStatementCollectionNew;
            street.setStatementCollection(statementCollectionNew);
            Collection<GeneralCafe> attachedGeneralCafeCollectionNew = new ArrayList<GeneralCafe>();
            for (GeneralCafe generalCafeCollectionNewGeneralCafeToAttach : generalCafeCollectionNew)
            {
                generalCafeCollectionNewGeneralCafeToAttach = em.getReference(generalCafeCollectionNewGeneralCafeToAttach.getClass(), generalCafeCollectionNewGeneralCafeToAttach.getIdUnion());
                attachedGeneralCafeCollectionNew.add(generalCafeCollectionNewGeneralCafeToAttach);
            }
            generalCafeCollectionNew = attachedGeneralCafeCollectionNew;
            street.setGeneralCafeCollection(generalCafeCollectionNew);
            street = em.merge(street);
            if (idCityOld != null && !idCityOld.equals(idCityNew))
            {
                idCityOld.getStreetCollection().remove(street);
                idCityOld = em.merge(idCityOld);
            }
            if (idCityNew != null && !idCityNew.equals(idCityOld))
            {
                idCityNew.getStreetCollection().add(street);
                idCityNew = em.merge(idCityNew);
            }
            for (GeneralTrade generalTradeCollectionNewGeneralTrade : generalTradeCollectionNew)
            {
                if (!generalTradeCollectionOld.contains(generalTradeCollectionNewGeneralTrade))
                {
                    Street oldIdAdrOfGeneralTradeCollectionNewGeneralTrade = generalTradeCollectionNewGeneralTrade.getIdAdr();
                    generalTradeCollectionNewGeneralTrade.setIdAdr(street);
                    generalTradeCollectionNewGeneralTrade = em.merge(generalTradeCollectionNewGeneralTrade);
                    if (oldIdAdrOfGeneralTradeCollectionNewGeneralTrade != null && !oldIdAdrOfGeneralTradeCollectionNewGeneralTrade.equals(street))
                    {
                        oldIdAdrOfGeneralTradeCollectionNewGeneralTrade.getGeneralTradeCollection().remove(generalTradeCollectionNewGeneralTrade);
                        oldIdAdrOfGeneralTradeCollectionNewGeneralTrade = em.merge(oldIdAdrOfGeneralTradeCollectionNewGeneralTrade);
                    }
                }
            }
            for (Statement statementCollectionNewStatement : statementCollectionNew)
            {
                if (!statementCollectionOld.contains(statementCollectionNewStatement))
                {
                    Street oldIdAdrOfStatementCollectionNewStatement = statementCollectionNewStatement.getIdAdr();
                    statementCollectionNewStatement.setIdAdr(street);
                    statementCollectionNewStatement = em.merge(statementCollectionNewStatement);
                    if (oldIdAdrOfStatementCollectionNewStatement != null && !oldIdAdrOfStatementCollectionNewStatement.equals(street))
                    {
                        oldIdAdrOfStatementCollectionNewStatement.getStatementCollection().remove(statementCollectionNewStatement);
                        oldIdAdrOfStatementCollectionNewStatement = em.merge(oldIdAdrOfStatementCollectionNewStatement);
                    }
                }
            }
            for (GeneralCafe generalCafeCollectionNewGeneralCafe : generalCafeCollectionNew)
            {
                if (!generalCafeCollectionOld.contains(generalCafeCollectionNewGeneralCafe))
                {
                    Street oldIdAdrOfGeneralCafeCollectionNewGeneralCafe = generalCafeCollectionNewGeneralCafe.getIdAdr();
                    generalCafeCollectionNewGeneralCafe.setIdAdr(street);
                    generalCafeCollectionNewGeneralCafe = em.merge(generalCafeCollectionNewGeneralCafe);
                    if (oldIdAdrOfGeneralCafeCollectionNewGeneralCafe != null && !oldIdAdrOfGeneralCafeCollectionNewGeneralCafe.equals(street))
                    {
                        oldIdAdrOfGeneralCafeCollectionNewGeneralCafe.getGeneralCafeCollection().remove(generalCafeCollectionNewGeneralCafe);
                        oldIdAdrOfGeneralCafeCollectionNewGeneralCafe = em.merge(oldIdAdrOfGeneralCafeCollectionNewGeneralCafe);
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
                Integer id = street.getIdAdr();
                if (findStreet(id) == null)
                {
                    throw new NonexistentEntityException("The street with id " + id + " no longer exists.");
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
            Street street;
            try
            {
                street = em.getReference(Street.class, id);
                street.getIdAdr();
            }
            catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The street with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<GeneralTrade> generalTradeCollectionOrphanCheck = street.getGeneralTradeCollection();
            for (GeneralTrade generalTradeCollectionOrphanCheckGeneralTrade : generalTradeCollectionOrphanCheck)
            {
                if (illegalOrphanMessages == null)
                {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Street (" + street + ") cannot be destroyed since the GeneralTrade " + generalTradeCollectionOrphanCheckGeneralTrade + " in its generalTradeCollection field has a non-nullable idAdr field.");
            }
            Collection<Statement> statementCollectionOrphanCheck = street.getStatementCollection();
            for (Statement statementCollectionOrphanCheckStatement : statementCollectionOrphanCheck)
            {
                if (illegalOrphanMessages == null)
                {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Street (" + street + ") cannot be destroyed since the Statement " + statementCollectionOrphanCheckStatement + " in its statementCollection field has a non-nullable idAdr field.");
            }
            Collection<GeneralCafe> generalCafeCollectionOrphanCheck = street.getGeneralCafeCollection();
            for (GeneralCafe generalCafeCollectionOrphanCheckGeneralCafe : generalCafeCollectionOrphanCheck)
            {
                if (illegalOrphanMessages == null)
                {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Street (" + street + ") cannot be destroyed since the GeneralCafe " + generalCafeCollectionOrphanCheckGeneralCafe + " in its generalCafeCollection field has a non-nullable idAdr field.");
            }
            if (illegalOrphanMessages != null)
            {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            City idCity = street.getIdCity();
            if (idCity != null)
            {
                idCity.getStreetCollection().remove(street);
                idCity = em.merge(idCity);
            }
            em.remove(street);
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

    public List<Street> findStreetEntities()
    {
        return findStreetEntities(true, -1, -1);
    }

    public List<Street> findStreetEntities(int maxResults, int firstResult)
    {
        return findStreetEntities(false, maxResults, firstResult);
    }

    private List<Street> findStreetEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Street.class));
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

    public Street findStreet(Integer id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(Street.class, id);
        }
        finally
        {
            em.close();
        }
    }

    public int getStreetCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Street> rt = cq.from(Street.class);
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
