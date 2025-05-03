package userUI;

import java.text.CharacterIterator;
import logInUI.LogInUI;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class UserLandingPageUI extends JFrame {
    
//fonts and colors used that has rgb values
    private static final Font F1 = new Font("Arial", Font.BOLD, 35);
    private static final Font F2 = new Font("Arial", Font.BOLD, 20);
    private static final Font F3 = new Font("Arial", Font.BOLD, 14);
    
//placeholder for sample datas
    private static String name1 = "test_user1";
    private String creationDate = "Account Created: 01-01-2001";
    private String accType = "Account Type: User";
    
// Private variables 
    private static String name = name1;
    private static String brand;
    private static String model;
    private static String color;
    private static String startDate;
    private static String endDate;
    private static double dailyRate;
    private static int totalDays;
    private static String status = "Not Renting";

    
    public UserLandingPageUI(){
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
        
        
        JLabel pageName = new JLabel("HOME PAGE - " + name);
        pageName.setForeground(new Color (39, 58, 87));
        pageName.setFont(F2);
        pageName.setBounds(40, 100, 360, 30);
        
        JLabel l1 = new JLabel("Welcome, " +name + "!");
        l1.setForeground(new Color (39, 58, 87));
        l1.setFont(F3);
        l1.setBounds(90, 140, 360, 30);
        
        JLabel l2 = new JLabel(creationDate);
        l2.setForeground(new Color (39, 58, 87));
        l2.setFont(F3);
        l2.setBounds(90, 170, 360, 30);
        
        JLabel l3 = new JLabel(accType);
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
        menuLabel.setBounds(0, 40, 900, 30);
        
        JButton viewVehicleBtn = new JButton("Show Vehicle Available");
        viewVehicleBtn.setFont(F3);


        JButton rentVehicleBtn = new JButton("Rent A Vehicle");
        rentVehicleBtn.setFont(F3);


        
        JButton returnVehicleBtn = new JButton("Return Vehicle");
        returnVehicleBtn.setFont(F3);

        
        JButton rentStatusBtn = new JButton("Show Booking Status");
        rentStatusBtn.setFont(F3);


        
        
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
        new BrowseVehicleUI();
    }
    private void goToRentVehicle(){
        dispose();
        new Booking();
    }
    private void goToReturnVehicle(){
        dispose();
        new ReturnVehicle();
    }
    private void goToViewBookingStatus(){
        dispose();
        new ViewBookingStatus().showViewBookingStatus();
    }
    private void logOut(){
        dispose();
        System.out.println("Logged Out Succesfully");
        new LogInUI();
    }
    
    //setter and getter methods for the private variable
    
    public static String getUserName() {
        return name;
    }

    public static String getBrand() {
        return brand;
    }

    public static void setBrand(String brand) {
        UserLandingPageUI.brand = brand;
    }

    public static String getModel() {
        return model;
    }

    public static void setModel(String model) {
        UserLandingPageUI.model = model;
    }

    public static String getColor() {
        return color;
    }

    public static void setColor(String color) {
        UserLandingPageUI.color = color;
    }

    public static String getStartDate() {
        return startDate;
    }

    public static void setStartDate(String startDate) {
        UserLandingPageUI.startDate = startDate;
    }

    public static String getEndDate() {
        return endDate;
    }

    public static void setEndDate(String endDate) {
        UserLandingPageUI.endDate = endDate;
    }

    public static double getDailyRate() {
        return dailyRate;
    }

    public static void setDailyRate(double dailyRate) {
        UserLandingPageUI.dailyRate = dailyRate;
    }

    public static int getTotalDays() {
        return totalDays;
    }

    public static void setTotalDays(int totalDays) {
        UserLandingPageUI.totalDays = totalDays;
    }

    public static String getStatus() {
        return status;
    }

    public static void setStatus(String status) {
        UserLandingPageUI.status = status;
    }

    
    
    
    
}


class runner{
    public static void main(String[] args) {
        new UserLandingPageUI();
    }
}