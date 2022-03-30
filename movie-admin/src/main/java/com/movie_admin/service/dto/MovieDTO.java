package com.movie_admin.service.dto;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;


public class MovieDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 200)
    private String name;

    @NotNull
    @Size(max = 200)
    private String nameInVietNamese;

    private String description;

    private Long time;

    private Float iMDB;

    private String director;

    private String script;

    private String coutry;

    @NotNull
    private LocalDate premiere;

    @NotNull
    private String posterUrl;

    private String backgroundUrl;

    @NotNull
    private String movieUrl;

    @NotNull
    private Boolean isActive;

    @NotNull
    private LocalDate updatedDate;

    @NotNull
    private LocalDate createdDate;

    private Set<CategoryDTO> categories = new HashSet<>();

    private Set<ActorDTO> actors = new HashSet<>();

    private SessionDTO session;

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

    public String getNameInVietNamese() {
        return nameInVietNamese;
    }

    public void setNameInVietNamese(String nameInVietNamese) {
        this.nameInVietNamese = nameInVietNamese;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Float getiMDB() {
        return iMDB;
    }

    public void setiMDB(Float iMDB) {
        this.iMDB = iMDB;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getCoutry() {
        return coutry;
    }

    public void setCoutry(String coutry) {
        this.coutry = coutry;
    }

    public LocalDate getPremiere() {
        return premiere;
    }

    public void setPremiere(LocalDate premiere) {
        this.premiere = premiere;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public void setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
    }

    public String getMovieUrl() {
        return movieUrl;
    }

    public void setMovieUrl(String movieUrl) {
        this.movieUrl = movieUrl;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Set<CategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryDTO> categories) {
        this.categories = categories;
    }

    public Set<ActorDTO> getActors() {
        return actors;
    }

    public void setActors(Set<ActorDTO> actors) {
        this.actors = actors;
    }

    public SessionDTO getSession() {
        return session;
    }

    public void setSession(SessionDTO session) {
        this.session = session;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MovieDTO)) {
            return false;
        }

        MovieDTO movieDTO = (MovieDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, movieDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MovieDTO{" +
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
            ", categories=" + getCategories() +
            ", actors=" + getActors() +
            ", session=" + getSession() +
            "}";
    }
}
