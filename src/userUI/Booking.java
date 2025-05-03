package userUI;


import javax.swing.*;
import java.awt.*;

public class Booking 
{



    public Booking() 
    {
        JFrame frm = new JFrame("Vehicle Rental System");
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setSize(900, 640);
        frm.setLayout(null);
        frm.setLocationRelativeTo(null);

        JPanel pnl = new JPanel();
        pnl.setBackground(new Color(196, 227, 244));
        pnl.setBounds(0, 0, 900, 640);
        pnl.setLayout(null);

        JPanel line1 = new JPanel();
        line1.setBackground(new Color(100, 149, 237));
        line1.setBounds(90, 0, 30, 640);

        JPanel line2 = new JPanel();
        line2.setBackground(new Color(100, 149, 237));
        line2.setBounds(150, 0, 30, 640);

        JButton backBtn = new JButton("BACK");
        backBtn.setBounds(10, 20, 70, 20);
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.setBackground(new Color(135, 206, 235));
        backBtn.setOpaque(false);
        backBtn.setBorderPainted(false);
        backBtn.setFont(new Font("Arial", Font.BOLD, 12));
        
        JLabel label1 = new JLabel("",SwingConstants.CENTER);
        label1.setForeground(new Color (39, 58, 87));
        label1.setFont(new Font("Arial", Font.BOLD, 30));
        label1.setBounds(0, 270, 900, 40);
        
        if(UserLandingPageUI.getStatus()=="Not Renting"){
            JLabel bookingLbl = new JLabel("CHOOSE A VEHICLE", SwingConstants.CENTER);
            bookingLbl.setForeground(new Color (39, 58, 87));
            bookingLbl.setFont(new Font ("Arial", Font.BOLD, 40));
            bookingLbl.setBounds(0, 60, 900, 50);

            JLabel nameLbl = new JLabel("Customer Name:");
            nameLbl.setFont(new Font("Arial", Font.BOLD, 15));
            nameLbl.setForeground(new Color (39, 58, 87));
            nameLbl.setBounds(100, 130, 300, 35);
            JTextField nameFld = new JTextField(UserLandingPageUI.getUserName());
            nameFld.setEditable(false);
            nameFld.setBounds(100, 160, 650, 40);

            JLabel brandLbl = new JLabel("Brand:");
            brandLbl.setFont(new Font("Arial", Font.BOLD, 15));
            brandLbl.setForeground(new Color (39, 58, 87));
            brandLbl.setBounds(100, 200, 300, 35);
            JComboBox<String> brandCBox = new JComboBox<>();
            brandCBox.setBounds(100, 230, 650, 40);

            JLabel modelLbl = new JLabel("Model:");
            modelLbl.setFont(new Font("Arial", Font.BOLD, 15));
            modelLbl.setForeground(new Color (39, 58, 87));
            modelLbl.setBounds(100, 270, 300, 35);
            JComboBox<String> modelCBox = new JComboBox<>();
            modelCBox.setBounds(100, 300, 650, 40);

            JLabel colorLbl = new JLabel("Color:");
            colorLbl.setFont(new Font("Arial", Font.BOLD, 15));
            colorLbl.setForeground(new Color (39, 58, 87));
            colorLbl.setBounds(100, 340, 300, 35);
            JComboBox<String> colorCBox = new JComboBox<>();
            colorCBox.setBounds(100, 370, 650, 40);

            JLabel costLbl = new JLabel("Daily Rate ($):");
            colorLbl.setFont(new Font("Arial", Font.BOLD, 15));
            costLbl.setForeground(new Color (39, 58, 87));
            costLbl.setBounds(100, 410, 300, 35);
            JTextField costFld = new JTextField();
            costFld.setBounds(100, 440, 650, 40);
            costFld.setEditable(false);

            // Proceed Button
            JButton proceedBtn = new JButton("PROCEED");
            proceedBtn.setBounds(350, 520, 150, 40);

            // Sample Data
            String[] brands = {"Toyota", "Honda", "Ford"};
            String[][] models = {
                {"Corolla", "Camry"},
                {"Civic", "Accord"},
                {"Focus", "Fusion"}
            };
            String[][][] colors = {
                { {"White", "Black"}, {"Blue", "Gray"} },
                { {"Red", "Black"}, {"White", "Silver"} },
                { {"Yellow", "Black"}, {"Blue", "Red"} }
            };
            double[][][] rates = {
                { {40.0, 42.0}, {50.0, 48.0} },
                { {38.0, 36.0}, {45.0, 47.0} },
                { {35.0, 37.0}, {46.0, 44.0} }
            };

            // Load brands into ComboBox
            for (String b : brands) {
                brandCBox.addItem(b);
            }

        // Allows the user to select a car brand, which then determines the available models they can choose from.
        // It controls the filtering of models based on the selected brand.
            brandCBox.addActionListener(e -> {
                int brandIndex = brandCBox.getSelectedIndex();
                modelCBox.removeAllItems();
                colorCBox.removeAllItems();
                costFld.setText("");
                if (brandIndex >= 0) {
                    for (String m : models[brandIndex]) {
                        modelCBox.addItem(m);
                    }
                }
            });

        // Lets the user pick a specific model after choosing a brand.
        // It filters and displays available colors based on the selected brand and model.
            modelCBox.addActionListener(e -> {
                int brandIndex = brandCBox.getSelectedIndex();
                int modelIndex = modelCBox.getSelectedIndex();
                colorCBox.removeAllItems();
                costFld.setText("");
                if (brandIndex >= 0 && modelIndex >= 0) {
                    for (String c : colors[brandIndex][modelIndex]) {
                        colorCBox.addItem(c);
                    }
                }
            });

        // Enables the user to select a specific color for the chosen brand and model.
        // Once selected, it helps identify the correct rental price (daily rate) for the full vehicle choice.
            colorCBox.addActionListener(e -> {
                int brandIndex = brandCBox.getSelectedIndex();
                int modelIndex = modelCBox.getSelectedIndex();
                int colorIndex = colorCBox.getSelectedIndex();
                if (brandIndex >= 0 && modelIndex >= 0 && colorIndex >= 0) {
                    costFld.setText(String.valueOf(rates[brandIndex][modelIndex][colorIndex]));
                }
            });

            proceedBtn.addActionListener(e -> {
                int brandIndex = brandCBox.getSelectedIndex();
                int modelIndex = modelCBox.getSelectedIndex();
                int colorIndex = colorCBox.getSelectedIndex();



                try {
                    UserLandingPageUI.setBrand(brands[brandIndex]);
                    UserLandingPageUI.setModel(models[brandIndex][modelIndex]);
                    UserLandingPageUI.setColor(colors[brandIndex][modelIndex][colorIndex]);
                    UserLandingPageUI.setDailyRate(rates[brandIndex][modelIndex][colorIndex]);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frm, "Please complete all selections!");
                    return;
                }

                frm.dispose();
                new BookingDate();
            });




            pnl.add(bookingLbl);
            pnl.add(nameLbl);
            pnl.add(nameFld);
            pnl.add(brandLbl);
            pnl.add(brandCBox);
            pnl.add(modelLbl);
            pnl.add(modelCBox);
            pnl.add(colorLbl);
            pnl.add(colorCBox);
            pnl.add(costLbl);
            pnl.add(costFld);
            pnl.add(proceedBtn);
        }
            else if(UserLandingPageUI.getStatus()== "Pending Approval"){
                label1.setText("You currently have vehicle waiting for approval,");
                pnl.add(label1);
            }
                else{
                    label1.setText("You can only rent one vehicle at a time");
                    pnl.add(label1);
                }


            backBtn.addActionListener(e -> {
                frm.dispose();
                new UserLandingPageUI();
            });
        
        pnl.add(backBtn);
        pnl.add(line1);
        pnl.add(line2);

        frm.add(pnl);
        frm.setVisible(true);
    }

    public static void main(String[] args) {
        new Booking();
    }
}
