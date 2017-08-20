package dao;

import connection.DBConnection;
import model.Developers;
import model.Skills;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class DevelopersDAO extends AbstractDAO<Developers , Integer>{
    private class PersistDevelopers extends Developers{
        public void setId(Integer id){
            super.setId(id);
        }
    }

    public DevelopersDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Developers create() throws SQLException {
        Developers developers = new Developers();
        return insert(developers);
    }

    @Override
    public String getSelectQuery() {
        return "SELECT id, fname, lname, salary FROM "+Developers.TABLE_NAME;
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO model.Developers ( id, fname, lname, salary)\n"+
                "VALUES (?, ?, ?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE INTO model.Developers SET fname = ?, lname = ?, salary = ?\n"+
                "WHERE id = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM model.Developers\n"+
                "WHERE id = ?;";
    }
    public String getSkillsQuery(){
        return "SELECT s.title FROM skills s " +
                "INNER JOIN developers_skills ds ON s.id = ds.skill_id" +
                "INNER JOIN developers d ON ds.developer_id = d.id " +
                "WHERE d.id=?";
    }

    @Override
    protected List<Developers> parseResultSet(ResultSet rs) throws SQLException {
        LinkedList<Developers> result = new LinkedList<Developers>();
        try{
            while (rs.next()){
                PersistDevelopers developer = new PersistDevelopers();
                developer.setId(rs.getInt( Developers.ID_COLUMN));
                developer.setFname(rs.getString( Developers.FNAME_COLUMN));
                developer.setLname(rs.getString( Developers.LNAME_COLUMN));
                developer.setSalary(rs.getInt( Developers.SALARY_COLUMN));
                result.add(developer);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Developers object) throws SQLException {
        try {
            statement.setInt(1, object.getId());
            statement.setString(2, object.getFname());
            statement.setString(3, object.getLname());
            statement.setInt(4, object.getSalary());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Developers object) throws SQLException {
        try {
            statement.setString(1, object.getFname());
            statement.setString(2, object.getLname());
            statement.setInt(3, object.getSalary());
            statement.setInt(4, object.getId());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    private HashSet<Skills> showSkills(Developers developer) throws SQLException {
        HashSet<Skills> skills;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getSkillsQuery())){
            preparedStatement.setLong(1, developer.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            skills = new HashSet<>();
            while (resultSet.next()) {
                Skills skill = new Skills();
                skill.setTitle(resultSet.getString("title"));
                skills.add(skill);
            }
        }
        return skills;
    }
}
