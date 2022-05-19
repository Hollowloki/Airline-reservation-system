import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

public class ListForm extends DashboardForm {
    private JTable table1;

    public ListForm(JTable table1) {
        this.table1 = table1;
        JOptionPane.showMessageDialog(null, new JScrollPane(table1));
    }

    private void tickets() {
        final String DB_URL = "jdbc:mysql://localhost/MyStore?serverTimezone=UTC";
        final String MYSQL_SERVER_URL = "jdbc:mysql://localhost:3306/Muun";
        final String USERNAME = "root";
        final String PASSWORD = "muunuu123456";
        try {

            Connection conn = DriverManager.getConnection(MYSQL_SERVER_URL, USERNAME, PASSWORD);
            PreparedStatement ps = conn.prepareStatement("SELECT Ticket.idTicket,Ticket.user_id,Plane.where,Plane.to,Ticket.status FROM Muun.Ticket\n" +
                            "INNER JOIN Muun.Plane ON Muun.Ticket.plane_id=Muun.Plane.id\n" +
                            "WHERE user_id=?;",
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ps.setInt(1,userid);
            ResultSet rs = ps.executeQuery();

            table1=new JTable(buildTableModel(rs));


            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
