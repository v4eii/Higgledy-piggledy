/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administration.entity.controllers;

import administration.entity.City;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import administration.entity.Region;
import administration.entity.controllers.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author мвидео
 */
public class CityJpaController implements Serializable {

    public CityJpaController(EntityManagerFactory emf)
    {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(City city)
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Region idReg = city.getIdReg();
            if (idReg != null)
            {
                idReg = em.getReference(idReg.getClass(), idReg.getIdReg());
                city.setIdReg(idReg);
            }
            em.persist(city);
            if (idReg != null)
            {
                idReg.getCityCollection().add(city);
                idReg = em.merge(idReg);
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

    public void edit(City city) throws NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            City persistentCity = em.find(City.class, city.getIdCity());
            Region idRegOld = persistentCity.getIdReg();
            Region idRegNew = city.getIdReg();
            if (idRegNew != null)
            {
                idRegNew = em.getReference(idRegNew.getClass(), idRegNew.getIdReg());
                city.setIdReg(idRegNew);
            }
            city = em.merge(city);
            if (idRegOld != null && !idRegOld.equals(idRegNew))
            {
                idRegOld.getCityCollection().remove(city);
                idRegOld = em.merge(idRegOld);
            }
            if (idRegNew != null && !idRegNew.equals(idRegOld))
            {
                idRegNew.getCityCollection().add(city);
                idRegNew = em.merge(idRegNew);
            }
            em.getTransaction().commit();
        }
        catch (Exception ex)
        {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0)
            {
                Integer id = city.getIdCity();
                if (findCity(id) == null)
                {
                    throw new NonexistentEntityException("The city with id " + id + " no longer exists.");
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
            City city;
            try
            {
                city = em.getReference(City.class, id);
                city.getIdCity();
            }
            catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The city with id " + id + " no longer exists.", enfe);
            }
            Region idReg = city.getIdReg();
            if (idReg != null)
            {
                idReg.getCityCollection().remove(city);
                idReg = em.merge(idReg);
            }
            em.remove(city);
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

    public List<City> findCityEntities()
    {
        return findCityEntities(true, -1, -1);
    }

    public List<City> findCityEntities(int maxResults, int firstResult)
    {
        return findCityEntities(false, maxResults, firstResult);
    }

    private List<City> findCityEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(City.class));
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

    public City findCity(Integer id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(City.class, id);
        }
        finally
        {
            em.close();
        }
    }

    public int getCityCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<City> rt = cq.from(City.class);
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
