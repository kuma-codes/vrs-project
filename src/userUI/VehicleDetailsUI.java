package userUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.sql.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

public class VehicleDetailsUI extends JFrame {
    
    // Fonts and colors used
    private static final Font F1 = new Font("Arial", Font.BOLD, 14);
    private static final Font F2 = new Font("Arial", Font.BOLD, 40);
    private static final Font F3 = new Font("Arial", Font.BOLD, 20);
    private static final Color LBLUE = new Color(30, 144, 255);
    private static final Color DBLUE = new Color(71, 112, 139);
    private static final Color fontColor = Color.WHITE;
    private static final Color shadowColor = new Color(143,143,143);
    private static final Color fldBGColor = new Color(240, 240, 240);
    private static final Color btnBGColor = new Color(92,142,175);
    
    //Border
    private static final Border lineBorder = BorderFactory.createLineBorder(Color.GRAY,1);
    private static final Border empBorder = BorderFactory.createEmptyBorder(2,2,2,2);
    private static final Border Border = new CompoundBorder(lineBorder,empBorder);
    
    private String vID, accID;
    
    //JDBC Connection attributes
    private Connection conn;
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=vRentalSystemDB;encrypt=true;trustServerCertificate=true";
    private static final String DB_USER = "admin";
    private static final String DB_PASS = "admin456";
    
    VehicleDetailsUI(String vID, String AId) {
        this.vID = vID;
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
        
        JLabel title = new JLabel("VEHICLE DETAILS", SwingConstants.CENTER);
        title.setForeground(fontColor);
        title.setFont(F2);
        title.setBounds(0, 60, 900, 30);
        
        JLabel titleLS = new JLabel("VEHICLE DETAILS", SwingConstants.CENTER);
        titleLS.setForeground(shadowColor);
        titleLS.setFont(F2);
        titleLS.setBounds(3, 63, 900, 30);

        JPanel detailPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0,15));
        detailPanel.setBounds(60, 120, 750, 585);
        detailPanel.setOpaque(false);

        JLabel l1 = new JLabel("Vehicle Type:");
        l1.setFont(F3);
        l1.setForeground(fontColor);
        l1.setPreferredSize(new Dimension(265, 30));
        
        JLabel ls1 = new JLabel("Vehicle Type:");
        ls1.setFont(F3);
        ls1.setForeground(shadowColor);
        ls1.setBounds(172,142,265,30);

        JTextField vType = new JTextField();
        vType.setFont(F1);
        vType.setEditable(false);
        vType.setPreferredSize(new Dimension(265, 40));
        vType.setBorder(Border);

        JLabel l2 = new JLabel("Brand:");
        l2.setFont(F3);
        l2.setForeground(fontColor);
        l2.setPreferredSize(new Dimension(265, 30));
        
        JLabel ls2 = new JLabel("Brand:");
        ls2.setFont(F3);
        ls2.setForeground(shadowColor);
        ls2.setBounds(172,197,265,30);

        JTextField vBrand = new JTextField();
        vBrand.setFont(F1);
        vBrand.setEditable(false);
        vBrand.setPreferredSize(new Dimension(265, 40));
        vBrand.setBorder(Border);

        JLabel l3 = new JLabel("Model:");
        l3.setFont(F3);
        l3.setForeground(fontColor);
        l3.setPreferredSize(new Dimension(265, 30));
        
        JLabel ls3 = new JLabel("Model:");
        ls3.setFont(F3);
        ls3.setForeground(shadowColor);
        ls3.setBounds(172,252,265,30);

        JTextField vModel = new JTextField();
        vModel.setFont(F1);
        vModel.setEditable(false);
        vModel.setPreferredSize(new Dimension(265, 40));
        vModel.setBorder(Border);

        JLabel l4 = new JLabel("Color:");
        l4.setFont(F3);
        l4.setForeground(fontColor);
        l4.setPreferredSize(new Dimension(265, 30));
        
        JLabel ls4 = new JLabel("Color:");
        ls4.setFont(F3);
        ls4.setForeground(shadowColor);
        ls4.setBounds(172,307,265,30);

        JTextField vColor = new JTextField();
        vColor.setFont(F1);
        vColor.setEditable(false);
        vColor.setPreferredSize(new Dimension(265, 40));
        vColor.setBorder(Border);

        JLabel l5 = new JLabel("License Plate:");
        l5.setFont(F3);
        l5.setForeground(fontColor);
        l5.setPreferredSize(new Dimension(265, 30));
        
        JLabel ls5 = new JLabel("License Plate:");
        ls5.setFont(F3);
        ls5.setForeground(shadowColor);
        ls5.setBounds(172,362,265,30);

        JTextField vLPlate = new JTextField();
        vLPlate.setFont(F1);
        vLPlate.setEditable(false);
        vLPlate.setPreferredSize(new Dimension(265, 40));
        vLPlate.setBorder(Border);

        JLabel l6 = new JLabel("Rent Price:");
        l6.setFont(F3);
        l6.setForeground(fontColor);
        l6.setPreferredSize(new Dimension(265, 30));
        
        JLabel ls6 = new JLabel("Rent Price:");
        ls6.setFont(F3);
        ls6.setForeground(shadowColor);
        ls6.setBounds(172,417,265,30);

        JTextField vRPrice = new JTextField();
        vRPrice.setFont(F1);
        vRPrice.setEditable(false);
        vRPrice.setPreferredSize(new Dimension(265, 40));
        vRPrice.setBorder(Border);
        
        JLabel l7 = new JLabel("Rental Status:");
        l7.setFont(F3);
        l7.setForeground(fontColor);
        l7.setPreferredSize(new Dimension(265, 30));
        
        JLabel ls7 = new JLabel("Rental Status:");
        ls7.setFont(F3);
        ls7.setForeground(shadowColor);
        ls7.setBounds(172,472,265,30);

        JTextField vVStatus = new JTextField();
        vVStatus.setFont(F1);
        vVStatus.setEditable(false);
        vVStatus.setPreferredSize(new Dimension(265, 40));
        vVStatus.setBorder(Border);

        try {
            connectToDB();
            String sql = "SELECT * FROM VEHICLES WHERE VehicleID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, vID);

            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String type = rs.getString("VType");
                String brand = rs.getString("Brand");
                String model = rs.getString("Model");
                String color = rs.getString("Color");
                String licensePlate = rs.getString("LicensePlate");
                double rentPrice = rs.getDouble("RentPrice");
                String vehicleStatus = rs.getString("VehicleStatus");
                
                vType.setText("     "+type);
                vBrand.setText("     "+brand);
                vModel.setText("     "+model);
                vColor.setText("     "+color);
                vLPlate.setText("     "+licensePlate);
                vRPrice.setText(String.format("     ₱ "+"%.2f", rentPrice));
                vVStatus.setText("     "+vehicleStatus);
            } else {
                vType.setText("     "+"No record found");
                vBrand.setText("     "+"No record found");
                vModel.setText("     "+"No record found");
                vColor.setText("     "+"No record found");
                vLPlate.setText("     "+"No record found");
                vRPrice.setText("     "+"No record found");
                vRPrice.setText("     "+"No record found");
                vVStatus.setText("     "+"No record found");
            }
            closeConnection();

        } catch (SQLException ex) {
            ex.printStackTrace();
            vType.setText("     "+"DB Error");
            vBrand.setText("     "+"DB Error");
            vModel.setText("     "+"DB Error");
            vColor.setText("     "+"DB Error");
            vLPlate.setText("     "+"DB Error");
            vRPrice.setText("     "+"DB Error");
            vVStatus.setText("     "+"DB Error");
        }
        
        JLabel spacer = new JLabel();
        spacer.setPreferredSize(new Dimension(265, 30));

        //backBtn
        JButton backBtn = new JButton("<");
        backBtn.setBounds(20, 20, 60, 40);
        backBtn.setForeground(fontColor);
        backBtn.setFocusPainted(false);
        backBtn.setBackground(Color.WHITE);
        backBtn.setOpaque(false);
        backBtn.setBorderPainted(false);
        backBtn.setFont(F2);
        
        //backBtn shadow
        JButton bBtnShadow = new JButton("<");
        bBtnShadow.setBounds(23, 24, 60, 40);
        bBtnShadow.setForeground(shadowColor);
        bBtnShadow.setFocusPainted(false);
        bBtnShadow.setBackground(Color.WHITE);
        bBtnShadow.setEnabled(false);
        bBtnShadow.setOpaque(false);
        bBtnShadow.setBorderPainted(false);
        bBtnShadow.setFont(F2);
        
        pan.add(backBtn);
        pan.add(bBtnShadow);
        
        detailPanel.add(l1);
        detailPanel.add(vType);
        detailPanel.add(l2);
        detailPanel.add(vBrand);
        detailPanel.add(l3);
        detailPanel.add(vModel);
        detailPanel.add(l4);
        detailPanel.add(vColor);
        detailPanel.add(l5);
        detailPanel.add(vLPlate);
        detailPanel.add(l6);
        detailPanel.add(vRPrice);
        detailPanel.add(l7);
        detailPanel.add(vVStatus);
        detailPanel.add(spacer);

        // Action Listeners
        backBtn.addActionListener(e -> returnToBrowseVehicle());

        pan.add(title);
        pan.add(titleLS);
        pan.add(detailPanel);
        pan.add(ls1);
        pan.add(ls2);
        pan.add(ls3);
        pan.add(ls4);
        pan.add(ls5);
        pan.add(ls6);
        pan.add(ls7);
        pan.add(ls1);
        pan.add(line1);
        pan.add(line2);

        add(pan);
        setVisible(true);
    } // end of constructor

    private void returnToBrowseVehicle() {
        dispose();
        new BrowseVehicleUI(accID);
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
