import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class AppForm extends JPanel{
    OutputMessageForm messageForm = new OutputMessageForm();
    OutputStatusForm statusForm =  new OutputStatusForm();

    public AppForm() throws SQLException, IOException {
        String path = "C:\\Users\\proal\\Documents\\GitHub\\TrainyTrip\\src\\images\\train_transport.png";
        File file = new File(path);
        BufferedImage image = ImageIO.read(file);
        JLabel simpleTrain = new JLabel (new ImageIcon(image));

        Dimension size = getPreferredSize();
        size.width=640;
        size.height=270;
        setPreferredSize(size);

        setBorder(BorderFactory.createTitledBorder("Choose"));

        JLabel currentLocationLabel=new JLabel("Your location");
        JLabel destinationLocationLabel=new JLabel("Your destination");


        JComboBox startComboBox = new JComboBox();
        JComboBox endComboBox = new JComboBox();

        startComboBox.getActionListeners();
        endComboBox.getActionListeners();

        startComboBox.addItem("Your location");
        endComboBox.addItem("Destination");


        for (int i=0; i<21; i++){
            startComboBox.addItem(Database.extractANode(i));
            endComboBox.addItem(Database.extractANode(i));
            //System.out.println(Database.extractANode(i));
        }


        JButton searchBtn = new JButton("Search");

        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Database.runSearch(String.valueOf(startComboBox.getSelectedItem()),String.valueOf(endComboBox.getSelectedItem()));
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });


        JButton doubleSearchBtn = new JButton("Search double");

        doubleSearchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //String startComboBoxText= String.valueOf(startComboBox.getSelectedItem());
                   // String endComboBoxText=String.valueOf(endComboBox.getSelectedItem());
                    //Database.showRoute(Database.searchSimpleRoute(startComboBoxText,endComboBoxText));
                    Database.searchDoubleRoute(String.valueOf(startComboBox.getSelectedItem()),String.valueOf(endComboBox.getSelectedItem()));
                    Database.showRoute(Database.getRouteID1(),Database.getRouteID2());
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        JButton simpleSearchBtn = new JButton("Simple search");

        simpleSearchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Database.showRoute(Database.searchSimpleRoute(String.valueOf(startComboBox.getSelectedItem()),String.valueOf(endComboBox.getSelectedItem())));
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        JButton searchXIndirectXBtn = new JButton("Search xindirect");
        searchXIndirectXBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Database.searchRouteWithLastNode(String.valueOf(startComboBox.getSelectedItem()),String.valueOf(endComboBox.getSelectedItem()));
                    // Database.extractEdges(Database.getIntermediaryStationsID(startComboBoxText,endComboBoxText));
                    //Database.returnEdges();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        JButton searchIndirectBtn = new JButton("Search indirect");

        searchIndirectBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Database.searchRoutesWithFirstNode(String.valueOf(startComboBox.getSelectedItem()),String.valueOf(endComboBox.getSelectedItem()));
                   // Database.extractEdges(Database.getIntermediaryStationsID(startComboBoxText,endComboBoxText));
                    //Database.returnEdges();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        JButton viewAllRoutesBtn = new JButton("View all routes");

        viewAllRoutesBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Database.showItinerary();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();

        gc.weighty = 0.5;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.anchor = GridBagConstraints.WEST;


        gc.gridx = 1;
        gc.gridy = 0;
        add(startComboBox, gc);

        gc.gridx = 3 ;
        gc.gridy = 0;
        add(endComboBox, gc);

        gc.gridx = 2;
        gc.gridy = 0;
        add(simpleTrain, gc);

        gc.gridx = 2;
        gc.gridy = 5;
        add(simpleSearchBtn, gc);

        gc.gridx = 2;
        gc.gridy = 4;
        add(viewAllRoutesBtn, gc);

        gc.gridx = 3;
        gc.gridy = 4;
        add(searchIndirectBtn, gc);

        gc.gridx = 3;
        gc.gridy = 5;
        add(doubleSearchBtn, gc);

        gc.gridx = 3;
        gc.gridy = 6;
        add(searchXIndirectXBtn, gc);

        gc.gridx = 3;
        gc.gridy = 7;
        add(searchBtn, gc);


        gc.anchor = GridBagConstraints.LAST_LINE_END;
    }
}
