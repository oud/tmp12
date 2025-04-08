package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.PmEntreprise;
import com.mycompany.myapp.domain.PmEtablissement;
import com.mycompany.myapp.domain.Telephone;
import com.mycompany.myapp.service.dto.PmEntrepriseDTO;
import com.mycompany.myapp.service.dto.PmEtablissementDTO;
import com.mycompany.myapp.service.dto.TelephoneDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Telephone} and its DTO {@link TelephoneDTO}.
 */
@Mapper(componentModel = "spring")
public interface TelephoneMapper extends EntityMapper<TelephoneDTO, Telephone> {
    @Mapping(target = "pmEntreprise", source = "pmEntreprise", qualifiedByName = "pmEntrepriseId")
    @Mapping(target = "pmEtablissement", source = "pmEtablissement", qualifiedByName = "pmEtablissementId")
    TelephoneDTO toDto(Telephone s);

    @Named("pmEntrepriseId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PmEntrepriseDTO toDtoPmEntrepriseId(PmEntreprise pmEntreprise);

    @Named("pmEtablissementId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PmEtablissementDTO toDtoPmEtablissementId(PmEtablissement pmEtablissement);
}
