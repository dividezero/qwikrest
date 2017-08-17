package com.hazlanrozaimi.qwikrest.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hazlanrozaimi.qwikrest.domain.Artefact;
import com.hazlanrozaimi.qwikrest.service.ArtefactService;
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
 * REST controller for managing Artefact.
 */
@RestController
@RequestMapping("/api")
public class ArtefactResource {

    private final Logger log = LoggerFactory.getLogger(ArtefactResource.class);

    private static final String ENTITY_NAME = "artefact";
        
    private final ArtefactService artefactService;

    public ArtefactResource(ArtefactService artefactService) {
        this.artefactService = artefactService;
    }

    /**
     * POST  /artefacts : Create a new artefact.
     *
     * @param artefact the artefact to create
     * @return the ResponseEntity with status 201 (Created) and with body the new artefact, or with status 400 (Bad Request) if the artefact has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/artefacts")
    @Timed
    public ResponseEntity<Artefact> createArtefact(@Valid @RequestBody Artefact artefact) throws URISyntaxException {
        log.debug("REST request to save Artefact : {}", artefact);
        if (artefact.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new artefact cannot already have an ID")).body(null);
        }
        Artefact result = artefactService.save(artefact);
        return ResponseEntity.created(new URI("/api/artefacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /artefacts : Updates an existing artefact.
     *
     * @param artefact the artefact to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated artefact,
     * or with status 400 (Bad Request) if the artefact is not valid,
     * or with status 500 (Internal Server Error) if the artefact couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/artefacts")
    @Timed
    public ResponseEntity<Artefact> updateArtefact(@Valid @RequestBody Artefact artefact) throws URISyntaxException {
        log.debug("REST request to update Artefact : {}", artefact);
        if (artefact.getId() == null) {
            return createArtefact(artefact);
        }
        Artefact result = artefactService.save(artefact);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, artefact.getId().toString()))
            .body(result);
    }

    /**
     * GET  /artefacts : get all the artefacts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of artefacts in body
     */
    @GetMapping("/artefacts")
    @Timed
    public ResponseEntity<List<Artefact>> getAllArtefacts(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Artefacts");
        Page<Artefact> page = artefactService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/artefacts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /artefacts/:id : get the "id" artefact.
     *
     * @param id the id of the artefact to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the artefact, or with status 404 (Not Found)
     */
    @GetMapping("/artefacts/{id}")
    @Timed
    public ResponseEntity<Artefact> getArtefact(@PathVariable Long id) {
        log.debug("REST request to get Artefact : {}", id);
        Artefact artefact = artefactService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(artefact));
    }

    /**
     * DELETE  /artefacts/:id : delete the "id" artefact.
     *
     * @param id the id of the artefact to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/artefacts/{id}")
    @Timed
    public ResponseEntity<Void> deleteArtefact(@PathVariable Long id) {
        log.debug("REST request to delete Artefact : {}", id);
        artefactService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/artefacts?query=:query : search for the artefact corresponding
     * to the query.
     *
     * @param query the query of the artefact search 
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/artefacts")
    @Timed
    public ResponseEntity<List<Artefact>> searchArtefacts(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Artefacts for query {}", query);
        Page<Artefact> page = artefactService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/artefacts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
