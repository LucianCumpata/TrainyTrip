import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AppForm extends JPanel{
    OutputMessageForm statusMessage = new OutputMessageForm();

    public AppForm() throws SQLException {
        Dimension size = getPreferredSize();
        size.width=480;
        size.height=480;
        setPreferredSize(size);

        setBorder(BorderFactory.createTitledBorder("Choose "));

        JLabel currentLocationLabel=new JLabel("Your location");
        JLabel destinationLocationLabel=new JLabel("Your destination");

        JComboBox startComboBox = new JComboBox();
        JComboBox endComboBox = new JComboBox();

        for (int i=0; i<21; i++){
            startComboBox.addItem(Database.extractANode(i));
            endComboBox.addItem(Database.extractANode(i));
            //System.out.println(Database.extractANode(i));
        }


        JButton searchBtn = new JButton("Search");

        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        JButton showRoutesBtn = new JButton("All routes");

        showRoutesBtn.addActionListener(new ActionListener() {
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


        gc.gridx = 2;
        gc.gridy = 0;
        add(currentLocationLabel, gc);


        gc.gridx = 2;
        gc.gridy = 2;
        add(destinationLocationLabel, gc);


        gc.gridx = 3;
        gc.gridy = 0;
        add(startComboBox, gc);


        gc.gridx = 3;
        gc.gridy = 2;
        add(endComboBox, gc);

        gc.gridx = 2;
        gc.gridy = 4;
        add(searchBtn, gc);

        gc.gridx = 3;
        gc.gridy = 4;
        add(showRoutesBtn, gc);

        gc.anchor = GridBagConstraints.LAST_LINE_END;
    }
}
