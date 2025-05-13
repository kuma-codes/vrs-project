package userUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.border.*;
import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import java.time.LocalDate;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Receipt {
    
    //Fonts
    private static final Font F1 = new Font("Arial", Font.BOLD, 18);
    private static final Font F2 = new Font("Arial", Font.BOLD, 35);
    private static final Font F3 = new Font("Arial", Font.BOLD, 50);
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
    
    private Connection conn;
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=vRentalSystemDB;encrypt=true;trustServerCertificate=true";
    private static final String DB_USER = "admin";
    private static final String DB_PASS = "admin456";
    
    public Receipt(String accID, String rentalID, String vehicleID,Double rentalFee,Double damageFee,Double otFee,Double totalFee, Double amountPaid, Double change) {
        DecimalFormat twodec = new DecimalFormat("0.00");
        String fullName = "";
        String vehicleDetail = "";
        LocalDate startDate;
        LocalDate endDate;
        LocalDate billingDate;
        String formattedStartDate = "";
        String formattedEndDate = "";
        String formattedBillingDate = "";
        int totalDays = 0;
        
        try{
            connectToDB();
            PreparedStatement getAccDetails = conn.prepareStatement("SELECT FName, LName FROM ACCOUNT WHERE AccountID = ?");
            getAccDetails.setString(1, accID);
            ResultSet aDetails = getAccDetails.executeQuery();
            if(aDetails.next()){
            fullName = aDetails.getString("FName") + " " + aDetails.getString("LName");
            }
            getAccDetails.close();
            aDetails.close();

            PreparedStatement getVehicleDetails = conn.prepareStatement("SELECT * FROM VEHICLES WHERE VehicleID = ?");
            getVehicleDetails.setString(1, vehicleID);
            ResultSet vDetails = getVehicleDetails.executeQuery();
            if(vDetails.next()){
            vehicleDetail = vDetails.getString("Brand") + " " +vDetails.getString("Model");
            }
            getVehicleDetails.close();
            vDetails.close();
            
            PreparedStatement getRentalDetails = conn.prepareStatement("SELECT * FROM RENTAL_DETAILS WHERE RentalID = ?");
            getRentalDetails.setString(1, rentalID);
            ResultSet rDetails = getRentalDetails.executeQuery();  
            if(rDetails.next()){
            startDate = rDetails.getDate("PickupDate").toLocalDate();
            endDate = rDetails.getDate("ReturnDate").toLocalDate();
            billingDate = rDetails.getDate("BillingDate").toLocalDate();
                   
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
            long daysBetween = ChronoUnit.DAYS.between(startDate, endDate) + 1;
            totalDays = (int) daysBetween;
            formattedStartDate = startDate.format(formatter);
            formattedEndDate = endDate.format(formatter);
            formattedBillingDate = billingDate.format(formatter);

            }
        }
        catch(SQLException e){
        
        }
        
        JFrame frm = new JFrame("Vehicle Rental System");      
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setSize(900, 640);
        frm.setLayout(null);
        frm.setResizable(false);
        frm.setLocationRelativeTo(null);
        
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
        
        JLabel receipt = new JLabel("RECEIPT", SwingConstants.CENTER);
        receipt.setForeground(fontColor);
        receipt.setFont(F3);
        receipt.setBounds(-10, 40, 900, 55);
        
        JLabel receiptLS = new JLabel("RECEIPT", SwingConstants.CENTER);
        receiptLS.setForeground(shadowColor);
        receiptLS.setFont(F3);
        receiptLS.setBounds(-7, 43, 900, 55);
        
        JButton done = new JButton("DONE");                  
        done.setForeground(fontColor);
        done.setFont(F1);
        done.setBounds(360, 530, 160, 40);
        done.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        done.setBackground(btnBGColor);
        
        JPanel receiptPnl = new JPanel();                    
        receiptPnl.setBackground(fldBGColor);
        receiptPnl.setBounds(30, 110, 820, 390);
        receiptPnl.setLayout(null);
        receiptPnl.setBorder(Border);

         // 
        JLabel RC  = new JLabel("Rental Company: VEHICLE RENTAL SYSTEM");
        JLabel BN = new JLabel("Billing No.: "+rentalID+accID+vehicleID);
        JLabel BD = new JLabel("Billing Date: " + formattedBillingDate);

        RC.setForeground(Color.BLACK);
        RC.setFont(new Font("Arial", Font.BOLD, 12));
        RC.setBounds(80, 132, 280, 20);

        BN.setForeground(Color.BLACK);
        BN.setFont(new Font("Arial", Font.BOLD, 12));
        BN.setBounds(80, 152, 280, 20);

        BD.setForeground(Color.BLACK);
        BD.setFont(new Font("Arial", Font.BOLD, 12));
        BD.setBounds(80, 172, 280, 20);
        
        // Customer details
        JLabel CN  = new JLabel("Customer Name:                                                                                                                                     " + fullName);
        JLabel VBM  = new JLabel("Vehicle brand & Model:                                                                                                                         " + vehicleDetail);
        JLabel RSD  = new JLabel("Rental Start Date:                                                                                                                                    " + formattedStartDate);
        JLabel RED  = new JLabel("Rental End Date:                                                                                                                                     " + formattedEndDate);
        JLabel RF  = new JLabel("Rental fee:                                                                         ₱ " + String.valueOf(twodec.format(rentalFee)));
        JLabel TRD = new JLabel("Total Rental Days:                                                           " + totalDays + " ");
        JLabel OF  = new JLabel("Ot fee:                                                                                 ₱ " + String.valueOf(twodec.format(otFee)));
        JLabel DF = new JLabel("Damage fee:                                                                      ₱ "+String.valueOf(twodec.format(damageFee)));
        JLabel TF = new JLabel("Total fee:                                                                            ₱ "+String.valueOf(twodec.format(totalFee)));
        JLabel AP = new JLabel("Amount Paid:                                                                    ₱ "+String.valueOf(twodec.format(amountPaid)));
        JLabel C = new JLabel("Change:       $"+String.valueOf(twodec.format(change)));
        
        CN.setForeground(Color.BLACK);
        CN.setFont(new Font("Arial", Font.BOLD, 13));
        CN.setBounds(80, 212, 900, 20);

        VBM.setForeground(Color.BLACK);
        VBM.setFont(new Font("Arial", Font.BOLD, 13));
        VBM.setBounds(80, 232, 900, 20);

        RSD.setForeground(Color.BLACK);
        RSD.setFont(new Font("Arial", Font.BOLD, 13));
        RSD.setBounds(80, 252, 900, 20);

        RED.setForeground(Color.BLACK);
        RED.setFont(new Font("Arial", Font.BOLD, 13));
        RED.setBounds(80, 272, 900, 20);

        RF.setForeground(Color.BLACK);
        RF.setFont(new Font("Arial", Font.BOLD, 13));
        RF.setBounds(240, 312, 900, 20);

        TRD.setForeground(Color.BLACK);
        TRD.setFont(new Font("Arial", Font.BOLD, 13));
        TRD.setBounds(240, 332, 900, 20);

        OF.setForeground(Color.BLACK);
        OF.setFont(new Font("Arial", Font.BOLD, 13));
        OF.setBounds(240, 352, 900, 20);

        DF.setForeground(Color.BLACK);
        DF.setFont(new Font("Arial", Font.BOLD, 13));
        DF.setBounds(240, 372, 900, 20);

        TF.setForeground(Color.BLACK);
        TF.setFont(new Font("Arial", Font.BOLD, 13));
        TF.setBounds(240, 392, 900, 20);
        
        AP.setForeground(Color.BLACK);
        AP.setFont(new Font("Arial", Font.BOLD, 13));
        AP.setBounds(240, 412, 900, 20);
        
        C.setForeground(Color.BLACK);
        C.setFont(new Font("Arial", Font.BOLD, 15));
        C.setBounds(360, 452, 900, 20);
           
        done.addActionListener(e -> {frm.dispose(); new UserLandingPageUI(accID);});
        
        pan.add(C);
        pan.add(AP);
        pan.add(TF);
        pan.add(DF);
        pan.add(TRD);
        pan.add(RED);
        pan.add(RSD);
        pan.add(OF);
        pan.add(RF);
        pan.add(VBM);
        pan.add(CN);
        pan.add(BD);
        pan.add(BN);
        pan.add(RC);
        pan.add(receiptPnl);
        pan.add(done);
        pan.add(receipt);
        pan.add(receiptLS);
        pan.add(line1);
        pan.add(line2);

        frm.add(pan);
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
       new Receipt("","","",0.0,0.0,0.0,0.0,0.0,0.0);
}

}