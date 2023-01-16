package Logic.Dao;

import java.util.Optional;

public interface Dao<T> extends MiniDao<T> {
    Optional<T> get(long id);
    boolean update(T t);
    //boolean delete(T t);
    
}
