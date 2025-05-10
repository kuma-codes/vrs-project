package logInUI;




import java.awt.Color;
import java.awt.Font;
import java.sql.*;
import java.text.ParseException;
import java.time.LocalDate;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane;
import javax.swing.text.MaskFormatter;


public class RegisterUI extends JFrame {
    private static final Font F1 = new Font("Arial", Font.BOLD, 19);
    private static final Font F2 = new Font("Arial", Font.BOLD, 16);
    private static final Font F3 = new Font("Arial", Font.BOLD, 12);
    private static final Color LBLUE = new Color(30,144,255);
     //to count the uservalues to auto assign AccountID
    // JDBC setup
    private Connection conn;
    private final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=vRentalSystemDB;encrypt=true;trustServerCertificate=true";
    private final String DB_USER = "admin";
    private final String DB_PASS = "admin456";

    public RegisterUI(){
        setTitle("Register");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 740);
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
        pan.setBounds(0, 0, 900, 740);
        pan.setLayout(null);

        JPanel line1 = new JPanel();
        line1.setBackground(new Color(100, 149, 237)); 
        line1.setBounds(90, 0, 30, 740);

        JPanel line2 = new JPanel();
        line2.setBackground(new Color(100, 149, 237)); 
        line2.setBounds(150, 0, 30, 740);

        JLabel pageName = new JLabel("REGISTER", SwingConstants.CENTER);
        pageName.setForeground(new Color (39, 58, 87));
        pageName.setFont(new Font ("Arial", Font.BOLD, 50));
        pageName.setBounds(0, 60, 900, 50);

        JPanel regPanel = new JPanel(null);
        regPanel.setBounds(40, 100, 900, 740);
        regPanel.setOpaque(false);

        JLabel fLabel = new JLabel("First Name:");
        fLabel.setFont(new Font("Arial", Font.BOLD, 15));
        fLabel.setForeground(new Color (39, 58, 87));
        fLabel.setBounds(100, 30, 300, 35);
        JTextField fField = new JTextField();
        fField.setFont(F3);
        fField.setBounds(100, 60, 600, 30);

        JLabel lLabel = new JLabel("Last Name:");
        lLabel.setFont(new Font("Arial", Font.BOLD, 15));
        lLabel.setForeground(new Color (39, 58, 87));
        lLabel.setBounds(100, 100, 300, 35);
        JTextField lField = new JTextField();
        lField.setFont(F3);
        lField.setBounds(100, 130, 600, 30);

        JLabel licenseLabel = new JLabel("Drivers License ID No.: (e.g. L12-32-321321)");
        licenseLabel.setFont(new Font("Arial", Font.BOLD, 15));
        licenseLabel.setForeground(new Color (39, 58, 87));
        licenseLabel.setBounds(100, 170, 500, 35);


        JFormattedTextField licenseField = new JFormattedTextField(licenseMask);
        licenseField.setFont(F3);
        licenseField.setBounds(100, 200, 600, 30);

        JLabel eLabel = new JLabel("Email: (e.g. 123@example.com)");
        eLabel.setFont(new Font("Arial", Font.BOLD, 15));
        eLabel.setForeground(new Color (39, 58, 87));
        eLabel.setBounds(100, 240, 300, 35);
        JTextField eField = new JTextField();
        eField.setFont(F3);
        eField.setBounds(100, 270, 600, 30);

        JLabel nLabel = new JLabel("Phone Number:");
        nLabel.setFont(new Font("Arial", Font.BOLD, 15));
        nLabel.setForeground(new Color (39, 58, 87));
        nLabel.setBounds(100, 310, 300, 35);
        JFormattedTextField nField = new JFormattedTextField(numberMask);
        nField.setFont(F3);
        nField.setBounds(100, 340, 600, 30);

        JLabel pLabel = new JLabel("Password:");
        pLabel.setFont(new Font("Arial", Font.BOLD, 15));
        pLabel.setForeground(new Color (39, 58, 87));
        pLabel.setBounds(100, 380, 300, 35);
        JPasswordField pField = new JPasswordField();
        pField.setFont(F3);
        pField.setBounds(100, 410, 600, 30);

        JLabel confPLabel = new JLabel("Confirm Password:");
        confPLabel.setFont(new Font("Arial", Font.BOLD, 15));
        confPLabel.setForeground(new Color (39, 58, 87));
        confPLabel.setBounds(100, 450, 300, 35);
        JPasswordField confPField = new JPasswordField();
        confPField.setFont(F3);
        confPField.setBounds(100, 480, 600, 30);

        JButton signUpBtn = new JButton("Sign-Up");
        signUpBtn.setBounds(100, 525, 600, 30);
        signUpBtn.setBackground(LBLUE);
        signUpBtn.setForeground(Color.WHITE);

        JLabel rLabel = new JLabel("Already have an account?", SwingConstants.RIGHT);
        rLabel.setFont(F3);
        rLabel.setForeground(Color.WHITE);
        rLabel.setBounds(50, 555, 600, 30);

        JButton logInBtn = new JButton("Log in.");
        logInBtn.setFont(F3);
        logInBtn.setBackground(new Color(135, 206, 235));
        logInBtn.setForeground(new Color (8,39,245));
        logInBtn.setOpaque(false);
        logInBtn.setBorder(null);
        logInBtn.setBounds(380, 560, 600, 20);

        regPanel.add(fLabel); regPanel.add(fField);
        regPanel.add(lLabel); regPanel.add(lField);
        regPanel.add(licenseLabel); regPanel.add(licenseField);
        regPanel.add(eLabel); regPanel.add(eField);
        regPanel.add(nLabel); regPanel.add(nField);
        regPanel.add(pLabel); regPanel.add(pField);
        regPanel.add(confPLabel); 
        regPanel.add(confPField);
        regPanel.add(signUpBtn);
        regPanel.add(rLabel);
        regPanel.add(logInBtn);

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
            else if (!new String(pField.getPassword()).equals(new String(confPField.getPassword()))) {
                JOptionPane.showMessageDialog(null, "Passwords do not match", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                    int ans = JOptionPane.showConfirmDialog(this, "Are your details accurate?", "Confirmation", JOptionPane.YES_NO_OPTION);
                    if(ans == JOptionPane.YES_OPTION){
                        JOptionPane.showMessageDialog(null, "Account Created Successfully!");

                        try{
                        LocalDate localDate = LocalDate.now();
                        Date sqlDate = Date.valueOf(localDate);
                        connectToDB();
                        
                        String getID = "SELECT TOP 1 AccountID FROM ACCOUNT ORDER BY AccountID DESC";
                        PreparedStatement stmt = conn.prepareStatement(getID);
                        ResultSet rs = stmt.executeQuery();
                        int count=0; 
                        if (rs.next()) {
                            String lastID = rs.getString("AccountID"); 
                            lastID = lastID.substring(1);
                            count = Integer.parseInt(lastID);
                        }
                        
                        String query = "INSERT INTO ACCOUNT VALUES (?,?,?,?,?,?,?,?,?,?)";
                        PreparedStatement p = conn.prepareStatement(query);
                        p.setString(1, "U"+(count +1));
                        p.setString(2,fField.getText().trim());
                        p.setString(3,lField.getText().trim());
                        p.setString(4,licenseField.getText().trim());
                        p.setString(5,eField.getText().trim());
                        p.setString(6,nField.getText().trim());
                        p.setString(7,pField.getText());
                        p.setDate(8,sqlDate);
                        p.setString(9,"User");
                        p.setString(10,"Not Renting");
                        p.executeUpdate();
                        stmt.close();
                        p.close();
                        closeConnection();
                        dispose();
                        count = 0;
                        new LogInUI();
                        
                        }
                            catch(SQLException ex){
                                JOptionPane.showMessageDialog(null, ex.getMessage());
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
