package org.example;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/*рассписание каждый день одинаковое
 */
public class Schedule {
    private static LocalDate date;
    private static List<Showtime> showtimes;


    public Schedule(LocalDate date) {
        this.date = date;
        this.showtimes = new ArrayList<>();
    }


    public void addShowtime(Film film, LocalTime time) {
        LocalDateTime showtime = LocalDateTime.of(date, time);
        showtimes.add(new Showtime(film, showtime));
        //System.out.println("Показ фильма \"" + film.getTitle() + "\" добавлен на " + showtime);
    }


    public boolean removeShowtime(Film film, LocalTime time) {
        LocalDateTime showtime = LocalDateTime.of(date, time);
        boolean removed = showtimes.removeIf(s -> s.getFilm().equals(film) && s.getShowtime().equals(showtime));
        if (removed) {
            //System.out.println("Показ фильма \"" + film.getTitle() + "\" удален из расписания.");
        } else {
            //System.out.println("Показ фильма \"" + film.getTitle() + "\" не найден в расписании.");
        }
        return removed;
    }


    public static String displaySchedule() {
        date = LocalDate.now();
        String schedule = "Расписание на " + date + ":\n";
        if (showtimes.isEmpty()) {
            schedule += "Нет показов на этот день.";
            return schedule;
        }
        for (Showtime showtime : showtimes) {
            schedule += (showtime.getShowtime() + " - " + showtime.getFilm().getTitle() + '\n');
        }
        return schedule;
    }
}
class Showtime {
    private Film film;
    private LocalDateTime showtime;
    private double ticketPrice; // Цена за билет

    public Showtime(Film film, LocalDateTime showtime) {
        this.film = film;
        this.showtime = showtime;
        this.ticketPrice = ticketPrice;
    }

    public Film getFilm() {
        return film;
    }

    public LocalDateTime getShowtime() {
        return showtime;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }
}