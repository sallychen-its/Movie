package com.movie_admin.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;


public class TrailerDTO implements Serializable {

    private Long id;

    @NotNull
    private String url;

    @NotNull
    private LocalDate createdDate;

    private MovieDTO movie;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
        if (!(o instanceof TrailerDTO)) {
            return false;
        }

        TrailerDTO trailerDTO = (TrailerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, trailerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TrailerDTO{" +
            "id=" + getId() +
            ", url='" + getUrl() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", movie=" + getMovie() +
            "}";
    }
}
