package saooperation.dao.dao;

import java.util.List;

public interface ITableDAO<T> {
    // CRUD
    void insert(T obj);
    void update(T obj);
    void delete(T obj);
    T selectById(T obj);
    List<T> selectAll(T obj);
    void truncate(T obj);
    void recover(List<T> obj);
}
