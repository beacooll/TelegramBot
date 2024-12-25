package org.example;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.time.LocalDate;
import java.util.Optional;

public class FilmsCollection implements Iterable<Film> {
    private List <Film> films;
    FilmsCollection(){
        films = new ArrayList<>();
    }
    public void addFilm(String title, String genre, String description, int duration, LocalDate releaseDate, String director, String imageURL) {
        films.add(new Film(title, genre, description, duration, releaseDate, director, imageURL));
    }

    public boolean removeFilm(String title) {
        return films.removeIf(film -> film.getTitle().equalsIgnoreCase(title));
    }

    public Optional<Film> findFilmByTitle(String title) {
        return films.stream()
                .filter(film -> film.getTitle().equalsIgnoreCase(title))
                .findFirst();
    }

    public List<Film> findFilmsByGenre(String genre) {
        List<Film> result = new ArrayList<>();
        for (Film film : films) {
            if (film.getGenre().equalsIgnoreCase(genre)) {
                result.add(film);
            }
        }
        return result;
    }


    public List<Film> findFilmsByReleaseDate(LocalDate releaseDate) {
        List<Film> result = new ArrayList<>();
        for (Film film : films) {
            if (film.getReleaseDate().equals(releaseDate)) {
                result.add(film);
            }
        }
        return result;
    }

    @NotNull
    @Override
    public Iterator<Film> iterator() {
        return films.iterator();
    }
}
class Film{
    private String title;
    private String genre;
    private String description;
    private int duration;
    private LocalDate releaseDate;
    private String director;
    private String imageURL;


    public Film(String title, String genre, String description, int duration, LocalDate releaseDate, String director, String imageURL) {
        this.title = title;
        this.genre = genre;
        this.description = description;
        this.duration = duration;
        this.releaseDate = releaseDate;
        this.director = director;
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return "Фильм: " + title +
                "\nЖанр: " + genre +
                "\nОписание: " + description +
                "\nПродолжительность: " + duration + " минут" +
                "\nДата выхода: " + releaseDate +
                "\nРежиссер: " + director;
    }

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public String getGenre() {return genre;}

    public void setGenre(String genre) {this.genre = genre;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public int getDuration() {return duration;}

    public void setDuration(int duration) {this.duration = duration;}

    public LocalDate getReleaseDate() {return releaseDate;}

    public void setReleaseDate(LocalDate releaseDate) {this.releaseDate = releaseDate;}

    public String getDirector() {return director;}

    public void setDirector(String director) {this.director = director;}

    public String getImageURL() {return imageURL;}
}