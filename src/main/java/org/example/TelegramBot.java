package org.example;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.HashMap;
import java.util.Map;

public class TelegramBot implements LongPollingSingleThreadUpdateConsumer {
    private final TelegramClient client;
    private final Map<Long, Boolean> waitingForPrompt = new HashMap<>();
    private final Map<Long, Request> requests = new HashMap<>();
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

            if (messageText.startsWith("/")) {
                handleCommand(messageText, chatID);
            } else {
                    handleUser(messageText, chatID);
            }
        } else if (update.hasCallbackQuery()) {
            String callData = update.getCallbackQuery().getData();
            long chatID = update.getCallbackQuery().getMessage().getChatId();

            handleCallback(callData, chatID);
        }
    }

    private void handleCommand(String command, long chatID) {
        String answer;
        switch (command) {
            case "/start":
                 answer = "Добро пожаловать! Этот бот предназначен для создания изображений с помощью нейроной сети Kandinsky 3.1";
                 SendMessage startMessage = messageMaker.makeMessage(answer, chatID);
                try {
                    client.execute(startMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;
            case "/makeImage":
                answer = "Пожалуйста, введите ваш промпт для создания изображения:";
                SendMessage promptMessage = messageMaker.makeMessage(answer, chatID);
                waitingForPrompt.put(chatID, true);
                try {
                    client.execute(promptMessage);
                }catch (TelegramApiException e){
                    e.printStackTrace();
                }
                break;
        }
    }

    private void handleUser(String userInput, long chatID) {
        if(waitingForPrompt.get(chatID)){
            waitingForPrompt.put(chatID, false);

            Request request = new Request();
            request.setPrompt(userInput);

            requests.put(chatID, request);

            imageRequest(chatID);
        }

    }

    private void handleCallback (String callData, long chatID){
        Request request = requests.get(chatID);
        if (request != null) {
            switch (callData) {
                case "16:9":
                    request.setHeight(1024);
                    request.setWeight(576);
                    break;
                case "9:16":
                    request.setWeight(1024);
                    requests.get(chatID).setHeight(576);
                case "1:1":
                    request.setHeight(1024);
                    request.setWeight(1024);
                    break;
                case "3:2":
                    request.setHeight(1024);
                    request.setWeight(680);
                    break;
                case "2:3":
                    request.setWeight(680);
                    request.setHeight(1024);
                    break;
            }
        }
    }


    private void imageRequest(long chatID) {
        String prompt;
        prompt = requests.get(chatID).getPrompt();
        SendMessage sizeMessage = messageMaker.makeMessageWithButtons("Выберите соотношение сторон:", chatID, markupMaker::sizeButtons);
        try {
            client.execute(sizeMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}

class Request {
    String prompt;
    int weight;
    int height;
    String model;

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWeight(int weight){
        this.weight = weight;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPrompt() {
        return prompt;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public String getModel() {
        return model;
    }
}