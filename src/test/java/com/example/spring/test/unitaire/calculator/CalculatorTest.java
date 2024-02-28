package com.example.spring.test.unitaire.calculator;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CalculatorTest {

    Calculator calculator;

    @BeforeEach
    public void before() {
        System.out.println("This method is executed before each method");
        calculator = new Calculator();
    }

    @AfterEach
    public void after() {
        System.out.println("This method is executed after each method");
    }

    @BeforeAll
    public static void beforeClass() {
        System.out.println("This method is executed before the class");
    }

    @AfterAll
    public static void afterClass() {
        System.out.println("This method is executed after the class");
    }


    @Test
    public void testMultiply(){
        System.out.println("test multiply");
        assertEquals(20,calculator.multiply(4,5));
        assertEquals(25,calculator.multiply(5,5));
    }

    @Test
    public void testDivide(){
        System.out.println("test divide");
        assertEquals(2,calculator.divide(4,2));
        assertEquals(5,calculator.divide(5,1));
    }

    @Test
    public void sum_with3numbers() {
        System.out.println("test sum with three values");
        assertEquals(6, calculator.sum(new int[] { 1, 2, 3 }));
    }

    @Test
    public void sum_with1number() {
        System.out.println("test sum with one value");
        assertEquals(3, calculator.sum(new int[] { 3 }));
    }

}
