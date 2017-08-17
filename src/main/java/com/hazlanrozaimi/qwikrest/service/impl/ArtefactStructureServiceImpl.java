package com.hazlanrozaimi.qwikrest.service.impl;

import com.hazlanrozaimi.qwikrest.service.ArtefactStructureService;
import com.hazlanrozaimi.qwikrest.domain.ArtefactStructure;
import com.hazlanrozaimi.qwikrest.repository.ArtefactStructureRepository;
import com.hazlanrozaimi.qwikrest.repository.search.ArtefactStructureSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ArtefactStructure.
 */
@Service
@Transactional
public class ArtefactStructureServiceImpl implements ArtefactStructureService{

    private final Logger log = LoggerFactory.getLogger(ArtefactStructureServiceImpl.class);
    
    private final ArtefactStructureRepository artefactStructureRepository;

    private final ArtefactStructureSearchRepository artefactStructureSearchRepository;

    public ArtefactStructureServiceImpl(ArtefactStructureRepository artefactStructureRepository, ArtefactStructureSearchRepository artefactStructureSearchRepository) {
        this.artefactStructureRepository = artefactStructureRepository;
        this.artefactStructureSearchRepository = artefactStructureSearchRepository;
    }

    /**
     * Save a artefactStructure.
     *
     * @param artefactStructure the entity to save
     * @return the persisted entity
     */
    @Override
    public ArtefactStructure save(ArtefactStructure artefactStructure) {
        log.debug("Request to save ArtefactStructure : {}", artefactStructure);
        ArtefactStructure result = artefactStructureRepository.save(artefactStructure);
        artefactStructureSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the artefactStructures.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ArtefactStructure> findAll(Pageable pageable) {
        log.debug("Request to get all ArtefactStructures");
        Page<ArtefactStructure> result = artefactStructureRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one artefactStructure by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ArtefactStructure findOne(Long id) {
        log.debug("Request to get ArtefactStructure : {}", id);
        ArtefactStructure artefactStructure = artefactStructureRepository.findOne(id);
        return artefactStructure;
    }

    /**
     *  Delete the  artefactStructure by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ArtefactStructure : {}", id);
        artefactStructureRepository.delete(id);
        artefactStructureSearchRepository.delete(id);
    }

    /**
     * Search for the artefactStructure corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ArtefactStructure> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ArtefactStructures for query {}", query);
        Page<ArtefactStructure> result = artefactStructureSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
