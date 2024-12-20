package org.example;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class TelegramBot implements LongPollingSingleThreadUpdateConsumer {
    private final TelegramClient client;

    TelegramBot(String token){
        client = new OkHttpTelegramClient(token);
    }

    @Override
    public void consume(Update update){
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatID = update.getMessage().getChatId();

            SendMessage message = SendMessage
                    .builder()
                    .chatId(chatID)
                    .text(messageText)
                    .build();
            try {
                switch (messageText) {
                    case "/start":
                        client.execute(message);
                        break;
                    case "makeImage":
                }
            }
            catch (TelegramApiException e){
                e.printStackTrace();
            }
        }
    }
}