package com.company;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

public class Ticket {
    public int zahialgiin_id;
    public int zahialsan_hereglegchiin_id;
    public int plane_id;
    public int created;
    public String status;




    public DBconnection con = new DBconnection();

    public void Ticketorder(int idTicket, int signeduserid) {
        con.update(String.format("INSERT INTO Ticket (idTicket, ordered_user) VALUES ('%s', '%s')", idTicket, signeduserid ));

    }

}
