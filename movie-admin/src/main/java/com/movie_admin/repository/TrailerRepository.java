package com.movie_admin.repository;

import com.movie_admin.domain.Trailer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


@SuppressWarnings("unused")
@Repository
public interface TrailerRepository extends JpaRepository<Trailer, Long> {}
