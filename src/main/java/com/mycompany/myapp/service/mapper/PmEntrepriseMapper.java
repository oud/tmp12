package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.PmEntreprise;
import com.mycompany.myapp.service.dto.PmEntrepriseDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PmEntreprise} and its DTO {@link PmEntrepriseDTO}.
 */
@Mapper(componentModel = "spring")
public interface PmEntrepriseMapper extends EntityMapper<PmEntrepriseDTO, PmEntreprise> {}
