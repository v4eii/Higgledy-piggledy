/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administration.entity.controllers;

import administration.entity.General2;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import administration.entity.Statement;
import administration.entity.Users;
import administration.entity.Specialization;
import administration.entity.controllers.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author мвидео
 */
public class General2JpaController implements Serializable {

    public General2JpaController(EntityManagerFactory emf)
    {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(General2 general2)
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Statement idSt = general2.getIdSt();
            if (idSt != null)
            {
                idSt = em.getReference(idSt.getClass(), idSt.getIdSt());
                general2.setIdSt(idSt);
            }
            Users idUser = general2.getIdUser();
            if (idUser != null)
            {
                idUser = em.getReference(idUser.getClass(), idUser.getIdUser());
                general2.setIdUser(idUser);
            }
            Specialization idSpec = general2.getIdSpec();
            if (idSpec != null)
            {
                idSpec = em.getReference(idSpec.getClass(), idSpec.getIdSpec());
                general2.setIdSpec(idSpec);
            }
            em.persist(general2);
            if (idSt != null)
            {
                idSt.getGeneral2Collection().add(general2);
                idSt = em.merge(idSt);
            }
            if (idUser != null)
            {
                idUser.getGeneral2Collection().add(general2);
                idUser = em.merge(idUser);
            }
            if (idSpec != null)
            {
                idSpec.getGeneral2Collection().add(general2);
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

    public void edit(General2 general2) throws NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            General2 persistentGeneral2 = em.find(General2.class, general2.getIdUnion());
            Statement idStOld = persistentGeneral2.getIdSt();
            Statement idStNew = general2.getIdSt();
            Users idUserOld = persistentGeneral2.getIdUser();
            Users idUserNew = general2.getIdUser();
            Specialization idSpecOld = persistentGeneral2.getIdSpec();
            Specialization idSpecNew = general2.getIdSpec();
            if (idStNew != null)
            {
                idStNew = em.getReference(idStNew.getClass(), idStNew.getIdSt());
                general2.setIdSt(idStNew);
            }
            if (idUserNew != null)
            {
                idUserNew = em.getReference(idUserNew.getClass(), idUserNew.getIdUser());
                general2.setIdUser(idUserNew);
            }
            if (idSpecNew != null)
            {
                idSpecNew = em.getReference(idSpecNew.getClass(), idSpecNew.getIdSpec());
                general2.setIdSpec(idSpecNew);
            }
            general2 = em.merge(general2);
            if (idStOld != null && !idStOld.equals(idStNew))
            {
                idStOld.getGeneral2Collection().remove(general2);
                idStOld = em.merge(idStOld);
            }
            if (idStNew != null && !idStNew.equals(idStOld))
            {
                idStNew.getGeneral2Collection().add(general2);
                idStNew = em.merge(idStNew);
            }
            if (idUserOld != null && !idUserOld.equals(idUserNew))
            {
                idUserOld.getGeneral2Collection().remove(general2);
                idUserOld = em.merge(idUserOld);
            }
            if (idUserNew != null && !idUserNew.equals(idUserOld))
            {
                idUserNew.getGeneral2Collection().add(general2);
                idUserNew = em.merge(idUserNew);
            }
            if (idSpecOld != null && !idSpecOld.equals(idSpecNew))
            {
                idSpecOld.getGeneral2Collection().remove(general2);
                idSpecOld = em.merge(idSpecOld);
            }
            if (idSpecNew != null && !idSpecNew.equals(idSpecOld))
            {
                idSpecNew.getGeneral2Collection().add(general2);
                idSpecNew = em.merge(idSpecNew);
            }
            em.getTransaction().commit();
        }
        catch (Exception ex)
        {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0)
            {
                Integer id = general2.getIdUnion();
                if (findGeneral2(id) == null)
                {
                    throw new NonexistentEntityException("The general2 with id " + id + " no longer exists.");
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
            General2 general2;
            try
            {
                general2 = em.getReference(General2.class, id);
                general2.getIdUnion();
            }
            catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The general2 with id " + id + " no longer exists.", enfe);
            }
            Statement idSt = general2.getIdSt();
            if (idSt != null)
            {
                idSt.getGeneral2Collection().remove(general2);
                idSt = em.merge(idSt);
            }
            Users idUser = general2.getIdUser();
            if (idUser != null)
            {
                idUser.getGeneral2Collection().remove(general2);
                idUser = em.merge(idUser);
            }
            Specialization idSpec = general2.getIdSpec();
            if (idSpec != null)
            {
                idSpec.getGeneral2Collection().remove(general2);
                idSpec = em.merge(idSpec);
            }
            em.remove(general2);
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

    public List<General2> findGeneral2Entities()
    {
        return findGeneral2Entities(true, -1, -1);
    }

    public List<General2> findGeneral2Entities(int maxResults, int firstResult)
    {
        return findGeneral2Entities(false, maxResults, firstResult);
    }

    private List<General2> findGeneral2Entities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(General2.class));
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

    public General2 findGeneral2(Integer id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(General2.class, id);
        }
        finally
        {
            em.close();
        }
    }

    public int getGeneral2Count()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<General2> rt = cq.from(General2.class);
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
