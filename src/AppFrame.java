import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class AppFrame extends JFrame{
    private AppForm uiForm;
    private OutputMessageForm uiMessages;
    public AppFrame(String title) throws SQLException {
        super(title);
        setLayout(new BorderLayout());
        uiForm = new AppForm();
        uiMessages = new OutputMessageForm();
        Container c = getContentPane();
        c.add(uiForm, BorderLayout.NORTH);
        c.add(uiMessages, BorderLayout.CENTER);
    }
}

