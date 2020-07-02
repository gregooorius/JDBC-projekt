
package com.mycompany.firstproject;

import java.util.ArrayList;


public class Valami {
    public static void main(String[] args) {
        DB db = new DB();
        //db.addUser("gyula", "Budapest");
        //db.showAllUsers();
        //db.showUsersMeta();
        ArrayList<User> users = db.getAllUsers();
        for(User u: users){
            System.out.println(u.getName());
        }
    }
}
