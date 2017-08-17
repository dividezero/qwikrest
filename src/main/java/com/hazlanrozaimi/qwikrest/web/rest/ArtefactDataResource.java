package com.hazlanrozaimi.qwikrest.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hazlanrozaimi.qwikrest.domain.ArtefactData;
import com.hazlanrozaimi.qwikrest.service.ArtefactDataService;
import com.hazlanrozaimi.qwikrest.web.rest.util.HeaderUtil;
import com.hazlanrozaimi.qwikrest.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing ArtefactData.
 */
@RestController
@RequestMapping("/api")
public class ArtefactDataResource {

    private final Logger log = LoggerFactory.getLogger(ArtefactDataResource.class);

    private static final String ENTITY_NAME = "artefactData";
        
    private final ArtefactDataService artefactDataService;

    public ArtefactDataResource(ArtefactDataService artefactDataService) {
        this.artefactDataService = artefactDataService;
    }

    /**
     * POST  /artefact-data : Create a new artefactData.
     *
     * @param artefactData the artefactData to create
     * @return the ResponseEntity with status 201 (Created) and with body the new artefactData, or with status 400 (Bad Request) if the artefactData has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/artefact-data")
    @Timed
    public ResponseEntity<ArtefactData> createArtefactData(@Valid @RequestBody ArtefactData artefactData) throws URISyntaxException {
        log.debug("REST request to save ArtefactData : {}", artefactData);
        if (artefactData.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new artefactData cannot already have an ID")).body(null);
        }
        ArtefactData result = artefactDataService.save(artefactData);
        return ResponseEntity.created(new URI("/api/artefact-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /artefact-data : Updates an existing artefactData.
     *
     * @param artefactData the artefactData to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated artefactData,
     * or with status 400 (Bad Request) if the artefactData is not valid,
     * or with status 500 (Internal Server Error) if the artefactData couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/artefact-data")
    @Timed
    public ResponseEntity<ArtefactData> updateArtefactData(@Valid @RequestBody ArtefactData artefactData) throws URISyntaxException {
        log.debug("REST request to update ArtefactData : {}", artefactData);
        if (artefactData.getId() == null) {
            return createArtefactData(artefactData);
        }
        ArtefactData result = artefactDataService.save(artefactData);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, artefactData.getId().toString()))
            .body(result);
    }

    /**
     * GET  /artefact-data : get all the artefactData.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of artefactData in body
     */
    @GetMapping("/artefact-data")
    @Timed
    public ResponseEntity<List<ArtefactData>> getAllArtefactData(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of ArtefactData");
        Page<ArtefactData> page = artefactDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/artefact-data");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /artefact-data/:id : get the "id" artefactData.
     *
     * @param id the id of the artefactData to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the artefactData, or with status 404 (Not Found)
     */
    @GetMapping("/artefact-data/{id}")
    @Timed
    public ResponseEntity<ArtefactData> getArtefactData(@PathVariable Long id) {
        log.debug("REST request to get ArtefactData : {}", id);
        ArtefactData artefactData = artefactDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(artefactData));
    }

    /**
     * DELETE  /artefact-data/:id : delete the "id" artefactData.
     *
     * @param id the id of the artefactData to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/artefact-data/{id}")
    @Timed
    public ResponseEntity<Void> deleteArtefactData(@PathVariable Long id) {
        log.debug("REST request to delete ArtefactData : {}", id);
        artefactDataService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/artefact-data?query=:query : search for the artefactData corresponding
     * to the query.
     *
     * @param query the query of the artefactData search 
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/artefact-data")
    @Timed
    public ResponseEntity<List<ArtefactData>> searchArtefactData(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of ArtefactData for query {}", query);
        Page<ArtefactData> page = artefactDataService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/artefact-data");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
