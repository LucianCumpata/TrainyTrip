import java.sql.*;
import java.util.LinkedList;
import java.util.Properties;

public class Database {
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String USER = "luci";
    private static final String PASSWORD = "andreea";
    private static Connection connection = null;
    private static Statement stmt = null;
    private static Properties props = new Properties();
    private static String[] nodes = new String[100];
    static LinkedList<Integer> linkedRoute = new LinkedList();
    static LinkedList<Integer> linkedNodes = new LinkedList();
    static int iterator = 1;
    static Integer routeID1 = null;
    static Integer routeID2 = null;
    static String[] specialSql = new String[10000];

    private Database() {
    }

    public static void createConnection() throws SQLException {
        props.setProperty("user", USER);
        props.setProperty("password", PASSWORD);
        connection = DriverManager.getConnection(URL, props);
        String message = "Connection established";
        System.out.println(message);
        OutputStatusForm.setStatusText(message);

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

    public static void closeConnection() throws SQLException {
        connection.close();
        System.out.println("Status:connection closed");
    }


    public static String extractANode(int j) throws SQLException {
        getConnection();
        String sql = "SELECT NAME FROM NODES";
        PreparedStatement preStatement = connection.prepareStatement(sql);
        ResultSet result = preStatement.executeQuery();
        int i = 0;
        while (result.next()) {
            nodes[i] = result.getString(1);
            //System.out.println(nodes[i]);
            i++;

            // System.out.println(result.getString(1));
        }
        return nodes[j];
    }

    public static void extractAllNodes() throws SQLException {
        getConnection();
        String sql = "SELECT NAME FROM NODES";
        PreparedStatement preStatement = connection.prepareStatement(sql);
        ResultSet result = preStatement.executeQuery();
        int i = 0;
        while (result.next()) {
            nodes[i] = result.getString(1);
            //System.out.println(nodes[i]);
            i++;
            // System.out.println(result.getString(1));
        }
    }

    public static void showItinerary() throws SQLException {
        getConnection();
        String sql = "select r.id, n1.name, n2.name from ROUTES r\n" +
                "join NODES n1 on r.start_point=n1.id\n" +
                "join nodes n2 on r.end_point=n2.id";
        PreparedStatement preStatement = connection.prepareStatement(sql);
        ResultSet result = preStatement.executeQuery();
        Integer i = 0;
        Integer[] routeID = new Integer[100];
        String[] startNode = new String[100];
        String[] endNode = new String[100];
        while (result.next()) {
            routeID[i] = result.getInt(1);
            startNode[i] = result.getString(2);
            endNode[i] = result.getString(3);
            //System.out.println(routeID[i]+" "+startNode[i]+" "+endNode[i]);
            i++;
        }
        String tableHtml = OutputMessageForm.beginTable();
        for (int K = 0; K < i; K++) {
            tableHtml = tableHtml + OutputMessageForm.saveRouteData(routeID[K], startNode[K], endNode[K]);
            System.out.println(routeID[K] + " " + startNode[K] + " " + endNode[K]);
        }
        tableHtml = tableHtml + OutputMessageForm.endTable();
        OutputMessageForm.setOuputText(tableHtml);
    }

    public static void searchSimpleRoute(String start, String end) throws SQLException {
        getConnection();
        try (Statement stmt = connection.createStatement();
             ResultSet result = stmt.executeQuery("select r.id, n1.name, n2.name from ROUTES r\n" +
                     "join NODES n1 on r.start_point=n1.id\n" +
                     "join nodes n2 on r.end_point=n2.id\n" +
                     "where n1.name=\'" + start + "\' and n2.name=\'" + end + "\'")) {
            Integer searchSimpleRouteResult = null;
            while (result.next()) {
                System.out.println(result.getInt(1));
                searchSimpleRouteResult = result.getInt(1);
            }
            OutputMessageForm.clearAllOutput();
            searchDoubleRoute(start, end);
            if (start.equals(end)) {
                OutputMessageForm.setOuputText("You current location must not be equal to your destination");
            } else if (start.equals("Your location") || end.equals("Destination")) {
                OutputMessageForm.setOuputText("Please choose a location");
            } else if (searchSimpleRouteResult != null) {
                showRoute(searchSimpleRouteResult);
            } else {
                if (searchRoutesWithFirstNode(start, end) != null) {
                    showRouteByInterNode(searchRoutesWithFirstNode(start, end));
                } else if (searchRouteWithLastNode(start, end) != null) {
                    showRouteByInterNode(searchRouteWithLastNode(start, end));

                } else if (getRouteID1() != null && getRouteID2() != null) {
                    showRoute(getRouteID1(), getRouteID2());
                } else {
                    if ((searchSimpleRouteResult == null) || (searchRoutesWithFirstNode(start, end) == null)
                            || (searchRouteWithLastNode(start, end) == null)
                            || (getRouteID1() == null && getRouteID2() == null)) {
                        OutputMessageForm.setOuputText("Route not found!");
                    }
                }
            }

        }

    }


    public static void searchDoubleRoute(String start, String end) throws SQLException {
        String destination = null;
        getConnection();
        try (Statement stmt = connection.createStatement();
             ResultSet result1 = stmt.executeQuery("select r.id as \"Route id\", n1.name as \"Current station\", n2.name as \"Destination\" from ROUTES r\n" +
                     "join NODES n1 on r.start_point=n1.id\n" +
                     "join nodes n2 on r.end_point=n2.id\n" +
                     "where r.start_point=" + convertNameToID(start) + " and r.end_point in (\n" +
                     "    SELECT start_point FROM ROUTES WHERE end_point=" + convertNameToID(end) + ")")) {

            while (result1.next()) {
                destination = result1.getString(3);
                setRouteID1(result1.getInt(1));
            }
        }

        try (Statement stmt = connection.createStatement();
             ResultSet result2 = stmt.executeQuery("select r.id as \"Route id\", n1.name as \"Current station\", n2.name as \"Destination\" from ROUTES r\n" +
                     "join NODES n1 on r.start_point=n1.id\n" +
                     "join nodes n2 on r.end_point=n2.id\n" +
                     "where r.start_point=" + convertNameToID(destination) + " and r.end_point=" + convertNameToID(end))) {
            while (result2.next()) {
                setRouteID2(result2.getInt(1));
            }
        }
    }




    public static void showRoute(Integer routeID) throws SQLException {
        getConnection();
        String sql = "select r.id as \"Route id\", n1.name as \"Current station\", n2.name as \"Destination\" from ROUTES r\n" +
                "join NODES n1 on r.start_point=n1.id\n" +
                "join nodes n2 on r.end_point=n2.id\n" +
                "where r.id=" + routeID;
        PreparedStatement preStatement = connection.prepareStatement(sql);
        ResultSet result = preStatement.executeQuery();
        while (result.next()) {
            OutputMessageForm.setOutputTable(result.getInt(1), result.getString(2), result.getString(3));
        }
        if (routeID == null)
            OutputStatusForm.setStatusText("No route found");
    }

    public static void showRoute(Integer routeID1, Integer routeID2) throws SQLException {
        getConnection();
        String sql1 = "select r.id as \"Route id\", n1.name as \"Current station\", n2.name as \"Destination\" from ROUTES r\n" +
                "join NODES n1 on r.start_point=n1.id\n" +
                "join nodes n2 on r.end_point=n2.id\n" +
                "where r.id=" + routeID1;
        String sql2 = "select r.id as \"Route id\", n1.name as \"Current station\", n2.name as \"Destination\" from ROUTES r\n" +
                "join NODES n1 on r.start_point=n1.id\n" +
                "join nodes n2 on r.end_point=n2.id\n" +
                "where r.id=" + routeID2;
        PreparedStatement preStatement1 = connection.prepareStatement(sql1);
        PreparedStatement preStatement2 = connection.prepareStatement(sql2);
        ResultSet result1 = preStatement1.executeQuery();
        ResultSet result2 = preStatement2.executeQuery();
        Integer resultID1 = null;
        String resultStart1 = "";
        String resultFinish1 = "";
        Integer resultID2 = null;
        String resultStart2 = "";
        String resultFinish2 = "";

        while (result1.next()) {
            resultID1 = result1.getInt(1);
            resultStart1 = result1.getString(2);
            resultFinish1 = result1.getString(3);
        }

        while (result2.next()) {
            resultID2 = result2.getInt(1);
            resultStart2 = result2.getString(2);
            resultFinish2 = result2.getString(3);
            OutputMessageForm.setOutputTable(resultID1, resultID2, resultStart1, resultStart2, resultFinish1, resultFinish2);
        }
        /*
        if ((routeID1 == null) || (routeID2 == null))
            OutputStatusForm.setStatusText("No routes found");
            */
    }

    public static void showRouteByInterNode(Integer interNode) throws SQLException {
        getConnection();
        String sql = "select r.id,n1.name, n2.name,\n" +
                "r.INTERMEDIARY_STATIONS from ROUTES r\n" +
                "join NODES n1 on r.start_point=n1.id\n" +
                "join nodes n2 on r.end_point=n2.id\n" +
                "where r.INTERMEDIARY_STATIONS=" + interNode;
        PreparedStatement preStatement = connection.prepareStatement(sql);
        ResultSet result = preStatement.executeQuery();
        Integer routeId = null;
        String name1 = null;
        String name2 = null;
        while (result.next()) {
            routeId = result.getInt(1);
            name1 = result.getString(2);
            name2 = result.getString(3);
            OutputMessageForm.setOutputTable(routeId, name1, name2);
        }
    }

    //Cautam toate rutele care se termina cu un anumit nod
    public static Integer searchRouteWithLastNode(String startNode, String endNode) throws SQLException {
        getConnection();
        String sql = "select r.id,n1.name, n2.name,\n" +
                "r.INTERMEDIARY_STATIONS from ROUTES r\n" +
                "join NODES n1 on r.start_point=n1.id\n" +
                "join nodes n2 on r.end_point=n2.id\n" +
                "where r.end_point=" + convertNameToID(endNode);
        PreparedStatement preStatement = connection.prepareStatement(sql);
        ResultSet result = preStatement.executeQuery();
        linkedRoute.clear();
        linkedNodes.clear();
        while (result.next()) {
            extractEdges(result.getInt(4));
            extractNodes(linkedRoute);
            System.out.println("Linked route" + linkedRoute);
            System.out.println("Linked nodes" + linkedNodes);
            if ((wasNodeFound(linkedNodes, convertNameToID(startNode)) == true)) {
                return result.getInt(4);
                //showRouteByInterNode(result.getInt(4));
                //break;

            }
        }
        return null;
    }


    //Cautam toate rutele care incep cu un anumit nod
    public static Integer searchRoutesWithFirstNode(String startNode, String endNode) throws SQLException {
        getConnection();
        String sql = "select r.id,n1.name, n2.name,\n" +
                "r.INTERMEDIARY_STATIONS from ROUTES r\n" +
                "join NODES n1 on r.start_point=n1.id\n" +
                "join nodes n2 on r.end_point=n2.id\n" +
                "where r.start_point=" + convertNameToID(startNode);
        PreparedStatement preStatement = connection.prepareStatement(sql);
        ResultSet result = preStatement.executeQuery();
        linkedRoute.clear();
        linkedNodes.clear();
        while (result.next()) {
            extractEdges(result.getInt(4));
            extractNodes(linkedRoute);
            System.out.println("Linked route" + linkedRoute);
            System.out.println("Linked nodes" + linkedNodes);
            if ((wasNodeFound(linkedNodes, convertNameToID(endNode)) == true)) {
                return result.getInt(4);
                // showRouteByInterNode(result.getInt(4));
                //  break;

            }
        }
        return null;
    }

    //Extrage muchiile dintr-o ruta si le adauga intro lista simpla inlantuita
    public static void extractEdges(Integer interID) throws SQLException {
        getConnection();
        String sql = "select edge,next from INTER_NODES where ID=" + interID;
        PreparedStatement preStatement = connection.prepareStatement(sql);
        ResultSet result = preStatement.executeQuery();
        Integer nextID = 0;
        int lol = 0;
        while (result.next()) {
            nextID = result.getInt(2);
            linkedRoute.add(result.getInt(1));
            // System.out.println("First Next ID:"+nextID);
        }

        while (nextID != 0) {
            specialSql[lol] = "select edge,next from INTER_NODES where ID=" + nextID;
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(specialSql[lol])) {
                //specialResult[j] = specialPrepareStatement[j] .executeQuery();
                while (resultSet.next()) {
                    nextID = resultSet.getInt(2);
                    linkedRoute.add(resultSet.getInt(1));
                    iterator++;
                    // System.out.println("Next ID:"+nextID);
                }
                lol++;
            }
        }
    }

    //Extrage nodurile dintr-o lista inlantuita care contine muchiile rutei, si le baga in alta lista inlantuita
    public static void extractNodes(LinkedList<Integer> list) throws SQLException {
        getConnection();
        String sql = "select start_point from edges where id in (" + returnEdges(linkedRoute) + ")";
        PreparedStatement preStatement = connection.prepareStatement(sql);
        ResultSet result = preStatement.executeQuery();

        String sql2 = "select end_point from edges where id=" + list.getLast();
        PreparedStatement preStatement2 = connection.prepareStatement(sql2);
        ResultSet result2 = preStatement2.executeQuery();
        int i = 0;
        while (result.next()) {
            linkedNodes.add(i, result.getInt(1));
            i++;
        }

        while (result2.next()) {
            linkedNodes.add(i, result2.getInt(1));
        }
    }

    //Verifica daca nodul face parte din ruta
    public static boolean wasNodeFound(LinkedList<Integer> list, Integer searchNode) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == (int) searchNode)
                return true;
        }
        return false;
    }


    public static Integer getIntermediaryStationsID(String start, String end) throws SQLException {
        getConnection();
        int intermediaryID = 0;
        try (Statement stmt = connection.createStatement();
             ResultSet result = stmt.executeQuery("select r.id, n1.name, n2.name,r.intermediary_stations from ROUTES r\n" +
                     "join NODES n1 on r.start_point=n1.id\n" +
                     "join nodes n2 on r.end_point=n2.id\n" +
                     "where n1.name=\'" + start + "\' and n2.name=\'" + end + "\'")) {
            while (result.next()) {
                intermediaryID = result.getInt(4);
                //String message = result.getInt(1) + " " + result.getString(2) + " " + result.getString(3);
            }
        }
        return intermediaryID;
    }

    public static String returnEdges(LinkedList list) {
        String stringResult = "";
        for (int i = 0; i < list.size() - 1; i++) {
            stringResult = stringResult + linkedRoute.get(i);
            stringResult += ",";
        }
        stringResult = stringResult + linkedRoute.get((Integer) list.size() - 1);
        //System.out.println(stringResult);
        return stringResult;
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

    public static Integer convertNameToID(String name) throws SQLException {
        getConnection();
        Integer ID = null;
        String sql = "SELECT ID FROM NODES WHERE NAME='" + name + "'";
        PreparedStatement preStatement = connection.prepareStatement(sql);
        ResultSet result = preStatement.executeQuery();
        while (result.next())
            ID = result.getInt(1);
        return ID;
    }

    public static Integer getRouteID1() {
        return routeID1;
    }

    public static void setRouteID1(Integer routeID1) {
        Database.routeID1 = routeID1;
    }

    public static Integer getRouteID2() {
        return routeID2;
    }

    public static void setRouteID2(Integer routeID2) {
        Database.routeID2 = routeID2;
    }
}
