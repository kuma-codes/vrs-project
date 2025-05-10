package userUI;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;

public class BookingSummary{
    
    // JDBC setup
    private Connection conn;
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=vRentalSystemDB;encrypt=true;trustServerCertificate=true";
    private static final String DB_USER = "admin";
    private static final String DB_PASS = "admin456";
    
    public BookingSummary(String accID, String vehicleID, LocalDate startDate, LocalDate endDate, int TotalDays) 
    {
        String name="", model="", brand="", color="", licensePlate="";
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

        JLabel summaryLbl = new JLabel("BOOKING SUMMARY", SwingConstants.CENTER);
        summaryLbl.setForeground(new Color (39, 58, 87));
        summaryLbl.setFont(new Font ("Arial", Font.BOLD, 40));
        summaryLbl.setBounds(0, 60, 900, 50);

        JPanel panel1 = new JPanel();
        panel1.setBackground(new Color(240, 240, 240)); 
        panel1.setBounds(30, 140, 820, 340);
        panel1.setLayout(null);
        
        try{
        connectToDB();
        String accountDetails = "SELECT FName, LName FROM ACCOUNT WHERE AccountID = ?";
        PreparedStatement aDetails = conn.prepareStatement(accountDetails);
        aDetails.setString(1,accID);
        ResultSet aSet = aDetails.executeQuery();

        if(aSet.next()){
            name = aSet.getString("FName") + " " + aSet.getString("LName");
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
        
        }
        catch(SQLException e){
        JOptionPane.showMessageDialog(null, e.getMessage());
        }
        finally{
        closeConnection();
        }
        
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
        totalPrice = TotalDays * rentPrice;
        JLabel daysLbl = new JLabel("Total Rental Time: " + TotalDays + " Days");
        daysLbl.setBounds(20, 260, 260, 25);
        JLabel priceLbl = new JLabel("Total Payment: $" + totalPrice);
        priceLbl.setBounds(20, 290, 260, 25);

        // Confirm Button
        JButton confirmBtn = new JButton("Confirm Transaction");
        confirmBtn.setBounds(350, 520, 180, 40);
        confirmBtn.setFocusPainted(false);
        confirmBtn.setFont(new Font("Arial", Font.BOLD, 14));
        
        confirmBtn.addActionListener(e -> {
            int ans= JOptionPane.showConfirmDialog(null, "Are you sure to proceed with this transaction?","",JOptionPane.YES_NO_OPTION);
            if(ans == JOptionPane.YES_OPTION){
                JOptionPane.showMessageDialog(frm, "Transaction Confirmed! Thank you.");
                
                try{
                    int count = 0;
                    Date sDate = Date.valueOf(startDate);
                    Date eDate = Date.valueOf(endDate);

                    
                    connectToDB();
                    String getID = "SELECT * FROM RENTAL_DETAILS WHERE CAST(SUBSTRING(RentalID, 2, 10) AS INT) = (SELECT MAX(CAST(SUBSTRING(RentalID, 2, 10) AS INT)) FROM RENTAL_DETAILS);";
                    PreparedStatement stmt = conn.prepareStatement(getID);
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        String lastID = rs.getString("RentalID"); 
                        lastID = lastID.substring(1);
                        count = Integer.parseInt(lastID);
                    }

                    rs.close();
                    
                    String addRental= "INSERT INTO RENTAL_DETAILS "
                            + "(RentalID, AccountID, VehicleID, PickupDate, ReturnDate, BillingDate,BillAmount, PaymentMethod,RentalStatus) "
                            + "VALUES (?,?,?,?,?,?,?,?,?)";
                    PreparedStatement ins= conn.prepareStatement(addRental);
                    ins.setString(1,"R"+(count+1));
                    ins.setString(2, accID);
                    ins.setString(3,vehicleID);
                    ins.setDate(4, sDate);
                    ins.setDate(5,eDate);
                    ins.setDate(6, null);
                    ins.setDouble(7,0.0);
                    ins.setString(8, "Not Yet Paid");
                    ins.setString(9,"Pending Approval");
                    int rowsAffected = ins.executeUpdate();
                    ins.close();
                    
                    String updateAccStatus = "UPDATE ACCOUNT SET AccountStatus = 'Pending Approval' WHERE AccountID = ?";
                    PreparedStatement updA = conn.prepareStatement(updateAccStatus);
                    updA.setString(1,accID);
                    updA.executeUpdate();
                    updA.close();
                    
                    String updateVehicleStatus = "UPDATE VEHICLES SET VehicleStatus = 'Pending Approval' WHERE VehicleID = ?";
                    PreparedStatement updV = conn.prepareStatement(updateVehicleStatus);
                    updV.setString(1,vehicleID);
                    updV.executeUpdate();
                    updV.close();
                    
                }
                catch(SQLException e1){
                    JOptionPane.showMessageDialog(null, e1.getMessage());
                }
                frm.dispose();
                new UserLandingPageUI(accID);
                }
//            
        });

        backBtn.addActionListener(e -> {
            frm.dispose();
//            new BookingDate();
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
        

        pnl.add(backBtn);
        pnl.add(summaryLbl);
        pnl.add(panel1);
        pnl.add(confirmBtn);
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
