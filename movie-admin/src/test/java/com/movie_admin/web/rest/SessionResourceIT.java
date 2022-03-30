package com.movie_admin.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.movie_admin.IntegrationTest;
import com.movie_admin.domain.Session;
import com.movie_admin.repository.SessionRepository;
import com.movie_admin.service.dto.SessionDTO;
import com.movie_admin.service.mapper.SessionMapper;
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
class SessionResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/sessions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private SessionMapper sessionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSessionMockMvc;

    private Session session;


    public static Session createEntity(EntityManager em) {
        Session session = new Session().name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION).createdDate(DEFAULT_CREATED_DATE);
        return session;
    }


    public static Session createUpdatedEntity(EntityManager em) {
        Session session = new Session().name(UPDATED_NAME).description(UPDATED_DESCRIPTION).createdDate(UPDATED_CREATED_DATE);
        return session;
    }

    @BeforeEach
    public void initTest() {
        session = createEntity(em);
    }

    @Test
    @Transactional
    void createSession() throws Exception {
        int databaseSizeBeforeCreate = sessionRepository.findAll().size();
        // Create the Session
        SessionDTO sessionDTO = sessionMapper.toDto(session);
        restSessionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sessionDTO)))
            .andExpect(status().isCreated());

        // Validate the Session in the database
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeCreate + 1);
        Session testSession = sessionList.get(sessionList.size() - 1);
        assertThat(testSession.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSession.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSession.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    void createSessionWithExistingId() throws Exception {
        // Create the Session with an existing ID
        session.setId(1L);
        SessionDTO sessionDTO = sessionMapper.toDto(session);

        int databaseSizeBeforeCreate = sessionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSessionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sessionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Session in the database
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = sessionRepository.findAll().size();
        // set the field null
        session.setName(null);

        // Create the Session, which fails.
        SessionDTO sessionDTO = sessionMapper.toDto(session);

        restSessionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sessionDTO)))
            .andExpect(status().isBadRequest());

        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = sessionRepository.findAll().size();
        // set the field null
        session.setDescription(null);

        // Create the Session, which fails.
        SessionDTO sessionDTO = sessionMapper.toDto(session);

        restSessionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sessionDTO)))
            .andExpect(status().isBadRequest());

        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = sessionRepository.findAll().size();
        // set the field null
        session.setCreatedDate(null);

        // Create the Session, which fails.
        SessionDTO sessionDTO = sessionMapper.toDto(session);

        restSessionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sessionDTO)))
            .andExpect(status().isBadRequest());

        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSessions() throws Exception {
        // Initialize the database
        sessionRepository.saveAndFlush(session);

        // Get all the sessionList
        restSessionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(session.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getSession() throws Exception {
        // Initialize the database
        sessionRepository.saveAndFlush(session);

        // Get the session
        restSessionMockMvc
            .perform(get(ENTITY_API_URL_ID, session.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(session.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSession() throws Exception {
        // Get the session
        restSessionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSession() throws Exception {
        // Initialize the database
        sessionRepository.saveAndFlush(session);

        int databaseSizeBeforeUpdate = sessionRepository.findAll().size();

        // Update the session
        Session updatedSession = sessionRepository.findById(session.getId()).get();
        // Disconnect from session so that the updates on updatedSession are not directly saved in db
        em.detach(updatedSession);
        updatedSession.name(UPDATED_NAME).description(UPDATED_DESCRIPTION).createdDate(UPDATED_CREATED_DATE);
        SessionDTO sessionDTO = sessionMapper.toDto(updatedSession);

        restSessionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sessionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sessionDTO))
            )
            .andExpect(status().isOk());

        // Validate the Session in the database
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeUpdate);
        Session testSession = sessionList.get(sessionList.size() - 1);
        assertThat(testSession.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSession.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSession.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingSession() throws Exception {
        int databaseSizeBeforeUpdate = sessionRepository.findAll().size();
        session.setId(count.incrementAndGet());

        // Create the Session
        SessionDTO sessionDTO = sessionMapper.toDto(session);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSessionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sessionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sessionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Session in the database
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSession() throws Exception {
        int databaseSizeBeforeUpdate = sessionRepository.findAll().size();
        session.setId(count.incrementAndGet());

        // Create the Session
        SessionDTO sessionDTO = sessionMapper.toDto(session);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSessionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sessionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Session in the database
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSession() throws Exception {
        int databaseSizeBeforeUpdate = sessionRepository.findAll().size();
        session.setId(count.incrementAndGet());

        // Create the Session
        SessionDTO sessionDTO = sessionMapper.toDto(session);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSessionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sessionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Session in the database
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSessionWithPatch() throws Exception {
        // Initialize the database
        sessionRepository.saveAndFlush(session);

        int databaseSizeBeforeUpdate = sessionRepository.findAll().size();

        // Update the session using partial update
        Session partialUpdatedSession = new Session();
        partialUpdatedSession.setId(session.getId());

        partialUpdatedSession.description(UPDATED_DESCRIPTION).createdDate(UPDATED_CREATED_DATE);

        restSessionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSession.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSession))
            )
            .andExpect(status().isOk());

        // Validate the Session in the database
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeUpdate);
        Session testSession = sessionList.get(sessionList.size() - 1);
        assertThat(testSession.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSession.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSession.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateSessionWithPatch() throws Exception {
        // Initialize the database
        sessionRepository.saveAndFlush(session);

        int databaseSizeBeforeUpdate = sessionRepository.findAll().size();

        // Update the session using partial update
        Session partialUpdatedSession = new Session();
        partialUpdatedSession.setId(session.getId());

        partialUpdatedSession.name(UPDATED_NAME).description(UPDATED_DESCRIPTION).createdDate(UPDATED_CREATED_DATE);

        restSessionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSession.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSession))
            )
            .andExpect(status().isOk());

        // Validate the Session in the database
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeUpdate);
        Session testSession = sessionList.get(sessionList.size() - 1);
        assertThat(testSession.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSession.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSession.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingSession() throws Exception {
        int databaseSizeBeforeUpdate = sessionRepository.findAll().size();
        session.setId(count.incrementAndGet());

        // Create the Session
        SessionDTO sessionDTO = sessionMapper.toDto(session);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSessionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sessionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sessionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Session in the database
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSession() throws Exception {
        int databaseSizeBeforeUpdate = sessionRepository.findAll().size();
        session.setId(count.incrementAndGet());

        // Create the Session
        SessionDTO sessionDTO = sessionMapper.toDto(session);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSessionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sessionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Session in the database
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSession() throws Exception {
        int databaseSizeBeforeUpdate = sessionRepository.findAll().size();
        session.setId(count.incrementAndGet());

        // Create the Session
        SessionDTO sessionDTO = sessionMapper.toDto(session);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSessionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(sessionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Session in the database
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSession() throws Exception {
        // Initialize the database
        sessionRepository.saveAndFlush(session);

        int databaseSizeBeforeDelete = sessionRepository.findAll().size();

        // Delete the session
        restSessionMockMvc
            .perform(delete(ENTITY_API_URL_ID, session.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
