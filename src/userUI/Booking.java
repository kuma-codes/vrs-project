package userUI;


import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class Booking 
{
    //Fonts
    private static final Font F1 = new Font("Arial", Font.BOLD, 18);
    private static final Font F2 = new Font("Arial", Font.BOLD, 35);
    private static final Font F3 = new Font("Arial", Font.BOLD, 40);
    private static final Font F4 = new Font("Arial", Font.BOLD, 13);
    
    //Colors
    private static final Color fontColor = Color.WHITE;
    private static final Color shadowColor = new Color(143,143,143);
    private static final Color fldBGColor = new Color(240, 240, 240);
    private static final Color btnBGColor = new Color(92,142,175);
    
    //Border
    private static final Border lineBorder = BorderFactory.createLineBorder(Color.GRAY,1);
    private static final Border empBorder = BorderFactory.createEmptyBorder(2,2,2,2);
    private static final Border Border = new CompoundBorder(lineBorder,empBorder);
    
    // JDBC setup
    private Connection conn;
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=vRentalSystemDB;encrypt=true;trustServerCertificate=true";
    private static final String DB_USER = "admin";
    private static final String DB_PASS = "admin456";
    String vehicleIDSelected = "";

    public Booking(String AId) 
    {
        
        JFrame frm = new JFrame("Rent a Vehicle");
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
        line1.setBackground(new Color(132, 168, 230));
        line1.setBounds(90, 0, 30, 640);

        JPanel line2 = new JPanel();
        line2.setBackground(new Color(132, 168, 230));
        line2.setBounds(150, 0, 30, 640);

        //backBtn
        JButton backBtn = new JButton("<");
        backBtn.setBounds(20, 20, 60, 40);
        backBtn.setForeground(fontColor);
        backBtn.setFocusPainted(false);
        backBtn.setBackground(Color.WHITE);
        backBtn.setOpaque(false);
        backBtn.setBorderPainted(false);
        backBtn.setFont(F3);
        
        //backBtn shadow
        JButton bBtnShadow = new JButton("<");
        bBtnShadow.setBounds(23, 24, 60, 40);
        bBtnShadow.setForeground(shadowColor);
        bBtnShadow.setFocusPainted(false);
        bBtnShadow.setBackground(Color.WHITE);
        bBtnShadow.setEnabled(false);
        bBtnShadow.setOpaque(false);
        bBtnShadow.setBorderPainted(false);
        bBtnShadow.setFont(F3);
        
        JLabel label1 = new JLabel("",SwingConstants.CENTER);
        label1.setForeground(fontColor);
        label1.setFont(F2);
        label1.setBounds(-10, 270, 900, 40);
        
        JLabel label2 = new JLabel("",SwingConstants.CENTER);
        label2.setForeground(shadowColor);
        label2.setFont(F2);
        label2.setBounds(-7, 273, 900, 40);
        
        
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
                //bookingLbl
                JLabel bookingLbl = new JLabel("CHOOSE A VEHICLE", SwingConstants.CENTER);
                bookingLbl.setForeground(Color.WHITE);
                bookingLbl.setFont(F3);
                bookingLbl.setBounds(-10, 25, 900, 55);
                
                    JLabel bookingLS = new JLabel("CHOOSE A VEHICLE", SwingConstants.CENTER);
                    bookingLS.setForeground(shadowColor);
                    bookingLS.setFont(F3);
                    bookingLS.setBounds(-7, 28, 900, 55);
                
                JLabel nameLbl = new JLabel("Customer Name:");
                nameLbl.setFont(F1);
                nameLbl.setForeground(fontColor);
                nameLbl.setBounds(125, 95, 280, 35);
                
                    JLabel nameLS = new JLabel("Customer Name:");
                    nameLS.setFont(F1);
                    nameLS.setForeground(shadowColor);
                    nameLS.setBounds(127, 97, 280, 35);
                
                JTextField nameFld = new JTextField(name);
                nameFld.setEditable(false);
                nameFld.setBounds(125, 125, 640, 40);
                nameFld.setBackground(fldBGColor);
                nameFld.setBorder(Border);
                nameFld.setFont(F4);

                JLabel typeLbl = new JLabel("Type:");
                typeLbl.setFont(F1);
                typeLbl.setForeground(fontColor);
                typeLbl.setBounds(125, 165, 280, 35);
                    
                    JLabel typeLS = new JLabel("Type:");
                    typeLS.setFont(F1);
                    typeLS.setForeground(shadowColor);
                    typeLS.setBounds(127, 167, 280, 35);
                
                JComboBox<String> typeCBox = new JComboBox<>();
                typeCBox.setBackground(fldBGColor);
                typeCBox.addItem("All");
                for (String type : fetchDistinctColumnValues("VType")) {
                    typeCBox.addItem(type);
                }
                typeCBox.setBounds(125, 195, 640, 40);
                typeCBox.setFont(F4);
                
                JLabel brandLbl = new JLabel("Brand:");
                brandLbl.setFont(F1);
                brandLbl.setForeground(fontColor);
                brandLbl.setBounds(125, 235, 280, 35);
                
                    JLabel brandLS = new JLabel("Brand:");
                    brandLS.setFont(F1);
                    brandLS.setForeground(shadowColor);
                    brandLS.setBounds(127, 237, 280, 35);
                
                JComboBox<String> brandCBox = new JComboBox<>();
                brandCBox.addItem("---");
                brandCBox.setBackground(fldBGColor);
                brandCBox.setEnabled(false);
                brandCBox.setBounds(125, 265, 640, 40);
                brandCBox.setFont(F4);

                JLabel modelLbl = new JLabel("Model:");
                modelLbl.setFont(F1);
                modelLbl.setForeground(fontColor);
                modelLbl.setBounds(125, 305, 280, 35);

                    JLabel modelLS = new JLabel("Model:");
                    modelLS.setFont(F1);
                    modelLS.setForeground(shadowColor);
                    modelLS.setBounds(127, 307, 280, 35);
                
                JComboBox<String> modelCBox = new JComboBox<>();
                modelCBox.addItem("---");
                modelCBox.setBackground(fldBGColor);
                modelCBox.setEnabled(false);
                modelCBox.setBounds(125, 335, 640, 40);
                modelCBox.setFont(F4);

                JLabel colorLbl = new JLabel("Color:");
                colorLbl.setFont(F1);
                colorLbl.setForeground(fontColor);
                colorLbl.setBounds(125, 375, 280, 35);

                    JLabel colorLS = new JLabel("Color:");
                    colorLS.setFont(F1);
                    colorLS.setForeground(shadowColor);
                    colorLS.setBounds(127, 377, 280, 35);
                
                JComboBox<String> colorCBox = new JComboBox<>();
                colorCBox.addItem("---");
                colorCBox.setBackground(fldBGColor);
                colorCBox.setEnabled(false);
                colorCBox.setBounds(125, 405, 640, 40);
                colorCBox.setFont(F4);

                JLabel rentPriceLbl = new JLabel("Rent Price:");
                rentPriceLbl.setFont(F1);
                rentPriceLbl.setForeground(fontColor);
                rentPriceLbl.setBounds(125, 445, 280, 35);

                    JLabel rentPriceLS = new JLabel("Rent Price:");
                    rentPriceLS.setFont(F1);
                    rentPriceLS.setForeground(shadowColor);
                    rentPriceLS.setBounds(127, 447, 280, 35);
                
                JTextField rentPriceFld = new JTextField();
                rentPriceFld.setBounds(125, 475, 640, 40);
                rentPriceFld.setBackground(fldBGColor);
                rentPriceFld.setText("---");
                rentPriceFld.setEditable(false);
                rentPriceFld.setBorder(Border);
                rentPriceFld.setFont(F4);

                // Proceed Button
                JButton proceedBtn = new JButton("PROCEED");
                proceedBtn.setBounds(360, 530, 160, 40);
                proceedBtn.setForeground(fontColor);
                proceedBtn.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                proceedBtn.setBackground(btnBGColor);
                proceedBtn.setFont(F1);
                
                typeCBox.addActionListener(e -> {
                    if(typeCBox.getSelectedItem()!="All"){
                        brandCBox.removeAllItems();
                        brandCBox.setEnabled(true);
                        for (String brand : fetchDistinctColumnValues("Brand", "VType = '" 
                            + typeCBox.getSelectedItem() + "'")) {
                                brandCBox.addItem(brand);
                        }
                    } else {
                        brandCBox.removeAllItems();
                        brandCBox.addItem("---");
                        brandCBox.setEnabled(false);
                        modelCBox.removeAllItems();
                        modelCBox.addItem("---");
                        modelCBox.setEnabled(false);
                        colorCBox.removeAllItems();
                        colorCBox.addItem("---");
                        colorCBox.setEnabled(false);
                        rentPriceFld.setText("---");
                    }  
                });
                
                brandCBox.addActionListener(e->{
                    modelCBox.removeAllItems(); 
                    modelCBox.setEnabled(true);
                    for (String model : fetchDistinctColumnValues("Model", "VType = '" + 
                    typeCBox.getSelectedItem() + "' AND Brand = '" 
                    +brandCBox.getSelectedItem()+"' ")) {
                        modelCBox.addItem(model);
                    }
                });
                
                modelCBox.addActionListener(e -> {
                    colorCBox.removeAllItems();
                    colorCBox.setEnabled(true);
                    for (String color : fetchDistinctColumnValues("Color", "VType = '" + typeCBox.getSelectedItem() + "' AND Brand = '" +brandCBox.getSelectedItem()+"' AND MODEL = '"
                        + modelCBox.getSelectedItem() + "' ")) {
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
                            rentPriceFld.setText("₱ "+rp);
                               vehicleIDSelected = rs.getString("VehicleID");
                            }
                    }
                    catch(SQLException exc){
                        JOptionPane.showMessageDialog(null, exc.getMessage());
                    }
                });
                
                proceedBtn.addActionListener(e1->{
                    if(brandCBox.getSelectedItem()=="---"||modelCBox.getSelectedItem()=="---"||colorCBox.getSelectedItem()=="---"){
                        JOptionPane.showMessageDialog(null, "Please select all values");
                    } else{
                            int x = JOptionPane.showConfirmDialog(null, "Are you sure you want to proceed?",
                            "Confirmation Message",JOptionPane.YES_NO_OPTION);
                            if(x==JOptionPane.YES_OPTION){
                                frm.dispose();
                                new BookingDate(AId, vehicleIDSelected);
                                closeConnection();
                            }
                        }

                });

                pnl.add(bookingLbl);    pnl.add(bookingLS);
                pnl.add(nameLbl);       pnl.add(nameLS);
                pnl.add(nameFld);       
                pnl.add(typeLbl);       pnl.add(typeLS);
                pnl.add(typeCBox);      
                pnl.add(brandLbl);      pnl.add(brandLS);
                pnl.add(brandCBox);     
                pnl.add(modelLbl);      pnl.add(modelLS);
                pnl.add(modelCBox);     
                pnl.add(colorLbl);      pnl.add(colorLS);
                pnl.add(colorCBox);     
                pnl.add(rentPriceLbl);  pnl.add(rentPriceLS);
                pnl.add(rentPriceFld);       
                pnl.add(proceedBtn);    
                
            }
            else if(status.equals("Pending Approval")){
                    label1.setText("You currently have vehicle waiting for approval.");
                    label2.setText("You currently have vehicle waiting for approval.");
                    pnl.add(label1);
                    pnl.add(label2);
                }
                    else{
                        label1.setText("You can only rent one vehicle at a time");
                        label2.setText("You can only rent one vehicle at a time");
                        pnl.add(label1);
                        pnl.add(label2);
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
        pnl.add(bBtnShadow);
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
}
