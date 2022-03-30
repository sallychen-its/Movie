package com.movie_admin.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;


public class EpisodeDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private LocalDate createdDate;

    private MovieDTO movie;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public MovieDTO getMovie() {
        return movie;
    }

    public void setMovie(MovieDTO movie) {
        this.movie = movie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EpisodeDTO)) {
            return false;
        }

        EpisodeDTO episodeDTO = (EpisodeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, episodeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EpisodeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", movie=" + getMovie() +
            "}";
    }
}
