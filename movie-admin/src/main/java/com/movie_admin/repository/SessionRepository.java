package com.movie_admin.repository;

import com.movie_admin.domain.Session;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


@SuppressWarnings("unused")
@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {}
