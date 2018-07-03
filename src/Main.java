import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        AppFrame appFrame = new AppFrame("TrainyTrip");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Database.getConnection();
                try {
                    Database.extractAllNodes();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                appFrame.setSize(640,640);
                appFrame.setResizable(false);
                appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                appFrame.setVisible(true);

            }
        });


    }
}
