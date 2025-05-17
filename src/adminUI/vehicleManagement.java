package adminUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.optionalusertools.DateVetoPolicy;
import java.awt.Font;
import java.awt.Color;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

public class vehicleManagement extends JFrame {

    private static final Font fontA = new Font("Arial", Font.BOLD, 18);
    private static final Font fontB = new Font("Arial", Font.BOLD, 15);
    private static final Font fontC = new Font("Arial", Font.BOLD, 17);
    private static final Color lblue = new Color(135, 206, 235);
    private static final Color dblue = new Color(132, 168, 230);
    private static final Color fontColor = Color.WHITE;
    private static final Color shadowColor = new Color(143,143,143);
    private static final Color btnBGColor = new Color(92,142,175);
    private static final Color fldBGColor = new Color(240, 240, 240);
    
    private static final Border outer = BorderFactory.createBevelBorder(BevelBorder.RAISED);
    private static final Border inner = BorderFactory.createBevelBorder(BevelBorder.RAISED);
    private static final Border lineBorder = BorderFactory.createLineBorder(Color.GRAY,2);
    private static final Border empBorder = BorderFactory.createEmptyBorder(2,2,2,2);
    private static final Border lineBorder1 = BorderFactory.createLineBorder(Color.GRAY,1);
    private static final Border btnBorder = BorderFactory.createCompoundBorder(outer,inner);
    private static final Border btnBorder1 = new CompoundBorder(lineBorder1,outer);
    private static final Border Border = new CompoundBorder(lineBorder,empBorder);
    private static final Border fldBorder = new CompoundBorder(lineBorder1,empBorder);
    
    private JPanel mainPnl;
    private JPanel addPnl, modifyPnl, removePnl, maintenancePnl;
    private JTable vehicleTab;

    // JDBC setup
    private Connection conn;
    private final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=vRentalSystemDB;encrypt=true;trustServerCertificate=true";
    private final String DB_USER = "admin";
    private final String DB_PASS = "admin456";
    private ArrayList<String> vehicleData = new ArrayList<>();
    private boolean dbUpdated  = false;
    private final NoPastDatesPolicy noPastDatesPolicy = new NoPastDatesPolicy();
    
    public vehicleManagement()
    {
        setTitle("Vehicle Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 660);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);

        mainPnl = new JPanel();
        mainPnl.setBackground(new Color(196, 227, 244));
        mainPnl.setBounds(0, 0, 900, 660);
        mainPnl.setLayout(null);

        JPanel line1 = new JPanel();
        line1.setBackground(dblue);
        line1.setBounds(90, 0, 30, 660);

        JPanel line2 = new JPanel();
        line2.setBackground(dblue);
        line2.setBounds(150, 0, 30, 660);

        JLabel title = new JLabel("VEHICLE MANAGEMENT", SwingConstants.CENTER);
        title.setForeground(fontColor);
        title.setFont(new Font ("Arial", Font.BOLD, 40));
        title.setBounds(0, 60, 900, 45);
        
        JLabel titleLS = new JLabel("VEHICLE MANAGEMENT", SwingConstants.CENTER);
        titleLS.setForeground(shadowColor);
        titleLS.setFont(new Font ("Arial", Font.BOLD, 40));
        titleLS.setBounds(3, 63, 900, 45);

        JButton addVehicle = new JButton("Add Vehicle");
        addVehicle.setBounds(80, 140, 740, 70);
        addVehicle.setFont(new Font("Arial", Font.BOLD, 21));
        addVehicle.setForeground(fontColor);
        addVehicle.setBackground(btnBGColor);
        addVehicle.setBorder(btnBorder);

        JButton modifyVehicle = new JButton("Modify Vehicle");
        modifyVehicle.setBounds(80, 230, 740, 70);
        modifyVehicle.setFont(new Font("Arial", Font.BOLD, 21));
        modifyVehicle.setForeground(fontColor);
        modifyVehicle.setBackground(btnBGColor);
        modifyVehicle.setBorder(btnBorder);

        JButton removeVehicle = new JButton("Remove Vehicle");
        removeVehicle.setBounds(80, 320, 740, 70);
        removeVehicle.setFont(new Font("Arial", Font.BOLD, 21));
        removeVehicle.setForeground(fontColor);
        removeVehicle.setBackground(btnBGColor);
        removeVehicle.setBorder(btnBorder);

        JButton maintenance = new JButton("Schedule Maintenance");
        maintenance.setBounds(80, 410, 740, 70);
        maintenance.setFont(new Font("Arial", Font.BOLD, 21));
        maintenance.setForeground(fontColor);
        maintenance.setBackground(btnBGColor);
        maintenance.setBorder(btnBorder);

        JButton back = new JButton("Back");
        back.setBounds(80, 500, 740, 70);
        back.setFont(new Font("Arial", Font.BOLD, 21));
        back.setForeground(fontColor);
        back.setBackground(btnBGColor);
        back.setBorder(btnBorder);

        mainPnl.add(title);
        mainPnl.add(titleLS);
        mainPnl.add(addVehicle);
        mainPnl.add(modifyVehicle);
        mainPnl.add(removeVehicle);
        mainPnl.add(maintenance);
        mainPnl.add(back);
        mainPnl.add(line1);
        mainPnl.add(line2);

        // panel for add / edit / remove / maintenance
        addPnl = createPanel("Add Vehicle");
        modifyPnl = createPanel("Modify Vehicle");
        removePnl = createPanel("Remove Vehicle");
        maintenancePnl = createPanel("Maintenance");

        // close application
        back.addActionListener(e -> {dispose(); new vehicleRental();});

        // button functions to switch to their respective panel
        addVehicle.addActionListener(e -> switchPnl(addPnl));
        modifyVehicle.addActionListener(e -> switchPnl(modifyPnl));
        removeVehicle.addActionListener(e -> switchPnl(removePnl));
        maintenance.addActionListener(e -> switchPnl(maintenancePnl));

        add(mainPnl);
        setVisible(true);
    }
    
    private void connectToDB() {
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } 
        catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Database connection error: " + e.getMessage());
        }
    }
    
    private void closeConnection(){
        try {
        conn.close();
        }
        catch(SQLException e){
        e.printStackTrace();
        }
    }

    private JPanel createPanel(String title) {
        
        JPanel panel = new JPanel();
        panel.setBackground(new Color(196, 227, 244));
        panel.setBounds(0, 0, 900, 640);
        panel.setLayout(null);

        JLabel titlelbl = new JLabel(title.toUpperCase(), SwingConstants.CENTER);
        titlelbl.setForeground(fontColor);
        titlelbl.setFont(new Font("Arial", Font.BOLD, 40));
        titlelbl.setBounds(0, 60, 890, 30);
        panel.add(titlelbl);
        
        JLabel titlels = new JLabel(title.toUpperCase(), SwingConstants.CENTER);
        titlels.setForeground(shadowColor);
        titlels.setFont(new Font ("Arial", Font.BOLD, 40));
        titlels.setBounds(3, 63, 890, 30);
        panel.add(titlels);

        JPanel line1 = new JPanel();
        line1.setBackground(dblue);
        line1.setBounds(90, 0, 30, 640);

        JPanel line2 = new JPanel();
        line2.setBackground(dblue);
        line2.setBounds(150, 0, 30, 640);

        // Add Vehicle Panel
        if (title.equals("Add Vehicle")) {
            
            JLabel vehicleType = new JLabel("Vehicle Type:");
            vehicleType.setBounds(145, 130, 200, 20);
            vehicleType.setForeground(fontColor);
            vehicleType.setFont(fontA);
            panel.add(vehicleType);
            
            JLabel vehicleTypeLS = new JLabel("Vehicle Type:");
            vehicleTypeLS.setBounds(147, 132, 200, 20);
            vehicleTypeLS.setForeground(shadowColor);
            vehicleTypeLS.setFont(fontA);
            panel.add(vehicleTypeLS);
            
            String[] vehicles = {
                "Motorcycle",
                "SUV",
                "Sedan",
                "Coupe",
                "PickUp",
                "Van",
                "Minivan",
                "Convertible" ,
                "Hatchback",
                "Crossover",
                "Electric Vehicle"};
                //type of vehicles
            JComboBox <String> vehiclesBox = new JComboBox<>(vehicles);
            vehiclesBox.setBounds(145, 155, 600, 40);
            vehiclesBox.setBackground(fldBGColor);
            vehiclesBox.setFont(fontB);
            panel.add(vehiclesBox);

            JLabel brand = new JLabel("Brand:");
            brand.setBounds(110, 220, 200, 20);
            brand.setForeground(fontColor);
            brand.setFont(fontA);
            panel.add(brand);
            
            JLabel brandLS = new JLabel("Brand:");
            brandLS.setBounds(112, 222, 200, 20);
            brandLS.setForeground(shadowColor);
            brandLS.setFont(fontA);
            panel.add(brandLS);
            
            JTextField brandFld = new JTextField();
            brandFld.setBounds(110, 245, 280, 35);
            brandFld.setBackground(fldBGColor);
            brandFld.setBorder(fldBorder);
            brandFld.setFont(fontB);
            panel.add(brandFld);

            JLabel model = new JLabel("Model(Year):");
            model.setBounds(110, 295, 200, 20);
            model.setForeground(fontColor);
            model.setFont(fontA);
            panel.add(model);
            
            JLabel modelLS = new JLabel("Model(Year):");
            modelLS.setBounds(112, 297, 200, 20);
            modelLS.setForeground(shadowColor);
            modelLS.setFont(fontA);
            panel.add(modelLS);

            JTextField modelFld = new JTextField();
            modelFld.setBounds(110, 320, 280, 35);
            modelFld.setBackground(fldBGColor);
            modelFld.setBorder(fldBorder);
            modelFld.setFont(fontB);
            panel.add(modelFld);
            
            JLabel color = new JLabel("Color:");
            color.setBounds(110, 370, 200, 20);
            color.setForeground(fontColor);
            color.setFont(fontA);
            panel.add(color);
            
            JLabel colorLS = new JLabel("Color:");
            colorLS.setBounds(112, 372, 200, 20);
            colorLS.setForeground(shadowColor);
            colorLS.setFont(fontA);
            panel.add(colorLS);
            
            JTextField colorFld = new JTextField();
            colorFld.setBounds(110, 395, 280, 35);
            colorFld.setBackground(fldBGColor);
            colorFld.setBorder(fldBorder);
            colorFld.setFont(fontB);
            panel.add(colorFld);
            
            JLabel license = new JLabel("License Plate:");
            license.setBounds(500, 220, 200, 20);
            license.setForeground(fontColor);
            license.setFont(fontA);
            panel.add(license);
            
            JLabel licenseLS = new JLabel("License Plate:");
            licenseLS.setBounds(502, 222, 200, 20);
            licenseLS.setForeground(shadowColor);
            licenseLS.setFont(fontA);
            panel.add(licenseLS);
            
            JTextField licenseFld = new JTextField();
            licenseFld.setBounds(500, 245, 280, 35);
            licenseFld.setBackground(fldBGColor);
            licenseFld.setBorder(fldBorder);
            licenseFld.setFont(fontB);
            panel.add(licenseFld);
            
            JLabel price = new JLabel("Rent Price:");
            price.setBounds(500, 295, 210, 20);
            price.setForeground(fontColor);
            price.setFont(fontA);
            panel.add(price);
            
            JLabel priceLS = new JLabel("Rent Price:");
            priceLS.setBounds(502, 297, 210, 20);
            priceLS.setForeground(shadowColor);
            priceLS.setFont(fontA);
            panel.add(priceLS);
            
            JTextField priceFld = new JTextField();
            priceFld.setText(" ₱ ");
            priceFld.setBounds(500, 320, 280, 35);
            priceFld.setBackground(fldBGColor);
            priceFld.setBorder(fldBorder);
            priceFld.setFont(fontB);
            panel.add(priceFld);
            
            JLabel status = new JLabel("Status:");
            status.setBounds(500, 370, 200, 20);
            status.setForeground(fontColor);
            status.setFont(fontA);
            panel.add(status);
            
            JLabel statusLS = new JLabel("Status:");
            statusLS.setBounds(502, 372, 200, 20);
            statusLS.setForeground(shadowColor);
            statusLS.setFont(fontA);
            panel.add(status);
            
            String[] statuses = {"Available"};
            JComboBox <String> statusBox = new JComboBox<>(statuses);
            statusBox.setBounds(500, 395, 280, 35);
            statusBox.setBackground(fldBGColor);
            statusBox.setFont(fontB);
            panel.add(statusBox);

            JButton add = new JButton("ADD");
            add.setBounds(260, 490, 170, 40);
            add.setForeground(fontColor);
            add.setBackground(btnBGColor);
            add.setBorder(btnBorder1);
            add.setFont(fontC);
            panel.add(add);

            JButton back = new JButton("BACK");
            back.setBounds(460, 490, 170, 40);
            back.setForeground(fontColor);
            back.setBackground(btnBGColor);
            back.setBorder(btnBorder1);
            back.setFont(fontC);
            panel.add(back);
            
            add.addActionListener(e -> addNewVehicle
                (vehiclesBox, brandFld, modelFld, colorFld, licenseFld, priceFld, statusBox, panel));

            back.addActionListener(e -> {
                    if(dbUpdated){
                    JOptionPane.showMessageDialog(panel, "Database updated, changes we're made.");
                    dispose();
                    new vehicleManagement();
                    dbUpdated = false;
                }
                    else{
                        switchPnl(mainPnl);
                    }
            });
        }

        else if (title.equals("Modify Vehicle")) {
            
            JLabel vehicleID = new JLabel("Vehicle ID:");
            vehicleID.setBounds(110, 130, 200, 20);
            vehicleID.setForeground(fontColor);
            vehicleID.setFont(fontA);
            panel.add(vehicleID);
            
            JLabel vehicleIDLS = new JLabel("Vehicle ID:");
            vehicleIDLS.setBounds(112, 132, 200, 20);
            vehicleIDLS.setForeground(shadowColor);
            vehicleIDLS.setFont(fontA);
            panel.add(vehicleIDLS);

            JTextField vehicleIDFld = new JTextField();
            vehicleIDFld.setBounds(110, 155, 280, 35);
            vehicleIDFld.setBackground(fldBGColor);
            vehicleIDFld.setBorder(fldBorder);
            vehicleIDFld.setFont(fontB);
            panel.add(vehicleIDFld);
            
            JLabel vehicleType = new JLabel("Vehicle Type:");
            vehicleType.setBounds(110, 200, 200, 20);
            vehicleType.setForeground(fontColor);
            vehicleType.setFont(fontA);
            panel.add(vehicleType);
            
            JLabel vehicleTypeLS = new JLabel("Vehicle Type:");
            vehicleTypeLS.setBounds(112, 202, 200, 20);
            vehicleTypeLS.setForeground(shadowColor);
            vehicleTypeLS.setFont(fontA);
            panel.add(vehicleTypeLS);
            
            String[] vehicles = {                
                "Motorcycle",
                "SUV",
                "Sedan",
                "Coupe",
                "PickUp",
                "Van",
                "Minivan",
                "Convertible" ,
                "Hatchback",
                "Crossover",
                "Electric Vehicle"};
                 //type of vehicles
            JComboBox <String> vehiclesBox = new JComboBox<>(vehicles);
            vehiclesBox.setBounds(110, 225, 200, 35);
            vehiclesBox.setBackground(fldBGColor);
            vehiclesBox.setFont(fontB);
            panel.add(vehiclesBox);

            JLabel brand = new JLabel("Brand:");
            brand.setBounds(110, 270, 200, 20);
            brand.setForeground(fontColor);
            brand.setFont(fontA);
            panel.add(brand);
            
            JLabel brandLS = new JLabel("Brand:");
            brandLS.setBounds(112, 272, 200, 20);
            brandLS.setForeground(shadowColor);
            brandLS.setFont(fontA);
            panel.add(brandLS);
            
            JTextField brandFld = new JTextField();
            brandFld.setBounds(110, 295, 280, 35);
            brandFld.setBackground(fldBGColor);
            brandFld.setBorder(fldBorder);
            brandFld.setFont(fontB);
            panel.add(brandFld);

            JLabel model = new JLabel("Model(Year):");
            model.setBounds(110, 340, 200, 20);
            model.setForeground(fontColor);
            model.setFont(fontA);
            panel.add(model);
            
            JLabel modelLS = new JLabel("Model(Year):");
            modelLS.setBounds(112, 342, 200, 20);
            modelLS.setForeground(shadowColor);
            modelLS.setFont(fontA);
            panel.add(modelLS);

            JTextField modelFld = new JTextField();
            modelFld.setBounds(110, 365, 280, 35);
            modelFld.setBackground(fldBGColor);
            modelFld.setBorder(fldBorder);
            modelFld.setFont(fontB);
            panel.add(modelFld);
            
            JLabel color = new JLabel("Color:");
            color.setBounds(110, 410, 200, 20);
            color.setForeground(fontColor);
            color.setFont(fontA);
            panel.add(color);
            
            JLabel colorLS = new JLabel("Color:");
            colorLS.setBounds(112, 412, 200, 20);
            colorLS.setForeground(shadowColor);
            colorLS.setFont(fontA);
            panel.add(colorLS);
            
            JTextField colorFld = new JTextField();
            colorFld.setBounds(110, 435, 280, 35);
            colorFld.setBackground(fldBGColor);
            colorFld.setBorder(fldBorder);
            colorFld.setFont(fontB);
            panel.add(colorFld);

            JLabel license = new JLabel("License Plate:");
            license.setBounds(500, 270, 200, 20);
            license.setForeground(fontColor);
            license.setFont(fontA);
            panel.add(license);
            
            JLabel licenseLS = new JLabel("License Plate:");
            licenseLS.setBounds(502, 272, 200, 20);
            licenseLS.setForeground(shadowColor);
            licenseLS.setFont(fontA);
            panel.add(licenseLS);
            
            JTextField licenseFld = new JTextField();
            licenseFld.setBounds(500, 295, 280, 35);
            licenseFld.setBackground(fldBGColor);
            licenseFld.setBorder(fldBorder);
            licenseFld.setFont(fontB);
            panel.add(licenseFld);
            
            JLabel price = new JLabel("Rent Price:");
            price.setBounds(500, 340, 200, 20);
            price.setForeground(fontColor);
            price.setFont(fontA);
            panel.add(price);
            
            JLabel priceLS = new JLabel("Rent Price:");
            priceLS.setBounds(502, 342, 200, 20);
            priceLS.setForeground(shadowColor);
            priceLS.setFont(fontA);
            panel.add(priceLS);
            
            JTextField priceFld = new JTextField();
            priceFld.setText(" ₱ ");
            priceFld.setBounds(500, 365, 280, 35);
            priceFld.setBackground(fldBGColor);
            priceFld.setBorder(fldBorder);
            priceFld.setFont(fontB);
            panel.add(priceFld);
            
            JButton updateBtn = new JButton("ADD");
            updateBtn.setBounds(260, 515, 170, 40);
            updateBtn.setForeground(fontColor);
            updateBtn.setBackground(btnBGColor);
            updateBtn.setBorder(btnBorder1);
            updateBtn.setFont(fontC);
            panel.add(updateBtn);

            JButton back = new JButton("BACK");
            back.setBounds(460, 515, 170, 40);
            back.setForeground(fontColor);
            back.setBackground(btnBGColor);
            back.setBorder(btnBorder1);
            back.setFont(fontC);
            panel.add(back);
            
            
            updateBtn.addActionListener(e -> {
                modifyVehicle(vehicleIDFld.getText().trim(),vehiclesBox,brandFld,modelFld,
                                                            colorFld,licenseFld,priceFld,panel);
            });
            
            back.addActionListener(e -> {
                if(dbUpdated){
                    JOptionPane.showMessageDialog(panel, "Database updated, changes we're made.");
                    dispose();
                    new vehicleManagement();
                    dbUpdated = false;
                }
                    else{
                        switchPnl(mainPnl);
                    }
            });
        }

        // Remove Vehicle Panel
        else if (title.equals("Remove Vehicle")) {
            
            JLabel search = new JLabel("Search:");
            search.setBounds(70, 140, 60, 25);
            search.setForeground(fontColor);
            search.setFont(new Font("Arial", Font.BOLD, 14));
            panel.add(search);
            
            JLabel searchLS = new JLabel("Search:");
            searchLS.setBounds(72, 142, 60, 25);
            searchLS.setForeground(shadowColor);
            searchLS.setFont(new Font("Arial", Font.BOLD, 14));
            panel.add(searchLS);

            JTextField searchFld = new JTextField();
            searchFld.setBounds(135, 140, 200, 25);
            searchFld.setBackground(fldBGColor);
            searchFld.setBorder(fldBorder);
            panel.add(searchFld);

            JLabel vehicleType = new JLabel("Type:");
            vehicleType.setBounds(355, 140, 40, 25);
            vehicleType.setForeground(fontColor);
            vehicleType.setFont(new Font("Arial", Font.BOLD, 14));
            panel.add(vehicleType);
            
            JLabel vehicleTypeLS = new JLabel("Type:");
            vehicleTypeLS.setBounds(357, 142, 40, 25);
            vehicleTypeLS.setForeground(shadowColor);
            vehicleTypeLS.setFont(new Font("Arial", Font.BOLD, 14));
            panel.add(vehicleTypeLS);

            JComboBox <String> vehicleTypeBox = new JComboBox<>(new String[]{"All"});
            for (String type : fetchDistinctColumnValues("VType")) {
                vehicleTypeBox.addItem(type);
            }
            vehicleTypeBox.setBounds(405, 140, 150, 25);
            vehicleTypeBox.setBackground(fldBGColor);
            panel.add(vehicleTypeBox);

            JButton searchBtn = new JButton("Search");
            searchBtn.setBounds(610, 140, 100, 25);
            searchBtn.setForeground(fontColor);
            searchBtn.setBackground(btnBGColor);
            searchBtn.setBorder(btnBorder1);
            panel.add(searchBtn);

            JButton resetBtn = new JButton("Reset");
            resetBtn.setBounds(720, 140, 100, 25);
            resetBtn.setForeground(fontColor);
            resetBtn.setBackground(btnBGColor);
            resetBtn.setBorder(btnBorder1);
            panel.add(resetBtn);
    
            String[] attributes = {"Vehicle ID","Vehicle Description", "Vehicle Type", "Status"};
            Object[][] data = fetchVehicleData();
            
            DefaultTableModel vehicleModel = new DefaultTableModel(attributes, 0);
            JTable vehicleTab = new JTable(vehicleModel);
            vehicleTab.setRowHeight(25);
            vehicleTab.setEnabled(false);
    
            JScrollPane scroll = new JScrollPane(vehicleTab);
            scroll.setBounds(70, 190, 750, 250);
            scroll.setBackground(fldBGColor);
            scroll.setBorder(Border);
            panel.add(scroll);
        
            JLabel vehicleLbl = new JLabel("Enter Vehicle ID to Remove:");
            vehicleLbl.setBounds(70, 460, 200, 20);
            vehicleLbl.setForeground(fontColor);
            vehicleLbl.setFont(new Font("Arial", Font.BOLD, 14));
            panel.add(vehicleLbl);
            
            JLabel vehicleLS = new JLabel("Enter Vehicle ID to Remove:");
            vehicleLS.setBounds(72, 462, 200, 20);
            vehicleLS.setForeground(shadowColor);
            vehicleLS.setFont(new Font("Arial", Font.BOLD, 14));
            panel.add(vehicleLS);
        
            JTextField vehicleFld = new JTextField();
            vehicleFld.setBounds(70, 485, 280, 30);
            vehicleFld.setBackground(fldBGColor);
            vehicleFld.setBorder(fldBorder);
            panel.add(vehicleFld);

            JButton remove = new JButton("REMOVE");
            remove.setBounds (580, 520, 130, 40);
            remove.setForeground(fontColor);
            remove.setBackground(btnBGColor);
            remove.setBorder(btnBorder1);
            panel.add(remove);
    
            JButton back = new JButton("BACK");
            back.setBounds(720, 520, 100, 40);
            back.setForeground(fontColor);
            back.setBackground(btnBGColor);
            back.setBorder(btnBorder1);
            panel.add(back);
            
            removeVehicleFltr(data, vehicleModel, "", "All");

            searchBtn.addActionListener(e -> {
                String keyword = searchFld.getText().toLowerCase();
                String type = vehicleTypeBox.getSelectedItem().toString();
                removeVehicleFltr(data, vehicleModel, keyword, type);
            });

            resetBtn.addActionListener(e -> {
                searchFld.setText("");
                vehicleTypeBox.setSelectedIndex(0);
                removeVehicleFltr(data, vehicleModel, "", "All");
            });
    
            remove.addActionListener(e -> {
                String vehicleID = vehicleFld.getText().trim();
                deleteVehicle(vehicleID, panel, vehicleTab);
            });
            
            back.addActionListener(e -> {
                if(dbUpdated){
                    JOptionPane.showMessageDialog(panel, "Database updated, changes we're made.");
                    dispose();
                    new vehicleManagement();
                    dbUpdated = false;
                }
                    else{
                        switchPnl(mainPnl);
                    }
            });
    }

        // Scheduling Panel
        else if (title.equals("Maintenance")) {

            JLabel availVehicle = new JLabel("Available Vehicles:");
            availVehicle.setBounds(70, 140, 230, 20);
            availVehicle.setForeground(fontColor);
            availVehicle.setFont(new Font ("Arial", Font.BOLD, 20));
            panel.add(availVehicle);
            
            JLabel availVehicleLS = new JLabel("Available Vehicles:");
            availVehicleLS.setBounds(72, 142, 230, 20);
            availVehicleLS.setForeground(shadowColor);
            availVehicleLS.setFont(new Font ("Arial", Font.BOLD, 20));
            panel.add(availVehicleLS);

            String[] attributes = {"Vehicle ID","Vehicle Description", "Vehicle Type", "Status"};
            Object[][] data = maintenanceData();

            vehicleTab = new JTable(data, attributes);
            vehicleTab.setRowHeight(25);
            vehicleTab.setEnabled(false);

            JScrollPane scroll = new JScrollPane(vehicleTab);
            scroll.setBounds(70, 175, 750, 160);
            scroll.setBorder(Border);
            panel.add(scroll);
            
            JLabel vehicleIdLbl = new JLabel("Enter Vehicle ID:");
            vehicleIdLbl.setBounds(115, 355, 280, 20);
            vehicleIdLbl.setForeground(fontColor);
            vehicleIdLbl.setFont(new Font("Arial", Font.BOLD, 15));
            panel.add(vehicleIdLbl);
            
            JLabel vehicleIdLS = new JLabel("Enter Vehicle ID:");
            vehicleIdLS.setBounds(117, 357, 280, 20);
            vehicleIdLS.setForeground(shadowColor);
            vehicleIdLS.setFont(new Font("Arial", Font.BOLD, 15));
            panel.add(vehicleIdLS);

            JTextField vehicleIdFld = new JTextField();
            vehicleIdFld.setBounds(115, 380, 280, 35);
            vehicleIdFld.setBorder(fldBorder);
            panel.add(vehicleIdFld);

            JLabel startDate = new JLabel("Maintenance Start Date:");
            startDate.setBounds(490, 355, 280, 20);
            startDate.setForeground(fontColor);
            startDate.setFont(new Font("Arial", Font.BOLD, 15));
            panel.add(startDate);
            
            JLabel startDateLS = new JLabel("Maintenance Start Date:");
            startDateLS.setBounds(492, 357, 280, 20);
            startDateLS.setForeground(shadowColor);
            startDateLS.setFont(new Font("Arial", Font.BOLD, 15));
            panel.add(startDateLS);

            DatePickerSettings start = new DatePickerSettings();
            DatePicker pickStart = new DatePicker(start);
            pickStart.getSettings().setVetoPolicy(noPastDatesPolicy);
            pickStart.setBounds(490, 380, 280, 35);
            pickStart.getComponentToggleCalendarButton().setText("Select");
            pickStart.getComponentDateTextField().setEditable(false); 
            panel.add(pickStart);

            JLabel endDate = new JLabel("Maintenance End Date:");
            endDate.setBounds(490, 425, 280, 20);
            endDate.setForeground(fontColor);
            endDate.setFont(new Font("Arial", Font.BOLD, 15));
            panel.add(endDate);
            
            JLabel endDateLS = new JLabel("Maintenance End Date:");
            endDateLS.setBounds(492, 427, 280, 20);
            endDateLS.setForeground(shadowColor);
            endDateLS.setFont(new Font("Arial", Font.BOLD, 15));
            panel.add(endDateLS);

            DatePickerSettings end = new DatePickerSettings();
            DatePicker pickEnd = new DatePicker(end);
            pickEnd.getSettings().setVetoPolicy(noPastDatesPolicy);
            pickEnd.setBounds(490, 450, 280, 35);
            pickEnd.getComponentToggleCalendarButton().setText("Select");
            pickEnd.getComponentDateTextField().setEditable(false); 
            panel.add(pickEnd);

            JLabel description = new JLabel("Description:");
            description.setBounds(115, 425, 280, 20);
            description.setForeground(fontColor);
            description.setFont(new Font("Arial", Font.BOLD, 15));
            panel.add(description);
            
            JLabel descriptionLS = new JLabel("Description:");
            descriptionLS.setBounds(117, 427, 280, 20);
            descriptionLS.setForeground(shadowColor);
            descriptionLS.setFont(new Font("Arial", Font.BOLD, 15));
            panel.add(descriptionLS);

            JTextField descFld = new JTextField();
            descFld.setBounds(115, 450, 280, 35);
            descFld.setBorder(fldBorder);
            panel.add(descFld);

            JButton apply = new JButton("APPLY");
            apply.setBounds (580, 520, 130, 40);
            apply.setForeground(fontColor);
            apply.setBackground(btnBGColor);
            apply.setBorder(btnBorder1);
            panel.add(apply);
    
            JButton back = new JButton("BACK");
            back.setBounds(720, 520, 100, 40);
            back.setForeground(fontColor);
            back.setBackground(btnBGColor);
            back.setBorder(btnBorder1);
            panel.add(back);
            
            apply.addActionListener(e -> fetchAvailableVehicle(vehicleIdFld, pickStart, pickEnd, descFld));

            back.addActionListener(e -> {
                if(dbUpdated){
                    JOptionPane.showMessageDialog(panel, "Database updated, changes we're made.");
                    dispose();
                    new vehicleManagement();
                    dbUpdated = false;
                }
                    else{
                        switchPnl(mainPnl);
                    }
            });
    }
        panel.add(line1);
        panel.add(line2);
        return panel;
    }
    
        private void addNewVehicle(JComboBox <String> vehiclesBox, JTextField brandFld, JTextField modelFld,
                                   JTextField colorFld, JTextField licenseFld, JTextField priceFld,
                                   JComboBox <String> statusBox, JPanel vpnl) {

            String vehicleType = vehiclesBox.getItemAt(vehiclesBox.getSelectedIndex());
            String brand = brandFld.getText().trim();
            String model = modelFld.getText().trim();
            String color = colorFld.getText().trim();
            String licensePlate = licenseFld.getText().trim();
            String rentPriceText = priceFld.getText().replace("₱", "").trim();
            String status = statusBox.getItemAt(statusBox.getSelectedIndex());

            if (brand.isEmpty() || model.isEmpty() || color.isEmpty() || licensePlate.isEmpty() || rentPriceText.isEmpty()) {
                JOptionPane.showMessageDialog(vpnl, "Please fill in all fields.");
                return;
            }

                try {
                    double rentPrice = Double.parseDouble(rentPriceText);
                    insertVehicleDB(vehicleType, brand, model, color, licensePlate, rentPrice, status, vpnl);
                } catch (NumberFormatException no) {
            JOptionPane.showMessageDialog(vpnl, "Invalid rent price. Please enter a numeric value.");
            }
    }

    private void insertVehicleDB(String vType, String brand, String model, String color,
                                 String license, double rentPrice, String status, JPanel panel) {
    
        try {
            connectToDB();
            String queryID = "SELECT * FROM VEHICLES WHERE CAST(SUBSTRING(VehicleID, 2, 10) AS INT) = (SELECT MAX(CAST(SUBSTRING(VehicleID, 2, 10) AS INT)) FROM VEHICLES)";
            int count = 0;
            
        try (PreparedStatement id = conn.prepareStatement(queryID);
             ResultSet rs = id.executeQuery()) {
            
            if (rs.next()) {
                String lastID = rs.getString("VehicleID"); 
                lastID = lastID.substring(1);
                count = Integer.parseInt(lastID);
            }
        }

        String vehicleID = "V" + String.format("%03d", count + 1);
        String insert = "INSERT INTO VEHICLES (VehicleID, VType, Brand, Model, Color, LicensePlate, RentPrice, VehicleStatus)" +
                                               "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(insert)) {
            stmt.setString(1, vehicleID);
            stmt.setString(2, vType);
            stmt.setString(3, brand);
            stmt.setString(4, model);
            stmt.setString(5, color);
            stmt.setString(6, license);
            stmt.setDouble(7, rentPrice);
            stmt.setString(8, status);

            int rowsInserted = stmt.executeUpdate();
            
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(panel, "Vehicle added successfully.");
                    dbUpdated = true;
                    
                } 
                else {
                    JOptionPane.showMessageDialog(panel, "Failed to add vehicle.");
                }
            }conn.close();
        } 
            catch (SQLException ex) {
                 ex.printStackTrace();
                 JOptionPane.showMessageDialog(panel, "Database error: " + ex.getMessage());
            }
    }

    private void modifyVehicle(String vehicleID, JComboBox <String> vehiclesBox, JTextField brandFld, JTextField modelFld,
                                 JTextField colorFld, JTextField licenseFld, JTextField priceFld, JPanel pnl){
        
        if (vehicleID == null || vehicleID.isEmpty()) {
            JOptionPane.showMessageDialog(pnl, "Vehicle ID is missing.");
            return;
        }

        String vType = vehiclesBox.getSelectedItem().toString();
        String brand = brandFld.getText().trim();
        String model = modelFld.getText().trim();
        String color = colorFld.getText().trim();
        String license = licenseFld.getText().trim();
        String priceText = priceFld.getText().replace("₱", "").trim();

        if (brand.isEmpty() || model.isEmpty() || color.isEmpty() || license.isEmpty() || priceText.isEmpty()) {
            JOptionPane.showMessageDialog(pnl, "Please fill in all fields.");
            return;
        }

            try {
                double price = Double.parseDouble(priceText);
                updateVehicleDB(vehicleID, vType, brand, model, color, license, price, pnl);
            } 
                catch (NumberFormatException no) {
                       JOptionPane.showMessageDialog(pnl, "Invalid rent price.");
                }
    }
    
    private void updateVehicleDB(String vehicleID, String vType, String brand, String model, String color,
                                 String license, double rentPrice, JPanel panel) {
        connectToDB();
        String query = "UPDATE VEHICLES SET VType = ?, Brand = ?, Model = ?, Color = ?, LicensePlate = ?, RentPrice = ? WHERE VehicleID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, vType);
            stmt.setString(2, brand);
            stmt.setString(3, model);
            stmt.setString(4, color);
            stmt.setString(5, license);
            stmt.setDouble(6, rentPrice);
            stmt.setString(8, vehicleID);

        int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(panel, "Vehicle updated successfully.");
                dbUpdated = true;
            } 
              else {
                JOptionPane.showMessageDialog(panel, "Vehicle update failed or vehicle not found.");
            }
            conn.close();
        } 
            catch (SQLException e) {
                JOptionPane.showMessageDialog(panel, "Error updating vehicle: " + e.getMessage());
            }
        }

    private void removeVehicleFltr(Object[][] data, DefaultTableModel vModel, String keyword, String vType) {
        vModel.setRowCount(0); 

        for (Object[] row : data) {
            String id = row[0].toString().toLowerCase();
            String vehicleType = row[2].toString();
            String status = row[3].toString();
        
        boolean match = id.contains(keyword) || vehicleType.toLowerCase().contains(keyword) || status.toLowerCase().contains(keyword);
        boolean matchType = vType.equals("All") || vehicleType.equalsIgnoreCase(vType);
        if (match && matchType) {
            vModel.addRow(row);
         }
        }
    }

    private void deleteVehicle(String vehicleID, JPanel pnl, JTable deleteTab) {
    connectToDB();
        if (vehicleID == null || vehicleID.trim().isEmpty()) {
            JOptionPane.showMessageDialog(pnl, "Please enter a Vehicle ID.");
            return;
        }

        String checkRental = "SELECT VehicleID, VType, VehicleStatus FROM VEHICLES WHERE VehicleID = ?";
        String deleteVehicle = "DELETE FROM RENTAL_DETAILS WHERE VehicleID = ? "
                + "DELETE FROM MAINTENANCE WHERE VehicleID = ? "
                + "DELETE FROM VEHICLES WHERE VehicleID = ? ";

        try (PreparedStatement check = conn.prepareStatement(checkRental)){
            check.setString(1, vehicleID);
            ResultSet rs = check.executeQuery();

            if (rs.next()) {
                String status = rs.getString("VehicleStatus");
                
            if ("rented".equalsIgnoreCase(status)) {
                JOptionPane.showMessageDialog(pnl, "This vehicle is currently rented. Cannot remove.");
                return;
            }
                else if("pending approval".equalsIgnoreCase(status)){
                    JOptionPane.showMessageDialog(pnl, "This vehicle is currently in a transaction process. Cannot remove.");
                    return;
                }

                try (PreparedStatement delete = conn.prepareStatement(deleteVehicle)) {
                    delete.setString(1, vehicleID);
                    delete.setString(2, vehicleID);
                    delete.setString(3, vehicleID);
                    int rows = delete.executeUpdate();

                if (rows > 0) {
                    JOptionPane.showMessageDialog(pnl, "Vehicle removed successfully.");
                    dbUpdated = true;
                    Object[][] updatedData = fetchVehicleData();
                    DefaultTableModel model = (DefaultTableModel) deleteTab.getModel();
                    removeVehicleFltr(updatedData, model, "", "All");

                } 
                    else {
                        JOptionPane.showMessageDialog(pnl, "Vehicle not found or could not be removed.");
                    }
              }
            } 
                else {
                    JOptionPane.showMessageDialog(pnl, "Vehicle not found.");
                }
            conn.close();

        } 
            catch (SQLException e) {
                JOptionPane.showMessageDialog(pnl, "Database error: " + e.getMessage());
            }
    }
    
    private ArrayList<String> fetchDistinctColumnValues(String columnName) {
        connectToDB();
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
        closeConnection();
        return values;
    }
    
    private Object[][] fetchVehicleData() {
        connectToDB();
        ArrayList <Object[]> vehicleList = new ArrayList<>();
    
        String query = "SELECT VehicleID, VType, Brand, Model, VehicleStatus FROM VEHICLES";

        try (Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                String id = rs.getString("VehicleID");
                String desc = rs.getString("Brand") +" " +rs.getString("Model");
                String type = rs.getString("VType");
                String status = rs.getString("VehicleStatus");
                vehicleList.add(new Object[]{id,desc, type, status});
                 
            }
            conn.close();
        }       
            catch (SQLException e) {
                e.printStackTrace(); 
            }
        return vehicleList.toArray(new Object[0][]);
       
    }

    private void fetchAvailableVehicle(JTextField vehicleIdFld, DatePicker pickStart, DatePicker pickEnd, JTextField descFld) {
        connectToDB();
        String vehicleId = vehicleIdFld.getText().trim();
        String startDate = pickStart.getDate() != null ? pickStart.getDate().toString() : "";
        String endDate = pickEnd.getDate() != null ? pickEnd.getDate().toString() : "";
        String description = descFld.getText().trim();

            if (vehicleId.isEmpty() || startDate.isEmpty() || endDate.isEmpty() || description.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                return;
            }

            try {
                String maintenanceId = "M" + System.currentTimeMillis() % 100000;
                String insert = "INSERT INTO MAINTENANCE (MaintenanceID, VehicleID, MaintenanceDate, MaintenanceStatus, MDescription) VALUES (?, ?, ?, ?, ?)";

                PreparedStatement stmt = conn.prepareStatement(insert);
                stmt.setString(1, maintenanceId);
                stmt.setString(2, vehicleId);
                stmt.setDate(3, Date.valueOf(startDate)); 
                stmt.setString(4, "Scheduled");
                stmt.setString(5, description);

                int rows = stmt.executeUpdate();

                if (rows > 0) {
                    String updateVehicle = "UPDATE VEHICLES SET VehicleStatus = 'Under Maintenance' WHERE VehicleID = ?";
                    PreparedStatement updateStmt = conn.prepareStatement(updateVehicle);
                    updateStmt.setString(1, vehicleId);
                    updateStmt.executeUpdate();

                    DefaultTableModel model = new DefaultTableModel(maintenanceData(), new String[]{"Vehicle ID", "Vehicle Type", "Status"});
                    vehicleTab.setModel(model); 

                    JOptionPane.showMessageDialog(null, "Maintenance record added and table updated.");
                    dbUpdated = true;
                } 
                    else {
                        JOptionPane.showMessageDialog(null, "Failed to add maintenance record.");
                }
                conn.close();
            } 
                catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
        }
    }

    public Object[][] maintenanceData() {
        ArrayList<Object[]> vehicleList = new ArrayList<>();
        connectToDB();
        String query = "SELECT VehicleID, VType, Brand, Model, VehicleStatus FROM VEHICLES WHERE VehicleStatus = 'Available'";

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                while (rs.next()) {
                    String id = rs.getString("VehicleID");
                    String desc = rs.getString("Brand") + " " +rs.getString("Model");
                    String type = rs.getString("VType");
                    String status = rs.getString("VehicleStatus");
                    vehicleList.add(new Object[]{id,desc, type, status});
                }conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return vehicleList.toArray(new Object[0][]);
    }

    
    private void switchPnl(JPanel newPnl) {
        getContentPane().removeAll(); 
        add(newPnl);
        revalidate(); 
        repaint(); 
    }

    public static void main(String[] args) {
        new vehicleManagement();
    }
}

class NoPastDatesPolicy implements DateVetoPolicy {
    @Override
    public boolean isDateAllowed(LocalDate date) {
        return !date.isBefore(LocalDate.now()); // Only allow today and future
    }
}