package userUI;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.optionalusertools.DateVetoPolicy;
import java.time.LocalDate;

public class BookingDate 
{
    //Fonts
    private static final Font F1 = new Font("Arial", Font.BOLD, 18);
    private static final Font F2 = new Font("Arial", Font.BOLD, 30);
    private static final Font F3 = new Font("Arial", Font.BOLD, 40);
    private static final Font F4 = new Font("Arial", Font.BOLD, 13);
    
    //Colors
    private static final Color fontColor = Color.WHITE;
    private static final Color shadowColor = new Color(143,143,143);
    private static final Color fldBGColor = new Color(240, 240, 240);
    private static final Color btnBGColor = new Color(92,142,175);
    
    JFrame frm;
    DatePicker startDateFld, endDateFld;


    public BookingDate(String AId, String vID) 
    {
        NoPastDatesPolicy noPastDatesPolicy = new NoPastDatesPolicy();
        frm = new JFrame("Booking Date");
        frm.setSize(900, 440);
        frm.setLayout(null);
        frm.setResizable(false);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setLocationRelativeTo(null);

        JPanel pnl = new JPanel();
        pnl.setBackground(new Color(196, 227, 244));
        pnl.setBounds(0, 0, 900, 640);
        pnl.setLayout(null);

        JPanel line1 = new JPanel();
        line1.setBackground(new Color(132, 168, 230));
        line1.setBounds(90, 0, 30, 640);

        JPanel line2 = new JPanel();
        line2.setBackground(new Color(132, 168, 230));
        line2.setBounds(150, 0, 30, 640);
        
        //backBtn
        JButton backBtn = new JButton("<");
        backBtn.setBounds(20, 20, 60, 40);
        backBtn.setForeground(fontColor);
        backBtn.setFocusPainted(false);
        backBtn.setBackground(Color.WHITE);
        backBtn.setOpaque(false);
        backBtn.setBorderPainted(false);
        backBtn.setFont(F3);
        
        //backBtn shadow
        JButton bBtnShadow = new JButton("<");
        bBtnShadow.setBounds(23, 24, 60, 40);
        bBtnShadow.setForeground(shadowColor);
        bBtnShadow.setFocusPainted(false);
        bBtnShadow.setBackground(Color.WHITE);
        bBtnShadow.setEnabled(false);
        bBtnShadow.setOpaque(false);
        bBtnShadow.setBorderPainted(false);
        bBtnShadow.setFont(F3);

        JLabel bookingDateLbl = new JLabel("DATE OF RENT", SwingConstants.CENTER);
        bookingDateLbl.setForeground(fontColor);
        bookingDateLbl.setFont(F3);
        bookingDateLbl.setBounds(-10, 60, 900, 55);
        
            JLabel bookingDateLS = new JLabel("DATE OF RENT", SwingConstants.CENTER);
            bookingDateLS.setForeground(shadowColor);
            bookingDateLS.setFont(F3);
            bookingDateLS.setBounds(-7, 63, 900, 55);

        JLabel startDateLbl = new JLabel("Rental Start Date:");
        startDateLbl.setFont(F1);
        startDateLbl.setForeground(fontColor);
        startDateLbl.setBounds(90, 130, 300, 35);
        
            JLabel startDateLS = new JLabel("Rental Start Date:");
            startDateLS.setFont(F1);
            startDateLS.setForeground(shadowColor);
            startDateLS.setBounds(92, 132, 300, 35);
            
        startDateFld = new DatePicker();
        startDateFld.getSettings().setVetoPolicy(noPastDatesPolicy);
        startDateFld.getComponentDateTextField().setEditable(false); 
        startDateFld.getComponentToggleCalendarButton().setText("Select");
        startDateFld.setBounds(90, 160, 650, 40);
        startDateFld.getSettings().setFontValidDate(F4);
        startDateFld.getSettings().setFontInvalidDate(F4);


        JLabel endDateLbl = new JLabel("Rental End Date:");
        endDateLbl.setFont(F1);
        endDateLbl.setForeground(fontColor);
        endDateLbl.setBounds(90, 200, 300, 35);
        
            JLabel endDateLS = new JLabel("Rental End Date:");
            endDateLS.setFont(F1);
            endDateLS.setForeground(shadowColor);
            endDateLS.setBounds(92, 202, 300, 35);
        
        endDateFld = new DatePicker();
        endDateFld.getSettings().setVetoPolicy(noPastDatesPolicy);
        endDateFld.getComponentDateTextField().setEditable(false);
        endDateFld.getComponentToggleCalendarButton().setText("Select");
        endDateFld.setBounds(90, 230, 650, 40);
        endDateFld.getSettings().setFontValidDate(F4);
        endDateFld.getSettings().setFontInvalidDate(F4);

        // Proceed Button
        JButton proceedBtn = new JButton("PROCEED");
        proceedBtn.setBounds(360, 310, 160, 40);
        proceedBtn.setForeground(fontColor);
        proceedBtn.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        proceedBtn.setBackground(btnBGColor);
        proceedBtn.setFont(F1);
            
        // Proceed Btn    
        proceedBtn.addActionListener(e -> {
            try {
                
                String startDateStr = startDateFld.getDate().toString();
                String endDateStr = endDateFld.getDate().toString();

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

                int x = JOptionPane.showConfirmDialog(null, "Are you sure you want to proceed?",
                "Confirmation Message",JOptionPane.YES_NO_OPTION);
                
                if(x==JOptionPane.YES_OPTION){
                    frm.dispose();
                    new BookingSummary(
                            AId,
                            vID,
                            startDateFld.getDate(),
                            endDateFld.getDate(),
                            endDays - startDays + 1 // +1 to include start day
                    );
                    JOptionPane.showMessageDialog(frm, "Please check your details before confirming\n your transaction.");
                    
                }
                
                
            } 
            catch (Exception ex) {
                JOptionPane.showMessageDialog(frm, "Dates cannot be empty, please select date");
            }
        });
        
        // Back Button
        backBtn.addActionListener(e -> {
            frm.dispose();
            new Booking(AId);
        });

        pnl.add(backBtn);           pnl.add(bBtnShadow);
        pnl.add(bookingDateLbl);    pnl.add(bookingDateLS);    
        pnl.add(startDateLbl);      pnl.add(startDateLS);
        pnl.add(startDateFld);
        pnl.add(endDateLbl);        pnl.add(endDateLS);
        pnl.add(endDateFld);
        pnl.add(proceedBtn);
        pnl.add(line1);
        pnl.add(line2);

        frm.add(pnl);
        frm.setVisible(true);
    }

}

class NoPastDatesPolicy implements DateVetoPolicy {
    @Override
    public boolean isDateAllowed(LocalDate date) {
        return !date.isBefore(LocalDate.now()); // Only allow today and future
    }
}
