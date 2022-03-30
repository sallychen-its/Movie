package com.movie_admin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Table(name = "trailer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Trailer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "url", nullable = false)
    private String url;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @ManyToOne
    @JsonIgnoreProperties(value = { "trailers", "episodes", "categories", "actors", "session" }, allowSetters = true)
    private Movie movie;



    public Long getId() {
        return this.id;
    }

    public Trailer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return this.url;
    }

    public Trailer url(String url) {
        this.setUrl(url);
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public Trailer createdDate(LocalDate createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Movie getMovie() {
        return this.movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Trailer movie(Movie movie) {
        this.setMovie(movie);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Trailer)) {
            return false;
        }
        return id != null && id.equals(((Trailer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Trailer{" +
            "id=" + getId() +
            ", url='" + getUrl() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
