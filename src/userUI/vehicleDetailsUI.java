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

public class vehicleDetailsUI extends JFrame {
    
    // Fonts and colors used
    private static final Font F1 = new Font("Arial", Font.BOLD, 14);
    private static final Font F2 = new Font("Arial", Font.BOLD, 40);
    private static final Font F3 = new Font("Arial", Font.BOLD, 20);
    private static final Color LBLUE = new Color(30, 144, 255);
    private static final Color DBLUE = new Color(71, 112, 139);
    private String type, color, brand;

    vehicleDetailsUI(String type, String color, String brand) {
        this.type = type;
        this.color = color;
        this.brand = brand;

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

        JTextField vType = new JTextField(type);
        vType.setFont(F1);
        vType.setEditable(false);
        vType.setPreferredSize(new Dimension(265, 30));

        JLabel l2 = new JLabel("Brand:");
        l2.setFont(F3);
        l2.setForeground(new Color (39, 58, 87));
        l2.setPreferredSize(new Dimension(265, 30));

        JTextField vBrand = new JTextField(brand);
        vBrand.setFont(F1);
        vBrand.setEditable(false);
        vBrand.setPreferredSize(new Dimension(265, 30));

        JLabel l3 = new JLabel("Model:");
        l3.setFont(F3);
        l3.setForeground(new Color (39, 58, 87));
        l3.setPreferredSize(new Dimension(265, 30));

        JTextField vModel = new JTextField("Model will be populated from DB");
        vModel.setFont(F1);
        vModel.setEditable(false);
        vModel.setPreferredSize(new Dimension(265, 30));

        JLabel l4 = new JLabel("Color:");
        l4.setFont(F3);
        l4.setForeground(new Color (39, 58, 87));
        l4.setPreferredSize(new Dimension(265, 30));

        JTextField vColor = new JTextField(color);
        vColor.setFont(F1);
        vColor.setEditable(false);
        vColor.setPreferredSize(new Dimension(265, 30));

        JLabel l5 = new JLabel("License Plate:");
        l5.setFont(F3);
        l5.setForeground(new Color (39, 58, 87));
        l5.setPreferredSize(new Dimension(265, 30));

        JTextField vLPlate = new JTextField("License Plate will be populated from DB");
        vLPlate.setFont(F1);
        vLPlate.setEditable(false);
        vLPlate.setPreferredSize(new Dimension(265, 30));

        JLabel l6 = new JLabel("Rent Price:");
        l6.setFont(F3);
        l6.setForeground(new Color (39, 58, 87));
        l6.setPreferredSize(new Dimension(265, 30));

        JTextField vRPrice = new JTextField("Price will be populated from DB");
        vRPrice.setFont(F1);
        vRPrice.setEditable(false);
        vRPrice.setPreferredSize(new Dimension(265, 30));

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
        new browseVehicleUI();
    }

    // Main method to open the frame
    public static void main(String[] args) {
        // Sample data for the vehicle
        String vehicleType = "SUV";
        String vehicleColor = "Red";
        String vehicleBrand = "Toyota";

        // Create and display the vehicle details UI with the sample data
        new vehicleDetailsUI(vehicleType, vehicleColor, vehicleBrand);
    }
}
