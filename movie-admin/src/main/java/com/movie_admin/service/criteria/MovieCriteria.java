package com.movie_admin.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.movie_admin.domain.Movie} entity. This class is used
 * in {@link com.movie_admin.web.rest.MovieResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /movies?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MovieCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter nameInVietNamese;

    private StringFilter description;

    private LongFilter time;

    private FloatFilter iMDB;

    private StringFilter director;

    private StringFilter script;

    private StringFilter coutry;

    private LocalDateFilter premiere;

    private StringFilter posterUrl;

    private StringFilter backgroundUrl;

    private StringFilter movieUrl;

    private BooleanFilter isActive;

    private LocalDateFilter updatedDate;

    private LocalDateFilter createdDate;

    private LongFilter trailerId;

    private LongFilter episodeId;

    private LongFilter categoryId;

    private LongFilter actorId;

    private LongFilter sessionId;

    private Boolean distinct;

    public MovieCriteria() {}

    public MovieCriteria(MovieCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.nameInVietNamese = other.nameInVietNamese == null ? null : other.nameInVietNamese.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.time = other.time == null ? null : other.time.copy();
        this.iMDB = other.iMDB == null ? null : other.iMDB.copy();
        this.director = other.director == null ? null : other.director.copy();
        this.script = other.script == null ? null : other.script.copy();
        this.coutry = other.coutry == null ? null : other.coutry.copy();
        this.premiere = other.premiere == null ? null : other.premiere.copy();
        this.posterUrl = other.posterUrl == null ? null : other.posterUrl.copy();
        this.backgroundUrl = other.backgroundUrl == null ? null : other.backgroundUrl.copy();
        this.movieUrl = other.movieUrl == null ? null : other.movieUrl.copy();
        this.isActive = other.isActive == null ? null : other.isActive.copy();
        this.updatedDate = other.updatedDate == null ? null : other.updatedDate.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.trailerId = other.trailerId == null ? null : other.trailerId.copy();
        this.episodeId = other.episodeId == null ? null : other.episodeId.copy();
        this.categoryId = other.categoryId == null ? null : other.categoryId.copy();
        this.actorId = other.actorId == null ? null : other.actorId.copy();
        this.sessionId = other.sessionId == null ? null : other.sessionId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public MovieCriteria copy() {
        return new MovieCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getNameInVietNamese() {
        return nameInVietNamese;
    }

    public StringFilter nameInVietNamese() {
        if (nameInVietNamese == null) {
            nameInVietNamese = new StringFilter();
        }
        return nameInVietNamese;
    }

    public void setNameInVietNamese(StringFilter nameInVietNamese) {
        this.nameInVietNamese = nameInVietNamese;
    }

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public LongFilter getTime() {
        return time;
    }

    public LongFilter time() {
        if (time == null) {
            time = new LongFilter();
        }
        return time;
    }

    public void setTime(LongFilter time) {
        this.time = time;
    }

    public FloatFilter getiMDB() {
        return iMDB;
    }

    public FloatFilter iMDB() {
        if (iMDB == null) {
            iMDB = new FloatFilter();
        }
        return iMDB;
    }

    public void setiMDB(FloatFilter iMDB) {
        this.iMDB = iMDB;
    }

    public StringFilter getDirector() {
        return director;
    }

    public StringFilter director() {
        if (director == null) {
            director = new StringFilter();
        }
        return director;
    }

    public void setDirector(StringFilter director) {
        this.director = director;
    }

    public StringFilter getScript() {
        return script;
    }

    public StringFilter script() {
        if (script == null) {
            script = new StringFilter();
        }
        return script;
    }

    public void setScript(StringFilter script) {
        this.script = script;
    }

    public StringFilter getCoutry() {
        return coutry;
    }

    public StringFilter coutry() {
        if (coutry == null) {
            coutry = new StringFilter();
        }
        return coutry;
    }

    public void setCoutry(StringFilter coutry) {
        this.coutry = coutry;
    }

    public LocalDateFilter getPremiere() {
        return premiere;
    }

    public LocalDateFilter premiere() {
        if (premiere == null) {
            premiere = new LocalDateFilter();
        }
        return premiere;
    }

    public void setPremiere(LocalDateFilter premiere) {
        this.premiere = premiere;
    }

    public StringFilter getPosterUrl() {
        return posterUrl;
    }

    public StringFilter posterUrl() {
        if (posterUrl == null) {
            posterUrl = new StringFilter();
        }
        return posterUrl;
    }

    public void setPosterUrl(StringFilter posterUrl) {
        this.posterUrl = posterUrl;
    }

    public StringFilter getBackgroundUrl() {
        return backgroundUrl;
    }

    public StringFilter backgroundUrl() {
        if (backgroundUrl == null) {
            backgroundUrl = new StringFilter();
        }
        return backgroundUrl;
    }

    public void setBackgroundUrl(StringFilter backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
    }

    public StringFilter getMovieUrl() {
        return movieUrl;
    }

    public StringFilter movieUrl() {
        if (movieUrl == null) {
            movieUrl = new StringFilter();
        }
        return movieUrl;
    }

    public void setMovieUrl(StringFilter movieUrl) {
        this.movieUrl = movieUrl;
    }

    public BooleanFilter getIsActive() {
        return isActive;
    }

    public BooleanFilter isActive() {
        if (isActive == null) {
            isActive = new BooleanFilter();
        }
        return isActive;
    }

    public void setIsActive(BooleanFilter isActive) {
        this.isActive = isActive;
    }

    public LocalDateFilter getUpdatedDate() {
        return updatedDate;
    }

    public LocalDateFilter updatedDate() {
        if (updatedDate == null) {
            updatedDate = new LocalDateFilter();
        }
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateFilter updatedDate) {
        this.updatedDate = updatedDate;
    }

    public LocalDateFilter getCreatedDate() {
        return createdDate;
    }

    public LocalDateFilter createdDate() {
        if (createdDate == null) {
            createdDate = new LocalDateFilter();
        }
        return createdDate;
    }

    public void setCreatedDate(LocalDateFilter createdDate) {
        this.createdDate = createdDate;
    }

    public LongFilter getTrailerId() {
        return trailerId;
    }

    public LongFilter trailerId() {
        if (trailerId == null) {
            trailerId = new LongFilter();
        }
        return trailerId;
    }

    public void setTrailerId(LongFilter trailerId) {
        this.trailerId = trailerId;
    }

    public LongFilter getEpisodeId() {
        return episodeId;
    }

    public LongFilter episodeId() {
        if (episodeId == null) {
            episodeId = new LongFilter();
        }
        return episodeId;
    }

    public void setEpisodeId(LongFilter episodeId) {
        this.episodeId = episodeId;
    }

    public LongFilter getCategoryId() {
        return categoryId;
    }

    public LongFilter categoryId() {
        if (categoryId == null) {
            categoryId = new LongFilter();
        }
        return categoryId;
    }

    public void setCategoryId(LongFilter categoryId) {
        this.categoryId = categoryId;
    }

    public LongFilter getActorId() {
        return actorId;
    }

    public LongFilter actorId() {
        if (actorId == null) {
            actorId = new LongFilter();
        }
        return actorId;
    }

    public void setActorId(LongFilter actorId) {
        this.actorId = actorId;
    }

    public LongFilter getSessionId() {
        return sessionId;
    }

    public LongFilter sessionId() {
        if (sessionId == null) {
            sessionId = new LongFilter();
        }
        return sessionId;
    }

    public void setSessionId(LongFilter sessionId) {
        this.sessionId = sessionId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MovieCriteria that = (MovieCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(nameInVietNamese, that.nameInVietNamese) &&
            Objects.equals(description, that.description) &&
            Objects.equals(time, that.time) &&
            Objects.equals(iMDB, that.iMDB) &&
            Objects.equals(director, that.director) &&
            Objects.equals(script, that.script) &&
            Objects.equals(coutry, that.coutry) &&
            Objects.equals(premiere, that.premiere) &&
            Objects.equals(posterUrl, that.posterUrl) &&
            Objects.equals(backgroundUrl, that.backgroundUrl) &&
            Objects.equals(movieUrl, that.movieUrl) &&
            Objects.equals(isActive, that.isActive) &&
            Objects.equals(updatedDate, that.updatedDate) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(trailerId, that.trailerId) &&
            Objects.equals(episodeId, that.episodeId) &&
            Objects.equals(categoryId, that.categoryId) &&
            Objects.equals(actorId, that.actorId) &&
            Objects.equals(sessionId, that.sessionId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            nameInVietNamese,
            description,
            time,
            iMDB,
            director,
            script,
            coutry,
            premiere,
            posterUrl,
            backgroundUrl,
            movieUrl,
            isActive,
            updatedDate,
            createdDate,
            trailerId,
            episodeId,
            categoryId,
            actorId,
            sessionId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MovieCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (nameInVietNamese != null ? "nameInVietNamese=" + nameInVietNamese + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (time != null ? "time=" + time + ", " : "") +
            (iMDB != null ? "iMDB=" + iMDB + ", " : "") +
            (director != null ? "director=" + director + ", " : "") +
            (script != null ? "script=" + script + ", " : "") +
            (coutry != null ? "coutry=" + coutry + ", " : "") +
            (premiere != null ? "premiere=" + premiere + ", " : "") +
            (posterUrl != null ? "posterUrl=" + posterUrl + ", " : "") +
            (backgroundUrl != null ? "backgroundUrl=" + backgroundUrl + ", " : "") +
            (movieUrl != null ? "movieUrl=" + movieUrl + ", " : "") +
            (isActive != null ? "isActive=" + isActive + ", " : "") +
            (updatedDate != null ? "updatedDate=" + updatedDate + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (trailerId != null ? "trailerId=" + trailerId + ", " : "") +
            (episodeId != null ? "episodeId=" + episodeId + ", " : "") +
            (categoryId != null ? "categoryId=" + categoryId + ", " : "") +
            (actorId != null ? "actorId=" + actorId + ", " : "") +
            (sessionId != null ? "sessionId=" + sessionId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
