package com.movie_admin.service;

import com.movie_admin.domain.Actor;
import com.movie_admin.repository.ActorRepository;
import com.movie_admin.service.dto.ActorDTO;
import com.movie_admin.service.mapper.ActorMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class ActorService {

    private final Logger log = LoggerFactory.getLogger(ActorService.class);

    private final ActorRepository actorRepository;

    private final ActorMapper actorMapper;

    public ActorService(ActorRepository actorRepository, ActorMapper actorMapper) {
        this.actorRepository = actorRepository;
        this.actorMapper = actorMapper;
    }


    public ActorDTO save(ActorDTO actorDTO) {
        log.debug("Request to save Actor : {}", actorDTO);
        Actor actor = actorMapper.toEntity(actorDTO);
        actor = actorRepository.save(actor);
        return actorMapper.toDto(actor);
    }


    public Optional<ActorDTO> partialUpdate(ActorDTO actorDTO) {
        log.debug("Request to partially update Actor : {}", actorDTO);

        return actorRepository
            .findById(actorDTO.getId())
            .map(existingActor -> {
                actorMapper.partialUpdate(existingActor, actorDTO);

                return existingActor;
            })
            .map(actorRepository::save)
            .map(actorMapper::toDto);
    }


    @Transactional(readOnly = true)
    public List<ActorDTO> findAll() {
        log.debug("Request to get all Actors");
        return actorRepository.findAll().stream().map(actorMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }


    @Transactional(readOnly = true)
    public Optional<ActorDTO> findOne(Long id) {
        log.debug("Request to get Actor : {}", id);
        return actorRepository.findById(id).map(actorMapper::toDto);
    }


    public void delete(Long id) {
        log.debug("Request to delete Actor : {}", id);
        actorRepository.deleteById(id);
    }
}
