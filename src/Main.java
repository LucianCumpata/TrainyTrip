import javax.swing.*;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
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
                appFrame.setSize(420,640);
                appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                appFrame.setVisible(true);

            }
        });


    }
}
