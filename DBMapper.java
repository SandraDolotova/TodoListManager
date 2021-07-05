package todo_manager2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBMapper {

    public static String getTasks() throws SQLException, ClassNotFoundException {

        Connection connection = DBConnection.DBConnection();

        String result = "";

        if (connection!=null){
            try{
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM events");
                StringBuilder stringBuffer = new StringBuilder();

                while (resultSet.next()){
                    stringBuffer.append(resultSet.getString(1));
                    stringBuffer.append(": ");
                    stringBuffer.append(resultSet.getString(3));
                    stringBuffer.append(" - ");
                    stringBuffer.append(resultSet.getString(4));
                    stringBuffer.append(" - ");
                    stringBuffer.append(resultSet.getString(5));
                    stringBuffer.append(", ");
                    stringBuffer.append(resultSet.getDate(2));
                    stringBuffer.append("; " + "\n");
                }
                result = stringBuffer.toString();
                connection.close();
            }catch (Exception exc){
                exc.printStackTrace();
            }
        }
        return result;
    }

}
