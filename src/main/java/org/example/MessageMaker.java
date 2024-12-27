package org.example;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import java.util.*;
import java.util.function.Function;

public class MessageMaker {
    public SendMessage makeMessage(String answer, long chatID){
        return SendMessage.builder()
                .chatId(chatID)
                .text(answer)
                .build();
    }
    public SendMessage makeMessageWithButtons(String answer, long chatID, Function<Long, InlineKeyboardMarkup> markupFunction ){
        InlineKeyboardMarkup markup = markupFunction.apply(chatID);
        return  SendMessage.builder()
                .chatId(chatID)
                .text(answer)
                .replyMarkup(markup)
                .build();
    }
    public SendPhoto makeMessageWithPhoto(Film film, long chatID) {
        return SendPhoto.builder()
                .chatId(chatID)
                .photo(new InputFile(film.getImageURL()))
                .caption(film.toString())
                .build();
    }
}
class MarkupMaker {
    static InlineKeyboardRowMaker inlineKeyboardRowMaker = new InlineKeyboardRowMaker();
    static InlineKeyboardButtonMaker inlineKeyboardButtonMaker = new InlineKeyboardButtonMaker();

    public InlineKeyboardMarkup balanceButtons(long ID){
        return InlineKeyboardMarkup.builder()
                .keyboardRow(inlineKeyboardRowMaker.make("100","+100"))
                .keyboardRow(inlineKeyboardRowMaker.make("500","+500"))
                .keyboardRow(inlineKeyboardRowMaker.make("2000","+2000"))
                .keyboardRow(inlineKeyboardRowMaker.make("7700","+7700")
                )
                .build();
    }
    public InlineKeyboardMarkup queryButton(long ID){
        return InlineKeyboardMarkup.builder()
                .keyboardRow(inlineKeyboardRowMaker.make("Посмотреть какие есть фильмы", "films"))
                        .build();
    }
    public InlineKeyboardMarkup toBalanceButtons(long ID) {
        return InlineKeyboardMarkup.builder()
                .keyboardRow(inlineKeyboardRowMaker.make("Пополнение банаеса","balance"))
                .build();
    }
    public static InlineKeyboardMarkup addFilmsButtons(CommandContext commandContext) {
        List<Film> films = commandContext.getFilms().getFilms();
        Iterator<Film> iterator = films.iterator();
        List<InlineKeyboardRow> rows = new ArrayList<>();

        while (iterator.hasNext()) {
            Film film = iterator.next();
            String title = film.getTitle();
            InlineKeyboardButton button = inlineKeyboardButtonMaker.make(title,"add " + title);
            InlineKeyboardRow row = new InlineKeyboardRow();
            row.add(button);
            rows.add(row);
        }
        return InlineKeyboardMarkup.builder()
                .keyboard(rows)
                .build();
    }
    public static InlineKeyboardMarkup FilmsButtons(CommandContext commandContext, String command){
        List<ShowFilm> showfilms = commandContext.getSchedule().getShowtimes();
        Iterator<ShowFilm> iterator = showfilms.iterator();
        List<InlineKeyboardRow> rows = new ArrayList<>();
        UserManager userManager = new UserManager();
        long chatID = commandContext.getChatId();


        while (iterator.hasNext()) {
            ShowFilm showtime = iterator.next();
            InlineKeyboardButton button = null;
            String title = showtime.getFilm().getTitle();
            if(command.equals("add ")){
                button = inlineKeyboardButtonMaker.make(title,command + title);
            }
            else if(command.equals("delete ")){
                button = inlineKeyboardButtonMaker.make(title + " " + showtime.getShowtime().toString(),
                        command + title + showfilms.indexOf(commandContext.getSchedule().find(title, showtime.getShowtime())));
            }
            else if(command.isEmpty()){
                button = inlineKeyboardButtonMaker.make(title,command + title);
            }
            else if(title.equals(command)){
                button = inlineKeyboardButtonMaker.make(title + " " + showtime.getShowtime() + " " + showtime.getTicketPrice(),
                        "buy " + title + showfilms.indexOf(commandContext.getSchedule().find(title, showtime.getShowtime())));
            }
            if (button != null) {
                InlineKeyboardRow row = new InlineKeyboardRow();
                row.add(button);
                rows.add(row);
            }
        }
        return InlineKeyboardMarkup.builder()
                .keyboard(rows)
                .build();
    }
    public InlineKeyboardMarkup timeButtons(long ID){
        return InlineKeyboardMarkup.builder()
                .keyboardRow(inlineKeyboardRowMaker.make("12:00","12:00"))
                .keyboardRow(inlineKeyboardRowMaker.make("13:30","13:30"))
                .keyboardRow(inlineKeyboardRowMaker.make("15:00","15:00"))
                .keyboardRow(inlineKeyboardRowMaker.make("16:30","16:30"))
                .keyboardRow(inlineKeyboardRowMaker.make("18:00","18:00"))
                .keyboardRow(inlineKeyboardRowMaker.make("19:30","19:30"))
                .keyboardRow(inlineKeyboardRowMaker.make("21:00","21:00"))
                .keyboardRow(inlineKeyboardRowMaker.make("22:30","22:30"))
                .build();
    }
    public static InlineKeyboardMarkup priceButtons(long ID){
        return InlineKeyboardMarkup.builder()
                .keyboardRow(inlineKeyboardRowMaker.make("500","500"))
                .keyboardRow(inlineKeyboardRowMaker.make("100","100"))
                .keyboardRow(inlineKeyboardRowMaker.make("1000","1000"))
                .keyboardRow(inlineKeyboardRowMaker.make("700","700"))
                .build();
    }
}
class InlineKeyboardRowMaker{
    public InlineKeyboardRow make(String text, String callBack){
        InlineKeyboardButtonMaker inlineKeyboardButtonMaker = new InlineKeyboardButtonMaker();
        return new InlineKeyboardRow(inlineKeyboardButtonMaker.make(text,callBack));
    }
}
class InlineKeyboardButtonMaker{
    public InlineKeyboardButton make(String text, String callBack){
        return InlineKeyboardButton.builder()
                .text(text)
                .callbackData(callBack)
                .build();
    }
}