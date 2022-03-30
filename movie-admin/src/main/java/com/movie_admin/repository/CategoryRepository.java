package com.movie_admin.repository;

import com.movie_admin.domain.Category;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


@SuppressWarnings("unused")
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {}
