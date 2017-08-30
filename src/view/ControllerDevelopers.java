package view;

import Factory.FactoryDAO;
import dao.DevelopersDAO;
import model.Developers;
import model.Skills;

import java.io.IOException;
import java.sql.SQLException;

public class ControllerDevelopers {
    private DevelopersDAO developersDAO;
    int choice = -1;

    public ControllerDevelopers(DevelopersDAO developersDAO) {
        this.developersDAO = developersDAO;
    }

    public void developersManu() throws SQLException, IOException {
        while (true) {
            ConcollHelper.writeMessage("====================================");
            ConcollHelper.writeMessage("Input the command number");
            ConcollHelper.writeMessage("====================================");
            ConcollHelper.writeMessage("1 - Create developer");
            ConcollHelper.writeMessage("2 - Show developer`s skills");
            ConcollHelper.writeMessage("3 - Update data");
            ConcollHelper.writeMessage("4 - Get all data");
            ConcollHelper.writeMessage("5 - Delete developer");
            ConcollHelper.writeMessage("6 - Get developer by Id");
            ConcollHelper.writeMessage("7 - Add developer`s skill");
            ConcollHelper.writeMessage("0 - Return to main menu");
            try {
                choice = ConcollHelper.readInt();
                if(choice > 7 ) ConcollHelper.writeMessage("Error. There is no such command. Try again");
            } catch (IOException e) {
                ConcollHelper.writeMessage("Error. There is no such command.");
            }

            switch (choice) {
                case 1:
                    create();
                    break;
                case 2:
                    showSkills();
                    break;
                case 3:
                    update();
                    break;
                case 4:
                    getAll();
                    break;
                case 5:
                    delete();
                    break;
                case 6:
                    geyById();
                    break;
                case 7:
                    addDeveloperSkills();
                    break;
                case 0:
                    MainController.mainManu();
                    break;

            }
        }

    }
    private void create() throws SQLException, IOException {
        ConcollHelper.writeMessage("Input id");
        Integer id = ConcollHelper.readInt();
        ConcollHelper.writeMessage("Input first name:");
        String fname = ConcollHelper.readString();
        ConcollHelper.writeMessage("Input last name:");
        String lname = ConcollHelper.readString();
        ConcollHelper.writeMessage("Input salary:");
        Integer salary = ConcollHelper.readInt();
        Developers developers = new Developers(id,fname,lname,salary);
        FactoryDAO.getDevelopersDAO().insert(developers);
        ConcollHelper.writeMessage("Developer created successfully.\n" +
                "You can see the result with the getAll command");

    }

    private void update() throws IOException, SQLException {
        ConcollHelper.writeMessage("Input id: ");
        Integer id = ConcollHelper.readInt();
        ConcollHelper.writeMessage("Input first name:");
        String fname = ConcollHelper.readString();
        ConcollHelper.writeMessage("Input last name:");
        String lname = ConcollHelper.readString();
        ConcollHelper.writeMessage("Input salary:");
        Integer salary = ConcollHelper.readInt();
        Developers developers = new Developers(id,fname,lname,salary);
        FactoryDAO.getDevelopersDAO().update(developers);
        ConcollHelper.writeMessage("Developers updated successfully.\n"+
                "You can see the result with the getAll command");

    }
    private void getAll() throws SQLException {
        FactoryDAO.getDevelopersDAO().getAll().forEach(System.out::println);

    }
    private void delete() throws IOException, SQLException {
        ConcollHelper.writeMessage("Input id: ");
        Integer id = ConcollHelper.readInt();
        Developers developers = new Developers(id);
        FactoryDAO.getDevelopersDAO().delete(developers);
        ConcollHelper.writeMessage("Developers deleted successfully.\n" +
                "You can see the result with the getAll command");
    }
    private void geyById() throws IOException, SQLException{
        ConcollHelper.writeMessage("Input id: ");
        Integer id = ConcollHelper.readInt();
        Developers developers = new Developers(id);
        ConcollHelper.writeMessage(String.valueOf(FactoryDAO.getDevelopersDAO().getById(id)));
    }
    private void showSkills() throws IOException, SQLException{
        ConcollHelper.writeMessage("Input id: ");
        Integer id = ConcollHelper.readInt();
        Developers developers = new Developers(id);
        FactoryDAO.getDevelopersDAO().showSkills(developers).forEach(System.out::println);
    }
    private void addDeveloperSkills() throws IOException, SQLException{
        ConcollHelper.writeMessage("Input developer`s id:");
        Integer developer_id = ConcollHelper.readInt();
        Developers developers = new Developers(developer_id);
        ConcollHelper.writeMessage("Input skil`s id: ");
        Integer skill_id = ConcollHelper.readInt();
        Skills skills = new Skills(skill_id);
        FactoryDAO.getDevelopersDAO().addDevelopersSkills(developers, skills);
        ConcollHelper.writeMessage("Skill add successfully.\n" +
                "You can see the result with the showSkills command");
    }
}
