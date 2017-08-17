package com.hazlanrozaimi.qwikrest.web.rest;

import com.hazlanrozaimi.qwikrest.QwikrestApp;

import com.hazlanrozaimi.qwikrest.domain.ArtefactData;
import com.hazlanrozaimi.qwikrest.domain.Artefact;
import com.hazlanrozaimi.qwikrest.repository.ArtefactDataRepository;
import com.hazlanrozaimi.qwikrest.service.ArtefactDataService;
import com.hazlanrozaimi.qwikrest.repository.search.ArtefactDataSearchRepository;
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
 * Test class for the ArtefactDataResource REST controller.
 *
 * @see ArtefactDataResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QwikrestApp.class)
public class ArtefactDataResourceIntTest {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private ArtefactDataRepository artefactDataRepository;

    @Autowired
    private ArtefactDataService artefactDataService;

    @Autowired
    private ArtefactDataSearchRepository artefactDataSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restArtefactDataMockMvc;

    private ArtefactData artefactData;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ArtefactDataResource artefactDataResource = new ArtefactDataResource(artefactDataService);
        this.restArtefactDataMockMvc = MockMvcBuilders.standaloneSetup(artefactDataResource)
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
    public static ArtefactData createEntity(EntityManager em) {
        ArtefactData artefactData = new ArtefactData()
            .value(DEFAULT_VALUE);
        // Add required entity
        Artefact artefact = ArtefactResourceIntTest.createEntity(em);
        em.persist(artefact);
        em.flush();
        artefactData.setArtefact(artefact);
        return artefactData;
    }

    @Before
    public void initTest() {
        artefactDataSearchRepository.deleteAll();
        artefactData = createEntity(em);
    }

    @Test
    @Transactional
    public void createArtefactData() throws Exception {
        int databaseSizeBeforeCreate = artefactDataRepository.findAll().size();

        // Create the ArtefactData
        restArtefactDataMockMvc.perform(post("/api/artefact-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(artefactData)))
            .andExpect(status().isCreated());

        // Validate the ArtefactData in the database
        List<ArtefactData> artefactDataList = artefactDataRepository.findAll();
        assertThat(artefactDataList).hasSize(databaseSizeBeforeCreate + 1);
        ArtefactData testArtefactData = artefactDataList.get(artefactDataList.size() - 1);
        assertThat(testArtefactData.getValue()).isEqualTo(DEFAULT_VALUE);

        // Validate the ArtefactData in Elasticsearch
        ArtefactData artefactDataEs = artefactDataSearchRepository.findOne(testArtefactData.getId());
        assertThat(artefactDataEs).isEqualToComparingFieldByField(testArtefactData);
    }

    @Test
    @Transactional
    public void createArtefactDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = artefactDataRepository.findAll().size();

        // Create the ArtefactData with an existing ID
        artefactData.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArtefactDataMockMvc.perform(post("/api/artefact-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(artefactData)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ArtefactData> artefactDataList = artefactDataRepository.findAll();
        assertThat(artefactDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllArtefactData() throws Exception {
        // Initialize the database
        artefactDataRepository.saveAndFlush(artefactData);

        // Get all the artefactDataList
        restArtefactDataMockMvc.perform(get("/api/artefact-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(artefactData.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getArtefactData() throws Exception {
        // Initialize the database
        artefactDataRepository.saveAndFlush(artefactData);

        // Get the artefactData
        restArtefactDataMockMvc.perform(get("/api/artefact-data/{id}", artefactData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(artefactData.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingArtefactData() throws Exception {
        // Get the artefactData
        restArtefactDataMockMvc.perform(get("/api/artefact-data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArtefactData() throws Exception {
        // Initialize the database
        artefactDataService.save(artefactData);

        int databaseSizeBeforeUpdate = artefactDataRepository.findAll().size();

        // Update the artefactData
        ArtefactData updatedArtefactData = artefactDataRepository.findOne(artefactData.getId());
        updatedArtefactData
            .value(UPDATED_VALUE);

        restArtefactDataMockMvc.perform(put("/api/artefact-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedArtefactData)))
            .andExpect(status().isOk());

        // Validate the ArtefactData in the database
        List<ArtefactData> artefactDataList = artefactDataRepository.findAll();
        assertThat(artefactDataList).hasSize(databaseSizeBeforeUpdate);
        ArtefactData testArtefactData = artefactDataList.get(artefactDataList.size() - 1);
        assertThat(testArtefactData.getValue()).isEqualTo(UPDATED_VALUE);

        // Validate the ArtefactData in Elasticsearch
        ArtefactData artefactDataEs = artefactDataSearchRepository.findOne(testArtefactData.getId());
        assertThat(artefactDataEs).isEqualToComparingFieldByField(testArtefactData);
    }

    @Test
    @Transactional
    public void updateNonExistingArtefactData() throws Exception {
        int databaseSizeBeforeUpdate = artefactDataRepository.findAll().size();

        // Create the ArtefactData

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restArtefactDataMockMvc.perform(put("/api/artefact-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(artefactData)))
            .andExpect(status().isCreated());

        // Validate the ArtefactData in the database
        List<ArtefactData> artefactDataList = artefactDataRepository.findAll();
        assertThat(artefactDataList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteArtefactData() throws Exception {
        // Initialize the database
        artefactDataService.save(artefactData);

        int databaseSizeBeforeDelete = artefactDataRepository.findAll().size();

        // Get the artefactData
        restArtefactDataMockMvc.perform(delete("/api/artefact-data/{id}", artefactData.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean artefactDataExistsInEs = artefactDataSearchRepository.exists(artefactData.getId());
        assertThat(artefactDataExistsInEs).isFalse();

        // Validate the database is empty
        List<ArtefactData> artefactDataList = artefactDataRepository.findAll();
        assertThat(artefactDataList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchArtefactData() throws Exception {
        // Initialize the database
        artefactDataService.save(artefactData);

        // Search the artefactData
        restArtefactDataMockMvc.perform(get("/api/_search/artefact-data?query=id:" + artefactData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(artefactData.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArtefactData.class);
        ArtefactData artefactData1 = new ArtefactData();
        artefactData1.setId(1L);
        ArtefactData artefactData2 = new ArtefactData();
        artefactData2.setId(artefactData1.getId());
        assertThat(artefactData1).isEqualTo(artefactData2);
        artefactData2.setId(2L);
        assertThat(artefactData1).isNotEqualTo(artefactData2);
        artefactData1.setId(null);
        assertThat(artefactData1).isNotEqualTo(artefactData2);
    }
}
