
import javax.swing.*;
import java.awt.*;

public class OutputMessageForm extends JPanel{
    static JLabel outputText = new JLabel();

    public OutputMessageForm() {
        Dimension size = getPreferredSize();
        size.width = 420;
        size.height = 100;
        setBorder(BorderFactory.createTitledBorder("Output"));


        GridBagConstraints gc = new GridBagConstraints();

        gc.weighty = 0.5;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.anchor = GridBagConstraints.WEST;

        //Output text field
        gc.gridx = 3;
        gc.gridy = 8;
        add(outputText, gc);

    }

    public static void setOutputTxt(String t) {
        outputText.setText(t);
    }
}
