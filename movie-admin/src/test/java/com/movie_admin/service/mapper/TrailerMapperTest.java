package com.movie_admin.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TrailerMapperTest {

    private TrailerMapper trailerMapper;

    @BeforeEach
    public void setUp() {
        trailerMapper = new TrailerMapperImpl();
    }
}
