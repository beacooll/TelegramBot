package org.example;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.HashMap;


public class TelegramBot implements LongPollingSingleThreadUpdateConsumer {
    private final TelegramClient client;

    private static HashMap<Long, BotUser> users = new HashMap<>();
    private static HashMap<Long, Admin> admins = new HashMap<>();
    private static FilmsCollection films;

    private static final MessageMaker messageMaker = new MessageMaker();
    private static final MarkupMaker markupMaker = new MarkupMaker();

    public TelegramBot(String token) {
        client = new OkHttpTelegramClient(token);
    }


    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatID = update.getMessage().getChatId();

            if (users.containsKey(chatID)) {
                handleUserCommand(messageText, users.get(chatID));
            }
            else if(admins.containsKey(chatID)){
                handleAdminCommand(messageText, users.get(chatID));
            }
            else{
                String firstName = update.getMessage().getFrom().getFirstName();
                String lastName = update.getMessage().getFrom().getLastName();

                BotUser newUser = new BotUser(chatID, lastName, firstName);
            }
        } else if (update.hasCallbackQuery()) {
            String callData = update.getCallbackQuery().getData();
            long chatID = update.getCallbackQuery().getMessage().getChatId();

            handleCallback(callData, chatID);
        }
    }

    private void handleUserCommand(String command, BotUser user) {
        String answer;
        long chatID = user.getChatID();
        SendMessage message = null;

        switch (command) {
            case "/start":
                answer = "Добро пожаловать! \nЭтот бот предназначен для посетителей кинотеатра \"NoisyCrazyBizzareFilms\"\nТут вы можите ознакомится с афишей, рассписанием, а также бронировать и покупать билеты на сеансы";
                message = messageMaker.makeMessage(answer, chatID);

                break;
            case "/profile":
                answer = "Уважаемый " + user.getFirstName() + ' ' + user.getLastName() + ", вот все что мы о вас знаем:\nВаш баланс: " + user.getBalance();
                message = messageMaker.makeMessage(answer, chatID);
                break;
            case "/view the poster":


        }try {
            client.execute(message);
        }catch (TelegramApiException e){
            e.printStackTrace();
        }

    }

    private void handleAdminCommand(String command, BotUser user) {
        String answer;
        long chatID = user.getChatID();

        switch (command) {
            case "/start":
                answer = "";
                SendMessage startMessage = messageMaker.makeMessage(answer, chatID);
                try {
                    client.execute(startMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;
            case "/":
                answer = "";
                SendMessage message = messageMaker.makeMessage(answer, chatID);

                try {
                    client.execute(message);
                }catch (TelegramApiException e){
                    e.printStackTrace();
                }
                break;
        }
    }

    private void handleCallback (String callData, long chatID){
/*
        if ( != null) {
            switch (callData) {
                case "":

                    break;
            }
        }*/
    }



}

