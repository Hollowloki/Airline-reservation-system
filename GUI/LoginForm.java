import com.company.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginForm extends JDialog {
    private JTextField loginemail;
    private JButton loginButton;
    private JPanel loginPanel;
    private JButton registerlgn;
    private JPasswordField loginpassword;

    public LoginForm(JFrame parent) {
        super(parent);
        setTitle("Нэвтрэх");
        setContentPane(loginPanel);
        setMinimumSize(new Dimension(450, 474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = loginemail.getText();
                String password = String.valueOf(loginpassword.getPassword());

                user = getAuthenticatedUser(email, password);

                if (user != null) {
                    dispose();

                }
                else {
                    JOptionPane.showMessageDialog(LoginForm.this,
                            "И-мейл болон нууц үг оруулна уу",
                            "Дахин оролдоно уу",
                            JOptionPane.ERROR_MESSAGE);
                }

            }
        });



        registerlgn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(this);

                dispose();
                //register form
                AirlineSystem airlineSystem = new AirlineSystem(null);
            }
        });




        setVisible(true);
    }
    public com.company.User user;
    private User getAuthenticatedUser(String email, String password) {
        User user = null;

        final String DB_URL = "jdbc:mysql://localhost:3306/Muun";
        final String USERNAME = "root";
        final String PASSWORD = "muunuu123456";

        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            // Connected to database successfully...

            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM User WHERE email=? AND password=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.user_id = resultSet.getInt("user_id");
                user.username = resultSet.getString("username");
                user.email = resultSet.getString("email");
                user.password = resultSet.getString("password");
            }

            stmt.close();
            conn.close();

        }catch(Exception e){
            e.printStackTrace();
        }


        return user ;
    }
    public static void main(String[] args) {
        LoginForm loginForm = new LoginForm(null);
        User user = loginForm.user;
        if (user != null) {
            System.out.println("Амжилттай нэвтэрлээ : " + user.username);
            System.out.println("          И-мейл: " + user.email);
            new DashboardForm().setVisible(true);
        }
        else {
            System.out.println("Нэвтрэх цуцлагдлаа");
        }
    }
}
