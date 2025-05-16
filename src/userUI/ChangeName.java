package userUI;

import javax.swing.*;
import java.awt.*;
import java.sql.*; 
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

public class ChangeName extends JFrame {

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
    private String password = "";

    public ChangeName(String accID) {

        setTitle("Change Name");
        setSize(900, 440);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setLocationRelativeTo(null);
        setLayout(null);

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

        //backBtn
        JButton backBtn = new JButton("<");
        backBtn.setBounds(20, 20, 60, 40);
        backBtn.setForeground(fontColor);
        backBtn.setFocusPainted(false);
        backBtn.setBackground(Color.WHITE);
        backBtn.setOpaque(false);
        backBtn.setBorderPainted(false);
        backBtn.setFont(F1);        
        pan.add(backBtn);

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
        pan.add(bBtnShadow);        

        
        
        JLabel changeNameLbl = new JLabel("Change Name",SwingConstants.CENTER);
        changeNameLbl.setForeground(fontColor);
        changeNameLbl.setFont(F1);
        changeNameLbl.setBounds(-10, 50, 900, 50);        
        pan.add(changeNameLbl);

        JLabel changeNameLblLS = new JLabel("Change Name",SwingConstants.CENTER);
        changeNameLblLS.setForeground(shadowColor);
        changeNameLblLS.setFont(F1);
        changeNameLblLS.setBounds(-7, 50, 900, 50);
        pan.add(changeNameLblLS);

        JLabel fNameLbl = new JLabel("First Name:");
        fNameLbl.setFont(new Font("Arial", Font.PLAIN, 18));
        fNameLbl.setBounds(200, 120, 200, 30);
        pan.add(fNameLbl);

        JTextField fNameFld = new JTextField();
        fNameFld.setFont(new Font("Arial", Font.PLAIN, 18));
        fNameFld.setBounds(200, 150, 500, 30);
        pan.add(fNameFld);

        JLabel lNameLbl = new JLabel("Last Name:");
        lNameLbl.setFont(new Font("Arial", Font.PLAIN, 18));
        lNameLbl.setBounds(200, 190, 200, 30);
        pan.add(lNameLbl);

        JTextField lNameFld = new JTextField();
        lNameFld.setFont(new Font("Arial", Font.PLAIN, 18));
        lNameFld.setBounds(200, 220, 500, 30);
        pan.add(lNameFld);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        passwordLabel.setBounds(200, 260, 200, 30);
        pan.add(passwordLabel);
        
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 18));
        passwordField.setBounds(200, 290, 500, 30);
        pan.add(passwordField);
        
        JButton saveBtn = new JButton("Save Changes");
        saveBtn.setForeground(fontColor);
        saveBtn.setFont(F3);
        saveBtn.setBackground(btnBGColor);
        saveBtn.setBorder(Border);
        saveBtn.setBounds(370, 330, 150, 30);
        pan.add(saveBtn);
        
        //ActionListeners
        backBtn.addActionListener(e -> {dispose(); new Settings(accID);});
        saveBtn.addActionListener(e -> {
            
            try{
                connectToDB();
                String findAccount = "SELECT AccountPassword FROM ACCOUNT WHERE AccountID = ? ;";
                PreparedStatement find = conn.prepareStatement(findAccount);
                find.setString(1,accID);
                ResultSet rs = find.executeQuery();
                
                if(rs.next()){
                    password = rs.getString("AccountPassword");
                }
            }
            catch(SQLException e1){
                JOptionPane.showMessageDialog(this, "Database could not load.");
            }
            
            if(password.equals(new String(passwordField.getPassword()))){
                try{
                    int ans = JOptionPane.showConfirmDialog(this, "Save Changes?","Confirmation", JOptionPane.YES_NO_OPTION);
                    if(ans == JOptionPane.YES_OPTION){
                        connectToDB();
                        String updateName = "UPDATE ACCOUNT SET FName = ? , LName = ? WHERE AccountID = ? ;";
                        PreparedStatement upd = conn.prepareStatement(updateName);
                        upd.setString(1, fNameFld.getText().trim());
                        upd.setString(2, lNameFld.getText().trim());
                        upd.setString(3, accID);
                        upd.executeUpdate();
                        JOptionPane.showMessageDialog(this, "Name Succesfully updated!", "Information", JOptionPane.INFORMATION_MESSAGE);
                        upd.close();
                        closeConnection();
                        dispose();
                        new Settings(accID);
                    }
                }
                    catch(SQLException e1){
                        JOptionPane.showMessageDialog(this,"Could not change name", "Error", JOptionPane.ERROR_MESSAGE);
                    }
            }
                else{
                    JOptionPane.showMessageDialog(this, "The password you entered is incorrect", "Information", JOptionPane.WARNING_MESSAGE);
                }
                
        });

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
    }
}
