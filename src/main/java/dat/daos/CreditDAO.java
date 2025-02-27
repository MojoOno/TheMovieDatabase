package dat.daos;

import dat.config.HibernateConfig;
import dat.entities.Credit;
import dat.entities.Genre;
import dat.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class CreditDAO implements IDAO<Credit>
{
    private static EntityManagerFactory emf;
    private static CreditDAO instance;

    private CreditDAO(EntityManagerFactory emf)
    {
        this.emf = emf;
    }

    public static CreditDAO getInstance(EntityManagerFactory emf)
    {
        if (instance == null)
        {
            instance = new CreditDAO(emf);
        }
        return instance;
    }

    public Credit create(Credit object)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
            return object;
        }
        catch (Exception e)
        {
            throw new ApiException(401, "Error creating credit. ", e);
        }
    }

    public List<Credit> create(List<Credit> objects)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            for (Credit object : objects)
            {
                em.persist(object);
            }
            em.getTransaction().commit();
        }
        catch (Exception e)
        {
            throw new ApiException(401, "Error creating credits ", e);
        }
        return objects;
    }

    public Credit read(int id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.find(Credit.class, id);
        }
        catch (Exception e)
        {
            throw new ApiException(401, "Error reading credit from db", e);
        }
    }
    public Credit read(Credit object)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.find(Credit.class, object.getId());
        }
        catch (Exception e)
        {
            throw new ApiException(401, "Error reading credit from db", e);
        }

    }


    public Credit update(Credit object)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.merge(object);
            em.getTransaction().commit();
        }
        catch (Exception e)
        {
            throw new ApiException(401, "Error updating credit from db", e);
        }
        return object;
    }

    public void delete(Long id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.remove(id);
            em.getTransaction().commit();
        }
        catch (Exception e)
        {
            throw new ApiException(401, "Error deleting credit from db", e);
        }
    }
}
