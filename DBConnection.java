package todo_manager2;
import java.sql.*;

public class DBConnection {


   public static Connection DBConnection() throws ClassNotFoundException, SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection
                    ("jdbc:mysql://localhost:3306/todo_manager", "root", "root");
            if (!connection.isClosed()){
                System.out.println("Connected to SQL DB!");
            }
            return connection;

        } catch (ClassNotFoundException e) {
            System.out.println("Connection error");
            e.printStackTrace();
        }
        return null;
    }


}