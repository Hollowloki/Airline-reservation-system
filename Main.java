package com.company;
import java.sql.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);

        User user = new User();
        ResultSet usersignedin;
        boolean isSigned = false;
        int signeduserid=0;
        int usersignup;
        //user.signUp(name, password);
        while (true) {

            System.out.println("\nNevtreh bol 1.");
            System.out.println("Burtguuleh bol 2.");
            System.out.println("Nevtersen bol zahialga hiih 3.");
            System.out.println("Garah bol 0");
            int choice = sc.nextInt();
            switch (choice) {
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    System.out.println("Enter Email:");
                    String name = sc.next();
                    System.out.println("Enter Password: ");
                    String password = sc.next();
                     usersignedin = user.signIn(name, password);

                    if(usersignedin.next()) {
                        signeduserid = usersignedin.getInt("user_id");
                        isSigned = true;
                        System.out.println("Nevterlee");
                    } else {
                        System.out.println("Ner nuuts ug buruu oruulsan baina");
                    }
                    break;
                case 2:
                    System.out.println("Enter Email: ");
                    String name2 = sc.next();
                    System.out.println("Enter Password: ");

                    String password2 = sc.next();
                    user.signUp(name2, password2);
                    System.out.println("Amjilttai burtgelee");
                    break;
                case 3:
                    if(isSigned) {
                        //PLANE
                            //Ticket ticket = new Ticket();
                            //ticket.Ticketorder(signeduserid);
                        System.out.println("Ticket zahialna");
                    } else {
                        System.out.println("Nevterch orno uu");
                    }
            }
        }

    }
}
