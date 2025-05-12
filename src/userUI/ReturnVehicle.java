package userUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.text.DecimalFormat;
import javax.swing.BorderFactory;
import javax.swing.border.*;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.sql.*;
import java.time.LocalDate;
import javax.swing.JOptionPane;


public class ReturnVehicle extends JFrame{
    
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
        
        try
        {
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

            DecimalFormat twodec = new DecimalFormat("0.00");
            String accountStatus = "", name="", type="", model="", brand="", color="", licensePlate="", rentStatus = "";
            Date startDate= null, endDate = null;
            Double rentPrice=0.0;

            JPanel panel1 = new JPanel();
            panel1.setBackground(fldBGColor); 
            panel1.setBounds(140, 140, 620, 347);
            panel1.setLayout(null);
            panel1.setBorder(Border);

            JLabel returnLbl = new JLabel("RETURN VEHICLE", SwingConstants.CENTER);
            returnLbl.setForeground(fontColor);
            returnLbl.setFont(F3);
            returnLbl.setBounds(-10, 60, 900, 55);
            
            JLabel returnLS = new JLabel("RETURN VEHICLE", SwingConstants.CENTER);
            returnLS.setForeground(shadowColor);
            returnLS.setFont(F3);
            returnLS.setBounds(-7, 63, 900, 55);

            try
            {
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

            // 
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
            
            JButton returnBtn = new JButton("RETURN VEHICLE");
            returnBtn.setForeground(fontColor);
            returnBtn.setBounds(295, 520, 290, 40);
            returnBtn.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            returnBtn.setBackground(btnBGColor);
            returnBtn.setFont(F1);
            
            returnBtn.addActionListener(e -> {
                int ans = JOptionPane.showConfirmDialog(null, "Are you sure to return the vehicle now?", null, JOptionPane.YES_NO_OPTION);
                
                if(ans == JOptionPane.YES_OPTION){
                    int ans2 = JOptionPane.showConfirmDialog(null, "Are you returning the vehicle in good condition?", null, JOptionPane.YES_NO_OPTION);
                    
                    if(ans2==JOptionPane.YES_OPTION){
                        int ans3 = JOptionPane.showConfirmDialog(null, "Are you really sure? You will still be charged if damaged is found upon inspection.", null, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                        
                        if(ans3==JOptionPane.YES_OPTION){
                            dispose();
                            new Payment(AId, rentalID,vehicleID, totalPrice, overtimeDays, false);
                        } else{
                                dispose();
                                new Payment(AId, rentalID,vehicleID, totalPrice, overtimeDays, true);
                              }
                    }
                        else {
                            dispose();
                            new Payment(AId, rentalID,vehicleID, totalPrice, overtimeDays, true);
                        }
                }
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
            
            pan.add(panel1);
            pan.add(returnLbl);
            pan.add(returnLS);
            pan.add(returnBtn);
        }
        else if(accRentStatus.equals("Pending Approval")){
            label1.setText("Your rental request was not approved yet.");
            label2.setText("Your rental request was not approved yet.");
            pan.add(label1);
            pan.add(label2);
        }
        else{
            label1.setText("You are not renting at the moment.");
            label2.setText("You are not renting at the moment.");
            pan.add(label1);
            pan.add(label2);
        }
        
        //ActionListeners
        backBtn.addActionListener(e -> {
            dispose(); new UserLandingPageUI(AId);});
        
        pan.add(backBtn);
        pan.add(bBtnShadow);
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
