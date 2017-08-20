package dao;

import connection.DBConnection;
import model.Companies;
import model.Projects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class CompaniesDAO extends AbstractDAO<Companies, Integer>{
    private class PersistCompanies extends Companies{
        public void setId(Integer id){
            super.setId(id);
        }
    }

    public CompaniesDAO(Connection connection) {
        super(connection);
    }
    @Override
    public String getSelectQuery() {
        return "SELECT id, title FROM " + Companies.TABLE_NAME;
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO "+ Companies.TABLE_NAME+" ( id, title)\n"+
                "VALUES (?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE INTO " + Companies.TABLE_NAME + " SET title = ?\n"+
                "WHERE id = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM" + Companies.TABLE_NAME+"\n"+
                "WHERE id = ?;";

    }
    public  String getProjectsQuery(){
        return "SELECT  p.title\n" +
                "FROM company_projects cp INNER JOIN companies c ON cp.company_id = c.id\n" +
                "INNER JOIN projects p ON cp.project_id = p.id\n" +
                "WHERE c.id = ?";
    }

    @Override
    public Companies create() throws SQLException {
        Companies companies = new Companies();
        return insert(companies);
    }

    @Override
    protected List<Companies> parseResultSet(ResultSet rs) throws SQLException {
        LinkedList<Companies> result = new LinkedList<Companies>();
        try{
            while (rs.next()){
                PersistCompanies company = new PersistCompanies();
                company.setId(rs.getInt(Companies.ID_COLUMN));
                company.setTitle(rs.getString(Companies.TITLE_COLUMN));
                result.add(company);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Companies object) throws SQLException {
        try {
            statement.setInt(1, object.getId());
            statement.setString(2,object.getTitle());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Companies object) throws SQLException {
        try{
            statement.setString(1, object.getTitle());
            statement.setInt(2, object.getId());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
    private HashSet<Projects> showProjects(Companies companies) throws SQLException {
        HashSet<Projects> projects;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getProjectsQuery())){
            preparedStatement.setLong(1, companies.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            projects = new HashSet<>();
            while (resultSet.next()) {
                Projects project = new Projects();
                project.setTitle(resultSet.getString("title"));
                projects.add(project);
            }
        }
        return projects;
    }
}
