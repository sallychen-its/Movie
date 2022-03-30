package com.movie_admin.repository.timezone;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DateTimeWrapperRepository extends JpaRepository<DateTimeWrapper, Long> {}
