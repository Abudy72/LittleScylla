package Logic.Dao;

import java.util.List;

public interface MiniDao<T> {
    List<T> getAll();
    boolean save(T t);
}
