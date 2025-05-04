package userUI;

import javax.swing.*;
import java.awt.*;

public class ViewBookingStatus extends JFrame{
    
    public static boolean show = false;

    
    
    public void showViewBookingStatus() 
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

        // BACK Button
        JButton backBtn = new JButton("<");
        backBtn.setBounds(10, 20, 50, 30);
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.setBackground(new Color(135, 206, 235));
        backBtn.setBorderPainted(false);
        backBtn.setFont(new Font("Arial", Font.BOLD, 18));
        
        if(show){
            JLabel summaryLbl = new JLabel("BOOKING SUMMARY", SwingConstants.CENTER);
            summaryLbl.setForeground(new Color (39, 58, 87));
            summaryLbl.setFont(new Font ("Arial", Font.BOLD, 40));
            summaryLbl.setBounds(0, 60, 900, 30);

            JPanel panel1 = new JPanel();
            panel1.setBackground(new Color(240, 240, 240)); 
            panel1.setBounds(30, 140, 810, 340);
            panel1.setLayout(null);

            // Booking Summary
            JLabel nameLbl = new JLabel("Customer Name: " + UserLandingPageUI.getUserName());
            nameLbl.setBounds(20, 20, 260, 25);
            JLabel brandLbl = new JLabel("Car Brand: " + UserLandingPageUI.getBrand());
            brandLbl.setBounds(20, 50, 260, 25);
            JLabel modelLbl = new JLabel("Car Model: " + UserLandingPageUI.getModel());
            modelLbl.setBounds(20, 80, 260, 25);
            JLabel colorLbl = new JLabel("Car Color: " + UserLandingPageUI.getColor());
            colorLbl.setBounds(20, 110, 260, 25);
            JLabel startDateLbl = new JLabel("Rental Start Date: " + UserLandingPageUI.getStartDate());
            startDateLbl.setBounds(20, 140, 260, 25);
            JLabel endDateLbl = new JLabel("Rental End Date: " + UserLandingPageUI.getEndDate());
            endDateLbl.setBounds(20, 170, 260, 25);
            JLabel dailyRateLbl = new JLabel("Daily Rate: $" + UserLandingPageUI.getDailyRate());
            dailyRateLbl.setBounds(20, 200, 260, 25);
            JLabel daysLbl = new JLabel("Total Rental Time: " + UserLandingPageUI.getTotalDays() + " Days");
            daysLbl.setBounds(20, 230, 260, 25);
            double totalPrice = UserLandingPageUI.getTotalDays() * UserLandingPageUI.getDailyRate();
            JLabel priceLbl = new JLabel("Total Payment: $" + totalPrice);
            priceLbl.setBounds(20, 260, 260, 25);
            JLabel statusLbl = new JLabel("Status: " + UserLandingPageUI.getStatus());
            statusLbl.setBounds(20, 290, 260, 25);

            // Cancel Button
            JButton cancelBookingBtn = new JButton("Cancel Booking");
            cancelBookingBtn.setBounds(360, 510, 160, 40);
            cancelBookingBtn.setFocusPainted(false);
            cancelBookingBtn.setFont(new Font("Arial", Font.BOLD, 14));

            cancelBookingBtn.addActionListener(e -> {
                int ans = JOptionPane.showConfirmDialog(frm,"Are you sure you want to cancel?" , "Cancellation", JOptionPane.YES_NO_OPTION);
                if(ans == JOptionPane.YES_OPTION && UserLandingPageUI.getStatus() == "Pending Approval"){
                JOptionPane.showMessageDialog(frm,"Booking Succesfully Cancelled."
                        , "Cancellation", JOptionPane.INFORMATION_MESSAGE);
                UserLandingPageUI.setStatus("Not Renting");
                show = false;
                frm.dispose();
//                new UserLandingPageUI();
                }
                    else if(UserLandingPageUI.getStatus() != "Pending Approval"){
                    JOptionPane.showMessageDialog(frm,"Cannot Cancel Approved Booking"
                            , "Cancellation", JOptionPane.WARNING_MESSAGE);
                    }

            });

            panel1.add(nameLbl);
            panel1.add(brandLbl);
            panel1.add(modelLbl);
            panel1.add(colorLbl);
            panel1.add(startDateLbl);
            panel1.add(endDateLbl);
            panel1.add(dailyRateLbl); 
            panel1.add(daysLbl);
            panel1.add(priceLbl);
            panel1.add(statusLbl);

            pnl.add(summaryLbl);
            pnl.add(panel1);
            pnl.add(cancelBookingBtn);
        }
            else{
               JLabel l1 = new JLabel("You have no booking at the moment");
               l1.setForeground(new Color (39, 58, 87));
               l1.setFont(new Font("Arial", Font.BOLD, 30));
               l1.setBounds(200, 270, 900, 40);
               pnl.add(l1);
            }

        backBtn.addActionListener(e -> {
        frm.dispose();
//        new UserLandingPageUI();
        });
        
        pnl.add(backBtn);
        pnl.add(line1);
        pnl.add(line2);
        frm.add(pnl);
        
        frm.setVisible(true);
    }
     public static void main(String[] args) {
    ViewBookingStatus vbs = new ViewBookingStatus();
    vbs.show = true; 
    vbs.showViewBookingStatus();
}
}
