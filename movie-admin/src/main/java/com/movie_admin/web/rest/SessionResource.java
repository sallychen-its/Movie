package com.movie_admin.web.rest;

import com.movie_admin.repository.SessionRepository;
import com.movie_admin.service.SessionService;
import com.movie_admin.service.dto.SessionDTO;
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
public class SessionResource {

    private final Logger log = LoggerFactory.getLogger(SessionResource.class);

    private static final String ENTITY_NAME = "session";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SessionService sessionService;

    private final SessionRepository sessionRepository;

    public SessionResource(SessionService sessionService, SessionRepository sessionRepository) {
        this.sessionService = sessionService;
        this.sessionRepository = sessionRepository;
    }


    @PostMapping("/sessions")
    public ResponseEntity<SessionDTO> createSession(@Valid @RequestBody SessionDTO sessionDTO) throws URISyntaxException {
        log.debug("REST request to save Session : {}", sessionDTO);
        if (sessionDTO.getId() != null) {
            throw new BadRequestAlertException("A new session cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SessionDTO result = sessionService.save(sessionDTO);
        return ResponseEntity
            .created(new URI("/api/sessions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    @PutMapping("/sessions/{id}")
    public ResponseEntity<SessionDTO> updateSession(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SessionDTO sessionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Session : {}, {}", id, sessionDTO);
        if (sessionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sessionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sessionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SessionDTO result = sessionService.save(sessionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sessionDTO.getId().toString()))
            .body(result);
    }


    @PatchMapping(value = "/sessions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SessionDTO> partialUpdateSession(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SessionDTO sessionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Session partially : {}, {}", id, sessionDTO);
        if (sessionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sessionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sessionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SessionDTO> result = sessionService.partialUpdate(sessionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sessionDTO.getId().toString())
        );
    }


    @GetMapping("/sessions")
    public List<SessionDTO> getAllSessions() {
        log.debug("REST request to get all Sessions");
        return sessionService.findAll();
    }


    @GetMapping("/sessions/{id}")
    public ResponseEntity<SessionDTO> getSession(@PathVariable Long id) {
        log.debug("REST request to get Session : {}", id);
        Optional<SessionDTO> sessionDTO = sessionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sessionDTO);
    }


    @DeleteMapping("/sessions/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable Long id) {
        log.debug("REST request to delete Session : {}", id);
        sessionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
