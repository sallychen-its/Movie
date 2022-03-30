package com.movie_admin.repository;

import com.movie_admin.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuthorityRepository extends JpaRepository<Authority, String> {}
