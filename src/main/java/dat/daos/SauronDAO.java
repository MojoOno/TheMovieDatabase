package dat.daos;

import dat.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * Thread-safe DAO with multi-threaded batch operations.
 */
public class SauronDAO {
    private static EntityManagerFactory emf;
    private static SauronDAO instance;
    private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    // Ensure each thread gets its own EntityManager
    private static final ThreadLocal<EntityManager> threadLocalEm = ThreadLocal.withInitial(() -> emf.createEntityManager());

    private SauronDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public static synchronized SauronDAO getInstance(EntityManagerFactory emf) {
        if (instance == null) {
            instance = new SauronDAO(emf);
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return threadLocalEm.get();
    }

    /**
     * Creates a single entity.
     */
    public <T> T create(T object) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
            return object;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new ApiException(401, "Error persisting object to DB.", e);
        } finally {
            em.close();
        }
    }

    /**
     * Multi-threaded batch insert.
     */
    public <T> List<T> create(List<T> objects) {
        List<Future<T>> futures = new ArrayList<>();
        for (T object : objects) {
            futures.add(executor.submit(() -> create(object)));
        }
        return collectResults(futures);
    }

    /**
     * Reads an entity by ID.
     */
    public <T> T read(Class<T> type, Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(type, id);
        } catch (Exception e) {
            throw new ApiException(401, "Error reading object from DB.", e);
        } finally {
            em.close();
        }
    }

    /**
     * Fetches all entities of a given type.
     */
    public <T> List<T> findAll(Class<T> type) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT t FROM " + type.getSimpleName() + " t", type).getResultList();
        } catch (Exception e) {
            throw new ApiException(401, "Error reading objects from DB.", e);
        } finally {
            em.close();
        }
    }

    /**
     * Updates an entity.
     */
    public <T> T update(T object) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            T updatedEntity = em.merge(object);
            em.getTransaction().commit();
            return updatedEntity;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new ApiException(401, "Error updating object.", e);
        } finally {
            em.close();
        }
    }

    /**
     * Multi-threaded batch update.
     */
    public <T> List<T> update(List<T> objects) {
        List<Future<T>> futures = new ArrayList<>();
        for (T object : objects) {
            futures.add(executor.submit(() -> update(object)));
        }
        return collectResults(futures);
    }

    /**
     * Deletes an entity.
     */
    public <T> void delete(T object) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(em.contains(object) ? object : em.merge(object));
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new ApiException(401, "Error deleting object.", e);
        } finally {
            em.close();
        }
    }

    /**
     * Deletes an entity by ID.
     */
    public <T> void delete(Class<T> type, Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            T object = em.find(type, id);
            if (object != null) {
                em.remove(object);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new ApiException(401, "Error deleting object.", e);
        } finally {
            em.close();
        }
    }

    /**
     * Helper method to collect results from multiple threads.
     */
    private <T> List<T> collectResults(List<Future<T>> futures) {
        List<T> results = new ArrayList<>();
        for (Future<T> future : futures) {
            try {
                results.add(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    /**
     * Shuts down the thread pool when the DAO is no longer needed.
     */
    public void shutdown() {
        executor.shutdown();
    }
}
