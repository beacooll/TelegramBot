package org.example;

public class BotUser {
    private final long chatID;
    private long balance;
    private final String firstName;
    private final String lastName;


    BotUser(long chatID, String lastName, String firstName){
        this.chatID = chatID;
        this.firstName = firstName;
        this.lastName = lastName;
        balance = 0;
    }

    public void addToBalance(long money) {balance += money;}
    public long getChatID() {return chatID;}
    public String getFirstName() {return firstName;}
    public String getLastName() {return lastName;}
    public long getBalance() {return balance;}
}
