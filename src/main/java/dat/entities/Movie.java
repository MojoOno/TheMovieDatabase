package dat.entities;

import dat.dtos.MovieDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Movie
{
    @Id
    private Long id;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(name = "release_date")
    private LocalDate releaseDate;
    private double rating;
    private double popularity;

    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Genre> genres = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "movie_actor",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id"))
    private Set<Credit> actors = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "movie_director",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "director_id"))
    private Set<Credit> directors = new HashSet<>();

    public Movie(MovieDTO movieDTO)
    {
        this.id = movieDTO.getMovieId();
        this.title = movieDTO.getTitle();
        this.description = movieDTO.getDescription();
        this.releaseDate = movieDTO.getReleaseDate();
        this.rating = movieDTO.getScore();
        this.popularity = movieDTO.getPopularity();
    }
    public void addGenre(Genre genre)
    {
        if (genre != null)
        {
            genres.add(genre);
            genre.addMovie(this);
        }
    }

    public void addActor(Credit credit)
    {
        if (credit != null)
        {
            actors.add(credit);
            credit.addActorMovie(this);
        }
    }

    public void addDirector(Credit credit)
    {
        if (credit != null)
        {
            directors.add(credit);
            credit.addDirectorMovie(this);
        }
    }

}
