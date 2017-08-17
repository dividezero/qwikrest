package com.hazlanrozaimi.qwikrest.service;

import com.hazlanrozaimi.qwikrest.domain.Artefact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Artefact.
 */
public interface ArtefactService {

    /**
     * Save a artefact.
     *
     * @param artefact the entity to save
     * @return the persisted entity
     */
    Artefact save(Artefact artefact);

    /**
     *  Get all the artefacts.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Artefact> findAll(Pageable pageable);

    /**
     *  Get the "id" artefact.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Artefact findOne(Long id);

    /**
     *  Delete the "id" artefact.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the artefact corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Artefact> search(String query, Pageable pageable);
}
