package adminUI;

import adminUI.vehicleRental;
import java.awt.Color;
import java.awt.Font;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import com.github.lgooddatepicker.components.DatePicker;



public class salesReport extends JFrame {

    // Fonts
    private static final Font F1 = new Font("Arial", Font.BOLD, 18);
    private static final Font F3 = new Font("Arial", Font.BOLD, 40);
    private static final Font F4 = new Font("Arial", Font.BOLD, 13);

    // Colors
    private static final Color fontColor = Color.WHITE;
    private static final Color shadowColor = new Color(143,143,143);
    private static final Color fldBGColor = new Color(240, 240, 240);
    private static final Color btnBGColor = new Color(92,142,175);

    // Borders
    private static final Border lineBorder = BorderFactory.createLineBorder(Color.GRAY,2);
    private static final Border empBorder = BorderFactory.createEmptyBorder(5,5,5,5);
    private static final Border border = new CompoundBorder(lineBorder,empBorder);

    private Connection conn;
    private final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=vRentalSystemDB;encrypt=true;trustServerCertificate=true";
    private final String DB_USER = "admin";
    private final String DB_PASS = "admin456";

    public salesReport() {
        setTitle("Sales Report");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 640);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);
        connectToDB();

        JPanel mainPnl = new JPanel();
        mainPnl.setBackground(new Color(196, 227, 244));
        mainPnl.setBounds(0, 0, 900, 640);
        mainPnl.setLayout(null);

        JPanel line1 = new JPanel();
        line1.setBackground(new Color(132, 168, 230));
        line1.setBounds(90, 0, 30, 640);

        JPanel line2 = new JPanel();
        line2.setBackground(new Color(132, 168, 230));
        line2.setBounds(150, 0, 30, 640);

        JLabel title = new JLabel("SALES REPORT", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(F3);
        title.setBounds(-10, 30, 900, 55);

        JLabel titleShadow = new JLabel("SALES REPORT", SwingConstants.CENTER);
        titleShadow.setForeground(shadowColor);
        titleShadow.setFont(F3);
        titleShadow.setBounds(-7, 33, 900, 55);

        JButton back = new JButton("<");
        back.setBounds(20, 20, 60, 40);
        back.setForeground(fontColor);
        back.setFocusPainted(false);
        back.setBackground(Color.WHITE);
        back.setOpaque(false);
        back.setBorderPainted(false);
        back.setFont(F3);
        
        back.addActionListener(e -> {
            dispose();
            new manageBookings();
        });

        JButton backShadow = new JButton("<");
        backShadow.setBounds(23, 24, 60, 40);
        backShadow.setForeground(shadowColor);
        backShadow.setFocusPainted(false);
        backShadow.setBackground(Color.WHITE);
        backShadow.setEnabled(false);
        backShadow.setOpaque(false);
        backShadow.setBorderPainted(false);
        backShadow.setFont(F3);

        // Date pickers
        DatePicker startDate = new DatePicker();
        DatePicker endDate = new DatePicker();
        startDate.setBounds(585, 105, 200, 30);
        endDate.setBounds(585, 145, 200, 30);
        startDate.getSettings().setFontValidDate(F4);
        endDate.getSettings().setFontValidDate(F4);
        startDate.getSettings().setFontInvalidDate(F4);
        endDate.getSettings().setFontInvalidDate(F4);
        startDate.getComponentDateTextField().setEditable(false);
        endDate.getComponentDateTextField().setEditable(false);
        startDate.getComponentToggleCalendarButton().setText("Select");
        endDate.getComponentToggleCalendarButton().setText("Select");

        JLabel fromLbl = new JLabel("From:");
        fromLbl.setFont(new Font("Arial", Font.BOLD, 15));
        fromLbl.setForeground(fontColor);
        fromLbl.setBounds(535, 105, 50, 30);
        
        JLabel fromLS = new JLabel("From:");
        fromLS.setFont(new Font("Arial", Font.BOLD, 15));
        fromLS.setForeground(shadowColor);
        fromLS.setBounds(537, 107, 50, 30);

        JLabel toLbl = new JLabel("To:");
        toLbl.setFont(new Font("Arial", Font.BOLD, 15));
        toLbl.setForeground(fontColor);
        toLbl.setBounds(535, 145, 50, 30);

        JLabel toLS = new JLabel("To:");
        toLS.setFont(new Font("Arial", Font.BOLD, 15));
        toLS.setForeground(shadowColor);
        toLS.setBounds(537, 147, 50, 30);

        JLabel filterLbl = new JLabel("Type:");
        filterLbl.setFont(new Font("Arial", Font.BOLD, 16));
        filterLbl.setForeground(fontColor);
        filterLbl.setBounds(95, 104, 100, 30);

        JLabel filterLS = new JLabel("Type:");
        filterLS.setFont(new Font("Arial", Font.BOLD, 16));
        filterLS.setForeground(shadowColor);
        filterLS.setBounds(97, 106, 100, 30);

        String[] types = {"All", "SUV", "Sedan", "Coupe", "Pick Up", "Van", "Minivan", 
                          "Convertible", "Hatchback", "Crossover", "Electric Vehicle", "Motorcycle"};
        
        JComboBox<String> category = new JComboBox<>(types);
        category.setBounds(155, 105, 200, 30);
        category.setBackground(fldBGColor);
        category.setFont(F4);

        DefaultTableModel columns = new DefaultTableModel(new String[]{"Name", "Vehicle Type", "Billing Date", 
                                                                       "Amount", "Payment Method"}, 0);
        JTable reportTab = new JTable(columns);
        JScrollPane scroll = new JScrollPane(reportTab);
        scroll.setBounds(90, 195, 700, 240);
        scroll.setBorder(border);

        JLabel revenue = new JLabel("Total Revenue                :         ₱ 0.00");
        revenue.setFont(F1);
        revenue.setForeground(fontColor);
        revenue.setBounds(245, 445, 400, 30);

        JLabel revenueLS = new JLabel("Total Revenue                :         ₱ 0.00");
        revenueLS.setFont(F1);
        revenueLS.setForeground(shadowColor);
        revenueLS.setBounds(247, 447, 400, 30);

        JLabel rentalCount = new JLabel("Number of Rentals         :         0");
        rentalCount.setFont(F1);
        rentalCount.setForeground(fontColor);
        rentalCount.setBounds(245, 475, 400, 30);

        JLabel rentalCountLS = new JLabel("Number of Rentals         :         0");
        rentalCountLS.setFont(F1);
        rentalCountLS.setForeground(shadowColor);
        rentalCountLS.setBounds(247, 477, 400, 30);

        JButton generate = new JButton("GENERATE REPORT");
        generate.setBounds(320, 530, 240, 40);
        generate.setFont(new Font("Arial", Font.BOLD, 16));
        generate.setBackground(btnBGColor); 
        generate.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        generate.setForeground(Color.WHITE);
        generate.setFocusPainted(false);

        mainPnl.add(title);
        mainPnl.add(titleShadow);
        mainPnl.add(back);
        mainPnl.add(backShadow);
        mainPnl.add(fromLbl);
        mainPnl.add(fromLS);
        mainPnl.add(startDate);
        mainPnl.add(toLbl);
        mainPnl.add(toLS);
        mainPnl.add(endDate);
        mainPnl.add(filterLbl);
        mainPnl.add(filterLS);
        mainPnl.add(category);
        mainPnl.add(scroll);
        mainPnl.add(revenue);
        mainPnl.add(revenueLS);
        mainPnl.add(rentalCount);
        mainPnl.add(rentalCountLS);
        mainPnl.add(generate);
        mainPnl.add(line1);
        mainPnl.add(line2);

        generate.addActionListener(e -> {
            columns.setRowCount(0);
            double totalRevenue = 0;
            int count = 0;

            String selectType = (String) category.getSelectedItem();
            LocalDate start = startDate.getDate();
            LocalDate end = endDate.getDate();

            if (start == null || end == null) {
                JOptionPane.showMessageDialog(this, "Please select both start and end dates.");
                return;
            }
            if(end.isBefore(start)){
                JOptionPane.showMessageDialog(this, "End date must be the same as or after the start date.\nPlease select a valid date range.",null,JOptionPane.ERROR_MESSAGE);
                return;
            }

            String sql = "SELECT A.FName + ' ' + A.LName AS Name, V.VType, RD.BillingDate, RD.BillAmount, RD.PaymentMethod FROM RENTAL_DETAILS RD "
                         + "JOIN VEHICLES V ON RD.VehicleID = V.VehicleID JOIN ACCOUNT A ON RD.AccountID = A.AccountID "
                         + "WHERE RD.BillingDate BETWEEN ? AND ?";


            if (!"All".equals(selectType)) {
                sql += " AND V.VType = ?";
            }


            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setDate(1, java.sql.Date.valueOf(start));
                stmt.setDate(2, java.sql.Date.valueOf(end));

                if (!"All".equals(selectType)) {
                    stmt.setString(3, selectType);
                }

                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    String name = rs.getString("Name");
                    String vtype = rs.getString("VType");
                    String bDate = rs.getString("BillingDate");
                    double amount = rs.getDouble("BillAmount");
                    String method = rs.getString("PaymentMethod");

                    columns.addRow(new Object[]{
                        name, vtype, bDate, "₱" + String.format("%.2f", amount), method
                    });

                    totalRevenue += amount;
                    count++;
                }


                revenue.setText("Total Revenue                :         ₱ " + String.format("%.2f", totalRevenue));
                rentalCount.setText("Number of Rentals         :         " + count);
                revenueLS.setText("Total Revenue                :         ₱ " + String.format("%.2f", totalRevenue));
                rentalCountLS.setText("Number of Rentals         :         " + count);
            } 
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error generating report: " + ex.getMessage());
                }
    });

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

    public static void main(String[] args) {
        new salesReport();
    }
}