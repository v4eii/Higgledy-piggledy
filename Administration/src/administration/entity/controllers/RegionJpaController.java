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
import administration.entity.Region;
import administration.entity.controllers.exceptions.IllegalOrphanException;
import administration.entity.controllers.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author мвидео
 */
public class RegionJpaController implements Serializable {

    public RegionJpaController(EntityManagerFactory emf)
    {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(Region region)
    {
        if (region.getCityCollection() == null)
        {
            region.setCityCollection(new ArrayList<City>());
        }
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<City> attachedCityCollection = new ArrayList<City>();
            for (City cityCollectionCityToAttach : region.getCityCollection())
            {
                cityCollectionCityToAttach = em.getReference(cityCollectionCityToAttach.getClass(), cityCollectionCityToAttach.getIdCity());
                attachedCityCollection.add(cityCollectionCityToAttach);
            }
            region.setCityCollection(attachedCityCollection);
            em.persist(region);
            for (City cityCollectionCity : region.getCityCollection())
            {
                Region oldIdRegOfCityCollectionCity = cityCollectionCity.getIdReg();
                cityCollectionCity.setIdReg(region);
                cityCollectionCity = em.merge(cityCollectionCity);
                if (oldIdRegOfCityCollectionCity != null)
                {
                    oldIdRegOfCityCollectionCity.getCityCollection().remove(cityCollectionCity);
                    oldIdRegOfCityCollectionCity = em.merge(oldIdRegOfCityCollectionCity);
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

    public void edit(Region region) throws IllegalOrphanException, NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Region persistentRegion = em.find(Region.class, region.getIdReg());
            Collection<City> cityCollectionOld = persistentRegion.getCityCollection();
            Collection<City> cityCollectionNew = region.getCityCollection();
            List<String> illegalOrphanMessages = null;
            for (City cityCollectionOldCity : cityCollectionOld)
            {
                if (!cityCollectionNew.contains(cityCollectionOldCity))
                {
                    if (illegalOrphanMessages == null)
                    {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain City " + cityCollectionOldCity + " since its idReg field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null)
            {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<City> attachedCityCollectionNew = new ArrayList<City>();
            for (City cityCollectionNewCityToAttach : cityCollectionNew)
            {
                cityCollectionNewCityToAttach = em.getReference(cityCollectionNewCityToAttach.getClass(), cityCollectionNewCityToAttach.getIdCity());
                attachedCityCollectionNew.add(cityCollectionNewCityToAttach);
            }
            cityCollectionNew = attachedCityCollectionNew;
            region.setCityCollection(cityCollectionNew);
            region = em.merge(region);
            for (City cityCollectionNewCity : cityCollectionNew)
            {
                if (!cityCollectionOld.contains(cityCollectionNewCity))
                {
                    Region oldIdRegOfCityCollectionNewCity = cityCollectionNewCity.getIdReg();
                    cityCollectionNewCity.setIdReg(region);
                    cityCollectionNewCity = em.merge(cityCollectionNewCity);
                    if (oldIdRegOfCityCollectionNewCity != null && !oldIdRegOfCityCollectionNewCity.equals(region))
                    {
                        oldIdRegOfCityCollectionNewCity.getCityCollection().remove(cityCollectionNewCity);
                        oldIdRegOfCityCollectionNewCity = em.merge(oldIdRegOfCityCollectionNewCity);
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
                Integer id = region.getIdReg();
                if (findRegion(id) == null)
                {
                    throw new NonexistentEntityException("The region with id " + id + " no longer exists.");
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
            Region region;
            try
            {
                region = em.getReference(Region.class, id);
                region.getIdReg();
            }
            catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The region with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<City> cityCollectionOrphanCheck = region.getCityCollection();
            for (City cityCollectionOrphanCheckCity : cityCollectionOrphanCheck)
            {
                if (illegalOrphanMessages == null)
                {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Region (" + region + ") cannot be destroyed since the City " + cityCollectionOrphanCheckCity + " in its cityCollection field has a non-nullable idReg field.");
            }
            if (illegalOrphanMessages != null)
            {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(region);
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

    public List<Region> findRegionEntities()
    {
        return findRegionEntities(true, -1, -1);
    }

    public List<Region> findRegionEntities(int maxResults, int firstResult)
    {
        return findRegionEntities(false, maxResults, firstResult);
    }

    private List<Region> findRegionEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Region.class));
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

    public Region findRegion(Integer id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(Region.class, id);
        }
        finally
        {
            em.close();
        }
    }

    public int getRegionCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Region> rt = cq.from(Region.class);
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
