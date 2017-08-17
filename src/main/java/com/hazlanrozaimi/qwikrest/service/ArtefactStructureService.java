package com.hazlanrozaimi.qwikrest.service;

import com.hazlanrozaimi.qwikrest.domain.ArtefactStructure;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ArtefactStructure.
 */
public interface ArtefactStructureService {

    /**
     * Save a artefactStructure.
     *
     * @param artefactStructure the entity to save
     * @return the persisted entity
     */
    ArtefactStructure save(ArtefactStructure artefactStructure);

    /**
     *  Get all the artefactStructures.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ArtefactStructure> findAll(Pageable pageable);

    /**
     *  Get the "id" artefactStructure.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ArtefactStructure findOne(Long id);

    /**
     *  Delete the "id" artefactStructure.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the artefactStructure corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ArtefactStructure> search(String query, Pageable pageable);
}
