package com.movie_admin.service.mapper;

import com.movie_admin.domain.*;
import com.movie_admin.service.dto.ActorDTO;
import java.util.Set;
import org.mapstruct.*;


@Mapper(componentModel = "spring", uses = {})
public interface ActorMapper extends EntityMapper<ActorDTO, Actor> {
    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Set<ActorDTO> toDtoIdSet(Set<Actor> actor);
}
