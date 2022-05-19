package com.company;
import java.sql.*;

public class DBconnection {


    static final String DB_URL = "jdbc:mysql://localhost:3306/Muun";
    static final String DB_USER = "root";
    static final String DB_PASSWORD = "muunuu123456";
    Connection connection;

    public DBconnection(){
        connect();

    }

    public void connect(){
        try{
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Холбогдлоо");
        }catch(SQLException e){
            System.out.println("Хооболтод алдаа гарлаа!");
            System.out.println(e.toString());
        }
    }

    public ResultSet select(String query){
        try{
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            return result;
        }catch(SQLException e){
            System.out.println("QUERY-г ажиллуулж чадсангүй!");
            System.out.println(e.toString());
            return null;
        }
    }

    public int update(String query){
        try{
            Statement statement = connection.createStatement();
            return statement.executeUpdate(query);
        }catch(SQLException e){
            System.out.println("ШИНЭЧЛЭХ QUERY-г ажиллуулж чадсангүй");
            System.out.println(e.toString());
            return -1;
        }
    }

    public int delete(String query){
        try{
            Statement statement = connection.createStatement();
            return statement.executeUpdate(query);
        }catch(SQLException e){
            System.out.println("УСТГАХ QUERY-г ажиллуулж чадсангүй");
            System.out.println(e.toString());
            return -1;
        }
    }

    public void close(){
        try{
            connection.close();
        }catch(SQLException e){
            System.out.println("ХОЛБОЛТ салгахад алдаа гарлаа!");
            System.out.println(e.toString());
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
