import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class EventManagement extends JFrame {

    JTextField nameField, dateField, locationField;
    JTextArea displayArea;

    public EventManagement() {
        setTitle("Event Management System");
        setSize(500, 500);
        setLayout(new FlowLayout());

        add(new JLabel("Event Name:"));
        nameField = new JTextField(20);
        add(nameField);

        add(new JLabel("Date:"));
        dateField = new JTextField(20);
        add(dateField);

        add(new JLabel("Location:"));
        locationField = new JTextField(20);
        add(locationField);

        JButton addBtn = new JButton("Add Event");
        JButton viewBtn = new JButton("View Events");
        JButton deleteBtn = new JButton("Delete Event");

        add(addBtn);
        add(viewBtn);
        add(deleteBtn);

        displayArea = new JTextArea(15, 40);
        add(new JScrollPane(displayArea));

        addBtn.addActionListener(e -> addEvent());
        viewBtn.addActionListener(e -> viewEvents());
        deleteBtn.addActionListener(e -> deleteEvent());

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    void addEvent() {
        try {
            Connection con = DBConnection.getConnection();
            String query = "INSERT INTO events(name, date, location) VALUES (?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, nameField.getText());
            ps.setString(2, dateField.getText());
            ps.setString(3, locationField.getText());

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Event Added!");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void viewEvents() {
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM events");

            displayArea.setText("");

            while (rs.next()) {
                displayArea.append(
                    rs.getInt("id") + " | " +
                    rs.getString("name") + " | " +
                    rs.getString("date") + " | " +
                    rs.getString("location") + "\n"
                );
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void deleteEvent() {
        String id = JOptionPane.showInputDialog("Enter Event ID to delete");

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM events WHERE id=?");
            ps.setInt(1, Integer.parseInt(id));

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Deleted!");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        new EventManagement();
    }
}