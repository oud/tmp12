package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Adresse;
import com.mycompany.myapp.domain.PmEntreprise;
import com.mycompany.myapp.domain.PmEtablissement;
import com.mycompany.myapp.service.dto.AdresseDTO;
import com.mycompany.myapp.service.dto.PmEntrepriseDTO;
import com.mycompany.myapp.service.dto.PmEtablissementDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Adresse} and its DTO {@link AdresseDTO}.
 */
@Mapper(componentModel = "spring")
public interface AdresseMapper extends EntityMapper<AdresseDTO, Adresse> {
    @Mapping(target = "pmEntreprise", source = "pmEntreprise", qualifiedByName = "pmEntrepriseId")
    @Mapping(target = "pmEtablissement", source = "pmEtablissement", qualifiedByName = "pmEtablissementId")
    AdresseDTO toDto(Adresse s);

    @Named("pmEntrepriseId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PmEntrepriseDTO toDtoPmEntrepriseId(PmEntreprise pmEntreprise);

    @Named("pmEtablissementId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PmEtablissementDTO toDtoPmEtablissementId(PmEtablissement pmEtablissement);
}
