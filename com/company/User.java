package com.company;
import java.sql.*;
public class User {
    public String username;
    public int user_id;
    public String email;
    public boolean male;
    public String password;
    public int age;
    public int zahialgiin_id;
    public DBconnection con = new DBconnection();

    public void signUp(String email, String password) {

        con.update(String.format("INSERT INTO User (email, password) VALUES ('%s', '%s')", email, password));

    }
    public ResultSet signIn(String email, String password) {

        String SQL = "SELECT * FROM User WHERE email='" + email + "' && password='" + password+"'";
        return con.select(SQL);

    }
    //incoming email == db_email; password == db_password
   /* public boolean isAuth() {
        if(db_email == email && db_password == password) {
            return true;
        } else {
            return false;
        }
    }*/
    public void orderTicket() {


    }
}