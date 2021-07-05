package todo_manager2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;


public class Panel  {

    public String result = "";

   public Panel() throws SQLException, ClassNotFoundException {
       this.result = "Task List:\n" + DBMapper.getTasks();
   }

    void showPanel(String tasks) {

        JPanel panel = new JPanel();
        JButton save = new JButton("SAVE");
        JButton delete = new JButton("DELETE");
        JButton set = new JButton("SET");

        JTextField field1 = new JTextField(10);
        JTextField field2 = new JTextField(10);
        JTextField field3 = new JTextField(10);
        JTextField field4 = new JTextField(10);
        JTextArea field5 = new JTextArea(result);

        JComboBox<Event> dropDownList = new JComboBox();
        dropDownList.setModel(new DefaultComboBoxModel<>(Event.values()));
        dropDownList.getSelectedObjects();
        dropDownList.setEditable(true);

        String[] taskStatus = {"DONE", "NOT_DONE", "PROGRESS"};
        JComboBox taskStatusBox = new JComboBox(taskStatus);
        taskStatusBox.getSelectedObjects();


        GridLayout layout = new GridLayout(6, 7); // appearance of the panel
        panel.setLayout(layout);

        panel.add(new JLabel("Create task: "));
        panel.add(field1);
        panel.add(dropDownList);
        panel.add(new JLabel("Set Date (format MM/dd/yyyy): "));
        panel.add(field2);
        panel.add(save);
        panel.add(new JLabel("To Delete insert task id: "));
        panel.add(field3);
        panel.add(delete);
        panel.add(new JLabel("To set task status insert task id: "));
        panel.add(field4);
        panel.add(taskStatusBox);
        panel.add(new JLabel(" "));
        panel.add(new JLabel(" "));
        panel.add(set);
        panel.add(field5);


        ActionListener actionListenerSave = e -> {
            Connection connection = null;
            try {
                connection = DBConnection.DBConnection();
            } catch (ClassNotFoundException | SQLException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
            if (connection != null) {
                try {
                    String eventNext = field1.getText();
                    String eventDropList = Arrays.toString(dropDownList.getSelectedObjects());
                    String dateInput = field2.getText();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "MM/dd/yyyy" ) ;
                    LocalDate formattedDateInput = LocalDate.parse( dateInput, formatter ) ;

                    Statement statement = connection.createStatement();
                    String sql = "INSERT into events (description, event, event_date) values ('" + eventNext + "', '" + eventDropList + "' , '" + formattedDateInput + "')";
                    statement.execute(sql);
                    System.out.println("Event has been added to the list");
                    connection.close();
                } catch (Exception exc) {
                    System.out.println("Event was not added to DB");
                    exc.printStackTrace();
                }
            }
        };
        ActionListener actionListenerDelete = e -> {
            Connection connection = null;
            try {
                connection = DBConnection.DBConnection();
            } catch (ClassNotFoundException | SQLException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
            if (connection != null) {
                try {
                    int taskId = Integer.parseInt(field3.getText());
                    Statement statement = connection.createStatement();
                    String sql = "DELETE from events WHERE (event_id) = (' " + taskId + "')";

                    statement.execute(sql);
                    System.out.println("Task was removed from the list");
                    connection.close();


                } catch (Exception exc) {
                    System.out.println("Error. Task was NOT removed from the list ");
                    exc.printStackTrace();
                }
            }
        };
        ActionListener actionListenerSet = e -> {
            Connection connection = null;
            try {
                connection = DBConnection.DBConnection();
            } catch (ClassNotFoundException | SQLException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
            if (connection != null) {
                try {
                    Object[] setStatus =  taskStatusBox.getSelectedObjects();
                    int taskId = Integer.parseInt(field4.getText());
                    Statement statement = connection.createStatement();
                    String sql = "UPDATE events SET status = ' " + Arrays.toString(setStatus) + "' WHERE event_id = '" + taskId + "'" ;
                    statement.executeUpdate(sql);
                    System.out.println("Task status was changed to: " + Arrays.toString(setStatus));
                    connection.close();
                } catch (Exception exc) {
                    System.out.println("Error. Task status was NOT changed");
                    exc.printStackTrace();
                }
            }
        };

                save.addActionListener(actionListenerSave);
                delete.addActionListener(actionListenerDelete);
                set.addActionListener(actionListenerSet);


        JFrame frame = new JFrame("TODO MANAGER");
        //frame.pack();    // what this is doing ? what is included in the pack ?
        frame.setContentPane(panel);
        frame.setSize(800, 800);
        frame.setVisible(true);

    }

    }

