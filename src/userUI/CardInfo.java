package userUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Font;
import java.text.ParseException;
import javax.swing.JFormattedTextField;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.text.MaskFormatter;
import java.sql.*;
import java.time.LocalDate;

public class CardInfo {
    //JDBC Setup
    private Connection conn;
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=vRentalSystemDB;encrypt=true;trustServerCertificate=true";
    private static final String DB_USER = "admin";
    private static final String DB_PASS = "admin456";
    
    public CardInfo(String accID, String rentalID, String vehicleID,Double rentalFee,Double damageFee,Double otFee,Double totalFee, Double amountPaid, Double change) {
        JFrame frm = new JFrame("Vehicle Rental System"); // frame
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setSize(900, 550);
        frm.setResizable(false);
        frm.setLayout(null);
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

        JLabel payment = new JLabel("Card Info", SwingConstants.CENTER);
        payment.setForeground(new Color (39, 58, 87));
        payment.setFont(new Font ("Arial", Font.BOLD, 40));
        payment.setBounds(0, 60, 900, 50);
        try {
            MaskFormatter mask = new MaskFormatter("'###-##-######");
            mask.setPlaceholderCharacter('_');
            JFormattedTextField field1 = new JFormattedTextField(mask); // Card No.
            field1.setBounds(100, 160, 650, 40);

            JTextField field2 = new JTextField(); // Expired Date
            field2.setBounds(100, 230, 650, 40);

            JPasswordField field3 = new JPasswordField(); // Cvv/Pin
            field3.setBounds(100, 300, 650, 40);

            
            JTextField field4 = new JTextField(); // Card Holder
            field4.setBounds(100, 370, 650, 40);
 

            JButton payb = new JButton("PAY"); // button
            payb.setFont(new Font("Arial", Font.BOLD, 20));
            payb.setBounds(360, 430, 110, 40);

            JLabel CN = new JLabel("Card No."); 
            CN.setForeground(new Color (39, 58, 87));
            CN.setFont(new Font("Arial", Font.BOLD, 15));
            CN.setBounds(100, 130, 300, 35);

            JLabel ED = new JLabel("Expired Date"); 
            ED.setForeground(new Color (39, 58, 87));
            ED.setFont(new Font("Arial", Font.BOLD, 15));
            ED.setBounds(100, 200, 300, 35);

            JLabel PIN = new JLabel("Cvv/Pin"); 
            PIN.setForeground(new Color (39, 58, 87));
            PIN.setFont(new Font("Arial", Font.BOLD, 15));
            PIN.setBounds(100, 270, 300, 35);

            JLabel CH = new JLabel("Card Holder(Optional)"); 
            CH.setForeground(new Color (39, 58, 87));
            CH.setFont(new Font("Arial", Font.BOLD, 15));
            CH.setBounds(100, 340, 300, 35);

            JLabel back = new JLabel("BACK"); // back label
            back.setForeground(new Color(0, 0, 139));
            back.setFont(new Font("Arial", Font.BOLD, 12));
            back.setBounds(10, 2, 110, 40);

            payb.addActionListener(e -> {
                String cardNumber = field1.getText();
                String expiry = field2.getText();
                String cvv = field3.getText();
                String holder = field4.getText();

                if (cardNumber.isEmpty() || expiry.isEmpty() || cvv.isEmpty()) { 
                    JOptionPane.showMessageDialog(frm, "Please fill all card information besides card holder.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    try{
                    Date now = Date.valueOf(LocalDate.now());
                    connectToDB();
                    PreparedStatement updA = conn.prepareStatement("UPDATE ACCOUNT SET AccountStatus = 'Not Renting' WHERE AccountID = '" + accID +"'");
                    updA.executeUpdate();
                    PreparedStatement updV = conn.prepareStatement("UPDATE VEHICLES SET VehicleStatus = 'Available' WHERE VehicleID = '" + vehicleID +"'");
                    updV.executeUpdate();
                    PreparedStatement updR = conn.prepareStatement("UPDATE RENTAL_DETAILS SET BillingDate = ? , BillAmount = ? ,PaymentMethod = 'Card', RentalStatus = 'Completed' WHERE RentalID = ? ");
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
                    new Receipt(accID,rentalID,vehicleID,rentalFee, damageFee, otFee,totalFee, amountPaid, change);
                    }
                    catch(SQLException sqle){
                        JOptionPane.showMessageDialog(frm, sqle.getMessage());
                    }

                    
                }
            });

            pan.add(back);
            pan.add(CH);
            pan.add(PIN);
            pan.add(ED);
            pan.add(CN);
            pan.add(payb);
            pan.add(payment);
            pan.add(field4);
            pan.add(field3);
            pan.add(field2);
            pan.add(field1);
            pan.add(line1);
            pan.add(line2);

            frm.add(pan);
            frm.setVisible(true);
        } 
        catch (ParseException e) {
            JOptionPane.showMessageDialog(frm, e.getMessage());
        }

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
