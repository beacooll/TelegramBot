package org.example;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;


public class TelegramBot implements LongPollingSingleThreadUpdateConsumer {
    private final TelegramClient client;

    private static HashMap<Long, BotUser> users = new HashMap<>();
    private static HashMap<Long, Admin> admins = new HashMap<>();
    private static FilmsCollection films;
    private static Schedule schedule = new Schedule(LocalDate.now());

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

                users.put(chatID, new BotUser(chatID, lastName, firstName));
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
                answer = "Добро пожаловать! \nЭтот бот предназначен для посетителей кинотеатра \"NoisyCrazyBizarreFilms\"\nТут вы можите ознакомится с афишей, рассписанием, а также покупать билеты на сеансы";
                message = messageMaker.makeMessage(answer, chatID);
                break;
            case "/profile":
                answer = "Уважаемый " + user.getFirstName() + ' ' + user.getLastName() + ", вот все что мы о вас знаем:\nВаш баланс: " + user.getBalance();
                message = messageMaker.makeMessageWithButtons(answer, chatID, markupMaker::toBalanceButtons);
                break;
            case "/view the poster":
                sendFilmsToUser(chatID);
                break;
            case "/view the schedule":
                answer = Schedule.displaySchedule();
                message = messageMaker.makeMessage(answer, chatID);
                break;
            case "/replenish your balance":
                answer = "Выбирете необходимую сумму";
                message = messageMaker.makeMessageWithButtons(answer, chatID, markupMaker::balanceButtons);
                break;
            case "/become a admin":
                admins.put(chatID, new Admin(chatID, users.get(chatID).getLastName(), users.get(chatID).getFirstName()));
                users.remove(chatID);
                break;
            }try {
            client.execute(message);
        }catch (TelegramApiException e){
            e.printStackTrace();
        }

    }

    private void handleAdminCommand(String command, BotUser user) {
        String answer;
        long chatID = user.getChatID();
        SendMessage message = null;

        switch (command) {
            case "/start":
                answer = "Добро пожаловать! \nЭтот бот предназначен для посетителей кинотеатра \"NoisyCrazyBizarreFilms\"\nВы являетесь администратором этого бота";
                message = messageMaker.makeMessage(answer, chatID);
                break;
            case "/add film":

                break;
        }
        try {
            client.execute(message);
        }catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    private void handleCallback (String callData, long chatID){
        SendMessage message = null;
        switch (callData) {
            case "100":
                users.get(chatID).addToBalance(100);
                message = messageMaker.makeMessage("Вы получили 100", chatID);
                break;
            case "500":
                users.get(chatID).addToBalance(500);
                message = messageMaker.makeMessage("Вы получили 100", chatID);
                break;
            case "2000":
                users.get(chatID).addToBalance(2000);
                message = messageMaker.makeMessage("Вы получили 2000", chatID);
                break;
            case "7700":
                users.get(chatID).addToBalance(7700);
                message = messageMaker.makeMessage("Вы получили 7700", chatID);
                break;
        }
        try {
            client.execute(message);
        }
        catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    private void sendFilmsToUser(long chatID){
        Iterator<Film> iterator = films.iterator();
        while(iterator.hasNext()){
            Film film = iterator.next();
            SendPhoto message = messageMaker.makeMessageWithPhoto(film, chatID);
            try {
                client.execute(message);
            }
            catch (TelegramApiException e){
                e.printStackTrace();
            }
        }
    }
}

