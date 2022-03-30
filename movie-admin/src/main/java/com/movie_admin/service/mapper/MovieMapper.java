package com.movie_admin.service.mapper;

import com.movie_admin.domain.*;
import com.movie_admin.service.dto.MovieDTO;
import java.util.Set;
import org.mapstruct.*;


@Mapper(componentModel = "spring", uses = { CategoryMapper.class, ActorMapper.class, SessionMapper.class })
public interface MovieMapper extends EntityMapper<MovieDTO, Movie> {

    @Mapping(target = "categories", source = "categories", qualifiedByName = "nameSet")
    @Mapping(target = "actors", source = "actors", qualifiedByName = "idSet")
    @Mapping(target = "session", source = "session", qualifiedByName = "name")
    MovieDTO toDto(Movie s);

    @Named("withoutCategory")
    @Mapping(target = "actors", source = "actors", qualifiedByName = "idSet")
    @Mapping(target = "session", source = "session", qualifiedByName = "name")
    @Mapping(target = "categories", source = "categories", ignore = true)
    MovieDTO toDtoWithoutCategory(Movie movie);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MovieDTO toDtoId(Movie movie);

    @Mapping(target = "removeCategory", ignore = true)
    @Mapping(target = "removeActor", ignore = true)
    Movie toEntity(MovieDTO movieDTO);
}
