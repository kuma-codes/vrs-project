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
import java.sql.*;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

public class manageBookings extends JFrame {

    private static final Color dblue = new Color(132, 168, 230);
    private static final Color fontColor = Color.WHITE;
    private static final Color shadowColor = new Color(143,143,143);
    private static final Color btnBGColor = new Color(92,142,175);
    private static final Color fldBGColor = new Color(240, 240, 240);
    
    private static final Border outer = BorderFactory.createBevelBorder(BevelBorder.RAISED);
    private static final Border inner = BorderFactory.createBevelBorder(BevelBorder.RAISED);
    private static final Border lineBorder = BorderFactory.createLineBorder(Color.GRAY,2);
    private static final Border empBorder = BorderFactory.createEmptyBorder(2,2,2,2);
    private static final Border lineBorder1 = BorderFactory.createLineBorder(Color.GRAY,1);
    private static final Border btnBorder = BorderFactory.createCompoundBorder(outer,inner);
    private static final Border btnBorder1 = new CompoundBorder(lineBorder1,outer);
    private static final Border Border = new CompoundBorder(lineBorder,empBorder);
    private static final Border fldBorder = new CompoundBorder(lineBorder1,empBorder);
    
    private JPanel mainPnl;
    private JPanel viewPnl, approvePnl, preparePnl;
    
    // JDBC setup
    private Connection conn;
    private final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=vRentalSystemDB;encrypt=true;trustServerCertificate=true";
    private final String DB_USER = "admin";
    private final String DB_PASS = "admin456";
    private ArrayList<String> vehicleData = new ArrayList<>();

    public manageBookings() {
        setTitle("Booking Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 620);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);
        connectToDB();

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
        title.setForeground(fontColor);
        title.setFont(new Font ("Arial", Font.BOLD, 40));
        title.setBounds(0, 40, 900, 45);
        
        JLabel titleLS = new JLabel("BOOKING MANAGEMENT", SwingConstants.CENTER);
        titleLS.setForeground(shadowColor);
        titleLS.setFont(new Font ("Arial", Font.BOLD, 40));
        titleLS.setBounds(3, 43, 900, 45);

        JButton viewBookings = new JButton("View Bookings");
        viewBookings.setBounds(80, 120, 740, 70);
        viewBookings.setFont(new Font("Arial", Font.BOLD, 21));
        viewBookings.setForeground(fontColor);
        viewBookings.setBackground(btnBGColor);
        viewBookings.setBorder(btnBorder);

        JButton approveBooking = new JButton("Approve Booking");
        approveBooking.setBounds(80, 205, 740, 70);
        approveBooking.setFont(new Font("Arial", Font.BOLD, 21));
        approveBooking.setForeground(fontColor);
        approveBooking.setBackground(btnBGColor);
        approveBooking.setBorder(btnBorder);

        JButton prepareVehicle = new JButton("Prepare Vehicle");
        prepareVehicle.setBounds(80, 290, 740, 70);
        prepareVehicle.setFont(new Font("Arial", Font.BOLD, 21));
        prepareVehicle.setForeground(fontColor);
        prepareVehicle.setBackground(btnBGColor);
        prepareVehicle.setBorder(btnBorder);
        
        JButton salesReport = new JButton("Sales Report");
        salesReport.setBounds(80, 375, 740, 70);
        salesReport.setFont(new Font("Arial", Font.BOLD, 21));
        salesReport.setForeground(fontColor);
        salesReport.setBackground(btnBGColor);
        salesReport.setBorder(btnBorder);

        JButton back = new JButton("Back");
        back.setBounds(80, 460, 740, 70);
        back.setFont(new Font("Arial", Font.BOLD, 21));
        back.setForeground(fontColor);
        back.setBackground(btnBGColor);
        back.setBorder(btnBorder);

        mainPnl.add(title);
        mainPnl.add(titleLS);
        mainPnl.add(viewBookings);
        mainPnl.add(approveBooking);
        mainPnl.add(prepareVehicle);
        mainPnl.add(salesReport);
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
        salesReport.addActionListener(e -> {dispose(); new salesReport();});
        back.addActionListener(e -> {dispose(); new vehicleRental();});

        add(mainPnl);
        setVisible(true);
       
    }
    
    private void connectToDB() {
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } 
        catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Database connection error: " + e.getMessage());
        }
    }
    
    private void closeConnection(){
        try {
        conn.close();
        }
        catch(SQLException e){
        e.printStackTrace();
        }
    }

    private JPanel createPanel(String title) {
        
        JPanel panel = new JPanel();
        panel.setBackground(new Color(196, 227, 244));
        panel.setBounds(0, 0, 900, 640);
        panel.setLayout(null);

        JLabel titlelbl = new JLabel(title.toUpperCase(), SwingConstants.CENTER);
        titlelbl.setForeground(fontColor);
        titlelbl.setFont(new Font ("Arial", Font.BOLD, 40));
        titlelbl.setBounds(0, 60, 890, 30);
        panel.add(titlelbl);
        
        JLabel titlels = new JLabel(title.toUpperCase(), SwingConstants.CENTER);
        titlels.setForeground(shadowColor);
        titlels.setFont(new Font ("Arial", Font.BOLD, 40));
        titlels.setBounds(3, 63, 890, 30);
        panel.add(titlels);

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
            search.setForeground(fontColor);
            search.setFont(new Font("Arial", Font.BOLD, 14));
            panel.add(search);
            
            JLabel searchLS = new JLabel("Search:");
            searchLS.setBounds(72, 142, 60, 25);
            searchLS.setForeground(shadowColor);
            searchLS.setFont(new Font("Arial", Font.BOLD, 14));
            panel.add(searchLS);
            
            JTextField searchFld = new JTextField();
            searchFld.setBounds(135, 140, 200, 25);
            searchFld.setBackground(fldBGColor);
            searchFld.setBorder(fldBorder);
            panel.add(searchFld);
            
            JLabel status = new JLabel("Status:");
            status.setBounds(355, 140, 50, 25);
            status.setForeground(fontColor);
            status.setFont(new Font("Arial", Font.BOLD, 14));
            panel.add(status);
            
            JLabel statusLS = new JLabel("Status:");
            statusLS.setBounds(357, 142, 50, 25);
            statusLS.setForeground(shadowColor);
            statusLS.setFont(new Font("Arial", Font.BOLD, 14));
            panel.add(statusLS);

            JComboBox <String> statusBox = new JComboBox<>(new String[]{"All","Completed", "Not Yet Returned", "Pending Approval", "Cancelled","Rejected"});
            statusBox.setBounds(410, 140, 150, 25);
            statusBox.setBackground(fldBGColor);
            panel.add(statusBox);

            JButton searchBtn = new JButton("Search");
            searchBtn.setBounds(610, 140, 100, 25);
            searchBtn.setForeground(fontColor);
            searchBtn.setBackground(btnBGColor);
            searchBtn.setBorder(btnBorder1);
            panel.add(searchBtn);

            JButton resetBtn = new JButton("Reset");
            resetBtn.setBounds(720, 140, 100, 25);
            resetBtn.setForeground(fontColor);
            resetBtn.setBackground(btnBGColor);
            resetBtn.setBorder(btnBorder1);
            panel.add(resetBtn);

            String[] attributes = {"RentalID", "Account Details", "Vehicle Details", "ReturnDate", "RentalStatus"};

            /* sample lang
            pending - booking req has been made but have not yet reviewed/confirmed by admin
            approved - booking has been reviewed / accepted
            completed - rental was fulfilled */
            Object[][] data = fetchBookingData();
            
            DefaultTableModel table = new DefaultTableModel(attributes, 0);
            JTable bookTab = new JTable(table);
            bookTab.setRowHeight(25);
            bookTab.setEnabled(false);

            JScrollPane scroll = new JScrollPane(bookTab);
            scroll.setBounds(70, 190, 750, 300);
            scroll.setBackground(fldBGColor);
            scroll.setBorder(Border);
            panel.add(scroll);

            JButton back = new JButton("BACK");
            back.setBounds(720, 520, 100, 35);
            back.setForeground(fontColor);
            back.setBackground(btnBGColor);
            back.setBorder(btnBorder1);
            panel.add(back);
            
            viewBookingFltr(data, table, "", "All");
            
            searchBtn.addActionListener(e -> {
                String keyword = searchFld.getText().toLowerCase();
                String statuses = statusBox.getSelectedItem().toString();
                viewBookingFltr(data, table, keyword, statuses);
            });

            resetBtn.addActionListener(e -> {
                searchFld.setText(" ");
                statusBox.setSelectedIndex(0);
                viewBookingFltr(data, table, "", "All");
            });

            back.addActionListener(e -> switchPnl(mainPnl));
    }


        // Approve Booking Panel
        else if (title.equals("Approve Booking")) {
            
            JLabel search = new JLabel("Search:");
            search.setBounds(70, 130, 60, 25);
            search.setForeground(fontColor);
            search.setFont(new Font("Arial", Font.BOLD, 14));
            panel.add(search);
            
            JLabel searchLS = new JLabel("Search:");
            searchLS.setBounds(72, 132, 60, 25);
            searchLS.setForeground(shadowColor);
            searchLS.setFont(new Font("Arial", Font.BOLD, 14));
            panel.add(searchLS);
            
            JTextField searchFld = new JTextField();
            searchFld.setBounds(135, 130, 420, 25);
            searchFld.setBackground(fldBGColor);
            searchFld.setBorder(fldBorder);
            panel.add(searchFld);

            JButton searchBtn = new JButton("Search");
            searchBtn.setBounds(610, 130, 100, 25);
            searchBtn.setForeground(fontColor);
            searchBtn.setBackground(btnBGColor);
            searchBtn.setBorder(btnBorder1);
            panel.add(searchBtn);

            JButton resetBtn = new JButton("Reset");
            resetBtn.setBounds(720, 130, 100, 25);
            resetBtn.setForeground(fontColor);
            resetBtn.setBackground(btnBGColor);
            resetBtn.setBorder(btnBorder1);
            panel.add(resetBtn);

            String[] attributes = {"RentalID","AccountID", "VehicleID", "RentalStatus"};
            Object[][] fullData  = fetchBookingData();
            Object[][] data = approveBookingFltr(fullData);

            DefaultTableModel table = new DefaultTableModel(attributes, 0);
            JTable pendingTab = new JTable(table);
            pendingTab.setBounds(40, 140, 280, 120);
            pendingTab.setEnabled(false);
            
            JScrollPane scroll = new JScrollPane(pendingTab);
            scroll.setBounds(70, 170, 750, 300);
            scroll.setBackground(fldBGColor);
            scroll.setBorder(Border);
            panel.add(scroll);

            JLabel bookingID = new JLabel("Enter Rental ID:");
            bookingID.setBounds(70, 490, 200, 20);
            bookingID.setForeground(fontColor);
            bookingID.setFont(new Font("Arial", Font.BOLD, 14));
            panel.add(bookingID);
            
            JLabel bookingIDLS = new JLabel("Enter Rental ID:");
            bookingIDLS.setBounds(72, 492, 200, 20);
            bookingIDLS.setForeground(shadowColor);
            bookingIDLS.setFont(new Font("Arial", Font.BOLD, 14));
            panel.add(bookingIDLS);

            JTextField bookingFld = new JTextField();
            bookingFld.setBounds(70, 515, 280, 30);
            bookingFld.setBackground(fldBGColor);
            bookingFld.setBorder(fldBorder);
            panel.add(bookingFld);
            
            JButton rejectBtn = new JButton("REJECT");
            rejectBtn.setBounds(440, 510, 130, 35);
            rejectBtn.setForeground(fontColor);
            rejectBtn.setBackground(btnBGColor);
            rejectBtn.setBorder(btnBorder1);
            panel.add(rejectBtn);
            
            JButton approveBtn = new JButton("APPROVE");
            approveBtn.setBounds(580, 510, 130, 35);
            approveBtn.setForeground(fontColor);
            approveBtn.setBackground(btnBGColor);
            approveBtn.setBorder(btnBorder1);
            panel.add(approveBtn);
            
            JButton back = new JButton("BACK");
            back.setBounds(720, 510, 100, 35);
            back.setForeground(fontColor);
            back.setBackground(btnBGColor);
            back.setBorder(btnBorder1);
            panel.add(back);
            
            adjustVehicleFltr(data, table, "", "All", 2);
            
            rejectBtn.addActionListener(e -> {
                String rentalID = bookingFld.getText();
                rejectBooking(rentalID);
            });
            
            approveBtn.addActionListener(e -> {
                String rentalID = bookingFld.getText();
                approveBooking(rentalID);

            });
            
            
            searchBtn.addActionListener(e -> {
                String keyword = searchFld.getText().toLowerCase();

                Object[][] refreshedData = approveBookingFltr(fetchBookingData());
                adjustVehicleFltr(refreshedData, table, keyword, "All", 2);
            });

            resetBtn.addActionListener(e -> {
                searchFld.setText("");

                Object[][] refreshedData = approveBookingFltr(fetchBookingData());
                adjustVehicleFltr(refreshedData, table, " ", "All", 2);
            });

            
            back.addActionListener(e -> switchPnl(mainPnl));
    }


        // Prepare Vehicle Panel
        else if (title.equals("Prepare Vehicle")) {
            
            JLabel search = new JLabel("Search:");
            search.setBounds(70, 130, 60, 25);
            search.setForeground(fontColor);
            search.setFont(new Font("Arial", Font.BOLD, 14));
            panel.add(search);
            
            JLabel searchLS = new JLabel("Search:");
            searchLS.setBounds(72, 132, 60, 25);
            searchLS.setForeground(shadowColor);
            searchLS.setFont(new Font("Arial", Font.BOLD, 14));
            panel.add(searchLS);

            JTextField searchFld = new JTextField();
            searchFld.setBounds(135, 130, 200, 25);
            searchFld.setBackground(fldBGColor);
            searchFld.setBorder(fldBorder);
            panel.add(searchFld);

            JLabel statusLbl = new JLabel("Type:");
            statusLbl.setBounds(355, 130, 40, 25);
            statusLbl.setForeground(fontColor);
            statusLbl.setFont(new Font("Arial", Font.BOLD, 14));
            panel.add(statusLbl);
            
            JLabel statusLS = new JLabel("Type:");
            statusLS.setBounds(357, 132, 40, 25);
            statusLS.setForeground(shadowColor);
            statusLS.setFont(new Font("Arial", Font.BOLD, 14));
            panel.add(statusLS);

            JComboBox <String> typeCBox = new JComboBox<>(new String[]{"All"});
            typeCBox.setBounds(405, 130, 150, 25);
            typeCBox.setBackground(fldBGColor);
            panel.add(typeCBox); 
            for (String type : fetchDistinctColumnValues("VType")) {
            typeCBox.addItem(type);
            }

            
            JButton searchBtn = new JButton("Search");
            searchBtn.setBounds(595, 130, 100, 25);
            searchBtn.setForeground(fontColor);
            searchBtn.setBackground(btnBGColor);
            searchBtn.setBorder(btnBorder1);
            panel.add(searchBtn);

            JButton resetBtn = new JButton("Reset");
            resetBtn.setBounds(705, 130, 100, 25);
            resetBtn.setForeground(fontColor);
            resetBtn.setBackground(btnBGColor);
            resetBtn.setBorder(btnBorder1);
            panel.add(resetBtn);

            String[] attributes = {"VehicleID", "Vehicle Description", "VType",  "VehicleStatus"};
            Object[][] data = fetchStatusData();
            
            DefaultTableModel table = new DefaultTableModel(attributes, 0);
            JTable prepareTab = new JTable(table);
            prepareTab.setRowHeight(25);
            prepareTab.setEnabled(false);

            JScrollPane scroll = new JScrollPane(prepareTab);
            scroll.setBounds(70, 170, 750, 300);
            scroll.setBackground(fldBGColor);
            scroll.setBorder(Border);
            panel.add(scroll);
    
            JLabel bookingID = new JLabel("Enter Vehicle ID:");
            bookingID.setBounds(70, 490, 200, 20);
            bookingID.setForeground(fontColor);
            bookingID.setFont(new Font("Arial", Font.BOLD, 14));
            panel.add(bookingID);
            
            JLabel bookingIDLS = new JLabel("Enter Vehicle ID:");
            bookingIDLS.setBounds(72, 492, 200, 20);
            bookingIDLS.setForeground(shadowColor);
            bookingIDLS.setFont(new Font("Arial", Font.BOLD, 14));
            panel.add(bookingIDLS);

            JTextField bookingFld = new JTextField();
            bookingFld.setBounds(70, 515, 300, 30);
            bookingFld.setBackground(fldBGColor);
            bookingFld.setBorder(fldBorder);
            panel.add(bookingFld);

            JButton prepareBtn = new JButton("PREPARE");
            prepareBtn.setBounds(580, 510, 120, 35);
            prepareBtn.setForeground(fontColor);
            prepareBtn.setBackground(btnBGColor);
            prepareBtn.setBorder(btnBorder1);
            panel.add(prepareBtn);
            
            JButton back = new JButton("BACK");
            back.setBounds(720, 510, 100, 35);
            back.setForeground(fontColor);
            back.setBackground(btnBGColor);
            back.setBorder(btnBorder1);
            panel.add(back);

            adjustVehicleFltr(data, table, " ", "All", 2);
            
            prepareBtn.addActionListener(e -> {
                String rentalID = bookingFld.getText().trim();
                updatePrepareVehicle(rentalID, table);
            });

    
            searchBtn.addActionListener(e -> {
                String keyword = searchFld.getText().toLowerCase();
                String statuses = typeCBox.getSelectedItem().toString();
                adjustVehicleFltr(data, table, keyword, statuses, 2);
            });

            resetBtn.addActionListener(e -> {
                searchFld.setText(" ");
                typeCBox.setSelectedIndex(0);
                adjustVehicleFltr(data, table, " ", "All", 2);
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

            String rowStr = String.join(" ", 
                row[0].toString().toLowerCase(), 
                row[1].toString().toLowerCase(), 
                row[2].toString().toLowerCase(), 
                row[3].toString().toLowerCase(), 
                row[4].toString().toLowerCase());
                boolean match = rowStr.contains(keyword.toLowerCase());
                boolean matchStatus = bookFltr.equals("All") || row[4].toString().equalsIgnoreCase(bookFltr);

                if (match && matchStatus) {
                    vModel.addRow(row);
                }

      }
    }

    private ArrayList<String> fetchDistinctColumnValues(String columnName) {
        ArrayList<String> values = new ArrayList<>();
        String query = "SELECT DISTINCT " + columnName + " FROM VEHICLES ORDER BY " + columnName;
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String val = rs.getString(columnName);
                if (val != null && !val.trim().isEmpty()) {
                    values.add(val);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading filter values: " + e.getMessage());
        }
        return values;
    }
    
    
    private void adjustVehicleFltr(Object[][] data, DefaultTableModel vModel, String keyword, String statusFltr, int status) {
        vModel.setRowCount(0); 
        for (Object[] row : data) {
            StringBuilder rowStr = new StringBuilder();
            
            for (Object item : row) {
                rowStr.append(item.toString().toLowerCase()).append(" ");
            }
        boolean keywordMatch = rowStr.toString().contains(keyword.toLowerCase());
        boolean statusMatch = statusFltr.equalsIgnoreCase("All") ||
                              row[status].toString().equalsIgnoreCase(statusFltr);

        if (keywordMatch && statusMatch) {
            vModel.addRow(row); 
        }
      }
    }

    private Object[][] approveBookingFltr(Object[][] fData) {
        ArrayList<Object[]> filter = new ArrayList<>();

        for (Object[] row : fData) {
            String status = row[4].toString();

            if (status.equalsIgnoreCase("Pending Approval")) {

                filter.add(new Object[]{row[0], row[1], row[2], row[4]});
            }
        }
        return filter.toArray(new Object[0][]);
    }


    private Object[][] fetchBookingData() {
        ArrayList<Object[]> bookings = new ArrayList<>();

        String query = "SELECT RD.RentalID, A.AccountID, A.FName, A.LName , V.VehicleID,V.Model,V.Brand,  RD.ReturnDate, RD.RentalStatus, CAST(SUBSTRING(RD.RentalID, 2, 10) AS INT) AS NumericID " +
                       "FROM RENTAL_DETAILS RD " +
                       "JOIN ACCOUNT A ON RD.AccountID = A.AccountID " +
                       "JOIN VEHICLES V ON RD.VehicleID = V.VehicleID ORDER BY NumericID";

            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    String rentalID = rs.getString("RentalID");
                    String accountID = rs.getString("AccountID") + " - " + rs.getString("FName") + " " +rs.getString("LName");
                    String vehicleID = rs.getString("VehicleID") + " - " + rs.getString("Brand") + " " +rs.getString("Model");
                    String date = rs.getString("ReturnDate");
                    String status = rs.getString("RentalStatus");
                    bookings.add(new Object[]{rentalID, accountID, vehicleID, date, status});
                }

                if (bookings.isEmpty()) {
                    System.out.println("No booking data found.");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error fetching bookings: " + e.getMessage());
            }
        return bookings.toArray(new Object[0][]);
    }
    private void rejectBooking(String RentalID) {
        
        if (RentalID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid Booking ID.");
            return;
        }

        String getDetails = "SELECT RD.RentalID, A.AccountID, V.VehicleID, RD.RentalStatus " +
                            "FROM RENTAL_DETAILS RD " +
                            "JOIN VEHICLES V ON RD.VehicleID = V.VehicleID " +
                            "JOIN ACCOUNT A ON RD.AccountID = A.AccountID WHERE RD.RentalID = ?";
    
        //update the status after approving
        String updateAccountStatus = "UPDATE ACCOUNT SET AccountStatus = 'Not Renting' WHERE AccountID = ?";
        String updateVehicleStatus = "UPDATE VEHICLES SET VehicleStatus = 'Available' WHERE VehicleID = ?";
        String updateRentalStatus = "UPDATE RENTAL_DETAILS SET RentalStatus = 'Rejected' WHERE RentalID = ? AND RentalStatus = 'Pending Approval'";

        try (
            PreparedStatement getStmt = conn.prepareStatement(getDetails);
            PreparedStatement accountStmt = conn.prepareStatement(updateAccountStatus);
            PreparedStatement vehicleStmt = conn.prepareStatement(updateVehicleStatus);
            PreparedStatement rentalStmt = conn.prepareStatement(updateRentalStatus)
        ) {
            getStmt.setString(1, RentalID);
            ResultSet rs = getStmt.executeQuery();

            if (rs.next()) {
                String accID = rs.getString("AccountID");
                String vehicleID = rs.getString("VehicleID");

                accountStmt.setString(1, accID);
                accountStmt.executeUpdate();

            vehicleStmt.setString(1, vehicleID);
            vehicleStmt.executeUpdate();

            rentalStmt.setString(1, RentalID);
            int updated = rentalStmt.executeUpdate();

                if (updated > 0) {
                    JOptionPane.showMessageDialog(this, "Booking Rejected and statuses updated.");
                    dispose();
                    new manageBookings();
                } 
                    else {
                        JOptionPane.showMessageDialog(this, "Rental ID not found or already rejected.");
                }
            } 
                else {
                    JOptionPane.showMessageDialog(this, "Rental ID not found.");
            }
        } 
            catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error rejecting booking: " + e.getMessage());
        }
    }
    
    private void approveBooking(String RentalID) {
        
        if (RentalID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid Booking ID.");
            return;
        }

        String getDetails = "SELECT RD.RentalID, A.AccountID, V.VehicleID, RD.RentalStatus " +
                            "FROM RENTAL_DETAILS RD " +
                            "JOIN VEHICLES V ON RD.VehicleID = V.VehicleID " +
                            "JOIN ACCOUNT A ON RD.AccountID = A.AccountID WHERE RD.RentalID = ?";
    
        //update the status after approving
        String updateAccountStatus = "UPDATE ACCOUNT SET AccountStatus = 'Currently Renting' WHERE AccountID = ?";
        String updateVehicleStatus = "UPDATE VEHICLES SET VehicleStatus = 'Rented' WHERE VehicleID = ?";
        String updateRentalStatus = "UPDATE RENTAL_DETAILS SET RentalStatus = 'Not Yet Returned' WHERE RentalID = ? AND RentalStatus = 'Pending Approval'";

        try (
            PreparedStatement getStmt = conn.prepareStatement(getDetails);
            PreparedStatement accountStmt = conn.prepareStatement(updateAccountStatus);
            PreparedStatement vehicleStmt = conn.prepareStatement(updateVehicleStatus);
            PreparedStatement rentalStmt = conn.prepareStatement(updateRentalStatus)
        ) {
            getStmt.setString(1, RentalID);
            ResultSet rs = getStmt.executeQuery();

            if (rs.next()) {
                String accID = rs.getString("AccountID");
                String vehicleID = rs.getString("VehicleID");

                accountStmt.setString(1, accID);
                accountStmt.executeUpdate();

            vehicleStmt.setString(1, vehicleID);
            vehicleStmt.executeUpdate();

            rentalStmt.setString(1, RentalID);
            int updated = rentalStmt.executeUpdate();

                if (updated > 0) {
                    JOptionPane.showMessageDialog(this, "Booking Approved and statuses updated.");
                    dispose();
                    new manageBookings();
                } 
                    else {
                        JOptionPane.showMessageDialog(this, "Rental ID not found or already approved.");
                }
            } 
                else {
                    JOptionPane.showMessageDialog(this, "Rental ID not found.");
            }
        } 
            catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error approving booking: " + e.getMessage());
        }
    }

    private Object[][] fetchStatusData() {
        ArrayList<Object[]> vehicleList = new ArrayList<>();
    
        String query = "SELECT VehicleID, Brand, Model, VType, VehicleStatus FROM VEHICLES WHERE VehicleStatus = 'Under Maintenance'";
    
        try (PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {
        
        while (rs.next()) {
            String vehicleID = rs.getString("VehicleID");
            String vehicleDesc = rs.getString("Model") + " " + rs.getString("Brand");
            String vType = rs.getString("VType");
            String status = rs.getString("VehicleStatus");
            vehicleList.add(new Object[]{vehicleID, vehicleDesc, vType, status});
        }
            if (vehicleList.isEmpty()) {
                System.out.println("No vehicle preparation data found.");
        }
    } 
        catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching preparation data: " + e.getMessage());
        }
        
    return vehicleList.toArray(new Object[0][]);
    }

    private void updatePrepareVehicle(String vehicleID, DefaultTableModel table) {
        
        if (vehicleID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid Rental ID.");
            return;
        }
    
        String getVehicle = "SELECT VehicleID FROM VEHICLES WHERE VehicleID = ?";
        String updateStatus = "UPDATE VEHICLES SET VehicleStatus = 'Available' WHERE VehicleID = ? AND VehicleStatus = 'Under Maintenance';"
                            + "UPDATE MAINTENANCE SET MaintenanceStatus = 'Completed' WHERE VehicleID = ? and MaintenanceStatus = 'Scheduled'";

        try (
            PreparedStatement get = conn.prepareStatement(getVehicle);
            PreparedStatement update = conn.prepareStatement(updateStatus))
        {
            get.setString(1, vehicleID);            
            ResultSet rs = get.executeQuery();

        if (rs.next()) {
            update.setString(1, vehicleID);
            update.setString(2, vehicleID);
            int rowsUpdated = update.executeUpdate();

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Vehicle status updated to Available.");
                Object[][] data = fetchStatusData();
                adjustVehicleFltr(data, table, " ", "All", 3);
            } 
             else {
                JOptionPane.showMessageDialog(this, "Vehicle is not under maintenance or already available.");
             }
        } 
         else {
            JOptionPane.showMessageDialog(this, "Vehicle ID not found.");
          }
        }    
        catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error updating vehicle status: " + e.getMessage());
        }
    }
    
    private void switchPnl(JPanel newPnl) {
        getContentPane().removeAll();
        add(newPnl);
        revalidate();
        repaint();
    }


}