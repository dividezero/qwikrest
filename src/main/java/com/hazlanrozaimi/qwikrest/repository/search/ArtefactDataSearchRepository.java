package com.hazlanrozaimi.qwikrest.repository.search;

import com.hazlanrozaimi.qwikrest.domain.ArtefactData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ArtefactData entity.
 */
public interface ArtefactDataSearchRepository extends ElasticsearchRepository<ArtefactData, Long> {
}
