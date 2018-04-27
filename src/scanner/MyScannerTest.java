package scanner;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.assertEquals;

/**
 * @author Gabriel Bergstrom
 *
 */
public class MyScannerTest {
    /**
     * Testing the yytext() function by checking if the lexeme is being set correctly.
     * @throws Exception an exception is thrown if the actual and expected results do not line up.
     */
    @Test
    public void testYytext() throws Exception {
        String filename = "src/Testing/simple.pas";
        FileInputStream fis = null;
        fis = new FileInputStream(filename);
        InputStreamReader isr = new InputStreamReader(fis);
        MyScanner scan = new MyScanner(isr);
        Token aToken = null;
        String expetedLexeme = null;
        String actualLexeme = null;

        aToken = scan.nextToken();
        expetedLexeme = "program";
        actualLexeme = aToken.getLexeme();
        assertEquals(expetedLexeme, actualLexeme);

        aToken = scan.nextToken();
        expetedLexeme = "foo";
        actualLexeme = aToken.getLexeme();
        assertEquals(expetedLexeme, actualLexeme);

        aToken = scan.nextToken();
        expetedLexeme = ";";
        actualLexeme = aToken.getLexeme();
        assertEquals(expetedLexeme, actualLexeme);

        aToken = scan.nextToken();
        expetedLexeme = "begin";
        actualLexeme = aToken.getLexeme();
        assertEquals(expetedLexeme, actualLexeme);

        aToken = scan.nextToken();
        expetedLexeme = "end";
        actualLexeme = aToken.getLexeme();
        assertEquals(expetedLexeme, actualLexeme);

        aToken = scan.nextToken();
        expetedLexeme = ".";
        actualLexeme = aToken.getLexeme();
        assertEquals(expetedLexeme, actualLexeme);
    }
    /**
     * Testing the nextToken() function by checking if the token types are set correctly
     * @throws Exception an exception is thrown if the actual and expected results do not line up.
     */
    @Test
    public void testNextToken() throws Exception {
        String filename = "src/Testing/simple.pas";
        FileInputStream fis = null;
        fis = new FileInputStream(filename);
        InputStreamReader isr = new InputStreamReader(fis);
        MyScanner scan = new MyScanner(isr);
        Token aToken = null;
        TokenType expectedType = null;
        TokenType actualType = null;

        aToken = scan.nextToken();
        expectedType = TokenType.PROGRAM;
        actualType = aToken.getType();
        assertEquals(expectedType, actualType);

        aToken = scan.nextToken();
        expectedType = TokenType.ID;
        actualType = aToken.getType();
        assertEquals(expectedType, actualType);

        aToken = scan.nextToken();
        expectedType = TokenType.SEMI;
        actualType = aToken.getType();
        assertEquals(expectedType, actualType);

        aToken = scan.nextToken();
        expectedType = TokenType.BEGIN;
        actualType = aToken.getType();
        assertEquals(expectedType, actualType);

        aToken = scan.nextToken();
        expectedType = TokenType.END;
        actualType = aToken.getType();
        assertEquals(expectedType, actualType);

        aToken = scan.nextToken();
        expectedType = TokenType.PERIOD;
        actualType = aToken.getType();
        assertEquals(expectedType, actualType);
    }
}