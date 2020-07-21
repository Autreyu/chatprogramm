package com.company;

import java.sql.*;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

//    ToDo:
//          TimerTask auslagern/zum laufen bringen,
//          mit Jar testen

    private static Object TimerTask;
    private static Connection conn;

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/chatprogramm?user=root";
        
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("connection access");

            connectionAndSend(conn);

            Timer timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    connectionAndRead(conn);
                }
            };
            timer.schedule(timerTask, 0, 1000);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
            	if(conn != null) {
            		conn.close();
            	}
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void connectionAndSend(Connection conn) {
        User sender = null;
        User receiver = null;
        try {
            Statement stmt = null;
            System.out.println("User Name eingeben:");
            Scanner scanner = new Scanner(System.in);
            String scanUserName = scanner.nextLine();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from user where userName = '" + scanUserName + "' limit 1");
            rs.next();
            sender = new User(rs.getString("userName"), rs.getInt("userId"));

            Statement stmt2 = null;
            stmt2 = conn.createStatement();
            ResultSet rsGetNames = stmt.executeQuery("SELECT userName FROM `user` WHERE userName != '" + scanUserName + "'");
            System.out.println("mit wem möchten Sie chatten:");
            while (rsGetNames.next()) {
                String userName = rsGetNames.getString("userName");
                System.out.println(userName);
            }

            String scannerWithWho = scanner.nextLine();
            Statement stmt3 = conn.createStatement();
            ResultSet rsGetReceiver = stmt3.executeQuery("select * from user where userName = '" + scannerWithWho + "' limit 1");
            rsGetReceiver.next();
            receiver = new User(rsGetReceiver.getString("userName"), rsGetReceiver.getInt("userId"));


            System.out.println("Ihre Nachricht:");
            String userMessage = scanner.nextLine();
            String sql = "INSERT INTO chat (`message`, `receiver_id`, `sender_id`) " +
                    "VALUES ('"+ userMessage +"' , " + receiver.getUserId() + ", " + sender.getUserId() + ")";

            stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }

    private static void connectionAndRead(Connection conn) {
        User sender = null;
        User receiver = null;
        Statement stmt = null;
        try {

            try {
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select * from user where userName = '" + sender.getUserId() + "'");
                rs.next();
                sender = new User(rs.getString("userName"), rs.getInt("userId"));
                while (rs.next()) {
                    String userName = rs.getString("userName");
                    String message = rs.getString("message");
                    System.out.println(userName + "  schreibt:\n" + message);
                }
            } catch (SQLException e) {
                throw new Error("Problem", e);
            } finally {
                if (stmt != null) {stmt.close(); }
            }

        } catch (SQLException e) {
            throw new Error("Problem", e);
        } 
    }
}
