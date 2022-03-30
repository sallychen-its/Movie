package com.movie_admin.service.mapper;

import com.movie_admin.domain.*;
import com.movie_admin.service.dto.TrailerDTO;
import org.mapstruct.*;


@Mapper(componentModel = "spring", uses = { MovieMapper.class })
public interface TrailerMapper extends EntityMapper<TrailerDTO, Trailer> {
    @Mapping(target = "movie", source = "movie", qualifiedByName = "id")
    TrailerDTO toDto(Trailer s);
}
