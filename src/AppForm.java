import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppForm extends JPanel{
    OutputMessageForm statusMessage = new OutputMessageForm();

    public AppForm(){
        Dimension size = getPreferredSize();
        size.width=480;
        size.height=480;
        setPreferredSize(size);

        setBorder(BorderFactory.createTitledBorder("Choose "));

        JLabel currentLocationLabel=new JLabel("Your location");
        JLabel destinationLocationLabel=new JLabel("Your destination");

        String[] nodes = new String[]{"A","B","C","D","E","F","1","2","3"};
        JSpinner startSpinner = new JSpinner(new SpinnerListModel(nodes));
        JSpinner endSpinner = new JSpinner(new SpinnerListModel(nodes));

        JButton searchBtn = new JButton("Search");

        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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
        add(startSpinner, gc);


        gc.gridx = 3;
        gc.gridy = 2;
        add(endSpinner, gc);

        gc.gridx = 3;
        gc.gridy = 4;
        add(searchBtn, gc);

        gc.anchor = GridBagConstraints.LAST_LINE_END;
    }
}
