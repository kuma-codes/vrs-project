package userUI;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.text.DecimalFormat;

public class BookingSummary{
    
    //Fonts
    private static final Font F1 = new Font("Arial", Font.BOLD, 18);
    //private static final Font F2 = new Font("Arial", Font.BOLD, 30);
    private static final Font F3 = new Font("Arial", Font.BOLD, 40);
    private static final Font F4 = new Font("Arial", Font.BOLD, 14);
    
    //Colors
    private static final Color fontColor = Color.WHITE;
    private static final Color shadowColor = new Color(143,143,143);
    private static final Color fldBGColor = new Color(240, 240, 240);
    private static final Color btnBGColor = new Color(92,142,175);
    
    //Border
    private static final Border lineBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY,2);
    private static final Border empBorder = BorderFactory.createEmptyBorder(4,4,4,4);
    private static final Border Border = new CompoundBorder(lineBorder,empBorder);
    
    // JDBC setup
    private Connection conn;
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=vRentalSystemDB;encrypt=true;trustServerCertificate=true";
    private static final String DB_USER = "admin";
    private static final String DB_PASS = "admin456";
    
    public BookingSummary(String accID, String vehicleID, LocalDate startDate, LocalDate endDate, int totalDays) 
    {
        DecimalFormat twodec = new DecimalFormat("0.00");
        String name="", type="", model="", brand="", color="", licensePlate="";
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

        JLabel summaryLbl = new JLabel("BOOKING SUMMARY", SwingConstants.CENTER);
        summaryLbl.setForeground(fontColor);
        summaryLbl.setFont(F3);
        summaryLbl.setBounds(-10, 60, 900, 55);
        
        JLabel summaryLS = new JLabel("BOOKING SUMMARY", SwingConstants.CENTER);
        summaryLS.setForeground(shadowColor);
        summaryLS.setFont(F3);
        summaryLS.setBounds(-7, 63, 900, 55);

        JPanel panel1 = new JPanel();
        panel1.setBackground(fldBGColor); 
        panel1.setBounds(140, 140, 620, 347);
        panel1.setLayout(null);
        panel1.setBorder(Border);
        
        try
        {
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
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        finally{
            closeConnection();
        }
        
        // Booking Summary
        JPanel lblPanel = new JPanel();
        lblPanel.setBounds(25,10,260,327);
        lblPanel.setBackground(fldBGColor); 
        lblPanel.setLayout(new FlowLayout(FlowLayout.LEFT ,30 ,7));
        
        JPanel inputPanel = new JPanel();
        inputPanel.setBounds(315,10,260,327);
        inputPanel.setBackground(fldBGColor); 
        inputPanel.setLayout(new FlowLayout(FlowLayout.RIGHT ,20 ,7));
        
        JPanel colonPanel = new JPanel();
        colonPanel.setBounds(230,10,50,327); 
        colonPanel.setLayout(new FlowLayout(FlowLayout.LEFT ,15 ,7));
        
        for(int x=0;x<10;++x){
            JLabel colon = new JLabel(":");
            colon.setFont(F4);
            colon.setPreferredSize(new Dimension(50,25));
            colonPanel.add(colon);
        }
        
        //Booking Summary
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

        // Confirm Button
        JButton confirmBtn = new JButton("CONFIRM TRANSACTION");
        confirmBtn.setBounds(295, 520, 290, 40);
        confirmBtn.setForeground(fontColor);
        confirmBtn.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        confirmBtn.setBackground(btnBGColor);
        confirmBtn.setFont(F1);
        
        confirmBtn.addActionListener(e -> {
            
            int ans= JOptionPane.showConfirmDialog(null, "Are you sure to confirm with this transaction?","",JOptionPane.YES_NO_OPTION);
            
            if(ans == JOptionPane.YES_OPTION){
                JOptionPane.showMessageDialog(frm, "Transaction Confirmed! Thank you.");
                
                try{
                    int count = 0;
                    Date sDate = Date.valueOf(startDate);
                    Date eDate = Date.valueOf(endDate);

                    connectToDB();
                    String getID = "SELECT * FROM RENTAL_DETAILS WHERE CAST(SUBSTRING(RentalID, 2, 10) AS INT) "
                            + "= (SELECT MAX(CAST(SUBSTRING(RentalID, 2, 10) AS INT)) FROM RENTAL_DETAILS);";
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
        });

        backBtn.addActionListener(e -> {
            frm.dispose();
            new BookingDate(accID, vehicleID);
        });

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
        
            for(Component comp:lblPanel.getComponents()) 
                if(comp instanceof JLabel)comp.setFont(F4);

            for(Component comp:inputPanel.getComponents()) 
                if(comp instanceof JLabel)comp.setFont(F4);
        
        panel1.add(colonPanel);
        panel1.add(lblPanel);
        panel1.add(inputPanel);
        
        pnl.add(backBtn);
        pnl.add(bBtnShadow);
        pnl.add(summaryLbl);    
        pnl.add(summaryLS);
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
