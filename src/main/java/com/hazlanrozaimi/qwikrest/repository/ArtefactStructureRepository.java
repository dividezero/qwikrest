package com.hazlanrozaimi.qwikrest.repository;

import com.hazlanrozaimi.qwikrest.domain.ArtefactStructure;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ArtefactStructure entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArtefactStructureRepository extends JpaRepository<ArtefactStructure,Long> {

}
