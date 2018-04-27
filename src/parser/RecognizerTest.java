package parser;

import org.junit.Test;
import scanner.TokenType;

import static org.junit.Assert.fail;

/**
 * This class uses the JUnit testing library to test various functions in the Parser class.
 * Each function has multiple inputs pushed through it to test for the handling of the different input types
 * from the scanner. A test is deemed successful if no exceptions are thrown. For each of the bad inputs, there is
 * a system.err call that outputs each error in red to show how the Parser handles the various types of errors that can occur.
 * @author Gabriel Bergstrom
 */
public class RecognizerTest {
    /**
     * Tests the program function
     * @throws Exception thrown when an error occurs in the Parser on a bad input.
     */
    @Test
    public void testProgram() throws Exception {
        String s ="program foo; begin end .";
        Parser p = new Parser(s, false);
        try {
            p.program();
        }catch (Exception e){
            String m = e.getMessage();
            fail(m);
        }
        String s1 ="program foo; begen end ; fee fi .";
        Parser p1 = new Parser(s1, false);
        try {
            p1.program();
        }catch (Exception e){
            String m = e.getMessage();
            System.err.print("Program: bad input test: "+m);
        }
        String s2 ="program foo; array[] end .";
        Parser p2 = new Parser(s2, false);
        try {
            p2.program();
        }catch (Exception e){
            String m = e.getMessage();
            System.err.print("Program Test 2: bad input test: "+m);
        }
        String s3 ="program foo; begin end ;";
        Parser p3 = new Parser(s3, false);
        try {
            p3.program();
        }catch (Exception e){
            String m = e.getMessage();
            System.err.print("Program Test 3: bad input test: "+m);
        }
    }

    /**
     * Tests the declarations function
     * @throws Exception thrown when an error occurs in the Parser on a bad input.
     */
    @Test
    public void testDeclarations() throws Exception {
        String s ="var foo, fee, fi: real; ";
        Parser p = new Parser(s, false);
        try {
            p.declarations();
        }catch (Exception e){
            String m = e.getMessage();
            fail(m);
        }
        String s1 ="var foo, fee, fi: integer; var fum: array[5:5] of real; ";
        Parser p1 = new Parser(s1, false);
        try {
            p1.declarations();
        }catch (Exception e){
            String m = e.getMessage();
            fail(m);
        }
        String s2 ="var foo, fee, fi: integer; var fum: array[fee:5] of real;";
        Parser p2 = new Parser(s2, false);
        try {
            p2.declarations();
        }catch (Exception e){
            String m = e.getMessage();
            System.err.print("Declarations Test 2: bad input test: "+m);
        }
        String s3 ="var foo,fi: function;";
        Parser p3 = new Parser(s3, false);
        try {
            p3.declarations();
        }catch (Exception e){
            String m = e.getMessage();
            System.err.print("Declarations Test 3: bad input test: "+m);
        }
        String s4 ="var 45654[] fee, fi, program: real;";
        Parser p4 = new Parser(s4, false);
        try {
            p4.declarations();
        }catch (Exception e){
            String m = e.getMessage();
            System.err.print("Declarations Test 4: bad input test: "+m);
        }
    }

    /**
     * Tests the subprogram_declaration function
     * @throws Exception thrown when an error occurs in the Parser on a bad input.
     */
    @Test
    public void testSubprogramDeclaration() throws Exception {
        String s ="function dog (run :real) : real; var eat, sleep, bark: real; begin end";
        Parser p = new Parser(s, false);
        try {
            p.subprogramDeclaration();
        }catch (Exception e){
            String m = e.getMessage();
            fail(m);
        }
        String s1 ="procedure dog; var eat, sleep, bark: real; function cat(purr : integer): integer; begin end; begin end";
        Parser p1 = new Parser(s1, false);
        try {
            p1.subprogramDeclaration();
        }catch (Exception e){
            String m = e.getMessage();
            fail(m);
        }
        String s2 ="function dog (run :program) : real; var eat, sleep, 789: real; beg end";
        Parser p2 = new Parser(s2, false);
        try {
            p2.subprogramDeclaration();
        }catch (Exception e){
            String m = e.getMessage();
            System.err.print("SubprogramDeclaration Test 2: bad input test: "+m);
        }
        String s3 ="function 88 (run :fake) : real; var eat, sleep, bark: real; begin end";
        Parser p3 = new Parser(s3, false);
        try {
            p3.subprogramDeclaration();
        }catch (Exception e){
            String m = e.getMessage();
            System.err.print("SubprogramDeclaration Test 3: bad input test: "+m);
        }
        String s4 ="function dog (run*real) ? real; var eat, array[], bark: real; begin end";
        Parser p4 = new Parser(s4, false);
        try {
            p4.subprogramDeclaration();
        }catch (Exception e){
            String m = e.getMessage();
            System.err.print("SubprogramDeclaration Test 4: bad input test: "+m);
        }
        String s5 ="function dog (run:real) : real; var eat, bark[] real; begin end";
        Parser p5 = new Parser(s5, false);
        try {
            p5.subprogramDeclaration();
        }catch (Exception e){
            String m = e.getMessage();
            System.err.print("SubprogramDeclaration Test 5: bad input test: "+m);
        }
    }

    /**
     * Tests the statement function
     * There will be more testing added once the Symbol Table is created.
     * @throws Exception thrown when an error occurs in the Parser on a bad input.
     */
    @Test
    public void testStatement() throws Exception {
        String s1 ="foo := 13 + 7";
        Parser p1 = new Parser(s1, false);
        try {
            p1.getSymbolTable().addVariable("foo", TokenType.INTEGER);
            p1.statement();
        }catch (Exception e){
            String m = e.getMessage();
            fail(m);
        }
        String s2 ="begin if foo < 5 then fi := 5 else fi := 10 end";
        Parser p2 = new Parser(s2, false);
        try {
            p2.getSymbolTable().addVariable("foo", TokenType.INTEGER);
            p2.getSymbolTable().addVariable("fi", TokenType.INTEGER);
            p2.statement();
        }catch (Exception e){
            String m = e.getMessage();
            fail(m);
        }
        String s3 ="while foo < 5 do foo := foo + 1";
        Parser p3 = new Parser(s3, false);
        try {
            p3.getSymbolTable().addVariable("foo", TokenType.INTEGER);
            p3.statement();
        }catch (Exception e){
            String m = e.getMessage();
            fail(m);
        }
        String s4 ="foo(10 > 7)";
        Parser p4 = new Parser(s4, false);
        try {
            p4.getSymbolTable().addProcedure("foo");
            p4.statement();
        }catch (Exception e){
            String m = e.getMessage();
            System.out.print(m);
            fail(m);
        }
        String s6 ="while foo < 5 do foo {} foo + 1";
        Parser p6 = new Parser(s6, false);
        try {
            p6.getSymbolTable().addVariable("fo", TokenType.INTEGER);
            p6.statement();
        }catch (Exception e){
            String m = e.getMessage();
            System.err.print("Statement bad input test1: "+m);
        }
        String s7 ="foo(10 / foo)";
        Parser p7 = new Parser(s7, false);
        try {
            p7.getSymbolTable().addProcedure("foo");
            p7.getSymbolTable().addProcedure("fi");
            p7.statement();
        }catch (Exception e){
            String m = e.getMessage();
            System.err.print("Statement bad input test3: "+m);
        }
    }
    /**
     * Tests the simple_expression
     * @throws Exception thrown when an error occurs in the Parser on a bad input.
     */
    @Test
    public void testSimpleExpression() throws Exception {
        String s ="8 * 8";
        Parser p = new Parser(s, false);
        try {
            p.simpleExpression();
        }catch (Exception e){
            String m = e.getMessage();
            fail(m);
        }
        String s1 ="fee * not foo + fi or fum - fee/fum";
        Parser p1 = new Parser(s1, false);
        try {
            p1.simpleExpression();
        }catch (Exception e){
            String m = e.getMessage();
            fail(m);
        }
        String s2 ="[] + integer or fum - fee";
        Parser p2 = new Parser(s2, false);
        try {
            p2.simpleExpression();
        }catch (Exception e){
            String m = e.getMessage();
            System.err.print("SimpleExpression Test 2: bad input test: "+m);
        }
        String s3 ="or fum - fee";
        Parser p3 = new Parser(s3, false);
        try {
            p3.simpleExpression();
        }catch (Exception e){
            String m = e.getMessage();
            System.err.print("SimpleExpression Test 3: bad input test: "+m);
        }
        String s4 ="fee + program  fee + fi or fum - ()";
        Parser p4 = new Parser(s4, false);
        try {
            p4.simpleExpression();
        }catch (Exception e){
            String m = e.getMessage();
            System.err.print("SimpleExpression Test 4: bad input test: "+m);
        }
        String s5 ="fee + not() fi or fum - fee/]";
        Parser p5 = new Parser(s5, false);
        try {
            p5.simpleExpression();
        }catch (Exception e){
            String m = e.getMessage();
            System.err.print("SimpleExpression Test 5: bad input test: "+m);
        }
    }
    /**
     * Tests the factor function
     * @throws Exception thrown when an error occurs in the Parser on a bad input.
     */
    @Test
    public void testFactor() throws Exception {
        String s = "fee";
        Parser p = new Parser(s, false);
        try {
            p.factor();
        }catch (Exception e){
            String m = e.getMessage();
            fail(m);
        }
        String s2 = "fee (fee)";
        Parser p2 = new Parser(s2, false);
        try {
            p2.factor();
        }catch (Exception e){
            String m = e.getMessage();
            fail(m);
        }
        String s3 = "999999";
        Parser p3 = new Parser(s3, false);
        try {
            p3.factor();
        }catch (Exception e){
            String m = e.getMessage();
            fail(m);
        }
        String s4 = "fee [fee]";
        Parser p4 = new Parser(s4, false);
        try {
            p4.factor();
        }catch (Exception e){
            String m = e.getMessage();
            fail(m);
        }
        String s5 = "not fee";
        Parser p5 = new Parser(s5, false);
        try {
            p5.factor();
        }catch (Exception e){
            String m = e.getMessage();
            fail(m);
        }
        String s6 = "program";
        Parser p6 = new Parser(s6, false);
        try {
            p6.factor();
        }catch (Exception e){
            String m = e.getMessage();
            System.err.print("Factor Test 6: bad input test: "+m);
        }
        String s7 = "array";
        Parser p7 = new Parser(s7, false);
        try {
            p7.factor();
        }catch (Exception e){
            String m = e.getMessage();
            System.err.print("Factor Test 7: bad input test: "+m);
        }
    }
    @Test
    public void testOptionalStatement(){
        String s1 ="foo := 13 + 7";
        Parser p1 = new Parser(s1, false);
        try {
            p1.getSymbolTable().addVariable("foo", TokenType.INTEGER);
            p1.optionalStatements();
        }catch (Exception e){
            String m = e.getMessage();
            fail(m);
        }
        String s2 ="begin if foo < 5 then fi := 5 else fi := 10 end";
        Parser p2 = new Parser(s2, false);
        try {
            p2.getSymbolTable().addVariable("foo", TokenType.INTEGER);
            p2.getSymbolTable().addVariable("fi", TokenType.INTEGER);
            p2.optionalStatements();
        }catch (Exception e){
            String m = e.getMessage();
            fail(m);
        }
        String s3 ="while foo < 5 do foo := foo + 1";
        Parser p3 = new Parser(s3, false);
        try {
            p3.getSymbolTable().addVariable("foo", TokenType.INTEGER);
            p3.optionalStatements();
        }catch (Exception e){
            String m = e.getMessage();
            fail(m);
        }

    }

}