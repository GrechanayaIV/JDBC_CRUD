package view;

import Factory.FactoryDAO;
import dao.CustomersDAO;
import model.Customers;
import model.Projects;

import java.io.IOException;
import java.sql.SQLException;

public class ControllerCustomers {
    private CustomersDAO customersDAO;
    int choice = -1;

    public ControllerCustomers(CustomersDAO customersDAO) {
        this.customersDAO = customersDAO;
    }

    public void customersManu() throws SQLException, IOException {
        while (true) {
            ConcollHelper.writeMessage("====================================");
            ConcollHelper.writeMessage("Input the command number");
            ConcollHelper.writeMessage("====================================");
            ConcollHelper.writeMessage("1 - Create customer");
            ConcollHelper.writeMessage("2 - Show customer`s projects");
            ConcollHelper.writeMessage("3 - Update data");
            ConcollHelper.writeMessage("4 - Get all data");
            ConcollHelper.writeMessage("5 - Delete customer");
            ConcollHelper.writeMessage("6 - Get customer by Id");
            ConcollHelper.writeMessage("7 - Add customer`s project");
            ConcollHelper.writeMessage("0 - Return to main menu");
            try {
                choice = ConcollHelper.readInt();
                if(choice > 7){
                    ConcollHelper.writeMessage("Error. There is no such command. Try again");
                }
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
                    addCustomerProject();
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
        Customers customers = new Customers(id,title);
        FactoryDAO.getCustomersDAO().insert(customers);
        ConcollHelper.writeMessage("Customer created successfully.\n" +
                "You can see the result with the getAll command");
    }

    private void update() throws IOException, SQLException {
        ConcollHelper.writeMessage("Input id: ");
        Integer id = ConcollHelper.readInt();
        ConcollHelper.writeMessage("Input title: ");
        String title = ConcollHelper.readString();
        Customers customers = new Customers(id,title);
        FactoryDAO.getCustomersDAO().update(customers);
        ConcollHelper.writeMessage("Customer updated successfully.\n"+
                "You can see the result with the getAll command");

    }
    private void getAll() throws SQLException {
        FactoryDAO.getCustomersDAO().getAll().forEach(System.out::println);

    }
    private void delete() throws IOException, SQLException {
        ConcollHelper.writeMessage("Input id: ");
        Integer id = ConcollHelper.readInt();
        Customers customers = new Customers(id);
        FactoryDAO.getCustomersDAO().delete(customers);
        ConcollHelper.writeMessage("Customer deleted successfully.\n" +
                "You can see the result with the getAll command");
    }
    private void geyById() throws IOException, SQLException{
        ConcollHelper.writeMessage("Input id: ");
        Integer id = ConcollHelper.readInt();
        Customers customers = new Customers(id);
        ConcollHelper.writeMessage(String.valueOf(FactoryDAO.getCustomersDAO().getById(id)));
    }
    private void showProjects() throws IOException, SQLException{
        ConcollHelper.writeMessage("Input id: ");
        Integer id = ConcollHelper.readInt();
        Customers customers = new Customers(id);
        FactoryDAO.getCustomersDAO().showProjects(customers).forEach(System.out::println);
    }
    private void addCustomerProject() throws IOException, SQLException{
        ConcollHelper.writeMessage("Input customer`s id:");
        Integer customer_id = ConcollHelper.readInt();
        Customers customers = new Customers(customer_id);
        ConcollHelper.writeMessage("Input project`s id: ");
        Integer project_id = ConcollHelper.readInt();
        Projects projects = new Projects(project_id);
        FactoryDAO.getCustomersDAO().addCustomerProjects(customers, projects);
        ConcollHelper.writeMessage("Project add successfully.\n" +
                "You can see the result with the showProjects command");
    }
}
