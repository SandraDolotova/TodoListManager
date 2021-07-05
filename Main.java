package todo_manager2;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {


        Panel panel = new Panel();
        panel.showPanel(DBMapper.getTasks());


    }
}