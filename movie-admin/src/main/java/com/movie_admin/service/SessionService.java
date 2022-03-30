package com.movie_admin.service;

import com.movie_admin.domain.Session;
import com.movie_admin.repository.SessionRepository;
import com.movie_admin.service.dto.SessionDTO;
import com.movie_admin.service.mapper.SessionMapper;
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
public class SessionService {

    private final Logger log = LoggerFactory.getLogger(SessionService.class);

    private final SessionRepository sessionRepository;

    private final SessionMapper sessionMapper;

    public SessionService(SessionRepository sessionRepository, SessionMapper sessionMapper) {
        this.sessionRepository = sessionRepository;
        this.sessionMapper = sessionMapper;
    }


    public SessionDTO save(SessionDTO sessionDTO) {
        log.debug("Request to save Session : {}", sessionDTO);
        Session session = sessionMapper.toEntity(sessionDTO);
        session = sessionRepository.save(session);
        return sessionMapper.toDto(session);
    }


    public Optional<SessionDTO> partialUpdate(SessionDTO sessionDTO) {
        log.debug("Request to partially update Session : {}", sessionDTO);

        return sessionRepository
            .findById(sessionDTO.getId())
            .map(existingSession -> {
                sessionMapper.partialUpdate(existingSession, sessionDTO);

                return existingSession;
            })
            .map(sessionRepository::save)
            .map(sessionMapper::toDto);
    }


    @Transactional(readOnly = true)
    public List<SessionDTO> findAll() {
        log.debug("Request to get all Sessions");
        return sessionRepository.findAll().stream().map(sessionMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }


    @Transactional(readOnly = true)
    public Optional<SessionDTO> findOne(Long id) {
        log.debug("Request to get Session : {}", id);
        return sessionRepository.findById(id).map(sessionMapper::toDto);
    }


    public void delete(Long id) {
        log.debug("Request to delete Session : {}", id);
        sessionRepository.deleteById(id);
    }
}
