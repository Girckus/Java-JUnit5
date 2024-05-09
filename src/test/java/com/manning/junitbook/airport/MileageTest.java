package com.manning.junitbook.airport;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import com.tds.flightmanagement.Mileage;
import com.tds.flightmanagement.Passenger;

public class MileageTest {

    private Passenger mike;
    private Passenger john;
	
	private Mileage mileage;

    @BeforeEach
    public void setUp() {
        mileage = new Mileage();
        mike = new Passenger("Mike", false);
        john = new Passenger("John", true);
    }

    @ParameterizedTest
    @MethodSource("providePassengersAndMilesRegularPassenger")
    @Tag("parameterized")
    public void testAddMileagesAndPointsWithRegularPassenger(int mileage1, int mileage2, int mileage3, int expectedPoints) {
    	mileage.addMileage(mike, mileage1);
        mileage.addMileage(mike, mileage2);
        mileage.addMileage(mike, mileage3);
        
        mileage.calculateGivenPoints();

        assertEquals(expectedPoints, mileage.getPassengersPointsMap().get(mike).intValue());
    }
    
    @ParameterizedTest
    @CsvSource(value = {"349:319:623:129", "312:356:135:80", "223:786:503:151", "482:98:591:117", "128:176:304:60"}, delimiter = ':')
    @Tag("parameterized")
    public void testAddMileagesAndPointsWithVIPPassenger(int mileage1, int mileage2, int mileage3, int expectedPoints) {
    	mileage.addMileage(john, mileage1);
        mileage.addMileage(john, mileage2);
        mileage.addMileage(john, mileage3);
        
        mileage.calculateGivenPoints();

        assertEquals(expectedPoints, mileage.getPassengersPointsMap().get(john).intValue());
    }
    
    @TestFactory
    @Tag("dynamic")
    public List<DynamicTest> testAddMileageAndCalculatePoints() {
        List<DynamicTest> dynamicTests = new ArrayList<>();
        
        // Regular passenger test
        dynamicTests.add(DynamicTest.dynamicTest("Test add mileage and calculate points for regular passenger", () -> {
            Passenger regularPassenger = new Passenger("Jane", false);
            mileage.addMileage(regularPassenger, 2000);
            mileage.calculateGivenPoints();
            assertEquals(100, mileage.getPassengersPointsMap().get(regularPassenger).intValue());
        }));
        
        // VIP passenger test
        dynamicTests.add(DynamicTest.dynamicTest("Test add mileage and calculate points for VIP passenger", () -> {
            Passenger vipPassenger = new Passenger("Alice", true);
            mileage.addMileage(vipPassenger, 10000);
            mileage.calculateGivenPoints();
            assertEquals(1000, mileage.getPassengersPointsMap().get(vipPassenger).intValue());
        }));

        return dynamicTests;
    }
    
    private static Stream<Arguments> providePassengersAndMilesRegularPassenger() {
        return Stream.of( Arguments.of(349, 319, 623, 64),
        				  Arguments.of(312, 356, 135, 40),
        				  Arguments.of(223, 786, 503, 75),
        				  Arguments.of(482, 98, 591, 58),
        				  Arguments.of(128, 176, 304, 30));
    }
    
}