package view;

import Factory.FactoryDAO;
import dao.SkillsDAO;
import model.Skills;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

public class ControllerSkills {
    private SkillsDAO skillsDAO;
    int choice = -1;

    public ControllerSkills(SkillsDAO skillsDAO) {
        this.skillsDAO = skillsDAO;
    }

    public void skillsManu() throws SQLException, IOException {
        while (true) {
            ConcollHelper.writeMessage("====================================");
            ConcollHelper.writeMessage("Input the command number");
            ConcollHelper.writeMessage("====================================");
            ConcollHelper.writeMessage("1 - Create skills");
            ConcollHelper.writeMessage("2 - Update data");
            ConcollHelper.writeMessage("3 - Get all data");
            ConcollHelper.writeMessage("4 - Delete skills");
            ConcollHelper.writeMessage("5 - Get skill by id");
            ConcollHelper.writeMessage("0 - Return to main menu");
            try {
                choice = ConcollHelper.readInt();
            } catch (IOException e) {
                ConcollHelper.writeMessage("Error. There is no such command.");
            }

            switch (choice) {
                case 1:
                    create();
                    break;
                case 2:
                    update();
                    break;
                case 3:
                    getAll();
                    break;
                case 4:
                    delete();
                    break;
                case 5:
                    getById();
                    break;
                case 0:
                    MainConsolHelper.mainManu();
                    break;

            }
        }

    }
    private void create() throws SQLException, IOException {
        ConcollHelper.writeMessage("Input id");
        Integer id = ConcollHelper.readInt();
        ConcollHelper.writeMessage("Input title");
        String title = ConcollHelper.readString();
        Skills skills = new Skills(id, title);
        FactoryDAO.getSkillsDAO().insert(skills);
        ConcollHelper.writeMessage("Skills created successfully.\n" +
                "You can see the result with the getAll command");

    }

    private void update() throws IOException, SQLException {
        ConcollHelper.writeMessage("Input id: ");
        Integer id = ConcollHelper.readInt();
        ConcollHelper.writeMessage("Input title: ");
        String title = ConcollHelper.readString();
        Skills skills = new Skills(id,title);
        FactoryDAO.getSkillsDAO().update(skills);
        ConcollHelper.writeMessage("Skills updated successfully.\n"+
                "You can see the result with the getAll command");
    }
    private void getAll() throws SQLException {
        FactoryDAO.getSkillsDAO().getAll().forEach(System.out::println);

    }
    private void delete() throws IOException, SQLException {
        ConcollHelper.writeMessage("Input id: ");
        Integer id = ConcollHelper.readInt();
        Skills skills = new Skills(id);
        FactoryDAO.getSkillsDAO().delete(skills);
        ConcollHelper.writeMessage("Skills deleted successfully.\n" +
                "You can see the result with the getAll command");

    }
    private void getById() throws IOException, SQLException{
        ConcollHelper.writeMessage("Input id: ");
        Integer id = ConcollHelper.readInt();
        Skills skills = new Skills(id);
        ConcollHelper.writeMessage(String.valueOf(FactoryDAO.getSkillsDAO().getById(id)));
    }
}
