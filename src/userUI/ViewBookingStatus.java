package userUI;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ViewBookingStatus extends JFrame{
    
   
    
    // JDBC setup
    private Connection conn;
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=vRentalSystemDB;encrypt=true;trustServerCertificate=true";
    private static final String DB_USER = "admin";
    private static final String DB_PASS = "admin456";
    private String vehicleID ="";
    
    public ViewBookingStatus(String accID) 
    {
        String accountStatus = "", name="", model="", brand="", color="", licensePlate="", rentStatus = "";
        Date startDate= null, endDate = null;
        Double rentPrice=0.0, totalPrice=0.0;
        
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

        // BACK Button
        JButton backBtn = new JButton("<");
        backBtn.setBounds(10, 20, 50, 30);
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.setBackground(new Color(135, 206, 235));
        backBtn.setBorderPainted(false);
        backBtn.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel summaryLbl = new JLabel("RENTING SUMMARY", SwingConstants.CENTER);
        summaryLbl.setForeground(new Color (39, 58, 87));
        summaryLbl.setFont(new Font ("Arial", Font.BOLD, 40));
        summaryLbl.setBounds(0, 60, 900, 50);

        JPanel panel1 = new JPanel();
        panel1.setBackground(new Color(240, 240, 240)); 
        panel1.setBounds(30, 140, 820, 350);
        panel1.setLayout(null);
        
        try{
        connectToDB();
        String accountDetails = "SELECT FName, LName, AccountStatus FROM ACCOUNT WHERE AccountID = ?";
        PreparedStatement aDetails = conn.prepareStatement(accountDetails);
        aDetails.setString(1,accID);
        ResultSet aSet = aDetails.executeQuery();

        if(aSet.next()){
            name = aSet.getString("FName") + " " + aSet.getString("LName");
            accountStatus = aSet.getString("AccountStatus");
        }
        
        String rentingDetails = "SELECT * FROM RENTAL_DETAILS WHERE AccountID = ? AND RentalStatus = 'Not Yet Returned' OR RentalStatus = 'Pending Approval'";
        PreparedStatement rDetails = conn.prepareStatement(rentingDetails);
        rDetails.setString(1,accID);
        ResultSet rSet = rDetails.executeQuery();
        if(rSet.next()){
           startDate = rSet.getDate("PickupDate");
           endDate = rSet.getDate("ReturnDate");
           rentStatus = rSet.getString("RentalStatus");
           vehicleID = rSet.getString("VehicleID");

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
        
        if(!accountStatus.equals("Not Renting") && !rentStatus.equals("Completed")){
            String sDate = startDate.toString();
            String eDate = endDate.toString();
            String[] startParts = sDate.split("-");
            String[] endParts = eDate.split("-");
            
            // Parse each part of the start date into integers
            int startYear = Integer.parseInt(startParts[0]);
            int startMonth = Integer.parseInt(startParts[1]);
            int startDay = Integer.parseInt(startParts[2]);

            // Parse each part of the end date into integers
            int endYear = Integer.parseInt(endParts[0]);
            int endMonth = Integer.parseInt(endParts[1]);
            int endDay = Integer.parseInt(endParts[2]);

            // Convert each date into "days since year 0"
            int startDays = startYear * 365 + (startMonth - 1) * 30 + startDay;
            int endDays = endYear * 365 + (endMonth - 1) * 30 + endDay;
            int totalDays = endDays - startDays + 1;
            
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
            pnl.add(panel1);
        }
            else{//This will appear if the user is not renting a vehicle
            JLabel label1 = new JLabel("",SwingConstants.CENTER);
            label1.setForeground(new Color (39, 58, 87));
            label1.setFont(new Font("Arial", Font.BOLD, 30));
            label1.setBounds(0, 270, 900, 40);
            label1.setText("You are currently not renting.");
            pnl.add(label1);
            }
        
       // Cancel Button
        JButton cancelBtn = new JButton("Cancel Transaction");
        cancelBtn.setBounds(350, 520, 180, 40);
        cancelBtn.setFocusPainted(false);
        cancelBtn.setFont(new Font("Arial", Font.BOLD, 14));

        cancelBtn.addActionListener(e -> {
                try{
                   int ans = JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel this Transaction?", "Confirmation", JOptionPane.YES_NO_OPTION);
                   if(ans==JOptionPane.YES_OPTION){
                   connectToDB();
                   String updR = "UPDATE RENTAL_DETAILS SET PaymentMethod = 'Cancelled', RentalStatus = 'Cancelled' WHERE AccountID = ? and VehicleID = ? and RentalStatus = 'Pending Approval'";
                   PreparedStatement updRDetails = conn.prepareStatement(updR);
                   updRDetails.setString(1, accID);                   
                   updRDetails.setString(2, vehicleID);
                   updRDetails.executeUpdate();
                   updRDetails.close();

                   String updA = "UPDATE ACCOUNT SET AccountStatus = 'Not Renting' WHERE AccountID = ?";
                   PreparedStatement updADetails = conn.prepareStatement(updA);
                   updADetails.setString(1, accID);   
                   updADetails.executeUpdate();
                   updADetails.close();

                   String updV = "UPDATE VEHICLES SET VehicleStatus = 'Available' WHERE VehicleID = ? ";
                   PreparedStatement updVDetails = conn.prepareStatement(updV);
                   updVDetails.setString(1, vehicleID);   
                   updVDetails.executeUpdate();
                   updVDetails.close();

                   }


                }

                catch(SQLException e2){
                    JOptionPane.showMessageDialog(null, e2.getMessage());
                }
                frm.dispose();
                new UserLandingPageUI(accID);
        });


        backBtn.addActionListener(e -> {
            frm.dispose();
            new UserLandingPageUI(accID);
        });

  
        
        
        pnl.add(summaryLbl);
        pnl.add(backBtn);
        
        if(rentStatus.equals("Pending Approval")){
        pnl.add(cancelBtn);
        }
        pnl.add(line1);
        pnl.add(line2);

        frm.add(pnl);
        frm.setVisible(true);
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
 
    }
}
