package com.movie_admin.repository;

import com.movie_admin.domain.Movie;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {
    @Query(
        value = "select distinct movie from Movie movie left join fetch movie.categories",
        countQuery = "select count(distinct movie) from Movie movie"
    )
    Page<Movie> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct movie from Movie movie left join fetch movie.categories left join fetch movie.actors")
    List<Movie> findAllWithEagerRelationships();

    @Query("select movie from Movie movie left join fetch movie.categories left join fetch movie.actors where movie.id =:id")
    Optional<Movie> findOneWithEagerRelationships(@Param("id") Long id);

    @Query(
        value = "select distinct movie from Movie movie left join fetch movie.categories c where c.id =:categoryId",
        countQuery = "select count(distinct movie) from Movie movie"
    )
    Page<Movie> findAllByCategoryId(Pageable pageable, @Param("categoryId") Long categoryId);
}
