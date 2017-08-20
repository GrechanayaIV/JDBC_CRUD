package dao;

import connection.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractDAO<T extends IdentifiedTable<PK>, PK> implements GenericDAO<T, PK> {
    Connection connection ;

    public AbstractDAO(Connection connection) {
        this.connection = this.connection;
    }

    public abstract String getSelectQuery();

    public abstract String getCreateQuery();

    public abstract String getUpdateQuery();

    public abstract String getDeleteQuery();

    protected abstract List<T> parseResultSet(ResultSet rs) throws SQLException;

    protected abstract void prepareStatementForInsert(PreparedStatement statement, T object) throws SQLException;

    protected abstract void prepareStatementForUpdate(PreparedStatement statement, T object) throws SQLException;

    @Override
    public T getById(PK id) throws SQLException {

        List<T> list = null;
        String sql = getSelectQuery();
        sql += "WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            list = parseResultSet(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if (list == null || list.size() == 0) {
            System.out.println("Record with PK = " + id + " not found.");
            throw new SQLException();
        }
        if (list.size() > 1) {
            System.out.println("Received more than one record.");
            throw new SQLException();
        }
        return list.iterator().next();
    }

    @Override
    public T insert(T entity) throws SQLException {
        T persistInstance;
        String sql = getCreateQuery();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            prepareStatementForInsert(statement, entity);
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new RuntimeException("On persist modify more then 1 record: " + count);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // Получаем только что вставленную запись
        sql = getSelectQuery() + " WHERE id = last_insert_id();";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            List<T> list = parseResultSet(rs);
            if ((list == null) || (list.size() != 1)) {
                throw new RuntimeException("Exception on findByPK new persist data.");
            }
            persistInstance = list.iterator().next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return persistInstance;

    }

    @Override
    public List<T> getAll() throws SQLException {
        List<T> list;
        String sql = getSelectQuery();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public void update(T entity) throws SQLException {
        String sql = getUpdateQuery();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);) {
            prepareStatementForUpdate(statement, entity); // заполнение аргументов запроса оставим на совесть потомков
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new RuntimeException("On update modify more then 1 record: " + count);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(T entity) throws SQLException {
        String sql = getDeleteQuery();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            try {
                statement.setObject(1, entity.getId());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new RuntimeException("On delete modify more then 1 record: " + count);
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
