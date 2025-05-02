package adminUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import java.util.Arrays;

public class manageBookings extends JFrame {

    private static final Font fontA = new Font("Arial", Font.BOLD, 18);
    private static final Font fontB = new Font("Arial", Font.BOLD, 14);
    private static final Color lblue = new Color(135, 206, 235);
    private static final Color dblue = new Color(100, 149, 237);

    private JPanel mainPnl;
    private JPanel viewPnl, approvePnl, preparePnl;

    public manageBookings() {
        setTitle("Booking Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 620);
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

        JLabel title = new JLabel("BOOKING MANAGEMENT", SwingConstants.CENTER);
        title.setForeground(new Color (39, 58, 87));
        title.setFont(new Font ("Arial", Font.BOLD, 40));
        title.setBounds(0, 60, 900, 30);

        JButton viewBookings = new JButton("View Bookings");
        viewBookings.setBounds(80, 140, 740, 70);
        viewBookings.setFont(new Font("Arial", Font.BOLD, 20));

        JButton approveBooking = new JButton("Approve Booking");
        approveBooking.setBounds(80, 230, 740, 70);
        approveBooking.setFont(new Font("Arial", Font.BOLD, 20));

        JButton prepareVehicle = new JButton("Prepare Vehicle");
        prepareVehicle.setBounds(80, 320, 740, 70);
        prepareVehicle.setFont(new Font("Arial", Font.BOLD, 20));

        JButton back = new JButton("Back");
        back.setBounds(80, 410, 740, 70);
        back.setFont(new Font("Arial", Font.BOLD, 20));

        mainPnl.add(title);
        mainPnl.add(viewBookings);
        mainPnl.add(approveBooking);
        mainPnl.add(prepareVehicle);
        mainPnl.add(back);
        mainPnl.add(line1);
        mainPnl.add(line2);

        // panel for view / approve / prepare 
        viewPnl = createPanel("View Bookings");
        approvePnl = createPanel("Approve Booking");
        preparePnl = createPanel("Prepare Vehicle");

        // button functions to switch to their respective panel
        viewBookings.addActionListener(e -> switchPnl(viewPnl));
        approveBooking.addActionListener(e -> switchPnl(approvePnl));
        prepareVehicle.addActionListener(e -> switchPnl(preparePnl));
        back.addActionListener(e -> {dispose(); new vehicleRental();});

        add(mainPnl);
        setVisible(true);
    }

    private JPanel createPanel(String title) {
        
        JPanel panel = new JPanel();
        panel.setBackground(new Color(196, 227, 244));
        panel.setBounds(0, 0, 900, 640);
        panel.setLayout(null);

        JLabel titlelbl = new JLabel(title.toUpperCase(), SwingConstants.CENTER);
        titlelbl.setForeground(new Color (39, 58, 87));
        titlelbl.setFont(new Font ("Arial", Font.BOLD, 40));
        titlelbl.setBounds(0, 60, 900, 30);
        panel.add(titlelbl);

        JPanel line1 = new JPanel();
        line1.setBackground(dblue);
        line1.setBounds(90, 0, 30, 640);

        JPanel line2 = new JPanel();
        line2.setBackground(dblue);
        line2.setBounds(150, 0, 30, 640);

        // View New Bookings Panel
        if (title.equals("View Bookings")) {

            JLabel search = new JLabel("Search:");
            search.setBounds(70, 140, 60, 25);
            panel.add(search);
            
            JTextField searchFld = new JTextField();
            searchFld.setBounds(130, 140, 200, 25);
            panel.add(searchFld);
            
            JLabel status = new JLabel("Status:");
            status.setBounds(350, 140, 50, 25);
            panel.add(status);

            JComboBox <String> statusBox = new JComboBox<>(new String[]{"All", "Approved", "Not Approved" ,"Pending", "Completed"});
            statusBox.setBounds(410, 140, 150, 25);
            panel.add(statusBox);

            JButton searchBtn = new JButton("Search");
            searchBtn.setBounds(570, 140, 100, 25);
            panel.add(searchBtn);

            JButton reset = new JButton("Reset");
            reset.setBounds(680, 140, 100, 25);
            panel.add(reset);

            String[] attributes = {"BookingID", "Name", "Vehicle ID", "Rental Date", "Status"};

            /* sample lang
            pending - booking req has been made but have not yet reviewed/confirmed by admin
            approved - booking has been reviewed / accepted
            completed - rental was fulfilled */
            Object[][] data = {{"B001", "Juan Dela Cruz", "VR001", "2025-05-01", "Pending"},
                               {"B002", "Kisha Bagayaua Pulido Santos", "VR002", "2025-05-03", "Approved"},
                               {"B003", "Sar Olivar Moreno", "VR003", "2025-05-05", "Completed"},
                               {"B004", "Nelo Batumbakal", "VR004", "2025-05-06", "Not Approved"}};

            DefaultTableModel table = new DefaultTableModel(attributes, 0);
            JTable bookTab = new JTable(table);
            bookTab.setRowHeight(25);
            bookTab.setEnabled(false);

            JScrollPane scroll = new JScrollPane(bookTab);
            scroll.setBounds(70, 190, 750, 300);
            panel.add(scroll);

            JButton back = new JButton("BACK");
            back.setBounds(730, 520, 100, 40);
            panel.add(back);
            
            viewBookingFltr(data, table, " ", "ALL");
            
            searchBtn.addActionListener(e -> {
                String keyword = searchFld.getText().toLowerCase();
                String statuses = statusBox.getSelectedItem().toString();
                viewBookingFltr(data, table, keyword, statuses);
            });

            reset.addActionListener(e -> {
                searchFld.setText(" ");
                statusBox.setSelectedIndex(0);
                viewBookingFltr(data, table, " ", "All");
            });

            back.addActionListener(e -> switchPnl(mainPnl));
    }


        // Approve Booking Panel
        else if (title.equals("Approve Booking")) {
            
            JLabel search = new JLabel("Search:");
            search.setBounds(70, 140, 60, 25);
            panel.add(search);
            
            JTextField searchFld = new JTextField();
            searchFld.setBounds(130, 140, 200, 25);
            panel.add(searchFld);

            JLabel status = new JLabel("Status:");
            status.setBounds(350, 140, 50, 25);
            panel.add(status);

            JComboBox <String> statusBox = new JComboBox<>(new String[]{"All", "Pending", "Approved"});
            statusBox.setBounds(410, 140, 150, 25);
            panel.add(statusBox);

            JButton searchBtn = new JButton("Search");
            searchBtn.setBounds(570, 140, 100, 25);
            panel.add(searchBtn);

            JButton resetBtn = new JButton("Reset");
            resetBtn.setBounds(680, 140, 100, 25);
            panel.add(resetBtn);

            // Sample data for pending bookings
            String[] attributes = {"Booking ID", "Vehicle ID", "Status"};
            Object[][] data = {{"B001", "VR001", "Not Approved"}};
    
            DefaultTableModel table = new DefaultTableModel(attributes, 0);
            JTable pendingTab = new JTable(table);
            pendingTab.setBounds(40, 140, 280, 120);
            pendingTab.setEnabled(false);
            
            JScrollPane scroll = new JScrollPane(pendingTab);
            scroll.setBounds(70, 170, 750, 300);
            panel.add(scroll);

            JLabel bookingID = new JLabel("Enter Booking ID:");
            bookingID.setBounds(70, 490, 200, 20);
            panel.add(bookingID);

            JTextField bookingFld = new JTextField();
            bookingFld.setBounds(70, 510, 280, 30);
            panel.add(bookingFld);

            JButton approveBtn = new JButton("APPROVE");
            approveBtn.setBounds(580, 530, 130, 40);
            panel.add(approveBtn);

            JButton back = new JButton("BACK");
            back.setBounds(720, 530, 100, 40);
            panel.add(back);
            
            adjustVehicleFltr(data, table, " ", "All", 2);
            
            searchBtn.addActionListener(e -> {
                String keyword = searchFld.getText().toLowerCase();
                String statuses = statusBox.getSelectedItem().toString();
                adjustVehicleFltr(data, table, keyword, statuses, 2);
            });

            resetBtn.addActionListener(e -> {
                searchFld.setText(" ");
                statusBox.setSelectedIndex(0);
                adjustVehicleFltr(data, table, " ", "All", 2);
            });

            back.addActionListener(e -> switchPnl(mainPnl));
    }


        // Prepare Vehicle Panel
        else if (title.equals("Prepare Vehicle")) {
            
            JLabel search = new JLabel("Search:");
            search.setBounds(70, 140, 60, 25);
            panel.add(search);

            JTextField searchFld = new JTextField();
            searchFld.setBounds(130, 140, 200, 25);
            panel.add(searchFld);

            JLabel statusLbl = new JLabel("Status:");
            statusLbl.setBounds(350, 140, 50, 25);
            panel.add(statusLbl);

            JComboBox <String> statusBox = new JComboBox<>(new String[]{"All", "Approved", "In Preparation", "Prepared"});
            statusBox.setBounds(410, 140, 150, 25);
            panel.add(statusBox);

            JButton searchBtn = new JButton("Search");
            searchBtn.setBounds(570, 140, 100, 25);
            panel.add(searchBtn);

            JButton resetBtn = new JButton("Reset");
            resetBtn.setBounds(680, 140, 100, 25);
            panel.add(resetBtn);

            String[] attributes = {"Booking ID", "Vehicle", "Customer Name", "Pickup Date", "Status"};
            Object[][] data = {{"B004", "SUV", "Juan Dela Cruz", "2025-05-02", "Approved"},
                               {"B005", "Motorcycle", "kesya pulido", "2025-05-07", "In Preparation"},
                               {"B006", "Car", "sesar moreno", "2025-05-12", "Prepared"}};

            DefaultTableModel table = new DefaultTableModel(attributes, 0);
            JTable prepareTab = new JTable(table);
            prepareTab.setRowHeight(25);
            prepareTab.setEnabled(false);

            JScrollPane scroll = new JScrollPane(prepareTab);
            scroll.setBounds(70, 190, 750, 300);
            panel.add(scroll);
    
            JButton back = new JButton("BACK");
            back.setBounds(730, 520, 100, 40);
            panel.add(back);

            adjustVehicleFltr(data, table, " ", "All", 4);
    
            searchBtn.addActionListener(e -> {
                String keyword = searchFld.getText().toLowerCase();
                String statuses = statusBox.getSelectedItem().toString();
                adjustVehicleFltr(data, table, keyword, statuses, 4);
            });

            resetBtn.addActionListener(e -> {
                searchFld.setText(" ");
                statusBox.setSelectedIndex(0);
                adjustVehicleFltr(data, table, " ", "All", 4);
            });

        
            back.addActionListener(e -> switchPnl(mainPnl));
    }

        panel.add(line1);
        panel.add(line2);
        return panel;
    }
    
    private void viewBookingFltr(Object[][] data, DefaultTableModel vModel, String keyword, String bookFltr) {
        vModel.setRowCount(0);
        
    for (Object[] row : data) {
        String rows = String.join(" ", 
            row[0].toString().toLowerCase(), 
            row[1].toString().toLowerCase(), 
            row[2].toString().toLowerCase(), 
            row[3].toString().toLowerCase(), 
            row[4].toString().toLowerCase());

        boolean match = rows.contains(keyword.toLowerCase());
        boolean matchStatus = bookFltr.equals("All") || row[4].toString().equalsIgnoreCase(bookFltr);

        if (match && matchStatus) {
            vModel.addRow(row);
        }
    }
}

    private void adjustVehicleFltr(Object[][] data, DefaultTableModel rmodel, String keyword, String statusFltr, int statusIndex) {
    rmodel.setRowCount(0);

    for (Object[] row : data) {
        String rows = String.join(" ",
            Arrays.stream(row).map(Object::toString).map(String::toLowerCase).toArray(String[]::new)
        );

        boolean match = rows.contains(keyword.toLowerCase());
        boolean matchStatus = statusFltr.equals("All") || row[statusIndex].toString().equalsIgnoreCase(statusFltr);

        if (match && matchStatus) {
            rmodel.addRow(row);
        }
    }
}

    private void switchPnl(JPanel newPnl) {
        getContentPane().removeAll();
        add(newPnl);
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        new manageBookings();
    }
}