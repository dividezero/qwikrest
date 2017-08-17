package com.hazlanrozaimi.qwikrest.service.impl;

import com.hazlanrozaimi.qwikrest.service.ArtefactService;
import com.hazlanrozaimi.qwikrest.domain.Artefact;
import com.hazlanrozaimi.qwikrest.repository.ArtefactRepository;
import com.hazlanrozaimi.qwikrest.repository.search.ArtefactSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Artefact.
 */
@Service
@Transactional
public class ArtefactServiceImpl implements ArtefactService{

    private final Logger log = LoggerFactory.getLogger(ArtefactServiceImpl.class);
    
    private final ArtefactRepository artefactRepository;

    private final ArtefactSearchRepository artefactSearchRepository;

    public ArtefactServiceImpl(ArtefactRepository artefactRepository, ArtefactSearchRepository artefactSearchRepository) {
        this.artefactRepository = artefactRepository;
        this.artefactSearchRepository = artefactSearchRepository;
    }

    /**
     * Save a artefact.
     *
     * @param artefact the entity to save
     * @return the persisted entity
     */
    @Override
    public Artefact save(Artefact artefact) {
        log.debug("Request to save Artefact : {}", artefact);
        Artefact result = artefactRepository.save(artefact);
        artefactSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the artefacts.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Artefact> findAll(Pageable pageable) {
        log.debug("Request to get all Artefacts");
        Page<Artefact> result = artefactRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one artefact by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Artefact findOne(Long id) {
        log.debug("Request to get Artefact : {}", id);
        Artefact artefact = artefactRepository.findOne(id);
        return artefact;
    }

    /**
     *  Delete the  artefact by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Artefact : {}", id);
        artefactRepository.delete(id);
        artefactSearchRepository.delete(id);
    }

    /**
     * Search for the artefact corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Artefact> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Artefacts for query {}", query);
        Page<Artefact> result = artefactSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
