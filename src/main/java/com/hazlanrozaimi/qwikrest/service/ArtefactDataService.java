package com.hazlanrozaimi.qwikrest.service;

import com.hazlanrozaimi.qwikrest.domain.ArtefactData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ArtefactData.
 */
public interface ArtefactDataService {

    /**
     * Save a artefactData.
     *
     * @param artefactData the entity to save
     * @return the persisted entity
     */
    ArtefactData save(ArtefactData artefactData);

    /**
     *  Get all the artefactData.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ArtefactData> findAll(Pageable pageable);

    /**
     *  Get the "id" artefactData.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ArtefactData findOne(Long id);

    /**
     *  Delete the "id" artefactData.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the artefactData corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ArtefactData> search(String query, Pageable pageable);
}
