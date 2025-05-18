package userUI;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;
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
import javax.swing.BorderFactory;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

public class CardInfo {
    
        
    //Fonts
    private static final Font F1 = new Font("Arial", Font.BOLD, 18);
    private static final Font F2 = new Font("Arial", Font.BOLD, 20);
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
    
    //JDBC Setup
    private Connection conn;
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=vRentalSystemDB;encrypt=true;trustServerCertificate=true";
    private static final String DB_USER = "admin";
    private static final String DB_PASS = "admin456";
    
    public CardInfo(String accID, String rentalID, String vehicleID,Double rentalFee,int otDays,Boolean isDamage,Double damageFee,Double otFee,Double totalFee, Double amountPaid, Double change) {
        JFrame frm = new JFrame("Card Info"); // frame
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setSize(900, 560);
        frm.setResizable(false);
        frm.setLayout(null);
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

        JLabel payment = new JLabel("Card Info", SwingConstants.CENTER);
        payment.setForeground(fontColor);
        payment.setFont(F3);
        payment.setBounds(0, 40, 900, 50);
       
        JLabel paymentls = new JLabel("Card Info", SwingConstants.CENTER);
        paymentls.setForeground(shadowColor);
        paymentls.setFont(F3);
        paymentls.setBounds(3, 43, 900, 50);
        
        try {
            MaskFormatter mask = new MaskFormatter("#### #### #### ####");
            mask.setPlaceholderCharacter('-');
            JFormattedTextField field1 = new JFormattedTextField(mask); // Card No.
            field1.setBounds(115, 130, 650, 40);
            field1.setBackground(fldBGColor);
            field1.setBorder(Border);
            field1.setFont(F4);

            DatePicker field2 = new DatePicker(); // Expired Date
            field2.getComponentDateTextField().setEditable(false);
            field2.setBounds(115, 205, 650, 40);
            field2.getComponentDateTextField().setBackground(fldBGColor);
            field2.getComponentDateTextField().setFont(F4);
            field2.getComponentDateTextField().setBorder(Border);
            field2.addDateChangeListener(new DateChangeListener() {
                @Override
                public void dateChanged(DateChangeEvent event) {
                    SwingUtilities.invokeLater(() ->{
                        field2.getComponentDateTextField().setBackground(fldBGColor);
                        field2.getComponentDateTextField().setFont(F4);
                        field2.getComponentDateTextField().setBorder(Border);
                    });
                }
            });

            JPasswordField field3 = new JPasswordField(); // Cvv/Pin
            field3.setColumns(6);
            field3.setBounds(115, 280, 650, 40);
            field3.setBackground(fldBGColor);
            field3.setBorder(Border);
            field3.setFont(F4);

            JTextField field4 = new JTextField(); // Card Holder
            field4.setBounds(115, 355, 650, 40);
            field4.setBackground(fldBGColor);
            field4.setBorder(Border);
            field4.setFont(F4);
 
            JButton payb = new JButton("PAY"); // button
            payb.setBounds(385, 450, 110, 40);
            payb.setForeground(fontColor);
            payb.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            payb.setBackground(btnBGColor);
            payb.setFont(F2);

            JLabel CN = new JLabel("Card No."); 
            CN.setForeground(fontColor);
            CN.setFont(F1);
            CN.setBounds(115, 100, 300, 35);

            JLabel ED = new JLabel("Expiry Date"); 
            ED.setForeground(fontColor);
            ED.setFont(F1);
            ED.setBounds(115, 175, 300, 35);

            JLabel PIN = new JLabel("Cvv/Pin"); 
            PIN.setForeground(fontColor);
            PIN.setFont(F1);
            PIN.setBounds(115, 250, 300, 35);

            JLabel CH = new JLabel("Card Holder(Optional)");
            CH.setForeground(fontColor);
            CH.setFont(F1);
            CH.setBounds(115, 325, 300, 35);
            
            JLabel CNls = new JLabel("Card No."); 
            CNls.setForeground(shadowColor);
            CNls.setFont(F1);
            CNls.setBounds(117, 102, 300, 35);

            JLabel EDls = new JLabel("Expiry Date"); 
            EDls.setForeground(shadowColor);
            EDls.setFont(F1);
            EDls.setBounds(117, 177, 300, 35);

            JLabel PINls = new JLabel("Cvv/Pin"); 
            PINls.setForeground(shadowColor);
            PINls.setFont(F1);
            PINls.setBounds(117, 252, 300, 35);

            JLabel CHls = new JLabel("Card Holder(Optional)");
            CHls.setForeground(shadowColor);
            CHls.setFont(F1);
            CHls.setBounds(117, 327, 300, 35);

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
            
            backBtn.addActionListener(e -> 
                {frm.dispose(); 
                new Payment(accID,rentalID,vehicleID,rentalFee,otDays,isDamage);
            });

            payb.addActionListener(e -> {
                String cardNumber = field1.getText();
                String expiry = field2.getText();
                String cvv = field3.getText();
                String holder = field4.getText();
                LocalDate dateNow = LocalDate.now();

                if (cardNumber.isEmpty() || expiry.isEmpty() || cvv.isEmpty()) { 
                    JOptionPane.showMessageDialog(frm, "Please fill all card information besides card holder.", "Error", JOptionPane.ERROR_MESSAGE);
                } else if(field2.getDate().isBefore(dateNow)){
                    JOptionPane.showMessageDialog(frm, "Card is expired please use another card.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    int ans = JOptionPane.showConfirmDialog(frm, "Pay this amount? " + "₱"+ String.format("%.2f", amountPaid), "Confirmation", JOptionPane.YES_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(ans==JOptionPane.YES_OPTION){
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
                        else{
                            JOptionPane.showMessageDialog(frm, "Operation Cancelled.", "Information", JOptionPane.INFORMATION_MESSAGE);
                        }
                    
                    
                }
            });

            pan.add(backBtn);
            pan.add(bBtnShadow);
            pan.add(CH);
            pan.add(PIN);
            pan.add(ED);
            pan.add(CN);
            pan.add(CHls);
            pan.add(PINls);
            pan.add(EDls);
            pan.add(CNls);
            pan.add(payb);
            pan.add(payment);
            pan.add(paymentls);
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
}
