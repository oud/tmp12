package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.DemandeXRM;
import com.mycompany.myapp.domain.PmEtablissement;
import com.mycompany.myapp.service.dto.DemandeXRMDTO;
import com.mycompany.myapp.service.dto.PmEtablissementDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PmEtablissement} and its DTO {@link PmEtablissementDTO}.
 */
@Mapper(componentModel = "spring")
public interface PmEtablissementMapper extends EntityMapper<PmEtablissementDTO, PmEtablissement> {
    @Mapping(target = "demandeXRM", source = "demandeXRM", qualifiedByName = "demandeXRMId")
    PmEtablissementDTO toDto(PmEtablissement s);

    @Named("demandeXRMId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DemandeXRMDTO toDtoDemandeXRMId(DemandeXRM demandeXRM);
}
