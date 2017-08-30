package view;

import Factory.FactoryDAO;

import java.io.IOException;
import java.sql.SQLException;

public class MainController {
    public static void main(String[] args) throws SQLException, IOException {
        mainManu();
    }
    static int choice ;
    public static String mainManu() throws SQLException, IOException {
        //int choice ;
        while (true) {
            ConcollHelper.writeMessage("====================================");
            ConcollHelper.writeMessage("Select a table to work with the database");
            ConcollHelper.writeMessage("====================================");
            ConcollHelper.writeMessage("1 - Developers");
            ConcollHelper.writeMessage("2 - Skills");
            ConcollHelper.writeMessage("3 - Companies");
            ConcollHelper.writeMessage("4 - Customers");
            ConcollHelper.writeMessage("5 - Projects");
            ConcollHelper.writeMessage("6 - Exit");
            try {
                choice = ConcollHelper.readInt();
            } catch (IOException e) {
                ConcollHelper.writeMessage("Error. There is no such table.");
            }
            switch (choice) {
                case 1:
                    new ControllerDevelopers(FactoryDAO.getDevelopersDAO()).developersManu();
                    break;
                case 2:
                    new ControllerSkills(FactoryDAO.getSkillsDAO()).skillsManu();
                    break;
                case 3:
                    new ControllerCompanies(FactoryDAO.getCompaniesDAO()).companiesManu();
                    break;
                case 4:
                    new ControllerCustomers(FactoryDAO.getCustomersDAO()).customersManu();
                    break;
                case 5:
                    new ControllerProjects(FactoryDAO.getProjectsDAO()).projectsManu();
                    break;
                case 6:
                    ConcollHelper.writeMessage("Good bay!");
                    System.exit(0);
                    break;

            }
        }
    }
}
