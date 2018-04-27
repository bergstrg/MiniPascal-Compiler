package symboltable;

import org.junit.Test;
import scanner.TokenType;

import static org.junit.Assert.*;

/**
 * This class uses the Junit library to test the various functions of the SymbolTable class.
 * Each of these functions tests a function within the SymbolTable by inputting different string variables.
 * For each of the add functions, a string is added to the Hashmap which if done successfully will show up when the Hashmap
 * is print out.
 * For each of the is functions, a string is added and then that string is checked for it's type. The testing has bad inputs
 * being fed into it's functions to test the error handling for when the SymbolTable is handed a bad input.
 *
 * @author Gabriel Bergstrom
 */
public class SymbolTableTest {
    /**
     * This function tests the addProgram by inputting strings and then printing out the contents of the Hashmap
     * to insure they have been added correctly.
     */
    @Test
    public void testAddProgram() {
        System.out.print("---addProgram tests---\n");
        SymbolTable symblT = new SymbolTable();
        String lexeme = "foo";

        symblT.addProgram(lexeme);
        System.out.print("addProgram test: " + symblT.toString() + "\n");

        lexeme = "fee";
        symblT.addProgram(lexeme);
        System.out.print("addProgram test: " + symblT.toString() + "\n");

        lexeme = "fi";
        symblT.addProgram(lexeme);
        System.out.print("addProgram test: " + symblT.toString() + "\n");

        lexeme = "fum";
        symblT.addProgram(lexeme);
        System.out.print("addProgram test: " + symblT.toString() + "\n");

    }

    /**
     * This function tests the addVariable by inputting strings and then printing out the contents of the Hashmap
     * to insure they have been added correctly.
     */
    @Test
    public void testAddVariable() {
        System.out.print("---addVariable tests---\n");
        SymbolTable symblT = new SymbolTable();
        String lexeme = "var1";
        TokenType type = TokenType.REAL;

        symblT.addVariable(lexeme, type);
        System.out.print("addVariable test: " + symblT.toString() + "\n");

        lexeme = "var2";

        symblT.addVariable(lexeme, type);
        System.out.print("addVariable test: " + symblT.toString() + "\n");

        type = TokenType.INTEGER;

        lexeme = "var3";

        symblT.addVariable(lexeme, type);
        System.out.print("addVariable test: " + symblT.toString() + "\n");

        lexeme = "var4";

        symblT.addVariable(lexeme, type);
        System.out.print("addVariable test: " + symblT.toString() + "\n");
    }

    /**
     * This function tests the addProcedure by inputting strings and then printing out the contents of the Hashmap
     * to insure they have been added correctly.
     */
    @Test
    public void testAddProcedure() {
        System.out.print("---addProcedure tests---\n");
        SymbolTable symblT = new SymbolTable();
        String lexeme = "procedure1";

        symblT.addProcedure(lexeme);
        System.out.print("addProcedure test1: " + symblT.toString() + "\n");

        lexeme = "procedure2";

        symblT.addProcedure(lexeme);
        System.out.print("addProcedure test2: " + symblT.toString() + "\n");

        lexeme = "procedure3";

        symblT.addProcedure(lexeme);
        System.out.print("addProcedure test3: " + symblT.toString() + "\n");

        lexeme = "procedure4";

        symblT.addProcedure(lexeme);
        System.out.print("addProcedure test4: " + symblT.toString() + "\n");
    }

    /**
     * This function tests the addFunction by inputting strings and then printing out the contents of the Hashmap
     * to insure they have been added correctly.
     */
    @Test
    public void testAddFunction() {
        System.out.print("---addFunction tests---\n");
        SymbolTable symblT = new SymbolTable();
        String lexeme = "function1";
        TokenType type = TokenType.REAL;

        symblT.addFunction(lexeme, type);
        System.out.print("addFunction test1: " + symblT.toString() + "\n");

        lexeme = "function2";
        symblT.addFunction(lexeme, type);
        System.out.print("addFunction test1: " + symblT.toString() + "\n");

        type = TokenType.INTEGER;

        lexeme = "function3";
        symblT.addFunction(lexeme, type);
        System.out.print("addFunction test1: " + symblT.toString() + "\n");

        lexeme = "function4";
        symblT.addFunction(lexeme, type);
        System.out.print("addFunction test1: " + symblT.toString() + "\n");
    }

    /**
     * This function tests isProgram by adding a string as Kind:Program to the Hashmap and
     * then checking that strings type. This tests if the add is correctly setting the type and
     * that the isProgram is correctly finding strings stored with Kind:Program
     *
     * @throws Exception an error is thrown when the function finds a bad input with the wrong type.
     */
    @Test
    public void testIsProgram() throws Exception {
        System.out.print("---isProgram tests---\n");
        SymbolTable symblT = new SymbolTable();

        String lexeme = "foo";
        symblT.addProgram(lexeme);
        boolean expected = true;
        boolean actual = symblT.isProgram(lexeme);
        assertEquals(expected, actual);

        lexeme = "fee";
        symblT.addProgram(lexeme);
        actual = symblT.isProgram(lexeme);
        assertEquals(expected, actual);

        symblT.addProgram(lexeme);
        lexeme = "0";
        actual = symblT.isProgram(lexeme);
        System.err.print("Program: bad input test 1: " + "Lexeme: " + lexeme + ": expected:" + expected + " : " + " actual:" + actual + "\n");

        symblT.addProgram(lexeme);
        lexeme = "fum";
        actual = symblT.isProgram(lexeme);
        System.err.print("Program: bad input test 2: " + "Lexeme: " + lexeme + ": expected:" + expected + " : " + " actual:" + actual + "\n");

        symblT.addProgram(lexeme);
        lexeme = "PROGRAM";
        actual = symblT.isProgram(lexeme);
        System.err.print("Program: bad input test 3: " + "Lexeme: " + lexeme + ": expected:" + expected + " : " + " actual:" + actual + "\n");
    }

    /**
     * This function tests isVariable by adding a string as Kind:Variable to the Hashmap and
     * then checking that strings type. This tests if the add is correctly setting the type and
     * that the isVariable is correctly finding strings stored with Kind:Variable
     *
     * @throws Exception an error is thrown when the function finds a bad input with the wrong type.
     */
    @Test
    public void testIsVariable() throws Exception {
        System.out.print("---isVariable tests---\n");
        SymbolTable symblT = new SymbolTable();
        TokenType type = TokenType.INTEGER;

        String lexeme = "var1";
        symblT.addVariable(lexeme, type);
        boolean expected = true;
        boolean actual = symblT.isVariable(lexeme);
        assertEquals(expected, actual);

        lexeme = "var2";
        symblT.addVariable(lexeme, type);
        actual = symblT.isVariable(lexeme);
        assertEquals(expected, actual);

        symblT.addVariable(lexeme, type);
        lexeme = "var3";
        actual = symblT.isVariable(lexeme);
        System.err.print("Variable: bad input test 1: " + "Lexeme: " + lexeme + ": expected:" + expected + " : " + " actual:" + actual + "\n");

        symblT.addVariable(lexeme, type);
        lexeme = "100";
        actual = symblT.isVariable(lexeme);
        System.err.print("Variable: bad input test 2: " + "Lexeme: " + lexeme + ": expected:" + expected + " : " + " actual:" + actual + "\n");

        symblT.addVariable(lexeme, type);
        lexeme = "!";
        actual = symblT.isVariable(lexeme);
        System.err.print("Variable: bad input test 3: " + "Lexeme: " + lexeme + ": expected:" + expected + " : " + " actual:" + actual + "\n");


    }

    /**
     * This function tests isProcedure by adding a string as Kind:Procedure to the Hashmap and
     * then checking that strings type. This tests if the add is correctly setting the type and
     * that the isProgram is correctly finding strings stored with Kind:Procedure
     *
     * @throws Exception an error is thrown when the function finds a bad input with the wrong type.
     */
    @Test
    public void testIsProcedure() throws Exception {
        System.out.print("---isProceduretests---\n");
        SymbolTable symblT = new SymbolTable();
        TokenType type = TokenType.INTEGER;

        String lexeme = "procedure1";
        symblT.addProcedure(lexeme);
        boolean expected = true;
        boolean actual = symblT.isProcedure(lexeme);
        assertEquals(expected, actual);

        lexeme = "procedure2";
        symblT.addVariable(lexeme, type);
        lexeme = "dog";
        actual = symblT.isProcedure(lexeme);
        System.err.print("Procedure: bad input test 1: " + "Lexeme: " + lexeme + ": expected:" + expected + " : " + " actual:" + actual + "\n");

        type = TokenType.REAL;

        lexeme = "procedure3";
        symblT.addVariable(lexeme, type);
        lexeme = "cat";
        actual = symblT.isProcedure(lexeme);
        System.err.print("Procedure: bad input test 2: " + "Lexeme: " + lexeme + ": expected:" + expected + " : " + " actual:" + actual + "\n");

        lexeme = "procedure4";
        symblT.addVariable(lexeme, type);
        lexeme = "foo";
        actual = symblT.isProcedure(lexeme);
        System.err.print("Procedure: bad input test 3: " + "Lexeme: " + lexeme + ": expected:" + expected + " : " + " actual:" + actual + "\n");
    }

    /**
     * This function tests isFunction by adding a string as Kind:Function to the Hashmap and
     * then checking that strings type. This tests if the add is correctly setting the type and
     * that the isProgram is correctly finding strings stored with Kind:Function
     *
     * @throws Exception an error is thrown when the function finds a bad input with the wrong type.
     */
    @Test
    public void testIsFunction() throws Exception {
        System.out.print("---isFunction tests---\n");
        SymbolTable symblT = new SymbolTable();
        TokenType type = TokenType.INTEGER;

        String lexeme = "function1";
        symblT.addFunction(lexeme, type);
        boolean expected = true;
        boolean actual = symblT.isFunction(lexeme);
        assertEquals(expected, actual);

        lexeme = "function2";
        symblT.addFunction(lexeme, type);
        lexeme = "wronginput";
        actual = symblT.isFunction(lexeme);
        System.err.print("Function: bad input test 1: " + "Lexeme: " + lexeme + ": expected:" + expected + " : " + " actual:" + actual + "\n");

        lexeme = "function2";
        symblT.addFunction(lexeme, type);
        lexeme = "00000000";
        actual = symblT.isFunction(lexeme);
        System.err.print("Function: bad input test 2: " + "Lexeme: " + lexeme + ": expected:" + expected + " : " + " actual:" + actual + "\n");

        type = TokenType.REAL;

        lexeme = "function3";
        symblT.addFunction(lexeme, type);
        lexeme = "PROGRAM-VARIABLE";
        actual = symblT.isFunction(lexeme);
        System.err.print("Function: bad input test 3: " + "Lexeme: " + lexeme + ": expected:" + expected + " : " + " actual:" + actual + "\n");

        lexeme = "function4";
        symblT.addFunction(lexeme, type);
        lexeme = "@@@@@@++_+*%#(#)@#($";
        actual = symblT.isFunction(lexeme);
        System.err.print("Function: bad input test 4: " + "Lexeme: " + lexeme + ": expected:" + expected + " : " + " actual:" + actual + "\n");


    }

}