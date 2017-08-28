package dao;

import connection.DBConnection;
import model.Customers;
import model.Projects;
import model.Skills;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class CustomersDAO extends AbstractDAO<Customers, Integer> {
    private class PersistCustomers extends Customers{
        public void setId(Integer id){
            super.setId(id);
        }
    }

    public CustomersDAO(Connection connection) {
        super(connection);
    }
    @Override
    public String getSelectQuery() {
        return "SELECT * FROM " + Customers.TABLE_NAME;
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO "+ Customers.TABLE_NAME+" ( " + Customers.ID_COLUMN +
        " , " + Customers.TITLE_COLUMN + ")\n"+
                "VALUES (?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE " + Customers.TABLE_NAME + " SET " + Customers.TITLE_COLUMN + "= ?\n"+
                "WHERE " + Customers.ID_COLUMN + " = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM " + Customers.TABLE_NAME+"\n"+
                "WHERE id = ?;";

    }
    public String getProjectsQuery(){
        return "SELECT p.id, p.title, p.cost\n" +
                "FROM customer_projects cp INNER JOIN customers c ON cp.customer_id = c.id\n" +
                "INNER JOIN projects p ON cp.project_id = p.id\n" +
                "WHERE c.id = ?";
    }
    public String addProjectsQuery(){
        return "INSERT INTO customer_projects (customer_id, project_id) VALUES (?, ?);";
    }


    @Override
    protected List<Customers> parseResultSet(ResultSet rs) throws SQLException {
        LinkedList<Customers> result = new LinkedList<Customers>();
        try{
            while (rs.next()){
                PersistCustomers customer = new PersistCustomers();
                customer.setId(rs.getInt(Skills.ID_COLUMN));
                customer.setTitle(rs.getString(Skills.TITLE_COLUMN));
                result.add(customer);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Customers object) throws SQLException {
        try {
            statement.setInt(1, object.getId());
            statement.setString(2,object.getTitle());
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Customers object) throws SQLException {
        try{
            statement.setString(1, object.getTitle());
            statement.setInt(2, object.getId());
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }
    public Set<Projects> showProjects(Customers customers) throws SQLException {
        HashSet<Projects> projects;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getProjectsQuery())) {
            preparedStatement.setLong(1, customers.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            projects = new HashSet<>();
            while (resultSet.next()) {
                Projects project = new Projects();
                project.setId(resultSet.getInt(Projects.ID_COLUMN));
                project.setTitle(resultSet.getString(Projects.TITLE_COLUMN));
                project.setCost(resultSet.getInt(Projects.COST_COLUMN));
                projects.add(project);
            customers.setProjects(projects);
        }
    }
        return customers.getProjects();
    }
    public void addCustomerProjects(Customers customers, Projects projects)throws SQLException{
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(addProjectsQuery())){
            statement.setInt(1,customers.getId());
            statement.setInt(2,projects.getId());
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new RuntimeException("On persist modify more then 1 record: " + count);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
