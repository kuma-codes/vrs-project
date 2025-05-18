package logInUI;

import adminUI.vehicleRental;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane;
import userUI.UserLandingPageUI;
import java.sql.*;
import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

public class LogInUI extends JFrame {
    
    private static final Font F3 = new Font("Arial", Font.BOLD, 12);
    private static final Color LBLUE = new Color(30,144,255);
    private static final Color DBLUE = new Color(71,112,139);
    private static final Color fontColor = Color.WHITE;
    private static final Color shadowColor = new Color(143,143,143);
    private static final Color fldBGColor = new Color(240, 240, 240);
    private static final Color btnBGColor = new Color(92,142,175);
    
    //Border
    private static final Border lineBorder = BorderFactory.createLineBorder(Color.GRAY,1);
    private static final Border empBorder = BorderFactory.createEmptyBorder(2,10,2,2);
    private static final Border Border = new CompoundBorder(lineBorder,empBorder);    
    
    // JDBC setup
    private Connection conn;
    private final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=vRentalSystemDB;encrypt=true;trustServerCertificate=true";
    private final String DB_USER = "admin";
    private final String DB_PASS = "admin456";

    public LogInUI(){
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 540);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);

        JPanel pan = new JPanel();
        pan.setBackground(new Color(196, 227, 244));
        pan.setBounds(0, 0, 900, 640);
        pan.setLayout(null);

        JPanel line1 = new JPanel();
        line1.setBackground(new Color(132, 168, 230));
        line1.setBounds(90, 0, 30, 640);

        JPanel line2 = new JPanel();
        line2.setBackground(new Color(132, 168, 230));
        line2.setBounds(150, 0, 30, 640);

        JLabel pageName = new JLabel("LOGIN", SwingConstants.CENTER);
        pageName.setForeground(fontColor);
        pageName.setFont(new Font ("Arial", Font.BOLD,60));
        pageName.setBounds(0, 70, 900, 50);
        
        JLabel pageName1 = new JLabel("LOGIN", SwingConstants.CENTER);
        pageName1.setForeground(shadowColor);
        pageName1.setFont(new Font ("Arial", Font.BOLD,60));
        pageName1.setBounds(3, 73, 900, 50);

        // No layout manager
        JPanel logInPanel = new JPanel();
        logInPanel.setLayout(null);
        logInPanel.setBounds(40, 100, 900, 700);
        logInPanel.setOpaque(false);

        JLabel eLabel = new JLabel("Email:");
        eLabel.setFont(new Font("Arial", Font.BOLD, 16));
        eLabel.setForeground(fontColor);
        eLabel.setBounds(100, 50, 300, 35);
        
        JLabel eLS = new JLabel("Email:");
        eLS.setFont(new Font("Arial", Font.BOLD, 16));
        eLS.setForeground(shadowColor);
        eLS.setBounds(102, 52, 300, 35);

        JTextField eField = new JTextField();
        eField.setFont(F3);
        eField.setBounds(100, 80, 600, 40);
        eField.setBackground(fldBGColor);
        eField.setBorder(Border);

        JLabel pLabel = new JLabel("Password");
        pLabel.setFont(new Font("Arial", Font.BOLD, 16));
        pLabel.setForeground(fontColor);
        pLabel.setBounds(100, 130, 300, 35);
        
        JLabel pLS = new JLabel("Password");
        pLS.setFont(new Font("Arial", Font.BOLD, 16));
        pLS.setForeground(shadowColor);
        pLS.setBounds(102, 132, 300, 35);

        JPasswordField pField = new JPasswordField();
        pField.setFont(F3);
        pField.setBounds(100, 160, 600, 40);
        pField.setBackground(fldBGColor);
        pField.setBorder(Border);

        JButton logInBtn = new JButton("Log-In");
        logInBtn.setBounds (100, 250, 600, 40);
        logInBtn.setFont(new Font("Arial", Font.BOLD, 16));
        logInBtn.setBackground(btnBGColor);
        logInBtn.setForeground(Color.WHITE);
        logInBtn.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

        JLabel rLabel = new JLabel("Don't have an account?", SwingConstants.RIGHT);
        rLabel.setFont(F3);
        rLabel.setForeground(fontColor);
        rLabel.setBounds(90, 205, 150, 25);
        
        JLabel rLS = new JLabel("Don't have an account?", SwingConstants.RIGHT);
        rLS.setFont(F3);
        rLS.setForeground(shadowColor);
        rLS.setBounds(91, 206, 150, 25);

        JButton signUpBtn = new JButton("<html><u>Sign-Up.</u><html>");
        signUpBtn.setFont(F3);
        signUpBtn.setBackground(new Color(135, 206, 235));
        signUpBtn.setForeground(btnBGColor);
        signUpBtn.setOpaque(false);
        signUpBtn.setBorder(null);
        signUpBtn.setBounds(640, 205, 60, 25);
        
        JLabel signUpBS = new JLabel("<html><u>Sign-Up.</u><html>");
        signUpBS.setFont(F3);
        signUpBS.setForeground(Color.WHITE);
        signUpBS.setBounds(646, 206, 60, 25);

        logInPanel.add(eLabel);        
        logInPanel.add(eField);
        logInPanel.add(pLabel);
        logInPanel.add(pField);
        logInPanel.add(eLS); 
        logInPanel.add(pLS);
        logInPanel.add(logInBtn);
        logInPanel.add(rLabel);
        logInPanel.add(signUpBtn);
        logInPanel.add(rLS);
        logInPanel.add(signUpBS);

        logInBtn.addActionListener(e -> {
            if(eField.getText().isBlank()){
                JOptionPane.showMessageDialog(null, "Fields cannot be empty", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                connectToDB();

                // Get account where email matches case-insensitively
                String loginQuery = "SELECT AccountID, Email, AccountPassword, AccountRole FROM ACCOUNT WHERE Email = ?";
                PreparedStatement p = conn.prepareStatement(loginQuery);
                p.setString(1, eField.getText().trim());

                ResultSet rs = p.executeQuery();

                boolean found = false;
                
                while (rs.next()) {
                    
                    if (rs.getString("Email").equals(eField.getText().toLowerCase().trim())) {
                        found = true;
                        if (!rs.getString("AccountPassword").equals(pField.getText())) {
                            JOptionPane.showMessageDialog(null, "Incorrect Password", "Warning", JOptionPane.WARNING_MESSAGE);
                        } else {
                            if (rs.getString("AccountRole").equals("Admin")) {
                                dispose();
                                new vehicleRental();
                                closeConnection();
                            } else {
                                dispose();
                                new UserLandingPageUI(rs.getString("AccountID"));
                                closeConnection();
                            }
                        }
                        break;
                    }
                }
                    if(!found){
                        JOptionPane.showMessageDialog(null, "Email not found.", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
            } catch (SQLException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage());
            }
            
        });

        signUpBtn.addActionListener(e1 -> {
            dispose();
            new RegisterUI();
        });

        pan.add(pageName);
        pan.add(pageName1);
        pan.add(logInPanel);
        pan.add(line1);
        pan.add(line2);
        add(pan);
        setVisible(true);
        
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

