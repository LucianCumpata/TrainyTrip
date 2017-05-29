import java.sql.*;
import java.util.Properties;

public class Database {
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String USER = "luci";
    private static final String PASSWORD = "andreea";
    private static Connection connection = null;
    private static Statement stmt = null;
    private static Properties props = new Properties();
    private static String[] nodes = new String[100];

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

    public static String extractANode(int j) throws SQLException{
        getConnection();
        String sql="SELECT NAME FROM NODES";
        PreparedStatement preStatement = connection.prepareStatement(sql);
        ResultSet result = preStatement.executeQuery();
        int i=0;
        while (result.next()){
            nodes[i]=result.getString(1);
            //System.out.println(nodes[i]);
            i++;

            // System.out.println(result.getString(1));
        }
        return nodes[j];
    }

    public static void extractAllNodes() throws SQLException{
        getConnection();
        String sql="SELECT NAME FROM NODES";
        PreparedStatement preStatement = connection.prepareStatement(sql);
        ResultSet result = preStatement.executeQuery();
        int i=0;
        while (result.next()){
            nodes[i]=result.getString(1);
            //System.out.println(nodes[i]);
            i++;
           // System.out.println(result.getString(1));
        }

    }

    public static void showItinerary() throws SQLException {
        getConnection();
        String sql="select r.id as \"Route id\", n1.name as \"Current station\", n2.name as \"Destination\" from ROUTES r\n" +
                "join NODES n1 on r.start_point=n1.id\n" +
                "join nodes n2 on r.end_point=n2.id";
        PreparedStatement preStatement = connection.prepareStatement(sql);
        ResultSet result = preStatement.executeQuery();
        String[] message = new String[10];
        int i=0;
        while (result.next()){
            message[i]=result.getInt(1)+" "+ result.getString(2) + " " +result.getString(3);
            OutputMessageForm.setOutputTxt(message[i]);
            System.out.println(message[i]);
            i++;
        }

    }

    public static void searchSimpleRoute (String start, String end) throws SQLException{
        getConnection();
        try (Statement stmt = connection.createStatement();
             ResultSet result = stmt.executeQuery("select r.id, n1.name, n2.name from ROUTES r\n" +
                     "join NODES n1 on r.start_point=n1.id\n" +
                     "join nodes n2 on r.end_point=n2.id\n" +
                     "where n1.name=\'" + start + "\' and n2.name=\'"+ end +"\'")) {
            while(result.next()) {
                    String message = "<html>\n" +
                            "<h2>Route &nbsp; Start &nbsp; End &nbsp;   </h2>\n" +
                            "<h2>" + result.getInt(1) + " &nbsp;" + result.getString(2) + "  &nbsp;" + result.getString(3) + " &nbsp; </h2>\n" +
                            "</html>";
                    //String message = result.getInt(1) + " " + result.getString(2) + " " + result.getString(3);
                    OutputMessageForm.setOutputTxt(message);

                   // OutputMessageForm.setOutputTxt("Route not found");
            }
           // return result.next() ? rs.getString(1) : null;
        }
    }

    public static void showRoute(int routeID) throws SQLException {
        getConnection();
        String sql = "select r.id as \"Route id\", n1.name as \"Current station\", n2.name as \"Destination\" from ROUTES r\n" +
                "join NODES n1 on r.start_point=n1.id\n" +
                "join nodes n2 on r.end_point=n2.id\n" +
                "where r.id=" + routeID;
        PreparedStatement preStatement = connection.prepareStatement(sql);
        ResultSet result = preStatement.executeQuery();
        while (result.next()) {
            String message = result.getInt(1) + " " + result.getString(2) + " " + result.getString(3);
            OutputMessageForm.setOutputTxt(message);
        }
    }
}
