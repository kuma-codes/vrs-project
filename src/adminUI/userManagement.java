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

public class userManagement extends JFrame {

    private static final Font fontA = new Font("Arial", Font.BOLD, 18);
    private static final Font fontB = new Font("Arial", Font.BOLD, 14);
    private static final Color lblue = new Color(135, 206, 235);
    private static final Color dblue = new Color(100, 149, 237);

    private JPanel mainPnl;
    private JPanel viewUsersPnl, addUserPnl, removeUserPnl;

    public userManagement() {
        setTitle("User Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 640);
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

        JLabel title = new JLabel("USER MANAGEMENT", SwingConstants.CENTER);
        title.setForeground(new Color (39, 58, 87));
        title.setFont(new Font ("Arial", Font.BOLD, 40));
        title.setBounds(0, 60, 900, 30);

        JButton viewUsers = new JButton("View Users");
        viewUsers.setBounds(80, 170, 740, 70);
        viewUsers.setFont(new Font("Arial", Font.BOLD, 20));

        JButton remove = new JButton("Remove User");
        remove.setBounds(80, 260, 740, 70);
        remove.setFont(new Font("Arial", Font.BOLD, 20));

        JButton back = new JButton("Back");
        back.setBounds(80, 350, 740, 70);
         back.setFont(new Font("Arial", Font.BOLD, 20));

        mainPnl.add(title);
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

        // View Users Panel
        if (title.equals("View Users")) {

            JLabel search = new JLabel("Search:");
            search.setBounds(70, 140, 60, 25);
            panel.add(search);

            JTextField searchFld = new JTextField();
            searchFld.setBounds(130, 140, 200, 25);
            panel.add(searchFld);

            JLabel role = new JLabel("Role:");
            role.setBounds(350, 140, 40, 25);
            panel.add(role);

            JComboBox <String> roleBox = new JComboBox<>(new String[]{"All", "Admin", "User"});
            roleBox.setBounds(390, 140, 150, 25);
            panel.add(roleBox);

            JButton searchBtn = new JButton("Search");
            searchBtn.setBounds(550, 140, 100, 25);
            panel.add(searchBtn);

            JButton resetBtn = new JButton("Reset");
            resetBtn.setBounds(660, 140, 100, 25);
            panel.add(resetBtn);

            String[] userNames = {"User ID","Customer ID", "Name", "Email", "Role", "Registration Date"};
            Object[][] userData = {{"UR01", "CSO1", "Juan Dela Cruz", "juanDC@gmail.com", "User", "04-12-2025"},
                                   {"UR02", "NULL","Kesya Pulido", "kez@gmail.com", "Admin", "02-07-2025"},
                                   {"UR03", "NULL","Sesar Moreno", "zar@gmail.com", "Admin", "01-09-2025"}};

            DefaultTableModel table = new DefaultTableModel(userNames, 0);
            JTable userTab = new JTable(table);
            userTab.setRowHeight(25);
            userTab.setEnabled(false);

            JScrollPane scroll = new JScrollPane(userTab);
            scroll.setBounds(70, 190, 750, 300);
            panel.add(scroll);
            
            JButton back = new JButton("BACK");
            back.setBounds(730, 520, 100, 40);
            panel.add(back);

            viewUserFltr(userData, table, " ", "All");

            searchBtn.addActionListener(e -> {
                String keyword = searchFld.getText().toLowerCase();
                String selectRole = roleBox.getSelectedItem().toString();
                viewUserFltr(userData, table, keyword, selectRole);
            });

            resetBtn.addActionListener(e -> {
                searchFld.setText("");
                roleBox.setSelectedIndex(0);
                viewUserFltr(userData, table, "", "All");
            });

            back.addActionListener(e -> switchPnl(mainPnl));
    }
        

        //remove user 
        else if (title.equals("Remove User")) {

            JLabel search = new JLabel("Search:");
            search.setBounds(70, 140, 60, 25);
            panel.add(search);

            JTextField searchFld = new JTextField();
            searchFld.setBounds(130, 140, 200, 25);
            panel.add(searchFld);

            JLabel role = new JLabel("Role:");
            role.setBounds(350, 140, 40, 25);
            panel.add(role);

            JComboBox <String> roleBox = new JComboBox<>(new String[]{"All", "Admin", "User"});
            roleBox.setBounds(390, 140, 150, 25);
            panel.add(roleBox);

            JButton searchBtn = new JButton("Search");
            searchBtn.setBounds(550, 140, 100, 25);
            panel.add(searchBtn);

            JButton resetBtn = new JButton("Reset");
            resetBtn.setBounds(660, 140, 100, 25);
            panel.add(resetBtn);

            String[] userNames = {"User ID", "Customer ID", "Name", "Role"};
            Object[][] data = {{"UR01", "CSO1", "Juan Dela Cruz", "User"},
                               {"UR02", "NULL", "kesya pulido", "Admin"},
                               {"UR03", "NULL", "sesar moreno", "Admin"}};

            DefaultTableModel table = new DefaultTableModel(userNames, 0);
            JTable userTab = new JTable(table);
            userTab.setRowHeight(25);
            userTab.setEnabled(false);

            JScrollPane scroll = new JScrollPane(userTab);
            scroll.setBounds(70, 190, 720, 280);
            panel.add(scroll);
            
            JLabel user = new JLabel("Enter User ID:");
            user.setBounds(70, 490, 200, 20);
            panel.add(user);

            JTextField userFld = new JTextField();
            userFld.setBounds(70, 510, 280, 30);
            panel.add(userFld);

            JButton removeUserBtn = new JButton("REMOVE");
            removeUserBtn.setBounds(580, 530, 130, 40);
            panel.add(removeUserBtn);

            JButton back = new JButton("BACK");
            back.setBounds(720, 530, 100, 40);
            panel.add(back);

            removeUserFltr(data, table, " ", "All");

            searchBtn.addActionListener(e -> {
                String keyword = searchFld.getText().toLowerCase();
                String selectRole = roleBox.getSelectedItem().toString();
                removeUserFltr(data, table, keyword, selectRole);
            });

            resetBtn.addActionListener(e -> {
                searchFld.setText(" ");
                roleBox.setSelectedIndex(0);
                removeUserFltr(data, table, " ", "All");
            });

            back.addActionListener(e -> switchPnl(mainPnl));
    }


        panel.add(line1);
        panel.add(line2);
        return panel;
    }
    
    private void viewUserFltr(Object[][] data, DefaultTableModel vModel, String keyword, String roleFltr) {
        vModel.setRowCount(0); 

    for (Object[] row : data) {
        String rows = String.join(" ", 
            row[0].toString().toLowerCase(), 
            row[1].toString().toLowerCase(), 
            row[2].toString().toLowerCase(), 
            row[3].toString().toLowerCase(), 
            row[4].toString().toLowerCase(), 
            row[5].toString().toLowerCase());

        boolean match = rows.contains(keyword.toLowerCase());
        boolean matchRole = roleFltr.equals("All") || row[4].toString().equalsIgnoreCase(roleFltr);

        if (match && matchRole) {
            vModel.addRow(row);
        }
    }
}
    
    private void removeUserFltr(Object[][] data, DefaultTableModel rModel, String keyword, String roleFltr) {
        rModel.setRowCount(0);
        
    for (Object[] row : data) {
        String rows = String.join(" ", 
            row[0].toString().toLowerCase(),
            row[1].toString().toLowerCase(),
            row[2].toString().toLowerCase(),
            row[3].toString().toLowerCase()
        );
        
        boolean match = rows.contains(keyword.toLowerCase());
        boolean matchRole = roleFltr.equals("All") || row[3].toString().equalsIgnoreCase(roleFltr);
        
        if (match && matchRole) {
            rModel.addRow(row);
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
        new userManagement();
    }
}