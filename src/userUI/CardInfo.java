package userUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

public class CardInfo {
    public CardInfo() {
        JFrame frm = new JFrame("Vehicle Rental System"); // frame
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setSize(900, 550);
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

        JTextField field1 = new JTextField(); // Card No.
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

        JLabel CH = new JLabel("Card Holder"); 
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

            if (cardNumber.isEmpty() || expiry.isEmpty() || cvv.isEmpty() || holder.isEmpty()) { 
                JOptionPane.showMessageDialog(frm, "Please fill all card information.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frm, "Payment Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                frm.dispose();
                new receipt(); 
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
    
     public static void main(String[] args) {
        new CardInfo();
    }
}
