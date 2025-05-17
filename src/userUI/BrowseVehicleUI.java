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
import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

public class BrowseVehicleUI extends JFrame {
    
    private static final Font F1 = new Font("Arial", Font.BOLD, 19);
    private static final Font F2 = new Font("Arial", Font.BOLD, 18);
    private static final Font F3 = new Font("Arial", Font.BOLD, 12);
    private static final Font F4 = new Font("Arial", Font.BOLD, 14);
    private static final Color LBLUE = new Color(30,144,255);
    private static final Color DBLUE = new Color(71,112,139);
    private static final Color white = Color.WHITE;
    private static final Color shadowColor = new Color(143,143,143);
    private static final Color btnBGColor = new Color(92,142,175);
    private static final Color fldBGColor = new Color(240, 240, 240);
    
    //Border
    private static final Border lineBorder = BorderFactory.createLineBorder(Color.GRAY,1);
    private static final Border empBorder = BorderFactory.createEmptyBorder(2,2,2,2);
    private static final Border Border = new CompoundBorder(lineBorder,empBorder);
    private static final Border lineBorder2 = BorderFactory.createLineBorder(Color.LIGHT_GRAY,2);
    private static final Border empBorder4 = BorderFactory.createEmptyBorder(2,2,2,2);
    private static final Border Border1 = new CompoundBorder(lineBorder2,empBorder4);

    private DefaultListModel<String> listModel;
    private JList<String> itemList;
    private JTextField searchField;
    private JComboBox<String> typeBox;        
    private JComboBox<String> colorBox;

    // JDBC setup
    private Connection conn;
    private final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=vRentalSystemDB;encrypt=true;trustServerCertificate=true";
    private final String DB_USER = "admin";
    private final String DB_PASS = "admin456";
    private String accID;
    private ArrayList<String> vehicleData = new ArrayList<>();

    BrowseVehicleUI(String AId){
        this.accID = AId;
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
        line1.setBackground(new Color(132, 168, 230));
        line1.setBounds(90, 0, 30, 640);

        JPanel line2 = new JPanel();
        line2.setBackground(new Color(132, 168, 230));
        line2.setBounds(150, 0, 30, 640);

        JPanel browsePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        browsePanel.setBounds(40,25,800,520);
        browsePanel.setOpaque(false);

        JLabel sLabel = new JLabel("Search:");
        sLabel.setPreferredSize(new Dimension(60,20));
        sLabel.setFont(F4);
        sLabel.setForeground(white);
        
        JLabel sLS = new JLabel("Search:");
        sLS.setFont(F4);
        sLS.setForeground(shadowColor);
        sLS.setBounds(47,39,60,20);
        
        searchField = new JTextField();
        searchField.setFont(F3);
        searchField.setPreferredSize(new Dimension(190,25));
        searchField.setBorder(Border);
        searchField.setBackground(fldBGColor);

        //Eto ung nagiinitialize ng connection sa database
        connectToDB();
        
        JLabel tLabel = new JLabel("  Type:");
        tLabel.setPreferredSize(new Dimension(50,20));
        tLabel.setFont(F4);
        tLabel.setForeground(white);
        
        JLabel tLS = new JLabel("  Type:");
        tLS.setFont(F4);
        tLS.setForeground(shadowColor);
        tLS.setBounds(317,39,50,20);
        
        typeBox = new JComboBox<>();
        typeBox.addItem("All");
        for (String type : fetchDistinctColumnValues("VType")) {
            typeBox.addItem(type);
        }
        typeBox.setPreferredSize(new Dimension(195,25));
        typeBox.setBackground(fldBGColor);

        JLabel cLabel = new JLabel("  Color:");
        cLabel.setPreferredSize(new Dimension(50,20));
        cLabel.setFont(F4);
        cLabel.setForeground(white);
        
        JLabel cLS = new JLabel("  Color:");
        cLS.setFont(F4);
        cLS.setForeground(shadowColor);
        cLS.setBounds(582,39,50,20);
        
        colorBox = new JComboBox<>();
        colorBox.addItem("All");
        for (String color : fetchDistinctColumnValues("Color")) {
        colorBox.addItem(color);
        }
        colorBox.setPreferredSize(new Dimension(195,25));
        colorBox.setBackground(fldBGColor);

        JButton searchBtn = new JButton("Search");
        searchBtn.setPreferredSize(new Dimension(122,23));
        searchBtn.setForeground(white);
        searchBtn.setBackground(btnBGColor);
        searchBtn.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        

        JButton resetBtn = new JButton("Reset");
        resetBtn.setPreferredSize(new Dimension(122,25));
        resetBtn.setForeground(white);
        resetBtn.setBackground(btnBGColor);
        resetBtn.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

        JLabel l1 = new JLabel("List of Available Vehicles: ");
        l1.setPreferredSize(new Dimension(500,30));
        l1.setFont(F2);
        l1.setForeground(white);
        
        JLabel l2 = new JLabel("List of Available Vehicles: ");
        l2.setFont(F2);
        l2.setForeground(shadowColor);
        l2.setBounds(60,72,500,30);

        listModel = new DefaultListModel<>();
        itemList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(itemList);
        scrollPane.setPreferredSize(new Dimension(700,300));
        scrollPane.setBackground(fldBGColor);
        scrollPane.setBorder(Border1);

        JPanel btnGrp = new JPanel(new GridLayout(1,2,30,0));
        btnGrp.setBorder(BorderFactory.createEmptyBorder(5,0,0,0));
        btnGrp.setOpaque(false);
        JButton backBtn = new JButton("Return");
        backBtn.setFont(new Font("Arial",Font.BOLD,15));
        backBtn.setBackground(btnBGColor);
        backBtn.setForeground(white);
        backBtn.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        JButton selectBtn = new JButton("Select");
        selectBtn.setFont(new Font("Arial",Font.BOLD,15));
        selectBtn.setBackground(btnBGColor);
        selectBtn.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        selectBtn.setForeground(white);
        btnGrp.add(backBtn);
        btnGrp.add(selectBtn);
        btnGrp.setPreferredSize(new Dimension(300,40));
        
        

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
                goToVehicleDetails(parts[0], AId);
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
        pan.add(sLS);
        pan.add(tLS);
        pan.add(cLS);
        pan.add(l2);
        pan.add(line1);
        pan.add(line2);

        //eto ung nagcclose ng DB
 
        
        add(pan);
        setVisible(true);
    }

    private void returnToLandingPage() {
        dispose();
        new UserLandingPageUI(accID);
    }

    private void goToVehicleDetails(String vID,String AId ) {
        dispose();
        new VehicleDetailsUI(vID,AId);
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
    
    private void connectToDB(){
        try {
        conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASS);
        }
            catch(SQLException e){
            e.printStackTrace();
            }
    }
    
    private void closeConnection(){
        try
        {
        conn.close();
        }
            catch(SQLException e){
            e.printStackTrace();
            }
    }
    
    private ArrayList<String> fetchDistinctColumnValues(String columnName) {
        ArrayList<String> values = new ArrayList<>();
        String query = "SELECT DISTINCT " + columnName + " FROM VEHICLES ORDER BY " + columnName;
        try (PreparedStatement stmt = conn.prepareStatement(query);
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
        try {
            String query = "SELECT VehicleID,VType, Brand, Model, Color FROM VEHICLES WHERE VehicleStatus = 'Available'";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String id = rs.getString("VehicleID");
                String type = rs.getString("VType");
                String brand = rs.getString("Brand");
                String model = rs.getString("Model");
                String color = rs.getString("Color");
                String display = id + " - " + type + " - " + brand + " " + model + " - " + color;
                vehicleData.add(display);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
        }
    }
}
