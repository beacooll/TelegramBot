package org.example;

import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Main {

    public static String token = "7919067093:AAEs8_-I-t2FkqgIyoZwEQOjORYCajOmm2o";


    public static void main(String[] args) throws Exception {
        try {
            TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication();
            botsApplication.registerBot(token, new TelegramBot(token));
        }
        catch (TelegramApiException e) {
            System.out.println("Некоректный токен бота");
        }
    }


}
