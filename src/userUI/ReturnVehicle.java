package userUI;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.sql.*;
import java.time.LocalDate;
import javax.swing.JOptionPane;


public class ReturnVehicle extends JFrame{
    // JDBC setup
    private Connection conn;
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=vRentalSystemDB;encrypt=true;trustServerCertificate=true";
    private static final String DB_USER = "admin";
    private static final String DB_PASS = "admin456";
    private String accRentStatus;
    private String rentalID;
    private String vehicleID = "";
    private Double totalPrice = 0.0;
    
    public ReturnVehicle(String AId){
        setTitle("Vehicle Rental System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 640);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);

        JPanel pan = new JPanel();
        pan.setBackground(new Color(196, 227, 244));
        pan.setBounds(0, 0, 900, 640);
        pan.setLayout(null);

        JPanel line1 = new JPanel();
        line1.setBackground(new Color(100, 149, 237)); 
        line1.setBounds(80, 0, 30, 640);

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
        PreparedStatement p = conn.prepareStatement("SELECT AccountStatus FROM ACCOUNT WHERE AccountID = ?");
        p.setString(1, AId);
        ResultSet rs = p.executeQuery();
        
        if(rs.next()){
        accRentStatus = rs.getString("AccountStatus");
        }
        p.close();
        rs.close();
        closeConnection();
        }
        catch(SQLException e){
        JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
        if(accRentStatus.equals("Currently Renting")){
            
            String accountStatus = "", name="", model="", brand="", color="", licensePlate="", rentStatus = "";
            Date startDate= null, endDate = null;
            Double rentPrice=0.0;
            
            JPanel panel1 = new JPanel();
            panel1.setBackground(new Color(240, 240, 240)); 
            panel1.setBounds(30, 140, 820, 350);
            panel1.setLayout(null);
            
            JLabel returnLbl = new JLabel("RETURN VEHICLE", SwingConstants.CENTER);
            returnLbl.setForeground(new Color (39, 58, 87));
            returnLbl.setFont(new Font ("Arial", Font.BOLD, 40));
            returnLbl.setBounds(0, 60, 900, 50);
            
            try{
            connectToDB();
            String accountDetails = "SELECT FName, LName, AccountStatus FROM ACCOUNT WHERE AccountID = ?";
            PreparedStatement aDetails = conn.prepareStatement(accountDetails);
            aDetails.setString(1,AId);
            ResultSet aSet = aDetails.executeQuery();

            if(aSet.next()){
                name = aSet.getString("FName") + " " + aSet.getString("LName");
                accountStatus = aSet.getString("AccountStatus");
            }

            String rentingDetails = "SELECT * FROM RENTAL_DETAILS WHERE AccountID = ? AND RentalStatus = 'Not Yet Returned' OR RentalStatus = 'Pending Approval'";
            PreparedStatement rDetails = conn.prepareStatement(rentingDetails);
            rDetails.setString(1,AId);
            ResultSet rSet = rDetails.executeQuery();
            if(rSet.next()){
               startDate = rSet.getDate("PickupDate");
               endDate = rSet.getDate("ReturnDate");
               rentStatus = rSet.getString("RentalStatus");
               vehicleID = rSet.getString("VehicleID");
               rentalID = rSet.getString("RentalID");

            }

            String vehicleDetails = "SELECT * FROM VEHICLES WHERE VehicleID = ?";
            PreparedStatement vDetails = conn.prepareStatement(vehicleDetails);
            vDetails.setString(1,vehicleID);
            ResultSet vSet = vDetails.executeQuery();

            if(vSet.next()){
                brand = vSet.getString("Brand");
                model = vSet.getString("Model");
                color = vSet.getString("Color");
                licensePlate = vSet.getString("LicensePlate");
                rentPrice = vSet.getDouble("RentPrice");
                }
            aSet.close();
            aDetails.close();        
            vSet.close();
            vDetails.close();
            rSet.close();
            rDetails.close();

            }
            catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
            }
            finally{
            closeConnection();
            }

            String sDate = startDate.toString();
            String eDate = endDate.toString();
            String rDate = LocalDate.now().toString();
            String[] startParts = sDate.split("-");
            String[] endParts = eDate.split("-");
            String[] returnParts = rDate.split("-");
            
            // Parse each part of the start date into integers
            int startYear = Integer.parseInt(startParts[0]);
            int startMonth = Integer.parseInt(startParts[1]);
            int startDay = Integer.parseInt(startParts[2]);

            // Parse each part of the end date into integers
            int endYear = Integer.parseInt(endParts[0]);
            int endMonth = Integer.parseInt(endParts[1]);
            int endDay = Integer.parseInt(endParts[2]);
            
            // Parse each part of the end date into integers
            int rYear = Integer.parseInt(returnParts[0]);
            int rMonth = Integer.parseInt(returnParts[1]);
            int rDay = Integer.parseInt(returnParts[2]);

            // Convert each date into "days since year 0"
            int startDays = startYear * 365 + (startMonth - 1) * 30 + startDay;
            int endDays = endYear * 365 + (endMonth - 1) * 30 + endDay;
            int returnDays = rYear * 365 + (rMonth - 1) * 30 + rDay;
            int totalDays = endDays - startDays + 1;
            int overtimeDays = returnDays - endDays;


            // Booking Summary
            JLabel nameLbl = new JLabel("Customer Name: " + name);
            nameLbl.setBounds(20, 20, 260, 25);
            JLabel brandLbl = new JLabel("Car Brand: " + brand);
            brandLbl.setBounds(20, 50, 260, 25);
            JLabel modelLbl = new JLabel("Car Model: " + model);
            modelLbl.setBounds(20, 80, 260, 25);
            JLabel colorLbl = new JLabel("Car Color: " + color);
            colorLbl.setBounds(20, 110, 260, 25);
            JLabel licenseLbl = new JLabel("License Plate: " + licensePlate);
            licenseLbl.setBounds(20, 140, 260, 25);
            JLabel startDateLbl = new JLabel("Rental Start Date: " + startDate);
            startDateLbl.setBounds(20, 170, 260, 25);
            JLabel endDateLbl = new JLabel("Rental End Date: " + endDate);
            endDateLbl.setBounds(20, 200, 260, 25);
            JLabel dailyRateLbl = new JLabel("Daily Rate: $" + rentPrice);
            dailyRateLbl.setBounds(20, 230, 260, 25);
            totalPrice = totalDays * rentPrice;
            JLabel daysLbl = new JLabel("Total Rental Time: " + totalDays + " Days");
            daysLbl.setBounds(20, 260, 260, 25);
            JLabel priceLbl = new JLabel("Total Payment: $" + totalPrice);
            priceLbl.setBounds(20, 290, 260, 25);
            JLabel statusLbl = new JLabel("Rental Status: " + rentStatus);
            statusLbl.setBounds(20, 320, 260, 25);
            JButton returnBtn = new JButton("Return Vehicle");
            returnBtn.setBounds(350, 520, 160, 40);
            returnBtn.setFocusPainted(false);
            returnBtn.setFont(new Font("Arial", Font.BOLD, 14));
            
            
            
            returnBtn.addActionListener(e -> {
                int ans = JOptionPane.showConfirmDialog(null, "Are you sure to return the vehicle now?", null, JOptionPane.YES_NO_OPTION);
                
                if(ans == JOptionPane.YES_OPTION){
                    int ans2 = JOptionPane.showConfirmDialog(null, "Are you returning the vehicle in good condition?", null, JOptionPane.YES_NO_OPTION);
                    if(ans2==JOptionPane.YES_OPTION){
                        int ans3 = JOptionPane.showConfirmDialog(null, "Are you really sure? You will still be charged if damaged is found upon inspection.", null, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                        if(ans3==JOptionPane.YES_OPTION){
                        dispose();
                        new Payment(AId, rentalID,vehicleID, totalPrice, overtimeDays, false);
                        }
                            else{
                            dispose();
                            new Payment(AId, rentalID,vehicleID, totalPrice, overtimeDays, true);
                            }
                    }
                        else{
                        dispose();
                        new Payment(AId, rentalID,vehicleID, totalPrice, overtimeDays, true);
                        }
                }
            });
            
            
            panel1.add(nameLbl);
            panel1.add(brandLbl);
            panel1.add(modelLbl);
            panel1.add(colorLbl);
            panel1.add(licenseLbl);
            panel1.add(startDateLbl);
            panel1.add(endDateLbl);
            panel1.add(dailyRateLbl); 
            panel1.add(daysLbl);
            panel1.add(priceLbl);
            panel1.add(statusLbl);
            pan.add(returnBtn);
            pan.add(returnLbl);
            pan.add(panel1);
        }
        else if(accRentStatus.equals("Pending Approval")){
        label1.setText("Your rental request was not approved yet.");
        }
        else{
        label1.setText("You are not renting at the moment.");
        }
        
        
        //ActionListeners
        backBtn.addActionListener(e -> {dispose(); new UserLandingPageUI(AId);});
        
        pan.add(backBtn);
        pan.add(label1);
        pan.add(line1);
        pan.add(line2);
 
        add(pan);
        setVisible(true);
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
        new ReturnVehicle("U1");
    }
}
