package org.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @BeforeAll
    static void setup(){
    }

    @Test
    void AppTest(){
        System.out.printf("Hello Test!");
    }
}
