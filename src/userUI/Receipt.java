package userUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import java.time.LocalDate;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Receipt {
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
        frm.setSize(900, 740);
        frm.setLayout(null);
        frm.setResizable(false);
        frm.setLocationRelativeTo(null);
        

        JPanel pan = new JPanel();                              
        pan.setBackground(new Color(196, 227, 244));
        pan.setBounds(0, 0, 900, 740);
        pan.setLayout(null);

        JPanel line1 = new JPanel();                            
        line1.setBackground(new Color(100, 149, 237)); 
        line1.setBounds(80, 0, 30, 740);

        JPanel line2 = new JPanel();
        line2.setBackground(new Color(100, 149, 237)); 
        line2.setBounds(150, 0, 30, 740);
        
        JLabel receipt = new JLabel("RECEIPT", SwingConstants.CENTER);
        receipt.setForeground(new Color (39, 58, 87));
        receipt.setFont(new Font ("Arial", Font.BOLD,60));
        receipt.setBounds(0, 80, 900, 50);
        
        JButton done = new JButton("DONE");                  
        done.setForeground(Color.WHITE);
        done.setFont(new Font("Arial", Font.BOLD, 20));
        done.setBackground(new Color(71, 112, 139));
        done.setBounds(360, 620, 150, 30);
        
        JTextField lfield = new JTextField();                    
        lfield.setBackground(new Color(217, 217, 217));
        lfield.setBounds(30, 180, 820, 390);
        
        JLabel home1 = new JLabel("HOME");                      
        home1.setForeground(new Color(0, 0, 139));
        home1.setFont(new Font("Arial", Font.BOLD, 12));
        home1.setBounds(820, 660, 110, 40);
        
        // eme
        JLabel RC  = new JLabel("Rental Company: VEHICLE RENTAL SYSTEM");
        JLabel BN = new JLabel("Billing No.: "+rentalID+accID+vehicleID);
        JLabel BD = new JLabel("Billing Date: " + formattedBillingDate);

        RC.setForeground(Color.BLACK);
        RC.setFont(new Font("Arial", Font.BOLD, 11));
        RC.setBounds(80, 190, 280, 20);

        BN.setForeground(Color.BLACK);
        BN.setFont(new Font("Arial", Font.BOLD, 11));
        BN.setBounds(80, 210, 280, 20);

        BD.setForeground(Color.BLACK);
        BD.setFont(new Font("Arial", Font.BOLD, 11));
        BD.setBounds(80, 230, 280, 20);

        // Customer details
        JLabel CN  = new JLabel("Customer Name:                                                                                                                                                                 " + fullName);
        JLabel VBM  = new JLabel("Vehicle brand & Model:                                                                                                                                                     " + vehicleDetail);
        JLabel RSD  = new JLabel("Rental Start Date:                                                                                                                                                               " + formattedStartDate);
        JLabel RED  = new JLabel("Rental End Date:                                                                                                                                                                 " + formattedEndDate);
        JLabel RF  = new JLabel("Rental fee:                                                                            $" + String.valueOf(twodec.format(rentalFee)));
        JLabel TRD = new JLabel("Total Rental Days:                                                               " + totalDays + " ");
        JLabel OF  = new JLabel("Ot fee:                                                                                   $" + String.valueOf(twodec.format(otFee)));
        JLabel DF = new JLabel("Damage fee:                                                                         $"+String.valueOf(twodec.format(damageFee)));
        JLabel TF = new JLabel("Total fee:                                                                              $"+String.valueOf(twodec.format(totalFee)));
        JLabel AP = new JLabel("Amount Paid:                                                                        $"+String.valueOf(twodec.format(amountPaid)));
        JLabel C = new JLabel("Change:       $"+String.valueOf(twodec.format(change)));
        
        CN.setForeground(Color.BLACK);
        CN.setFont(new Font("Arial", Font.BOLD, 12));
        CN.setBounds(80, 280, 900, 20);

        VBM.setForeground(Color.BLACK);
        VBM.setFont(new Font("Arial", Font.BOLD, 12));
        VBM.setBounds(80, 300, 900, 20);

        RF.setForeground(Color.BLACK);
        RF.setFont(new Font("Arial", Font.BOLD, 12));
        RF.setBounds(240, 380, 900, 20);

        OF.setForeground(Color.BLACK);
        OF.setFont(new Font("Arial", Font.BOLD, 12));
        OF.setBounds(240, 420, 900, 20);

        RSD.setForeground(Color.BLACK);
        RSD.setFont(new Font("Arial", Font.BOLD, 12));
        RSD.setBounds(80, 320, 900, 20);

        RED.setForeground(Color.BLACK);
        RED.setFont(new Font("Arial", Font.BOLD, 12));
        RED.setBounds(80, 340, 900, 20);

        TRD.setForeground(Color.BLACK);
        TRD.setFont(new Font("Arial", Font.BOLD, 12));
        TRD.setBounds(240, 400, 900, 20);

        DF.setForeground(Color.BLACK);
        DF.setFont(new Font("Arial", Font.BOLD, 12));
        DF.setBounds(240, 440, 900, 20);

        TF.setForeground(Color.BLACK);
        TF.setFont(new Font("Arial", Font.BOLD, 12));
        TF.setBounds(240, 460, 900, 20);
        
        AP.setForeground(Color.BLACK);
        AP.setFont(new Font("Arial", Font.BOLD, 12));
        AP.setBounds(240, 480, 900, 20);
        
        C.setForeground(Color.BLACK);
        C.setFont(new Font("Arial", Font.BOLD, 14));
        C.setBounds(360, 530, 900, 20);

           
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
        pan.add(home1);
        pan.add(lfield);
        pan.add(done);
        pan.add(receipt);
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