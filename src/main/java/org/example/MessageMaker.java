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
    public InlineKeyboardMarkup balanceButtons(long ID){
        return InlineKeyboardMarkup.builder()
                .keyboardRow(
                        new InlineKeyboardRow(
                                InlineKeyboardButton.builder()
                                        .text("100")
                                        .callbackData("100")
                                        .build()
                        )
                )
                .keyboardRow(
                        new InlineKeyboardRow(
                                InlineKeyboardButton.builder()
                                        .text("500")
                                        .callbackData("500")
                                        .build()
                        )
                )
                .keyboardRow(
                        new InlineKeyboardRow(
                                InlineKeyboardButton.builder()
                                        .text("2000")
                                        .callbackData("2000")
                                        .build()
                        )
                )
                .keyboardRow(
                        new InlineKeyboardRow(
                                InlineKeyboardButton.builder()
                                        .text("7700")
                                        .callbackData("7700")
                                        .build()
                        )
                )
                .build();
    }public InlineKeyboardMarkup toBalanceButtons(long ID) {
        return InlineKeyboardMarkup.builder()
                .keyboardRow(
                        new InlineKeyboardRow(
                                InlineKeyboardButton.builder()
                                        .text("100")
                                        .callbackData("100")
                                        .build()
                        )
                )
                .build();
    }
}