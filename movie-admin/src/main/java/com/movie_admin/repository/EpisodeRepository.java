package com.movie_admin.repository;

import com.movie_admin.domain.Episode;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


@SuppressWarnings("unused")
@Repository
public interface EpisodeRepository extends JpaRepository<Episode, Long> {}
