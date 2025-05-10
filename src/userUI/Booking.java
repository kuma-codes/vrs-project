package userUI;


import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class Booking 
{

    // JDBC setup
    private Connection conn;
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=vRentalSystemDB;encrypt=true;trustServerCertificate=true";
    private static final String DB_USER = "admin";
    private static final String DB_PASS = "admin456";
    String vehicleIDSelected = "";

    public Booking(String AId) 
    {
        
        JFrame frm = new JFrame("Vehicle Rental System");
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setSize(900, 640);
        frm.setLayout(null);
        frm.setResizable(false);
        frm.setLocationRelativeTo(null);

        JPanel pnl = new JPanel();
        pnl.setBackground(new Color(196, 227, 244));
        pnl.setBounds(0, 0, 900, 640);
        pnl.setLayout(null);

        JPanel line1 = new JPanel();
        line1.setBackground(new Color(100, 149, 237));
        line1.setBounds(90, 0, 30, 640);

        JPanel line2 = new JPanel();
        line2.setBackground(new Color(100, 149, 237));
        line2.setBounds(150, 0, 30, 640);

        JButton backBtn = new JButton("BACK");
        backBtn.setBounds(10, 20, 70, 20);
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.setBackground(new Color(135, 206, 235));
        backBtn.setOpaque(false);
        backBtn.setBorderPainted(false);
        backBtn.setFont(new Font("Arial", Font.BOLD, 12));
        
        JLabel label1 = new JLabel("",SwingConstants.CENTER);
        label1.setForeground(new Color (39, 58, 87));
        label1.setFont(new Font("Arial", Font.BOLD, 30));
        label1.setBounds(0, 270, 900, 40);
        
        
        try{
            connectToDB();
            
            String aQuery = "SELECT FName, LName, AccountStatus FROM ACCOUNT WHERE AccountID = '" + AId + "'";
            PreparedStatement as = conn.prepareStatement(aQuery);
            ResultSet aDetails = as.executeQuery();
            
            String name = "";
            String status = "";
            
            if(aDetails.next()){
            name = aDetails.getString("FName") +" "+ aDetails.getString("LName");
            status = aDetails.getString("AccountStatus");
            }
            
            if(status.equals("Not Renting")){
                JLabel bookingLbl = new JLabel("CHOOSE A VEHICLE", SwingConstants.CENTER);
                bookingLbl.setForeground(new Color (39, 58, 87));
                bookingLbl.setFont(new Font ("Arial", Font.BOLD, 40));
                bookingLbl.setBounds(0, 30, 900, 50);

                JLabel nameLbl = new JLabel("Customer Name:");
                nameLbl.setFont(new Font("Arial", Font.BOLD, 15));
                nameLbl.setForeground(new Color (39, 58, 87));
                nameLbl.setBounds(100, 100, 300, 35);
                JTextField nameFld = new JTextField(name);
                nameFld.setEditable(false);
                nameFld.setBounds(100, 130, 650, 40);

                JLabel typeLbl = new JLabel("Type:");
                typeLbl.setFont(new Font("Arial", Font.BOLD, 15));
                typeLbl.setForeground(new Color (39, 58, 87));
                typeLbl.setBounds(100, 170, 300, 35);
                JComboBox<String> typeCBox = new JComboBox<>();
                for (String type : fetchDistinctColumnValues("VType")) {
                typeCBox.addItem(type);
                }
                typeCBox.setBounds(100, 200, 650, 40);
                
                
                JLabel brandLbl = new JLabel("Brand:");
                brandLbl.setFont(new Font("Arial", Font.BOLD, 15));
                brandLbl.setForeground(new Color (39, 58, 87));
                brandLbl.setBounds(100, 240, 300, 35);
                JComboBox<String> brandCBox = new JComboBox<>();
                brandCBox.setEnabled(false);
                brandCBox.setBounds(100, 270, 650, 40);

                JLabel modelLbl = new JLabel("Model:");
                modelLbl.setFont(new Font("Arial", Font.BOLD, 15));
                modelLbl.setForeground(new Color (39, 58, 87));
                modelLbl.setBounds(100, 310, 300, 35);
                JComboBox<String> modelCBox = new JComboBox<>();
                modelCBox.setEnabled(false);
                modelCBox.setBounds(100, 340, 650, 40);

                JLabel colorLbl = new JLabel("Color:");
                colorLbl.setFont(new Font("Arial", Font.BOLD, 15));
                colorLbl.setForeground(new Color (39, 58, 87));
                colorLbl.setBounds(100, 380, 300, 35);
                JComboBox<String> colorCBox = new JComboBox<>();
                colorCBox.setEnabled(false);
                colorCBox.setBounds(100, 410, 650, 40);

                JLabel costLbl = new JLabel("Daily Rate:");
                colorLbl.setFont(new Font("Arial", Font.BOLD, 15));
                costLbl.setForeground(new Color (39, 58, 87));
                costLbl.setBounds(100, 450, 300, 35);
                JTextField costFld = new JTextField();
                costFld.setBounds(100, 480, 650, 40);
                costFld.setEditable(false);

                // Proceed Button
                JButton proceedBtn = new JButton("PROCEED");
                proceedBtn.setBounds(350, 530, 150, 40);

                
                typeCBox.addActionListener(e -> {
                brandCBox.removeAllItems();
                brandCBox.setEnabled(true);               
                for (String brand : fetchDistinctColumnValues("Brand", "VType = '" +
                     typeCBox.getSelectedItem() + "'")) {
                brandCBox.addItem(brand);
                }
                });
                
                brandCBox.addActionListener(e->{
                modelCBox.removeAllItems();
                modelCBox.setEnabled(true);   
                for (String model : fetchDistinctColumnValues("Model", "VType = '" +
                    typeCBox.getSelectedItem() + "' AND Brand = '" +brandCBox.getSelectedItem()+"' ")) {
                modelCBox.addItem(model);
                }
                });
                
                modelCBox.addActionListener(e -> {
                colorCBox.removeAllItems();
                colorCBox.setEnabled(true);   
                for (String color : fetchDistinctColumnValues("Color", "VType = '" +
                    typeCBox.getSelectedItem() + "' AND Brand = '" +brandCBox.getSelectedItem()+"' AND MODEL = '"
                    +modelCBox.getSelectedItem() + "' ")) {
                colorCBox.addItem(color);
                }
                });
                colorCBox.addActionListener(e->{
                try{
                        String query = "SELECT RentPrice, VehicleID FROM VEHICLES WHERE VType = '" + 
                        typeCBox.getSelectedItem() + "' AND Brand = '" + 
                        brandCBox.getSelectedItem()+"' AND MODEL = '"
                        +modelCBox.getSelectedItem() + "' ";
                        PreparedStatement p = conn.prepareStatement(query);
                        ResultSet rs = p.executeQuery();
                        
                        if(rs.next()){
                        Double dailyrate = rs.getDouble("RentPrice");
                        String rp = String.format("%.2f", dailyrate);
                        costFld.setText(rp);
                           vehicleIDSelected = rs.getString("VehicleID");
                        }
                }
                catch(SQLException exc){
                JOptionPane.showMessageDialog(null, exc.getMessage());
                }
                });
                
                proceedBtn.addActionListener(e1->{
                    if(brandCBox.getSelectedItem()==null||modelCBox.getSelectedItem()==null||colorCBox.getSelectedItem()==null){
                    JOptionPane.showMessageDialog(null, "Please select all values");
                    }
                    else{
                        frm.dispose();
                        new BookingDate(AId, vehicleIDSelected);
                        closeConnection();
                        }

                });

                pnl.add(bookingLbl);
                pnl.add(nameLbl);
                pnl.add(nameFld);
                pnl.add(typeLbl);
                pnl.add(typeCBox);
                pnl.add(brandLbl);
                pnl.add(brandCBox);
                pnl.add(modelLbl);
                pnl.add(modelCBox);
                pnl.add(colorLbl);
                pnl.add(colorCBox);
                pnl.add(costLbl);
                pnl.add(costFld);
                pnl.add(proceedBtn);
            }
            else if(status.equals("Pending Approval")){
                    label1.setText("You currently have vehicle waiting for approval.");
                    pnl.add(label1);
                }
                    else{
                        label1.setText("You can only rent one vehicle at a time");
                        pnl.add(label1);
                    }
        }
        catch(SQLException sqlexc){
        JOptionPane.showMessageDialog(null, sqlexc.getMessage());
        }

            backBtn.addActionListener(e -> {
                frm.dispose();
                new UserLandingPageUI(AId);
                closeConnection();
            });
        
        pnl.add(backBtn);
        pnl.add(line1);
        pnl.add(line2);

        frm.add(pnl);
        frm.setVisible(true);
    }
    
    private ArrayList<String> fetchDistinctColumnValues(String columnName, String Condition) {
        ArrayList<String> values = new ArrayList<>();
        String query = "SELECT DISTINCT " + columnName + " FROM VEHICLES " +"WHERE VehicleStatus = 'Available' AND " +Condition +"ORDER BY " + columnName;
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String val = rs.getString(columnName);
                if (val != null && !val.trim().isEmpty()) {
                    values.add(val);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading filter values: " + e.getMessage());
        }
        return values;
    }
    
    private ArrayList<String> fetchDistinctColumnValues(String columnName) {
        ArrayList<String> values = new ArrayList<>();
        String query = "SELECT DISTINCT " + columnName + " FROM VEHICLES WHERE VehicleStatus = 'Available' ORDER BY " + columnName;
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String val = rs.getString(columnName);
                if (val != null && !val.trim().isEmpty()) {
                    values.add(val);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading filter values: " + e.getMessage());
        }
        return values;
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
    public static void main(String[] args) {
        new Booking("U1");
    }
}
