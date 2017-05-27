import javax.swing.*;

public class Main {
    public static void main(String[] args){
        AppFrame appFrame = new AppFrame("TrainyTrip");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                appFrame.setSize(420,640);
                appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                appFrame.setVisible(true);
                Database.getConnection();
            }
        });


    }
}
