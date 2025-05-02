package adminUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTable;
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

            JLabel userDetails = new JLabel("User Details:");
            userDetails.setBounds(60, 140, 180, 20);
            userDetails.setFont(new Font ("Arial", Font.BOLD, 20));
            panel.add(userDetails);

            /*user id n customer id are in the same table bcoz userid can be the identifier whether customer/admin
              customerid used to track customer, if hindi customer u can put NULL 
            */
            String[] userNames = {"User ID","Customer ID", "Name", "Email", "Role", "Registration Date"};
            Object[][] data = {{"UR01", "CSO1", "Juan Dela Cruz", "juanDC@gmail.com", "User", "04-12-2025"},
                               {"UR02", "NULL","kesya pulido", "kez@gmail.com", "Admin", "02-07-2025"},
                               {"UR03", "NULL","sesar moreno", "zar@gmail.com", "Admin", "01-09-2025"}};

            JTable userTab = new JTable(data, userNames);
            userTab.setRowHeight(25);
            userTab.setEnabled(false);

            JScrollPane scroll = new JScrollPane(userTab);
            scroll.setBounds(70, 190, 750, 300);
            panel.add(scroll);

            JButton back = new JButton("BACK");
            back.setBounds(730, 520, 100, 40);
            panel.add(back);

            back.addActionListener(e -> switchPnl(mainPnl));
        }

        // Remove User Panel
        else if (title.equals("Remove User")) {
            
            JLabel userManagementLbl = new JLabel("User Details::");
            userManagementLbl.setBounds(60, 140, 180, 20);
            userManagementLbl.setFont(new Font ("Arial", Font.BOLD, 20));
            panel.add(userManagementLbl);

            String[] userNames = {"User ID", "Customer ID", "Name", "Role"};
            Object[][] data = {{"UR01", "CSO1", "Juan Dela Cruz", "User"},
                               {"UR02", "NULL","kesya pulido", "Admin"},
                               {"UR03", "NULL","sesar moreno", "Admin"}};

            JTable userTab = new JTable(data, userNames);
            userTab.setBounds(40, 140, 280, 120);
            userTab.setEnabled(false);
            
            JScrollPane scroll = new JScrollPane(userTab);
            scroll.setBounds(70, 190, 720, 280);
            panel.add(scroll);

            JLabel userlbl = new JLabel("Enter User ID:");
            userlbl.setBounds(70, 480, 200, 20);
            panel.add(userlbl);

            JTextField userFld = new JTextField();
            userFld.setBounds(70, 500, 280, 30);
            panel.add(userFld);

            JButton removeUserBtn = new JButton("REMOVE");
            removeUserBtn.setBounds (580, 530, 130, 40);
            panel.add(removeUserBtn);

            JButton back = new JButton("BACK");
            back.setBounds(720, 530, 100, 40);
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
        new userManagement();
    }
}
