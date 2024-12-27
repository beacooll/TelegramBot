package org.example;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.time.LocalDate;


public class FilmsCollection implements Iterable<Film> {
    private List <Film> films;
    FilmsCollection(){
        films = new ArrayList<>();
        films.add(new Film("OnePiece RED", "Мьюзикл, аниме", "Японский мултик про пиратов",
                LocalDate.parse("2022-07-22"), "Gorō Taniguchi",
                "https://upload.wikimedia.org/wikipedia/en/4/44/One_Piece_Film_Red_Visual_Poster.jpg" ));
        films.add(new Film("Интерстеллар", "Научная фантастика, драмма", "Космос космос космос ЧЕРНАЯ ДЫРА",
                LocalDate.parse("2014-09-18"), "Кристофер Нолан",
                "https://upload.wikimedia.org/wikipedia/ru/c/c3/Interstellar_2014.jpg" ));
        films.add(new Film("Как меня осудили на 7 суток", "Мистика", "Очень страшно",
                LocalDate.parse("2023-02-17"), "Вячеслав Машнов",
                "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse2.mm.bing.net%2Fth%3Fid%3DOVP.eXFgN5CFqaRyRD2_ewjfgAEsDh%26pid%3DApi&f=1&ipt=dd699b4d80ae0e4a1cab0b29e57515addaaa5dbd2c59fdb65426de2593c28ec1&ipo=videos" ));
    }

    public void addFilm(String title, String genre, String description, LocalDate releaseDate, String director, String imageURL) {
        films.add(new Film(title, genre, description, releaseDate, director, imageURL));
    }

    public boolean removeFilm(String title) {
        return films.removeIf(film -> film.getTitle().equalsIgnoreCase(title));
    }

    public List<Film> getFilms() {
        return films;
    }

    public Film findFilmByTitle(String title) {
        for (Film film : films) {
            if (film.getTitle().equals(title)) {
                return film; // Return the film if the title matches
            }
        }
        return null;
    }
    public boolean isFilm(String title){
        return findFilmByTitle(title)!=null;
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
