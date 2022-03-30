package com.movie_admin.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.movie_admin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TrailerDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrailerDTO.class);
        TrailerDTO trailerDTO1 = new TrailerDTO();
        trailerDTO1.setId(1L);
        TrailerDTO trailerDTO2 = new TrailerDTO();
        assertThat(trailerDTO1).isNotEqualTo(trailerDTO2);
        trailerDTO2.setId(trailerDTO1.getId());
        assertThat(trailerDTO1).isEqualTo(trailerDTO2);
        trailerDTO2.setId(2L);
        assertThat(trailerDTO1).isNotEqualTo(trailerDTO2);
        trailerDTO1.setId(null);
        assertThat(trailerDTO1).isNotEqualTo(trailerDTO2);
    }
}
