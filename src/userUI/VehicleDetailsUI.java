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

public class VehicleDetailsUI extends JFrame {
    
    // Fonts and colors used
    private static final Font F1 = new Font("Arial", Font.BOLD, 14);
    private static final Font F2 = new Font("Arial", Font.BOLD, 40);
    private static final Font F3 = new Font("Arial", Font.BOLD, 20);
    private static final Color LBLUE = new Color(30, 144, 255);
    private static final Color DBLUE = new Color(71, 112, 139);
    private String id;
    
    //JDBC Connection attributes
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=testDB;encrypt=true;trustServerCertificate=true";
    private static final String DB_USER = "admin";
    private static final String DB_PASS = "admin456";
    
    VehicleDetailsUI(String id) {
        this.id = id;
        
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
        line1.setBounds(90, 0, 30, 640);

        JPanel line2 = new JPanel();
        line2.setBackground(new Color(100, 149, 237));
        line2.setBounds(150, 0, 30, 640);
        JLabel title = new JLabel("VEHICLE DETAILS", SwingConstants.CENTER);
        title.setForeground(new Color (39, 58, 87));
        title.setFont(F2);
        title.setBounds(0, 60, 900, 30);


        JPanel detailPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0,35));
        detailPanel.setBounds(60, 120, 750, 585);
        detailPanel.setOpaque(false);

        JLabel l1 = new JLabel("Vehicle Type:");
        l1.setFont(F3);
        l1.setForeground(new Color (39, 58, 87));
        l1.setPreferredSize(new Dimension(265, 30));

        JTextField vType = new JTextField();
        vType.setFont(F1);
        vType.setEditable(false);
        vType.setPreferredSize(new Dimension(265, 30));

        JLabel l2 = new JLabel("Brand:");
        l2.setFont(F3);
        l2.setForeground(new Color (39, 58, 87));
        l2.setPreferredSize(new Dimension(265, 30));

        JTextField vBrand = new JTextField();
        vBrand.setFont(F1);
        vBrand.setEditable(false);
        vBrand.setPreferredSize(new Dimension(265, 30));

        JLabel l3 = new JLabel("Model:");
        l3.setFont(F3);
        l3.setForeground(new Color (39, 58, 87));
        l3.setPreferredSize(new Dimension(265, 30));

        JTextField vModel = new JTextField();
        vModel.setFont(F1);
        vModel.setEditable(false);
        vModel.setPreferredSize(new Dimension(265, 30));

        JLabel l4 = new JLabel("Color:");
        l4.setFont(F3);
        l4.setForeground(new Color (39, 58, 87));
        l4.setPreferredSize(new Dimension(265, 30));

        JTextField vColor = new JTextField();
        vColor.setFont(F1);
        vColor.setEditable(false);
        vColor.setPreferredSize(new Dimension(265, 30));

        JLabel l5 = new JLabel("License Plate:");
        l5.setFont(F3);
        l5.setForeground(new Color (39, 58, 87));
        l5.setPreferredSize(new Dimension(265, 30));

        JTextField vLPlate = new JTextField();
        vLPlate.setFont(F1);
        vLPlate.setEditable(false);
        vLPlate.setPreferredSize(new Dimension(265, 30));

        JLabel l6 = new JLabel("Rent Price:");
        l6.setFont(F3);
        l6.setForeground(new Color (39, 58, 87));
        l6.setPreferredSize(new Dimension(265, 30));

        JTextField vRPrice = new JTextField();
        vRPrice.setFont(F1);
        vRPrice.setEditable(false);
        vRPrice.setPreferredSize(new Dimension(265, 30));

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String sql = "SELECT * FROM Vehicles WHERE vehicleID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String type = rs.getString("vehicleType");
                String brand = rs.getString("brand");
                String model = rs.getString("model");
                String color = rs.getString("color");
                String licensePlate = rs.getString("licensePlate");
                double rentPrice = rs.getDouble("rentPrice");
                
                vType.setText(type);
                vBrand.setText(brand);
                vModel.setText(model);
                vColor.setText(color);
                vLPlate.setText(licensePlate);
                vRPrice.setText(String.format("%.2f", rentPrice));
            } else {
                vType.setText("No record found");
                vBrand.setText("No record found");
                vModel.setText("No record found");
                vColor.setText("No record found");
                vLPlate.setText("No record found");
                vRPrice.setText("No record found");
            }

            rs.close();
            pstmt.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            vType.setText("DB Error");
            vBrand.setText("DB Error");
            vModel.setText("DB Error");
            vColor.setText("DB Error");
            vLPlate.setText("DB Error");
            vRPrice.setText("DB Error");
        }
        
        JLabel spacer = new JLabel();
        spacer.setPreferredSize(new Dimension(265, 30));

        JButton backBtn = new JButton("Return");
        
        backBtn.setPreferredSize(new Dimension(78, 25));

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
        detailPanel.add(spacer);
        detailPanel.add(backBtn);

        // Action Listeners
        backBtn.addActionListener(e -> returnToBrowseVehicle());
        

        pan.add(title);
        pan.add(detailPanel);
        pan.add(line1);
        pan.add(line2);

        add(pan);
        setVisible(true);
    } // end of constructor

    private void returnToBrowseVehicle() {
        dispose();
        new BrowseVehicleUI();
    }

    // Main method to open the frame
    public static void main(String[] args) {

        new VehicleDetailsUI("V1111");
    }
}
