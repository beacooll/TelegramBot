package org.example;

import java.util.HashMap;

public class UserManager {
    private static HashMap<Long, BotUser> users = new HashMap<>();
    private static HashMap<Long, BotAdmin> admins = new HashMap<>();

    public boolean isAdmin(long chatID) {
        return admins.containsKey(chatID);
    }

    public boolean isUser(long chatID) {
        return users.containsKey(chatID);
    }

    public BotUser getBotUser(long chatID){
        return users.get(chatID);
    }

    public BotAdmin getBotAdmin(long chatID){
        return admins.get(chatID);
    }
    public void addUser(long chatID, String firstName, String lastName) {
        if(isUser(chatID)) {return;}
        users.put(chatID, new BotUser(firstName, lastName, chatID));
    }

    public void deleteUser(long chatID){
        users.remove(chatID);
    }
    public void deleteAdmin(long chatID){
        admins.remove(chatID);
    }

    public void addAdmin(long chatID, String firstName, String lastName){
        if(isAdmin(chatID)) {return;}
        admins.put(chatID, new BotAdmin(firstName, lastName, chatID));
    }
}


