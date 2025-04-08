package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.DemandeXRM;
import com.mycompany.myapp.domain.MiseEnGestion;
import com.mycompany.myapp.domain.PmEntreprise;
import com.mycompany.myapp.service.dto.DemandeXRMDTO;
import com.mycompany.myapp.service.dto.MiseEnGestionDTO;
import com.mycompany.myapp.service.dto.PmEntrepriseDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DemandeXRM} and its DTO {@link DemandeXRMDTO}.
 */
@Mapper(componentModel = "spring")
public interface DemandeXRMMapper extends EntityMapper<DemandeXRMDTO, DemandeXRM> {
    @Mapping(target = "pmEntreprise", source = "pmEntreprise", qualifiedByName = "pmEntrepriseId")
    @Mapping(target = "miseEnGestion", source = "miseEnGestion", qualifiedByName = "miseEnGestionId")
    DemandeXRMDTO toDto(DemandeXRM s);

    @Named("pmEntrepriseId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PmEntrepriseDTO toDtoPmEntrepriseId(PmEntreprise pmEntreprise);

    @Named("miseEnGestionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MiseEnGestionDTO toDtoMiseEnGestionId(MiseEnGestion miseEnGestion);
}
