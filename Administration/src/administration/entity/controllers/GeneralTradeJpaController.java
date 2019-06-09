/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administration.entity.controllers;

import administration.entity.GeneralTrade;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import administration.entity.Statement;
import administration.entity.Street;
import administration.entity.Specialization;
import administration.entity.controllers.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author v4e
 */
public class GeneralTradeJpaController implements Serializable {

    public GeneralTradeJpaController(EntityManagerFactory emf)
    {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(GeneralTrade generalTrade)
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Statement idSt = generalTrade.getIdSt();
            if (idSt != null)
            {
                idSt = em.getReference(idSt.getClass(), idSt.getIdSt());
                generalTrade.setIdSt(idSt);
            }
            Street idAdr = generalTrade.getIdAdr();
            if (idAdr != null)
            {
                idAdr = em.getReference(idAdr.getClass(), idAdr.getIdAdr());
                generalTrade.setIdAdr(idAdr);
            }
            Specialization idSpec = generalTrade.getIdSpec();
            if (idSpec != null)
            {
                idSpec = em.getReference(idSpec.getClass(), idSpec.getIdSpec());
                generalTrade.setIdSpec(idSpec);
            }
            em.persist(generalTrade);
            if (idSt != null)
            {
                idSt.getGeneralTradeCollection().add(generalTrade);
                idSt = em.merge(idSt);
            }
            if (idAdr != null)
            {
                idAdr.getGeneralTradeCollection().add(generalTrade);
                idAdr = em.merge(idAdr);
            }
            if (idSpec != null)
            {
                idSpec.getGeneralTradeCollection().add(generalTrade);
                idSpec = em.merge(idSpec);
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

    public void edit(GeneralTrade generalTrade) throws NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            GeneralTrade persistentGeneralTrade = em.find(GeneralTrade.class, generalTrade.getIdUnion());
            Statement idStOld = persistentGeneralTrade.getIdSt();
            Statement idStNew = generalTrade.getIdSt();
            Street idAdrOld = persistentGeneralTrade.getIdAdr();
            Street idAdrNew = generalTrade.getIdAdr();
            Specialization idSpecOld = persistentGeneralTrade.getIdSpec();
            Specialization idSpecNew = generalTrade.getIdSpec();
            if (idStNew != null)
            {
                idStNew = em.getReference(idStNew.getClass(), idStNew.getIdSt());
                generalTrade.setIdSt(idStNew);
            }
            if (idAdrNew != null)
            {
                idAdrNew = em.getReference(idAdrNew.getClass(), idAdrNew.getIdAdr());
                generalTrade.setIdAdr(idAdrNew);
            }
            if (idSpecNew != null)
            {
                idSpecNew = em.getReference(idSpecNew.getClass(), idSpecNew.getIdSpec());
                generalTrade.setIdSpec(idSpecNew);
            }
            generalTrade = em.merge(generalTrade);
            if (idStOld != null && !idStOld.equals(idStNew))
            {
                idStOld.getGeneralTradeCollection().remove(generalTrade);
                idStOld = em.merge(idStOld);
            }
            if (idStNew != null && !idStNew.equals(idStOld))
            {
                idStNew.getGeneralTradeCollection().add(generalTrade);
                idStNew = em.merge(idStNew);
            }
            if (idAdrOld != null && !idAdrOld.equals(idAdrNew))
            {
                idAdrOld.getGeneralTradeCollection().remove(generalTrade);
                idAdrOld = em.merge(idAdrOld);
            }
            if (idAdrNew != null && !idAdrNew.equals(idAdrOld))
            {
                idAdrNew.getGeneralTradeCollection().add(generalTrade);
                idAdrNew = em.merge(idAdrNew);
            }
            if (idSpecOld != null && !idSpecOld.equals(idSpecNew))
            {
                idSpecOld.getGeneralTradeCollection().remove(generalTrade);
                idSpecOld = em.merge(idSpecOld);
            }
            if (idSpecNew != null && !idSpecNew.equals(idSpecOld))
            {
                idSpecNew.getGeneralTradeCollection().add(generalTrade);
                idSpecNew = em.merge(idSpecNew);
            }
            em.getTransaction().commit();
        }
        catch (Exception ex)
        {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0)
            {
                Integer id = generalTrade.getIdUnion();
                if (findGeneralTrade(id) == null)
                {
                    throw new NonexistentEntityException("The generalTrade with id " + id + " no longer exists.");
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

    public void destroy(Integer id) throws NonexistentEntityException
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            GeneralTrade generalTrade;
            try
            {
                generalTrade = em.getReference(GeneralTrade.class, id);
                generalTrade.getIdUnion();
            }
            catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The generalTrade with id " + id + " no longer exists.", enfe);
            }
            Statement idSt = generalTrade.getIdSt();
            if (idSt != null)
            {
                idSt.getGeneralTradeCollection().remove(generalTrade);
                idSt = em.merge(idSt);
            }
            Street idAdr = generalTrade.getIdAdr();
            if (idAdr != null)
            {
                idAdr.getGeneralTradeCollection().remove(generalTrade);
                idAdr = em.merge(idAdr);
            }
            Specialization idSpec = generalTrade.getIdSpec();
            if (idSpec != null)
            {
                idSpec.getGeneralTradeCollection().remove(generalTrade);
                idSpec = em.merge(idSpec);
            }
            em.remove(generalTrade);
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

    public List<GeneralTrade> findGeneralTradeEntities()
    {
        return findGeneralTradeEntities(true, -1, -1);
    }

    public List<GeneralTrade> findGeneralTradeEntities(int maxResults, int firstResult)
    {
        return findGeneralTradeEntities(false, maxResults, firstResult);
    }

    private List<GeneralTrade> findGeneralTradeEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(GeneralTrade.class));
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

    public GeneralTrade findGeneralTrade(Integer id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(GeneralTrade.class, id);
        }
        finally
        {
            em.close();
        }
    }
    
    public List getOrganizationName(Integer id_union)
    {
        EntityManager em = getEntityManager();
        String tmp = String.format("SELECT Org FROM Statement WHERE id_st = (SELECT id_st FROM General_trade WHERE id_union = %d)", id_union + 1);
        Query q = em.createNativeQuery(tmp);
        
        return q.getResultList();
    }

    public int getGeneralTradeCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<GeneralTrade> rt = cq.from(GeneralTrade.class);
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
