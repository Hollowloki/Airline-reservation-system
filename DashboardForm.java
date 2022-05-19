import com.company.DBconnection;
import com.company.Plane;
import com.company.Ticket;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

public class DashboardForm extends JFrame {
    public final String DB_URL = "jdbc:mysql://localhost/MyStore?serverTimezone=UTC";
    public final String MYSQL_SERVER_URL = "jdbc:mysql://localhost:3306/Muun";
    public final String USERNAME = "root";
    public final String PASSWORD = "muunuu123456";
    public ArrayList<String> udur_sql = new ArrayList<>();
    public String[] udurarr = new String[udur_sql.size()];
    public ArrayList<String> tsag_sql = new ArrayList<>();
    public String[] tsagarr = new String[tsag_sql.size()];
    public String[] demo;
    public  String classcombodata;
    public String[] sqlroutes;
    public ArrayList<String> sqludur = new ArrayList<>();
    public ArrayList<String> sqltsag = new ArrayList<>();
    public String tsagudur;
    public String chigleldata;
    public com.company.Plane plane= new Plane();
    public String[] classarray = {"Single", "Adult", "VIP", "Child","Economy"};
    public ArrayList<String> chiglelz = new ArrayList<>();
    public String[] chiglel = new String[chiglelz.size()];
    private JTextField tax;
    private JTextField planeprice;
    private JTextField total;
    private JTextField Where;
    private JTextField To;
    private JTextField Dateofplane;
    private JTextField Timeofplane;
    private JTextField orderId;
    private JTextField Totalprint;
    private JPanel dashboardPanel;
    private JLabel usernamelabel;
    private JLabel emaillabel;
    private JComboBox routeBox;
    private JComboBox classcombo;
    private JCheckBox checkBox1;
    private JCheckBox checkBox2;
    private JCheckBox checkBox3;
    private JButton zahialah;
    private JButton confirmbutton;
    private JButton calc;
    private JLabel Tsag;
    private JComboBox comboBox1;
    private JButton bolomjitTsag;
    private JComboBox comboBox2;
    private JCheckBox checkBox4;
    private JCheckBox checkBox5;
    private JCheckBox checkBox6;
    private JButton showorder;
    private JButton logout;
    public int clickcount=0;
    public int planeidselected;
    public int userid;
    public int zahialga_id;


    public com.company.Ticket ticket = new Ticket();
    public DBconnection con = new DBconnection();

    public LoginForm loginForm;

    public DashboardForm() {

        routes();
        setTitle("Dashboard");
        setContentPane(dashboardPanel);
        setMinimumSize(new Dimension(500, 429));
        setSize(1200, 700);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        boolean hasRegistredUsers = connectToDatabase();

        //for(int)
        for(int i=0; i<classarray.length;i++){
            classcombo.insertItemAt(classarray[i], i);
        }
        showorder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tickets();
            }
        });

        classcombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               classcombodata = ""+ classcombo.getItemAt(classcombo.getSelectedIndex());
            }
        });

        routeBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chigleldata = ""+ routeBox.getItemAt(routeBox.getSelectedIndex());
                if(chigleldata.isEmpty()==false){
                        clickcount=0;
                        comboBox1.removeAllItems();
                        comboBox2.removeAllItems();
                        udur_sql.clear();

                        sqludur.clear();
                        sqltsag.clear();
                        tsag_sql.clear();
                }
            }
        });
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });


        calc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                calculateData();
            }
        });
        bolomjitTsag.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                okey();
                ArrayList<String> sqludurlist = removeDuplicates(sqludur);

                ArrayList<String> sqltsaglist = removeDuplicates(sqltsag);
                //JOptionPane.showMessageDialog(null,"Bolomjit udur tsaguud amjilttai garj irlee");
                int i;
                clickcount++;
                if(clickcount>1){
                }else{
                    for(i=0; i<sqludurlist.size();i++){
                        comboBox1.insertItemAt(sqludurlist.get(i),i);

                    }
                    for(i=0; i<sqltsaglist.size();i++){
                        comboBox2.insertItemAt(sqltsaglist.get(i),i);
                    }
                }

                udur_sql.clear();
                tsag_sql.clear();
            }
        });
        confirmbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                confirmButtonAc();

            }
        });
        zahialah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                zahialahquery();
                if(classcombo.getSelectedItem()=="Adult"){
                    checkBox1.setSelected(true);
                }
                if(classcombo.getSelectedItem()=="Child"){
                    checkBox2.setSelected(true);
                }
                if(classcombo.getSelectedItem()=="VIP"){
                    checkBox3.setSelected(true);
                }
                if(classcombo.getSelectedItem()=="Economy"){
                    checkBox4.setSelected(true);
                }
                if(classcombo.getSelectedItem()=="Single"){
                    checkBox5.setSelected(true);
                }
                if(classcombo.getSelectedItem()=="Engiin"){
                    checkBox6.setSelected(true);
                }
                JOptionPane.showMessageDialog(null,"Tani zahialga neegdsen");


                Dateofplane.setText((String) comboBox1.getSelectedItem());
                Timeofplane.setText((String) comboBox2.getSelectedItem());
                Totalprint.setText(total.getText());
            }
        });
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                loginForm = new LoginForm(null);
                com.company.User user = loginForm.user;
                if (user != null) {
                    usernamelabel.setText(user.username);
                    emaillabel.setText(user.email);
                    userid=user.user_id;
                    setLocationRelativeTo(null);
                    setVisible(true);
                }
                else {
                    dispose();
                }

            }
        });
//hereglegch baigaa tohioldold login form garna
        if (hasRegistredUsers) {
            LoginForm loginForm = new LoginForm(this);
            com.company.User user = loginForm.user;


            if (user != null) {
                usernamelabel.setText(user.username);
                emaillabel.setText(user.email);
                userid=user.user_id;
                setLocationRelativeTo(null);
                setVisible(true);
            }
            else {
                dispose();
            }
        }
        else {
            AirlineSystem registrationForm = new AirlineSystem(this);
            com.company.User user = registrationForm.user;

            if (user != null) {
                usernamelabel.setText(user.username);
                setLocationRelativeTo(null);
                setVisible(true);
            }
            else {
                dispose();
            }
        }
    }

    private void okey() {
        try {

            Connection conn = DriverManager.getConnection(MYSQL_SERVER_URL, USERNAME, PASSWORD);
            PreparedStatement ps = conn.prepareStatement("SELECT Plane.id,Plane.route,Plane.tax,Plane.price,Plane.class,Plane.where,Plane.to,Udur.udur,Tsag.tsag FROM Muun.Plane\n" +
                            "INNER JOIN Muun.Udur ON Muun.Plane.udur_id = Udur.udur_id\n" +
                            "INNER JOIN Muun.Tsag ON Muun.Udur.tsag_id = Tsag.tsag_id\n" +
                            "WHERE class=? AND route=?;",
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ps.setString(1,classcombodata);
            ps.setString(2,chigleldata);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                udur_sql.add(rs.getString(8));
                tsag_sql.add(rs.getString(9));

            }

            udurarr = udur_sql.toArray(new String[udur_sql.size()]);
            for(int i=0;i<udurarr.length;i++){
               // System.out.println(datesarr[i].substring(5,10));

                sqludur.add(udurarr[i]);
           }

            tsagarr = tsag_sql.toArray(new String[tsag_sql.size()]);
            for(int i=0;i<tsagarr.length;i++){
                // System.out.println(datesarr[i].substring(5,10));

                sqltsag.add(tsagarr[i]);
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void calculateData() {
        try {
            Connection conn = DriverManager.getConnection(MYSQL_SERVER_URL, USERNAME, PASSWORD);
            PreparedStatement ps = conn.prepareStatement("SELECT Plane.id,Plane.route,Plane.tax,Plane.price,Plane.class,Plane.where,Plane.to,Udur.udur,Tsag.tsag FROM Muun.Plane\n" +
                            "INNER JOIN Muun.Udur ON Muun.Plane.udur_id = Udur.udur_id\n" +
                            "INNER JOIN Muun.Tsag ON Muun.Udur.tsag_id = Tsag.tsag_id\n" +
                            "WHERE class=? AND route=? AND udur=? AND tsag=? ;",
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ps.setString(1,classcombodata);
            ps.setString(2,chigleldata);
            String selectedDay = (String) comboBox1.getSelectedItem();
            String selectedTime = (String) comboBox2.getSelectedItem();
            ps.setString(3,selectedDay);
            ps.setString(4,selectedTime);
            ResultSet rs = ps.executeQuery();
            rs.next();
            Where.setText(rs.getString("where"));
            To.setText(rs.getString("to"));

            planeidselected=rs.getInt("id");
            int taxv = rs.getInt("tax");
            tax.setText(Integer.toString(taxv));

            int pricev = rs.getInt("price");
            planeprice.setText(Integer.toString(pricev));

            int all = taxv+pricev;
            total.setText(Integer.toString(all));

         /*   while (rs.next()) {

            }*/

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void zahialahquery() {
        try {
            Connection conn = DriverManager.getConnection(MYSQL_SERVER_URL, USERNAME, PASSWORD);
            PreparedStatement ps = conn.prepareStatement("INSERT INTO Ticket (plane_id, user_id) VALUES (?, ?);");

            ps.setInt(1,planeidselected);
            ps.setInt(2,userid);
            ps.executeUpdate();
            PreparedStatement ps2 = conn.prepareStatement("SELECT * FROM Ticket WHERE plane_id=? AND user_id=? ORDER BY created DESC LIMIT 1;");
            ps2.setInt(1,planeidselected);
            ps2.setInt(2,userid);
            ResultSet rs = ps2.executeQuery();
            rs.next();
            orderId.setText(rs.getString("idTicket"));
            conn.close();
            plane.ongotsnii_id=planeidselected;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void routes() {
        try {

            Connection conn = DriverManager.getConnection(MYSQL_SERVER_URL, USERNAME, PASSWORD);
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Muun.Plane",
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                chiglelz.add(rs.getString(2));

            }
            ArrayList<String> chiglelxd = removeDuplicates(chiglelz);
            chiglel = chiglelxd.toArray(new String[chiglelxd.size()]);

            for(int i=0;i<chiglel.length;i++){
                // System.out.println(datesarr[i].substring(5,10));

                System.out.println(chiglel[i]);
               routeBox.insertItemAt(chiglel[i],i);
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void tickets() {
        try {

            Connection conn = DriverManager.getConnection(MYSQL_SERVER_URL, USERNAME, PASSWORD);
            PreparedStatement ps = conn.prepareStatement("SELECT Ticket.idTicket,Ticket.user_id,Plane.where,Plane.to,Ticket.status FROM Muun.Ticket\n" +
                            "INNER JOIN Muun.Plane ON Muun.Ticket.plane_id=Muun.Plane.id\n" +
                            "WHERE user_id=? ORDER BY created DESC;",
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ps.setInt(1,userid);
            ResultSet rs = ps.executeQuery();

            JTable table1=new JTable(buildTableModel(rs));

            JOptionPane.showMessageDialog(null, new JScrollPane(table1));

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void confirmButtonAc() {

        try {

            Connection conn = DriverManager.getConnection(MYSQL_SERVER_URL, USERNAME, PASSWORD);
            PreparedStatement ps = conn.prepareStatement("UPDATE Muun.Ticket SET status='open' WHERE idTicket=?");
            ps.setString(1, orderId.getText());
            int rs = ps.executeUpdate();
            if(rs!=0){
                JOptionPane.showMessageDialog(null, "Amjilttai batalgaajlaa");
            }
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DefaultTableModel buildTableModel(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);

    }

    public <T> ArrayList<T> removeDuplicates(ArrayList<T> list)
    {

        // Create a new ArrayList
        ArrayList<T> newList = new ArrayList<T>();

        // Traverse through the first list
        for (T element : list) {

            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) {

                newList.add(element);
            }
        }

        // return the new list
        return newList;
    }
    private boolean connectToDatabase() {
        boolean hasRegistredUsers = false;

        try{
            //First, connect to MYSQL server and create the database if not created
            Connection conn = DriverManager.getConnection(MYSQL_SERVER_URL, USERNAME, PASSWORD);
            Statement statement = conn.createStatement();
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS Muun");
            statement.close();
            conn.close();

            //Second, connect to the database and create the table "users" if cot created
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            statement = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS User ("
                    + "user_id INT( 10 ) NOT NULL PRIMARY KEY AUTO_INCREMENT,"
                    + "username VARCHAR(200) NOT NULL,"
                    + "email VARCHAR(200) NOT NULL UNIQUE,"
                    + "password VARCHAR(200) NOT NULL"
                    + ")";
            statement.executeUpdate(sql);


            //check if we have users in the table users
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM Muun.User");

            if (resultSet.next()) {
                int numUsers = resultSet.getInt(1);
                if (numUsers > 0) {
                    hasRegistredUsers = true;
                }
            }


            statement.close();
            conn.close();


        }catch(Exception e){
            e.printStackTrace();
        }

        return hasRegistredUsers;
    }

    public static void main(String[] args) {
        DashboardForm myForm = new DashboardForm();
    }
}
