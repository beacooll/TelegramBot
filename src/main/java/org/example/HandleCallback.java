package org.example;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class HandleCallback {
    public void processCommand(String callData, CommandContext commandContext){
        SendMessage message = null;
        MessageMaker messageMaker = commandContext.getMessageMaker();
        UserManager userManager = commandContext.getUserManager();
        long chatID = commandContext.getChatId();
        OkHttpTelegramClient client = commandContext.getClient();
        MarkupMaker markupMaker = commandContext.getMarkupMaker();
        Schedule schedule = commandContext.getSchedule();
        ShowFilm showFilm;

        switch (callData) {
            case "+100":
                userManager.getBotUser(chatID).addToBalance(100);
                message = messageMaker.makeMessage("Вы получили 100", chatID);
                break;
            case "+500":
                userManager.getBotUser(chatID).addToBalance(500);
                message = messageMaker.makeMessage("Вы получили 500", chatID);
                break;
            case "+2000":
                userManager.getBotUser(chatID).addToBalance(2000);
                message = messageMaker.makeMessage("Вы получили 2000", chatID);
                break;
            case "+7700":
                userManager.getBotUser(chatID).addToBalance(7700);
                message = messageMaker.makeMessage("Вы получили 7700", chatID);
                break;
            case "balance":
                message = messageMaker.makeMessageWithButtons("Фильм добавлен в расписание", chatID, markupMaker::balanceButtons);
                break;
            case "12:00":
                if(userManager.getBotAdmin(chatID).getShowtime() == null){
                    return;
                }
                if(schedule.checkFilmTime(LocalTime.of(12, 0))){
                    message =  messageMaker.makeMessage("В это время дпугой сеанс, выберите другое", chatID);
                    break;
                }
                userManager.getBotAdmin(chatID).getShowtime().setShowtime(LocalTime.of(12, 0));
                showFilm  = userManager.getBotAdmin(chatID).getShowtime();
                schedule.add(showFilm);
                userManager.getBotAdmin(chatID).deleteShowtime();

                message = messageMaker.makeMessage("Фильм добавлен в расписание", chatID);
                break;
            case "13:30":
                if(userManager.getBotAdmin(chatID).getShowtime() == null){
                    return;
                }
                if(schedule.checkFilmTime(LocalTime.of(13, 30))){
                    message =  messageMaker.makeMessage("В это время дпугой сеанс, выберите другое", chatID);
                    break;
                }
                userManager.getBotAdmin(chatID).getShowtime().setShowtime(LocalTime.of(13, 30));
                showFilm  = userManager.getBotAdmin(chatID).getShowtime();
                schedule.add(showFilm);
                userManager.getBotAdmin(chatID).deleteShowtime();

                message = messageMaker.makeMessage("Фильм добавлен в расписание", chatID);
                break;
            case "15:00":
                if(userManager.getBotAdmin(chatID).getShowtime() == null){
                    return;
                }
                if(schedule.checkFilmTime(LocalTime.of(15, 0))){
                    message =  messageMaker.makeMessage("В это время дпугой сеанс, выберите другое", chatID);
                    break;
                }
                userManager.getBotAdmin(chatID).getShowtime().setShowtime(LocalTime.of(15, 0));
                showFilm  = userManager.getBotAdmin(chatID).getShowtime();
                schedule.add(showFilm);
                userManager.getBotAdmin(chatID).deleteShowtime();

                message = messageMaker.makeMessage("Фильм добавлен в расписание", chatID);
                break;
            case "16:30":
                if(userManager.getBotAdmin(chatID).getShowtime() == null){
                    return;
                }
                if(schedule.checkFilmTime(LocalTime.of(16, 30))){
                    message =  messageMaker.makeMessage("В это время дпугой сеанс, выберите другое", chatID);
                    break;
                }
                userManager.getBotAdmin(chatID).getShowtime().setShowtime(LocalTime.of(16, 30));
                showFilm  = userManager.getBotAdmin(chatID).getShowtime();
                schedule.add(showFilm);
                userManager.getBotAdmin(chatID).deleteShowtime();

                message = messageMaker.makeMessage("Фильм добавлен в расписание", chatID);
                break;
            case "18:00":
                if(userManager.getBotAdmin(chatID).getShowtime() == null){
                    return;
                }
                if(schedule.checkFilmTime(LocalTime.of(18, 0))){
                    message =  messageMaker.makeMessage("В это время дпугой сеанс, выберите другое", chatID);
                    break;
                }
                userManager.getBotAdmin(chatID).getShowtime().setShowtime(LocalTime.of(18, 0));
                showFilm  = userManager.getBotAdmin(chatID).getShowtime();
                schedule.add(showFilm);
                userManager.getBotAdmin(chatID).deleteShowtime();

                message = messageMaker.makeMessage("Фильм добавлен в расписание", chatID);
                break;
            case "19:30":
                if(userManager.getBotAdmin(chatID).getShowtime() == null){
                    return;
                }
                if(schedule.checkFilmTime(LocalTime.of(19, 30))){
                    message =  messageMaker.makeMessage("В это время дпугой сеанс, выберите другое", chatID);
                    break;
                }
                userManager.getBotAdmin(chatID).getShowtime().setShowtime(LocalTime.of(19, 30));
                showFilm  = userManager.getBotAdmin(chatID).getShowtime();
                schedule.add(showFilm);
                userManager.getBotAdmin(chatID).deleteShowtime();

                message = messageMaker.makeMessage("Фильм добавлен в расписание", chatID);
                break;
            case "21:00":
                if(userManager.getBotAdmin(chatID).getShowtime() == null){
                    return;
                }
                if(schedule.checkFilmTime(LocalTime.of(21, 0))){
                    message =  messageMaker.makeMessage("В это время дпугой сеанс, выберите другое", chatID);
                    break;
                }
                userManager.getBotAdmin(chatID).getShowtime().setShowtime(LocalTime.of(21, 0));
                showFilm  = userManager.getBotAdmin(chatID).getShowtime();
                schedule.add(showFilm);
                userManager.getBotAdmin(chatID).deleteShowtime();

                message = messageMaker.makeMessage("Фильм добавлен в расписание", chatID);
                break;
            case "22:30":
                if(userManager.getBotAdmin(chatID).getShowtime() == null){
                    return;
                }
                if(schedule.checkFilmTime(LocalTime.of(22, 30))){
                    message =  messageMaker.makeMessage("В это время дпугой сеанс, выберите другое", chatID);
                    break;
                }
                userManager.getBotAdmin(chatID).getShowtime().setShowtime(LocalTime.of(22, 30));
                showFilm  = userManager.getBotAdmin(chatID).getShowtime();
                schedule.add(showFilm);
                userManager.getBotAdmin(chatID).deleteShowtime();

                message = messageMaker.makeMessage("Фильм добавлен в расписание", chatID);
                break;
            case "100":
                showFilm  = userManager.getBotAdmin(chatID).getShowtime();
                showFilm.setTicketPrice(100);

                message = messageMaker.makeMessageWithButtons("Выберите время", chatID, markupMaker::timeButtons);
                break;
            case "500":
                showFilm  = userManager.getBotAdmin(chatID).getShowtime();
                showFilm.setTicketPrice(500);

                message = messageMaker.makeMessageWithButtons("Выберите время", chatID, markupMaker::timeButtons);
                break;
            case "700":
                showFilm  = userManager.getBotAdmin(chatID).getShowtime();
                showFilm.setTicketPrice(700);

                message = messageMaker.makeMessageWithButtons("Выберите время", chatID, markupMaker::timeButtons);
                break;
            case "1000":
                showFilm  = userManager.getBotAdmin(chatID).getShowtime();
                showFilm.setTicketPrice(1000);

                message = messageMaker.makeMessageWithButtons("Выберите время", chatID, markupMaker::timeButtons);
                break;
            case "films":
                HandleUserCommand handleUserCommand = new HandleUserCommand();
                handleUserCommand.sendFilmsToUser(chatID, commandContext);
            default:
                callFilm(commandContext, callData);
                return;
        }

        try {
            client.execute(message);
        }
        catch (
                TelegramApiException e){
            e.printStackTrace();
        }
    }
    public void callFilm(CommandContext commandContext, String callData){
        List<Film> films = commandContext.getFilms().getFilms();
        Iterator<Film> iterator = films.iterator();
        MessageMaker messageMaker = commandContext.getMessageMaker();
        UserManager userManager = commandContext.getUserManager();
        OkHttpTelegramClient client = commandContext.getClient();
        Schedule schedule = commandContext.getSchedule();
        long chatID = commandContext.getChatId();

        while(iterator.hasNext()){
            Film film = iterator.next();
            String title = film.getTitle();


            if(Objects.equals(callData, "add " + title)){
                userManager.getBotAdmin(chatID).newShowtime();
                userManager.getBotAdmin(chatID).getShowtime().setFilm(film);
                SendMessage message = messageMaker.makeMessageWithButtons("Выбирете цену билета фильма", chatID, MarkupMaker::priceButtons);
                try{
                    client.execute(message);
                }
                catch (TelegramApiException e){
                    e.printStackTrace();
                }
            }else if(callData.contains("delete " + title) ) {
                String index = callData.substring( 7 + title.length());
                schedule.removeShowtime(schedule.getShowtimes().get(Integer.parseInt(index)));

                SendMessage message = messageMaker.makeMessage("Фильм удален из расписания", chatID);
                try {
                    client.execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }else if(callData.contains("buy " + title)) {
                String index = callData.substring( 4 + title.length());
                ShowFilm showFilm = schedule.getShowtimes().get(Integer.parseInt(index));
                SendMessage message = null;
                if(userManager.getBotUser(chatID).getBalance() >= showFilm.getTicketPrice()){
                    if(showFilm.getPlacesInCinemaCount() >= 1){
                        message = messageMaker.makeMessage("Вы купили билет на фильм \"" + title + "\" \nСеанс начнется в " + showFilm.getShowtime(), chatID);
                        userManager.getBotUser(chatID).subBalance(showFilm.getTicketPrice());
                        userManager.getBotUser(chatID).setFilmQuery(null);
                        commandContext.put(commandContext.getUserManager().getBotUser(chatID), showFilm);
                        showFilm.setPlacesInCinemaCount(showFilm.getPlacesInCinemaCount()-1);
                    }

                }else{
                    message = messageMaker.makeMessage("Пополните баланс чтобы получить билет на фильм", chatID);
                    userManager.getBotUser(chatID).setFilmQuery(null);
                }



                try {
                    client.execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
