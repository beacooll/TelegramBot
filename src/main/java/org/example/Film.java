package org.example;

import java.time.LocalDate;

public class Film{
    private String title;
    private String genre;
    private String description;
    private LocalDate releaseDate;
    private String director;
    private String imageURL;


    public Film(String title, String genre, String description, LocalDate releaseDate, String director, String imageURL) {
        this.title = title;
        this.genre = genre;
        this.description = description;
        this.releaseDate = releaseDate;
        this.director = director;
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return "Фильм: " + title +
                "\nЖанр: " + genre +
                "\nОписание: " + description +
                "\nДата выхода: " + releaseDate +
                "\nРежиссер: " + director;
    }

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public String getGenre() {return genre;}

    public void setGenre(String genre) {this.genre = genre;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public LocalDate getReleaseDate() {return releaseDate;}

    public void setReleaseDate(LocalDate releaseDate) {this.releaseDate = releaseDate;}

    public String getDirector() {return director;}

    public void setDirector(String director) {this.director = director;}

    public String getImageURL() {return imageURL;}
}