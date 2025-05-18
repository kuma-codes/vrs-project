package adminUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import logInUI.LogInUI;

public class vehicleRental extends JFrame {

    private static final Font fontA = new Font("Arial", Font.BOLD, 18);
    private static final Font fontB = new Font("Arial", Font.BOLD, 14);
    private static final Color lblue = new Color(135, 206, 235);
    private static final Color dblue = new Color(132, 168, 230);
    private static final Color fontColor = Color.WHITE;
    private static final Color shadowColor = new Color(143,143,143);
    private static final Color btnBGColor = new Color(92,142,175);
    
    private static final Border outer = BorderFactory.createBevelBorder(BevelBorder.RAISED);
    private static final Border inner = BorderFactory.createBevelBorder(BevelBorder.RAISED);
    private static final Border btnBorder = BorderFactory.createCompoundBorder(outer,inner);

    public vehicleRental() {
        
        setTitle("Vejicle Rental System Admin");
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
        line1.setBackground(dblue);
        line1.setBounds(90, 0, 30, 640);

        JPanel line2 = new JPanel();
        line2.setBackground(dblue);
        line2.setBounds(150, 0, 30, 640);

        JLabel title = new JLabel("Vehicle Rental System", SwingConstants.CENTER);
        title.setForeground(fontColor);
        title.setFont(new Font ("Arial", Font.BOLD, 40));
        title.setBounds(0, 60, 900, 45);
        
        JLabel titleLS = new JLabel("Vehicle Rental System", SwingConstants.CENTER);
        titleLS.setForeground(shadowColor);
        titleLS.setFont(new Font ("Arial", Font.BOLD, 40));
        titleLS.setBounds(3, 63, 900, 45);

        JButton bookingBtn = new JButton("Booking Management");
        bookingBtn.setBounds(85, 150, 720, 70);
        bookingBtn.setFont(new Font("Arial", Font.BOLD, 21));
        bookingBtn.setForeground(fontColor);
        bookingBtn.setBackground(btnBGColor);
        bookingBtn.setBorder(btnBorder);

        JButton vehicleBtn = new JButton("Vehicle Management");
        vehicleBtn.setBounds(85, 240, 720, 70);
        vehicleBtn.setFont(new Font("Arial", Font.BOLD, 21));
        vehicleBtn.setForeground(fontColor);
        vehicleBtn.setBackground(btnBGColor);
        vehicleBtn.setBorder(btnBorder);

        JButton userBtn = new JButton("User Management"); 
        userBtn.setBounds(85, 330, 720, 70);
        userBtn.setFont(new Font("Arial", Font.BOLD, 21));
        userBtn.setForeground(fontColor);
        userBtn.setBackground(btnBGColor);
        userBtn.setBorder(btnBorder);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(85, 420, 720, 70);
        logoutBtn.setFont(new Font("Arial", Font.BOLD, 21));
        logoutBtn.setForeground(fontColor);
        logoutBtn.setBackground(btnBGColor);
        logoutBtn.setBorder(btnBorder);
        
        // ActionListeners
        bookingBtn.addActionListener(e -> {dispose(); new manageBookings();});
        vehicleBtn.addActionListener(e -> {dispose(); new vehicleManagement();});
        userBtn.addActionListener(e -> {dispose(); new userManagement();});
        logoutBtn.addActionListener(e -> {dispose(); new LogInUI();});

        pan.add(title);
        pan.add(titleLS);
        pan.add(bookingBtn);
        pan.add(vehicleBtn);
        pan.add(userBtn); 
        pan.add(logoutBtn);
        pan.add(line1);
        pan.add(line2);

        add(pan);
        setVisible(true);
    }

}