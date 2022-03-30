package com.movie_admin.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.movie_admin.IntegrationTest;
import com.movie_admin.domain.Trailer;
import com.movie_admin.repository.TrailerRepository;
import com.movie_admin.service.dto.TrailerDTO;
import com.movie_admin.service.mapper.TrailerMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TrailerResourceIT {

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/trailers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TrailerRepository trailerRepository;

    @Autowired
    private TrailerMapper trailerMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTrailerMockMvc;

    private Trailer trailer;


    public static Trailer createEntity(EntityManager em) {
        Trailer trailer = new Trailer().url(DEFAULT_URL).createdDate(DEFAULT_CREATED_DATE);
        return trailer;
    }


    public static Trailer createUpdatedEntity(EntityManager em) {
        Trailer trailer = new Trailer().url(UPDATED_URL).createdDate(UPDATED_CREATED_DATE);
        return trailer;
    }

    @BeforeEach
    public void initTest() {
        trailer = createEntity(em);
    }

    @Test
    @Transactional
    void createTrailer() throws Exception {
        int databaseSizeBeforeCreate = trailerRepository.findAll().size();
        // Create the Trailer
        TrailerDTO trailerDTO = trailerMapper.toDto(trailer);
        restTrailerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trailerDTO)))
            .andExpect(status().isCreated());

        // Validate the Trailer in the database
        List<Trailer> trailerList = trailerRepository.findAll();
        assertThat(trailerList).hasSize(databaseSizeBeforeCreate + 1);
        Trailer testTrailer = trailerList.get(trailerList.size() - 1);
        assertThat(testTrailer.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testTrailer.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    void createTrailerWithExistingId() throws Exception {
        // Create the Trailer with an existing ID
        trailer.setId(1L);
        TrailerDTO trailerDTO = trailerMapper.toDto(trailer);

        int databaseSizeBeforeCreate = trailerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrailerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trailerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Trailer in the database
        List<Trailer> trailerList = trailerRepository.findAll();
        assertThat(trailerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = trailerRepository.findAll().size();
        // set the field null
        trailer.setUrl(null);

        // Create the Trailer, which fails.
        TrailerDTO trailerDTO = trailerMapper.toDto(trailer);

        restTrailerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trailerDTO)))
            .andExpect(status().isBadRequest());

        List<Trailer> trailerList = trailerRepository.findAll();
        assertThat(trailerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = trailerRepository.findAll().size();
        // set the field null
        trailer.setCreatedDate(null);

        // Create the Trailer, which fails.
        TrailerDTO trailerDTO = trailerMapper.toDto(trailer);

        restTrailerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trailerDTO)))
            .andExpect(status().isBadRequest());

        List<Trailer> trailerList = trailerRepository.findAll();
        assertThat(trailerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTrailers() throws Exception {
        // Initialize the database
        trailerRepository.saveAndFlush(trailer);

        // Get all the trailerList
        restTrailerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trailer.getId().intValue())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getTrailer() throws Exception {
        // Initialize the database
        trailerRepository.saveAndFlush(trailer);

        // Get the trailer
        restTrailerMockMvc
            .perform(get(ENTITY_API_URL_ID, trailer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(trailer.getId().intValue()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTrailer() throws Exception {
        // Get the trailer
        restTrailerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTrailer() throws Exception {
        // Initialize the database
        trailerRepository.saveAndFlush(trailer);

        int databaseSizeBeforeUpdate = trailerRepository.findAll().size();

        // Update the trailer
        Trailer updatedTrailer = trailerRepository.findById(trailer.getId()).get();
        // Disconnect from session so that the updates on updatedTrailer are not directly saved in db
        em.detach(updatedTrailer);
        updatedTrailer.url(UPDATED_URL).createdDate(UPDATED_CREATED_DATE);
        TrailerDTO trailerDTO = trailerMapper.toDto(updatedTrailer);

        restTrailerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trailerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trailerDTO))
            )
            .andExpect(status().isOk());

        // Validate the Trailer in the database
        List<Trailer> trailerList = trailerRepository.findAll();
        assertThat(trailerList).hasSize(databaseSizeBeforeUpdate);
        Trailer testTrailer = trailerList.get(trailerList.size() - 1);
        assertThat(testTrailer.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testTrailer.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingTrailer() throws Exception {
        int databaseSizeBeforeUpdate = trailerRepository.findAll().size();
        trailer.setId(count.incrementAndGet());

        // Create the Trailer
        TrailerDTO trailerDTO = trailerMapper.toDto(trailer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrailerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trailerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trailerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Trailer in the database
        List<Trailer> trailerList = trailerRepository.findAll();
        assertThat(trailerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTrailer() throws Exception {
        int databaseSizeBeforeUpdate = trailerRepository.findAll().size();
        trailer.setId(count.incrementAndGet());

        // Create the Trailer
        TrailerDTO trailerDTO = trailerMapper.toDto(trailer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrailerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trailerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Trailer in the database
        List<Trailer> trailerList = trailerRepository.findAll();
        assertThat(trailerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTrailer() throws Exception {
        int databaseSizeBeforeUpdate = trailerRepository.findAll().size();
        trailer.setId(count.incrementAndGet());

        // Create the Trailer
        TrailerDTO trailerDTO = trailerMapper.toDto(trailer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrailerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trailerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Trailer in the database
        List<Trailer> trailerList = trailerRepository.findAll();
        assertThat(trailerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTrailerWithPatch() throws Exception {
        // Initialize the database
        trailerRepository.saveAndFlush(trailer);

        int databaseSizeBeforeUpdate = trailerRepository.findAll().size();

        // Update the trailer using partial update
        Trailer partialUpdatedTrailer = new Trailer();
        partialUpdatedTrailer.setId(trailer.getId());

        partialUpdatedTrailer.url(UPDATED_URL).createdDate(UPDATED_CREATED_DATE);

        restTrailerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrailer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTrailer))
            )
            .andExpect(status().isOk());

        // Validate the Trailer in the database
        List<Trailer> trailerList = trailerRepository.findAll();
        assertThat(trailerList).hasSize(databaseSizeBeforeUpdate);
        Trailer testTrailer = trailerList.get(trailerList.size() - 1);
        assertThat(testTrailer.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testTrailer.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateTrailerWithPatch() throws Exception {
        // Initialize the database
        trailerRepository.saveAndFlush(trailer);

        int databaseSizeBeforeUpdate = trailerRepository.findAll().size();

        // Update the trailer using partial update
        Trailer partialUpdatedTrailer = new Trailer();
        partialUpdatedTrailer.setId(trailer.getId());

        partialUpdatedTrailer.url(UPDATED_URL).createdDate(UPDATED_CREATED_DATE);

        restTrailerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrailer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTrailer))
            )
            .andExpect(status().isOk());

        // Validate the Trailer in the database
        List<Trailer> trailerList = trailerRepository.findAll();
        assertThat(trailerList).hasSize(databaseSizeBeforeUpdate);
        Trailer testTrailer = trailerList.get(trailerList.size() - 1);
        assertThat(testTrailer.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testTrailer.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingTrailer() throws Exception {
        int databaseSizeBeforeUpdate = trailerRepository.findAll().size();
        trailer.setId(count.incrementAndGet());

        // Create the Trailer
        TrailerDTO trailerDTO = trailerMapper.toDto(trailer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrailerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, trailerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trailerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Trailer in the database
        List<Trailer> trailerList = trailerRepository.findAll();
        assertThat(trailerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTrailer() throws Exception {
        int databaseSizeBeforeUpdate = trailerRepository.findAll().size();
        trailer.setId(count.incrementAndGet());

        // Create the Trailer
        TrailerDTO trailerDTO = trailerMapper.toDto(trailer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrailerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trailerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Trailer in the database
        List<Trailer> trailerList = trailerRepository.findAll();
        assertThat(trailerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTrailer() throws Exception {
        int databaseSizeBeforeUpdate = trailerRepository.findAll().size();
        trailer.setId(count.incrementAndGet());

        // Create the Trailer
        TrailerDTO trailerDTO = trailerMapper.toDto(trailer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrailerMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(trailerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Trailer in the database
        List<Trailer> trailerList = trailerRepository.findAll();
        assertThat(trailerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTrailer() throws Exception {
        // Initialize the database
        trailerRepository.saveAndFlush(trailer);

        int databaseSizeBeforeDelete = trailerRepository.findAll().size();

        // Delete the trailer
        restTrailerMockMvc
            .perform(delete(ENTITY_API_URL_ID, trailer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Trailer> trailerList = trailerRepository.findAll();
        assertThat(trailerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
