package com.movie_admin.service;

import com.movie_admin.domain.Episode;
import com.movie_admin.repository.EpisodeRepository;
import com.movie_admin.service.dto.EpisodeDTO;
import com.movie_admin.service.mapper.EpisodeMapper;
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
public class EpisodeService {

    private final Logger log = LoggerFactory.getLogger(EpisodeService.class);

    private final EpisodeRepository episodeRepository;

    private final EpisodeMapper episodeMapper;

    public EpisodeService(EpisodeRepository episodeRepository, EpisodeMapper episodeMapper) {
        this.episodeRepository = episodeRepository;
        this.episodeMapper = episodeMapper;
    }


    public EpisodeDTO save(EpisodeDTO episodeDTO) {
        log.debug("Request to save Episode : {}", episodeDTO);
        Episode episode = episodeMapper.toEntity(episodeDTO);
        episode = episodeRepository.save(episode);
        return episodeMapper.toDto(episode);
    }


    public Optional<EpisodeDTO> partialUpdate(EpisodeDTO episodeDTO) {
        log.debug("Request to partially update Episode : {}", episodeDTO);

        return episodeRepository
            .findById(episodeDTO.getId())
            .map(existingEpisode -> {
                episodeMapper.partialUpdate(existingEpisode, episodeDTO);

                return existingEpisode;
            })
            .map(episodeRepository::save)
            .map(episodeMapper::toDto);
    }


    @Transactional(readOnly = true)
    public List<EpisodeDTO> findAll() {
        log.debug("Request to get all Episodes");
        return episodeRepository.findAll().stream().map(episodeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }


    @Transactional(readOnly = true)
    public Optional<EpisodeDTO> findOne(Long id) {
        log.debug("Request to get Episode : {}", id);
        return episodeRepository.findById(id).map(episodeMapper::toDto);
    }


    public void delete(Long id) {
        log.debug("Request to delete Episode : {}", id);
        episodeRepository.deleteById(id);
    }
}
