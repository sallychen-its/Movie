package com.movie_admin.service;

import com.movie_admin.domain.Trailer;
import com.movie_admin.repository.TrailerRepository;
import com.movie_admin.service.dto.TrailerDTO;
import com.movie_admin.service.mapper.TrailerMapper;
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
public class TrailerService {

    private final Logger log = LoggerFactory.getLogger(TrailerService.class);

    private final TrailerRepository trailerRepository;

    private final TrailerMapper trailerMapper;

    public TrailerService(TrailerRepository trailerRepository, TrailerMapper trailerMapper) {
        this.trailerRepository = trailerRepository;
        this.trailerMapper = trailerMapper;
    }


    public TrailerDTO save(TrailerDTO trailerDTO) {
        log.debug("Request to save Trailer : {}", trailerDTO);
        Trailer trailer = trailerMapper.toEntity(trailerDTO);
        trailer = trailerRepository.save(trailer);
        return trailerMapper.toDto(trailer);
    }


    public Optional<TrailerDTO> partialUpdate(TrailerDTO trailerDTO) {
        log.debug("Request to partially update Trailer : {}", trailerDTO);

        return trailerRepository
            .findById(trailerDTO.getId())
            .map(existingTrailer -> {
                trailerMapper.partialUpdate(existingTrailer, trailerDTO);

                return existingTrailer;
            })
            .map(trailerRepository::save)
            .map(trailerMapper::toDto);
    }


    @Transactional(readOnly = true)
    public List<TrailerDTO> findAll() {
        log.debug("Request to get all Trailers");
        return trailerRepository.findAll().stream().map(trailerMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }


    @Transactional(readOnly = true)
    public Optional<TrailerDTO> findOne(Long id) {
        log.debug("Request to get Trailer : {}", id);
        return trailerRepository.findById(id).map(trailerMapper::toDto);
    }


    public void delete(Long id) {
        log.debug("Request to delete Trailer : {}", id);
        trailerRepository.deleteById(id);
    }
}
