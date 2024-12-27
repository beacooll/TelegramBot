package org.example;

import java.time.LocalTime;

public class ShowFilm {
    private Film film;
    private LocalTime showtime;
    private double ticketPrice;
    private int placesInCinemaCount;

    public ShowFilm() {
        this.film = null;
        this.showtime = null;
        this.ticketPrice = 0;
        placesInCinemaCount = 50;
    }
    public ShowFilm(Film film, LocalTime time, double price) {
        this.film = film;
        this.showtime = time;
        this.ticketPrice = price;
        placesInCinemaCount = 50;
    }

    public Film getFilm() {
        return film;
    }

    public void setShowtime(LocalTime time) {
        showtime = time;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public LocalTime getShowtime() {
        return showtime;
    }

    public void setTicketPrice(double i) {
        ticketPrice = i;
    }

    public int getPlacesInCinemaCount() {
        return placesInCinemaCount;
    }

    public void setPlacesInCinemaCount(int placesInCinemaCount) {
        this.placesInCinemaCount = placesInCinemaCount;
    }
}