package com.hazlanrozaimi.qwikrest.domain;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ArtefactData.
 */
@Entity
@Table(name = "artefact_data")
@Document(indexName = "artefactdata")
public class ArtefactData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_value")
    private String value;

    @ManyToOne(optional = false)
    @NotNull
    private Artefact artefact;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public ArtefactData value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Artefact getArtefact() {
        return artefact;
    }

    public ArtefactData artefact(Artefact artefact) {
        this.artefact = artefact;
        return this;
    }

    public void setArtefact(Artefact artefact) {
        this.artefact = artefact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ArtefactData artefactData = (ArtefactData) o;
        if (artefactData.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), artefactData.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ArtefactData{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            "}";
    }
}
