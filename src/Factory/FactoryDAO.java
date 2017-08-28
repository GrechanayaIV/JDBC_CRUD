package Factory;

import connection.DBConnection;
import dao.*;
import model.Developers;

import java.sql.SQLException;

public class FactoryDAO {
    private static DevelopersDAO developersDAO;
    private static SkillsDAO skillsDAO;
    private static CompaniesDAO companiesDAO;
    private static CustomersDAO customersDAO;
    private static ProjectsDAO projectsDAO;

    public static DevelopersDAO getDevelopersDAO() throws SQLException {
        if(developersDAO == null){
            developersDAO = new DevelopersDAO(DBConnection.getConnection());
        }
        return developersDAO;
    }

    public static SkillsDAO getSkillsDAO() throws SQLException {
        if(skillsDAO == null){
            skillsDAO = new SkillsDAO(DBConnection.getConnection());
        }
        return skillsDAO;
    }

    public static CompaniesDAO getCompaniesDAO() throws SQLException{
        if(companiesDAO == null){
            companiesDAO = new CompaniesDAO(DBConnection.getConnection());
        }
        return companiesDAO;
    }

    public static CustomersDAO getCustomersDAO() throws SQLException{
        if(customersDAO == null){
            customersDAO = new CustomersDAO(DBConnection.getConnection());
        }
        return customersDAO;
    }

    public static ProjectsDAO getProjectsDAO() throws SQLException{
        if(projectsDAO == null){
            projectsDAO = new ProjectsDAO(DBConnection.getConnection());
        }
        return projectsDAO;
    }


}
