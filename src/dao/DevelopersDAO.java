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
import java.util.Set;

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
    public String getSelectQuery() {
        return "SELECT id, fname, lname, salary FROM "+Developers.TABLE_NAME;
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO developers ( id, fname, lname, salary)\n"+
                "VALUES (?, ?, ?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE " + Developers.TABLE_NAME + " SET " + Developers.FNAME_COLUMN + " = ?, " + Developers.LNAME_COLUMN+"= ?, "+Developers.SALARY_COLUMN+ "= ?\n"+
                "WHERE id = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM "+ Developers.TABLE_NAME+
                " WHERE id = ?;";
    }
    public String getSkillsQuery(){
        return "SELECT s.id , s.title FROM skills s " +
                "INNER JOIN developers_skills ds ON s.id = ds.skill_id " +
                "INNER JOIN developers d ON ds.developer_id = d.id " +
                "WHERE d.id = ? ;";
    }
    public String addSkillsQuery(){
        return "INSERT INTO developers_skills (developer_id, skill_id) VALUES (?, ?);";
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
    public Set<Skills> showSkills(Developers developer) throws SQLException {
        HashSet<Skills> skills;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(getSkillsQuery())){
            statement.setInt(1, developer.getId());
            ResultSet resultSet = statement.executeQuery();
            skills = new HashSet<>();
            while (resultSet.next()) {
                Skills skill = new Skills();
                skill.setId(resultSet.getInt(Skills.ID_COLUMN));
                skill.setTitle(resultSet.getString(Skills.TITLE_COLUMN));
                skills.add(skill);
                developer.setSkills(skills);
            }
        }
        return developer.getSkills();
    }
    public void addDevelopersSkills(Developers developers, Skills skills)throws SQLException{
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(addSkillsQuery())){
            statement.setInt(1,developers.getId());
            statement.setInt(2,skills.getId());
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new RuntimeException("On persist modify more then 1 record: " + count);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
