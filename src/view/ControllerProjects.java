package view;

import Factory.FactoryDAO;
import dao.ProjectsDAO;
import model.Developers;
import model.Projects;

import java.io.IOException;
import java.sql.SQLException;

public class ControllerProjects {
    private ProjectsDAO projectsDAO;
    int choice = -1;

    public ControllerProjects(ProjectsDAO projectsDAO) {
        this.projectsDAO = projectsDAO;
    }

    public void projectsManu() throws SQLException, IOException {
        while (true) {
            ConcollHelper.writeMessage("====================================");
            ConcollHelper.writeMessage("Input the command number");
            ConcollHelper.writeMessage("====================================");
            ConcollHelper.writeMessage("1 - Create project");
            ConcollHelper.writeMessage("2 - Show project`s developers");
            ConcollHelper.writeMessage("3 - Update data");
            ConcollHelper.writeMessage("4 - Get all data");
            ConcollHelper.writeMessage("5 - Delete project");
            ConcollHelper.writeMessage("6 - Get project by Id");
            ConcollHelper.writeMessage("7 - Add project`s developer");
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
                    showDevelopers();
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
                    addProjectDeveloper();
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
        ConcollHelper.writeMessage("Input cost: ");
        Integer cost = ConcollHelper.readInt();
        Projects projects = new Projects(id, title,cost);
        FactoryDAO.getProjectsDAO().insert(projects);
        ConcollHelper.writeMessage("Project created successfully.\n" +
                "You can see the result with the getAll command");
    }

    private void update() throws IOException, SQLException {
        ConcollHelper.writeMessage("Input id: ");
        Integer id = ConcollHelper.readInt();
        ConcollHelper.writeMessage("Input title: ");
        String title = ConcollHelper.readString();
        ConcollHelper.writeMessage("Input cost: ");
        Integer cost = ConcollHelper.readInt();
        Projects projects = new Projects(id,title,cost);
        FactoryDAO.getProjectsDAO().update(projects);
        ConcollHelper.writeMessage("Project updated successfully.\n"+
                "You can see the result with the getAll command");

    }
    private void getAll() throws SQLException {
        FactoryDAO.getProjectsDAO().getAll().forEach(System.out::println);

    }
    private void delete() throws IOException, SQLException {
        ConcollHelper.writeMessage("Input id: ");
        Integer id = ConcollHelper.readInt();
        Projects projects = new Projects(id);
        FactoryDAO.getProjectsDAO().delete(projects);
        ConcollHelper.writeMessage("Project deleted successfully.\n" +
                "You can see the result with the getAll command");
    }
    private void geyById() throws IOException, SQLException {
        ConcollHelper.writeMessage("Input id: ");
        Integer id = ConcollHelper.readInt();
        Projects projects = new Projects(id);
        ConcollHelper.writeMessage(String.valueOf(FactoryDAO.getProjectsDAO().getById(id)));
    }
    private void showDevelopers() throws IOException, SQLException{
        ConcollHelper.writeMessage("Input id: ");
        Integer id = ConcollHelper.readInt();
        Projects projects = new Projects(id);
        FactoryDAO.getProjectsDAO().showDevelopers(projects).forEach(System.out::println);
    }
    private void addProjectDeveloper() throws IOException, SQLException{
        ConcollHelper.writeMessage("Input project`s id: ");
        Integer project_id = ConcollHelper.readInt();
        Projects projects = new Projects(project_id);
        ConcollHelper.writeMessage("Input developer`s id:");
        Integer developer_id = ConcollHelper.readInt();
        Developers developers = new Developers(developer_id);
        FactoryDAO.getProjectsDAO().addProjectDeveloper(projects, developers);
        ConcollHelper.writeMessage("Developer add successfully.\n" +
                "You can see the result with the showDeveloper command");
    }
}
