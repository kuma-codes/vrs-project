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
import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

public class Payment {
    
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
    
    private Connection conn;
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=vRentalSystemDB;encrypt=true;trustServerCertificate=true";
    private static final String DB_USER = "admin";
    private static final String DB_PASS = "admin456";
    private Double totalFee = 0.0, otFee = 0.0, damageFee = 0.0, amountPaid = 0.0, change = 0.0;
    
    public Payment(String accID, String rentalID,String vehicleID, Double rentalFee, int otDays, Boolean isDamaged) { 
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
        line1.setBackground(new Color(132, 168, 230));
        line1.setBounds(90, 0, 30, 640);

        JPanel line2 = new JPanel(); // vline2
        line2.setBackground(new Color(132, 168, 230));
        line2.setBounds(150, 0, 30, 640);

        JLabel payment = new JLabel("PAYMENT", SwingConstants.CENTER); // label for Payment title
        payment.setForeground(fontColor);
        payment.setFont(F3);
        payment.setBounds(0, 40, 900, 50);
        
        JLabel paymentls = new JLabel("PAYMENT", SwingConstants.CENTER); // label for Payment title
        paymentls.setForeground(shadowColor);
        paymentls.setFont(F3);
        paymentls.setBounds(3, 43, 900, 50);

        JTextField field1 = new JTextField(); // first;
        field1.setEditable(false);
        field1.setBounds(125, 130, 640, 40);
        field1.setBackground(fldBGColor);
        field1.setBorder(Border);
        field1.setFont(F4);

        JTextField field2 = new JTextField(); // second
        field2.setEditable(false);
        field2.setBounds(125, 205, 640, 40);
        field2.setBackground(fldBGColor);
        field2.setBorder(Border);
        field2.setFont(F4);

        JTextField field3 = new JTextField(); // third
        field3.setEditable(false);
        field3.setBounds(125, 280, 640, 40);
        field3.setBackground(fldBGColor);
        field3.setBorder(Border);
        field3.setFont(F4);

        String[] mode = { "CASH", "CARD" }; // jlist for mode of Payment
        JList<String> sfield1 = new JList<>(mode);
        JScrollPane payy = new JScrollPane(sfield1);
        payy.setBounds(125, 365, 225, 45);
        payy.setBackground(fldBGColor);
        payy.setBorder(Border);
        payy.setFont(F4);

        JTextField sfield2 = new JTextField(); // total textfield
        sfield2.setEditable(false);
        sfield2.setBounds(380, 365, 225, 40);
        sfield2.setBackground(fldBGColor);
        sfield2.setBorder(Border);
        sfield2.setFont(F4);

        JButton payb = new JButton("PROCEED"); // button
        payb.setBounds(370, 445, 150, 40);
        payb.setForeground(fontColor);
        payb.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        payb.setBackground(btnBGColor);
        payb.setFont(F1);

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
        
        JLabel rentalf = new JLabel("RENTAL FEE"); // label for first
        rentalf.setFont(F1);
        rentalf.setForeground(fontColor);
        rentalf.setBounds(125, 100, 280, 35);
        
        JLabel rentalfls = new JLabel("RENTAL FEE"); // label for first with shadow
        rentalfls.setFont(F1);
        rentalfls.setForeground(shadowColor);
        rentalfls.setBounds(127, 102, 280, 35);

        JLabel OTF = new JLabel("OT FEE (6%/Day)"); // label for second
        OTF.setFont(F1);
        OTF.setForeground(fontColor);
        OTF.setBounds(125, 175, 280, 35);
        
        JLabel OTFls = new JLabel("OT FEE (6%/Day)"); // label for second with shadow
        OTFls.setFont(F1);
        OTFls.setForeground(shadowColor);
        OTFls.setBounds(127, 177, 280, 35);

        JLabel DF = new JLabel("DAMAGE FEE"); // label for third
        DF.setFont(F1);
        DF.setForeground(fontColor);
        DF.setBounds(125, 250, 280, 35);

        JLabel DFls = new JLabel("DAMAGE FEE"); // label for third with shadow
        DFls.setFont(F1);
        DFls.setForeground(shadowColor);
        DFls.setBounds(127, 252, 280, 35);

        JLabel modep = new JLabel("MODE OF PAYMENT"); // label for jlist
        modep.setFont(F1);
        modep.setForeground(fontColor);
        modep.setBounds(125, 335, 200, 35);

        JLabel modepls = new JLabel("MODE OF PAYMENT"); // label for jlist with shadow
        modepls.setFont(F1);
        modepls.setForeground(shadowColor);
        modepls.setBounds(127, 337, 200, 35);

        JLabel total = new JLabel("TOTAL"); // label for total with shadow
        total.setFont(F1);
        total.setForeground(fontColor);
        total.setBounds(380, 335, 200, 35);

        JLabel totalls = new JLabel("TOTAL"); // label for total with shadow
        totalls.setFont(F1);
        totalls.setForeground(shadowColor);
        totalls.setBounds(382, 337, 200, 35);
        
        if(otDays >= 0){
            otFee = otDays * (rentalFee * .06);
        }

        field1.setText(String.valueOf(twodec.format(rentalFee)));
        field2.setText(String.valueOf(twodec.format(otFee)));
        
        if(isDamaged){
            damageFee = rentalFee * .20;
            field3.setText(String.valueOf(twodec.format(damageFee)));
        } else{
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
                    try
                    {
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
                        amountPaid = totalFee;
                        new CardInfo(accID,rentalID,vehicleID,rentalFee,otDays,isDamaged,damageFee,otFee, totalFee, amountPaid, change);
                    }
        });

        pan.add(backBtn);
        pan.add(bBtnShadow);
        pan.add(payment);
        pan.add(paymentls);
        pan.add(field1);
        pan.add(field2);
        pan.add(field3);
        pan.add(payy);
        pan.add(sfield2);
        pan.add(rentalf);
        pan.add(OTF);
        pan.add(DF);
        pan.add(modep);
        pan.add(total);
        pan.add(rentalfls);
        pan.add(OTFls);
        pan.add(DFls);
        pan.add(modepls);
        pan.add(totalls);
        pan.add(payb);
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