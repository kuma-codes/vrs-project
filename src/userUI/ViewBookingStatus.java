package userUI;

import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.border.*;
import java.awt.*;
import java.sql.*;
import java.text.DecimalFormat;

public class ViewBookingStatus extends JFrame{
    
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
    private String vehicleID ="";
    
    public ViewBookingStatus(String accID) 
    {
        String accountStatus = "", name="", type= "", model="", brand="", color="", licensePlate="", rentStatus = "";
        Date startDate= null, endDate = null;
        DecimalFormat twodec = new DecimalFormat("0.00");
        Double rentPrice=0.00, totalPrice=0.00;
        
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

        JLabel summaryLbl = new JLabel("RENTING SUMMARY", SwingConstants.CENTER);
        summaryLbl.setForeground(fontColor);
        summaryLbl.setFont(F3);
        summaryLbl.setBounds(-10, 60, 900, 55);
        
        JLabel summaryLS = new JLabel("RENTING SUMMARY", SwingConstants.CENTER);
        summaryLS.setForeground(shadowColor);
        summaryLS.setFont(F3);
        summaryLS.setBounds(-7, 63, 900, 55);

        JPanel panel1 = new JPanel();
        panel1.setBackground(fldBGColor); 
        panel1.setBounds(140, 140, 620, 347);
        panel1.setLayout(null);
        panel1.setBorder(Border);
        
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
            type = vSet.getString("VType");
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
            JPanel lblPanel = new JPanel();
            lblPanel.setBounds(25,15,260,327);
            lblPanel.setBackground(fldBGColor); 
            lblPanel.setLayout(new FlowLayout(FlowLayout.LEFT ,30 ,4));

            JPanel inputPanel = new JPanel();
            inputPanel.setBounds(315,15,260,327);
            inputPanel.setBackground(fldBGColor); 
            inputPanel.setLayout(new FlowLayout(FlowLayout.RIGHT ,20 ,4));

            JPanel colonPanel = new JPanel();
            colonPanel.setBounds(230,15,50,327); 
            colonPanel.setLayout(new FlowLayout(FlowLayout.LEFT ,15 ,4));

            for(int x=0;x<11;++x){
                JLabel colon = new JLabel(":");
                colon.setFont(F4);
                colon.setPreferredSize(new Dimension(50,25));
                colonPanel.add(colon);
            }

            JLabel nameLbl = new JLabel ("Customer Name");
            nameLbl.setPreferredSize(new Dimension(200, 25));
            JLabel brandLbl = new JLabel(type + " Brand");
            brandLbl.setPreferredSize(new Dimension(200, 25));
            JLabel modelLbl = new JLabel(type + " Model");
            modelLbl.setPreferredSize(new Dimension(200, 25));
            JLabel colorLbl = new JLabel(type + " Color");
            colorLbl.setPreferredSize(new Dimension(2300, 25));
            JLabel licenseLbl = new JLabel("License Plate");
            licenseLbl.setPreferredSize(new Dimension(200, 25));
            JLabel startDateLbl = new JLabel("Rental Start Date");
            startDateLbl.setPreferredSize(new Dimension(200, 25));
            JLabel endDateLbl = new JLabel("Rental End Date");
            endDateLbl.setPreferredSize(new Dimension(200, 25));
            JLabel rentPriceLbl = new JLabel("Rent Price");
            rentPriceLbl.setPreferredSize(new Dimension(200, 25));
            JLabel daysLbl = new JLabel("Total Rental Time");
            daysLbl.setPreferredSize(new Dimension(200, 25));
            JLabel priceLbl = new JLabel("Total Payment" );
            priceLbl.setPreferredSize(new Dimension(200, 25));
            JLabel statusLbl = new JLabel("Rental Status");
            statusLbl.setPreferredSize(new Dimension(200, 25));

            JLabel nameInput = new JLabel(name);
            nameInput.setPreferredSize(new Dimension(200, 25));
            JLabel brandInput = new JLabel(brand);
            brandInput.setPreferredSize(new Dimension(200, 25));
            JLabel modelInput = new JLabel(model);
            modelInput.setPreferredSize(new Dimension(200, 25));
            JLabel colorInput = new JLabel(color);
            colorInput.setPreferredSize(new Dimension(200, 25));
            JLabel licenseInput = new JLabel(licensePlate);
            licenseInput.setPreferredSize(new Dimension(200, 25));
            JLabel startDateInput = new JLabel(startDate+"");
            startDateInput.setPreferredSize(new Dimension(200, 25));
            JLabel endDateInput = new JLabel(endDate+"");
            endDateInput.setPreferredSize(new Dimension(200, 25));
            JLabel rentPriceInput = new JLabel("₱ "+ String.valueOf(twodec.format(rentPrice)));
            rentPriceInput.setPreferredSize(new Dimension(200, 25));
            totalPrice = totalDays * rentPrice;
            JLabel daysInput = new JLabel(totalDays + " Days");
            daysInput.setPreferredSize(new Dimension(200, 25));
            JLabel priceInput = new JLabel("₱ " + String.valueOf(twodec.format(totalPrice)));
            priceInput.setPreferredSize(new Dimension(200, 25));
            JLabel statusLS = new JLabel(rentStatus);
            statusLS.setPreferredSize(new Dimension(200, 25));

            lblPanel.add(nameLbl);
            lblPanel.add(brandLbl);
            lblPanel.add(modelLbl);
            lblPanel.add(colorLbl);
            lblPanel.add(licenseLbl);
            lblPanel.add(startDateLbl);
            lblPanel.add(endDateLbl);
            lblPanel.add(rentPriceLbl); 
            lblPanel.add(daysLbl);
            lblPanel.add(priceLbl);
            lblPanel.add(statusLbl);

            inputPanel.add(nameInput);
            inputPanel.add(brandInput);
            inputPanel.add(modelInput);
            inputPanel.add(colorInput);
            inputPanel.add(licenseInput);
            inputPanel.add(startDateInput);
            inputPanel.add(endDateInput);
            inputPanel.add(rentPriceInput); 
            inputPanel.add(daysInput);
            inputPanel.add(priceInput);
            inputPanel.add(statusLS);
        
            for(Component comp:lblPanel.getComponents()) 
                if(comp instanceof JLabel)comp.setFont(F4);

            for(Component comp:inputPanel.getComponents()) 
                if(comp instanceof JLabel)comp.setFont(F4);
            
            panel1.add(colonPanel);
            panel1.add(lblPanel);
            panel1.add(inputPanel);
            
            pnl.add(panel1);
            pnl.add(summaryLbl);    
            pnl.add(summaryLS);
            
        } else{//This will appear if the user is not renting a vehicle
            JLabel label1 = new JLabel("",SwingConstants.CENTER);
            label1.setForeground(fontColor);
            label1.setFont(F2);
            label1.setBounds(-10, 270, 900, 40);

            JLabel label2 = new JLabel("",SwingConstants.CENTER);
            label2.setForeground(shadowColor);
            label2.setFont(F2);
            label2.setBounds(-7, 273, 900, 40);

            label1.setText("You are currently not renting.");
            label2.setText("You are currently not renting.");
            pnl.add(label1);
            pnl.add(label2);
        }
        
       // Cancel Button
        JButton cancelBtn = new JButton("CANCEL TRANSACTION");
        cancelBtn.setForeground(fontColor);
        cancelBtn.setBounds(295, 520, 290, 40);
        cancelBtn.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        cancelBtn.setBackground(btnBGColor);
        cancelBtn.setFont(F1);

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

        pnl.add(backBtn);
        pnl.add(bBtnShadow);
        
        if(accountStatus.equals("Pending Approval")){
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
}
