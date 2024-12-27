package org.example;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;

public class CommandContext {
    private final long chatId;
    private final OkHttpTelegramClient client;
    private final MessageMaker messageMaker;
    private final MarkupMaker markupMaker;
    private final FilmsCollection films;
    private UserManager userManager;
    private Schedule schedule;

    public CommandContext(long chatId,
                          OkHttpTelegramClient client, MessageMaker messageMaker,
                          MarkupMaker markupMaker, FilmsCollection films,
                          UserManager userManager, Schedule schedule) {
        this.chatId = chatId;
        this.client = client;
        this.messageMaker = messageMaker;
        this.markupMaker = markupMaker;
        this.films = films;
        this.userManager = userManager;
        this.schedule = schedule;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public FilmsCollection getFilms() {
        return films;
    }

    public long getChatId() {
        return chatId;
    }

    public OkHttpTelegramClient getClient() {
        return client;
    }

    public MessageMaker getMessageMaker() {
        return messageMaker;
    }

    public MarkupMaker getMarkupMaker() {
        return markupMaker;
    }
    public Schedule getSchedule() {
        return schedule;
    }
}