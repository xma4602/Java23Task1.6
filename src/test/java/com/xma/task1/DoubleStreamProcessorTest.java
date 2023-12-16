package com.xma.task1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Random;
import java.util.stream.DoubleStream;

import static org.junit.jupiter.api.Assertions.*;

class DoubleStreamProcessorTest {

    Random random = new Random();

    DoubleStream emptyStream;
    DoubleStream threeStream;
    DoubleStream fiveStream;
    DoubleStream randomStream;

    @BeforeEach
    void setUp() {
        emptyStream = DoubleStream.empty();
        threeStream = DoubleStream.of(1, 1, 1);
        fiveStream = DoubleStream.generate(() -> random.nextDouble(0.0, 1.0)).limit(5);
        randomStream = DoubleStream.concat(
                DoubleStream.of(0, 1.5, 0, -17.6, 0, 55.66, 0, 12345.0987654321, 0),
                DoubleStream.generate(() -> random.nextDouble(-10, 10)).limit(10)
        );
    }

    @Test
    void zerosCount() {
        assertEquals(0, DoubleStreamProcessor.zerosCount(emptyStream));
        assertEquals(0, DoubleStreamProcessor.zerosCount(threeStream));
        assertEquals(0, DoubleStreamProcessor.zerosCount(fiveStream));
        assertEquals(5, DoubleStreamProcessor.zerosCount(randomStream));
    }

    @Test
    void hasFractionalNumber() {
        assertFalse(DoubleStreamProcessor.hasFractionalNumber(emptyStream));
        assertFalse(DoubleStreamProcessor.hasFractionalNumber(threeStream));
        assertTrue(DoubleStreamProcessor.hasFractionalNumber(fiveStream));
        assertTrue(DoubleStreamProcessor.hasFractionalNumber(randomStream));
    }

    @Test
    void rangeOfValues() {
        assertThrows(NoSuchElementException.class, () -> DoubleStreamProcessor.rangeOfValues(emptyStream));
        assertEquals(0, DoubleStreamProcessor.rangeOfValues(threeStream));
        assertTrue(DoubleStreamProcessor.rangeOfValues(fiveStream) < 1.0);
        assertTrue(DoubleStreamProcessor.rangeOfValues(randomStream) < 12345.0987654321 + 20);
    }

    @Test
    void biggerThenLimit_NOTHING() {
        double limit = Double.MAX_VALUE;
        assertEquals(0, DoubleStreamProcessor.biggerThenLimit(emptyStream, limit).length);
        assertEquals(0, DoubleStreamProcessor.biggerThenLimit(threeStream, limit).length);
        assertEquals(0, DoubleStreamProcessor.biggerThenLimit(fiveStream, limit).length);
        assertEquals(0, DoubleStreamProcessor.biggerThenLimit(randomStream, limit).length);
    }

    @Test
    void biggerThenLimit_ALL() {
        double limit = -Double.MAX_VALUE;
        assertEquals(0, DoubleStreamProcessor.biggerThenLimit(emptyStream, limit).length);
        assertEquals(3, DoubleStreamProcessor.biggerThenLimit(threeStream, limit).length);
        assertEquals(5, DoubleStreamProcessor.biggerThenLimit(fiveStream, limit).length);
        assertEquals(19, DoubleStreamProcessor.biggerThenLimit(randomStream, limit).length);
    }

    @Test
    void biggerThenLimit_ANY() {
        double limit = 0.5;
        assertTrue(DoubleStreamProcessor.biggerThenLimit(emptyStream, limit).length == 0);
        assertTrue(DoubleStreamProcessor.biggerThenLimit(threeStream, limit).length == 3);
        assertTrue(DoubleStreamProcessor.biggerThenLimit(fiveStream, limit).length > 0);
        assertTrue(DoubleStreamProcessor.biggerThenLimit(randomStream, limit).length > 0);
    }

    @Test
    void mostLongString() {
        assertThrows(NoSuchElementException.class, () -> DoubleStreamProcessor.mostLongString(emptyStream));
        double num;
        num = DoubleStreamProcessor.mostLongString(threeStream);
        System.out.println(num);
        num = DoubleStreamProcessor.mostLongString(fiveStream);
        System.out.println(num);
        num = DoubleStreamProcessor.mostLongString(randomStream);
        System.out.println(num);
    }
}