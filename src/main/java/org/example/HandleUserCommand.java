package org.example;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Iterator;

import static org.example.MarkupMaker.FilmsButtons;

public class HandleUserCommand {
    public void processCommand(String command, CommandContext commandContext){
        String answer;
        SendMessage message = null;
        long chatID = commandContext.getChatId();
        BotUser user = commandContext.getUserManager().getBotUser(chatID);
        MessageMaker messageMaker = commandContext.getMessageMaker();
        MarkupMaker markupMaker = commandContext.getMarkupMaker();
        OkHttpTelegramClient client = commandContext.getClient();
        UserManager userManager = commandContext.getUserManager();

        switch (command) {
            case "/start":
                answer = "Добро пожаловать! \nЭтот бот предназначен для посетителей кинотеатра \"OnlyFilms\"\nТут вы можите ознакомится с афишей, рассписанием, а также покупать билеты на сеансы";
                message = messageMaker.makeMessage(answer, user.getChatID());
                break;
            case "/profile":
                answer = "Уважаемый " + user.getFirstName() + ' ' + user.getLastName() + ", вот все что мы о вас знаем:\nВаш баланс: " + user.getBalance();
                message = messageMaker.makeMessageWithButtons(answer, chatID, markupMaker::toBalanceButtons);
                break;
            case "/view_the_poster":
                sendFilmsToUser(chatID, commandContext);
                return;
            case "/view_the_schedule":
                answer = commandContext.getSchedule().toString();
                message = messageMaker.makeMessage(answer, chatID);
                break;
            case "/replenish_your_balance":
                answer = "Выбирете необходимую сумму";
                message = messageMaker.makeMessageWithButtons(answer, chatID, markupMaker::balanceButtons);
                break;
            case "/become_a_admin":
                userManager.addAdmin(chatID, user.getFirstName(), user.getLastName());
                userManager.deleteUser(chatID);
                answer = "Теперь вы админ!";
                message = messageMaker.makeMessage(answer, chatID);
                break;
            case "/help":
                answer = "Вам доступны следующие команды\n/profile\n/view_the_poster\n/view_the_schedule\n/replenish_your_balance\n/become_a_admin\n/buy";
                message = messageMaker.makeMessage(answer, chatID);
                break;
            case "/buy":
                answer = "Напишите название интересующего вас фильма";
                user.setFilmQuery("");
                message = messageMaker.makeMessage(answer, chatID);
                break;
            default:
                answer = "Напишите /help, чтобы ознакомится со списком команд";
                message = messageMaker.makeMessage(answer, chatID);
                break;
        }try {
            client.execute(message);
        }catch (
                TelegramApiException e){
            e.printStackTrace();
        }
    }

    public void sendFilmsToUser(long chatID, CommandContext commandContext){
        FilmsCollection films = commandContext.getFilms();
        MessageMaker messageMaker = commandContext.getMessageMaker();
        OkHttpTelegramClient client = commandContext.getClient();

        Iterator<Film> iterator = films.iterator();
        if(!iterator.hasNext()){
            try {
                SendMessage message = messageMaker.makeMessage("Похоже, что фильмов нет", chatID);
                client.execute(message);
            }
            catch (TelegramApiException e){
                e.printStackTrace();
            }

        }
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
    public void buy(CommandContext commandContext, String film){
        long chatID = commandContext.getChatId();
        MessageMaker messageMaker = commandContext.getMessageMaker();
        SendMessage message = messageMaker.makeMessageWithButtons("Выберите сеанс", chatID, (id) -> FilmsButtons(commandContext, film));
        try{
            commandContext.getClient().execute(message);
        }catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

}
