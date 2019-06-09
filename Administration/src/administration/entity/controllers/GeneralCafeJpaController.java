/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administration.entity.controllers;

import administration.entity.GeneralCafe;
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
 * @author мвидео
 */
public class GeneralCafeJpaController implements Serializable {

    public GeneralCafeJpaController(EntityManagerFactory emf)
    {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(GeneralCafe generalCafe)
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Statement idSt = generalCafe.getIdSt();
            if (idSt != null)
            {
                idSt = em.getReference(idSt.getClass(), idSt.getIdSt());
                generalCafe.setIdSt(idSt);
            }
            Street idAdr = generalCafe.getIdAdr();
            if (idAdr != null)
            {
                idAdr = em.getReference(idAdr.getClass(), idAdr.getIdAdr());
                generalCafe.setIdAdr(idAdr);
            }
            Specialization idSpec = generalCafe.getIdSpec();
            if (idSpec != null)
            {
                idSpec = em.getReference(idSpec.getClass(), idSpec.getIdSpec());
                generalCafe.setIdSpec(idSpec);
            }
            em.persist(generalCafe);
            if (idSt != null)
            {
                idSt.getGeneralCafeCollection().add(generalCafe);
                idSt = em.merge(idSt);
            }
            if (idAdr != null)
            {
                idAdr.getGeneralCafeCollection().add(generalCafe);
                idAdr = em.merge(idAdr);
            }
            if (idSpec != null)
            {
                idSpec.getGeneralCafeCollection().add(generalCafe);
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

    public void edit(GeneralCafe generalCafe) throws NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            GeneralCafe persistentGeneralCafe = em.find(GeneralCafe.class, generalCafe.getIdUnion());
            Statement idStOld = persistentGeneralCafe.getIdSt();
            Statement idStNew = generalCafe.getIdSt();
            Street idAdrOld = persistentGeneralCafe.getIdAdr();
            Street idAdrNew = generalCafe.getIdAdr();
            Specialization idSpecOld = persistentGeneralCafe.getIdSpec();
            Specialization idSpecNew = generalCafe.getIdSpec();
            if (idStNew != null)
            {
                idStNew = em.getReference(idStNew.getClass(), idStNew.getIdSt());
                generalCafe.setIdSt(idStNew);
            }
            if (idAdrNew != null)
            {
                idAdrNew = em.getReference(idAdrNew.getClass(), idAdrNew.getIdAdr());
                generalCafe.setIdAdr(idAdrNew);
            }
            if (idSpecNew != null)
            {
                idSpecNew = em.getReference(idSpecNew.getClass(), idSpecNew.getIdSpec());
                generalCafe.setIdSpec(idSpecNew);
            }
            generalCafe = em.merge(generalCafe);
            if (idStOld != null && !idStOld.equals(idStNew))
            {
                idStOld.getGeneralCafeCollection().remove(generalCafe);
                idStOld = em.merge(idStOld);
            }
            if (idStNew != null && !idStNew.equals(idStOld))
            {
                idStNew.getGeneralCafeCollection().add(generalCafe);
                idStNew = em.merge(idStNew);
            }
            if (idAdrOld != null && !idAdrOld.equals(idAdrNew))
            {
                idAdrOld.getGeneralCafeCollection().remove(generalCafe);
                idAdrOld = em.merge(idAdrOld);
            }
            if (idAdrNew != null && !idAdrNew.equals(idAdrOld))
            {
                idAdrNew.getGeneralCafeCollection().add(generalCafe);
                idAdrNew = em.merge(idAdrNew);
            }
            if (idSpecOld != null && !idSpecOld.equals(idSpecNew))
            {
                idSpecOld.getGeneralCafeCollection().remove(generalCafe);
                idSpecOld = em.merge(idSpecOld);
            }
            if (idSpecNew != null && !idSpecNew.equals(idSpecOld))
            {
                idSpecNew.getGeneralCafeCollection().add(generalCafe);
                idSpecNew = em.merge(idSpecNew);
            }
            em.getTransaction().commit();
        }
        catch (Exception ex)
        {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0)
            {
                Integer id = generalCafe.getIdUnion();
                if (findGeneralCafe(id) == null)
                {
                    throw new NonexistentEntityException("The generalCafe with id " + id + " no longer exists.");
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
            GeneralCafe generalCafe;
            try
            {
                generalCafe = em.getReference(GeneralCafe.class, id);
                generalCafe.getIdUnion();
            }
            catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The generalCafe with id " + id + " no longer exists.", enfe);
            }
            Statement idSt = generalCafe.getIdSt();
            if (idSt != null)
            {
                idSt.getGeneralCafeCollection().remove(generalCafe);
                idSt = em.merge(idSt);
            }
            Street idAdr = generalCafe.getIdAdr();
            if (idAdr != null)
            {
                idAdr.getGeneralCafeCollection().remove(generalCafe);
                idAdr = em.merge(idAdr);
            }
            Specialization idSpec = generalCafe.getIdSpec();
            if (idSpec != null)
            {
                idSpec.getGeneralCafeCollection().remove(generalCafe);
                idSpec = em.merge(idSpec);
            }
            em.remove(generalCafe);
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

    public List<GeneralCafe> findGeneralCafeEntities()
    {
        return findGeneralCafeEntities(true, -1, -1);
    }

    public List<GeneralCafe> findGeneralCafeEntities(int maxResults, int firstResult)
    {
        return findGeneralCafeEntities(false, maxResults, firstResult);
    }
    
    public List getOrganizationName(Integer id_union)
    {
        EntityManager em = getEntityManager();
        String tmp = String.format("SELECT Org FROM Statement WHERE id_st = (SELECT id_st FROM General_cafe WHERE id_union = %d)", id_union + 1);
        Query q = em.createNativeQuery(tmp);
        
        return q.getResultList();
    }

    private List<GeneralCafe> findGeneralCafeEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(GeneralCafe.class));
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

    public GeneralCafe findGeneralCafe(Integer id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(GeneralCafe.class, id);
        }
        finally
        {
            em.close();
        }
    }

    public int getGeneralCafeCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<GeneralCafe> rt = cq.from(GeneralCafe.class);
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
