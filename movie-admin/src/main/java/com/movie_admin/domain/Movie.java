package com.movie_admin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Table(name = "movie")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Movie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 200)
    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @NotNull
    @Size(max = 200)
    @Column(name = "name_in_viet_namese", length = 200, nullable = false)
    private String nameInVietNamese;

    @Column(name = "description")
    private String description;

    @Column(name = "time")
    private Long time;

    @Column(name = "i_mdb")
    private Float iMDB;

    @Column(name = "director")
    private String director;

    @Column(name = "script")
    private String script;

    @Column(name = "coutry")
    private String coutry;

    @NotNull
    @Column(name = "premiere", nullable = false)
    private LocalDate premiere;

    @NotNull
    @Column(name = "poster_url", nullable = false)
    private String posterUrl;

    @Column(name = "background_url")
    private String backgroundUrl;

    @NotNull
    @Column(name = "movie_url", nullable = false)
    private String movieUrl;

    @NotNull
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @NotNull
    @Column(name = "updated_date", nullable = false)
    private LocalDate updatedDate;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @OneToMany(mappedBy = "movie")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "movie" }, allowSetters = true)
    private Set<Trailer> trailers = new HashSet<>();

    @OneToMany(mappedBy = "movie")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "movie" }, allowSetters = true)
    private Set<Episode> episodes = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_movie__category",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "movies" }, allowSetters = true)
    private Set<Category> categories = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "rel_movie__actor", joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "actor_id"))
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "movies" }, allowSetters = true)
    private Set<Actor> actors = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "movies" }, allowSetters = true)
    private Session session;



    public Long getId() {
        return this.id;
    }

    public Movie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Movie name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameInVietNamese() {
        return this.nameInVietNamese;
    }

    public Movie nameInVietNamese(String nameInVietNamese) {
        this.setNameInVietNamese(nameInVietNamese);
        return this;
    }

    public void setNameInVietNamese(String nameInVietNamese) {
        this.nameInVietNamese = nameInVietNamese;
    }

    public String getDescription() {
        return this.description;
    }

    public Movie description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTime() {
        return this.time;
    }

    public Movie time(Long time) {
        this.setTime(time);
        return this;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Float getiMDB() {
        return this.iMDB;
    }

    public Movie iMDB(Float iMDB) {
        this.setiMDB(iMDB);
        return this;
    }

    public void setiMDB(Float iMDB) {
        this.iMDB = iMDB;
    }

    public String getDirector() {
        return this.director;
    }

    public Movie director(String director) {
        this.setDirector(director);
        return this;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getScript() {
        return this.script;
    }

    public Movie script(String script) {
        this.setScript(script);
        return this;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getCoutry() {
        return this.coutry;
    }

    public Movie coutry(String coutry) {
        this.setCoutry(coutry);
        return this;
    }

    public void setCoutry(String coutry) {
        this.coutry = coutry;
    }

    public LocalDate getPremiere() {
        return this.premiere;
    }

    public Movie premiere(LocalDate premiere) {
        this.setPremiere(premiere);
        return this;
    }

    public void setPremiere(LocalDate premiere) {
        this.premiere = premiere;
    }

    public String getPosterUrl() {
        return this.posterUrl;
    }

    public Movie posterUrl(String posterUrl) {
        this.setPosterUrl(posterUrl);
        return this;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getBackgroundUrl() {
        return this.backgroundUrl;
    }

    public Movie backgroundUrl(String backgroundUrl) {
        this.setBackgroundUrl(backgroundUrl);
        return this;
    }

    public void setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
    }

    public String getMovieUrl() {
        return this.movieUrl;
    }

    public Movie movieUrl(String movieUrl) {
        this.setMovieUrl(movieUrl);
        return this;
    }

    public void setMovieUrl(String movieUrl) {
        this.movieUrl = movieUrl;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Movie isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDate getUpdatedDate() {
        return this.updatedDate;
    }

    public Movie updatedDate(LocalDate updatedDate) {
        this.setUpdatedDate(updatedDate);
        return this;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public Movie createdDate(LocalDate createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Set<Trailer> getTrailers() {
        return this.trailers;
    }

    public void setTrailers(Set<Trailer> trailers) {
        if (this.trailers != null) {
            this.trailers.forEach(i -> i.setMovie(null));
        }
        if (trailers != null) {
            trailers.forEach(i -> i.setMovie(this));
        }
        this.trailers = trailers;
    }

    public Movie trailers(Set<Trailer> trailers) {
        this.setTrailers(trailers);
        return this;
    }

    public Movie addTrailer(Trailer trailer) {
        this.trailers.add(trailer);
        trailer.setMovie(this);
        return this;
    }

    public Movie removeTrailer(Trailer trailer) {
        this.trailers.remove(trailer);
        trailer.setMovie(null);
        return this;
    }

    public Set<Episode> getEpisodes() {
        return this.episodes;
    }

    public void setEpisodes(Set<Episode> episodes) {
        if (this.episodes != null) {
            this.episodes.forEach(i -> i.setMovie(null));
        }
        if (episodes != null) {
            episodes.forEach(i -> i.setMovie(this));
        }
        this.episodes = episodes;
    }

    public Movie episodes(Set<Episode> episodes) {
        this.setEpisodes(episodes);
        return this;
    }

    public Movie addEpisode(Episode episode) {
        this.episodes.add(episode);
        episode.setMovie(this);
        return this;
    }

    public Movie removeEpisode(Episode episode) {
        this.episodes.remove(episode);
        episode.setMovie(null);
        return this;
    }

    public Set<Category> getCategories() {
        return this.categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Movie categories(Set<Category> categories) {
        this.setCategories(categories);
        return this;
    }

    public Movie addCategory(Category category) {
        this.categories.add(category);
        category.getMovies().add(this);
        return this;
    }

    public Movie removeCategory(Category category) {
        this.categories.remove(category);
        category.getMovies().remove(this);
        return this;
    }

    public Set<Actor> getActors() {
        return this.actors;
    }

    public void setActors(Set<Actor> actors) {
        this.actors = actors;
    }

    public Movie actors(Set<Actor> actors) {
        this.setActors(actors);
        return this;
    }

    public Movie addActor(Actor actor) {
        this.actors.add(actor);
        actor.getMovies().add(this);
        return this;
    }

    public Movie removeActor(Actor actor) {
        this.actors.remove(actor);
        actor.getMovies().remove(this);
        return this;
    }

    public Session getSession() {
        return this.session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Movie session(Session session) {
        this.setSession(session);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Movie)) {
            return false;
        }
        return id != null && id.equals(((Movie) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Movie{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", nameInVietNamese='" + getNameInVietNamese() + "'" +
            ", description='" + getDescription() + "'" +
            ", time=" + getTime() +
            ", iMDB=" + getiMDB() +
            ", director='" + getDirector() + "'" +
            ", script='" + getScript() + "'" +
            ", coutry='" + getCoutry() + "'" +
            ", premiere='" + getPremiere() + "'" +
            ", posterUrl='" + getPosterUrl() + "'" +
            ", backgroundUrl='" + getBackgroundUrl() + "'" +
            ", movieUrl='" + getMovieUrl() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
