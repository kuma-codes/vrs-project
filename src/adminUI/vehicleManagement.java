package adminUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;

public class vehicleManagement extends JFrame {

    private static final Font fontA = new Font("Arial", Font.BOLD, 18);
    private static final Font fontB = new Font("Arial", Font.BOLD, 14);
    private static final Color lblue = new Color(135, 206, 235);
    private static final Color dblue = new Color(100, 149, 237);

    private JPanel mainPnl;
    private JPanel addPnl, modifyPnl, removePnl, maintenancePnl;

    public vehicleManagement() {
        setTitle("Vehicle Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 660);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);

        mainPnl = new JPanel();
        mainPnl.setBackground(new Color(196, 227, 244));
        mainPnl.setBounds(0, 0, 900, 640);
        mainPnl.setLayout(null);

        JPanel line1 = new JPanel();
        line1.setBackground(dblue);
        line1.setBounds(90, 0, 30, 640);

        JPanel line2 = new JPanel();
        line2.setBackground(dblue);
        line2.setBounds(150, 0, 30, 640);

        JLabel title = new JLabel("VEHICLE MANAGEMENT", SwingConstants.CENTER);
        title.setForeground(new Color (39, 58, 87));
        title.setFont(new Font ("Arial", Font.BOLD, 40));
        title.setBounds(0, 60, 900, 30);

        JButton addVehicle = new JButton("Add Vehicle");
        addVehicle.setBounds(80, 140, 740, 70);
        addVehicle.setFont(new Font("Arial", Font.BOLD, 20));

        JButton modifyVehicle = new JButton("Modify Vehicle");
        modifyVehicle.setBounds(80, 230, 740, 70);
        modifyVehicle.setFont(new Font("Arial", Font.BOLD, 20));

        JButton removeVehicle = new JButton("Remove Vehicle");
        removeVehicle.setBounds(80, 320, 740, 70);
        removeVehicle.setFont(new Font("Arial", Font.BOLD, 20));

        JButton maintenance = new JButton("Schedule Maintenance");
        maintenance.setBounds(80, 410, 740, 70);
        maintenance.setFont(new Font("Arial", Font.BOLD, 20));

        JButton back = new JButton("Back");
        back.setBounds(80, 500, 740, 70);
        back.setFont(new Font("Arial", Font.BOLD, 20));

        mainPnl.add(title);
        mainPnl.add(addVehicle);
        mainPnl.add(modifyVehicle);
        mainPnl.add(removeVehicle);
        mainPnl.add(maintenance);
        mainPnl.add(back);
        mainPnl.add(line1);
        mainPnl.add(line2);

        // panel for add / edit / remove / maintenance
        addPnl = createPanel("Add Vehicle");
        modifyPnl = createPanel("Modify Vehicle");
        removePnl = createPanel("Remove Vehicle");
        maintenancePnl = createPanel("Maintenance");

        // close application
        back.addActionListener(e -> {dispose(); new vehicleRental();});

        // button functions to switch to their respective panel
        addVehicle.addActionListener(e -> switchPnl(addPnl));
        modifyVehicle.addActionListener(e -> switchPnl(modifyPnl));
        removeVehicle.addActionListener(e -> switchPnl(removePnl));
        maintenance.addActionListener(e -> switchPnl(maintenancePnl));

        add(mainPnl);
        setVisible(true);
    }

    private JPanel createPanel(String title) {
        
        JPanel panel = new JPanel();
        panel.setBackground(new Color(196, 227, 244));
        panel.setBounds(0, 0, 900, 540);
        panel.setLayout(null);

        JLabel titlelbl = new JLabel(title.toUpperCase(), SwingConstants.CENTER);
        titlelbl.setForeground(new Color (39, 58, 87));
        titlelbl.setFont(new Font("Arial", Font.BOLD, 40));
        titlelbl.setBounds(0, 60, 900, 30);;
        panel.add(titlelbl);

        JPanel line1 = new JPanel();
        line1.setBackground(dblue);
        line1.setBounds(90, 0, 30, 640);

        JPanel line2 = new JPanel();
        line2.setBackground(dblue);
        line2.setBounds(150, 0, 30, 640);

        // Add Vehicle Panel
        if (title.equals("Add Vehicle")) {
            
            JLabel vehicleType = new JLabel("Vehicle Type:");
            vehicleType.setBounds(140, 130, 200, 20);
            panel.add(vehicleType);
            
            String[] vehicles = {"Car", "Motorcycle", "SUV"}; //type of vehicles
            JComboBox <String> vehiclesBox = new JComboBox<>(vehicles);
            vehiclesBox.setBounds(140, 150, 600, 40);
            panel.add(vehiclesBox);

            JLabel brand = new JLabel("Brand:");
            brand.setBounds(100, 210, 200, 20);
            panel.add(brand);
            
            JTextField brandFld = new JTextField();
            brandFld.setBounds(100, 230, 280, 30);
            panel.add(brandFld);

            JLabel model = new JLabel("Model(Year:");
            model.setBounds(100, 270, 200, 20);
            panel.add(model);

            JTextField modelFld = new JTextField();
            modelFld.setBounds(100, 290, 280, 30);
            panel.add(modelFld);
            
            JLabel color = new JLabel("Color:");
            color.setBounds(100, 330, 200, 20);
            panel.add(color);
            
            
            JTextField colorFld = new JTextField();
            colorFld.setBounds(100, 350, 280, 30);
            panel.add(colorFld);
//
            JLabel license = new JLabel("License Plate:");
            license.setBounds(480, 210, 200, 20);
            panel.add(license);
            
            JTextField licenseFld = new JTextField();
            licenseFld.setBounds(480, 230, 280, 30);
            panel.add(licenseFld);
            
            JLabel price = new JLabel("Rent Price:");
            price.setBounds(480, 260, 210, 20);
            panel.add(price);
            
            JTextField priceFld = new JTextField();
            priceFld.setText(" ₱ ");
            priceFld.setBounds(480, 290, 280, 30);
            panel.add(priceFld);
            
            JLabel status = new JLabel("Status:");
            status.setBounds(480, 330, 200, 20);
            panel.add(status);
            
            String[] statuses = {"Available", "Rented", "Under Maintenance"};
            JComboBox <String> statusBox = new JComboBox<>(statuses);
            statusBox.setBounds(480, 350, 280, 30);
            panel.add(statusBox);

            JButton apply = new JButton("ADD");
            apply.setBounds(300, 430, 100, 40);
            panel.add(apply);

            JButton back = new JButton("BACK");
            back.setBounds(420, 430, 100, 40);
            panel.add(back);

            back.addActionListener(e -> switchPnl(mainPnl));
        }

        else if (title.equals("Modify Vehicle")) {

              JLabel vehicleType = new JLabel("Vehicle Type:");
            vehicleType.setBounds(140, 130, 200, 20);
            panel.add(vehicleType);
            
            String[] vehicles = {"Car", "Motorcycle", "SUV"}; //type of vehicles
            JComboBox <String> vehiclesBox = new JComboBox<>(vehicles);
            vehiclesBox.setBounds(140, 150, 600, 40);
            panel.add(vehiclesBox);

            JLabel brand = new JLabel("Brand:");
            brand.setBounds(100, 210, 200, 20);
            panel.add(brand);
            
            JTextField brandFld = new JTextField();
            brandFld.setBounds(100, 230, 280, 30);
            panel.add(brandFld);

            JLabel model = new JLabel("Model(Year:");
            model.setBounds(100, 270, 200, 20);
            panel.add(model);

            JTextField modelFld = new JTextField();
            modelFld.setBounds(100, 290, 280, 30);
            panel.add(modelFld);
            
            JLabel color = new JLabel("Color:");
            color.setBounds(100, 330, 200, 20);
            panel.add(color);
            
            
            JTextField colorFld = new JTextField();
            colorFld.setBounds(100, 350, 280, 30);
            panel.add(colorFld);
//
            JLabel license = new JLabel("License Plate:");
            license.setBounds(480, 210, 200, 20);
            panel.add(license);
            
            JTextField licenseFld = new JTextField();
            licenseFld.setBounds(480, 230, 280, 30);
            panel.add(licenseFld);
            
            JLabel price = new JLabel("Rent Price:");
            price.setBounds(480, 260, 210, 20);
            panel.add(price);
            
            JTextField priceFld = new JTextField();
            priceFld.setText(" ₱ ");
            priceFld.setBounds(480, 290, 280, 30);
            panel.add(priceFld);
            
            JLabel status = new JLabel("Status:");
            status.setBounds(480, 330, 200, 20);
            panel.add(status);
            
            String[] statuses = {"Available", "Rented", "Under Maintenance"};
            JComboBox <String> statusBox = new JComboBox<>(statuses);
            statusBox.setBounds(480, 350, 280, 30);
            panel.add(statusBox);

            JButton apply = new JButton("ADD");
            apply.setBounds(300, 430, 100, 40);
            panel.add(apply);

            JButton back = new JButton("BACK");
            back.setBounds(420, 430, 100, 40);
            panel.add(back);



            back.addActionListener(e -> switchPnl(mainPnl));
        }

        // Remove Vehicle Panel
        else if (title.equals("Remove Vehicle")) {
    
            JLabel availVehicle = new JLabel("Available Vehicles: ");
            availVehicle.setBounds(60, 140, 230, 20);
            availVehicle.setFont(new Font ("Arial", Font.BOLD, 20));
            panel.add(availVehicle);
    
            String[] attributes = {"Vehicle ID", "Vehicle Type", "Status"};
            Object[][] data = {{"VR001", "SUV", "Available"},
                               {"VR002", "Car", "Available"},
                               {"VR003", "Motorcycle", "Available"}};
            
            JTable vehicleTab = new JTable(data, attributes);
            vehicleTab.setRowHeight(25);
            vehicleTab.setEnabled(false);
    
            JScrollPane scroll = new JScrollPane(vehicleTab);
            scroll.setBounds(70, 190, 750, 200);
            panel.add(scroll);
        
            JLabel vehicleLbl = new JLabel("Enter Vehicle ID to Remove:");
            vehicleLbl.setBounds(70, 410, 200, 20);
            panel.add(vehicleLbl);
        
            JTextField vehicleFld = new JTextField();
            vehicleFld.setBounds(70, 430, 280, 30);
            panel.add(vehicleFld);

            JButton remove = new JButton("REMOVE");
            remove.setBounds (580, 480, 130, 40);
            panel.add(remove);
    
            JButton back = new JButton("BACK");
            back.setBounds(720, 480, 100, 40);
            panel.add(back);
    
    
            back.addActionListener(e -> switchPnl(mainPnl));
    }


        // Scheduling Panel
        else if (title.equals("Maintenance")) {

            JLabel availVehicle = new JLabel("Available Vehicles:");
            availVehicle.setBounds(60, 140, 230, 20);
            availVehicle.setFont(new Font ("Arial", Font.BOLD, 20));
            panel.add(availVehicle);

            String[] attributes = {"Vehicle ID", "Vehicle Type", "Status"};
            Object[][] data = {{"VR001", "SUV", "Available"},
                               {"VR002", "Car", "Available"},
                               {"VR003", "Motorcycle", "Available"}};

            JTable vehicleTab = new JTable(data, attributes);
            vehicleTab.setRowHeight(25);
            vehicleTab.setEnabled(false);

            JScrollPane scroll = new JScrollPane(vehicleTab);
            scroll.setBounds(70, 190, 750, 120);
            panel.add(scroll);
            
            JLabel vehicleIdLbl = new JLabel("Enter Vehicle ID:");
            vehicleIdLbl.setBounds(100, 330, 280, 20);
            panel.add(vehicleIdLbl);

            JTextField vehicleIdFld = new JTextField();
            vehicleIdFld.setBounds(100, 350, 280, 30);
            panel.add(vehicleIdFld);

            JLabel startDate = new JLabel("Maintenance Start Date:");
            startDate.setBounds(480, 330, 280, 20);
            panel.add(startDate);

            JTextField startFld = new JTextField("MM-DD-YYYY");
            startFld.setForeground(Color.LIGHT_GRAY);
            startFld.setBounds(480, 350, 280, 30);
            panel.add(startFld);

            JLabel endDate = new JLabel("Maintenance End Date:");
            endDate.setBounds(480, 390, 280, 20);
            panel.add(endDate);

            JTextField endFld = new JTextField("MM-DD-YYYY");
            endFld.setForeground(Color.LIGHT_GRAY);
            endFld.setBounds(480, 410, 280, 30);
            panel.add(endFld);

            JLabel description = new JLabel("Description:");
            description.setBounds(100, 390, 280, 20);
            panel.add(description);

            JTextField descFld = new JTextField();
            descFld.setBounds(100, 410, 280, 30);
            panel.add(descFld);

            JButton apply = new JButton("APPLY");
            apply.setBounds (580, 480, 130, 40);
            panel.add(apply);

            JButton back = new JButton("BACK");
            back.setBounds(720, 480, 100, 40);
            panel.add(back);

            back.addActionListener(e -> switchPnl(mainPnl));
    }


        panel.add(line1);
        panel.add(line2);
        return panel;
    }

    private void switchPnl(JPanel newPnl) {
        getContentPane().removeAll(); 
        add(newPnl);
        revalidate(); 
        repaint(); 
    }

    public static void main(String[] args) {
        new vehicleManagement();
    }
}