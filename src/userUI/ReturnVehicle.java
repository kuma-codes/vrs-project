package userUI;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class ReturnVehicle extends JFrame{
    
    public ReturnVehicle(){
        setTitle("Vehicle Rental System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 640);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);

        JPanel pan = new JPanel();
        pan.setBackground(new Color(196, 227, 244));
        pan.setBounds(0, 0, 900, 640);
        pan.setLayout(null);

        JPanel line1 = new JPanel();
        line1.setBackground(new Color(100, 149, 237)); 
        line1.setBounds(80, 0, 30, 640);

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
        
        if(UserLandingPageUI.getStatus().equals("Pending Approval")){
        label1.setText("Please wait for your approval.");
        }
        else if(UserLandingPageUI.getStatus().equals("Renting")){
        label1.setText("You Are Renting");
        }
        else{
        label1.setText("You are not currently renting a vehicle.");
        }
        
        
        //ActionListeners
//        backBtn.addActionListener(e -> {dispose(); new UserLandingPageUI();});
        
        pan.add(backBtn);
        pan.add(label1);
        pan.add(line1);
        pan.add(line2);
 
        add(pan);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        new ReturnVehicle();
    }
}
