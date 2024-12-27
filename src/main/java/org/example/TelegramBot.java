package org.example;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.util.HashMap;


public class TelegramBot implements LongPollingSingleThreadUpdateConsumer {
    private final OkHttpTelegramClient client;
    private static UserManager userManager = new UserManager();
    private static FilmsCollection films = new FilmsCollection();
    private static Schedule schedule = new Schedule(films);
    private static HashMap<BotUser, ShowFilm> soldTicket = new HashMap<>();
    public TelegramBot(String token) {
        client = new OkHttpTelegramClient(token);
    }

    @Override
    public void consume(Update update) {
        HandleUserCommand handleUserCommand = new HandleUserCommand();
        HandleAdminCommand handleAdminCommand = new HandleAdminCommand();
        HandleCallback handleCallback = new HandleCallback();

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatID = update.getMessage().getChatId();
            CommandContext commandContext = new CommandContext(chatID, client, new MessageMaker(), new MarkupMaker(), films, userManager, schedule, soldTicket);
            if(userManager.isAdmin(chatID)){
                if(commandContext.getUserManager().getBotAdmin(chatID).getFilmInputState() != null){
                    handleAdminCommand.filmInputProcess(messageText, commandContext);
                    return;
                }
                handleAdminCommand.processCommand(messageText, commandContext);
            }
            else if (userManager.isUser(chatID)) {
                if(commandContext.getUserManager().getBotUser(chatID).getFilmQuery() != null){
                    if(films.isFilm(messageText)){
                        userManager.getBotUser(chatID).setFilmQuery(messageText);
                        handleUserCommand.buy(commandContext, messageText);
                        return;
                    }
                    SendMessage message = commandContext.getMessageMaker().makeMessageWithButtons("Введите название фильма", chatID, commandContext.getMarkupMaker()::queryButton);
                    try {
                        client.execute(message);
                    }
                    catch (TelegramApiException e){
                        e.printStackTrace();
                    }
                }
                handleUserCommand.processCommand(messageText, commandContext);
            }
            else{
                String firstName = update.getMessage().getFrom().getFirstName();
                String lastName = update.getMessage().getFrom().getLastName();
                userManager.addUser(chatID, firstName, lastName);
                handleUserCommand.processCommand(messageText, commandContext);
            }
        } else if (update.hasCallbackQuery()) {
            String callData = update.getCallbackQuery().getData();
            long chatID = update.getCallbackQuery().getMessage().getChatId();
            CommandContext commandContext = new CommandContext(chatID, client, new MessageMaker(), new MarkupMaker(), films, userManager, schedule, soldTicket);

            handleCallback.processCommand(callData, commandContext);
        }
    }



}

