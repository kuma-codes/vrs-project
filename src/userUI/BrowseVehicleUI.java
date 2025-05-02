package userUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class BrowseVehicleUI extends JFrame {
    
    //fonts and colors used that has rgb values
        private static final Font F1 = new Font("Arial", Font.BOLD, 19);
        private static final Font F2 = new Font("Arial", Font.BOLD, 16);
        private static final Font F3 = new Font("Arial", Font.BOLD, 12);
        private static final Color LBLUE = new Color(30,144,255);
        private static final Color DBLUE = new Color(71,112,139);


     //components that mainly used for the operation.
        private DefaultListModel<String> listModel;
        private JList<String> itemList;
        private JTextField searchField;
        private JComboBox<String> typeBox;        
        private JComboBox<String> colorBox;

        
    // Sample data
        private String[] data = {
        "Motorcycle  - Suzuuki - Red", "Car - Toyota - Orange", "Car - Mitsubishi - Yellow",
        "Motorcycle - Yamaha - Red", "Car - Suzuki - Orange", "Car - Toyota - Brown"
        };
    
    BrowseVehicleUI(){
        setTitle("Vehicle Rental System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 530);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);

        JPanel pan = new JPanel();
        pan.setBackground(new Color(196, 227, 244));
        pan.setBounds(0, 0, 900, 640);
        pan.setLayout(null);

        JPanel line1 = new JPanel();
        line1.setBackground(new Color(100, 149, 237)); 
        line1.setBounds(90, 0, 30, 640);

        JPanel line2 = new JPanel();
        line2.setBackground(new Color(100, 149, 237)); 
        line2.setBounds(150, 0, 30, 640);


        
        JPanel browsePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        browsePanel.setBounds(40,40,800,520);
        browsePanel.setOpaque(false);
        

        //Browse UI Start
        
        
        JLabel sLabel = new JLabel("Search:");
        sLabel.setPreferredSize(new Dimension(50,20));
        sLabel.setFont(F3);
        sLabel.setForeground(new Color (39, 58, 87));
        searchField = new JTextField();
        searchField.setFont(F3);
        searchField.setPreferredSize(new Dimension(195,25));
        
        JLabel tLabel = new JLabel("Type:");
        tLabel.setPreferredSize(new Dimension(50,20));
        tLabel.setFont(F3);
        tLabel.setForeground(new Color (39, 58, 87));
        typeBox = new JComboBox<>(new String[]{"All", "Motorcycle", "Car"});
        typeBox.setPreferredSize(new Dimension(195,25));
        typeBox.setBackground(Color.WHITE);
        
        
        JLabel cLabel = new JLabel("Color:");
        cLabel.setPreferredSize(new Dimension(50,20));
        cLabel.setFont(F3);
        cLabel.setForeground(new Color (39, 58, 87));
        colorBox = new JComboBox<>(new String[]{"All","Brown", "Red", "Orange", "Yellow"});
        colorBox.setPreferredSize(new Dimension(195,25));
        colorBox.setBackground(Color.WHITE);
        
        
        JButton searchBtn = new JButton("Search");
        searchBtn.setPreferredSize(new Dimension(122,25));
//        searchBtn.setBackground(DBLUE);
          searchBtn.setForeground(new Color (39, 58, 87));
        
        
        JButton resetBtn = new JButton("Reset");
        resetBtn.setPreferredSize(new Dimension(122,25));
//        resetBtn.setBackground(DBLUE);
        resetBtn.setForeground(new Color (39, 58, 87));
        
        
        JLabel l1 = new JLabel("List of Available Vehicles: ");
        l1.setPreferredSize(new Dimension(500,30));
        l1.setFont(F2);
        l1.setForeground(new Color (39, 58, 87));
        
        
        
        listModel = new DefaultListModel<>();
        itemList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(itemList);
        scrollPane.setPreferredSize(new Dimension(700,300));
        
        
        JPanel btnGrp = new JPanel(new GridLayout(1,2,110,0));
        btnGrp.setOpaque(false);
        JButton backBtn = new JButton("Return");
//        backBtn.setBackground(DBLUE);        
//        backBtn.setForeground(Color.WHITE);
        JButton selectBtn = new JButton("Select");
//        selectBtn.setBackground(DBLUE);
//        selectBtn.setForeground(Color.WHITE);
        btnGrp.add(backBtn);
        btnGrp.add(selectBtn);
        btnGrp.setPreferredSize(new Dimension(265,25));


        browsePanel.add(sLabel);
        browsePanel.add(searchField);
        browsePanel.add(tLabel);
        browsePanel.add(typeBox);        
        browsePanel.add(cLabel);
        browsePanel.add(colorBox);
        browsePanel.add(l1);
        
        browsePanel.add(resetBtn);
        browsePanel.add(searchBtn);
        browsePanel.add(scrollPane);

 
        browsePanel.add(btnGrp);

        
        //Loads the unfiltered data
        loadList(data);
        
        //ActionListeners
        searchBtn.addActionListener(e -> filterList());
        backBtn.addActionListener(e -> returnToLandingPage());
        selectBtn.addActionListener(e ->{ 
            try{
                String item = itemList.getSelectedValue();
                String[] parts = item.split(" - ");
                if (parts.length == 3) {
                    String type = parts[0];
                    String brand = parts[1];
                    String color = parts[2];
                    goToVehicleDetails(type,color,brand);
                }
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(this, "No Vehicle Selected");
            }
        
        });
        resetBtn.addActionListener(e -> {
            searchField.setText("");
            typeBox.setSelectedIndex(0);
            colorBox.setSelectedIndex(0);
            loadList(data);
        });
        
        
        
        
        //Browse UI ends
        
        pan.add(browsePanel);
        pan.add(line1);
        pan.add(line2);

        add(pan);
        setVisible(true);
    }// end of constructor
    
    private void returnToLandingPage(){
    dispose();
    new UserLandingPageUI();
    }
    
    private void goToVehicleDetails(String t, String c, String b){
    dispose();
    new VehicleDetailsUI(t,c,b);
    }
    
    // Loads data into the list
    private void loadList(String[] items) {
        listModel.clear();
        for (String item : items) {
            listModel.addElement(item);
            
        }
    }

    // Filter list based on search field and category
    private void filterList() {
        String keyword = searchField.getText().toLowerCase();
        String vehicleType = typeBox.getSelectedItem().toString();
        String vehicleColor = colorBox.getSelectedItem().toString();

        listModel.clear();

        for (String item : data) {
            boolean matchesKeyword = item.toLowerCase().contains(keyword);
            boolean matchesType = vehicleType.equals("All") || item.toLowerCase().contains(vehicleType.toLowerCase());
            boolean matchesColor = vehicleColor.equals("All") || item.toLowerCase().contains(vehicleColor.toLowerCase());
            
//            boolean matchesPrice = price.equals("All") || item.toLowerCase().contains(price);
// to add later, lagyan mo lang ng price range filter wag mo kalimutan
            if (matchesKeyword && matchesType && matchesColor) {
                listModel.addElement(item);
            }
        }
    }

}

class runner1{
    public static void main(String[] args) {
        new BrowseVehicleUI();
    }
}