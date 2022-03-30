package com.movie_admin.web.rest;

import com.movie_admin.repository.TrailerRepository;
import com.movie_admin.service.TrailerService;
import com.movie_admin.service.dto.TrailerDTO;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;


@RestController
@RequestMapping("/api")
public class TrailerResource {

    private final Logger log = LoggerFactory.getLogger(TrailerResource.class);

    private static final String ENTITY_NAME = "trailer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TrailerService trailerService;

    private final TrailerRepository trailerRepository;

    public TrailerResource(TrailerService trailerService, TrailerRepository trailerRepository) {
        this.trailerService = trailerService;
        this.trailerRepository = trailerRepository;
    }


    @PostMapping("/trailers")
    public ResponseEntity<TrailerDTO> createTrailer(@Valid @RequestBody TrailerDTO trailerDTO) throws URISyntaxException {
        log.debug("REST request to save Trailer : {}", trailerDTO);
        if (trailerDTO.getId() != null) {
            throw new BadRequestAlertException("A new trailer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TrailerDTO result = trailerService.save(trailerDTO);
        return ResponseEntity
            .created(new URI("/api/trailers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    @PutMapping("/trailers/{id}")
    public ResponseEntity<TrailerDTO> updateTrailer(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TrailerDTO trailerDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Trailer : {}, {}", id, trailerDTO);
        if (trailerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trailerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trailerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TrailerDTO result = trailerService.save(trailerDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, trailerDTO.getId().toString()))
            .body(result);
    }


    @PatchMapping(value = "/trailers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TrailerDTO> partialUpdateTrailer(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TrailerDTO trailerDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Trailer partially : {}, {}", id, trailerDTO);
        if (trailerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trailerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trailerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TrailerDTO> result = trailerService.partialUpdate(trailerDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, trailerDTO.getId().toString())
        );
    }


    @GetMapping("/trailers")
    public List<TrailerDTO> getAllTrailers() {
        log.debug("REST request to get all Trailers");
        return trailerService.findAll();
    }


    @GetMapping("/trailers/{id}")
    public ResponseEntity<TrailerDTO> getTrailer(@PathVariable Long id) {
        log.debug("REST request to get Trailer : {}", id);
        Optional<TrailerDTO> trailerDTO = trailerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(trailerDTO);
    }


    @DeleteMapping("/trailers/{id}")
    public ResponseEntity<Void> deleteTrailer(@PathVariable Long id) {
        log.debug("REST request to delete Trailer : {}", id);
        trailerService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
