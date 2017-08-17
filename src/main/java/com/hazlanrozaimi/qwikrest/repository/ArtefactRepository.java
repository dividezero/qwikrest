package com.hazlanrozaimi.qwikrest.repository;

import com.hazlanrozaimi.qwikrest.domain.Artefact;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Artefact entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArtefactRepository extends JpaRepository<Artefact,Long> {

}
