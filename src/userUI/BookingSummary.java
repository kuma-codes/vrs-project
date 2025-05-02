package userUI;

import javax.swing.*;
import java.awt.*;

public class BookingSummary{
    public static void showSummary(String name, String brand, String model, String color, String startDate, String endDate, double dailyRate, String status) 
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

        JLabel summaryLbl = new JLabel("BOOKING SUMMARY", SwingConstants.CENTER);
        summaryLbl.setForeground(new Color (39, 58, 87));
        summaryLbl.setFont(new Font ("Arial", Font.BOLD, 40));
        summaryLbl.setBounds(0, 60, 900, 50);

        JPanel panel1 = new JPanel();
        panel1.setBackground(new Color(240, 240, 240)); 
        panel1.setBounds(30, 140, 820, 340);
        panel1.setLayout(null);

        // Booking Summary
        JLabel nameLbl = new JLabel("Customer Name: " + name);
        nameLbl.setBounds(20, 20, 260, 25);
        JLabel brandLbl = new JLabel("Car Brand: " + brand);
        brandLbl.setBounds(20, 50, 260, 25);
        JLabel modelLbl = new JLabel("Car Model: " + model);
        modelLbl.setBounds(20, 80, 260, 25);
        JLabel colorLbl = new JLabel("Car Color: " + color);
        colorLbl.setBounds(20, 110, 260, 25);
        JLabel startDateLbl = new JLabel("Rental Start Date: " + startDate);
        startDateLbl.setBounds(20, 140, 260, 25);
        JLabel endDateLbl = new JLabel("Rental End Date: " + endDate);
        endDateLbl.setBounds(20, 170, 260, 25);
        JLabel dailyRateLbl = new JLabel("Daily Rate: $" + dailyRate);
        dailyRateLbl.setBounds(20, 200, 260, 25);
        JLabel daysLbl = new JLabel("Total Rental Time: " + BookingDate.totalDays + " Days");
        daysLbl.setBounds(20, 230, 260, 25);
        double totalPrice = BookingDate.totalDays * dailyRate;
        JLabel priceLbl = new JLabel("Total Payment: $" + totalPrice);
        priceLbl.setBounds(20, 260, 260, 25);
        JLabel statusLbl = new JLabel("Status: " + status);
        statusLbl.setBounds(20, 290, 260, 25);

        // Confirm Button
        JButton confirmBtn = new JButton("DONE");
        confirmBtn.setBounds(350, 520, 130, 40);
        confirmBtn.setFocusPainted(false);
        confirmBtn.setFont(new Font("Arial", Font.BOLD, 14));
        
        confirmBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(frm, "Booking Confirmed! Thank you.");
            new ViewBookingStatus().passSummary(name,brand,model,color,startDate,endDate,dailyRate, status);
            ViewBookingStatus.show = true;
            frm.dispose();
            new userLandingPageUI();
        });

        backBtn.addActionListener(e -> {
            frm.dispose();
            new BookingDate();
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

        pnl.add(backBtn);
        pnl.add(summaryLbl);
        pnl.add(panel1);
        pnl.add(confirmBtn);
        pnl.add(line1);
        pnl.add(line2);

        frm.add(pnl);
        frm.setVisible(true);
    }
     public static void main(String[] args) {
    // Example values for the parameters
    String name = "John Doe";
    String brand = "Toyota";
    String model = "Corolla";
    String color = "Blue";
    String startDate = "2025-04-01";
    String endDate = "2025-04-07";
    double dailyRate = 30.0;
    String status = "Confirmed";
    
    // Call the method to show the booking summary
    showSummary(name, brand, model, color, startDate, endDate, dailyRate, status);
}
}
