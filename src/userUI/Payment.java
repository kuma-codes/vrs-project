package userUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.sql.*;
import java.time.LocalDate;

public class Payment {
    private Connection conn;
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=vRentalSystemDB;encrypt=true;trustServerCertificate=true";
    private static final String DB_USER = "admin";
    private static final String DB_PASS = "admin456";
    private Double totalFee = 0.0, otFee = 0.0, damageFee = 0.0, amountPaid = 0.0, change = 0.0;
    public Payment(String accID, String rentalID,String vehicleID, Double rentalFee, int otDays, Boolean isDamaged) { 
        System.out.println(otDays);
        DecimalFormat twodec = new DecimalFormat("0.00");
        JFrame frm = new JFrame("Vehicle Rental System"); // frame
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setSize(900, 560);
        frm.setLayout(null);
        frm.setResizable(false);
        frm.setLocationRelativeTo(null);

        JPanel pan = new JPanel(); // panel
        pan.setBackground(new Color(196, 227, 244));
        pan.setBounds(0, 0, 900, 640);
        pan.setLayout(null);

        JPanel line1 = new JPanel(); // vline1
        line1.setBackground(new Color(100, 149, 237));
        line1.setBounds(90, 0, 30, 640);

        JPanel line2 = new JPanel(); // vline2
        line2.setBackground(new Color(100, 149, 237));
        line2.setBounds(150, 0, 30, 640);

        JLabel payment = new JLabel("PAYMENT", SwingConstants.CENTER); // label for Payment title
        payment.setForeground(new Color (39, 58, 87));
        payment.setFont(new Font ("Arial", Font.BOLD, 40));
        payment.setBounds(0, 60, 900, 30);

        JTextField field1 = new JTextField(); // first;
        field1.setEditable(false);
        field1.setBounds(100, 160, 660, 30);

        JTextField field2 = new JTextField(); // second
        field2.setEditable(false);
        field2.setBounds(100, 230, 660, 30);

        JTextField field3 = new JTextField(); // third
        field3.setEditable(false);
        field3.setBounds(100, 300, 660, 30);

        String[] mode = { "CASH", "CARD" }; // jlist for mode of Payment
        JList<String> sfield1 = new JList<>(mode);
        JScrollPane payy = new JScrollPane(sfield1);
        payy.setBackground(new Color(217, 217, 217));
        payy.setBounds(100, 370, 150, 40);

        JTextField sfield2 = new JTextField(); // total textfield
        sfield2.setEditable(false);
        sfield2.setBounds(290, 370, 150, 40);

        JButton payb = new JButton("PROCEED"); // button
        payb.setBounds(360, 450, 150, 30);


        JButton backBtn = new JButton("BACK");
        backBtn.setBounds(10, 20, 70, 20);
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.setBackground(new Color(135, 206, 235));
        backBtn.setOpaque(false);
        backBtn.setBorderPainted(false);
        backBtn.setFont(new Font("Arial", Font.BOLD, 12));
        
        JLabel rentalf = new JLabel("RENTAL FEE"); // label for first
        rentalf.setFont(new Font("Arial", Font.BOLD, 15));
        rentalf.setForeground(new Color (39, 58, 87));
        rentalf.setBounds(100, 130, 300, 35);

        JLabel OTF = new JLabel("OT FEE (6%/Day)"); // label for second
        OTF.setFont(new Font("Arial", Font.BOLD, 15));
        OTF.setForeground(new Color (39, 58, 87));
        OTF.setBounds(100, 200, 300, 35);

        JLabel DF = new JLabel("DAMAGE FEE"); // label for third
        DF.setFont(new Font("Arial", Font.BOLD, 15));
        DF.setForeground(new Color (39, 58, 87));
        DF.setBounds(100, 270, 300, 35);

        JLabel modep = new JLabel("MODE OF PAYMENT"); // label for jlist
        modep.setFont(new Font("Arial", Font.BOLD, 15));
        modep.setForeground(new Color (39, 58, 87));
        modep.setBounds(100, 340, 300, 35);

        JLabel total = new JLabel("TOTAL"); // label for total
        total.setFont(new Font("Arial", Font.BOLD, 15));
        total.setForeground(new Color (39, 58, 87));
        total.setBounds(290, 340, 300, 35);
        
        if(otDays >= 0){
            otFee = otDays * (rentalFee * .06);
        }

        
        field1.setText(String.valueOf(twodec.format(rentalFee)));
        field2.setText(String.valueOf(twodec.format(otFee)));
        if(isDamaged){
        damageFee = rentalFee * .20;
        field3.setText(String.valueOf(twodec.format(damageFee)));
        }
        else{
        damageFee = rentalFee * 0;
        field3.setText(String.valueOf(twodec.format(damageFee)));
        }
        totalFee = rentalFee + damageFee + otFee;
        sfield2.setText(String.valueOf(twodec.format(totalFee)));

        
        
        backBtn.addActionListener(e -> {frm.dispose(); new ReturnVehicle(accID);});
        payb.addActionListener(e -> {
            String selected = sfield1.getSelectedValue();
            if (selected == null) {
                JOptionPane.showMessageDialog(frm, "Please select a mode of payment.", "Error", JOptionPane.ERROR_MESSAGE);
            } 
                else if ("CASH".equals(selected)) {
                    try{
                        amountPaid = Double.parseDouble(JOptionPane.showInputDialog(frm, "Enter payment amount", null, JOptionPane.INFORMATION_MESSAGE));
                        if(amountPaid >=totalFee){
                        int ans = JOptionPane.showConfirmDialog(frm,"Proceed with the Payment?", null, JOptionPane.YES_NO_OPTION);
                            if(ans== JOptionPane.YES_OPTION){
                                change = amountPaid - totalFee;
                                Date now = Date.valueOf(LocalDate.now());
                                
                                connectToDB();
                                
                                PreparedStatement updA = conn.prepareStatement("UPDATE ACCOUNT SET AccountStatus = 'Not Renting' WHERE AccountID = '" + accID +"'");
                                updA.executeUpdate();
                                PreparedStatement updV = conn.prepareStatement("UPDATE VEHICLES SET VehicleStatus = 'Available' WHERE VehicleID = '" + vehicleID +"'");
                                updV.executeUpdate();
                                PreparedStatement updR = conn.prepareStatement("UPDATE RENTAL_DETAILS SET BillingDate = ? , BillAmount = ? ,PaymentMethod = 'Cash', RentalStatus = 'Completed' WHERE RentalID = ? ");
                                updR.setDate(1, now);
                                updR.setDouble(2, totalFee);
                                updR.setString(3, rentalID);
                                updR.executeUpdate();
                                JOptionPane.showMessageDialog(frm, "Payment Successful!", null, JOptionPane.INFORMATION_MESSAGE);
                                closeConnection();
                                updA.close();
                                updV.close();
                                updR.close();
                                frm.dispose();
                                new Receipt(accID, rentalID, vehicleID,rentalFee, damageFee, otFee,totalFee, amountPaid, change);
                            }

                        }
                            else{
                                JOptionPane.showMessageDialog(frm, "Insufficient amount", null, JOptionPane.WARNING_MESSAGE);
                            }
                    }
                    catch(NumberFormatException ne){
                        JOptionPane.showMessageDialog(frm, "Only input numbers.",null, JOptionPane.WARNING_MESSAGE);
                    }
                    catch(SQLException se){
                        JOptionPane.showMessageDialog(frm, se.getMessage(),null, JOptionPane.WARNING_MESSAGE);
                    }
                } 
                    else if ("CARD".equals(selected)) {
                        
                        frm.dispose(); 
                        new CardInfo(accID,rentalID,vehicleID,rentalFee,damageFee,otFee, totalFee, amountPaid, change);
                        
                    }
        });

        pan.add(backBtn);
        pan.add(total);
        pan.add(modep);
        pan.add(DF);
        pan.add(OTF);
        pan.add(rentalf);
        pan.add(payb);
        pan.add(payment);
        pan.add(payy);
        pan.add(sfield2);
        pan.add(field3);
        pan.add(field2);
        pan.add(field1);
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
    }
}