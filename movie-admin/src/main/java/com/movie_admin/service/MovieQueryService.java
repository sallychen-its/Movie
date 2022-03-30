package com.movie_admin.service;

import com.movie_admin.domain.*; // for static metamodels
import com.movie_admin.domain.Movie;
import com.movie_admin.repository.MovieRepository;
import com.movie_admin.service.criteria.MovieCriteria;
import com.movie_admin.service.dto.MovieDTO;
import com.movie_admin.service.mapper.MovieMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Movie} entities in the database.
 * The main input is a {@link MovieCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MovieDTO} or a {@link Page} of {@link MovieDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MovieQueryService extends QueryService<Movie> {

    private final Logger log = LoggerFactory.getLogger(MovieQueryService.class);

    private final MovieRepository movieRepository;

    private final MovieMapper movieMapper;

    public MovieQueryService(MovieRepository movieRepository, MovieMapper movieMapper) {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
    }

    /**
     * Return a {@link List} of {@link MovieDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MovieDTO> findByCriteria(MovieCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Movie> specification = createSpecification(criteria);
        return movieMapper.toDto(movieRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MovieDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MovieDTO> findByCriteria(MovieCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Movie> specification = createSpecification(criteria);
        return movieRepository.findAll(specification, page).map(movieMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MovieCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Movie> specification = createSpecification(criteria);
        return movieRepository.count(specification);
    }

    /**
     * Function to convert {@link MovieCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Movie> createSpecification(MovieCriteria criteria) {
        Specification<Movie> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Movie_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Movie_.name));
            }
            if (criteria.getNameInVietNamese() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNameInVietNamese(), Movie_.nameInVietNamese));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Movie_.description));
            }
            if (criteria.getTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTime(), Movie_.time));
            }
            if (criteria.getiMDB() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getiMDB(), Movie_.iMDB));
            }
            if (criteria.getDirector() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDirector(), Movie_.director));
            }
            if (criteria.getScript() != null) {
                specification = specification.and(buildStringSpecification(criteria.getScript(), Movie_.script));
            }
            if (criteria.getCoutry() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCoutry(), Movie_.coutry));
            }
            if (criteria.getPremiere() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPremiere(), Movie_.premiere));
            }
            if (criteria.getPosterUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPosterUrl(), Movie_.posterUrl));
            }
            if (criteria.getBackgroundUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBackgroundUrl(), Movie_.backgroundUrl));
            }
            if (criteria.getMovieUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMovieUrl(), Movie_.movieUrl));
            }
            if (criteria.getIsActive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsActive(), Movie_.isActive));
            }
            if (criteria.getUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedDate(), Movie_.updatedDate));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Movie_.createdDate));
            }
            if (criteria.getTrailerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getTrailerId(), root -> root.join(Movie_.trailers, JoinType.LEFT).get(Trailer_.id))
                    );
            }
            if (criteria.getEpisodeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getEpisodeId(), root -> root.join(Movie_.episodes, JoinType.LEFT).get(Episode_.id))
                    );
            }
            if (criteria.getCategoryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCategoryId(), root -> root.join(Movie_.categories, JoinType.LEFT).get(Category_.id))
                    );
            }
            if (criteria.getActorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getActorId(), root -> root.join(Movie_.actors, JoinType.LEFT).get(Actor_.id))
                    );
            }
            if (criteria.getSessionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getSessionId(), root -> root.join(Movie_.session, JoinType.LEFT).get(Session_.id))
                    );
            }
        }
        return specification;
    }
}
