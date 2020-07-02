
package com.mycompany.firstproject;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.derby.database.Database;


public class DB {
    final String JDBC_DRIVER="org.apache.derby.jdbc.EmbeddedDriver";
    final String URL = "jdbc:derby:samleDB;create = true";
    final String USERNAME = "geri";
    final String PASSWORD = "Geri";
    DatabaseMetaData dbmd = null;
    Connection conn = null;
    Statement createStatement = null;
    
    public DB(){
        
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("Siker");
        } catch (SQLException ex) {
            System.out.println(""+ex);
        }
        /////////////
        
        try {
            createStatement = conn.createStatement();
        } catch (SQLException ex) {
            System.out.println("Itt meg mi van");
            System.out.println("" + ex);
        }
        //////////////
        
        try {
            dbmd = conn.getMetaData();
        } catch (SQLException ex) {
            System.out.println("Haho hello");
            System.err.println("" + ex);
        }
        ///////////////
        try {
            ResultSet rs = dbmd.getTables(null, "APP", "USERS", null);
            if(!rs.next()){
                createStatement.execute("create table users(name varchar(20),address varchar(20))");
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van");
        }
        
    }
    
    public void addUser(String name, String address){
        try {
             //String sql = "insert into users values('"+ name + "' , '" + address +"')";
             //createStatement.execute(sql);
             String sql = "insert into users values(?,?)";
             PreparedStatement prepstate = conn.prepareStatement(sql);
             prepstate.setString(1, name);
             prepstate.setString(2, address);
             prepstate.execute();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a létrehozással");
        }
    }
    
    public void showAllUsers(){
        String sql = "select * from users";
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            while(rs.next()){
                String name = rs.getString("name");
                String address = rs.getString("address");
                System.out.println(name + "\n" + address);
            }
        } catch (SQLException ex) {
            System.out.println("Nem tudok lekérdezni");
            System.out.println("" + ex);
        }
    }
    
    public void showUsersMeta(){
        String sql = "select * from users";
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        try {
            rs = createStatement.executeQuery(sql);
            rsmd = rs.getMetaData();
            int columCount = rsmd.getColumnCount();
            for(int x =1;x<=columCount;x++){
                System.out.print(rsmd.getColumnName(x) + " | ");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
    public ArrayList<User> getAllUsers(){
        String sql = "select * from users";
        ArrayList<User> users = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            users = new ArrayList<>();
            while(rs.next()){
                String name = rs.getString("name");
                String address = rs.getString("address");
                User accuser = new User(rs.getString("name"),rs.getString("address"));
                //user.setAddress(rs.getString("address"));
                //user.setName(rs.getString("name"));
                users.add(accuser);
            }
        } catch (SQLException ex) {
            System.out.println("Nem tudok lekérdezni");
            System.out.println("" + ex);
        }
        
        return users;
    }
}
