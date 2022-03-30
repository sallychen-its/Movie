package com.movie_admin.service.mapper;

import com.movie_admin.domain.*;
import com.movie_admin.service.dto.SessionDTO;
import org.mapstruct.*;


@Mapper(componentModel = "spring", uses = {})
public interface SessionMapper extends EntityMapper<SessionDTO, Session> {
    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    SessionDTO toDtoName(Session session);
}
