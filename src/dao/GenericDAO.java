package dao;

import java.sql.SQLException;
import java.util.List;

public interface GenericDAO<T extends IdentifiedTable<PK>,PK> {
    T create() throws SQLException;
    T getById(PK id) throws SQLException;
    T insert(T entity)throws SQLException;
    List<T>getAll()throws SQLException;
    void update(T entity) throws SQLException;
    void delete(T entity) throws SQLException;

}
