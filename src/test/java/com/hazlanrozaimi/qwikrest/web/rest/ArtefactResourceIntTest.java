package com.hazlanrozaimi.qwikrest.web.rest;

import com.hazlanrozaimi.qwikrest.QwikrestApp;

import com.hazlanrozaimi.qwikrest.domain.Artefact;
import com.hazlanrozaimi.qwikrest.domain.Project;
import com.hazlanrozaimi.qwikrest.repository.ArtefactRepository;
import com.hazlanrozaimi.qwikrest.service.ArtefactService;
import com.hazlanrozaimi.qwikrest.repository.search.ArtefactSearchRepository;
import com.hazlanrozaimi.qwikrest.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ArtefactResource REST controller.
 *
 * @see ArtefactResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QwikrestApp.class)
public class ArtefactResourceIntTest {

    private static final String DEFAULT_NAME = "//";
    private static final String UPDATED_NAME = "/</";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ArtefactRepository artefactRepository;

    @Autowired
    private ArtefactService artefactService;

    @Autowired
    private ArtefactSearchRepository artefactSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restArtefactMockMvc;

    private Artefact artefact;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ArtefactResource artefactResource = new ArtefactResource(artefactService);
        this.restArtefactMockMvc = MockMvcBuilders.standaloneSetup(artefactResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Artefact createEntity(EntityManager em) {
        Artefact artefact = new Artefact()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        // Add required entity
        Project project = ProjectResourceIntTest.createEntity(em);
        em.persist(project);
        em.flush();
        artefact.setProject(project);
        return artefact;
    }

    @Before
    public void initTest() {
        artefactSearchRepository.deleteAll();
        artefact = createEntity(em);
    }

    @Test
    @Transactional
    public void createArtefact() throws Exception {
        int databaseSizeBeforeCreate = artefactRepository.findAll().size();

        // Create the Artefact
        restArtefactMockMvc.perform(post("/api/artefacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(artefact)))
            .andExpect(status().isCreated());

        // Validate the Artefact in the database
        List<Artefact> artefactList = artefactRepository.findAll();
        assertThat(artefactList).hasSize(databaseSizeBeforeCreate + 1);
        Artefact testArtefact = artefactList.get(artefactList.size() - 1);
        assertThat(testArtefact.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testArtefact.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the Artefact in Elasticsearch
        Artefact artefactEs = artefactSearchRepository.findOne(testArtefact.getId());
        assertThat(artefactEs).isEqualToComparingFieldByField(testArtefact);
    }

    @Test
    @Transactional
    public void createArtefactWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = artefactRepository.findAll().size();

        // Create the Artefact with an existing ID
        artefact.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArtefactMockMvc.perform(post("/api/artefacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(artefact)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Artefact> artefactList = artefactRepository.findAll();
        assertThat(artefactList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = artefactRepository.findAll().size();
        // set the field null
        artefact.setName(null);

        // Create the Artefact, which fails.

        restArtefactMockMvc.perform(post("/api/artefacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(artefact)))
            .andExpect(status().isBadRequest());

        List<Artefact> artefactList = artefactRepository.findAll();
        assertThat(artefactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllArtefacts() throws Exception {
        // Initialize the database
        artefactRepository.saveAndFlush(artefact);

        // Get all the artefactList
        restArtefactMockMvc.perform(get("/api/artefacts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(artefact.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getArtefact() throws Exception {
        // Initialize the database
        artefactRepository.saveAndFlush(artefact);

        // Get the artefact
        restArtefactMockMvc.perform(get("/api/artefacts/{id}", artefact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(artefact.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingArtefact() throws Exception {
        // Get the artefact
        restArtefactMockMvc.perform(get("/api/artefacts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArtefact() throws Exception {
        // Initialize the database
        artefactService.save(artefact);

        int databaseSizeBeforeUpdate = artefactRepository.findAll().size();

        // Update the artefact
        Artefact updatedArtefact = artefactRepository.findOne(artefact.getId());
        updatedArtefact
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restArtefactMockMvc.perform(put("/api/artefacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedArtefact)))
            .andExpect(status().isOk());

        // Validate the Artefact in the database
        List<Artefact> artefactList = artefactRepository.findAll();
        assertThat(artefactList).hasSize(databaseSizeBeforeUpdate);
        Artefact testArtefact = artefactList.get(artefactList.size() - 1);
        assertThat(testArtefact.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testArtefact.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the Artefact in Elasticsearch
        Artefact artefactEs = artefactSearchRepository.findOne(testArtefact.getId());
        assertThat(artefactEs).isEqualToComparingFieldByField(testArtefact);
    }

    @Test
    @Transactional
    public void updateNonExistingArtefact() throws Exception {
        int databaseSizeBeforeUpdate = artefactRepository.findAll().size();

        // Create the Artefact

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restArtefactMockMvc.perform(put("/api/artefacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(artefact)))
            .andExpect(status().isCreated());

        // Validate the Artefact in the database
        List<Artefact> artefactList = artefactRepository.findAll();
        assertThat(artefactList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteArtefact() throws Exception {
        // Initialize the database
        artefactService.save(artefact);

        int databaseSizeBeforeDelete = artefactRepository.findAll().size();

        // Get the artefact
        restArtefactMockMvc.perform(delete("/api/artefacts/{id}", artefact.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean artefactExistsInEs = artefactSearchRepository.exists(artefact.getId());
        assertThat(artefactExistsInEs).isFalse();

        // Validate the database is empty
        List<Artefact> artefactList = artefactRepository.findAll();
        assertThat(artefactList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchArtefact() throws Exception {
        // Initialize the database
        artefactService.save(artefact);

        // Search the artefact
        restArtefactMockMvc.perform(get("/api/_search/artefacts?query=id:" + artefact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(artefact.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Artefact.class);
        Artefact artefact1 = new Artefact();
        artefact1.setId(1L);
        Artefact artefact2 = new Artefact();
        artefact2.setId(artefact1.getId());
        assertThat(artefact1).isEqualTo(artefact2);
        artefact2.setId(2L);
        assertThat(artefact1).isNotEqualTo(artefact2);
        artefact1.setId(null);
        assertThat(artefact1).isNotEqualTo(artefact2);
    }
}
