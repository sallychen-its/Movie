package com.movie_admin.web.rest;

import com.movie_admin.repository.EpisodeRepository;
import com.movie_admin.service.EpisodeService;
import com.movie_admin.service.dto.EpisodeDTO;
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
public class EpisodeResource {

    private final Logger log = LoggerFactory.getLogger(EpisodeResource.class);

    private static final String ENTITY_NAME = "episode";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EpisodeService episodeService;

    private final EpisodeRepository episodeRepository;

    public EpisodeResource(EpisodeService episodeService, EpisodeRepository episodeRepository) {
        this.episodeService = episodeService;
        this.episodeRepository = episodeRepository;
    }


    @PostMapping("/episodes")
    public ResponseEntity<EpisodeDTO> createEpisode(@Valid @RequestBody EpisodeDTO episodeDTO) throws URISyntaxException {
        log.debug("REST request to save Episode : {}", episodeDTO);
        if (episodeDTO.getId() != null) {
            throw new BadRequestAlertException("A new episode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EpisodeDTO result = episodeService.save(episodeDTO);
        return ResponseEntity
            .created(new URI("/api/episodes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    @PutMapping("/episodes/{id}")
    public ResponseEntity<EpisodeDTO> updateEpisode(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EpisodeDTO episodeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Episode : {}, {}", id, episodeDTO);
        if (episodeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, episodeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!episodeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EpisodeDTO result = episodeService.save(episodeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, episodeDTO.getId().toString()))
            .body(result);
    }


    @PatchMapping(value = "/episodes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EpisodeDTO> partialUpdateEpisode(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EpisodeDTO episodeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Episode partially : {}, {}", id, episodeDTO);
        if (episodeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, episodeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!episodeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EpisodeDTO> result = episodeService.partialUpdate(episodeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, episodeDTO.getId().toString())
        );
    }


    @GetMapping("/episodes")
    public List<EpisodeDTO> getAllEpisodes() {
        log.debug("REST request to get all Episodes");
        return episodeService.findAll();
    }


    @GetMapping("/episodes/{id}")
    public ResponseEntity<EpisodeDTO> getEpisode(@PathVariable Long id) {
        log.debug("REST request to get Episode : {}", id);
        Optional<EpisodeDTO> episodeDTO = episodeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(episodeDTO);
    }


    @DeleteMapping("/episodes/{id}")
    public ResponseEntity<Void> deleteEpisode(@PathVariable Long id) {
        log.debug("REST request to delete Episode : {}", id);
        episodeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
