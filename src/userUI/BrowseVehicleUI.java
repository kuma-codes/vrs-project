package userUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.sql.*;
import java.util.ArrayList;

public class BrowseVehicleUI extends JFrame {
    
    private static final Font F1 = new Font("Arial", Font.BOLD, 19);
    private static final Font F2 = new Font("Arial", Font.BOLD, 16);
    private static final Font F3 = new Font("Arial", Font.BOLD, 12);
    private static final Color LBLUE = new Color(30,144,255);
    private static final Color DBLUE = new Color(71,112,139);

    private DefaultListModel<String> listModel;
    private JList<String> itemList;
    private JTextField searchField;
    private JComboBox<String> typeBox;        
    private JComboBox<String> colorBox;

    // JDBC setup
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=testDB;encrypt=true;trustServerCertificate=true";
    private static final String DB_USER = "admin";
    private static final String DB_PASS = "admin456";
    private ArrayList<String> vehicleData = new ArrayList<>();

    BrowseVehicleUI(){
        setTitle("Vehicle Rental System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 530);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);

        JPanel pan = new JPanel();
        pan.setBackground(new Color(196, 227, 244));
        pan.setBounds(0, 0, 900, 640);
        pan.setLayout(null);

        JPanel line1 = new JPanel();
        line1.setBackground(new Color(100, 149, 237)); 
        line1.setBounds(90, 0, 30, 640);

        JPanel line2 = new JPanel();
        line2.setBackground(new Color(100, 149, 237)); 
        line2.setBounds(150, 0, 30, 640);

        JPanel browsePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        browsePanel.setBounds(40,40,800,520);
        browsePanel.setOpaque(false);

        JLabel sLabel = new JLabel("Search:");
        sLabel.setPreferredSize(new Dimension(50,20));
        sLabel.setFont(F3);
        sLabel.setForeground(new Color (39, 58, 87));
        searchField = new JTextField();
        searchField.setFont(F3);
        searchField.setPreferredSize(new Dimension(195,25));

        JLabel tLabel = new JLabel("Type:");
        tLabel.setPreferredSize(new Dimension(50,20));
        tLabel.setFont(F3);
        tLabel.setForeground(new Color (39, 58, 87));
        typeBox = new JComboBox<>();
        typeBox.addItem("All");
        for (String type : fetchDistinctColumnValues("vehicleType")) {
            typeBox.addItem(type);
        }
        typeBox.setPreferredSize(new Dimension(195,25));
        typeBox.setBackground(Color.WHITE);

        JLabel cLabel = new JLabel("Color:");
        cLabel.setPreferredSize(new Dimension(50,20));
        cLabel.setFont(F3);
        cLabel.setForeground(new Color (39, 58, 87));
        colorBox = new JComboBox<>();
        colorBox.addItem("All");
        for (String color : fetchDistinctColumnValues("color")) {
        colorBox.addItem(color);
        }
        colorBox.setPreferredSize(new Dimension(195,25));
        colorBox.setBackground(Color.WHITE);

        JButton searchBtn = new JButton("Search");
        searchBtn.setPreferredSize(new Dimension(122,25));
        searchBtn.setForeground(new Color (39, 58, 87));

        JButton resetBtn = new JButton("Reset");
        resetBtn.setPreferredSize(new Dimension(122,25));
        resetBtn.setForeground(new Color (39, 58, 87));

        JLabel l1 = new JLabel("List of Available Vehicles: ");
        l1.setPreferredSize(new Dimension(500,30));
        l1.setFont(F2);
        l1.setForeground(new Color (39, 58, 87));

        listModel = new DefaultListModel<>();
        itemList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(itemList);
        scrollPane.setPreferredSize(new Dimension(700,300));

        JPanel btnGrp = new JPanel(new GridLayout(1,2,110,0));
        btnGrp.setOpaque(false);
        JButton backBtn = new JButton("Return");
        JButton selectBtn = new JButton("Select");
        btnGrp.add(backBtn);
        btnGrp.add(selectBtn);
        btnGrp.setPreferredSize(new Dimension(265,25));

        browsePanel.add(sLabel);
        browsePanel.add(searchField);
        browsePanel.add(tLabel);
        browsePanel.add(typeBox);        
        browsePanel.add(cLabel);
        browsePanel.add(colorBox);
        browsePanel.add(l1);
        browsePanel.add(resetBtn);
        browsePanel.add(searchBtn);
        browsePanel.add(scrollPane);
        browsePanel.add(btnGrp);

        loadVehicleDataFromDB();
        loadList(vehicleData.toArray(new String[0]));

        // Action Listeners
        searchBtn.addActionListener(e -> filterList());
        backBtn.addActionListener(e -> returnToLandingPage());
        selectBtn.addActionListener(e -> {
            try {
                String item = itemList.getSelectedValue();
                String[] parts = item.split(" - ");
                goToVehicleDetails(parts[0]);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "No Vehicle Selected");
            }
        });
        resetBtn.addActionListener(e -> {
            searchField.setText("");
            typeBox.setSelectedIndex(0);
            colorBox.setSelectedIndex(0);
            loadList(vehicleData.toArray(new String[0]));
        });

        pan.add(browsePanel);
        pan.add(line1);
        pan.add(line2);

        add(pan);
        setVisible(true);
    }

    private void returnToLandingPage() {
        dispose();
        new UserLandingPageUI();
    }

    private void goToVehicleDetails(String id) {
        dispose();
        new VehicleDetailsUI(id);
    }

    private void loadList(String[] items) {
        listModel.clear();
        for (String item : items) {
            listModel.addElement(item);
        }
    }

    private void filterList() {
        String keyword = searchField.getText().toLowerCase();
        String vehicleType = typeBox.getSelectedItem().toString();
        String vehicleColor = colorBox.getSelectedItem().toString();

        listModel.clear();
        for (String item : vehicleData) {
            boolean matchesKeyword = item.toLowerCase().contains(keyword);
            boolean matchesType = vehicleType.equals("All") || item.toLowerCase().contains(vehicleType.toLowerCase());
            boolean matchesColor = vehicleColor.equals("All") || item.toLowerCase().contains(vehicleColor.toLowerCase());

            if (matchesKeyword && matchesType && matchesColor) {
                listModel.addElement(item);
            }
        }
    }
    private ArrayList<String> fetchDistinctColumnValues(String columnName) {
        ArrayList<String> values = new ArrayList<>();
        String query = "SELECT DISTINCT " + columnName + " FROM Vehicles ORDER BY " + columnName;
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String val = rs.getString(columnName);
                if (val != null && !val.trim().isEmpty()) {
                    values.add(val);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading filter values: " + e.getMessage());
        }
        return values;
    }
    
    private void loadVehicleDataFromDB() {
        vehicleData.clear();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String query = "SELECT vehicleID,vehicleType, brand, model, color FROM Vehicles WHERE vehicleStatus = 'Available'";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String id = rs.getString("vehicleID");
                String type = rs.getString("vehicleType");
                String brand = rs.getString("brand");
                String model = rs.getString("model");
                String color = rs.getString("color");
                String display = id + " - " + type + " - " + brand + " " + model + " - " + color;
                vehicleData.add(display);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
        }
    }
}

class runner1 {
    public static void main(String[] args) {
        new BrowseVehicleUI();
    }
}