
package userUI;

import logInUI.LogInUI;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.sql.*;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

public class UserLandingPageUI extends JFrame {

    //fonts and colors used that has rgb values
    private static final Font F1 = new Font("Arial", Font.BOLD, 40);
    private static final Font F2 = new Font("Arial", Font.BOLD, 20);
    private static final Font F3 = new Font("Arial", Font.BOLD, 16);
    private static final Font F4 = new Font("Arial", Font.BOLD, 16);
    private static final Font F5 = new Font("Arial", Font.BOLD, 13); 
    
    /* Fonts
    private static final Font F1 = new Font("Arial", Font.BOLD, 18);
    private static final Font F2 = new Font("Arial", Font.BOLD, 35);
    private static final Font F3 = new Font("Arial", Font.BOLD, 40);
    private static final Font F4 = new Font("Arial", Font.BOLD, 13); */
    
    //Colors
    private static final Color fontColor = Color.WHITE;
    private static final Color shadowColor = new Color(143,143,143);
    private static final Color shadowColor1 = new Color(148,159,163);
    private static final Color fldBGColor = new Color(240, 240, 240);
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
    private String accID;
    
    public UserLandingPageUI(String AId){
        this.accID = AId;
        
        setTitle("Vehicle Rental System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 640);
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
        
        LineBorder thickness = new LineBorder(fontColor,3);
        LineBorder thickness1 = new LineBorder(shadowColor1,3);
        
        TitledBorder border1 = BorderFactory.createTitledBorder(thickness,"");
        border1.setTitle(" HOME PAGE - ");
        border1.setTitleFont(F2);
        border1.setTitleColor(fontColor);
        border1.setTitlePosition(TitledBorder.TOP);
        border1.setTitleJustification(TitledBorder.LEFT);
        
        TitledBorder border2 = BorderFactory.createTitledBorder(thickness1,"");
        border2.setTitle(" HOME PAGE - ");
        border2.setTitleFont(F2);
        border2.setTitleColor(shadowColor1);
        border2.setTitlePosition(TitledBorder.TOP);
        border2.setTitleJustification(TitledBorder.LEFT);
        
        JPanel pagePanel = new JPanel();
        pagePanel.setOpaque(false);
        pagePanel.setBounds(120, 110, 640, 160);
        pagePanel.setBorder(border1);
        
        JPanel pagePanel1 = new JPanel();
        pagePanel1.setOpaque(false);
        pagePanel1.setBounds(122, 112, 640, 160);
        pagePanel1.setBorder(border2);
        
        pan.add(pagePanel);
        pan.add(pagePanel1);
        
        JLabel l1 = new JLabel();
        l1.setForeground(fontColor);
        l1.setFont(F3);
        l1.setBounds(200, 150, 360, 30);
        
        JLabel ls1 = new JLabel();
        ls1.setForeground(shadowColor1);
        ls1.setFont(F3);
        ls1.setBounds(202, 152, 360, 30);
        
        JLabel l2 = new JLabel();
        l2.setForeground(fontColor);
        l2.setFont(F3);
        l2.setBounds(200, 180, 360, 30);
        
        JLabel ls2 = new JLabel();
        ls2.setForeground(shadowColor1);
        ls2.setFont(F3);
        ls2.setBounds(202, 182, 360, 30);
        
        JLabel l3 = new JLabel();
        l3.setForeground(fontColor);
        l3.setFont(F3);
        l3.setBounds(200, 210, 360, 30);
        
        JLabel ls3 = new JLabel();
        ls3.setForeground(shadowColor1);
        ls3.setFont(F3);
        ls3.setBounds(202, 212, 360, 30);
        
        JButton logOutBtn = new JButton("Log Out");
        logOutBtn.setBounds(715, 555, 85, 30);
        logOutBtn.setForeground(fontColor);
        logOutBtn.setFont(F5);
        logOutBtn.setBackground(btnBGColor);
        logOutBtn.setBorder(Border);
        
        JButton settingsBtn = new JButton("Settings");
        settingsBtn.setBounds(80, 555, 85, 30);
        settingsBtn.setForeground(fontColor);
        settingsBtn.setFont(F5);
        settingsBtn.setBackground(btnBGColor);
        settingsBtn.setBorder(Border);

        JPanel selection = new JPanel(new GridLayout(5,0,30,10));
        selection.setOpaque(false);
        
        JLabel menuLabel = new JLabel("Vehicle Management Menu",SwingConstants.CENTER);
        menuLabel.setForeground(fontColor);
        menuLabel.setFont(F1);
        menuLabel.setBounds(-10, 35, 900, 50);
        
        JLabel menuLS = new JLabel("Vehicle Management Menu",SwingConstants.CENTER);
        menuLS.setForeground(shadowColor);
        menuLS.setFont(F1);
        menuLS.setBounds(-7, 38, 900, 50);
        
        JButton viewVehicleBtn = new JButton("SHOW VEHICLE AVAILABLE");
        viewVehicleBtn.setBounds(100, 340, 680, 50);
        viewVehicleBtn.setForeground(fontColor);
        viewVehicleBtn.setFont(F4);
        viewVehicleBtn.setBackground(btnBGColor);
        viewVehicleBtn.setBorder(Border);

        JButton rentVehicleBtn = new JButton("RENT A VEHICLE");
        rentVehicleBtn.setForeground(fontColor);
        rentVehicleBtn.setFont(F4);
        rentVehicleBtn.setBackground(btnBGColor);
        rentVehicleBtn.setBorder(Border);

        JButton returnVehicleBtn = new JButton("RETURN VEHICLE");
        returnVehicleBtn.setForeground(fontColor);
        returnVehicleBtn.setFont(F4);
        returnVehicleBtn.setBackground(btnBGColor);
        returnVehicleBtn.setBorder(Border);

        JButton rentStatusBtn = new JButton("SHOW BOOKING STATUS");
        rentStatusBtn.setForeground(fontColor);
        rentStatusBtn.setFont(F4);
        rentStatusBtn.setBackground(btnBGColor);
        rentStatusBtn.setBorder(Border);
        
        try{
                connectToDB();
                String loginQuery = "SELECT AccountID, FName, LName, DateCreated, AccountStatus FROM ACCOUNT WHERE AccountID = ? ";
                PreparedStatement p = conn.prepareStatement(loginQuery);
                p.setString(1,AId);
                ResultSet rs = p.executeQuery();
                
                if(rs.next()){
                border1.setTitle(" HOME PAGE - " + rs.getString("FName") +" ");
                border2.setTitle(" HOME PAGE - " + rs.getString("FName") +" ");
                l1.setText("Welcome, " +rs.getString("FName") +" "+rs.getString("LName")+ "!");
                l2.setText("Account Status: " + rs.getString("AccountStatus"));
                l3.setText("Account Created at " + rs.getDate("DateCreated") );
                }
                closeConnection();
                rs.close();
                p.close();
        }
        catch(SQLException e){
        
        }

        selection.add(viewVehicleBtn);       
        selection.add(rentVehicleBtn);
        selection.add(returnVehicleBtn);
        selection.add(rentStatusBtn);
        selection.setBounds(80,295,720,300);

        //ActionListeners
        viewVehicleBtn.addActionListener(e -> goToViewVehicle());
        rentVehicleBtn.addActionListener(e -> goToRentVehicle());
        rentStatusBtn.addActionListener(e -> goToViewBookingStatus());
        returnVehicleBtn.addActionListener(e -> goToReturnVehicle());
        settingsBtn.addActionListener(e -> goToSettings());
        logOutBtn.addActionListener(e -> logOut());
        
        pan.add(menuLabel);
        pan.add(menuLS);
        pan.add(l1);
        pan.add(ls1);
        pan.add(l2);
        pan.add(ls2);
        pan.add(l3);
        pan.add(ls3);        
        pan.add(selection);
        pan.add(settingsBtn);
        pan.add(logOutBtn);
        pan.add(line1);
        pan.add(line2);
        add(pan);
        setVisible(true);
    }

    private void goToViewVehicle(){
        dispose();
        new BrowseVehicleUI(accID);
    }
    private void goToRentVehicle(){
        dispose();
        new Booking(accID);
    }
    private void goToReturnVehicle(){
        dispose();
        new ReturnVehicle(accID);
    }
    private void goToViewBookingStatus(){
        dispose();
        new ViewBookingStatus(accID);
    }
    private void goToSettings(){
        dispose();
        new Settings(accID);
    }
    private void logOut(){
        int ans = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?",null,JOptionPane.YES_NO_OPTION);
        if(ans==JOptionPane.YES_OPTION){
        dispose();
        new LogInUI();
        }
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

class runner{
    public static void main(String[] args) {
        new UserLandingPageUI("V0001");
    }
}