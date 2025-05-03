package userUI;

import javax.swing.*;
import java.awt.*;
import com.github.lgooddatepicker.components.DatePicker;

public class BookingDate {
    JFrame frm;
    DatePicker startDateFld, endDateFld;


    public BookingDate() 
    {
        frm = new JFrame("Booking Date");
        frm.setSize(900, 440);
        frm.setLayout(null);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        
        JButton backBtn = new JButton("BACK"); // Now labeled BACK
        backBtn.setBounds(10, 20, 70, 20);
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.setBackground(new Color(135, 206, 235));
        backBtn.setBorderPainted(false);
        backBtn.setFont(new Font("Arial", Font.BOLD, 12));

        JLabel bookingDateLbl = new JLabel("DATE OF RENT", SwingConstants.CENTER);
        bookingDateLbl.setForeground(new Color (39, 58, 87));
        bookingDateLbl.setFont(new Font ("Arial", Font.BOLD, 40));
        bookingDateLbl.setBounds(0, 60, 900, 50);

        JLabel startDateLbl = new JLabel("Rental Start Date:");
        startDateLbl.setFont(new Font("Arial", Font.BOLD, 15));
        startDateLbl.setForeground(new Color (39, 58, 87));
        startDateLbl.setBounds(100, 130, 300, 35);
        startDateFld = new DatePicker();
        startDateFld.getComponentDateTextField().setEditable(false); 
        startDateFld.getComponentToggleCalendarButton().setText("Select");
        startDateFld.setBounds(100, 160, 650, 40);

        JLabel endDateLbl = new JLabel("Rental End Date:");
        endDateLbl.setFont(new Font("Arial", Font.BOLD, 15));
        endDateLbl.setForeground(new Color (39, 58, 87));
        endDateLbl.setBounds(100, 200, 300, 35);
        endDateFld = new DatePicker();
        endDateFld.getComponentDateTextField().setEditable(false);
        endDateFld.getComponentToggleCalendarButton().setText("Select");
        endDateFld.setBounds(100, 230, 650, 40);

        JButton doneBtn = new JButton("DONE");
        doneBtn.setBounds(350, 310, 130, 40);
            
        // Done Btn    
        doneBtn.addActionListener(e -> {
            try {
                
                String startDateStr = startDateFld.getDate().toString();
                String endDateStr = endDateFld.getDate().toString();
                UserLandingPageUI.setStartDate(startDateStr);
                UserLandingPageUI.setEndDate(endDateStr);

                if (startDateStr.isEmpty() || endDateStr.isEmpty()) {
                    JOptionPane.showMessageDialog(frm, "Please enter both dates!");
                    return;
                }

                // Split the date strings into parts (year, month, day) using '-' as separator
                String[] startParts = startDateStr.split("-");
                String[] endParts = endDateStr.split("-");

                // Parse each part of the start date into integers
                int startYear = Integer.parseInt(startParts[0]);
                int startMonth = Integer.parseInt(startParts[1]);
                int startDay = Integer.parseInt(startParts[2]);

                // Parse each part of the end date into integers
                int endYear = Integer.parseInt(endParts[0]);
                int endMonth = Integer.parseInt(endParts[1]);
                int endDay = Integer.parseInt(endParts[2]);

                // Convert each date into "days since year 0"
                int startDays = startYear * 365 + (startMonth - 1) * 30 + startDay;
                int endDays = endYear * 365 + (endMonth - 1) * 30 + endDay;

                if (endDays < startDays) {
                    JOptionPane.showMessageDialog(frm, "End date must be after start date!");
                    return;
                }

                UserLandingPageUI.setTotalDays(endDays - startDays + 1); // +1 to include start day
                UserLandingPageUI.setStatus("Pending Approval");
                JOptionPane.showMessageDialog(frm, "Booking Succesfully created, please wait for approval of your booking.");
                frm.dispose();
                
                BookingSummary.showSummary(
                    UserLandingPageUI.getUserName(),
                    UserLandingPageUI.getBrand(),
                    UserLandingPageUI.getModel(),
                    UserLandingPageUI.getColor(),
                    startDateStr,
                    endDateStr,
                    UserLandingPageUI.getDailyRate(),
                    UserLandingPageUI.getStatus()
                );
                
            } 
            catch (Exception ex) {
                JOptionPane.showMessageDialog(frm, "Dates cannot be empty, please select date");
            }
        });
        
        // Back Button
        backBtn.addActionListener(e -> {
            frm.dispose();
            new Booking();
        });

        pnl.add(backBtn);
        pnl.add(bookingDateLbl);
        pnl.add(startDateLbl);
        pnl.add(startDateFld);
        pnl.add(endDateLbl);
        pnl.add(endDateFld);
        pnl.add(doneBtn);
        pnl.add(line1);
        pnl.add(line2);

        frm.add(pnl);
        frm.setVisible(true);
    }
    public static void main(String[] args) {
        new BookingDate();
    }
}
