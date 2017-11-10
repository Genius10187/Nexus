package com.meti.util;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UtilityTest {
    @Test
    void castIfOfInstance() {
        String input = "This is a test!";

        Object obj = input;
        String output = Utility.castIfOfInstance(obj, String.class);

        assertEquals(input, output);
    }

    @Test
    void getExtension() {
        File input = new File("testFile.txt");

        String extension = Utility.getExtension(input);

        assertEquals("txt", extension);
    }
}