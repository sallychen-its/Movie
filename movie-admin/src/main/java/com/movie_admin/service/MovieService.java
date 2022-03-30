package com.movie_admin.service;

import com.movie_admin.domain.Movie;
import com.movie_admin.repository.MovieRepository;
import com.movie_admin.service.dto.MovieDTO;
import com.movie_admin.service.mapper.MovieMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Movie}.
 */
@Service
@Transactional
public class MovieService {

    private final Logger log = LoggerFactory.getLogger(MovieService.class);

    private final MovieRepository movieRepository;

    private final MovieMapper movieMapper;

    public MovieService(MovieRepository movieRepository, MovieMapper movieMapper) {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
    }


    public MovieDTO save(MovieDTO movieDTO) {
        log.debug("Request to save Movie : {}", movieDTO);
        Movie movie = movieMapper.toEntity(movieDTO);
        movie = movieRepository.save(movie);
        return movieMapper.toDto(movie);
    }


    public Optional<MovieDTO> partialUpdate(MovieDTO movieDTO) {
        log.debug("Request to partially update Movie : {}", movieDTO);

        return movieRepository
            .findById(movieDTO.getId())
            .map(existingMovie -> {
                movieMapper.partialUpdate(existingMovie, movieDTO);

                return existingMovie;
            })
            .map(movieRepository::save)
            .map(movieMapper::toDto);
    }


    @Transactional(readOnly = true)
    public Page<MovieDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Movies");
        return movieRepository.findAll(pageable).map(movieMapper::toDto);
    }


    public Page<MovieDTO> findAllWithEagerRelationships(Pageable pageable) {
        return movieRepository.findAllWithEagerRelationships(pageable).map(movieMapper::toDto);
    }


    @Transactional(readOnly = true)
    public Optional<MovieDTO> findOne(Long id) {
        log.debug("Request to get Movie : {}", id);
        return movieRepository.findOneWithEagerRelationships(id).map(movieMapper::toDto);
    }


    public void delete(Long id) {
        log.debug("Request to delete Movie : {}", id);
        movieRepository.deleteById(id);
    }

    public Page<MovieDTO> findAllByCategoryId(Pageable pageable, Long categoryId) {
        return movieRepository.findAllByCategoryId(pageable, categoryId).map(movieMapper::toDto);
    }
}
