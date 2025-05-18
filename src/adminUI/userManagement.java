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

public class userManagement extends JFrame {

    private static final Font fontA = new Font("Arial", Font.BOLD, 18);
    private static final Font fontB = new Font("Arial", Font.BOLD, 14);
    private static final Color lblue = new Color(135, 206, 235);
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
    private JPanel viewUsersPnl, addUserPnl, removeUserPnl;
   
    
    
    // JDBC setup
    private Connection conn;
    private final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=vRentalSystemDB;encrypt=true;trustServerCertificate=true";
    private final String DB_USER = "admin";
    private final String DB_PASS = "admin456";
    private Object[][] data;
    private boolean dbUpdated = false;
    

    public userManagement() {
        setTitle("User Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 640);
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

        JLabel title = new JLabel("USER MANAGEMENT", SwingConstants.CENTER);
        title.setForeground(fontColor);
        title.setFont(new Font ("Arial", Font.BOLD, 40));
        title.setBounds(0, 60, 900, 45);
        
        JLabel titleLS = new JLabel("USER MANAGEMENT", SwingConstants.CENTER);
        titleLS.setForeground(shadowColor);
        titleLS.setFont(new Font ("Arial", Font.BOLD, 40));
        titleLS.setBounds(3, 63, 900, 45);

        JButton viewUsers = new JButton("View Users");
        viewUsers.setBounds(80, 170, 740, 70);
        viewUsers.setFont(new Font("Arial", Font.BOLD, 21));
        viewUsers.setForeground(fontColor);
        viewUsers.setBackground(btnBGColor);
        viewUsers.setBorder(btnBorder);

        JButton remove = new JButton("Remove User");
        remove.setBounds(80, 260, 740, 70);
        remove.setFont(new Font("Arial", Font.BOLD, 21));
        remove.setForeground(fontColor);
        remove.setBackground(btnBGColor);
        remove.setBorder(btnBorder);

        JButton back = new JButton("Back");
        back.setBounds(80, 350, 740, 70);
        back.setFont(new Font("Arial", Font.BOLD, 21));
        back.setForeground(fontColor);
        back.setBackground(btnBGColor);
        back.setBorder(btnBorder);

        mainPnl.add(title);
        mainPnl.add(titleLS);
        mainPnl.add(viewUsers);
        mainPnl.add(remove);
        mainPnl.add(back);
        mainPnl.add(line1);
        mainPnl.add(line2);
        
        viewUsersPnl = createPanel("View Users");
        addUserPnl = createPanel("Add User");
        removeUserPnl = createPanel("Remove User");

        viewUsers.addActionListener(e -> switchPnl(viewUsersPnl));
        remove.addActionListener(e -> switchPnl(removeUserPnl));
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

        // View Users Panel
        if (title.equals("View Users")) {
            
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

            JLabel role = new JLabel("Role:");
            role.setBounds(355, 140, 40, 25);
            role.setForeground(fontColor);
            role.setFont(new Font("Arial", Font.BOLD, 14));
            panel.add(role);
            
            JLabel roleLS = new JLabel("Role:");
            roleLS.setBounds(357, 142, 40, 25);
            roleLS.setForeground(shadowColor);
            roleLS.setFont(new Font("Arial", Font.BOLD, 14));
            panel.add(roleLS);

            JComboBox <String> roleBox = new JComboBox<>(new String[]{"All", "Admin", "User"});
            roleBox.setBounds(405, 140, 150, 25);
            roleBox.setBackground(fldBGColor);
            panel.add(roleBox);

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

            String[] attributes = {"AccountID", "FirstName", "Surname", "DriverLicenseNo", "Email", "DateCreated", "Role", "Status"};
            data = fetchUserData();
                    
            DefaultTableModel table = new DefaultTableModel(data, attributes);
            JTable userTab = new JTable(table);
            userTab.setRowHeight(25);
            userTab.setEnabled(false);

            JScrollPane scroll = new JScrollPane(userTab);
            scroll.setBounds(70, 190, 750, 300);
            scroll.setBackground(fldBGColor);
            scroll.setBorder(Border);
            panel.add(scroll);
            
            JButton back = new JButton("BACK");
            back.setBounds(720, 520, 100, 40);
            back.setForeground(fontColor);
            back.setBackground(btnBGColor);
            back.setBorder(btnBorder1);
            panel.add(back);

            viewUserFltr(table, " ", "All");

            searchBtn.addActionListener(e -> {
                String keyword = searchFld.getText().toLowerCase();
                String selectRole = roleBox.getSelectedItem().toString();
                viewUserFltr(table, keyword, selectRole);
            });

            resetBtn.addActionListener(e -> {
                searchFld.setText("");
                roleBox.setSelectedIndex(0);
                viewUserFltr(table, "", "All");
            });

            back.addActionListener(e -> {
                if(dbUpdated){
                    JOptionPane.showMessageDialog(panel, "Database updated, changes we're made.");
                    dispose();
                    new userManagement();
                    dbUpdated = false;
                }
                    else{
                        switchPnl(mainPnl);
                    }            
            });
    }
        

        //remove user 
        else if (title.equals("Remove User")) {

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

            JLabel role = new JLabel("Role:");
            role.setBounds(355, 140, 40, 25);
            role.setForeground(fontColor);
            role.setFont(new Font("Arial", Font.BOLD, 14));
            panel.add(role);
            
            JLabel roleLS = new JLabel("Role:");
            roleLS.setBounds(357, 142, 40, 25);
            roleLS.setForeground(shadowColor);
            roleLS.setFont(new Font("Arial", Font.BOLD, 14));
            panel.add(roleLS);

            JComboBox <String> roleBox = new JComboBox<>(new String[]{"All", "Admin", "User"});
            roleBox.setBounds(405, 140, 150, 25);
            roleBox.setBackground(fldBGColor);
            panel.add(roleBox);

            JButton searchBtn = new JButton("Search");
            searchBtn.setBounds(595, 140, 100, 25);
            searchBtn.setForeground(fontColor);
            searchBtn.setBackground(btnBGColor);
            searchBtn.setBorder(btnBorder1);
            panel.add(searchBtn);

            JButton resetBtn = new JButton("Reset");
            resetBtn.setBounds(705, 140, 100, 25);
            resetBtn.setForeground(fontColor);
            resetBtn.setBackground(btnBGColor);
            resetBtn.setBorder(btnBorder1);
            panel.add(resetBtn);

            String[] attributes = {"AccountID", "FirstName", "Surname", "DriverLicenseNo", "Email", "DateCreated", "Role", "Status"};
            data = fetchUserData();

            DefaultTableModel table = new DefaultTableModel(data, attributes);
            JTable userTab = new JTable(table);
            userTab.setRowHeight(25);
            userTab.setEnabled(false);

            JScrollPane scroll = new JScrollPane(userTab);
            scroll.setBounds(85, 190, 720, 280);
            scroll.setBackground(fldBGColor);
            scroll.setBorder(Border);
            panel.add(scroll);
            
            JLabel user = new JLabel("Enter User ID:");
            user.setBounds(70, 490, 200, 20);
            user.setForeground(fontColor);
            user.setFont(new Font("Arial", Font.BOLD, 14));
            panel.add(user);
            
            JLabel userLS = new JLabel("Enter User ID:");
            userLS.setBounds(72, 492, 200, 20);
            userLS.setForeground(shadowColor);
            userLS.setFont(new Font("Arial", Font.BOLD, 14));
            panel.add(userLS);

            JTextField userFld = new JTextField();
            userFld.setBounds(70, 515, 280, 30);
            userFld.setBackground(fldBGColor);
            userFld.setBorder(fldBorder);
            panel.add(userFld);

            JButton removeUserBtn = new JButton("REMOVE");
            removeUserBtn.setBounds(565, 530, 130, 40);
            removeUserBtn.setForeground(fontColor);
            removeUserBtn.setBackground(btnBGColor);
            removeUserBtn.setBorder(btnBorder1);
            panel.add(removeUserBtn);

            JButton back = new JButton("BACK");
            back.setBounds(705, 530, 100, 40);
            back.setForeground(fontColor);
            back.setBackground(btnBGColor);
            back.setBorder(btnBorder1);
            panel.add(back);

            removeUserFltr(data, table, "", "All");

            searchBtn.addActionListener(e -> {
                String keyword = searchFld.getText().toLowerCase();
                String selectRole = roleBox.getSelectedItem().toString();
                viewUserFltr(table, keyword, selectRole);
            });

            resetBtn.addActionListener(e -> {
                searchFld.setText("");
                roleBox.setSelectedIndex(0);
                removeUserFltr(data, table, "", "All");
            });
            
            removeUserBtn.addActionListener(e -> {
                String userId = userFld.getText().trim();
                removeUser(userId, table);
            });

            back.addActionListener(e -> {
            if(dbUpdated){
                    JOptionPane.showMessageDialog(panel, "Database updated, changes we're made.");
                    dispose();
                    new userManagement();
                    dbUpdated = false;
                }
                    else{
                        switchPnl(mainPnl);
                    }
            });
    }


        panel.add(line1);
        panel.add(line2);
        return panel;
    }
    
    private void viewUserFltr(DefaultTableModel vModel, String keyword, String roleFltr) {
        vModel.setRowCount(0);  
        keyword = keyword.toLowerCase();
        data = fetchUserData();

        for (Object[] row : data) {
            String combined = String.join(" ", row[0].toString(), row[1].toString(), row[2].toString(), row[3].toString(), 
                                               row[4].toString(), row[5].toString()).toLowerCase();
            boolean match = combined.contains(keyword);
            boolean roleMatch = roleFltr.equalsIgnoreCase("All") || row[6].toString().equalsIgnoreCase(roleFltr);

            if (match && roleMatch) {
                vModel.addRow(row); 
            }
        }
    }
    
    private Object[][] fetchUserData() {
        ArrayList<Object[]> userData = new ArrayList<>();
        String query = "SELECT AccountID, FName, LName, DriverLicenseNo, Email, DateCreated, AccountRole, AccountStatus,CAST(SUBSTRING(AccountID, 2, 10) AS INT) AS NumericID FROM ACCOUNT ORDER BY NumericID";

        try (Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query)) {

        while (rs.next()) {
            String accountId = rs.getString("AccountID");
            String fName = rs.getString("FName");
            String lName = rs.getString("LName");
            String driverLicenseNo = rs.getString("DriverLicenseNo");
            String email = rs.getString("Email");
            String dateCreated = rs.getString("DateCreated");
            String accountRole = rs.getString("AccountRole");
            String accountStatus = rs.getString("AccountStatus");

            userData.add(new Object[]{accountId, fName, lName, driverLicenseNo, email, dateCreated, accountRole, accountStatus});
         }
        } 
            catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error fetching data from database: " + e.getMessage());
            }
        return userData.toArray(new Object[0][]);
    }
    
    private void removeUserFltr(Object[][] data, DefaultTableModel rModel, String keyword, String roleFltr) {
        rModel.setRowCount(0);
        keyword = keyword.toLowerCase();

        for (Object[] row : data) {
            String combined = String.join(" ", row[0].toString(), row[1].toString(), row[2].toString(), row[3].toString()).toLowerCase();
            boolean match = combined.contains(keyword);
            boolean roleMatch = roleFltr.equalsIgnoreCase("All") || row[3].toString().equalsIgnoreCase(roleFltr);

            if (match && roleMatch) {
                rModel.addRow(row);
            }
        }
    }

    private void removeUser(String userId, DefaultTableModel model) {
        String status = "";
        if (userId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a User ID.");
            return;
        }
           
        
        
        try {
            String check = "SELECT * FROM ACCOUNT WHERE AccountID = ?";
            PreparedStatement checkUser = conn.prepareStatement(check);
            checkUser.setString(1, userId);
            ResultSet rs = checkUser.executeQuery();
            if(rs.next()){
                status = rs.getString("AccountStatus");
                if (!status.equals("Not Renting")){
                JOptionPane.showMessageDialog(this, "Account is currently on transaction, cannot remove.");
                return;
            }
            }
            

            
            String query = "DELETE FROM RENTAL_DETAILS WHERE AccountID = ? "
                    + "DELETE FROM ACCOUNT WHERE AccountID = ? ";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, userId);
            stmt.setString(2, userId);

        int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "User successfully removed.");
                data = fetchUserData();
                dbUpdated = true;
                removeUserFltr(data, model, "", "All");
            } 
                else {
                    JOptionPane.showMessageDialog(this, "User ID not found.");
                }
        } 
            catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error deleting user: " + ex.getMessage());
            }
    }

    private void switchPnl(JPanel newPnl) {
        getContentPane().removeAll();
        add(newPnl);
        revalidate();
        repaint();
            
    }

}