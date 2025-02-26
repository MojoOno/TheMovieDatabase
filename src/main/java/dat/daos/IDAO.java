package dat.daos;

public interface IDAO<T>
{
    T create(T t);
    T read(int id);
    T update(T t);
    void delete(Long id);
}