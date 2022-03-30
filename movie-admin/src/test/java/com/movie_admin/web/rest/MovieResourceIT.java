package com.movie_admin.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.movie_admin.IntegrationTest;
import com.movie_admin.domain.Actor;
import com.movie_admin.domain.Category;
import com.movie_admin.domain.Episode;
import com.movie_admin.domain.Movie;
import com.movie_admin.domain.Session;
import com.movie_admin.domain.Trailer;
import com.movie_admin.repository.MovieRepository;
import com.movie_admin.service.MovieService;
import com.movie_admin.service.criteria.MovieCriteria;
import com.movie_admin.service.dto.MovieDTO;
import com.movie_admin.service.mapper.MovieMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class MovieResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_IN_VIET_NAMESE = "AAAAAAAAAA";
    private static final String UPDATED_NAME_IN_VIET_NAMESE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_TIME = 1L;
    private static final Long UPDATED_TIME = 2L;
    private static final Long SMALLER_TIME = 1L - 1L;

    private static final Float DEFAULT_I_MDB = 1F;
    private static final Float UPDATED_I_MDB = 2F;
    private static final Float SMALLER_I_MDB = 1F - 1F;

    private static final String DEFAULT_DIRECTOR = "AAAAAAAAAA";
    private static final String UPDATED_DIRECTOR = "BBBBBBBBBB";

    private static final String DEFAULT_SCRIPT = "AAAAAAAAAA";
    private static final String UPDATED_SCRIPT = "BBBBBBBBBB";

    private static final String DEFAULT_COUTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUTRY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_PREMIERE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PREMIERE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_PREMIERE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_POSTER_URL = "AAAAAAAAAA";
    private static final String UPDATED_POSTER_URL = "BBBBBBBBBB";

    private static final String DEFAULT_BACKGROUND_URL = "AAAAAAAAAA";
    private static final String UPDATED_BACKGROUND_URL = "BBBBBBBBBB";

    private static final String DEFAULT_MOVIE_URL = "AAAAAAAAAA";
    private static final String UPDATED_MOVIE_URL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final LocalDate DEFAULT_UPDATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_UPDATED_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CREATED_DATE = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/movies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MovieRepository movieRepository;

    @Mock
    private MovieRepository movieRepositoryMock;

    @Autowired
    private MovieMapper movieMapper;

    @Mock
    private MovieService movieServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMovieMockMvc;

    private Movie movie;


    public static Movie createEntity(EntityManager em) {
        Movie movie = new Movie()
            .name(DEFAULT_NAME)
            .nameInVietNamese(DEFAULT_NAME_IN_VIET_NAMESE)
            .description(DEFAULT_DESCRIPTION)
            .time(DEFAULT_TIME)
            .iMDB(DEFAULT_I_MDB)
            .director(DEFAULT_DIRECTOR)
            .script(DEFAULT_SCRIPT)
            .coutry(DEFAULT_COUTRY)
            .premiere(DEFAULT_PREMIERE)
            .posterUrl(DEFAULT_POSTER_URL)
            .backgroundUrl(DEFAULT_BACKGROUND_URL)
            .movieUrl(DEFAULT_MOVIE_URL)
            .isActive(DEFAULT_IS_ACTIVE)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .createdDate(DEFAULT_CREATED_DATE);
        return movie;
    }


    public static Movie createUpdatedEntity(EntityManager em) {
        Movie movie = new Movie()
            .name(UPDATED_NAME)
            .nameInVietNamese(UPDATED_NAME_IN_VIET_NAMESE)
            .description(UPDATED_DESCRIPTION)
            .time(UPDATED_TIME)
            .iMDB(UPDATED_I_MDB)
            .director(UPDATED_DIRECTOR)
            .script(UPDATED_SCRIPT)
            .coutry(UPDATED_COUTRY)
            .premiere(UPDATED_PREMIERE)
            .posterUrl(UPDATED_POSTER_URL)
            .backgroundUrl(UPDATED_BACKGROUND_URL)
            .movieUrl(UPDATED_MOVIE_URL)
            .isActive(UPDATED_IS_ACTIVE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .createdDate(UPDATED_CREATED_DATE);
        return movie;
    }

    @BeforeEach
    public void initTest() {
        movie = createEntity(em);
    }

    @Test
    @Transactional
    void createMovie() throws Exception {
        int databaseSizeBeforeCreate = movieRepository.findAll().size();
        // Create the Movie
        MovieDTO movieDTO = movieMapper.toDto(movie);
        restMovieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(movieDTO)))
            .andExpect(status().isCreated());

        // Validate the Movie in the database
        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeCreate + 1);
        Movie testMovie = movieList.get(movieList.size() - 1);
        assertThat(testMovie.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMovie.getNameInVietNamese()).isEqualTo(DEFAULT_NAME_IN_VIET_NAMESE);
        assertThat(testMovie.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMovie.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testMovie.getiMDB()).isEqualTo(DEFAULT_I_MDB);
        assertThat(testMovie.getDirector()).isEqualTo(DEFAULT_DIRECTOR);
        assertThat(testMovie.getScript()).isEqualTo(DEFAULT_SCRIPT);
        assertThat(testMovie.getCoutry()).isEqualTo(DEFAULT_COUTRY);
        assertThat(testMovie.getPremiere()).isEqualTo(DEFAULT_PREMIERE);
        assertThat(testMovie.getPosterUrl()).isEqualTo(DEFAULT_POSTER_URL);
        assertThat(testMovie.getBackgroundUrl()).isEqualTo(DEFAULT_BACKGROUND_URL);
        assertThat(testMovie.getMovieUrl()).isEqualTo(DEFAULT_MOVIE_URL);
        assertThat(testMovie.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testMovie.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testMovie.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    void createMovieWithExistingId() throws Exception {
        // Create the Movie with an existing ID
        movie.setId(1L);
        MovieDTO movieDTO = movieMapper.toDto(movie);

        int databaseSizeBeforeCreate = movieRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMovieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(movieDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Movie in the database
        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = movieRepository.findAll().size();
        // set the field null
        movie.setName(null);

        // Create the Movie, which fails.
        MovieDTO movieDTO = movieMapper.toDto(movie);

        restMovieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(movieDTO)))
            .andExpect(status().isBadRequest());

        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameInVietNameseIsRequired() throws Exception {
        int databaseSizeBeforeTest = movieRepository.findAll().size();
        // set the field null
        movie.setNameInVietNamese(null);

        // Create the Movie, which fails.
        MovieDTO movieDTO = movieMapper.toDto(movie);

        restMovieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(movieDTO)))
            .andExpect(status().isBadRequest());

        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPremiereIsRequired() throws Exception {
        int databaseSizeBeforeTest = movieRepository.findAll().size();
        // set the field null
        movie.setPremiere(null);

        // Create the Movie, which fails.
        MovieDTO movieDTO = movieMapper.toDto(movie);

        restMovieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(movieDTO)))
            .andExpect(status().isBadRequest());

        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPosterUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = movieRepository.findAll().size();
        // set the field null
        movie.setPosterUrl(null);

        // Create the Movie, which fails.
        MovieDTO movieDTO = movieMapper.toDto(movie);

        restMovieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(movieDTO)))
            .andExpect(status().isBadRequest());

        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMovieUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = movieRepository.findAll().size();
        // set the field null
        movie.setMovieUrl(null);

        // Create the Movie, which fails.
        MovieDTO movieDTO = movieMapper.toDto(movie);

        restMovieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(movieDTO)))
            .andExpect(status().isBadRequest());

        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = movieRepository.findAll().size();
        // set the field null
        movie.setIsActive(null);

        // Create the Movie, which fails.
        MovieDTO movieDTO = movieMapper.toDto(movie);

        restMovieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(movieDTO)))
            .andExpect(status().isBadRequest());

        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = movieRepository.findAll().size();
        // set the field null
        movie.setUpdatedDate(null);

        // Create the Movie, which fails.
        MovieDTO movieDTO = movieMapper.toDto(movie);

        restMovieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(movieDTO)))
            .andExpect(status().isBadRequest());

        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = movieRepository.findAll().size();
        // set the field null
        movie.setCreatedDate(null);

        // Create the Movie, which fails.
        MovieDTO movieDTO = movieMapper.toDto(movie);

        restMovieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(movieDTO)))
            .andExpect(status().isBadRequest());

        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMovies() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList
        restMovieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(movie.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].nameInVietNamese").value(hasItem(DEFAULT_NAME_IN_VIET_NAMESE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME.intValue())))
            .andExpect(jsonPath("$.[*].iMDB").value(hasItem(DEFAULT_I_MDB.doubleValue())))
            .andExpect(jsonPath("$.[*].director").value(hasItem(DEFAULT_DIRECTOR)))
            .andExpect(jsonPath("$.[*].script").value(hasItem(DEFAULT_SCRIPT)))
            .andExpect(jsonPath("$.[*].coutry").value(hasItem(DEFAULT_COUTRY)))
            .andExpect(jsonPath("$.[*].premiere").value(hasItem(DEFAULT_PREMIERE.toString())))
            .andExpect(jsonPath("$.[*].posterUrl").value(hasItem(DEFAULT_POSTER_URL)))
            .andExpect(jsonPath("$.[*].backgroundUrl").value(hasItem(DEFAULT_BACKGROUND_URL)))
            .andExpect(jsonPath("$.[*].movieUrl").value(hasItem(DEFAULT_MOVIE_URL)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMoviesWithEagerRelationshipsIsEnabled() throws Exception {
        when(movieServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMovieMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(movieServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMoviesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(movieServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMovieMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(movieServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getMovie() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get the movie
        restMovieMockMvc
            .perform(get(ENTITY_API_URL_ID, movie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(movie.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.nameInVietNamese").value(DEFAULT_NAME_IN_VIET_NAMESE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME.intValue()))
            .andExpect(jsonPath("$.iMDB").value(DEFAULT_I_MDB.doubleValue()))
            .andExpect(jsonPath("$.director").value(DEFAULT_DIRECTOR))
            .andExpect(jsonPath("$.script").value(DEFAULT_SCRIPT))
            .andExpect(jsonPath("$.coutry").value(DEFAULT_COUTRY))
            .andExpect(jsonPath("$.premiere").value(DEFAULT_PREMIERE.toString()))
            .andExpect(jsonPath("$.posterUrl").value(DEFAULT_POSTER_URL))
            .andExpect(jsonPath("$.backgroundUrl").value(DEFAULT_BACKGROUND_URL))
            .andExpect(jsonPath("$.movieUrl").value(DEFAULT_MOVIE_URL))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getMoviesByIdFiltering() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        Long id = movie.getId();

        defaultMovieShouldBeFound("id.equals=" + id);
        defaultMovieShouldNotBeFound("id.notEquals=" + id);

        defaultMovieShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMovieShouldNotBeFound("id.greaterThan=" + id);

        defaultMovieShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMovieShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMoviesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where name equals to DEFAULT_NAME
        defaultMovieShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the movieList where name equals to UPDATED_NAME
        defaultMovieShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllMoviesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where name not equals to DEFAULT_NAME
        defaultMovieShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the movieList where name not equals to UPDATED_NAME
        defaultMovieShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllMoviesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where name in DEFAULT_NAME or UPDATED_NAME
        defaultMovieShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the movieList where name equals to UPDATED_NAME
        defaultMovieShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllMoviesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where name is not null
        defaultMovieShouldBeFound("name.specified=true");

        // Get all the movieList where name is null
        defaultMovieShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllMoviesByNameContainsSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where name contains DEFAULT_NAME
        defaultMovieShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the movieList where name contains UPDATED_NAME
        defaultMovieShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllMoviesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where name does not contain DEFAULT_NAME
        defaultMovieShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the movieList where name does not contain UPDATED_NAME
        defaultMovieShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllMoviesByNameInVietNameseIsEqualToSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where nameInVietNamese equals to DEFAULT_NAME_IN_VIET_NAMESE
        defaultMovieShouldBeFound("nameInVietNamese.equals=" + DEFAULT_NAME_IN_VIET_NAMESE);

        // Get all the movieList where nameInVietNamese equals to UPDATED_NAME_IN_VIET_NAMESE
        defaultMovieShouldNotBeFound("nameInVietNamese.equals=" + UPDATED_NAME_IN_VIET_NAMESE);
    }

    @Test
    @Transactional
    void getAllMoviesByNameInVietNameseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where nameInVietNamese not equals to DEFAULT_NAME_IN_VIET_NAMESE
        defaultMovieShouldNotBeFound("nameInVietNamese.notEquals=" + DEFAULT_NAME_IN_VIET_NAMESE);

        // Get all the movieList where nameInVietNamese not equals to UPDATED_NAME_IN_VIET_NAMESE
        defaultMovieShouldBeFound("nameInVietNamese.notEquals=" + UPDATED_NAME_IN_VIET_NAMESE);
    }

    @Test
    @Transactional
    void getAllMoviesByNameInVietNameseIsInShouldWork() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where nameInVietNamese in DEFAULT_NAME_IN_VIET_NAMESE or UPDATED_NAME_IN_VIET_NAMESE
        defaultMovieShouldBeFound("nameInVietNamese.in=" + DEFAULT_NAME_IN_VIET_NAMESE + "," + UPDATED_NAME_IN_VIET_NAMESE);

        // Get all the movieList where nameInVietNamese equals to UPDATED_NAME_IN_VIET_NAMESE
        defaultMovieShouldNotBeFound("nameInVietNamese.in=" + UPDATED_NAME_IN_VIET_NAMESE);
    }

    @Test
    @Transactional
    void getAllMoviesByNameInVietNameseIsNullOrNotNull() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where nameInVietNamese is not null
        defaultMovieShouldBeFound("nameInVietNamese.specified=true");

        // Get all the movieList where nameInVietNamese is null
        defaultMovieShouldNotBeFound("nameInVietNamese.specified=false");
    }

    @Test
    @Transactional
    void getAllMoviesByNameInVietNameseContainsSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where nameInVietNamese contains DEFAULT_NAME_IN_VIET_NAMESE
        defaultMovieShouldBeFound("nameInVietNamese.contains=" + DEFAULT_NAME_IN_VIET_NAMESE);

        // Get all the movieList where nameInVietNamese contains UPDATED_NAME_IN_VIET_NAMESE
        defaultMovieShouldNotBeFound("nameInVietNamese.contains=" + UPDATED_NAME_IN_VIET_NAMESE);
    }

    @Test
    @Transactional
    void getAllMoviesByNameInVietNameseNotContainsSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where nameInVietNamese does not contain DEFAULT_NAME_IN_VIET_NAMESE
        defaultMovieShouldNotBeFound("nameInVietNamese.doesNotContain=" + DEFAULT_NAME_IN_VIET_NAMESE);

        // Get all the movieList where nameInVietNamese does not contain UPDATED_NAME_IN_VIET_NAMESE
        defaultMovieShouldBeFound("nameInVietNamese.doesNotContain=" + UPDATED_NAME_IN_VIET_NAMESE);
    }

    @Test
    @Transactional
    void getAllMoviesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where description equals to DEFAULT_DESCRIPTION
        defaultMovieShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the movieList where description equals to UPDATED_DESCRIPTION
        defaultMovieShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllMoviesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where description not equals to DEFAULT_DESCRIPTION
        defaultMovieShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the movieList where description not equals to UPDATED_DESCRIPTION
        defaultMovieShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllMoviesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultMovieShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the movieList where description equals to UPDATED_DESCRIPTION
        defaultMovieShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllMoviesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where description is not null
        defaultMovieShouldBeFound("description.specified=true");

        // Get all the movieList where description is null
        defaultMovieShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllMoviesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where description contains DEFAULT_DESCRIPTION
        defaultMovieShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the movieList where description contains UPDATED_DESCRIPTION
        defaultMovieShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllMoviesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where description does not contain DEFAULT_DESCRIPTION
        defaultMovieShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the movieList where description does not contain UPDATED_DESCRIPTION
        defaultMovieShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllMoviesByTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where time equals to DEFAULT_TIME
        defaultMovieShouldBeFound("time.equals=" + DEFAULT_TIME);

        // Get all the movieList where time equals to UPDATED_TIME
        defaultMovieShouldNotBeFound("time.equals=" + UPDATED_TIME);
    }

    @Test
    @Transactional
    void getAllMoviesByTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where time not equals to DEFAULT_TIME
        defaultMovieShouldNotBeFound("time.notEquals=" + DEFAULT_TIME);

        // Get all the movieList where time not equals to UPDATED_TIME
        defaultMovieShouldBeFound("time.notEquals=" + UPDATED_TIME);
    }

    @Test
    @Transactional
    void getAllMoviesByTimeIsInShouldWork() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where time in DEFAULT_TIME or UPDATED_TIME
        defaultMovieShouldBeFound("time.in=" + DEFAULT_TIME + "," + UPDATED_TIME);

        // Get all the movieList where time equals to UPDATED_TIME
        defaultMovieShouldNotBeFound("time.in=" + UPDATED_TIME);
    }

    @Test
    @Transactional
    void getAllMoviesByTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where time is not null
        defaultMovieShouldBeFound("time.specified=true");

        // Get all the movieList where time is null
        defaultMovieShouldNotBeFound("time.specified=false");
    }

    @Test
    @Transactional
    void getAllMoviesByTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where time is greater than or equal to DEFAULT_TIME
        defaultMovieShouldBeFound("time.greaterThanOrEqual=" + DEFAULT_TIME);

        // Get all the movieList where time is greater than or equal to UPDATED_TIME
        defaultMovieShouldNotBeFound("time.greaterThanOrEqual=" + UPDATED_TIME);
    }

    @Test
    @Transactional
    void getAllMoviesByTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where time is less than or equal to DEFAULT_TIME
        defaultMovieShouldBeFound("time.lessThanOrEqual=" + DEFAULT_TIME);

        // Get all the movieList where time is less than or equal to SMALLER_TIME
        defaultMovieShouldNotBeFound("time.lessThanOrEqual=" + SMALLER_TIME);
    }

    @Test
    @Transactional
    void getAllMoviesByTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where time is less than DEFAULT_TIME
        defaultMovieShouldNotBeFound("time.lessThan=" + DEFAULT_TIME);

        // Get all the movieList where time is less than UPDATED_TIME
        defaultMovieShouldBeFound("time.lessThan=" + UPDATED_TIME);
    }

    @Test
    @Transactional
    void getAllMoviesByTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where time is greater than DEFAULT_TIME
        defaultMovieShouldNotBeFound("time.greaterThan=" + DEFAULT_TIME);

        // Get all the movieList where time is greater than SMALLER_TIME
        defaultMovieShouldBeFound("time.greaterThan=" + SMALLER_TIME);
    }

    @Test
    @Transactional
    void getAllMoviesByiMDBIsEqualToSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where iMDB equals to DEFAULT_I_MDB
        defaultMovieShouldBeFound("iMDB.equals=" + DEFAULT_I_MDB);

        // Get all the movieList where iMDB equals to UPDATED_I_MDB
        defaultMovieShouldNotBeFound("iMDB.equals=" + UPDATED_I_MDB);
    }

    @Test
    @Transactional
    void getAllMoviesByiMDBIsNotEqualToSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where iMDB not equals to DEFAULT_I_MDB
        defaultMovieShouldNotBeFound("iMDB.notEquals=" + DEFAULT_I_MDB);

        // Get all the movieList where iMDB not equals to UPDATED_I_MDB
        defaultMovieShouldBeFound("iMDB.notEquals=" + UPDATED_I_MDB);
    }

    @Test
    @Transactional
    void getAllMoviesByiMDBIsInShouldWork() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where iMDB in DEFAULT_I_MDB or UPDATED_I_MDB
        defaultMovieShouldBeFound("iMDB.in=" + DEFAULT_I_MDB + "," + UPDATED_I_MDB);

        // Get all the movieList where iMDB equals to UPDATED_I_MDB
        defaultMovieShouldNotBeFound("iMDB.in=" + UPDATED_I_MDB);
    }

    @Test
    @Transactional
    void getAllMoviesByiMDBIsNullOrNotNull() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where iMDB is not null
        defaultMovieShouldBeFound("iMDB.specified=true");

        // Get all the movieList where iMDB is null
        defaultMovieShouldNotBeFound("iMDB.specified=false");
    }

    @Test
    @Transactional
    void getAllMoviesByiMDBIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where iMDB is greater than or equal to DEFAULT_I_MDB
        defaultMovieShouldBeFound("iMDB.greaterThanOrEqual=" + DEFAULT_I_MDB);

        // Get all the movieList where iMDB is greater than or equal to UPDATED_I_MDB
        defaultMovieShouldNotBeFound("iMDB.greaterThanOrEqual=" + UPDATED_I_MDB);
    }

    @Test
    @Transactional
    void getAllMoviesByiMDBIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where iMDB is less than or equal to DEFAULT_I_MDB
        defaultMovieShouldBeFound("iMDB.lessThanOrEqual=" + DEFAULT_I_MDB);

        // Get all the movieList where iMDB is less than or equal to SMALLER_I_MDB
        defaultMovieShouldNotBeFound("iMDB.lessThanOrEqual=" + SMALLER_I_MDB);
    }

    @Test
    @Transactional
    void getAllMoviesByiMDBIsLessThanSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where iMDB is less than DEFAULT_I_MDB
        defaultMovieShouldNotBeFound("iMDB.lessThan=" + DEFAULT_I_MDB);

        // Get all the movieList where iMDB is less than UPDATED_I_MDB
        defaultMovieShouldBeFound("iMDB.lessThan=" + UPDATED_I_MDB);
    }

    @Test
    @Transactional
    void getAllMoviesByiMDBIsGreaterThanSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where iMDB is greater than DEFAULT_I_MDB
        defaultMovieShouldNotBeFound("iMDB.greaterThan=" + DEFAULT_I_MDB);

        // Get all the movieList where iMDB is greater than SMALLER_I_MDB
        defaultMovieShouldBeFound("iMDB.greaterThan=" + SMALLER_I_MDB);
    }

    @Test
    @Transactional
    void getAllMoviesByDirectorIsEqualToSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where director equals to DEFAULT_DIRECTOR
        defaultMovieShouldBeFound("director.equals=" + DEFAULT_DIRECTOR);

        // Get all the movieList where director equals to UPDATED_DIRECTOR
        defaultMovieShouldNotBeFound("director.equals=" + UPDATED_DIRECTOR);
    }

    @Test
    @Transactional
    void getAllMoviesByDirectorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where director not equals to DEFAULT_DIRECTOR
        defaultMovieShouldNotBeFound("director.notEquals=" + DEFAULT_DIRECTOR);

        // Get all the movieList where director not equals to UPDATED_DIRECTOR
        defaultMovieShouldBeFound("director.notEquals=" + UPDATED_DIRECTOR);
    }

    @Test
    @Transactional
    void getAllMoviesByDirectorIsInShouldWork() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where director in DEFAULT_DIRECTOR or UPDATED_DIRECTOR
        defaultMovieShouldBeFound("director.in=" + DEFAULT_DIRECTOR + "," + UPDATED_DIRECTOR);

        // Get all the movieList where director equals to UPDATED_DIRECTOR
        defaultMovieShouldNotBeFound("director.in=" + UPDATED_DIRECTOR);
    }

    @Test
    @Transactional
    void getAllMoviesByDirectorIsNullOrNotNull() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where director is not null
        defaultMovieShouldBeFound("director.specified=true");

        // Get all the movieList where director is null
        defaultMovieShouldNotBeFound("director.specified=false");
    }

    @Test
    @Transactional
    void getAllMoviesByDirectorContainsSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where director contains DEFAULT_DIRECTOR
        defaultMovieShouldBeFound("director.contains=" + DEFAULT_DIRECTOR);

        // Get all the movieList where director contains UPDATED_DIRECTOR
        defaultMovieShouldNotBeFound("director.contains=" + UPDATED_DIRECTOR);
    }

    @Test
    @Transactional
    void getAllMoviesByDirectorNotContainsSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where director does not contain DEFAULT_DIRECTOR
        defaultMovieShouldNotBeFound("director.doesNotContain=" + DEFAULT_DIRECTOR);

        // Get all the movieList where director does not contain UPDATED_DIRECTOR
        defaultMovieShouldBeFound("director.doesNotContain=" + UPDATED_DIRECTOR);
    }

    @Test
    @Transactional
    void getAllMoviesByScriptIsEqualToSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where script equals to DEFAULT_SCRIPT
        defaultMovieShouldBeFound("script.equals=" + DEFAULT_SCRIPT);

        // Get all the movieList where script equals to UPDATED_SCRIPT
        defaultMovieShouldNotBeFound("script.equals=" + UPDATED_SCRIPT);
    }

    @Test
    @Transactional
    void getAllMoviesByScriptIsNotEqualToSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where script not equals to DEFAULT_SCRIPT
        defaultMovieShouldNotBeFound("script.notEquals=" + DEFAULT_SCRIPT);

        // Get all the movieList where script not equals to UPDATED_SCRIPT
        defaultMovieShouldBeFound("script.notEquals=" + UPDATED_SCRIPT);
    }

    @Test
    @Transactional
    void getAllMoviesByScriptIsInShouldWork() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where script in DEFAULT_SCRIPT or UPDATED_SCRIPT
        defaultMovieShouldBeFound("script.in=" + DEFAULT_SCRIPT + "," + UPDATED_SCRIPT);

        // Get all the movieList where script equals to UPDATED_SCRIPT
        defaultMovieShouldNotBeFound("script.in=" + UPDATED_SCRIPT);
    }

    @Test
    @Transactional
    void getAllMoviesByScriptIsNullOrNotNull() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where script is not null
        defaultMovieShouldBeFound("script.specified=true");

        // Get all the movieList where script is null
        defaultMovieShouldNotBeFound("script.specified=false");
    }

    @Test
    @Transactional
    void getAllMoviesByScriptContainsSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where script contains DEFAULT_SCRIPT
        defaultMovieShouldBeFound("script.contains=" + DEFAULT_SCRIPT);

        // Get all the movieList where script contains UPDATED_SCRIPT
        defaultMovieShouldNotBeFound("script.contains=" + UPDATED_SCRIPT);
    }

    @Test
    @Transactional
    void getAllMoviesByScriptNotContainsSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where script does not contain DEFAULT_SCRIPT
        defaultMovieShouldNotBeFound("script.doesNotContain=" + DEFAULT_SCRIPT);

        // Get all the movieList where script does not contain UPDATED_SCRIPT
        defaultMovieShouldBeFound("script.doesNotContain=" + UPDATED_SCRIPT);
    }

    @Test
    @Transactional
    void getAllMoviesByCoutryIsEqualToSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where coutry equals to DEFAULT_COUTRY
        defaultMovieShouldBeFound("coutry.equals=" + DEFAULT_COUTRY);

        // Get all the movieList where coutry equals to UPDATED_COUTRY
        defaultMovieShouldNotBeFound("coutry.equals=" + UPDATED_COUTRY);
    }

    @Test
    @Transactional
    void getAllMoviesByCoutryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where coutry not equals to DEFAULT_COUTRY
        defaultMovieShouldNotBeFound("coutry.notEquals=" + DEFAULT_COUTRY);

        // Get all the movieList where coutry not equals to UPDATED_COUTRY
        defaultMovieShouldBeFound("coutry.notEquals=" + UPDATED_COUTRY);
    }

    @Test
    @Transactional
    void getAllMoviesByCoutryIsInShouldWork() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where coutry in DEFAULT_COUTRY or UPDATED_COUTRY
        defaultMovieShouldBeFound("coutry.in=" + DEFAULT_COUTRY + "," + UPDATED_COUTRY);

        // Get all the movieList where coutry equals to UPDATED_COUTRY
        defaultMovieShouldNotBeFound("coutry.in=" + UPDATED_COUTRY);
    }

    @Test
    @Transactional
    void getAllMoviesByCoutryIsNullOrNotNull() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where coutry is not null
        defaultMovieShouldBeFound("coutry.specified=true");

        // Get all the movieList where coutry is null
        defaultMovieShouldNotBeFound("coutry.specified=false");
    }

    @Test
    @Transactional
    void getAllMoviesByCoutryContainsSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where coutry contains DEFAULT_COUTRY
        defaultMovieShouldBeFound("coutry.contains=" + DEFAULT_COUTRY);

        // Get all the movieList where coutry contains UPDATED_COUTRY
        defaultMovieShouldNotBeFound("coutry.contains=" + UPDATED_COUTRY);
    }

    @Test
    @Transactional
    void getAllMoviesByCoutryNotContainsSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where coutry does not contain DEFAULT_COUTRY
        defaultMovieShouldNotBeFound("coutry.doesNotContain=" + DEFAULT_COUTRY);

        // Get all the movieList where coutry does not contain UPDATED_COUTRY
        defaultMovieShouldBeFound("coutry.doesNotContain=" + UPDATED_COUTRY);
    }

    @Test
    @Transactional
    void getAllMoviesByPremiereIsEqualToSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where premiere equals to DEFAULT_PREMIERE
        defaultMovieShouldBeFound("premiere.equals=" + DEFAULT_PREMIERE);

        // Get all the movieList where premiere equals to UPDATED_PREMIERE
        defaultMovieShouldNotBeFound("premiere.equals=" + UPDATED_PREMIERE);
    }

    @Test
    @Transactional
    void getAllMoviesByPremiereIsNotEqualToSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where premiere not equals to DEFAULT_PREMIERE
        defaultMovieShouldNotBeFound("premiere.notEquals=" + DEFAULT_PREMIERE);

        // Get all the movieList where premiere not equals to UPDATED_PREMIERE
        defaultMovieShouldBeFound("premiere.notEquals=" + UPDATED_PREMIERE);
    }

    @Test
    @Transactional
    void getAllMoviesByPremiereIsInShouldWork() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where premiere in DEFAULT_PREMIERE or UPDATED_PREMIERE
        defaultMovieShouldBeFound("premiere.in=" + DEFAULT_PREMIERE + "," + UPDATED_PREMIERE);

        // Get all the movieList where premiere equals to UPDATED_PREMIERE
        defaultMovieShouldNotBeFound("premiere.in=" + UPDATED_PREMIERE);
    }

    @Test
    @Transactional
    void getAllMoviesByPremiereIsNullOrNotNull() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where premiere is not null
        defaultMovieShouldBeFound("premiere.specified=true");

        // Get all the movieList where premiere is null
        defaultMovieShouldNotBeFound("premiere.specified=false");
    }

    @Test
    @Transactional
    void getAllMoviesByPremiereIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where premiere is greater than or equal to DEFAULT_PREMIERE
        defaultMovieShouldBeFound("premiere.greaterThanOrEqual=" + DEFAULT_PREMIERE);

        // Get all the movieList where premiere is greater than or equal to UPDATED_PREMIERE
        defaultMovieShouldNotBeFound("premiere.greaterThanOrEqual=" + UPDATED_PREMIERE);
    }

    @Test
    @Transactional
    void getAllMoviesByPremiereIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where premiere is less than or equal to DEFAULT_PREMIERE
        defaultMovieShouldBeFound("premiere.lessThanOrEqual=" + DEFAULT_PREMIERE);

        // Get all the movieList where premiere is less than or equal to SMALLER_PREMIERE
        defaultMovieShouldNotBeFound("premiere.lessThanOrEqual=" + SMALLER_PREMIERE);
    }

    @Test
    @Transactional
    void getAllMoviesByPremiereIsLessThanSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where premiere is less than DEFAULT_PREMIERE
        defaultMovieShouldNotBeFound("premiere.lessThan=" + DEFAULT_PREMIERE);

        // Get all the movieList where premiere is less than UPDATED_PREMIERE
        defaultMovieShouldBeFound("premiere.lessThan=" + UPDATED_PREMIERE);
    }

    @Test
    @Transactional
    void getAllMoviesByPremiereIsGreaterThanSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where premiere is greater than DEFAULT_PREMIERE
        defaultMovieShouldNotBeFound("premiere.greaterThan=" + DEFAULT_PREMIERE);

        // Get all the movieList where premiere is greater than SMALLER_PREMIERE
        defaultMovieShouldBeFound("premiere.greaterThan=" + SMALLER_PREMIERE);
    }

    @Test
    @Transactional
    void getAllMoviesByPosterUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where posterUrl equals to DEFAULT_POSTER_URL
        defaultMovieShouldBeFound("posterUrl.equals=" + DEFAULT_POSTER_URL);

        // Get all the movieList where posterUrl equals to UPDATED_POSTER_URL
        defaultMovieShouldNotBeFound("posterUrl.equals=" + UPDATED_POSTER_URL);
    }

    @Test
    @Transactional
    void getAllMoviesByPosterUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where posterUrl not equals to DEFAULT_POSTER_URL
        defaultMovieShouldNotBeFound("posterUrl.notEquals=" + DEFAULT_POSTER_URL);

        // Get all the movieList where posterUrl not equals to UPDATED_POSTER_URL
        defaultMovieShouldBeFound("posterUrl.notEquals=" + UPDATED_POSTER_URL);
    }

    @Test
    @Transactional
    void getAllMoviesByPosterUrlIsInShouldWork() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where posterUrl in DEFAULT_POSTER_URL or UPDATED_POSTER_URL
        defaultMovieShouldBeFound("posterUrl.in=" + DEFAULT_POSTER_URL + "," + UPDATED_POSTER_URL);

        // Get all the movieList where posterUrl equals to UPDATED_POSTER_URL
        defaultMovieShouldNotBeFound("posterUrl.in=" + UPDATED_POSTER_URL);
    }

    @Test
    @Transactional
    void getAllMoviesByPosterUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where posterUrl is not null
        defaultMovieShouldBeFound("posterUrl.specified=true");

        // Get all the movieList where posterUrl is null
        defaultMovieShouldNotBeFound("posterUrl.specified=false");
    }

    @Test
    @Transactional
    void getAllMoviesByPosterUrlContainsSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where posterUrl contains DEFAULT_POSTER_URL
        defaultMovieShouldBeFound("posterUrl.contains=" + DEFAULT_POSTER_URL);

        // Get all the movieList where posterUrl contains UPDATED_POSTER_URL
        defaultMovieShouldNotBeFound("posterUrl.contains=" + UPDATED_POSTER_URL);
    }

    @Test
    @Transactional
    void getAllMoviesByPosterUrlNotContainsSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where posterUrl does not contain DEFAULT_POSTER_URL
        defaultMovieShouldNotBeFound("posterUrl.doesNotContain=" + DEFAULT_POSTER_URL);

        // Get all the movieList where posterUrl does not contain UPDATED_POSTER_URL
        defaultMovieShouldBeFound("posterUrl.doesNotContain=" + UPDATED_POSTER_URL);
    }

    @Test
    @Transactional
    void getAllMoviesByBackgroundUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where backgroundUrl equals to DEFAULT_BACKGROUND_URL
        defaultMovieShouldBeFound("backgroundUrl.equals=" + DEFAULT_BACKGROUND_URL);

        // Get all the movieList where backgroundUrl equals to UPDATED_BACKGROUND_URL
        defaultMovieShouldNotBeFound("backgroundUrl.equals=" + UPDATED_BACKGROUND_URL);
    }

    @Test
    @Transactional
    void getAllMoviesByBackgroundUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where backgroundUrl not equals to DEFAULT_BACKGROUND_URL
        defaultMovieShouldNotBeFound("backgroundUrl.notEquals=" + DEFAULT_BACKGROUND_URL);

        // Get all the movieList where backgroundUrl not equals to UPDATED_BACKGROUND_URL
        defaultMovieShouldBeFound("backgroundUrl.notEquals=" + UPDATED_BACKGROUND_URL);
    }

    @Test
    @Transactional
    void getAllMoviesByBackgroundUrlIsInShouldWork() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where backgroundUrl in DEFAULT_BACKGROUND_URL or UPDATED_BACKGROUND_URL
        defaultMovieShouldBeFound("backgroundUrl.in=" + DEFAULT_BACKGROUND_URL + "," + UPDATED_BACKGROUND_URL);

        // Get all the movieList where backgroundUrl equals to UPDATED_BACKGROUND_URL
        defaultMovieShouldNotBeFound("backgroundUrl.in=" + UPDATED_BACKGROUND_URL);
    }

    @Test
    @Transactional
    void getAllMoviesByBackgroundUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where backgroundUrl is not null
        defaultMovieShouldBeFound("backgroundUrl.specified=true");

        // Get all the movieList where backgroundUrl is null
        defaultMovieShouldNotBeFound("backgroundUrl.specified=false");
    }

    @Test
    @Transactional
    void getAllMoviesByBackgroundUrlContainsSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where backgroundUrl contains DEFAULT_BACKGROUND_URL
        defaultMovieShouldBeFound("backgroundUrl.contains=" + DEFAULT_BACKGROUND_URL);

        // Get all the movieList where backgroundUrl contains UPDATED_BACKGROUND_URL
        defaultMovieShouldNotBeFound("backgroundUrl.contains=" + UPDATED_BACKGROUND_URL);
    }

    @Test
    @Transactional
    void getAllMoviesByBackgroundUrlNotContainsSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where backgroundUrl does not contain DEFAULT_BACKGROUND_URL
        defaultMovieShouldNotBeFound("backgroundUrl.doesNotContain=" + DEFAULT_BACKGROUND_URL);

        // Get all the movieList where backgroundUrl does not contain UPDATED_BACKGROUND_URL
        defaultMovieShouldBeFound("backgroundUrl.doesNotContain=" + UPDATED_BACKGROUND_URL);
    }

    @Test
    @Transactional
    void getAllMoviesByMovieUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where movieUrl equals to DEFAULT_MOVIE_URL
        defaultMovieShouldBeFound("movieUrl.equals=" + DEFAULT_MOVIE_URL);

        // Get all the movieList where movieUrl equals to UPDATED_MOVIE_URL
        defaultMovieShouldNotBeFound("movieUrl.equals=" + UPDATED_MOVIE_URL);
    }

    @Test
    @Transactional
    void getAllMoviesByMovieUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where movieUrl not equals to DEFAULT_MOVIE_URL
        defaultMovieShouldNotBeFound("movieUrl.notEquals=" + DEFAULT_MOVIE_URL);

        // Get all the movieList where movieUrl not equals to UPDATED_MOVIE_URL
        defaultMovieShouldBeFound("movieUrl.notEquals=" + UPDATED_MOVIE_URL);
    }

    @Test
    @Transactional
    void getAllMoviesByMovieUrlIsInShouldWork() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where movieUrl in DEFAULT_MOVIE_URL or UPDATED_MOVIE_URL
        defaultMovieShouldBeFound("movieUrl.in=" + DEFAULT_MOVIE_URL + "," + UPDATED_MOVIE_URL);

        // Get all the movieList where movieUrl equals to UPDATED_MOVIE_URL
        defaultMovieShouldNotBeFound("movieUrl.in=" + UPDATED_MOVIE_URL);
    }

    @Test
    @Transactional
    void getAllMoviesByMovieUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where movieUrl is not null
        defaultMovieShouldBeFound("movieUrl.specified=true");

        // Get all the movieList where movieUrl is null
        defaultMovieShouldNotBeFound("movieUrl.specified=false");
    }

    @Test
    @Transactional
    void getAllMoviesByMovieUrlContainsSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where movieUrl contains DEFAULT_MOVIE_URL
        defaultMovieShouldBeFound("movieUrl.contains=" + DEFAULT_MOVIE_URL);

        // Get all the movieList where movieUrl contains UPDATED_MOVIE_URL
        defaultMovieShouldNotBeFound("movieUrl.contains=" + UPDATED_MOVIE_URL);
    }

    @Test
    @Transactional
    void getAllMoviesByMovieUrlNotContainsSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where movieUrl does not contain DEFAULT_MOVIE_URL
        defaultMovieShouldNotBeFound("movieUrl.doesNotContain=" + DEFAULT_MOVIE_URL);

        // Get all the movieList where movieUrl does not contain UPDATED_MOVIE_URL
        defaultMovieShouldBeFound("movieUrl.doesNotContain=" + UPDATED_MOVIE_URL);
    }

    @Test
    @Transactional
    void getAllMoviesByIsActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where isActive equals to DEFAULT_IS_ACTIVE
        defaultMovieShouldBeFound("isActive.equals=" + DEFAULT_IS_ACTIVE);

        // Get all the movieList where isActive equals to UPDATED_IS_ACTIVE
        defaultMovieShouldNotBeFound("isActive.equals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllMoviesByIsActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where isActive not equals to DEFAULT_IS_ACTIVE
        defaultMovieShouldNotBeFound("isActive.notEquals=" + DEFAULT_IS_ACTIVE);

        // Get all the movieList where isActive not equals to UPDATED_IS_ACTIVE
        defaultMovieShouldBeFound("isActive.notEquals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllMoviesByIsActiveIsInShouldWork() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where isActive in DEFAULT_IS_ACTIVE or UPDATED_IS_ACTIVE
        defaultMovieShouldBeFound("isActive.in=" + DEFAULT_IS_ACTIVE + "," + UPDATED_IS_ACTIVE);

        // Get all the movieList where isActive equals to UPDATED_IS_ACTIVE
        defaultMovieShouldNotBeFound("isActive.in=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllMoviesByIsActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where isActive is not null
        defaultMovieShouldBeFound("isActive.specified=true");

        // Get all the movieList where isActive is null
        defaultMovieShouldNotBeFound("isActive.specified=false");
    }

    @Test
    @Transactional
    void getAllMoviesByUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where updatedDate equals to DEFAULT_UPDATED_DATE
        defaultMovieShouldBeFound("updatedDate.equals=" + DEFAULT_UPDATED_DATE);

        // Get all the movieList where updatedDate equals to UPDATED_UPDATED_DATE
        defaultMovieShouldNotBeFound("updatedDate.equals=" + UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllMoviesByUpdatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where updatedDate not equals to DEFAULT_UPDATED_DATE
        defaultMovieShouldNotBeFound("updatedDate.notEquals=" + DEFAULT_UPDATED_DATE);

        // Get all the movieList where updatedDate not equals to UPDATED_UPDATED_DATE
        defaultMovieShouldBeFound("updatedDate.notEquals=" + UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllMoviesByUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where updatedDate in DEFAULT_UPDATED_DATE or UPDATED_UPDATED_DATE
        defaultMovieShouldBeFound("updatedDate.in=" + DEFAULT_UPDATED_DATE + "," + UPDATED_UPDATED_DATE);

        // Get all the movieList where updatedDate equals to UPDATED_UPDATED_DATE
        defaultMovieShouldNotBeFound("updatedDate.in=" + UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllMoviesByUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where updatedDate is not null
        defaultMovieShouldBeFound("updatedDate.specified=true");

        // Get all the movieList where updatedDate is null
        defaultMovieShouldNotBeFound("updatedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllMoviesByUpdatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where updatedDate is greater than or equal to DEFAULT_UPDATED_DATE
        defaultMovieShouldBeFound("updatedDate.greaterThanOrEqual=" + DEFAULT_UPDATED_DATE);

        // Get all the movieList where updatedDate is greater than or equal to UPDATED_UPDATED_DATE
        defaultMovieShouldNotBeFound("updatedDate.greaterThanOrEqual=" + UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllMoviesByUpdatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where updatedDate is less than or equal to DEFAULT_UPDATED_DATE
        defaultMovieShouldBeFound("updatedDate.lessThanOrEqual=" + DEFAULT_UPDATED_DATE);

        // Get all the movieList where updatedDate is less than or equal to SMALLER_UPDATED_DATE
        defaultMovieShouldNotBeFound("updatedDate.lessThanOrEqual=" + SMALLER_UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllMoviesByUpdatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where updatedDate is less than DEFAULT_UPDATED_DATE
        defaultMovieShouldNotBeFound("updatedDate.lessThan=" + DEFAULT_UPDATED_DATE);

        // Get all the movieList where updatedDate is less than UPDATED_UPDATED_DATE
        defaultMovieShouldBeFound("updatedDate.lessThan=" + UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllMoviesByUpdatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where updatedDate is greater than DEFAULT_UPDATED_DATE
        defaultMovieShouldNotBeFound("updatedDate.greaterThan=" + DEFAULT_UPDATED_DATE);

        // Get all the movieList where updatedDate is greater than SMALLER_UPDATED_DATE
        defaultMovieShouldBeFound("updatedDate.greaterThan=" + SMALLER_UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllMoviesByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where createdDate equals to DEFAULT_CREATED_DATE
        defaultMovieShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the movieList where createdDate equals to UPDATED_CREATED_DATE
        defaultMovieShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllMoviesByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultMovieShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the movieList where createdDate not equals to UPDATED_CREATED_DATE
        defaultMovieShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllMoviesByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultMovieShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the movieList where createdDate equals to UPDATED_CREATED_DATE
        defaultMovieShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllMoviesByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where createdDate is not null
        defaultMovieShouldBeFound("createdDate.specified=true");

        // Get all the movieList where createdDate is null
        defaultMovieShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllMoviesByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where createdDate is greater than or equal to DEFAULT_CREATED_DATE
        defaultMovieShouldBeFound("createdDate.greaterThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the movieList where createdDate is greater than or equal to UPDATED_CREATED_DATE
        defaultMovieShouldNotBeFound("createdDate.greaterThanOrEqual=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllMoviesByCreatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where createdDate is less than or equal to DEFAULT_CREATED_DATE
        defaultMovieShouldBeFound("createdDate.lessThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the movieList where createdDate is less than or equal to SMALLER_CREATED_DATE
        defaultMovieShouldNotBeFound("createdDate.lessThanOrEqual=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllMoviesByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where createdDate is less than DEFAULT_CREATED_DATE
        defaultMovieShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the movieList where createdDate is less than UPDATED_CREATED_DATE
        defaultMovieShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllMoviesByCreatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList where createdDate is greater than DEFAULT_CREATED_DATE
        defaultMovieShouldNotBeFound("createdDate.greaterThan=" + DEFAULT_CREATED_DATE);

        // Get all the movieList where createdDate is greater than SMALLER_CREATED_DATE
        defaultMovieShouldBeFound("createdDate.greaterThan=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllMoviesByTrailerIsEqualToSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);
        Trailer trailer;
        if (TestUtil.findAll(em, Trailer.class).isEmpty()) {
            trailer = TrailerResourceIT.createEntity(em);
            em.persist(trailer);
            em.flush();
        } else {
            trailer = TestUtil.findAll(em, Trailer.class).get(0);
        }
        em.persist(trailer);
        em.flush();
        movie.addTrailer(trailer);
        movieRepository.saveAndFlush(movie);
        Long trailerId = trailer.getId();

        // Get all the movieList where trailer equals to trailerId
        defaultMovieShouldBeFound("trailerId.equals=" + trailerId);

        // Get all the movieList where trailer equals to (trailerId + 1)
        defaultMovieShouldNotBeFound("trailerId.equals=" + (trailerId + 1));
    }

    @Test
    @Transactional
    void getAllMoviesByEpisodeIsEqualToSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);
        Episode episode;
        if (TestUtil.findAll(em, Episode.class).isEmpty()) {
            episode = EpisodeResourceIT.createEntity(em);
            em.persist(episode);
            em.flush();
        } else {
            episode = TestUtil.findAll(em, Episode.class).get(0);
        }
        em.persist(episode);
        em.flush();
        movie.addEpisode(episode);
        movieRepository.saveAndFlush(movie);
        Long episodeId = episode.getId();

        // Get all the movieList where episode equals to episodeId
        defaultMovieShouldBeFound("episodeId.equals=" + episodeId);

        // Get all the movieList where episode equals to (episodeId + 1)
        defaultMovieShouldNotBeFound("episodeId.equals=" + (episodeId + 1));
    }

    @Test
    @Transactional
    void getAllMoviesByCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);
        Category category;
        if (TestUtil.findAll(em, Category.class).isEmpty()) {
            category = CategoryResourceIT.createEntity(em);
            em.persist(category);
            em.flush();
        } else {
            category = TestUtil.findAll(em, Category.class).get(0);
        }
        em.persist(category);
        em.flush();
        movie.addCategory(category);
        movieRepository.saveAndFlush(movie);
        Long categoryId = category.getId();

        // Get all the movieList where category equals to categoryId
        defaultMovieShouldBeFound("categoryId.equals=" + categoryId);

        // Get all the movieList where category equals to (categoryId + 1)
        defaultMovieShouldNotBeFound("categoryId.equals=" + (categoryId + 1));
    }

    @Test
    @Transactional
    void getAllMoviesByActorIsEqualToSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);
        Actor actor;
        if (TestUtil.findAll(em, Actor.class).isEmpty()) {
            actor = ActorResourceIT.createEntity(em);
            em.persist(actor);
            em.flush();
        } else {
            actor = TestUtil.findAll(em, Actor.class).get(0);
        }
        em.persist(actor);
        em.flush();
        movie.addActor(actor);
        movieRepository.saveAndFlush(movie);
        Long actorId = actor.getId();

        // Get all the movieList where actor equals to actorId
        defaultMovieShouldBeFound("actorId.equals=" + actorId);

        // Get all the movieList where actor equals to (actorId + 1)
        defaultMovieShouldNotBeFound("actorId.equals=" + (actorId + 1));
    }

    @Test
    @Transactional
    void getAllMoviesBySessionIsEqualToSomething() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);
        Session session;
        if (TestUtil.findAll(em, Session.class).isEmpty()) {
            session = SessionResourceIT.createEntity(em);
            em.persist(session);
            em.flush();
        } else {
            session = TestUtil.findAll(em, Session.class).get(0);
        }
        em.persist(session);
        em.flush();
        movie.setSession(session);
        movieRepository.saveAndFlush(movie);
        Long sessionId = session.getId();

        // Get all the movieList where session equals to sessionId
        defaultMovieShouldBeFound("sessionId.equals=" + sessionId);

        // Get all the movieList where session equals to (sessionId + 1)
        defaultMovieShouldNotBeFound("sessionId.equals=" + (sessionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMovieShouldBeFound(String filter) throws Exception {
        restMovieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(movie.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].nameInVietNamese").value(hasItem(DEFAULT_NAME_IN_VIET_NAMESE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME.intValue())))
            .andExpect(jsonPath("$.[*].iMDB").value(hasItem(DEFAULT_I_MDB.doubleValue())))
            .andExpect(jsonPath("$.[*].director").value(hasItem(DEFAULT_DIRECTOR)))
            .andExpect(jsonPath("$.[*].script").value(hasItem(DEFAULT_SCRIPT)))
            .andExpect(jsonPath("$.[*].coutry").value(hasItem(DEFAULT_COUTRY)))
            .andExpect(jsonPath("$.[*].premiere").value(hasItem(DEFAULT_PREMIERE.toString())))
            .andExpect(jsonPath("$.[*].posterUrl").value(hasItem(DEFAULT_POSTER_URL)))
            .andExpect(jsonPath("$.[*].backgroundUrl").value(hasItem(DEFAULT_BACKGROUND_URL)))
            .andExpect(jsonPath("$.[*].movieUrl").value(hasItem(DEFAULT_MOVIE_URL)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));

        // Check, that the count call also returns 1
        restMovieMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMovieShouldNotBeFound(String filter) throws Exception {
        restMovieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMovieMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMovie() throws Exception {
        // Get the movie
        restMovieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMovie() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        int databaseSizeBeforeUpdate = movieRepository.findAll().size();

        // Update the movie
        Movie updatedMovie = movieRepository.findById(movie.getId()).get();
        // Disconnect from session so that the updates on updatedMovie are not directly saved in db
        em.detach(updatedMovie);
        updatedMovie
            .name(UPDATED_NAME)
            .nameInVietNamese(UPDATED_NAME_IN_VIET_NAMESE)
            .description(UPDATED_DESCRIPTION)
            .time(UPDATED_TIME)
            .iMDB(UPDATED_I_MDB)
            .director(UPDATED_DIRECTOR)
            .script(UPDATED_SCRIPT)
            .coutry(UPDATED_COUTRY)
            .premiere(UPDATED_PREMIERE)
            .posterUrl(UPDATED_POSTER_URL)
            .backgroundUrl(UPDATED_BACKGROUND_URL)
            .movieUrl(UPDATED_MOVIE_URL)
            .isActive(UPDATED_IS_ACTIVE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .createdDate(UPDATED_CREATED_DATE);
        MovieDTO movieDTO = movieMapper.toDto(updatedMovie);

        restMovieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, movieDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(movieDTO))
            )
            .andExpect(status().isOk());

        // Validate the Movie in the database
        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeUpdate);
        Movie testMovie = movieList.get(movieList.size() - 1);
        assertThat(testMovie.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMovie.getNameInVietNamese()).isEqualTo(UPDATED_NAME_IN_VIET_NAMESE);
        assertThat(testMovie.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMovie.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testMovie.getiMDB()).isEqualTo(UPDATED_I_MDB);
        assertThat(testMovie.getDirector()).isEqualTo(UPDATED_DIRECTOR);
        assertThat(testMovie.getScript()).isEqualTo(UPDATED_SCRIPT);
        assertThat(testMovie.getCoutry()).isEqualTo(UPDATED_COUTRY);
        assertThat(testMovie.getPremiere()).isEqualTo(UPDATED_PREMIERE);
        assertThat(testMovie.getPosterUrl()).isEqualTo(UPDATED_POSTER_URL);
        assertThat(testMovie.getBackgroundUrl()).isEqualTo(UPDATED_BACKGROUND_URL);
        assertThat(testMovie.getMovieUrl()).isEqualTo(UPDATED_MOVIE_URL);
        assertThat(testMovie.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testMovie.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testMovie.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingMovie() throws Exception {
        int databaseSizeBeforeUpdate = movieRepository.findAll().size();
        movie.setId(count.incrementAndGet());

        // Create the Movie
        MovieDTO movieDTO = movieMapper.toDto(movie);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMovieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, movieDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(movieDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Movie in the database
        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMovie() throws Exception {
        int databaseSizeBeforeUpdate = movieRepository.findAll().size();
        movie.setId(count.incrementAndGet());

        // Create the Movie
        MovieDTO movieDTO = movieMapper.toDto(movie);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMovieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(movieDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Movie in the database
        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMovie() throws Exception {
        int databaseSizeBeforeUpdate = movieRepository.findAll().size();
        movie.setId(count.incrementAndGet());

        // Create the Movie
        MovieDTO movieDTO = movieMapper.toDto(movie);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMovieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(movieDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Movie in the database
        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMovieWithPatch() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        int databaseSizeBeforeUpdate = movieRepository.findAll().size();

        // Update the movie using partial update
        Movie partialUpdatedMovie = new Movie();
        partialUpdatedMovie.setId(movie.getId());

        partialUpdatedMovie
            .description(UPDATED_DESCRIPTION)
            .time(UPDATED_TIME)
            .iMDB(UPDATED_I_MDB)
            .director(UPDATED_DIRECTOR)
            .script(UPDATED_SCRIPT)
            .premiere(UPDATED_PREMIERE)
            .posterUrl(UPDATED_POSTER_URL)
            .updatedDate(UPDATED_UPDATED_DATE);

        restMovieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMovie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMovie))
            )
            .andExpect(status().isOk());

        // Validate the Movie in the database
        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeUpdate);
        Movie testMovie = movieList.get(movieList.size() - 1);
        assertThat(testMovie.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMovie.getNameInVietNamese()).isEqualTo(DEFAULT_NAME_IN_VIET_NAMESE);
        assertThat(testMovie.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMovie.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testMovie.getiMDB()).isEqualTo(UPDATED_I_MDB);
        assertThat(testMovie.getDirector()).isEqualTo(UPDATED_DIRECTOR);
        assertThat(testMovie.getScript()).isEqualTo(UPDATED_SCRIPT);
        assertThat(testMovie.getCoutry()).isEqualTo(DEFAULT_COUTRY);
        assertThat(testMovie.getPremiere()).isEqualTo(UPDATED_PREMIERE);
        assertThat(testMovie.getPosterUrl()).isEqualTo(UPDATED_POSTER_URL);
        assertThat(testMovie.getBackgroundUrl()).isEqualTo(DEFAULT_BACKGROUND_URL);
        assertThat(testMovie.getMovieUrl()).isEqualTo(DEFAULT_MOVIE_URL);
        assertThat(testMovie.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testMovie.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testMovie.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateMovieWithPatch() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        int databaseSizeBeforeUpdate = movieRepository.findAll().size();

        // Update the movie using partial update
        Movie partialUpdatedMovie = new Movie();
        partialUpdatedMovie.setId(movie.getId());

        partialUpdatedMovie
            .name(UPDATED_NAME)
            .nameInVietNamese(UPDATED_NAME_IN_VIET_NAMESE)
            .description(UPDATED_DESCRIPTION)
            .time(UPDATED_TIME)
            .iMDB(UPDATED_I_MDB)
            .director(UPDATED_DIRECTOR)
            .script(UPDATED_SCRIPT)
            .coutry(UPDATED_COUTRY)
            .premiere(UPDATED_PREMIERE)
            .posterUrl(UPDATED_POSTER_URL)
            .backgroundUrl(UPDATED_BACKGROUND_URL)
            .movieUrl(UPDATED_MOVIE_URL)
            .isActive(UPDATED_IS_ACTIVE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .createdDate(UPDATED_CREATED_DATE);

        restMovieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMovie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMovie))
            )
            .andExpect(status().isOk());

        // Validate the Movie in the database
        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeUpdate);
        Movie testMovie = movieList.get(movieList.size() - 1);
        assertThat(testMovie.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMovie.getNameInVietNamese()).isEqualTo(UPDATED_NAME_IN_VIET_NAMESE);
        assertThat(testMovie.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMovie.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testMovie.getiMDB()).isEqualTo(UPDATED_I_MDB);
        assertThat(testMovie.getDirector()).isEqualTo(UPDATED_DIRECTOR);
        assertThat(testMovie.getScript()).isEqualTo(UPDATED_SCRIPT);
        assertThat(testMovie.getCoutry()).isEqualTo(UPDATED_COUTRY);
        assertThat(testMovie.getPremiere()).isEqualTo(UPDATED_PREMIERE);
        assertThat(testMovie.getPosterUrl()).isEqualTo(UPDATED_POSTER_URL);
        assertThat(testMovie.getBackgroundUrl()).isEqualTo(UPDATED_BACKGROUND_URL);
        assertThat(testMovie.getMovieUrl()).isEqualTo(UPDATED_MOVIE_URL);
        assertThat(testMovie.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testMovie.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testMovie.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingMovie() throws Exception {
        int databaseSizeBeforeUpdate = movieRepository.findAll().size();
        movie.setId(count.incrementAndGet());

        // Create the Movie
        MovieDTO movieDTO = movieMapper.toDto(movie);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMovieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, movieDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(movieDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Movie in the database
        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMovie() throws Exception {
        int databaseSizeBeforeUpdate = movieRepository.findAll().size();
        movie.setId(count.incrementAndGet());

        // Create the Movie
        MovieDTO movieDTO = movieMapper.toDto(movie);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMovieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(movieDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Movie in the database
        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMovie() throws Exception {
        int databaseSizeBeforeUpdate = movieRepository.findAll().size();
        movie.setId(count.incrementAndGet());

        // Create the Movie
        MovieDTO movieDTO = movieMapper.toDto(movie);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMovieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(movieDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Movie in the database
        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMovie() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        int databaseSizeBeforeDelete = movieRepository.findAll().size();

        // Delete the movie
        restMovieMockMvc
            .perform(delete(ENTITY_API_URL_ID, movie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
