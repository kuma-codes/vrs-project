package adminUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import logInUI.LogInUI;

public class vehicleRental extends JFrame {


    private static final Color DBLUE = new Color(100, 149, 237);

    public vehicleRental() {
        setTitle("vrs");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 620);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);

        JPanel pan = new JPanel();
        pan.setBackground(new Color(196, 227, 244));
        pan.setBounds(0, 0, 910, 640);
        pan.setLayout(null);

        JPanel line1 = new JPanel();
        line1.setBackground(DBLUE);
        line1.setBounds(90, 0, 30, 640);

        JPanel line2 = new JPanel();
        line2.setBackground(DBLUE);
        line2.setBounds(150, 0, 30, 640);

        JLabel title = new JLabel("Vehicle Rental System", SwingConstants.CENTER);
        title.setForeground(new Color (39, 58, 87));
        title.setFont(new Font ("Arial", Font.BOLD, 40));
        title.setBounds(0, 60, 900, 30);

        JButton bookingBtn = new JButton("Booking Management");
        bookingBtn.setBounds(80, 140, 740, 70);
        bookingBtn.setFont(new Font("Arial", Font.BOLD, 20));

        JButton vehicleBtn = new JButton("Vehicle Management");
        vehicleBtn.setBounds(80, 230, 740, 70);
        vehicleBtn.setFont(new Font("Arial", Font.BOLD, 20));

        JButton userBtn = new JButton("User Management"); 
        userBtn.setBounds(80, 320, 740, 70);
        userBtn.setFont(new Font("Arial", Font.BOLD, 20));

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(80, 410, 740, 70);
        logoutBtn.setFont(new Font("Arial", Font.BOLD, 20));
        
        // ActionListeners
        bookingBtn.addActionListener(e -> {dispose(); new manageBookings();});
        vehicleBtn.addActionListener(e -> {dispose(); new vehicleManagement();});
        userBtn.addActionListener(e -> {dispose(); new userManagement();});
        logoutBtn.addActionListener(e -> {dispose(); new LogInUI();});

        pan.add(title);
        pan.add(bookingBtn);
        pan.add(vehicleBtn);
        pan.add(userBtn); 
        pan.add(logoutBtn);
        pan.add(line1);
        pan.add(line2);

        add(pan);
        setVisible(true);
    }

    public static void main(String[] args) {
        new vehicleRental();
    }
}