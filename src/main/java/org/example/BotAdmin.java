package org.example;

public class BotAdmin extends BotUser{
    FilmInputState filmInputState;
    ShowFilm showtime;


    BotAdmin(String firstName, String lastName, long chatID) {
        super(firstName, lastName, chatID);
        filmInputState = null;
        showtime = null;
    }

    public FilmInputState getFilmInputState() {
        return filmInputState;
    }

    public ShowFilm getShowtime() {
        return showtime;
    }

    public void deleteFilmInputState() {
        filmInputState = null;
    }

    public void newFilmInputState(){
        filmInputState = new FilmInputState();
    }

    public void newShowtime(){
        showtime = new ShowFilm();
    }

    public void deleteShowtime() {
        showtime = null;
    }
    public void setShowtime(ShowFilm showFilm){
        showtime = showFilm;
    }
}
