package com.hazlanrozaimi.qwikrest.repository.search;

import com.hazlanrozaimi.qwikrest.domain.Artefact;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Artefact entity.
 */
public interface ArtefactSearchRepository extends ElasticsearchRepository<Artefact, Long> {
}
