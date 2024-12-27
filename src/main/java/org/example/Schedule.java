package org.example;


import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


public class Schedule {
    private static List<ShowFilm> showFilms;

    Schedule(FilmsCollection films) {
        showFilms = new ArrayList<>();
        showFilms.add(new ShowFilm(films.findFilmByTitle("OnePiece RED"), LocalTime.of(12,0), 500));
        showFilms.add(new ShowFilm(films.findFilmByTitle("Интерстеллар"), LocalTime.of(15,0), 700));
        showFilms.add(new ShowFilm(films.findFilmByTitle("Как меня осудили на 7 суток"), LocalTime.of(18,0), 1000));
    }
    public List<ShowFilm> getShowtimes(){
        return showFilms;
    }
    @Override
    public String toString(){
        StringBuilder schedule = new StringBuilder(" ");
        for (ShowFilm showtime: showFilms){
            schedule.append(showtime.getFilm().getTitle()).append(" - ").append(showtime.getShowtime()).append("\n");
        }
        return schedule.toString();
    }
    public ShowFilm find(String title, LocalTime time){
        ShowFilm findShowFilm = null;
        for (ShowFilm showtime: showFilms){
            if(showtime.getFilm().getTitle().equals(title) && showtime.getShowtime().equals(time)) {
                findShowFilm = showtime;
                break;
            }
        }
        return findShowFilm;
    }

    public void add(ShowFilm showtime){
        showFilms.add(showtime);
    }
    public void removeShowtime(ShowFilm showtime) {
        showFilms.remove(showtime);
    }
    public boolean checkFilmTime(LocalTime time){
        boolean ans = false;
        for (ShowFilm showFilm:showFilms){
            if(showFilm.getShowtime().equals(time)){
                ans = true;
                break;
            }
        }
        return ans;
    }
}
