package dat.daos;

import dat.config.HibernateConfig;
import dat.entities.Credit;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

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
        }
        return object;
    }

    public Credit read(int id)
    {
        return null;
    }


    public Credit update(Credit object)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.merge(object);
            em.getTransaction().commit();
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
    }
}
