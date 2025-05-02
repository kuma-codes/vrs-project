package userUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

public class Payment {
    public Payment() { 
        JFrame frm = new JFrame("Vehicle Rental System"); // frame
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setSize(900, 560);
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

        JLabel payment = new JLabel("PAYMENT", SwingConstants.CENTER); // label for Payment title
        payment.setForeground(new Color (39, 58, 87));
        payment.setFont(new Font ("Arial", Font.BOLD, 40));
        payment.setBounds(0, 60, 900, 30);

        JTextField field1 = new JTextField(); // first;
        field1.setBounds(100, 160, 660, 30);

        JTextField field2 = new JTextField(); // second
        field2.setBounds(100, 230, 660, 30);

        JTextField field3 = new JTextField(); // third
        field3.setBounds(100, 300, 660, 30);

        String[] mode = { "CASH", "CARD" }; // jlist for mode of Payment
        JList<String> sfield1 = new JList<>(mode);
        JScrollPane payy = new JScrollPane(sfield1);
        payy.setBackground(new Color(217, 217, 217));
        payy.setBounds(100, 370, 150, 40);

        JTextField sfield2 = new JTextField(); // total textfield
        sfield2.setBounds(290, 370, 150, 40);

        JButton payb = new JButton("PROCEED"); // button
        payb.setBounds(360, 450, 150, 30);

        payb.addActionListener(e -> {
            String selected = sfield1.getSelectedValue();
            if (selected == null) {
                JOptionPane.showMessageDialog(frm, "Please select a mode of payment.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if ("CASH".equals(selected)) {
                JOptionPane.showMessageDialog(frm, "Please pay at the counter.", "Payment Information", JOptionPane.INFORMATION_MESSAGE);
            } else if ("CARD".equals(selected)) {
                frm.dispose(); 
                CardInfo.main(null); //change the frame into CardInfo
            }
        });

        JLabel rentalf = new JLabel("RENTAL FEE"); // label for first
        rentalf.setFont(new Font("Arial", Font.BOLD, 15));
        rentalf.setForeground(new Color (39, 58, 87));
        rentalf.setBounds(100, 130, 300, 35);

        JLabel OTF = new JLabel("OT FEE"); // label for second
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

        JLabel home = new JLabel("HOME"); // home
        home.setForeground(new Color(0, 0, 139));
        home.setFont(new Font("Arial", Font.BOLD, 12));
        home.setBounds(830, 485, 110, 40);

        JLabel back = new JLabel("BACK"); // back
        back.setForeground(new Color(0, 0, 139));
        back.setFont(new Font("Arial", Font.BOLD, 12));
        back.setBounds(10, 2, 110, 40);

        pan.add(back);
        pan.add(home);
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

    public static void main(String[] args) {
        new Payment(); 
    }
}