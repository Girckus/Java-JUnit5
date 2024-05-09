package com.manning.junitbook.airport;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

public class RandomDisabledTests {

	@ParameterizedTest
    @NullSource
    @Disabled
    void testShouldReturnTrueForNullInputs(String input) {
        assertTrue(input == null);
    }

	@ParameterizedTest
	@EmptySource
	@Disabled
	void testShouldReturnTrueForEmptyStrings(String input) {
	    assertTrue(input.equals(""));
	}
	
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = {"  ", "\t", "\n"})
	@Disabled
	void isBlank_ShouldReturnTrueForAllTypesOfBlankStrings(String input) {
	    assertTrue(input.equals(""));
	}
}