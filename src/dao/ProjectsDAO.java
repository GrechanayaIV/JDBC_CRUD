package dao;

import connection.DBConnection;
import model.Developers;
import model.Projects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ProjectsDAO extends AbstractDAO<Projects, Integer> {
    private class PersistProjects extends Projects{
        public void setId(Integer id){
            super.setId(id);
        }
    }

    public ProjectsDAO(Connection connection){
        super(connection);
    }
    @Override
    public String getSelectQuery() {
        return "SELECT * FROM " + Projects.TABLE_NAME;
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO "+ Projects.TABLE_NAME+" ( " + Projects.ID_COLUMN +
                ", " + Projects.TITLE_COLUMN + ", " + Projects.COST_COLUMN + " )\n"+
                "VALUES (?, ?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE " + Projects.TABLE_NAME + " SET title = ?, cost = ?\n"+
                "WHERE id = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM " + Projects.TABLE_NAME+"\n"+
                "WHERE " + Projects.ID_COLUMN + " = ?;";

    }
    public String getDevelopersQuery() {
        return "SELECT d.id, d.fname, d.lname , d.salary " +
                "FROM project_developers pd INNER JOIN projects p ON pd.project_id = p.id " +
                "INNER JOIN developers d ON pd.developer_id = d.id " +
                "WHERE p.id = ?";
    }
    public String addDevelopersQuery(){
        return "INSERT INTO project_developers (project_id, developer_id) VALUES (?, ?);";
    }

    @Override
    protected List<Projects> parseResultSet(ResultSet rs) throws SQLException {
        LinkedList<Projects> result = new LinkedList<Projects>();
        try{
            while (rs.next()){
                PersistProjects projects = new PersistProjects();
                projects.setId(rs.getInt(Projects.ID_COLUMN));
                projects.setTitle(rs.getString(Projects.TITLE_COLUMN));
                projects.setCost(rs.getInt(Projects.COST_COLUMN));

                result.add(projects);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Projects object) throws SQLException {
        try {
            statement.setInt(1, object.getId());
            statement.setString(2, object.getTitle());
            statement.setInt(3, object.getCost());
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Projects object) throws SQLException {
        try{
            statement.setString(1, object.getTitle());
            statement.setInt(2,object.getCost());
            statement.setInt(3, object.getId());
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }
    public Set<Developers> showDevelopers(Projects projects) throws SQLException {
        HashSet<Developers> developers;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getDevelopersQuery())){
            preparedStatement.setLong(1, projects.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            developers = new HashSet<>();
            while (resultSet.next()) {
                Developers developer = new Developers();
                developer.setId(resultSet.getInt(Developers.ID_COLUMN));
                developer.setFname(resultSet.getString(Developers.FNAME_COLUMN));
                developer.setLname(resultSet.getString(Developers.LNAME_COLUMN));
                developer.setSalary(resultSet.getInt(Developers.SALARY_COLUMN));
                developers.add(developer);
                projects.setDevelopers(developers);
            }
        }
        return projects.getDevelopers();
    }
    public void addProjectDeveloper(Projects projects, Developers developers)throws SQLException{
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(addDevelopersQuery())){
            statement.setInt(1,projects.getId());
            statement.setInt(2,developers.getId());
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new RuntimeException("On persist modify more then 1 record: " + count);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
