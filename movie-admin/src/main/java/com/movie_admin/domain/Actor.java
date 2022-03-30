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
@Table(name = "actor")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Actor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 200)
    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @Size(max = 100)
    @Column(name = "job", length = 100)
    private String job;

    @Size(max = 10)
    @Column(name = "gender", length = 10)
    private String gender;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "home_town")
    private String homeTown;

    @Column(name = "story")
    private String story;

    @Column(name = "image_url")
    private String imageUrl;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @ManyToMany(mappedBy = "actors")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "trailers", "episodes", "categories", "actors", "session" }, allowSetters = true)
    private Set<Movie> movies = new HashSet<>();



    public Long getId() {
        return this.id;
    }

    public Actor id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Actor name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return this.job;
    }

    public Actor job(String job) {
        this.setJob(job);
        return this;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getGender() {
        return this.gender;
    }

    public Actor gender(String gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public Actor dateOfBirth(LocalDate dateOfBirth) {
        this.setDateOfBirth(dateOfBirth);
        return this;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getHomeTown() {
        return this.homeTown;
    }

    public Actor homeTown(String homeTown) {
        this.setHomeTown(homeTown);
        return this;
    }

    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    public String getStory() {
        return this.story;
    }

    public Actor story(String story) {
        this.setStory(story);
        return this;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public Actor imageUrl(String imageUrl) {
        this.setImageUrl(imageUrl);
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public Actor createdDate(LocalDate createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Set<Movie> getMovies() {
        return this.movies;
    }

    public void setMovies(Set<Movie> movies) {
        if (this.movies != null) {
            this.movies.forEach(i -> i.removeActor(this));
        }
        if (movies != null) {
            movies.forEach(i -> i.addActor(this));
        }
        this.movies = movies;
    }

    public Actor movies(Set<Movie> movies) {
        this.setMovies(movies);
        return this;
    }

    public Actor addMovie(Movie movie) {
        this.movies.add(movie);
        movie.getActors().add(this);
        return this;
    }

    public Actor removeMovie(Movie movie) {
        this.movies.remove(movie);
        movie.getActors().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Actor)) {
            return false;
        }
        return id != null && id.equals(((Actor) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Actor{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", job='" + getJob() + "'" +
            ", gender='" + getGender() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", homeTown='" + getHomeTown() + "'" +
            ", story='" + getStory() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
