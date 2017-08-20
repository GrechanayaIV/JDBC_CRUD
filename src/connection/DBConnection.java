package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection indtance;
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";

    private DBConnection() {
        try{
            Class.forName(DRIVER);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static Connection getConnection()throws SQLException{
        if(indtance == null){
            indtance = new DBConnection();
        }
        try {
            return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
        }catch (SQLException e){
            throw e;
        }
    }
    public static void close(Connection connection){
        try {
            if(connection != null){
                connection.close();
                connection = null;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
