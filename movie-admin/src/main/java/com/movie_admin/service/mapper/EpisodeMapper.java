package com.movie_admin.service.mapper;

import com.movie_admin.domain.*;
import com.movie_admin.service.dto.EpisodeDTO;
import org.mapstruct.*;


@Mapper(componentModel = "spring", uses = { MovieMapper.class })
public interface EpisodeMapper extends EntityMapper<EpisodeDTO, Episode> {
    @Mapping(target = "movie", source = "movie", qualifiedByName = "id")
    EpisodeDTO toDto(Episode s);
}
