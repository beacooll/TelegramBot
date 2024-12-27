package org.example;

import java.time.LocalDate;

public class FilmInputState {
    private String title;
    private String description;
    private LocalDate releaseDate;
    private String genre;
    private String director;
    private String imageURL;
    private int step;

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStep() {
        return step;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public String getDescription() {
        return description;
    }

    public String getDirector() {
        return director;
    }

    public String getGenre() {
        return genre;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getTitle() {
        return title;
    }

    FilmInputState() {
        title = null;
        description = null;
        releaseDate = null;
        genre = null;
        director = null;
        imageURL = null;
        step = 0;
    }
}
