package userUI;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;


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
        
        pan.add(line1);
        pan.add(line2);
        add(pan);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        new ReturnVehicle();
    }
}
