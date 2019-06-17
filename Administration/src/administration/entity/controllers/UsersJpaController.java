package administration.entity.controllers;

import administration.beans.DBBean;
import administration.entity.Users;
import administration.entity.controllers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javafx.application.Platform;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.security.auth.login.AccountException;

/**
 *
 * @author v4e
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
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(users);
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

    public void edit(Users users) throws NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            users = em.merge(users);
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

    public void destroy(Integer id) throws NonexistentEntityException
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
    
    public Users findUsers(String login)
    {
        EntityManager em = getEntityManager();
        try
        {
            Query q = em.createNamedQuery("Users.findByUserLogin", Users.class);
            q.setParameter("userLogin", login);
            return q.getFirstResult() == -1 ? null : (Users) q.getSingleResult();
        }
        catch(NoResultException ex)
        {
            DBBean.getInstance().showErrDialog(new AccountException("Не существующий пользователь"), "Ошибка", "Данный пользователь не существует");
            Platform.exit();
        }
        finally
        {
            em.close();
        }
        return null;
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
    
    public String getUserPassword(String login)
    {
        EntityManager em = getEntityManager();
        try
        {
            Query q = em.createNamedQuery("Users.findUserPassword", Users.class);
            q.setParameter("userLogin", login);
            return (String) q.getSingleResult();
        }
        catch (NoResultException ex)
        {
            System.err.println("Ошибка поиска пароля, ввиду отсутствия юзера");
        }
        finally
        {
            em.close();
        }
        return "";
    }
    
}
