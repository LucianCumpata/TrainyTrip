
import javax.swing.*;
import java.awt.*;

public class OutputMessageForm extends JPanel{
    static JLabel outputTable = new JLabel();
    static JLabel routeRoad = new JLabel();
    static JLabel ouputText = new JLabel();

    public OutputMessageForm() {


        Dimension size = getPreferredSize();
        size.width = 640;
        size.height = 270;
        setBorder(BorderFactory.createTitledBorder("Output"));


        GridBagConstraints gc = new GridBagConstraints();

        gc.weighty = 0.5;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.anchor = GridBagConstraints.WEST;

        //Output text fields

        gc.gridx = 5;
        gc.gridy = 3;
        add(outputTable, gc);

        gc.gridx = 8;
        gc.gridy = 4;
        add(ouputText, gc);
        gc.anchor = GridBagConstraints.LAST_LINE_END;

    }

    public static void setOutputTable(Integer routeID, String start, String finish) {
        outputTable.setText("<html>\n" +
                "<table>\n" +
                "    <tr>\n" +
                "        <th> Route </th>\n" +
                "        <th> Start </th>\n" +
                "        <th> End </th>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td>"+routeID + "  </td>\n" +
                "        <td>"+ start +"</td>\n" +
                "        <td>"+ finish +"</td>\n" +
                "    </tr>\n" +
                "</table>\n" +
                "\n" +
                "</html>");
    }

    public static void setOutputTable(Integer routeID1, Integer routeID2, String start1, String start2, String finish1, String finish2) {
        outputTable.setText("<html>\n" +
                "<table>\n" +
                "    <tr>\n" +
                "        <th> Route </th>\n" +
                "        <th> Start </th>\n" +
                "        <th> End </th>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td>"+routeID1 + "  </td>\n" +
                "        <td>"+ start1 +"</td>\n" +
                "        <td>"+ finish1 +"</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td>"+routeID2 + "  </td>\n" +
                "        <td>"+ start2+"</td>\n" +
                "        <td>"+ finish2 +"</td>\n" +
                "    </tr>\n" +
                "</table>\n" +
                "\n" +
                "</html>");
    }


    public static String beginTable(){
        String beginHtmlTable=  "<html>\n" +
                "<table>\n" +
                "    <tr>\n" +
                "        <th> Route </th>\n" +
                "        <th> Start </th>\n" +
                "        <th> End </th>\n" +
                "    </tr>\n";
        return beginHtmlTable;
    }

    public static String saveRouteData(Integer routeID,String start,String end){
        String routeData=  "    <tr>\n" +
                "        <td>"+routeID + "  </td>\n" +
                "        <td>"+ start +"</td>\n" +
                "        <td>"+ end +"</td>\n" +
                "    </tr>\n";
        return routeData;
    }

    public static String endTable(){
        String endHtmlTable= "\n" +
                "</html>";
        return endHtmlTable;
    }

    public static void setOuputText(String t) {ouputText.setText(t);}
    public static void clearAllOutput(){
        ouputText.setText("");
        outputTable.setText("");
        routeRoad.setText("");
    }
    public static void setRouteRoad (String t) { routeRoad.setText(t);}
}
