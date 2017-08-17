package com.hazlanrozaimi.qwikrest.domain;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ArtefactStructure.
 */
@Entity
@Table(name = "artefact_structure")
@Document(indexName = "artefactstructure")
public class ArtefactStructure implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @Size(max = 255)
    @Column(name = "description", length = 255)
    private String description;

    @Size(max = 20)
    @Column(name = "data_type", length = 20)
    private String dataType;

    @Column(name = "length")
    private Integer length;

    @Column(name = "nullable")
    private Boolean nullable;

    @ManyToOne(optional = false)
    @NotNull
    private Artefact artefact;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ArtefactStructure name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public ArtefactStructure description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDataType() {
        return dataType;
    }

    public ArtefactStructure dataType(String dataType) {
        this.dataType = dataType;
        return this;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Integer getLength() {
        return length;
    }

    public ArtefactStructure length(Integer length) {
        this.length = length;
        return this;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Boolean isNullable() {
        return nullable;
    }

    public ArtefactStructure nullable(Boolean nullable) {
        this.nullable = nullable;
        return this;
    }

    public void setNullable(Boolean nullable) {
        this.nullable = nullable;
    }

    public Artefact getArtefact() {
        return artefact;
    }

    public ArtefactStructure artefact(Artefact artefact) {
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
        ArtefactStructure artefactStructure = (ArtefactStructure) o;
        if (artefactStructure.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), artefactStructure.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ArtefactStructure{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", dataType='" + getDataType() + "'" +
            ", length='" + getLength() + "'" +
            ", nullable='" + isNullable() + "'" +
            "}";
    }
}
