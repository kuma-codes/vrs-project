package userUI;

import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

public class Settings extends JFrame {
    
    //fonts and colors used that has rgb values
    private static final Font F1 = new Font("Arial", Font.BOLD, 40);
    private static final Font F2 = new Font("Arial", Font.BOLD, 20);
    private static final Font F3 = new Font("Arial", Font.BOLD, 41);
    private static final Font F4 = new Font("Arial", Font.BOLD, 16);
    private static final Font F5 = new Font("Arial", Font.BOLD, 13); 
    
    //Colors
    private static final Color fontColor = Color.WHITE;
    private static final Color shadowColor = new Color(143,143,143);
    private static final Color shadowColor1 = new Color(148,159,163);
    private static final Color fldBGColor = new Color(240, 240, 240);
    private static final Color btnBGColor = new Color(92,142,175);
    
    //Border
    private static final Border lineBorder = BorderFactory.createLineBorder(Color.GRAY,1);
    private static final Border bevelBorder = BorderFactory.createBevelBorder(BevelBorder.RAISED);
    private static final Border Border = new CompoundBorder(lineBorder,bevelBorder);
    
    public Settings(String accID) {
        setTitle("Settings");
        setSize(900, 540);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null); 

        JPanel pan = new JPanel();
        pan.setBackground(new Color(196, 227, 244));
        pan.setBounds(0, 0, 900, 540);
        pan.setLayout(null);

        JPanel line1 = new JPanel();
        line1.setBackground(new Color(132, 168, 230));
        line1.setBounds(90, 0, 30, 540);

        JPanel line2 = new JPanel();
        line2.setBackground(new Color(132, 168, 230));
        line2.setBounds(150, 0, 30, 540);
        
        //backBtn
        JButton backBtn = new JButton("<");
        backBtn.setBounds(20, 20, 60, 40);
        backBtn.setForeground(fontColor);
        backBtn.setFocusPainted(false);
        backBtn.setBackground(Color.WHITE);
        backBtn.setOpaque(false);
        backBtn.setBorderPainted(false);
        backBtn.setFont(F1);
        //backBtn shadow
        
        JButton bBtnShadow = new JButton("<");
        bBtnShadow.setBounds(23, 24, 60, 40);
        bBtnShadow.setForeground(shadowColor);
        bBtnShadow.setFocusPainted(false);
        bBtnShadow.setBackground(Color.WHITE);
        bBtnShadow.setEnabled(false);
        bBtnShadow.setOpaque(false);
        bBtnShadow.setBorderPainted(false);
        bBtnShadow.setFont(F1);
        
        JLabel settingsLabel = new JLabel("Settings",SwingConstants.CENTER);
        settingsLabel.setForeground(fontColor);
        settingsLabel.setFont(F3);
        settingsLabel.setBounds(-10, 60, 900, 50);
        
        JLabel settingsLS = new JLabel("Settings",SwingConstants.CENTER);
        settingsLS.setForeground(shadowColor);
        settingsLS.setFont(F3);
        settingsLS.setBounds(-7, 63, 900, 50);
        
        JButton ChangeName     = new JButton("Change Name");
        ChangeName.setForeground(fontColor);
        ChangeName.setFont(F2);
        ChangeName.setBackground(btnBGColor);
        ChangeName.setBorder(Border);
        JButton ChangeEmail    = new JButton("Change Email");
        ChangeEmail.setForeground(fontColor);
        ChangeEmail.setFont(F2);
        ChangeEmail.setBackground(btnBGColor);
        ChangeEmail.setBorder(Border);
        JButton ChangePassword = new JButton("Change Password");
        ChangePassword.setForeground(fontColor);
        ChangePassword.setFont(F2);
        ChangePassword.setBackground(btnBGColor);
        ChangePassword.setBorder(Border);
        
        ChangeName.setBounds(90, 160, 700, 60);
        ChangeEmail.setBounds(90, 240, 700, 60);
        ChangePassword.setBounds(90, 320, 700, 60);

        pan.add(ChangeName);
        pan.add(ChangeEmail);
        pan.add(ChangePassword);
        pan.add(backBtn);
        pan.add(bBtnShadow);
        pan.add(settingsLabel);
        pan.add(settingsLS);
        pan.add(line1);
        pan.add(line2);
        add(pan);

        backBtn.addActionListener(e->{
            dispose();
            new UserLandingPageUI(accID);
        });
        ChangeName.addActionListener(e -> {dispose(); new ChangeName(accID);});
        ChangeEmail.addActionListener(e -> {dispose(); new ChangeEmail(accID);});
        ChangePassword.addActionListener(e -> {dispose(); new ChangePassword(accID);});
        setVisible(true);
    }

    public static void main(String[] args) {
        new Settings("U1");
    }
}
