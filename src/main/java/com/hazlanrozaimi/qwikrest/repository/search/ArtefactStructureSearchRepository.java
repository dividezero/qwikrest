package com.hazlanrozaimi.qwikrest.repository.search;

import com.hazlanrozaimi.qwikrest.domain.ArtefactStructure;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ArtefactStructure entity.
 */
public interface ArtefactStructureSearchRepository extends ElasticsearchRepository<ArtefactStructure, Long> {
}
