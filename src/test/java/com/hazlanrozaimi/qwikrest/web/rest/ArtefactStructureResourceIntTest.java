package com.hazlanrozaimi.qwikrest.web.rest;

import com.hazlanrozaimi.qwikrest.QwikrestApp;

import com.hazlanrozaimi.qwikrest.domain.ArtefactStructure;
import com.hazlanrozaimi.qwikrest.domain.Artefact;
import com.hazlanrozaimi.qwikrest.repository.ArtefactStructureRepository;
import com.hazlanrozaimi.qwikrest.service.ArtefactStructureService;
import com.hazlanrozaimi.qwikrest.repository.search.ArtefactStructureSearchRepository;
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
 * Test class for the ArtefactStructureResource REST controller.
 *
 * @see ArtefactStructureResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QwikrestApp.class)
public class ArtefactStructureResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DATA_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_DATA_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_LENGTH = 1;
    private static final Integer UPDATED_LENGTH = 2;

    private static final Boolean DEFAULT_NULLABLE = false;
    private static final Boolean UPDATED_NULLABLE = true;

    @Autowired
    private ArtefactStructureRepository artefactStructureRepository;

    @Autowired
    private ArtefactStructureService artefactStructureService;

    @Autowired
    private ArtefactStructureSearchRepository artefactStructureSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restArtefactStructureMockMvc;

    private ArtefactStructure artefactStructure;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ArtefactStructureResource artefactStructureResource = new ArtefactStructureResource(artefactStructureService);
        this.restArtefactStructureMockMvc = MockMvcBuilders.standaloneSetup(artefactStructureResource)
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
    public static ArtefactStructure createEntity(EntityManager em) {
        ArtefactStructure artefactStructure = new ArtefactStructure()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .dataType(DEFAULT_DATA_TYPE)
            .length(DEFAULT_LENGTH)
            .nullable(DEFAULT_NULLABLE);
        // Add required entity
        Artefact artefact = ArtefactResourceIntTest.createEntity(em);
        em.persist(artefact);
        em.flush();
        artefactStructure.setArtefact(artefact);
        return artefactStructure;
    }

    @Before
    public void initTest() {
        artefactStructureSearchRepository.deleteAll();
        artefactStructure = createEntity(em);
    }

    @Test
    @Transactional
    public void createArtefactStructure() throws Exception {
        int databaseSizeBeforeCreate = artefactStructureRepository.findAll().size();

        // Create the ArtefactStructure
        restArtefactStructureMockMvc.perform(post("/api/artefact-structures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(artefactStructure)))
            .andExpect(status().isCreated());

        // Validate the ArtefactStructure in the database
        List<ArtefactStructure> artefactStructureList = artefactStructureRepository.findAll();
        assertThat(artefactStructureList).hasSize(databaseSizeBeforeCreate + 1);
        ArtefactStructure testArtefactStructure = artefactStructureList.get(artefactStructureList.size() - 1);
        assertThat(testArtefactStructure.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testArtefactStructure.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testArtefactStructure.getDataType()).isEqualTo(DEFAULT_DATA_TYPE);
        assertThat(testArtefactStructure.getLength()).isEqualTo(DEFAULT_LENGTH);
        assertThat(testArtefactStructure.isNullable()).isEqualTo(DEFAULT_NULLABLE);

        // Validate the ArtefactStructure in Elasticsearch
        ArtefactStructure artefactStructureEs = artefactStructureSearchRepository.findOne(testArtefactStructure.getId());
        assertThat(artefactStructureEs).isEqualToComparingFieldByField(testArtefactStructure);
    }

    @Test
    @Transactional
    public void createArtefactStructureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = artefactStructureRepository.findAll().size();

        // Create the ArtefactStructure with an existing ID
        artefactStructure.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArtefactStructureMockMvc.perform(post("/api/artefact-structures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(artefactStructure)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ArtefactStructure> artefactStructureList = artefactStructureRepository.findAll();
        assertThat(artefactStructureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = artefactStructureRepository.findAll().size();
        // set the field null
        artefactStructure.setName(null);

        // Create the ArtefactStructure, which fails.

        restArtefactStructureMockMvc.perform(post("/api/artefact-structures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(artefactStructure)))
            .andExpect(status().isBadRequest());

        List<ArtefactStructure> artefactStructureList = artefactStructureRepository.findAll();
        assertThat(artefactStructureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllArtefactStructures() throws Exception {
        // Initialize the database
        artefactStructureRepository.saveAndFlush(artefactStructure);

        // Get all the artefactStructureList
        restArtefactStructureMockMvc.perform(get("/api/artefact-structures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(artefactStructure.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].dataType").value(hasItem(DEFAULT_DATA_TYPE.toString())))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH)))
            .andExpect(jsonPath("$.[*].nullable").value(hasItem(DEFAULT_NULLABLE.booleanValue())));
    }

    @Test
    @Transactional
    public void getArtefactStructure() throws Exception {
        // Initialize the database
        artefactStructureRepository.saveAndFlush(artefactStructure);

        // Get the artefactStructure
        restArtefactStructureMockMvc.perform(get("/api/artefact-structures/{id}", artefactStructure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(artefactStructure.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.dataType").value(DEFAULT_DATA_TYPE.toString()))
            .andExpect(jsonPath("$.length").value(DEFAULT_LENGTH))
            .andExpect(jsonPath("$.nullable").value(DEFAULT_NULLABLE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingArtefactStructure() throws Exception {
        // Get the artefactStructure
        restArtefactStructureMockMvc.perform(get("/api/artefact-structures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArtefactStructure() throws Exception {
        // Initialize the database
        artefactStructureService.save(artefactStructure);

        int databaseSizeBeforeUpdate = artefactStructureRepository.findAll().size();

        // Update the artefactStructure
        ArtefactStructure updatedArtefactStructure = artefactStructureRepository.findOne(artefactStructure.getId());
        updatedArtefactStructure
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .dataType(UPDATED_DATA_TYPE)
            .length(UPDATED_LENGTH)
            .nullable(UPDATED_NULLABLE);

        restArtefactStructureMockMvc.perform(put("/api/artefact-structures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedArtefactStructure)))
            .andExpect(status().isOk());

        // Validate the ArtefactStructure in the database
        List<ArtefactStructure> artefactStructureList = artefactStructureRepository.findAll();
        assertThat(artefactStructureList).hasSize(databaseSizeBeforeUpdate);
        ArtefactStructure testArtefactStructure = artefactStructureList.get(artefactStructureList.size() - 1);
        assertThat(testArtefactStructure.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testArtefactStructure.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testArtefactStructure.getDataType()).isEqualTo(UPDATED_DATA_TYPE);
        assertThat(testArtefactStructure.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testArtefactStructure.isNullable()).isEqualTo(UPDATED_NULLABLE);

        // Validate the ArtefactStructure in Elasticsearch
        ArtefactStructure artefactStructureEs = artefactStructureSearchRepository.findOne(testArtefactStructure.getId());
        assertThat(artefactStructureEs).isEqualToComparingFieldByField(testArtefactStructure);
    }

    @Test
    @Transactional
    public void updateNonExistingArtefactStructure() throws Exception {
        int databaseSizeBeforeUpdate = artefactStructureRepository.findAll().size();

        // Create the ArtefactStructure

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restArtefactStructureMockMvc.perform(put("/api/artefact-structures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(artefactStructure)))
            .andExpect(status().isCreated());

        // Validate the ArtefactStructure in the database
        List<ArtefactStructure> artefactStructureList = artefactStructureRepository.findAll();
        assertThat(artefactStructureList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteArtefactStructure() throws Exception {
        // Initialize the database
        artefactStructureService.save(artefactStructure);

        int databaseSizeBeforeDelete = artefactStructureRepository.findAll().size();

        // Get the artefactStructure
        restArtefactStructureMockMvc.perform(delete("/api/artefact-structures/{id}", artefactStructure.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean artefactStructureExistsInEs = artefactStructureSearchRepository.exists(artefactStructure.getId());
        assertThat(artefactStructureExistsInEs).isFalse();

        // Validate the database is empty
        List<ArtefactStructure> artefactStructureList = artefactStructureRepository.findAll();
        assertThat(artefactStructureList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchArtefactStructure() throws Exception {
        // Initialize the database
        artefactStructureService.save(artefactStructure);

        // Search the artefactStructure
        restArtefactStructureMockMvc.perform(get("/api/_search/artefact-structures?query=id:" + artefactStructure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(artefactStructure.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].dataType").value(hasItem(DEFAULT_DATA_TYPE.toString())))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH)))
            .andExpect(jsonPath("$.[*].nullable").value(hasItem(DEFAULT_NULLABLE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArtefactStructure.class);
        ArtefactStructure artefactStructure1 = new ArtefactStructure();
        artefactStructure1.setId(1L);
        ArtefactStructure artefactStructure2 = new ArtefactStructure();
        artefactStructure2.setId(artefactStructure1.getId());
        assertThat(artefactStructure1).isEqualTo(artefactStructure2);
        artefactStructure2.setId(2L);
        assertThat(artefactStructure1).isNotEqualTo(artefactStructure2);
        artefactStructure1.setId(null);
        assertThat(artefactStructure1).isNotEqualTo(artefactStructure2);
    }
}
