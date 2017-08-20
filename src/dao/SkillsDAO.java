package dao;

import model.Skills;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class SkillsDAO extends AbstractDAO<Skills, Integer> {
    private class PersistSkills extends Skills{
        public void setId(Integer id){
            super.setId(id);
        }
    }

    public SkillsDAO() {
        //super(connection);
        super(connection);
    }

    @Override
    public String getSelectQuery() {
        return "SELECT id, title FROM " + Skills.TABLE_NAME;
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO "+ Skills.TABLE_NAME+" ( id, title)\n"+
        "VALUES (?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE INTO " + Skills.TABLE_NAME + " SET title = ?\n"+
                "WHERE id = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM" + Skills.TABLE_NAME+"\n"+
                "WHERE id = ?;";

    }

    @Override
    public Skills create() throws SQLException {
        Skills skills = new Skills();
        return insert(skills);
    }

    @Override
    protected List<Skills> parseResultSet(ResultSet rs) throws SQLException {
        LinkedList<Skills> result = new LinkedList<Skills>();
        try{
            while (rs.next()){
                PersistSkills skill = new PersistSkills();
                skill.setId(rs.getInt(Skills.ID_COLUMN));
                skill.setTitle(rs.getString(Skills.TITLE_COLUMN));
                result.add(skill);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Skills object) throws SQLException {
        try {
            statement.setInt(1, object.getId());
            statement.setString(2,object.getTitle());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Skills object) throws SQLException {
        try{
            statement.setString(1, object.getTitle());
            statement.setInt(2, object.getId());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

}
