package com.movie_admin.repository;

import com.movie_admin.domain.Actor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


@SuppressWarnings("unused")
@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {}
