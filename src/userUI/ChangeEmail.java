package userUI;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.sql.*;

public class ChangeEmail extends JFrame {
    //fonts and colors used that has rgb values
    private static final Font F1 = new Font("Arial", Font.BOLD, 40);
    private static final Font F3 = new Font("Arial", Font.BOLD, 16);
    
    //Colors
    private static final Color fontColor = Color.WHITE;
    private static final Color shadowColor = new Color(143,143,143);
    private static final Color btnBGColor = new Color(92,142,175);
    private static final Color fldBGColor = new Color(240, 240, 240);
    
    //Border
    private static final Border lineBorder = BorderFactory.createLineBorder(Color.GRAY,1);
    private static final Border empBorder = BorderFactory.createEmptyBorder(2,8,2,8);
    private static final Border bevelBorder = BorderFactory.createBevelBorder(BevelBorder.RAISED);
    private static final Border Border1 = new CompoundBorder(lineBorder,bevelBorder);
    private static final Border Border = new CompoundBorder(lineBorder,empBorder);
    
    // JDBC setup
    private Connection conn;
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=vRentalSystemDB;encrypt=true;trustServerCertificate=true";
    private static final String DB_USER = "admin";
    private static final String DB_PASS = "admin456";
    private String email = "";
    private String password = "";
    
    public ChangeEmail(String accID) {
        setTitle("Change Email");
        setSize(900, 440);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);
        
        try{
            connectToDB();
            String findEmail = "SELECT Email, AccountPassword FROM ACCOUNT WHERE AccountID = ? ;";
            PreparedStatement find = conn.prepareStatement(findEmail);
            find.setString(1,accID);
            ResultSet rs = find.executeQuery();
            
            if(rs.next()){
                email = rs.getString("Email");
                password = rs.getString("AccountPassword");
            }
            closeConnection();
            find.close();
            rs.close();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Database could not load.");
        }
        JPanel pan = new JPanel();
        pan.setBackground(new Color(196, 227, 244));
        pan.setBounds(0, 0, 900, 540);
        pan.setLayout(null);

        JPanel line1 = new JPanel();
        line1.setBackground(new Color(132, 168, 230));
        line1.setBounds(90, 0, 30, 540);

        JPanel line2 = new JPanel();
        line2.setBackground(new Color(132, 168, 230));
        line2.setBounds(150, 0, 30, 540);

        JLabel changeEmailLbl = new JLabel("Change Email",SwingConstants.CENTER);
        changeEmailLbl.setForeground(fontColor);
        changeEmailLbl.setFont(F1);
        changeEmailLbl.setBounds(-10, 40, 900, 50);
        pan.add(changeEmailLbl);

        JLabel changeEmailLblLS = new JLabel("Change Email",SwingConstants.CENTER);
        changeEmailLblLS.setForeground(shadowColor);
        changeEmailLblLS.setFont(F1);
        changeEmailLblLS.setBounds(-7, 43, 900, 50);
        pan.add(changeEmailLblLS);

        JLabel currentEmailLabel = new JLabel("Current Email:");
        currentEmailLabel.setFont(new Font("Arial", Font.BOLD, 18));
        currentEmailLabel.setBounds(200, 100, 200, 30);
        currentEmailLabel.setForeground(fontColor);
        pan.add(currentEmailLabel);
        
        JLabel currentEmailLS = new JLabel("Current Email:");
        currentEmailLS.setFont(new Font("Arial", Font.BOLD, 18));
        currentEmailLS.setBounds(202, 102, 200, 30);
        currentEmailLS.setForeground(shadowColor);
        pan.add(currentEmailLS);

        JTextField currentEmailField = new JTextField(email);
        currentEmailField.setFont(new Font("Arial", Font.PLAIN, 18));
        currentEmailField.setBounds(200, 130, 500, 35);
        currentEmailField.setEditable(false);
        currentEmailField.setBackground(fldBGColor);
        currentEmailField.setBorder(Border);
        pan.add(currentEmailField);

        JLabel newEmailLabel = new JLabel("New Email:");
        newEmailLabel.setFont(new Font("Arial", Font.BOLD, 18));
        newEmailLabel.setBounds(200, 170, 200, 30);
        newEmailLabel.setForeground(fontColor);
        pan.add(newEmailLabel);
        
        JLabel newEmailLS = new JLabel("New Email:");
        newEmailLS.setFont(new Font("Arial", Font.BOLD, 18));
        newEmailLS.setBounds(202, 172, 200, 30);
        newEmailLS.setForeground(shadowColor);
        pan.add(newEmailLS);

        JTextField newEmailField = new JTextField();
        newEmailField.setFont(new Font("Arial", Font.PLAIN, 18));
        newEmailField.setBounds(200, 200, 500, 35);
        newEmailField.setBackground(fldBGColor);
        newEmailField.setBorder(Border);
        pan.add(newEmailField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 18));
        passwordLabel.setBounds(200, 240, 200, 30);
        passwordLabel.setForeground(fontColor);
        pan.add(passwordLabel);
        
        JLabel passwordLS = new JLabel("Password:");
        passwordLS.setFont(new Font("Arial", Font.BOLD, 18));
        passwordLS.setBounds(202, 242, 200, 30);
        passwordLS.setForeground(shadowColor);
        pan.add(passwordLS);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 18));
        passwordField.setBounds(200, 270, 500, 35);
        passwordField.setBackground(fldBGColor);
        passwordField.setBorder(Border);
        pan.add(passwordField);

        JButton saveBtn = new JButton("Save Changes");
        saveBtn.setForeground(fontColor);
        saveBtn.setFont(F3);
        saveBtn.setBackground(btnBGColor);
        saveBtn.setBorder(Border1);
        saveBtn.setBounds(370, 330, 150, 35);
        
        //backBtn
        JButton backBtn = new JButton("<");
        backBtn.setBounds(20, 20, 60, 40);
        backBtn.setForeground(fontColor);
        backBtn.setFocusPainted(false);
        backBtn.setBackground(Color.WHITE);
        backBtn.setOpaque(false);
        backBtn.setBorderPainted(false);
        backBtn.setFont(F1);
        //backBtn shadow
        
        JButton bBtnShadow = new JButton("<");
        bBtnShadow.setBounds(23, 24, 60, 40);
        bBtnShadow.setForeground(shadowColor);
        bBtnShadow.setFocusPainted(false);
        bBtnShadow.setBackground(Color.WHITE);
        bBtnShadow.setEnabled(false);
        bBtnShadow.setOpaque(false);
        bBtnShadow.setBorderPainted(false);
        bBtnShadow.setFont(F1);
        
        
        pan.add(saveBtn);
        pan.add(backBtn);
        pan.add(bBtnShadow);
        pan.add(line1);
        pan.add(line2);
        add(pan);


        saveBtn.addActionListener(e -> {
            if(password.equals(new String(passwordField.getPassword()))){
                if(email.equals(newEmailField.getText().trim())){
                    JOptionPane.showMessageDialog(this, "You're changing your email silly!");
                }
                else{
                    if(newEmailField.getText().trim().contains("@") && newEmailField.getText().trim().contains(".com")){
                        try{
                            connectToDB();
                            int ans = JOptionPane.showConfirmDialog(this, "Save Changes?","Confirmation", JOptionPane.YES_NO_OPTION);
                            if(ans == JOptionPane.YES_OPTION){
                                String update = "UPDATE ACCOUNT SET Email = ? WHERE AccountID = ? ;";
                                PreparedStatement updEmail = conn.prepareStatement(update);
                                updEmail.setString(1,newEmailField.getText().toLowerCase().trim());
                                updEmail.setString(2, accID);
                                updEmail.executeUpdate();
                                JOptionPane.showMessageDialog(this, "Email Succesfully updated!", "Information", JOptionPane.INFORMATION_MESSAGE);
                                dispose();
                                new Settings(accID);
                                updEmail.close();
                                closeConnection();
                            }
                            
                            
                        }
                        catch(SQLException e1){
                            JOptionPane.showMessageDialog(this, "This email is already used, please use another one.","Information", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(this, "Please enter a valid email address.","Information", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
                else{
                    JOptionPane.showMessageDialog(this, "The password you entered is incorrect", "Information", JOptionPane.WARNING_MESSAGE);
                }

        });

        backBtn.addActionListener(e -> {dispose(); new Settings(accID);});
        
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
