package org.example;

import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Main {

    public static String token = "7919067093:AAEs8_-I-t2FkqgIyoZwEQOjORYCajOmm2o";
    public static String apiKey = "BFB7B3E000BB1AEE8BF5D0E85AC46D8A";
    public static String secretKey = "B95A46589F5C89A471FC09020F3D3669";

    public static void main(String[] args) {
        try {
            TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication();
            botsApplication.registerBot(token, new TelegramBot(token));
        } catch (TelegramApiException e) {
            System.out.println("Некоректный токен бота");
        }
    }
}
