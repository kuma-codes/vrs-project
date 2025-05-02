package logInUI;
import userUI.userLandingPageUI;
import adminUI.vehicleRental;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane;
import userUI.userLandingPageUI;

public class logInUI extends JFrame {
    private static final Font F1 = new Font("Arial", Font.BOLD, 19);
    private static final Font F2 = new Font("Arial", Font.BOLD, 16);
    private static final Font F3 = new Font("Arial", Font.BOLD, 12);
    private static final Color LBLUE = new Color(30,144,255);
    private static final Color DBLUE = new Color(71,112,139);

    public logInUI(){
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
        line1.setBackground(new Color(100, 149, 237)); 
        line1.setBounds(90, 0, 30, 640);

        JPanel line2 = new JPanel();
        line2.setBackground(new Color(100, 149, 237)); 
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
        eField.setFont(new Font("Arial", Font.BOLD, 10));
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

        JButton signUpBtn = new JButton("Sign-In.");
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
            if(eField.getText().equals("admin") && pField.getText().equals("123")){
                dispose();
                new vehicleRental();
            }
            else if(eField.getText().equals("test_user1") && pField.getText().equals("12345")){
                dispose();
                new userLandingPageUI();
            }
            else if(!eField.getText().equals("") && !pField.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Incorrect username or password");
            }
            else {
                JOptionPane.showMessageDialog(null, "Fields cannot be empty");
            }
        });

        signUpBtn.addActionListener(e1 -> {
            dispose();
            new registerUI();
        });

        pan.add(pageName);
        pan.add(logInPanel);
        pan.add(line1);
        pan.add(line2);
        add(pan);
        setVisible(true);
    }

    public static void main(String[] args) {
        new logInUI();
    }
}

