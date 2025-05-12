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
import java.awt.Font;
import java.awt.Color;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class vehicleManagement extends JFrame {

    private static final Font fontA = new Font("Arial", Font.BOLD, 18);
    private static final Font fontB = new Font("Arial", Font.BOLD, 14);
    private static final Color lblue = new Color(135, 206, 235);
    private static final Color dblue = new Color(100, 149, 237);

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
    
    public vehicleManagement() {
        setTitle("Vehicle Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 660);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);
        

        mainPnl = new JPanel();
        mainPnl.setBackground(new Color(196, 227, 244));
        mainPnl.setBounds(0, 0, 900, 640);
        mainPnl.setLayout(null);

        JPanel line1 = new JPanel();
        line1.setBackground(dblue);
        line1.setBounds(90, 0, 30, 640);

        JPanel line2 = new JPanel();
        line2.setBackground(dblue);
        line2.setBounds(150, 0, 30, 640);

        JLabel title = new JLabel("VEHICLE MANAGEMENT", SwingConstants.CENTER);
        title.setForeground(new Color (39, 58, 87));
        title.setFont(new Font ("Arial", Font.BOLD, 40));
        title.setBounds(0, 60, 900, 30);

        JButton addVehicle = new JButton("Add Vehicle");
        addVehicle.setBounds(80, 140, 740, 70);
        addVehicle.setFont(new Font("Arial", Font.BOLD, 20));

        JButton modifyVehicle = new JButton("Modify Vehicle");
        modifyVehicle.setBounds(80, 230, 740, 70);
        modifyVehicle.setFont(new Font("Arial", Font.BOLD, 20));

        JButton removeVehicle = new JButton("Remove Vehicle");
        removeVehicle.setBounds(80, 320, 740, 70);
        removeVehicle.setFont(new Font("Arial", Font.BOLD, 20));

        JButton maintenance = new JButton("Schedule Maintenance");
        maintenance.setBounds(80, 410, 740, 70);
        maintenance.setFont(new Font("Arial", Font.BOLD, 20));

        JButton back = new JButton("Back");
        back.setBounds(80, 500, 740, 70);
        back.setFont(new Font("Arial", Font.BOLD, 20));

        mainPnl.add(title);
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
        titlelbl.setForeground(new Color (39, 58, 87));
        titlelbl.setFont(new Font("Arial", Font.BOLD, 40));
        titlelbl.setBounds(0, 60, 900, 30);
        panel.add(titlelbl);

        JPanel line1 = new JPanel();
        line1.setBackground(dblue);
        line1.setBounds(90, 0, 30, 640);

        JPanel line2 = new JPanel();
        line2.setBackground(dblue);
        line2.setBounds(150, 0, 30, 640);

        // Add Vehicle Panel
        if (title.equals("Add Vehicle")) {
            
            JLabel vehicleType = new JLabel("Vehicle Type:");
            vehicleType.setBounds(140, 130, 200, 20);
            panel.add(vehicleType);
            
            String[] vehicles = {"Car", "Motorcycle"}; //type of vehicles
            JComboBox <String> vehiclesBox = new JComboBox<>(vehicles);
            vehiclesBox.setBounds(140, 150, 600, 40);
            panel.add(vehiclesBox);

            JLabel brand = new JLabel("Brand:");
            brand.setBounds(100, 210, 200, 20);
            panel.add(brand);
            
            JTextField brandFld = new JTextField();
            brandFld.setBounds(100, 230, 280, 30);
            panel.add(brandFld);

            JLabel model = new JLabel("Model(Year):");
            model.setBounds(100, 270, 200, 20);
            panel.add(model);

            JTextField modelFld = new JTextField();
            modelFld.setBounds(100, 290, 280, 30);
            panel.add(modelFld);
            
            JLabel color = new JLabel("Color:");
            color.setBounds(100, 330, 200, 20);
            panel.add(color);
            
            JTextField colorFld = new JTextField();
            colorFld.setBounds(100, 350, 280, 30);
            panel.add(colorFld);
            
            JLabel license = new JLabel("License Plate:");
            license.setBounds(480, 210, 200, 20);
            panel.add(license);
            
            JTextField licenseFld = new JTextField();
            licenseFld.setBounds(480, 230, 280, 30);
            panel.add(licenseFld);
            
            JLabel price = new JLabel("Rent Price:");
            price.setBounds(480, 260, 210, 20);
            panel.add(price);
            
            JTextField priceFld = new JTextField();
            priceFld.setText(" ₱ ");
            priceFld.setBounds(480, 290, 280, 30);
            panel.add(priceFld);
            
            JLabel status = new JLabel("Status:");
            status.setBounds(480, 330, 200, 20);
            panel.add(status);
            
            String[] statuses = {"Available", "Under Maintenance"};
            JComboBox <String> statusBox = new JComboBox<>(statuses);
            statusBox.setBounds(480, 350, 280, 30);
            panel.add(statusBox);

            JButton add = new JButton("ADD");
            add.setBounds(300, 430, 100, 40);
            panel.add(add);

            JButton back = new JButton("BACK");
            back.setBounds(420, 430, 100, 40);
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
            vehicleID.setBounds(100, 130, 200, 20);
            panel.add(vehicleID);

            JTextField vehicleIDFld = new JTextField();
            vehicleIDFld.setBounds(100, 150, 280, 30);
            panel.add(vehicleIDFld);

            JLabel vehicleType = new JLabel("Vehicle Type:");
            vehicleType.setBounds(100, 190, 200, 20);
            panel.add(vehicleType);
            
            String[] vehicles = {"Car", "Motorcycle", "SUV"}; //type of vehicles
            JComboBox <String> vehiclesBox = new JComboBox<>(vehicles);
            vehiclesBox.setBounds(100, 210, 200, 30);
            panel.add(vehiclesBox);

            JLabel brand = new JLabel("Brand:");
            brand.setBounds(100, 250, 200, 20);
            panel.add(brand);
            
            JTextField brandFld = new JTextField();
            brandFld.setBounds(100, 270, 280, 30);
            panel.add(brandFld);

            JLabel model = new JLabel("Model(Year):");
            model.setBounds(100, 310, 200, 20);
            panel.add(model);

            JTextField modelFld = new JTextField();
            modelFld.setBounds(100, 330, 280, 30);
            panel.add(modelFld);
            
            JLabel color = new JLabel("Color:");
            color.setBounds(100, 370, 200, 20);
            panel.add(color);
            
            JTextField colorFld = new JTextField();
            colorFld.setBounds(100, 390, 280, 30);
            panel.add(colorFld);

            JLabel license = new JLabel("License Plate:");
            license.setBounds(480, 250, 200, 20);
            panel.add(license);
            
            JTextField licenseFld = new JTextField();
            licenseFld.setBounds(480, 270, 280, 30);
            panel.add(licenseFld);
            
            JLabel price = new JLabel("Rent Price:");
            price.setBounds(480, 310, 200, 20);
            panel.add(price);
            
            JTextField priceFld = new JTextField();
            priceFld.setText(" ₱ ");
            priceFld.setBounds(480, 330, 280, 30);
            panel.add(priceFld);
            
            JLabel status = new JLabel("Status:");
            status.setBounds(480, 370, 200, 20);
            panel.add(status);
            
            String[] statuses = {"Available", "Rented", "Under Maintenance"};
            JComboBox <String> statusBox = new JComboBox<>(statuses);
            statusBox.setBounds(480, 390, 280, 30);
            panel.add(statusBox);

            JButton updateBtn = new JButton("UPDATE");
            updateBtn.setBounds(300, 460, 100, 40);
            panel.add(updateBtn);

            JButton back = new JButton("BACK");
            back.setBounds(420, 460, 100, 40);
            panel.add(back);
            
            updateBtn.addActionListener(e -> {
                modifyVehicle(vehicleIDFld.getText().trim(),vehiclesBox,brandFld,modelFld,
                                                            colorFld,licenseFld,priceFld,statusBox,panel);
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
            panel.add(search);

            JTextField searchFld = new JTextField();
            searchFld.setBounds(130, 140, 200, 25);
            panel.add(searchFld);

            JLabel vehicleType = new JLabel("Type:");
            vehicleType.setBounds(350, 140, 40, 25);
            panel.add(vehicleType);

            JComboBox <String> vehicleTypeBox = new JComboBox<>(new String[]{"All"});
            for (String type : fetchDistinctColumnValues("VType")) {
                vehicleTypeBox.addItem(type);
            }
            
            vehicleTypeBox.setBounds(390, 140, 150, 25);
            panel.add(vehicleTypeBox);

            JButton searchBtn = new JButton("Search");
            searchBtn.setBounds(550, 140, 100, 25);
            panel.add(searchBtn);

            JButton resetBtn = new JButton("Reset");
            resetBtn.setBounds(660, 140, 100, 25);
            panel.add(resetBtn);
    
            String[] attributes = {"Vehicle ID","Vehicle Description", "Vehicle Type", "Status"};
            Object[][] data = fetchVehicleData();
            
            DefaultTableModel vehicleModel = new DefaultTableModel(attributes, 0);
            JTable vehicleTab = new JTable(vehicleModel);
            vehicleTab.setRowHeight(25);
            vehicleTab.setEnabled(false);
    
            JScrollPane scroll = new JScrollPane(vehicleTab);
            scroll.setBounds(70, 190, 750, 200);
            panel.add(scroll);
        
            JLabel vehicleLbl = new JLabel("Enter Vehicle ID to Remove:");
            vehicleLbl.setBounds(70, 410, 200, 20);
            panel.add(vehicleLbl);
        
            JTextField vehicleFld = new JTextField();
            vehicleFld.setBounds(70, 430, 280, 30);
            panel.add(vehicleFld);

            JButton remove = new JButton("REMOVE");
            remove.setBounds (580, 480, 130, 40);
            panel.add(remove);
    
            JButton back = new JButton("BACK");
            back.setBounds(720, 480, 100, 40);
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
            availVehicle.setBounds(60, 140, 230, 20);
            availVehicle.setFont(new Font ("Arial", Font.BOLD, 20));
            panel.add(availVehicle);

            String[] attributes = {"Vehicle ID","Vehicle Description", "Vehicle Type", "Status"};
            Object[][] data = maintenanceData();

            vehicleTab = new JTable(data, attributes);
            vehicleTab.setRowHeight(25);
            vehicleTab.setEnabled(false);

            JScrollPane scroll = new JScrollPane(vehicleTab);
            scroll.setBounds(70, 190, 750, 120);
            panel.add(scroll);
            
            JLabel vehicleIdLbl = new JLabel("Enter Vehicle ID:");
            vehicleIdLbl.setBounds(100, 330, 280, 20);
            panel.add(vehicleIdLbl);

            JTextField vehicleIdFld = new JTextField();
            vehicleIdFld.setBounds(100, 350, 280, 30);
            panel.add(vehicleIdFld);

            JLabel startDate = new JLabel("Maintenance Start Date:");
            startDate.setBounds(480, 330, 280, 20);
            panel.add(startDate);

            DatePickerSettings start = new DatePickerSettings();
            DatePicker pickStart = new DatePicker(start);
            pickStart.setBounds(480, 350, 280, 30);
            panel.add(pickStart);

            JLabel endDate = new JLabel("Maintenance End Date:");
            endDate.setBounds(480, 390, 280, 20);
            panel.add(endDate);

            DatePickerSettings end = new DatePickerSettings();
            DatePicker pickEnd = new DatePicker(end);
            pickEnd.setBounds(480, 410, 280, 30);
            panel.add(pickEnd);

            JLabel description = new JLabel("Description:");
            description.setBounds(100, 390, 280, 20);
            panel.add(description);

            JTextField descFld = new JTextField();
            descFld.setBounds(100, 410, 280, 30);
            panel.add(descFld);

            JButton apply = new JButton("APPLY");
            apply.setBounds (580, 480, 130, 40);
            panel.add(apply);

            JButton back = new JButton("BACK");
            back.setBounds(720, 480, 100, 40);
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
                                 JTextField colorFld, JTextField licenseFld, JTextField priceFld, JComboBox <String> statusBox, JPanel pnl){
        
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
        String status = statusBox.getSelectedItem().toString();

        if (brand.isEmpty() || model.isEmpty() || color.isEmpty() || license.isEmpty() || priceText.isEmpty()) {
            JOptionPane.showMessageDialog(pnl, "Please fill in all fields.");
            return;
        }

            try {
                double price = Double.parseDouble(priceText);
                updateVehicleDB(vehicleID, vType, brand, model, color, license, price, status, pnl);
            } 
                catch (NumberFormatException no) {
                       JOptionPane.showMessageDialog(pnl, "Invalid rent price.");
                }
    }
    
    private void updateVehicleDB(String vehicleID, String vType, String brand, String model, String color,
                                 String license, double rentPrice, String status, JPanel panel) {
        connectToDB();
        String query = "UPDATE VEHICLES SET VType = ?, Brand = ?, Model = ?, Color = ?, LicensePlate = ?, RentPrice = ?, VehicleStatus = ? WHERE VehicleID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, vType);
            stmt.setString(2, brand);
            stmt.setString(3, model);
            stmt.setString(4, color);
            stmt.setString(5, license);
            stmt.setDouble(6, rentPrice);
            stmt.setString(7, status);
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
            String vehicleType = row[1].toString();
            String status = row[2].toString();

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