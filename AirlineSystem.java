
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class AirlineSystem extends JDialog {
    private JTextField username;
    public JPanel registerPanel;
    private JTextField email;
    private JButton registerButton;
    private JButton LoginButton;
    private JPasswordField password;
    private JPasswordField passwordConfirm;
    ;
    public AirlineSystem(JFrame parent) {
        super(parent);
        this.setTitle("muun");
        this.setContentPane(registerPanel);
        setMinimumSize(new Dimension(450, 474));
        this.setModal(true);
        setLocationRelativeTo(parent);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }


        });

        // this.setSize(1000, 1000);


        LoginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               dispose();
               LoginForm loginForm = new LoginForm(parent);
            }
        });
        setVisible(true);
    }


    private void registerUser() {
        String name = this.username.getText();
        String email = this.email.getText();
        String password = String.valueOf(this.password.getPassword());
        String passwordConfirm = String.valueOf(this.passwordConfirm.getPassword());
        if(name.isEmpty() || email.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Дутуу бөглөсөн байна",
                    "Дахин оролдоно уу",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!password.equals(passwordConfirm)) {
            JOptionPane.showMessageDialog(this,
                    "Нууц үг таарахгүй байна",
                    "Дахин оролдоно уу",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        user = addUserToDatabase(name, email, password);
        if (user != null) {
            dispose();
        }
        else {
            JOptionPane.showMessageDialog(this,
                    "Failed to register new user",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }


    }

    public com.company.User user;
    private com.company.User addUserToDatabase(String name, String email, String password) {
        com.company.User user = null;
        final String DB_URL = "jdbc:mysql://localhost:3306/Muun";
        final String USERNAME = "root";
        final String PASSWORD = "muunuu123456";

        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            // Connected to database successfully...

            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO User (username, email, password) " +
                    "VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);

            //Insert row into the table
            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0) {
                user = new com.company.User();
                user.username = name;
                user.email = email;
                user.password = password;
            }

            stmt.close();
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        return user;
    }
    public static void main(String[] args) {

        AirlineSystem airlineSystem = new AirlineSystem(null);
        com.company.User user = airlineSystem.user;
        if (user != null) {
            System.out.println("Successful registration of: " + user.username);
        }
        else {
            System.out.println("Registration canceled");
        }
    }
}
