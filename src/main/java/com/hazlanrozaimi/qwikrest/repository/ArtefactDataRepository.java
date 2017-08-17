package com.hazlanrozaimi.qwikrest.repository;

import com.hazlanrozaimi.qwikrest.domain.ArtefactData;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ArtefactData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArtefactDataRepository extends JpaRepository<ArtefactData,Long> {

}
