package Dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    Optional<T> get(long id);
    boolean update(T t);
    //boolean delete(T t);
    
}
