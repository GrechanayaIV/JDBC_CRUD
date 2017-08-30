package view;

import Factory.FactoryDAO;
import dao.CompaniesDAO;
import model.Companies;
import model.Projects;

import java.io.IOException;
import java.sql.SQLException;

public class ControllerCompanies {

    private CompaniesDAO companiesDAO;
    int choice = -1;

    public ControllerCompanies(CompaniesDAO companiesDAO) {
        this.companiesDAO = companiesDAO;
    }

    public void companiesManu() throws SQLException, IOException {
        while (true) {
            ConcollHelper.writeMessage("====================================");
            ConcollHelper.writeMessage("Input the command number");
            ConcollHelper.writeMessage("====================================");
            ConcollHelper.writeMessage("1 - Create company");
            ConcollHelper.writeMessage("2 - Show company`s projects");
            ConcollHelper.writeMessage("3 - Update data");
            ConcollHelper.writeMessage("4 - Get all data");
            ConcollHelper.writeMessage("5 - Delete company");
            ConcollHelper.writeMessage("6 - Get company by Id");
            ConcollHelper.writeMessage("7 - Add company`s project");
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
                    showProjects();
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
                    addCompanyProject();
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
        ConcollHelper.writeMessage("Input title");
        String title = ConcollHelper.readString();
        Companies companies = new Companies(id, title);
        FactoryDAO.getCompaniesDAO().insert(companies);
        ConcollHelper.writeMessage("Companies created successfully.\n" +
                "You can see the result with the getAll command");
    }

    private void update() throws IOException, SQLException {
        ConcollHelper.writeMessage("Input id: ");
        Integer id = ConcollHelper.readInt();
        ConcollHelper.writeMessage("Input title: ");
        String title = ConcollHelper.readString();
        Companies companies = new Companies(id, title);
        FactoryDAO.getCompaniesDAO().update(companies);
        ConcollHelper.writeMessage("Companies updated successfully.\n"+
                "You can see the result with the getAll command");

    }
    private void getAll() throws SQLException {
        FactoryDAO.getCompaniesDAO().getAll().forEach(System.out::println);

    }
    private void delete() throws IOException, SQLException {
        ConcollHelper.writeMessage("Input id: ");
        Integer id = ConcollHelper.readInt();
        Companies companies = new Companies(id);
        FactoryDAO.getCompaniesDAO().delete(companies);
        ConcollHelper.writeMessage("Companies deleted successfully.\n" +
                "You can see the result with the getAll command");
    }
    private void geyById() throws IOException, SQLException{
        ConcollHelper.writeMessage("Input id: ");
        Integer id = ConcollHelper.readInt();
        Companies companies = new Companies(id);
        ConcollHelper.writeMessage(String.valueOf(FactoryDAO.getCompaniesDAO().getById(id)));
    }
    private void showProjects() throws IOException, SQLException {
        ConcollHelper.writeMessage("Input id: ");
        Integer id = ConcollHelper.readInt();
        Companies companies = new Companies(id);
        FactoryDAO.getCompaniesDAO().showProjects(companies).forEach(System.out::println);
    }
    private void addCompanyProject() throws IOException, SQLException{
        ConcollHelper.writeMessage("Input company`s id:");
        Integer company_id = ConcollHelper.readInt();
        Companies companies = new Companies(company_id);
        ConcollHelper.writeMessage("Input project`s id: ");
        Integer project_id = ConcollHelper.readInt();
        Projects projects = new Projects(project_id);
        FactoryDAO.getCompaniesDAO().addCompanyProjects(companies, projects);
        ConcollHelper.writeMessage("Project add successfully.\n" +
                "You can see the result with the showProjects command");
    }
}
