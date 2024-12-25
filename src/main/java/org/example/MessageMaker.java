package org.example;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.function.Function;

public class MessageMaker {
    public SendMessage makeMessage(String answer, long chatID){
        return SendMessage.builder()
                .chatId(chatID)
                .text(answer)
                .build();
    }
    public SendMessage makeMessageWithButtons(String answer, long chatID, Function<Long, InlineKeyboardMarkup> markupFunction ){
        MarkupMaker markupMaker = new MarkupMaker();
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
    public InlineKeyboardMarkup Buttons(long ID){
        return InlineKeyboardMarkup.builder()
                .keyboardRow(
                        new InlineKeyboardRow(
                                InlineKeyboardButton.builder()
                                        .text("")
                                        .callbackData("")
                                        .build()
                        )
                )
                .keyboardRow(
                        new InlineKeyboardRow(
                                InlineKeyboardButton.builder()
                                        .text("9:16")
                                        .callbackData("9:16")
                                        .build()
                        )
                )
                .keyboardRow(
                        new InlineKeyboardRow(
                                InlineKeyboardButton.builder()
                                        .text("3:2")
                                        .callbackData("3:2")
                                        .build()
                        )
                )
                .keyboardRow(
                        new InlineKeyboardRow(
                                InlineKeyboardButton.builder()
                                        .text("2:3")
                                        .callbackData("2:3")
                                        .build()
                        )
                )
                .keyboardRow(
                        new InlineKeyboardRow(
                                InlineKeyboardButton.builder()
                                        .text("1:1")
                                        .callbackData("1:1")
                                        .build()
                        )
                )
                .build();
    }
}