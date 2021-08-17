package it.vincendep.popcorn.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
public class ArrayUtilsTests {

    @Test
    void givenNull_whenIsEmpty_thenTrue() {
        Object[] array = null;
        assertThat(ArrayUtils.isEmpty(array)).isTrue();
    }

    @Test
    void givenNull_whenIsNotEmpty_thenFalse() {
        Object[] array = null;
        assertThat(ArrayUtils.isEmpty(array)).isTrue();
    }

    @Test
    void givenEmptyArray_whenIsEmpty_thenTrue() {
        Object[] array = {};
        assertThat(ArrayUtils.isEmpty(array)).isTrue();
    }

    @Test
    void givenNotEmptyArray_whenIsEmpty_thenFalse() {
        Object[] array = {"test"};
        assertThat(ArrayUtils.isEmpty(array)).isFalse();
    }

    @Test
    void givenNotEmptyArray_whenIsNotEmpty_thenTrue() {
        Object[] array = {"test"};
        assertThat(ArrayUtils.isNotEmpty(array)).isTrue();
    }

    @Test
    void givenEmptyArray_whenIsNotEmpty_thenFalse() {
        Object[] array = {};
        assertThat(ArrayUtils.isNotEmpty(array)).isFalse();
    }
}
