package com.movie_admin.web.rest;

import com.movie_admin.repository.ActorRepository;
import com.movie_admin.service.ActorService;
import com.movie_admin.service.dto.ActorDTO;
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
public class ActorResource {

    private final Logger log = LoggerFactory.getLogger(ActorResource.class);

    private static final String ENTITY_NAME = "actor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ActorService actorService;

    private final ActorRepository actorRepository;

    public ActorResource(ActorService actorService, ActorRepository actorRepository) {
        this.actorService = actorService;
        this.actorRepository = actorRepository;
    }


    @PostMapping("/actors")
    public ResponseEntity<ActorDTO> createActor(@Valid @RequestBody ActorDTO actorDTO) throws URISyntaxException {
        log.debug("REST request to save Actor : {}", actorDTO);
        if (actorDTO.getId() != null) {
            throw new BadRequestAlertException("A new actor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ActorDTO result = actorService.save(actorDTO);
        return ResponseEntity
            .created(new URI("/api/actors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    @PutMapping("/actors/{id}")
    public ResponseEntity<ActorDTO> updateActor(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ActorDTO actorDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Actor : {}, {}", id, actorDTO);
        if (actorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, actorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!actorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ActorDTO result = actorService.save(actorDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, actorDTO.getId().toString()))
            .body(result);
    }


    @PatchMapping(value = "/actors/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ActorDTO> partialUpdateActor(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ActorDTO actorDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Actor partially : {}, {}", id, actorDTO);
        if (actorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, actorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!actorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ActorDTO> result = actorService.partialUpdate(actorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, actorDTO.getId().toString())
        );
    }


    @GetMapping("/actors")
    public List<ActorDTO> getAllActors() {
        log.debug("REST request to get all Actors");
        return actorService.findAll();
    }


    @GetMapping("/actors/{id}")
    public ResponseEntity<ActorDTO> getActor(@PathVariable Long id) {
        log.debug("REST request to get Actor : {}", id);
        Optional<ActorDTO> actorDTO = actorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(actorDTO);
    }


    @DeleteMapping("/actors/{id}")
    public ResponseEntity<Void> deleteActor(@PathVariable Long id) {
        log.debug("REST request to delete Actor : {}", id);
        actorService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
