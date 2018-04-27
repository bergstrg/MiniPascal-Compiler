package parser;

import org.junit.Test;
import scanner.TokenType;
import syntaxtree.*;

import static org.junit.Assert.assertEquals;

/**
 * This class uses the JUnit testing library to test various functions in the Parser class
 * to insure that the Syntax Tree has been correctly implemented.
 * Each function has multiple inputs pushed through it for testing the creation of the nodes that make up the Syntax Tree.
 * @author Gabriel Bergstrom
 */
public class ParserTest {

    /**
     * Tests the program function with the implementation of the syntax tree
     */
    @Test
    public void testProgram(){
        String inputProgram = "program foo; begin end.";
        Parser parse = new Parser(inputProgram, false);
        ProgramNode program = parse.program();
        String expected = "|-- Program: foo\n" +
                "|-- --- Declarations\n" +
                "|-- --- SubProgramDeclarations\n" +
                "|-- --- Compound Statement\n";
        String actual = program.indentedToString(1);
        assertEquals(expected, actual);

        inputProgram = "program foo; var fee, fi: real; begin if fi < 5 then fee := 5 else fee := 10 end .";
        parse = new Parser(inputProgram, false);
        program = parse.program();
        expected = "|-- Program: foo\n" +
                "|-- --- Declarations\n" +
                "|-- --- --- Name: fee Type: REAL\n" +
                "|-- --- --- Name: fi Type: REAL\n" +
                "|-- --- SubProgramDeclarations\n" +
                "|-- --- Compound Statement\n" +
                "|-- --- --- If\n" +
                "|-- --- --- --- Operation: LTHAN\n" +
                "|-- --- --- --- --- Name: fi Type: ID\n" +
                "|-- --- --- --- --- Name: 5 Type: INTEGER\n" +
                "|-- --- --- --- Assignment\n" +
                "|-- --- --- --- --- Name: fee Type: ID\n" +
                "|-- --- --- --- --- Name: 5 Type: INTEGER\n" +
                "|-- --- --- --- Assignment\n" +
                "|-- --- --- --- --- Name: fee Type: ID\n" +
                "|-- --- --- --- --- Name: 10 Type: INTEGER\n";
        actual = program.indentedToString(1);
        assertEquals(expected, actual);

        inputProgram = "program foo; var fee, fi: real; function fum: real ; var walk, jog, run : real;"+
                       "begin if fum < 5 then run := 5 - 4 else run := 4 + 5 end; "+
                       "begin if fi < 5 then fee := 5 else fee := 10 end.";
        parse = new Parser(inputProgram, false);
        program = parse.program();
        expected = "|-- Program: foo\n" +
                "|-- --- Declarations\n" +
                "|-- --- --- Name: fee Type: REAL\n" +
                "|-- --- --- Name: fi Type: REAL\n" +
                "|-- --- SubProgramDeclarations\n" +
                "|-- --- --- SubProgram: fum, Return: null\n" +
                "|-- --- --- Arguments: fum[REAL]\n" +
                "|-- --- --- --- Declarations\n" +
                "|-- --- --- --- --- Name: walk Type: REAL\n" +
                "|-- --- --- --- --- Name: jog Type: REAL\n" +
                "|-- --- --- --- --- Name: run Type: REAL\n" +
                "|-- --- --- --- SubProgramDeclarations\n" +
                "|-- --- --- --- Compound Statement\n" +
                "|-- --- --- --- --- If\n" +
                "|-- --- --- --- --- --- Operation: LTHAN\n" +
                "|-- --- --- --- --- --- --- Name: fum Type: REAL\n" +
                "|-- --- --- --- --- --- --- Name: 5 Type: INTEGER\n" +
                "|-- --- --- --- --- --- Assignment\n" +
                "|-- --- --- --- --- --- --- Name: run Type: ID\n" +
                "|-- --- --- --- --- --- --- Operation: MINUS\n" +
                "|-- --- --- --- --- --- --- --- Name: 5 Type: INTEGER\n" +
                "|-- --- --- --- --- --- --- --- Name: 4 Type: INTEGER\n" +
                "|-- --- --- --- --- --- Assignment\n" +
                "|-- --- --- --- --- --- --- Name: run Type: ID\n" +
                "|-- --- --- --- --- --- --- Operation: PLUS\n" +
                "|-- --- --- --- --- --- --- --- Name: 4 Type: INTEGER\n" +
                "|-- --- --- --- --- --- --- --- Name: 5 Type: INTEGER\n" +
                "|-- --- Compound Statement\n" +
                "|-- --- --- If\n" +
                "|-- --- --- --- Operation: LTHAN\n" +
                "|-- --- --- --- --- Name: fi Type: ID\n" +
                "|-- --- --- --- --- Name: 5 Type: INTEGER\n" +
                "|-- --- --- --- Assignment\n" +
                "|-- --- --- --- --- Name: fee Type: ID\n" +
                "|-- --- --- --- --- Name: 5 Type: INTEGER\n" +
                "|-- --- --- --- Assignment\n" +
                "|-- --- --- --- --- Name: fee Type: ID\n" +
                "|-- --- --- --- --- Name: 10 Type: INTEGER\n";
        actual = program.indentedToString(1);
        assertEquals(expected, actual);

    }

    /**
     * Tests the declarations function with the implementation of the syntax tree
     */
    @Test
    public void testDeclarations(){
        String inputProgram = "var fee, fi, fum, fo: real;";
        Parser parse = new Parser(inputProgram, false);
        DeclarationsNode declare = parse.declarations();
        String expected = "|-- Declarations\n" +
                "|-- --- Name: fee Type: REAL\n" +
                "|-- --- Name: fi Type: REAL\n" +
                "|-- --- Name: fum Type: REAL\n" +
                "|-- --- Name: fo Type: REAL\n";
        String actual = declare.indentedToString(1) ;
        assertEquals(expected, actual);

        inputProgram = "var run, walk, jog, run: integer;";
        parse = new Parser(inputProgram, false);
        declare = parse.declarations();
        expected = "|-- Declarations\n" +
                "|-- --- Name: run Type: INTEGER\n" +
                "|-- --- Name: walk Type: INTEGER\n" +
                "|-- --- Name: jog Type: INTEGER\n" +
                "|-- --- Name: run Type: INTEGER\n";
        actual = declare.indentedToString(1) ;
        assertEquals(expected, actual);
    }

    /**
     * Tests the subProgram_declaration function with the implementation of the syntax tree
     */
    @Test
    public void testSubProgramDeclaration(){
        String inputProgram = "function doIt: real; var jump, duck: real; begin end ; .";
        Parser parse = new Parser(inputProgram, false);
        SubProgramDeclarationsNode subP = parse.subprogramDeclarations();
        String expected = "|-- SubProgramDeclarations\n" +
                "|-- --- SubProgram: doIt, Return: null\n" +
                "|-- --- Arguments: doIt[REAL]\n" +
                "|-- --- --- Declarations\n" +
                "|-- --- --- --- Name: jump Type: REAL\n" +
                "|-- --- --- --- Name: duck Type: REAL\n" +
                "|-- --- --- SubProgramDeclarations\n" +
                "|-- --- --- Compound Statement\n";
        String actual = subP.indentedToString(1) ;
        assertEquals(expected, actual);


        inputProgram = "function execute(foo, bar : integer ) : integer ; begin end ; .";
        parse = new Parser(inputProgram, false);
        subP = parse.subprogramDeclarations();
        expected = "|-- SubProgramDeclarations\n" +
                "|-- --- SubProgram: execute, Return: null\n" +
                "|-- --- Arguments: foo[INTEGER], \n" +
                "bar[INTEGER], \n" +
                "bar[INTEGER]\n" +
                "|-- --- --- Declarations\n" +
                "|-- --- --- SubProgramDeclarations\n" +
                "|-- --- --- Compound Statement\n";
        actual = subP.indentedToString(1) ;
        assertEquals(expected, actual);

        inputProgram = "function superExecute(foo, bar : real ) : real ; var fub, fum : real ; begin fub := foo + bar ; fum := bar - foo end ; .";
        parse = new Parser(inputProgram, false);
        subP = parse.subprogramDeclarations();
        expected = "|-- SubProgramDeclarations\n" +
                "|-- --- SubProgram: superExecute, Return: null\n" +
                "|-- --- Arguments: foo[REAL], \n" +
                "bar[REAL], \n" +
                "bar[REAL]\n" +
                "|-- --- --- Declarations\n" +
                "|-- --- --- --- Name: fub Type: REAL\n" +
                "|-- --- --- --- Name: fum Type: REAL\n" +
                "|-- --- --- SubProgramDeclarations\n" +
                "|-- --- --- Compound Statement\n" +
                "|-- --- --- --- Assignment\n" +
                "|-- --- --- --- --- Name: fub Type: ID\n" +
                "|-- --- --- --- --- Operation: PLUS\n" +
                "|-- --- --- --- --- --- Name: foo Type: ID\n" +
                "|-- --- --- --- --- --- Name: bar Type: REAL\n" +
                "|-- --- --- --- Assignment\n" +
                "|-- --- --- --- --- Name: fum Type: ID\n" +
                "|-- --- --- --- --- Operation: MINUS\n" +
                "|-- --- --- --- --- --- Name: bar Type: REAL\n" +
                "|-- --- --- --- --- --- Name: foo Type: ID\n";
        actual = subP.indentedToString(1) ;
        assertEquals(expected, actual);
    }

    /**
     * Tests the statement function with the implementation of the syntax tree
     */
    @Test
    public void testStatement(){
        String input ="if foo < 5 then fi := 5 else fi := 10";
        Parser parser = new Parser(input, false);
        parser.getSymbolTable().addVariable("foo", TokenType.INTEGER);
        parser.getSymbolTable().addVariable("fi", TokenType.INTEGER);
        StatementNode statement = parser.statement();
        String actual = statement.indentedToString(1);
        String expected="|-- If\n" +
                "|-- --- Operation: LTHAN\n" +
                "|-- --- --- Name: foo Type: INTEGER\n" +
                "|-- --- --- Name: 5 Type: INTEGER\n" +
                "|-- --- Assignment\n" +
                "|-- --- --- Name: fi Type: INTEGER\n" +
                "|-- --- --- Name: 5 Type: INTEGER\n" +
                "|-- --- Assignment\n" +
                "|-- --- --- Name: fi Type: INTEGER\n" +
                "|-- --- --- Name: 10 Type: INTEGER\n";

        assertEquals(expected, actual);

        input ="while foo = 5 do if fub < 4 then fub := 6 else fub := 4";
        parser = new Parser(input, false);
        parser.getSymbolTable().addVariable("foo", TokenType.INTEGER);
        parser.getSymbolTable().addVariable("fub", TokenType.INTEGER);
        statement = parser.statement();
        actual = statement.indentedToString(1);
        expected="|-- While\n" +
                "|-- --- Operation: EQUAL\n" +
                "|-- --- --- Name: foo Type: INTEGER\n" +
                "|-- --- --- Name: 5 Type: INTEGER\n" +
                "|-- Do:\n" +
                "|-- --- If\n" +
                "|-- --- --- Operation: LTHAN\n" +
                "|-- --- --- --- Name: fub Type: INTEGER\n" +
                "|-- --- --- --- Name: 4 Type: INTEGER\n" +
                "|-- --- --- Assignment\n" +
                "|-- --- --- --- Name: fub Type: INTEGER\n" +
                "|-- --- --- --- Name: 6 Type: INTEGER\n" +
                "|-- --- --- Assignment\n" +
                "|-- --- --- --- Name: fub Type: INTEGER\n" +
                "|-- --- --- --- Name: 4 Type: INTEGER\n";
        assertEquals(expected, actual);

        input ="while foo = 5 do if fub < 4 then fub := 6 else fub := 4";
        parser = new Parser(input, false);
        parser.getSymbolTable().addVariable("foo", TokenType.INTEGER);
        parser.getSymbolTable().addVariable("fub", TokenType.INTEGER);
        statement = parser.statement();
        actual = statement.indentedToString(1);
        expected="|-- While\n" +
                "|-- --- Operation: EQUAL\n" +
                "|-- --- --- Name: foo Type: INTEGER\n" +
                "|-- --- --- Name: 5 Type: INTEGER\n" +
                "|-- Do:\n" +
                "|-- --- If\n" +
                "|-- --- --- Operation: LTHAN\n" +
                "|-- --- --- --- Name: fub Type: INTEGER\n" +
                "|-- --- --- --- Name: 4 Type: INTEGER\n" +
                "|-- --- --- Assignment\n" +
                "|-- --- --- --- Name: fub Type: INTEGER\n" +
                "|-- --- --- --- Name: 6 Type: INTEGER\n" +
                "|-- --- --- Assignment\n" +
                "|-- --- --- --- Name: fub Type: INTEGER\n" +
                "|-- --- --- --- Name: 4 Type: INTEGER\n";
        assertEquals(expected, actual);
    }

    /**
     * Tests the simple_expression function with the implementation of the syntax tree
     */
    @Test
    public void SimpleExpression(){
        String input = "fee * not foo + fi or fum - fee/fum";
        Parser parser = new Parser(input, false);
        parser.getSymbolTable().addVariable("fee", TokenType.INTEGER);
        parser.getSymbolTable().addVariable("foo", TokenType.INTEGER);
        parser.getSymbolTable().addVariable("fum", TokenType.INTEGER);

        ExpressionNode actualNode = parser.simpleExpression();
        String actual = actualNode.indentedToString(1);
        String expected = "|-- Operation: PLUS\n" +
                "|-- --- Operation: ASTERISK\n" +
                "|-- --- --- Name: fee Type: INTEGER\n" +
                "|-- --- --- Sign: NOT, Type: INTEGER\n" +
                "|-- --- --- --- Name: foo Type: INTEGER\n" +
                "|-- --- Operation: OR\n" +
                "|-- --- --- Name: fi Type: null\n" +
                "|-- --- --- Operation: MINUS\n" +
                "|-- --- --- --- Name: fum Type: INTEGER\n" +
                "|-- --- --- --- Operation: FSLASH\n" +
                "|-- --- --- --- --- Name: fee Type: INTEGER\n" +
                "|-- --- --- --- --- Name: fum Type: INTEGER\n";
        assertEquals(expected, actual);

        input = "8 * 8";
        parser = new Parser(input, false);

        actualNode = parser.simpleExpression();
        actual = actualNode.indentedToString(1);
        expected = "|-- Operation: ASTERISK\n" +
                "|-- --- Name: 8 Type: INTEGER\n" +
                "|-- --- Name: 8 Type: INTEGER\n";
        assertEquals(expected, actual);

        input = "not foo - 8 / bar";
        parser = new Parser(input, false);
        parser.getSymbolTable().addVariable("foo", TokenType.INTEGER);
        parser.getSymbolTable().addVariable("bar", TokenType.REAL);
        actualNode = parser.simpleExpression();
        actual = actualNode.indentedToString(1);
        expected = "|-- Operation: MINUS\n" +
                "|-- --- Sign: NOT, Type: INTEGER\n" +
                "|-- --- --- Name: foo Type: INTEGER\n" +
                "|-- --- Operation: FSLASH\n" +
                "|-- --- --- Name: 8 Type: INTEGER\n" +
                "|-- --- --- Name: bar Type: REAL\n";
        assertEquals(expected, actual);


    }

    /**
     * Tests the factor function with the implementation of the syntax tree
     */
    @Test
    public void testFactor(){
        String input = "4";
        Parser parser = new Parser(input, false);
        ExpressionNode expression = parser.factor();
        String actual = expression.indentedToString(1);
        String expected = "|-- Name: 4 Type: INTEGER\n";

        input = "foo";
        parser = new Parser(input, false);
        parser.getSymbolTable().addVariable("foo", TokenType.ID);
        expression = parser.factor();
        actual = expression.indentedToString(1);
        expected = "|-- Name: foo Type: ID\n";

        input = "(21*12)";
        parser = new Parser(input, false);
        expression = parser.factor();
        actual = expression.indentedToString(1);
        expected = "|-- Operation: ASTERISK\n" +
                "|-- --- Name: 21 Type: INTEGER\n" +
                "|-- --- Name: 12 Type: INTEGER\n";
        assertEquals(expected, actual);

        input = "not 21";
        parser = new Parser(input, false);
        expression = parser.factor();
        actual = expression.indentedToString(1);
        expected = "|-- Sign: NOT, Type: INTEGER\n" +
                   "|-- --- Name: 21 Type: INTEGER\n";

        assertEquals(expected, actual);
    }

}