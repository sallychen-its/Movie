package com.movie_admin.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;


public class ActorDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 200)
    private String name;

    @Size(max = 100)
    private String job;

    @Size(max = 10)
    private String gender;

    private LocalDate dateOfBirth;

    private String homeTown;

    private String story;

    private String imageUrl;

    @NotNull
    private LocalDate createdDate;

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

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ActorDTO)) {
            return false;
        }

        ActorDTO actorDTO = (ActorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, actorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ActorDTO{" +
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
