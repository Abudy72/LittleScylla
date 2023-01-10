package Dao;

import java.util.List;

public interface MainDao<T> {
    List<T> getAll();
    boolean save(T t);
}
