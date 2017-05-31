import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public class AppFrame extends JFrame{
    private AppForm uiForm;
    private OutputMessageForm uiMessages;
    private OutputStatusForm uiStatus;
    public AppFrame(String title) throws SQLException, IOException {
        super(title);
        setLayout(new BorderLayout());
        uiForm = new AppForm();
        uiMessages = new OutputMessageForm();
        uiStatus = new OutputStatusForm();
        Container c = getContentPane();
        c.add(uiForm, BorderLayout.NORTH);
        c.add(uiMessages, BorderLayout.CENTER);
        c.add(uiStatus, BorderLayout.SOUTH);
    }
}

