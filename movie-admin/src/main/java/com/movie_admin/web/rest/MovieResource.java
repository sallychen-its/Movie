package com.movie_admin.web.rest;

import com.movie_admin.domain.Movie;
import com.movie_admin.repository.MovieRepository;
import com.movie_admin.service.MovieQueryService;
import com.movie_admin.service.MovieService;
import com.movie_admin.service.criteria.MovieCriteria;
import com.movie_admin.service.dto.MovieDTO;
import com.movie_admin.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;


@RestController
@RequestMapping("/api")
public class MovieResource {

    private final Logger log = LoggerFactory.getLogger(MovieResource.class);

    private static final String ENTITY_NAME = "movie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MovieService movieService;

    private final MovieRepository movieRepository;

    private final MovieQueryService movieQueryService;

    public MovieResource(MovieService movieService, MovieRepository movieRepository, MovieQueryService movieQueryService) {
        this.movieService = movieService;
        this.movieRepository = movieRepository;
        this.movieQueryService = movieQueryService;
    }


    @PostMapping("/movies")
    public ResponseEntity<MovieDTO> createMovie(@Valid @RequestBody MovieDTO movieDTO) throws URISyntaxException {
        log.debug("REST request to save Movie : {}", movieDTO);
        if (movieDTO.getId() != null) {
            throw new BadRequestAlertException("A new movie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MovieDTO result = movieService.save(movieDTO);
        return ResponseEntity
            .created(new URI("/api/movies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    @PutMapping("/movies/{id}")
    public ResponseEntity<MovieDTO> updateMovie(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MovieDTO movieDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Movie : {}, {}", id, movieDTO);
        if (movieDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, movieDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!movieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MovieDTO result = movieService.save(movieDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, movieDTO.getId().toString()))
            .body(result);
    }


    @PatchMapping(value = "/movies/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MovieDTO> partialUpdateMovie(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MovieDTO movieDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Movie partially : {}, {}", id, movieDTO);
        if (movieDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, movieDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!movieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MovieDTO> result = movieService.partialUpdate(movieDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, movieDTO.getId().toString())
        );
    }


    @GetMapping("/movies")
    public ResponseEntity<List<MovieDTO>> getAllMovies(MovieCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Movies by criteria: {}", criteria);
        Page<MovieDTO> page = movieQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/movie/{categoryId}")
    public ResponseEntity<List<MovieDTO>> getAllMovies(@PathVariable() Long categoryId, Pageable pageable) {
        Page<MovieDTO> page = movieService.findAllByCategoryId(pageable, )
    }


    @GetMapping("/movies/count")
    public ResponseEntity<Long> countMovies(MovieCriteria criteria) {
        log.debug("REST request to count Movies by criteria: {}", criteria);
        return ResponseEntity.ok().body(movieQueryService.countByCriteria(criteria));
    }


    @GetMapping("/movies/{id}")
    public ResponseEntity<MovieDTO> getMovie(@PathVariable Long id) {
        log.debug("REST request to get Movie : {}", id);
        Optional<MovieDTO> movieDTO = movieService.findOne(id);
        return ResponseUtil.wrapOrNotFound(movieDTO);
    }


    @DeleteMapping("/movies/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        log.debug("REST request to delete Movie : {}", id);
        movieService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
