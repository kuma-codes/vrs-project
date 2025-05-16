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
    
    //Border
    private static final Border lineBorder = BorderFactory.createLineBorder(Color.GRAY,1);
    private static final Border bevelBorder = BorderFactory.createBevelBorder(BevelBorder.RAISED);
    private static final Border Border = new CompoundBorder(lineBorder,bevelBorder);
    
    // JDBC setup
    private Connection conn;
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=vRentalSystemDB;encrypt=true;trustServerCertificate=true";
    private static final String DB_USER = "admin";
    private static final String DB_PASS = "admin456";
    private String email = "";
    private String password = "";
    
    public ChangeEmail(String accID) {
        setTitle("Change Email");
        setSize(900, 480);
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
        line1.setBackground(new Color(100, 149, 237));
        line1.setBounds(90, 0, 30, 540);

        JPanel line2 = new JPanel();
        line2.setBackground(new Color(100, 149, 237));
        line2.setBounds(150, 0, 30, 540);

                

        JLabel changeEmailLbl = new JLabel("Change Email",SwingConstants.CENTER);
        changeEmailLbl.setForeground(fontColor);
        changeEmailLbl.setFont(F1);
        changeEmailLbl.setBounds(-10, 50, 900, 50);
        pan.add(changeEmailLbl);

        JLabel changeEmailLblLS = new JLabel("Change Email",SwingConstants.CENTER);
        changeEmailLblLS.setForeground(shadowColor);
        changeEmailLblLS.setFont(F1);
        changeEmailLblLS.setBounds(-7, 50, 900, 50);
        pan.add(changeEmailLblLS);

        JLabel currentEmailLabel = new JLabel("Current Email:");
        currentEmailLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        currentEmailLabel.setBounds(200, 120, 200, 30);
        pan.add(currentEmailLabel);

        JTextField currentEmailField = new JTextField(email);
        currentEmailField.setFont(new Font("Arial", Font.PLAIN, 18));
        currentEmailField.setBounds(200, 150, 500, 30);
        currentEmailField.setEditable(false);
        pan.add(currentEmailField);

        JLabel newEmailLabel = new JLabel("New Email:");
        newEmailLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        newEmailLabel.setBounds(200, 190, 200, 30);
        pan.add(newEmailLabel);

        JTextField newEmailField = new JTextField();
        newEmailField.setFont(new Font("Arial", Font.PLAIN, 18));
        newEmailField.setBounds(200, 220, 500, 40);
        pan.add(newEmailField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        passwordLabel.setBounds(200, 270, 200, 30);
        pan.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 18));
        passwordField.setBounds(200, 300, 500, 40);
        pan.add(passwordField);

        JButton saveBtn = new JButton("Save Changes");
        saveBtn.setForeground(fontColor);
        saveBtn.setFont(F3);
        saveBtn.setBackground(btnBGColor);
        saveBtn.setBorder(Border);
        saveBtn.setBounds(370, 360, 150, 30);
        
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
                                updEmail.setString(1,newEmailField.getText().trim());
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
    public static void main(String[] args) {
        new ChangeEmail("U2");
    }
}
