package org.example;

public class BotUser {
    private double balance;
    private final String firstName;
    private final String lastName;
    private final long chatID;
    private String filmQuery;

    BotUser(String firstName, String lastName, long chatID){
        this.firstName = firstName;
        this.lastName = lastName;
        this.chatID = chatID;
        this.filmQuery = null;
        balance = 0;
    }

    public long getChatID() {
        return chatID;
    }
    public void addToBalance(long money) {
        balance += money;
    }
    public void subBalance(double money) {
        balance -= money;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public double getBalance() {
        return balance;
    }
    public String getFilmQuery() {
        return filmQuery;
    }
    public void setFilmQuery(String filmQuery) {
        this.filmQuery = filmQuery;
    }
}