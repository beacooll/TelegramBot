package org.example;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandContext {
    private final long chatId;
    private final OkHttpTelegramClient client;
    private final MessageMaker messageMaker;
    private final MarkupMaker markupMaker;
    private final FilmsCollection films;
    private UserManager userManager;
    private Schedule schedule;
    private HashMap<BotUser, ShowFilm> soldTickets;

    public CommandContext(long chatId,
                          OkHttpTelegramClient client, MessageMaker messageMaker,
                          MarkupMaker markupMaker, FilmsCollection films,
                          UserManager userManager, Schedule schedule,
                          HashMap<BotUser, ShowFilm> soldTickets) {
        this.chatId = chatId;
        this.client = client;
        this.messageMaker = messageMaker;
        this.markupMaker = markupMaker;
        this.films = films;
        this.userManager = userManager;
        this.schedule = schedule;
        this.soldTickets = soldTickets;
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
    public void put(BotUser user, ShowFilm showFilm){
        soldTickets.put(user, showFilm);
    }
    public String soldTickets(){
        StringBuilder result = new StringBuilder();
        for (Map.Entry<BotUser, ShowFilm> entry : soldTickets.entrySet()) {
            BotUser key = entry.getKey();
            ShowFilm value = entry.getValue();
            result.append(key.getFirstName()+" "+key.getLastName()+
                    ": "+value.getFilm().getTitle()+" - "+value.getShowtime()+"\n");
        }
        return result.toString();
    }
}