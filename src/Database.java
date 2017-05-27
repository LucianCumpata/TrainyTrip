import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Database {
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String USER = "luci";
    private static final String PASSWORD = "andreea";
    private static Connection connection = null;
    private static Statement stmt = null;
    private static Properties props = new Properties();

    private Database() {
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                createConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }


    public static void createConnection() throws SQLException {
        props.setProperty("user", USER);
        props.setProperty("password", PASSWORD);
        connection = DriverManager.getConnection(URL, props);
        String message="Status:Connection established";
        System.out.println(message);
        OutputMessageForm.setOutputTxt(message);
    }

    public static void closeConnection() throws SQLException {
        connection.close();
        System.out.println("Status:connection closed");
    }




    public static void commit() throws SQLException {
        try {
            //connection.setAutoCommit(false);

            //If there is NO errors
            System.out.println("Status:commiting data here...");
            connection.commit();
        } catch (SQLException e) {
            //If there is any error
            rollback();
        }
    }

    public static void rollback() throws SQLException {
        connection.rollback();
    }
}
