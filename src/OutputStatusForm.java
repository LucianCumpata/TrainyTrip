import javax.swing.*;
import java.awt.*;

public class OutputStatusForm extends JPanel{
    static JLabel statusText = new JLabel();

    public OutputStatusForm(){
        Dimension size = getPreferredSize();
        size.width = 640;
        size.height = 100;
        setBorder(BorderFactory.createTitledBorder("Status"));


        GridBagConstraints gc = new GridBagConstraints();

        gc.weighty = 0.5;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.anchor = GridBagConstraints.WEST;

        //Output status field
        gc.gridx = 1;
        gc.gridy = 1;
        add(statusText, gc);

    }

    public static void setStatusText(String t) {
        statusText.setText(t);
    }
}

