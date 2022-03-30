package com.movie_admin.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.movie_admin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TrailerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Trailer.class);
        Trailer trailer1 = new Trailer();
        trailer1.setId(1L);
        Trailer trailer2 = new Trailer();
        trailer2.setId(trailer1.getId());
        assertThat(trailer1).isEqualTo(trailer2);
        trailer2.setId(2L);
        assertThat(trailer1).isNotEqualTo(trailer2);
        trailer1.setId(null);
        assertThat(trailer1).isNotEqualTo(trailer2);
    }
}
