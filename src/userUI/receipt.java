package userUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class receipt {
    public receipt() {

        JFrame frm = new JFrame("Vehicle Rental System");      // FRAME
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setSize(900, 640);
        frm.setLayout(null);
        frm.setLocationRelativeTo(null);
        

        JPanel pan = new JPanel();                              // PANEL
        pan.setBackground(new Color(196, 227, 244));
        pan.setBounds(0, 0, 900, 640);
        pan.setLayout(null);

        JPanel line1 = new JPanel();                            // 2 Lines
        line1.setBackground(new Color(100, 149, 237)); 
        line1.setBounds(80, 0, 30, 640);

        JPanel line2 = new JPanel();
        line2.setBackground(new Color(100, 149, 237)); 
        line2.setBounds(150, 0, 30, 640);
        
        JLabel receipt = new JLabel("RECEIPT", SwingConstants.CENTER);
        receipt.setForeground(new Color (39, 58, 87));
        receipt.setFont(new Font ("Arial", Font.BOLD,60));
        receipt.setBounds(0, 80, 900, 50);
        
        JButton done = new JButton("DONE");                     // Done button
        done.setForeground(Color.WHITE);
        done.setFont(new Font("Arial", Font.BOLD, 20));
        done.setBackground(new Color(71, 112, 139));
        done.setBounds(360, 510, 150, 30);
        
        JTextField lfield = new JTextField();                    // Text field
        lfield.setBackground(new Color(217, 217, 217));
        lfield.setBounds(30, 180, 820, 290);
        
        JLabel home1 = new JLabel("HOME");                      // HOME and BACK
        home1.setForeground(new Color(0, 0, 139));
        home1.setFont(new Font("Arial", Font.BOLD, 12));
        home1.setBounds(820, 560, 110, 40);
        
        JLabel back1 = new JLabel("BACK");
        back1.setForeground(new Color(0, 0, 139));
        back1.setFont(new Font("Arial", Font.BOLD, 12));
        back1.setBounds(10, 2, 110, 40);

        // eme
        JLabel mema  = new JLabel("Rental Company: VEHICLE RENTAL SYSTEM");
        JLabel mema2 = new JLabel("Receipt No.: 0002458");
        JLabel mema3 = new JLabel("Date Issued: April 14, 2025");

        mema.setForeground(Color.BLACK);
        mema.setFont(new Font("Arial", Font.BOLD, 11));
        mema.setBounds(80, 190, 280, 20);

        mema2.setForeground(Color.BLACK);
        mema2.setFont(new Font("Arial", Font.BOLD, 11));
        mema2.setBounds(80, 210, 280, 20);

        mema3.setForeground(Color.BLACK);
        mema3.setFont(new Font("Arial", Font.BOLD, 11));
        mema3.setBounds(80, 230, 280, 20);

        // Customer details
        JLabel mema4  = new JLabel("Customer Name:                             Juan Dela Cruz");
        JLabel mema5  = new JLabel("Car Brand:                                          Toyota");
        JLabel mema6  = new JLabel("Car Model:                                          Corolla");
        JLabel mema7  = new JLabel("Car Color:                                           Black");
        JLabel mema8  = new JLabel("Rental Start Date:                              April 10, 2025");
        JLabel mema9  = new JLabel("Rental End Date:                                April 13, 2025");
        JLabel mema10 = new JLabel("Total Rental Time:                            3 Days");
        JLabel mema11 = new JLabel("Daily Rate:                                           $50");
        JLabel mema12 = new JLabel("Total Payment:                                   $150");

        mema4.setForeground(Color.BLACK);
        mema4.setFont(new Font("Arial", Font.BOLD, 10));
        mema4.setBounds(280, 260, 280, 20);

        mema5.setForeground(Color.BLACK);
        mema5.setFont(new Font("Arial", Font.BOLD, 10));
        mema5.setBounds(280, 280, 280, 20);

        mema6.setForeground(Color.BLACK);
        mema6.setFont(new Font("Arial", Font.BOLD, 10));
        mema6.setBounds(280, 300, 280, 20);

        mema7.setForeground(Color.BLACK);
        mema7.setFont(new Font("Arial", Font.BOLD, 10));
        mema7.setBounds(280, 320, 280, 20);

        mema8.setForeground(Color.BLACK);
        mema8.setFont(new Font("Arial", Font.BOLD, 10));
        mema8.setBounds(280, 340, 280, 20);

        mema9.setForeground(Color.BLACK);
        mema9.setFont(new Font("Arial", Font.BOLD, 10));
        mema9.setBounds(280, 360, 280, 20);

        mema10.setForeground(Color.BLACK);
        mema10.setFont(new Font("Arial", Font.BOLD, 10));
        mema10.setBounds(280, 380, 280, 20);

        mema11.setForeground(Color.BLACK);
        mema11.setFont(new Font("Arial", Font.BOLD, 10));
        mema11.setBounds(280, 400, 280, 20);

        mema12.setForeground(Color.BLACK);
        mema12.setFont(new Font("Arial", Font.BOLD, 10));
        mema12.setBounds(280, 420, 280, 20);

           
        pan.add(mema12);
        pan.add(mema11);
        pan.add(mema10);
        pan.add(mema9);
        pan.add(mema8);
        pan.add(mema7);
        pan.add(mema6);
        pan.add(mema5);
        pan.add(mema4);
        pan.add(mema3);
        pan.add(mema2);
        pan.add(mema);
        pan.add(back1);
        pan.add(home1);
        pan.add(lfield);
        pan.add(done);
        pan.add(receipt);
        pan.add(line1);
        pan.add(line2);

        frm.add(pan);
        frm.setVisible(true);
    }

    public static void main(String[] args) {
        new receipt();
    }
}