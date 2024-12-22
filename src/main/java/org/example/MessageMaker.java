package org.example;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
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
}
class MarkupMaker {
    public InlineKeyboardMarkup sizeButtons(long ID){
        return InlineKeyboardMarkup.builder()
                .keyboardRow(
                        new InlineKeyboardRow(
                                InlineKeyboardButton.builder()
                                        .text("16:9")
                                        .callbackData("16:9")
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
                                        .text("1:1")
                                        .callbackData("1:1")
                                        .build()
                        )
                )
                .build();
    }
}