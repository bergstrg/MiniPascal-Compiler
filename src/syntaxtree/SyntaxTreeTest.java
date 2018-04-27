package syntaxtree;

import org.junit.Test;
import scanner.TokenType;

import static org.junit.Assert.assertEquals;

/**
 * This class is manually building syntax trees to test the use of the indented toString and to show
 * how each of the nodes interacts with each other to form the Syntax Tree.
 * @author Gabriel Bergstrom
 */
public class SyntaxTreeTest {

    @Test
    public void testProgramNode(){
        System.out.println("---Syntax Tree Test: Basic Test---");
        ProgramNode program = new ProgramNode();
        DeclarationsNode foo = new DeclarationsNode();
        SubProgramDeclarationsNode subP = new SubProgramDeclarationsNode();
        CompoundStatementNode compS = new CompoundStatementNode();

        program.name = "test";
        program.setVariables(foo);
        program.setFunctions(subP);
        program.setMain(compS);

        String expected = "|-- Program: test\n" +
                "|-- --- Declarations\n" +
                "|-- --- SubProgramDeclarations\n" +
                "|-- --- Compound Statement\n";
        String actual = program.indentedToString(1);
        assertEquals(expected, actual);
        System.out.print(program.indentedToString(1));

        System.out.println("---Syntax Tree Test: Declarations Test---");

        ProgramNode program1 = new ProgramNode();
        DeclarationsNode foo1 = new DeclarationsNode();

        String variable = "fi";
        VariableNode var = new VariableNode(variable);
        var.setType(TokenType.VAR);
        foo1.addVariable(var);
        variable = "fum";
        var = new VariableNode(variable);
        var.setType(TokenType.VAR);
        foo1.addVariable(var);

        SubProgramDeclarationsNode subP1 = new SubProgramDeclarationsNode();
        CompoundStatementNode compS1 = new CompoundStatementNode();
        program1.name = "test1";
        program1.setVariables(foo1);
        program1.setFunctions(subP1);
        program1.setMain(compS1);

        String expected1 = "|-- Program: test1\n" +
                "|-- --- Declarations\n" +
                "|-- --- --- Name: fi\n" +
                "|-- --- --- Name: fum\n" +
                "|-- --- SubProgramDeclarations\n" +
                "|-- --- Compound Statement\n";
        String actual1 = program1.indentedToString(1);
        assertEquals(expected1, actual1);
        System.out.print(program1.indentedToString(1));

        System.out.println("---Syntax Tree Test: Compound Statement Test---");

        ProgramNode program2 = new ProgramNode();
        DeclarationsNode foo2 = new DeclarationsNode();

        variable = "fi";
        var = new VariableNode(variable);
        var.setType(TokenType.VAR);
        foo2.addVariable(var);
        variable = "fum";
        var = new VariableNode(variable);
        var.setType(TokenType.VAR);
        foo2.addVariable(var);

        SubProgramDeclarationsNode subP2 = new SubProgramDeclarationsNode();
        CompoundStatementNode compS2 = new CompoundStatementNode();
        IfStatementNode ifStatementNode = new IfStatementNode();
        OperationNode operation = new OperationNode(TokenType.LTHAN);


        variable = "jump";
        var = new VariableNode(variable);
        ExpressionNode expressSetLeft = var;
        operation.setLeft(expressSetLeft);

        variable = "5";
        var = new VariableNode(variable);
        ExpressionNode expressSetRight = var;
        operation.setRight(expressSetRight);

        ifStatementNode.setTest(expressSetLeft);

        AssignmentStatementNode assignment = new AssignmentStatementNode();
        variable = "run";
        var = new VariableNode(variable);
        assignment.setLvalue(var);
        variable = "5";
        var = new VariableNode(variable);
        OperationNode op = new OperationNode(TokenType.PLUS);
        op.setLeft(var);
        variable = "4";
        var = new VariableNode(variable);
        op.setRight(var);

        ExpressionNode express = op;
        express.setType(TokenType.PLUS);
        assignment.setExpression(express);
        ifStatementNode.setThenStatement(assignment);

        variable = "walk";
        var = new VariableNode(variable);
        assignment.setLvalue(var);
        express.setType(TokenType.MINUS);
        assignment.setExpression(express);
        ifStatementNode.setElseStatement(assignment);

        compS2.addStatement(ifStatementNode);


        program2.name = "test2";
        program2.setVariables(foo2);
        program2.setFunctions(subP2);
        program2.setMain(compS2);

        expected = "|-- Program: test2\n" +
                "|-- --- Declarations\n" +
                "|-- --- --- Name: fi\n" +
                "|-- --- --- Name: fum\n" +
                "|-- --- SubProgramDeclarations\n" +
                "|-- --- Compound Statement\n" +
                "|-- --- --- If\n" +
                "|-- --- --- --- Name: jump\n" +
                "|-- --- --- --- Assignment\n" +
                "|-- --- --- --- --- Name: walk\n" +
                "|-- --- --- --- --- Operation: PLUS\n" +
                "|-- --- --- --- --- --- Name: 5\n" +
                "|-- --- --- --- --- --- Name: 4\n" +
                "|-- --- --- --- Assignment\n" +
                "|-- --- --- --- --- Name: walk\n" +
                "|-- --- --- --- --- Operation: PLUS\n" +
                "|-- --- --- --- --- --- Name: 5\n" +
                "|-- --- --- --- --- --- Name: 4\n";
        actual = program2.indentedToString(1);
        assertEquals(expected, actual);
        System.out.print(program2.indentedToString(1));
    }

}