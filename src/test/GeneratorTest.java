package test;

import org.junit.jupiter.api.Test;
import utils.CodeGenerator;

import static org.junit.jupiter.api.Assertions.*;

public class GeneratorTest {

    @Test
    public void testCode(){

        int codeLen = 4;

        for (int i = 0; i < Math.pow(52, codeLen); i++) {

            String code = CodeGenerator.generate(codeLen);
            assertEquals(codeLen, code.length());
            assertTrue(code.matches("[a-zA-Z]+"));

        }

    }
}
