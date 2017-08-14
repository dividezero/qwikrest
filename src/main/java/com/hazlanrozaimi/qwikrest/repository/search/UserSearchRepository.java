package com.hazlanrozaimi.qwikrest.repository.search;

import com.hazlanrozaimi.qwikrest.domain.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the User entity.
 */
public interface UserSearchRepository extends ElasticsearchRepository<User, Long> {
}
