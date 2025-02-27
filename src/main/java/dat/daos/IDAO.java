package dat.daos;

import dat.exceptions.ApiException;
import jakarta.persistence.EntityManager;


import static dat.Main.emf;

public interface IDAO<T>
{
    default T create(T object)
    {
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
                throw new ApiException(401, "Error persisting object to db. ", e);
            }
        }
    }
    T read(int id);
    default T update(T object)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            T updated = em.merge(object);
            em.getTransaction().commit();
            return updated;
        }
        catch (Exception e)
        {
            throw new ApiException(401, "Error updating object. ", e);
        }
    }
    void delete(Long id);
}