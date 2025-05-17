package logInUI;

import java.awt.Color;
import java.awt.Font;
import java.sql.*;
import java.text.ParseException;
import java.time.LocalDate;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.text.MaskFormatter;


public class RegisterUI extends JFrame {
    
    private static final Font F1 = new Font("Arial", Font.BOLD, 19);
    private static final Font F2 = new Font("Arial", Font.BOLD, 16);
    private static final Font F3 = new Font("Arial", Font.BOLD, 12);
    private static final Color LBLUE = new Color(30,144,255);
    private static final Color shadowColor = new Color(143,143,143);
    private static final Color fldBGColor = new Color(240, 240, 240);
    private static final Color btnBGColor = new Color(92,142,175);
    
    //Border
    private static final Border lineBorder = BorderFactory.createLineBorder(Color.GRAY,1);
    private static final Border empBorder = BorderFactory.createEmptyBorder(2,10,2,2);
    private static final Border Border = new CompoundBorder(lineBorder,empBorder);
    
     //to count the uservalues to auto assign AccountID
    // JDBC setup
    private Connection conn;
    private final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=vRentalSystemDB;encrypt=true;trustServerCertificate=true";
    private final String DB_USER = "admin";
    private final String DB_PASS = "admin456";

    public RegisterUI(){
        
        setTitle("Register");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 680);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);
        MaskFormatter licenseMask = null;
        MaskFormatter numberMask = null;
        
        try{
        licenseMask = new MaskFormatter("U##-##-######");
        licenseMask.setPlaceholderCharacter('_');
        numberMask = new MaskFormatter("09#########");
        numberMask.setPlaceholderCharacter('_');
        }catch(ParseException e){
        
        }
        JPanel pan = new JPanel();
        pan.setBackground(new Color(196, 227, 244));
        pan.setBounds(0, 0, 900, 680);
        pan.setLayout(null);

        JPanel line1 = new JPanel();
        line1.setBackground(new Color(132, 168, 230));
        line1.setBounds(90, 0, 30, 680);

        JPanel line2 = new JPanel();
        line2.setBackground(new Color(132, 168, 230));
        line2.setBounds(150, 0, 30, 680);

        JLabel pageName = new JLabel("REGISTER", SwingConstants.CENTER);
        pageName.setForeground(Color.WHITE);
        pageName.setFont(new Font ("Arial", Font.BOLD, 50));
        pageName.setBounds(0, 30, 900, 50);
        
        JLabel pgNLS = new JLabel("REGISTER", SwingConstants.CENTER);
        pgNLS.setForeground(shadowColor);
        pgNLS.setFont(new Font ("Arial", Font.BOLD, 50));
        pgNLS.setBounds(3, 33, 900, 50);

        JPanel regPanel = new JPanel(null);
        regPanel.setBounds(40, 60, 900, 740);
        regPanel.setOpaque(false);

        JLabel fLabel = new JLabel("First Name:");
        fLabel.setFont(new Font("Arial", Font.BOLD, 16));
        fLabel.setForeground(Color.WHITE);
        fLabel.setBounds(100, 25, 300, 35);
        
        JLabel fLS = new JLabel("First Name:");
        fLS.setFont(new Font("Arial", Font.BOLD, 16));
        fLS.setForeground(shadowColor);
        fLS.setBounds(102, 27, 300, 35);
        
        JTextField fField = new JTextField();
        fField.setFont(F3);
        fField.setBounds(100, 55, 600, 35);
        fField.setBorder(Border);
        fField.setBackground(fldBGColor);

        JLabel lLabel = new JLabel("Last Name:");
        lLabel.setFont(new Font("Arial", Font.BOLD, 16));
        lLabel.setForeground(Color.WHITE);
        lLabel.setBounds(100, 90, 300, 35);
        
        JLabel lLS = new JLabel("Last Name:");
        lLS.setFont(new Font("Arial", Font.BOLD, 16));
        lLS.setForeground(shadowColor);
        lLS.setBounds(102, 92, 300, 35);
        
        JTextField lField = new JTextField();
        lField.setFont(F3);
        lField.setBounds(100, 120, 600, 35);
        lField.setBorder(Border);
        lField.setBackground(fldBGColor);

        JLabel licenseLabel = new JLabel("Drivers License ID No.: (e.g. L12-32-321321)");
        licenseLabel.setFont(new Font("Arial", Font.BOLD, 16));
        licenseLabel.setForeground(Color.WHITE);
        licenseLabel.setBounds(100, 155, 500, 35);
        
        JLabel licenseLS = new JLabel("Drivers License ID No.: (e.g. L12-32-321321)");
        licenseLS.setFont(new Font("Arial", Font.BOLD, 16));
        licenseLS.setForeground(shadowColor);
        licenseLS.setBounds(102, 157, 500, 35);

        JFormattedTextField licenseField = new JFormattedTextField(licenseMask);
        licenseField.setFont(F3);
        licenseField.setBounds(100, 185, 600, 35);
        licenseField.setBorder(Border);
        licenseField.setBackground(fldBGColor);

        JLabel eLabel = new JLabel("Email: (e.g. 123@example.com)");
        eLabel.setFont(new Font("Arial", Font.BOLD, 16));
        eLabel.setForeground(Color.WHITE);
        eLabel.setBounds(100, 220, 300, 35);
        
        JLabel eLS = new JLabel("Email: (e.g. 123@example.com)");
        eLS.setFont(new Font("Arial", Font.BOLD, 16));
        eLS.setForeground(shadowColor);
        eLS.setBounds(102, 222, 300, 35);
        
        JTextField eField = new JTextField();
        eField.setFont(F3);
        eField.setBounds(100, 250, 600, 35);
        eField.setBorder(Border);
        eField.setBackground(fldBGColor);

        JLabel nLabel = new JLabel("Phone Number:");
        nLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nLabel.setForeground(Color.WHITE);
        nLabel.setBounds(100, 285, 300, 35);
        
        JLabel nLS = new JLabel("Phone Number:");
        nLS.setFont(new Font("Arial", Font.BOLD, 16));
        nLS.setForeground(shadowColor);
        nLS.setBounds(102, 287, 300, 35);
        
        JFormattedTextField nField = new JFormattedTextField(numberMask);
        nField.setFont(F3);
        nField.setBounds(100, 315, 600, 35);
        nField.setBorder(Border);
        nField.setBackground(fldBGColor);

        JLabel pLabel = new JLabel("Password:");
        pLabel.setFont(new Font("Arial", Font.BOLD, 16));
        pLabel.setForeground(Color.WHITE);
        pLabel.setBounds(100, 350, 300, 35);
        
        JLabel pLS = new JLabel("Password:");
        pLS.setFont(new Font("Arial", Font.BOLD, 16));
        pLS.setForeground(shadowColor);
        pLS.setBounds(102, 352, 300, 35);
        
        JPasswordField pField = new JPasswordField();
        pField.setFont(F3);
        pField.setBounds(100, 380, 600, 35);
        pField.setBorder(Border);
        pField.setBackground(fldBGColor);

        JLabel confPLabel = new JLabel("Confirm Password:");
        confPLabel.setFont(new Font("Arial", Font.BOLD, 16));
        confPLabel.setForeground(Color.WHITE);
        confPLabel.setBounds(100, 415, 300, 35);
        
        JLabel confPLS = new JLabel("Confirm Password:");
        confPLS.setFont(new Font("Arial", Font.BOLD, 16));
        confPLS.setForeground(shadowColor);
        confPLS.setBounds(102, 417, 300, 35);
        
        JPasswordField confPField = new JPasswordField();
        confPField.setFont(F3);
        confPField.setBounds(100, 445, 600, 35);
        confPField.setBorder(Border);
        confPField.setBackground(fldBGColor);

        JButton signUpBtn = new JButton("Sign-Up");
        signUpBtn.setBounds(100, 500, 600, 35);
        signUpBtn.setFont(new Font("Arial", Font.BOLD, 15));
        signUpBtn.setBackground(btnBGColor);
        signUpBtn.setForeground(Color.WHITE);
        signUpBtn.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

        JLabel rLabel = new JLabel("Already have an account?", SwingConstants.RIGHT);
        rLabel.setFont(F3);
        rLabel.setForeground(Color.WHITE);
        rLabel.setBounds(100, 535, 150, 30);
        
        JLabel rLS = new JLabel("Already have an account?", SwingConstants.RIGHT);
        rLS.setFont(F3);
        rLS.setForeground(shadowColor);
        rLS.setBounds(101, 536, 150, 30);

        JButton logInBtn = new JButton("<html><u>Log In.</u><html>");
        logInBtn.setFont(F3);
        logInBtn.setBackground(new Color(135, 206, 235));
        logInBtn.setForeground(btnBGColor);
        logInBtn.setOpaque(false);
        logInBtn.setBorder(null);
        logInBtn.setBounds(640, 535, 60, 30);
        
        JLabel logInBLS = new JLabel("<html><u>Log In.</u><html>");
        logInBLS.setFont(F3);
        logInBLS.setBackground(new Color(135, 206, 235));
        logInBLS.setForeground(Color.WHITE);
        logInBLS.setBounds(651, 536, 60, 30);

        regPanel.add(fLabel); regPanel.add(fLS); regPanel.add(fField);
        regPanel.add(lLabel); regPanel.add(lLS); regPanel.add(lField);
        regPanel.add(licenseLabel); regPanel.add(licenseLS); regPanel.add(licenseField);
        regPanel.add(eLabel); regPanel.add(eLS); regPanel.add(eField);
        regPanel.add(nLabel); regPanel.add(nLS); regPanel.add(nField);
        regPanel.add(pLabel); regPanel.add(pLS); regPanel.add(pField);
        regPanel.add(confPLabel); regPanel.add(confPLS); 
        regPanel.add(confPField);
        regPanel.add(signUpBtn); regPanel.add(signUpBtn);
        regPanel.add(rLabel); regPanel.add(rLS);
        regPanel.add(logInBtn); regPanel.add(logInBLS);

        signUpBtn.addActionListener(e -> {
            if(fField.getText().isEmpty() || lField.getText().isEmpty() || licenseField.getText().isEmpty()
                || eField.getText().isEmpty() || nField.getText().isEmpty()
                || new String(pField.getPassword()).isEmpty() || new String(confPField.getPassword()).isEmpty()) {
                JOptionPane.showMessageDialog(null, "Fields cannot be empty","Warning", JOptionPane.WARNING_MESSAGE);
                }
            else if (!eField.getText().contains("@") || !eField.getText().contains(".com")) {
                JOptionPane.showMessageDialog(null, "Enter a valid email\n e.g. user@example.com","Warning", JOptionPane.WARNING_MESSAGE);
                } 
            else if (!nField.getText().startsWith("09")){
                JOptionPane.showMessageDialog(null, "Phone Number must start with 09","Warning", JOptionPane.WARNING_MESSAGE);                
            }
            else if (nField.getText().length() != 11) {
                JOptionPane.showMessageDialog(null, "Phone number must be exactly 11 digits long", "Warning", JOptionPane.WARNING_MESSAGE);                            
            }
            else if (!nField.getText().matches("\\d{11}")) {
                JOptionPane.showMessageDialog(null, "Phone number must contain only digits", "Warning", JOptionPane.WARNING_MESSAGE);
            }
            else if(pField.getText().length() <8){
                JOptionPane.showMessageDialog(null, "Password must consist of 8 characters and above.", "Warning", JOptionPane.WARNING_MESSAGE);                
            }
            else if (!new String(pField.getPassword()).equals(new String(confPField.getPassword()))) {
                JOptionPane.showMessageDialog(null, "Passwords do not match", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                    int ans = JOptionPane.showConfirmDialog(this, "Are your details accurate?", "Confirmation", JOptionPane.YES_NO_OPTION);
                    if(ans == JOptionPane.YES_OPTION){
                        

                        try{
                        LocalDate localDate = LocalDate.now();
                        Date sqlDate = Date.valueOf(localDate);
                        connectToDB();
                        
                        String getID = "SELECT * FROM ACCOUNT WHERE CAST(SUBSTRING(AccountID, 2, 10) AS INT) = (SELECT MAX(CAST(SUBSTRING(AccountID, 2, 10) AS INT)) FROM ACCOUNT);";
                        PreparedStatement stmt = conn.prepareStatement(getID);
                        ResultSet rs = stmt.executeQuery();
                        int count=0; 
                        if (rs.next()) {
                            String lastID = rs.getString("AccountID"); 
                            System.out.println(rs.getString("AccountID"));
                            lastID = lastID.substring(1);
                            count = Integer.parseInt(lastID);
                        }
                        
                        String query = "INSERT INTO ACCOUNT VALUES (?,?,?,?,?,?,?,?,?,?)";
                        PreparedStatement p = conn.prepareStatement(query);
                            System.out.println(count + 1);
                        p.setString(1, "U"+(count +1));
                        p.setString(2,fField.getText().trim());
                        p.setString(3,lField.getText().trim());
                        p.setString(4,licenseField.getText().trim());
                        p.setString(5,eField.getText().toLowerCase().trim());
                        p.setString(6,nField.getText().trim());
                        p.setString(7,pField.getText());
                        p.setDate(8,sqlDate);
                        p.setString(9,"User");
                        p.setString(10,"Not Renting");
                        p.executeUpdate();
                        stmt.close();
                        p.close();
                        closeConnection();
                        
                        JOptionPane.showMessageDialog(null, "Account Created Successfully!");
                        dispose();
                        count = 0;
                        new LogInUI();
                        }
                            catch(SQLException ex){
                                if (ex.getSQLState().equals("23000")) { // eto ung error code for duplication values, sabi sa online
                                    JOptionPane.showMessageDialog(null, "Email is already used please use another one.");
                                } 
                                    else {
                                        JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
                                    }   
                            }
                            
                        }
{                    }

            }
        });

        logInBtn.addActionListener(e -> {
            dispose();
            new LogInUI();
        });
        
        pan.add(pageName);
        pan.add(pgNLS);
        pan.add(regPanel);
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
        new RegisterUI();
    }
}
