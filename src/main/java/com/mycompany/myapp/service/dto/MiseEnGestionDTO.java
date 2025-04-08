package com.mycompany.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.MiseEnGestion} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MiseEnGestionDTO implements Serializable {

    private Long id;

    @NotNull
    private String typeMEG;

    @NotNull
    private String codeOffre;

    @NotNull
    private LocalDate dateEffet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeMEG() {
        return typeMEG;
    }

    public void setTypeMEG(String typeMEG) {
        this.typeMEG = typeMEG;
    }

    public String getCodeOffre() {
        return codeOffre;
    }

    public void setCodeOffre(String codeOffre) {
        this.codeOffre = codeOffre;
    }

    public LocalDate getDateEffet() {
        return dateEffet;
    }

    public void setDateEffet(LocalDate dateEffet) {
        this.dateEffet = dateEffet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MiseEnGestionDTO)) {
            return false;
        }

        MiseEnGestionDTO miseEnGestionDTO = (MiseEnGestionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, miseEnGestionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MiseEnGestionDTO{" +
            "id=" + getId() +
            ", typeMEG='" + getTypeMEG() + "'" +
            ", codeOffre='" + getCodeOffre() + "'" +
            ", dateEffet='" + getDateEffet() + "'" +
            "}";
    }
}
