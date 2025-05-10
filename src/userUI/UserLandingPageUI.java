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
import javax.swing.JOptionPane;

public class UserLandingPageUI extends JFrame {

    //fonts and colors used that has rgb values
    private static final Font F1 = new Font("Arial", Font.BOLD, 35);
    private static final Font F2 = new Font("Arial", Font.BOLD, 20);
    private static final Font F3 = new Font("Arial", Font.BOLD, 14);
    
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
        line1.setBackground(new Color(100, 149, 237)); 
        line1.setBounds(80, 0, 30, 640);

        JPanel line2 = new JPanel();
        line2.setBackground(new Color(100, 149, 237)); 
        line2.setBounds(150, 0, 30, 640);
        
        
        JLabel pageName = new JLabel();
        pageName.setForeground(new Color (39, 58, 87));
        pageName.setFont(F2);
        pageName.setBounds(40, 100, 360, 30);
        
        JLabel l1 = new JLabel();
        l1.setForeground(new Color (39, 58, 87));
        l1.setFont(F3);
        l1.setBounds(90, 140, 360, 30);
        
        JLabel l2 = new JLabel();
        l2.setForeground(new Color (39, 58, 87));
        l2.setFont(F3);
        l2.setBounds(90, 170, 360, 30);
        
        JLabel l3 = new JLabel();
        l3.setForeground(new Color (39, 58, 87));
        l3.setFont(F3);
        l3.setBounds(90, 200, 360, 30);
        
        JButton logOutBtn = new JButton("Log Out");
        logOutBtn.setBounds(730,550,85, 25);

        JPanel selection = new JPanel(new GridLayout(5,0,30,15));
        selection.setOpaque(false);
        
        JLabel menuLabel = new JLabel("Vehicle Management Menu",SwingConstants.CENTER);
        menuLabel.setForeground(new Color (39, 58, 87));
        menuLabel.setFont(F1);
        menuLabel.setBounds(0, 40, 900, 33);
        
        JButton viewVehicleBtn = new JButton("Show Vehicle Available");
        viewVehicleBtn.setFont(F3);


        JButton rentVehicleBtn = new JButton("Rent A Vehicle");
        rentVehicleBtn.setFont(F3);


        
        JButton returnVehicleBtn = new JButton("Return Vehicle");
        returnVehicleBtn.setFont(F3);

        
        JButton rentStatusBtn = new JButton("Show Booking Status");
        rentStatusBtn.setFont(F3);
        
        try{
                connectToDB();
                String loginQuery = "SELECT AccountID, FName, LName, DateCreated, AccountStatus FROM ACCOUNT WHERE AccountID = ? ";
                PreparedStatement p = conn.prepareStatement(loginQuery);
                p.setString(1,AId);
                ResultSet rs = p.executeQuery();
                
                if(rs.next()){
                pageName.setText("HOME PAGE - " + rs.getString("FName"));
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
        selection.setBounds(45,280,770,300);

        
        //ActionListeners
        viewVehicleBtn.addActionListener(e -> goToViewVehicle());
        rentVehicleBtn.addActionListener(e -> goToRentVehicle());
        rentStatusBtn.addActionListener(e -> goToViewBookingStatus());
        returnVehicleBtn.addActionListener(e -> goToReturnVehicle());
        logOutBtn.addActionListener(e -> logOut());
        
        pan.add(menuLabel);
        pan.add(pageName);
        pan.add(l1);
        pan.add(l2);
        pan.add(l3);        
        pan.add(selection);
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