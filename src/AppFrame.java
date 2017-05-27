import javax.swing.*;
import java.awt.*;

public class AppFrame extends JFrame{
    private AppForm uiForm;
    private OutputMessageForm uiMessages;
    public AppFrame(String title) {
        super(title);
        setLayout(new BorderLayout());
        uiForm = new AppForm();
        uiMessages = new OutputMessageForm();
        Container c = getContentPane();
        c.add(uiForm, BorderLayout.NORTH);
        c.add(uiMessages, BorderLayout.CENTER);
    }
}

