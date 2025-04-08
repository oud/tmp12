package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.MiseEnGestion;
import com.mycompany.myapp.service.dto.MiseEnGestionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MiseEnGestion} and its DTO {@link MiseEnGestionDTO}.
 */
@Mapper(componentModel = "spring")
public interface MiseEnGestionMapper extends EntityMapper<MiseEnGestionDTO, MiseEnGestion> {}
