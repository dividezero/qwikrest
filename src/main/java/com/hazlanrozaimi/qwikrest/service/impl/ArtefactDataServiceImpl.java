package com.hazlanrozaimi.qwikrest.service.impl;

import com.hazlanrozaimi.qwikrest.service.ArtefactDataService;
import com.hazlanrozaimi.qwikrest.domain.ArtefactData;
import com.hazlanrozaimi.qwikrest.repository.ArtefactDataRepository;
import com.hazlanrozaimi.qwikrest.repository.search.ArtefactDataSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ArtefactData.
 */
@Service
@Transactional
public class ArtefactDataServiceImpl implements ArtefactDataService{

    private final Logger log = LoggerFactory.getLogger(ArtefactDataServiceImpl.class);
    
    private final ArtefactDataRepository artefactDataRepository;

    private final ArtefactDataSearchRepository artefactDataSearchRepository;

    public ArtefactDataServiceImpl(ArtefactDataRepository artefactDataRepository, ArtefactDataSearchRepository artefactDataSearchRepository) {
        this.artefactDataRepository = artefactDataRepository;
        this.artefactDataSearchRepository = artefactDataSearchRepository;
    }

    /**
     * Save a artefactData.
     *
     * @param artefactData the entity to save
     * @return the persisted entity
     */
    @Override
    public ArtefactData save(ArtefactData artefactData) {
        log.debug("Request to save ArtefactData : {}", artefactData);
        ArtefactData result = artefactDataRepository.save(artefactData);
        artefactDataSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the artefactData.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ArtefactData> findAll(Pageable pageable) {
        log.debug("Request to get all ArtefactData");
        Page<ArtefactData> result = artefactDataRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one artefactData by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ArtefactData findOne(Long id) {
        log.debug("Request to get ArtefactData : {}", id);
        ArtefactData artefactData = artefactDataRepository.findOne(id);
        return artefactData;
    }

    /**
     *  Delete the  artefactData by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ArtefactData : {}", id);
        artefactDataRepository.delete(id);
        artefactDataSearchRepository.delete(id);
    }

    /**
     * Search for the artefactData corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ArtefactData> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ArtefactData for query {}", query);
        Page<ArtefactData> result = artefactDataSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
