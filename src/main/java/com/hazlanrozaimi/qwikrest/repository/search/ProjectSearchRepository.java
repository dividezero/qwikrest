package com.hazlanrozaimi.qwikrest.repository.search;

import com.hazlanrozaimi.qwikrest.domain.Project;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Project entity.
 */
public interface ProjectSearchRepository extends ElasticsearchRepository<Project, Long> {
}
