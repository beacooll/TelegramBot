package org.example;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.DateTimeException;
import java.time.LocalDate;

import static org.example.MarkupMaker.addFilmsButtons;
import static org.example.MarkupMaker.FilmsButtons;

public class HandleAdminCommand {
    public void processCommand(String command, CommandContext commandContext){
        String answer;
        SendMessage message = null;

        long chatID = commandContext.getChatId();
        MessageMaker messageMaker = commandContext.getMessageMaker();
        OkHttpTelegramClient client = commandContext.getClient();
        UserManager userManager = commandContext.getUserManager();

        switch (command) {
            case "/start":
                answer = "Добро пожаловать! \nЭтот бот предназначен для посетителей кинотеатра \"OnlyFilms\"\nВы являетесь администратором этого бота";
                message = messageMaker.makeMessage(answer, chatID);
                break;
            case "/add_film":
                answer = "Введите название фильма: ";
                message = messageMaker.makeMessage(answer, chatID);
                commandContext.getUserManager().getBotAdmin(chatID).newFilmInputState();
                break;
            case "/new_film_in_schedule":
                answer = "Выбирите фильм, который нужно добавить в расписание";
                message = messageMaker.makeMessageWithButtons(answer, chatID, (id) -> addFilmsButtons(commandContext));
                break;
            case "/delete_film_from_schedule":
                answer = "Выбирите фильм, который нужно удалить из расписание";
                message = messageMaker.makeMessageWithButtons(answer, chatID, (id) -> FilmsButtons(commandContext, "delete "));
                break;
            case "/help":
                answer = "Вам доступны следующие команды\n/add_film\n/new_film_in_schedule\n/delete_film_from_schedule\n/view_sold_tickets\n/become_a_user";
                message = messageMaker.makeMessage(answer, chatID);
                break;
            case "/become_a_user":
                userManager.addUser (chatID, userManager.getBotAdmin(chatID).getFirstName(), userManager.getBotAdmin(chatID).getLastName());
                userManager.deleteAdmin(chatID);
                answer = "Теперь вы не админ!";
                message = messageMaker.makeMessage(answer, chatID);
                break;
            case "/view_sold_tickets":
                answer = commandContext.soldTickets();
                message = messageMaker.makeMessage(answer, chatID);
                break;
            default:
                answer = "Напишите /help, чтобы ознакомится со списком команд";
                message = messageMaker.makeMessage(answer, chatID);
                break;
        }
        try {
            client.execute(message);
        }catch (
                TelegramApiException e){
            e.printStackTrace();
        }
    }
    public void filmInputProcess(String input, CommandContext commandContext){
        String text = "";
        long chatID = commandContext.getChatId();
        BotAdmin admin = commandContext.getUserManager().getBotAdmin(chatID);
        MessageMaker messageMaker = commandContext.getMessageMaker();
        FilmInputState inputState = admin.getFilmInputState();
        OkHttpTelegramClient client = commandContext.getClient();

        if (inputState == null) return;

        switch (inputState.getStep()) {
            case 0:
                inputState.setTitle(input);
                inputState.setStep(1);
                text = "Введите описание фильма:";
                break;
            case 1:
                inputState.setDescription(input);
                inputState.setStep(2);
                text = "Введите дату выхода фильма (в формате ГГГГ-ММ-ДД):";
                break;
            case 2:
                try{
                    inputState.setReleaseDate(LocalDate.parse(input));
                }catch (DateTimeException e){
                    e.printStackTrace();
                    text = "Некорректно введена дата. Попробуйте еще раз";
                    break;
                }
                inputState.setStep(3);
                text = "Введите жанр фильма:";
                break;
            case 3:
                inputState.setGenre(input);
                inputState.setStep(4);
                text = "Введите имя режиссера:";
                break;
            case 4:
                inputState.setDirector(input);
                inputState.setStep(5);
                text = "Введите ссылку на постер фильма:";
                break;
            case 5:
                inputState.setImageURL(input);
                if(!isImageURL(input)){
                    text = "Похоже вы ввели некоректную ссылку, введите ссылку на изображение";
                    break;
                }
                FilmsCollection films = commandContext.getFilms();
                films.addFilm(inputState.getTitle(), inputState.getGenre(), inputState.getDescription(),
                        inputState.getReleaseDate(), inputState.getDirector(),
                        inputState.getImageURL());
                text = "Фильм успешно добавлен!";
                admin.deleteFilmInputState();
                break;
        }
        try{
            client.execute(messageMaker.makeMessage(text, chatID));
        }catch (TelegramApiException e){
            e.printStackTrace();
        }
    }
    public static boolean isImageURL(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD"); // Use HEAD to avoid downloading the content
            connection.connect();
            String contentType = connection.getContentType();
            return contentType != null && contentType.startsWith("image/");
        } catch (MalformedURLException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }
}