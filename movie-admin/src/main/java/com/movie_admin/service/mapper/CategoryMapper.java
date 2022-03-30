package com.movie_admin.service.mapper;

import com.movie_admin.domain.*;
import com.movie_admin.service.dto.CategoryDTO;
import java.util.Set;
import org.mapstruct.*;


@Mapper(componentModel = "spring", uses = { MovieMapper.class })
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category> {
    @Named("nameSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    Set<CategoryDTO> toDtoNameSet(Set<Category> category);

    @Mapping(target = "movies", source = "movies", ignore = true)
    CategoryDTO toDto(Category category);

    @Named("moviesSet")
    @Mapping(target = "movies", source = "movies", qualifiedByName = "withoutCategory")
    CategoryDTO toDtoWithMovie(Category category);
}
