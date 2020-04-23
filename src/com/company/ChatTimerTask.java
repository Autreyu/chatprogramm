package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TimerTask;

public class ChatTimerTask extends TimerTask {
    /*
    z.B. 6
     */
    int lastId;

    @Override
    public void run() {


        /*
        select max(id) from chat
        ==> 8
        if(8 > lastId){
            select * from chat where id > lastId

            sout die zwei Nachrichten

            lastId = 8
        }
         */

    }
}