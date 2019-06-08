/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administration.entity.controllers;

import administration.entity.General;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import administration.entity.Specialization;
import administration.entity.Users;
import administration.entity.Statement;
import administration.entity.controllers.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author мвидео
 */
public class GeneralJpaController implements Serializable {

    public GeneralJpaController(EntityManagerFactory emf)
    {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(General general)
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Specialization idSpec = general.getIdSpec();
            if (idSpec != null)
            {
                idSpec = em.getReference(idSpec.getClass(), idSpec.getIdSpec());
                general.setIdSpec(idSpec);
            }
            Users idUser = general.getIdUser();
            if (idUser != null)
            {
                idUser = em.getReference(idUser.getClass(), idUser.getIdUser());
                general.setIdUser(idUser);
            }
            Statement idSt = general.getIdSt();
            if (idSt != null)
            {
                idSt = em.getReference(idSt.getClass(), idSt.getIdSt());
                general.setIdSt(idSt);
            }
            em.persist(general);
            if (idSpec != null)
            {
                idSpec.getGeneralCollection().add(general);
                idSpec = em.merge(idSpec);
            }
            if (idUser != null)
            {
                idUser.getGeneralCollection().add(general);
                idUser = em.merge(idUser);
            }
            if (idSt != null)
            {
                idSt.getGeneralCollection().add(general);
                idSt = em.merge(idSt);
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

    public void edit(General general) throws NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            General persistentGeneral = em.find(General.class, general.getIdUnion());
            Specialization idSpecOld = persistentGeneral.getIdSpec();
            Specialization idSpecNew = general.getIdSpec();
            Users idUserOld = persistentGeneral.getIdUser();
            Users idUserNew = general.getIdUser();
            Statement idStOld = persistentGeneral.getIdSt();
            Statement idStNew = general.getIdSt();
            if (idSpecNew != null)
            {
                idSpecNew = em.getReference(idSpecNew.getClass(), idSpecNew.getIdSpec());
                general.setIdSpec(idSpecNew);
            }
            if (idUserNew != null)
            {
                idUserNew = em.getReference(idUserNew.getClass(), idUserNew.getIdUser());
                general.setIdUser(idUserNew);
            }
            if (idStNew != null)
            {
                idStNew = em.getReference(idStNew.getClass(), idStNew.getIdSt());
                general.setIdSt(idStNew);
            }
            general = em.merge(general);
            if (idSpecOld != null && !idSpecOld.equals(idSpecNew))
            {
                idSpecOld.getGeneralCollection().remove(general);
                idSpecOld = em.merge(idSpecOld);
            }
            if (idSpecNew != null && !idSpecNew.equals(idSpecOld))
            {
                idSpecNew.getGeneralCollection().add(general);
                idSpecNew = em.merge(idSpecNew);
            }
            if (idUserOld != null && !idUserOld.equals(idUserNew))
            {
                idUserOld.getGeneralCollection().remove(general);
                idUserOld = em.merge(idUserOld);
            }
            if (idUserNew != null && !idUserNew.equals(idUserOld))
            {
                idUserNew.getGeneralCollection().add(general);
                idUserNew = em.merge(idUserNew);
            }
            if (idStOld != null && !idStOld.equals(idStNew))
            {
                idStOld.getGeneralCollection().remove(general);
                idStOld = em.merge(idStOld);
            }
            if (idStNew != null && !idStNew.equals(idStOld))
            {
                idStNew.getGeneralCollection().add(general);
                idStNew = em.merge(idStNew);
            }
            em.getTransaction().commit();
        }
        catch (Exception ex)
        {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0)
            {
                Integer id = general.getIdUnion();
                if (findGeneral(id) == null)
                {
                    throw new NonexistentEntityException("The general with id " + id + " no longer exists.");
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
            General general;
            try
            {
                general = em.getReference(General.class, id);
                general.getIdUnion();
            }
            catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The general with id " + id + " no longer exists.", enfe);
            }
            Specialization idSpec = general.getIdSpec();
            if (idSpec != null)
            {
                idSpec.getGeneralCollection().remove(general);
                idSpec = em.merge(idSpec);
            }
            Users idUser = general.getIdUser();
            if (idUser != null)
            {
                idUser.getGeneralCollection().remove(general);
                idUser = em.merge(idUser);
            }
            Statement idSt = general.getIdSt();
            if (idSt != null)
            {
                idSt.getGeneralCollection().remove(general);
                idSt = em.merge(idSt);
            }
            em.remove(general);
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

    public List<General> findGeneralEntities()
    {
        return findGeneralEntities(true, -1, -1);
    }

    public List<General> findGeneralEntities(int maxResults, int firstResult)
    {
        return findGeneralEntities(false, maxResults, firstResult);
    }

    private List<General> findGeneralEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(General.class));
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

    public General findGeneral(Integer id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(General.class, id);
        }
        finally
        {
            em.close();
        }
    }

    public int getGeneralCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<General> rt = cq.from(General.class);
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
