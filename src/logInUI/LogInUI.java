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

public class LogInUI extends JFrame {
    
    private static final Font F3 = new Font("Arial", Font.BOLD, 12);
    private static final Color LBLUE = new Color(30,144,255);
    private static final Color DBLUE = new Color(71,112,139);
    
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
        line1.setBackground(DBLUE); 
        line1.setBounds(90, 0, 30, 640);

        JPanel line2 = new JPanel();
        line2.setBackground(DBLUE); 
        line2.setBounds(150, 0, 30, 640);

        JLabel pageName = new JLabel("LOGIN", SwingConstants.CENTER);
        pageName.setForeground(new Color (39, 58, 87));
        pageName.setFont(new Font ("Arial", Font.BOLD,60));
        pageName.setBounds(0, 80, 900, 50);

        // No layout manager
        JPanel logInPanel = new JPanel();
        logInPanel.setLayout(null);
        logInPanel.setBounds(40, 130, 900, 300);
        logInPanel.setOpaque(false);

        JLabel eLabel = new JLabel("Email:");
        eLabel.setFont(new Font("Arial", Font.BOLD, 15));
        eLabel.setForeground(new Color (39, 58, 87));
        eLabel.setBounds(100, 50, 300, 35);

        JTextField eField = new JTextField();
        eField.setFont(F3);
        eField.setBounds(100, 80, 600, 40);

        JLabel pLabel = new JLabel("Password");
        pLabel.setFont(new Font("Arial", Font.BOLD, 15));
        pLabel.setForeground(new Color (39, 58, 87));
        pLabel.setBounds(100, 120, 300, 35);

        JPasswordField pField = new JPasswordField();
        pField.setFont(F3);
        pField.setBounds(100, 150, 600, 40);

        JButton logInBtn = new JButton("Log-In");
        logInBtn.setBounds (100, 220, 600, 35);
        logInBtn.setBackground(LBLUE);
        logInBtn.setForeground(Color.WHITE);

        JLabel rLabel = new JLabel("Don't have an account?", SwingConstants.RIGHT);
        rLabel.setFont(F3);
        rLabel.setForeground(Color.WHITE);
        rLabel.setBounds(90, 190, 150, 25);

        JButton signUpBtn = new JButton("Sign-Up.");
        signUpBtn.setFont(F3);
        signUpBtn.setBackground(new Color(135, 206, 235));
        signUpBtn.setForeground(Color.BLACK);
        signUpBtn.setOpaque(false);
        signUpBtn.setBorder(null);
        signUpBtn.setBounds(640, 190, 60, 25);

        logInPanel.add(eLabel);        
        logInPanel.add(eField);
        logInPanel.add(pLabel);
        logInPanel.add(pField);
        logInPanel.add(logInBtn);
        logInPanel.add(rLabel);
        logInPanel.add(signUpBtn);

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
                    String dbEmail = rs.getString("Email");
                    String inputEmail = eField.getText().trim();

                    // Perform case-sensitive check in Java
                    if (dbEmail.equals(inputEmail)) {
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

                if (!found) {
                    JOptionPane.showMessageDialog(null, "Email not found", "Warning", JOptionPane.WARNING_MESSAGE);
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

    public static void main(String[] args) {
        new LogInUI();
    }
}

