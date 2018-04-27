package codegeneration;

import scanner.TokenType;
import symboltable.SymbolTable;
import syntaxtree.*;

/**
 * @author Gabriel Bergstrom
 * This class will take in the program node generated from the Parser and will be converting it into MIPS assembly code.
 * The returned String will be a fully functioning MIPS assembly program that can be ran in the QTSPIM Simulator.
 */
public class CodeGeneration {

    private ProgramNode programNode;
    private SymbolTable symbolTable;
    private int currentRegister;
    private int ifNumber;
    private int looper;

    public CodeGeneration(ProgramNode program, SymbolTable symbolTbl){
        programNode = program;
        symbolTable = symbolTbl;
        currentRegister = 0;
        ifNumber = 0;
        looper = 0;
    }

    /**
     * Starts the code from the root node by writing the outline of the
     * assembly code, and telling the root node to write its answer into $s0.
     * @return A String of the assembly code.
     */
    public String writeCode(){
        StringBuilder code = new StringBuilder();
        code.append( ".data\n");
        for(VariableNode var: programNode.getVariables().getVariables()){
            code.append(var.getName()).append("\t:.word\t0\n");
            symbolTable.get(var.getName()).setMemoryAddress(var.getName());
        }
        code.append( ".text\n");
        code.append( "main:\n");
        code.append(pushToStack());
        for (StatementNode state : programNode.getMain().getStatement()) {
            code.append(writeStatement(state, "$s" + currentRegister));
        }
        code.append(popFromStack());

        return code.toString();
    }

    /**
     * Writes code for an operations node.
     * The code is written by gathering the child nodes' answers into
     * a pair of registers, and then executing the op on those registers,
     * placing the result in the given result register.
     * @param opNode The operation node to perform.
     * @param resultRegister The register in which to put the result.
     * @return The code which executes this operation.
     */
    private String writeOperation(OperationNode opNode, String resultRegister)
    {
        StringBuilder code = new StringBuilder();
        ExpressionNode left = opNode.getLeft();
        String leftRegister = "$t" + currentRegister++;
        code.append(writeExpression( left, leftRegister));
        ExpressionNode right = opNode.getRight();
        String rightRegister = "$t" + currentRegister++;
        code.append(writeExpression( right, rightRegister));
        TokenType kindOfOp = opNode.getOperation();
        if( kindOfOp == TokenType.PLUS)
        {
            code.append("add    " + resultRegister + ",   " + leftRegister +",   " + rightRegister + "\n");
        }
        if( kindOfOp == TokenType.MINUS)
        {
            code.append("sub    " + resultRegister + ",   " + leftRegister +",   " + rightRegister + "\n");
        }
        if( kindOfOp == TokenType.ASTERISK)
        {
            code.append("mult   " + leftRegister + ",   " + rightRegister + "\n");
            code.append("mflo   " + resultRegister + "\n");
        }
        if (kindOfOp == TokenType.AND) {
            code.append("and\t").append(resultRegister).append(",\t").append(leftRegister).append(",\t").append(rightRegister).append("\n");
        }
        if (kindOfOp == TokenType.OR) {
            code.append("or\t").append(resultRegister).append(",\t").append(leftRegister).append(",\t").append(rightRegister).append("\n");
        }
        if (kindOfOp == TokenType.LTHAN)
        {
            code.append("bge\t").append(leftRegister).append(",\t").append(rightRegister).append(",\t");
        }
        if (kindOfOp == TokenType.GTHAN)
        {
            code.append("ble\t").append(leftRegister).append(",\t").append(rightRegister).append(",\t");
        }
        if (kindOfOp == TokenType.LTHANEQ)
        {
            code.append("bgt\t").append(leftRegister).append(",\t").append(rightRegister).append(",\t");
        }
        if (kindOfOp == TokenType.GTHANEQ)
        {
            code.append("blt\t").append(leftRegister).append(",\t").append(rightRegister).append(",\t");
        }
        if (kindOfOp == TokenType.EQUAL)
        {
            code.append("bne\t").append(leftRegister).append(",\t").append(rightRegister).append(",\t");
        }
        if (kindOfOp == TokenType.NOTEQ)
        {
            code.append("beq\t").append(leftRegister).append(",\t").append(rightRegister).append(",\t");
        }
        this.currentRegister -= 2;
        return code.toString();
    }

    /**
     * Writes code for a value node.
     * The code is written by executing an add immediate with the value
     * into the destination register.
     * Writes code that looks like  addi $reg, $zero, value
     * @param valNode The node containing the value.
     * @param resultRegister The register in which to put the value.
     * @return The code which executes this value node.
     */
    private String writeValue(ValueNode valNode, String resultRegister)
    {
        StringBuilder code = new StringBuilder();
        String value = valNode.getAttribute();
        code.append("addi   " + resultRegister + ",   $zero, " + value + "\n");
        return code.toString();
    }

    /**
     * Writes code for the expression node.
     * @param express The node containing the value.
     * @param resultRegister The register in which to put the value.
     * @return The code which executes this expression node.
     */
    private String writeExpression(ExpressionNode express, String resultRegister) {
        StringBuilder code = new StringBuilder();
        code.append("\n#Expression\n");
        if(express instanceof ValueNode){
            code.append(writeValue((ValueNode) express, resultRegister));
        }
        else if (express instanceof OperationNode){
            code.append(writeOperation((OperationNode) express, resultRegister));
        }
        else if (express instanceof VariableNode){
            if(symbolTable.get(((VariableNode) express).getName()) != null){
                String variable = symbolTable.get(((VariableNode) express).getName()).getMemoryAddress();
                code.append("lw\t").append(resultRegister).append(",\t").append(variable).append("\n");
            }
            else{
                code.append("addi   " + resultRegister + ",   $zero, " + ((VariableNode) express).getName()+ "\n");
            }
        }
        else {
            code.append("ERROR AT WRITE EXPRESSION");
        }
        return code.toString();
    }

    /**
     * Writes code for the statement node.
     * @param statementNode The node containing the value.
     * @param resultRegister The register in which to put the value.
     * @return The code which executes this statement node.
     */
    private String writeStatement(StatementNode statementNode, String resultRegister){
        StringBuilder code = new StringBuilder();
        if(statementNode instanceof AssignmentStatementNode){
            code.append(writeAssignment((AssignmentStatementNode) statementNode, resultRegister ));
        }
        else if (statementNode instanceof IfStatementNode){
            code.append(writeIfStatement((IfStatementNode)statementNode, resultRegister));
        }
        else if (statementNode instanceof WhileStatementNode){
            code.append(writeWhileStatement((WhileStatementNode)statementNode, resultRegister));
        }
        else if (statementNode instanceof CompoundStatementNode){
            code.append(writeStatement((CompoundStatementNode)statementNode, resultRegister));
        }else{
            code.append("ERROR AT WRITE STATEMENT");
        }
        return code.toString();
    }

    /**
     * Writes code for the if_statement node.
     * @param ifStatementNode The node containing the value.
     * @param resultRegister The register in which to put the value.
     * @return The code which executes this if_statement node.
     */
    private String writeIfStatement(IfStatementNode ifStatementNode, String resultRegister) {
        StringBuilder code = new StringBuilder();
        code.append("\n#if statement\n");
        code.append(writeOperation((OperationNode) ifStatementNode.getTest(), resultRegister)).append("else").append(ifNumber).append("\n");

        code.append("\n# then\n");
        resultRegister = "$s" + currentRegister++;
        code.append(writeStatement(ifStatementNode.getThenStatement(), resultRegister));
        code.append("\nj\tendIf").append(ifNumber).append("\n");

        code.append("\n# else\n");
        resultRegister = "$s" + currentRegister++;
        code.append("else").append(ifNumber).append(":\n");
        code.append(writeStatement(ifStatementNode.getElseStatement(), resultRegister));
        code.append("\nendIf").append(ifNumber).append(":\n");

        currentRegister -= 2;
        return code.toString();
    }

    /**
     * Writes code for the assignment_statement node.
     * @param assignmentStatementNode The node containing the value.
     * @param resultRegister The register in which to put the value.
     * @return  The code which executes this assignment_statement node.
     */
    private String writeAssignment(AssignmentStatementNode assignmentStatementNode, String resultRegister) {
        StringBuilder code = new StringBuilder();
        code.append("\n#Assignment\n").append(writeExpression(assignmentStatementNode.getExpression(), resultRegister))
                .append("sw\t").append(resultRegister).append(",\t").append(symbolTable.get(assignmentStatementNode.getLvalue().getName()).getMemoryAddress());
        return code.toString();
    }

    /**
     * Writes code for the while_statement node.
     * @param whileStatementNode The node containing the value.
     * @param resultRegister The register in which to put the value.
     * @return  The code which executes this while_statement node.
     */
    private String writeWhileStatement(WhileStatementNode whileStatementNode, String resultRegister) {
        StringBuilder code = new StringBuilder();
        code.append("\n#While Loop\n");
        code.append("while").append(looper).append(":\n");
        code.append(writeExpression(whileStatementNode.getTest(), resultRegister));
        code.append("endWhile").append(looper).append(":\n");

        currentRegister++;
        resultRegister = "$s" + currentRegister;
        code.append(writeStatement(whileStatementNode.getDoStatement(), resultRegister));

        code.append("\tj while").append(looper).append(":\n");
        code.append("endWhile").append(looper).append(":\n");
        currentRegister--;
        return code.toString();
    }

    /**
     * Pushes all $s registers, $fp and $sp to the stack
     * @return The assembly code which executes this operation
     */
    private String pushToStack() {
        StringBuilder code = new StringBuilder();
        code.append("\n#Push to stack\n");
        code.append("addi\t$sp,\t$sp,\t-");
        code.append(8 * 4 + 8);
        code.append('\n');

        for (int i = 8 - 1; i >= 0; i--) {
            code.append("sw\t").append("$s").append(i).append(",\t").append(4 * (i + 2)).append("($sp)\n");
        }

        code.append("sw\t$fp,\t4($sp)\n");
        code.append("sw\t$ra,\t0($sp)\n");

        return code.toString();
    }

    /**
     * Pops all $s registers, $fp and $sp from the stack
     * @return The assembly code which executes this operation
     */
    private String popFromStack() {
        StringBuilder code = new StringBuilder();
        code.append("\n#Restore from stack\n");

        for (int i = 8 - 1; i >= 0; i--) {
            code.append("lw\t").append("$s").append(i).append(",\t").append(4 * (i + 2)).append("($sp)\n");
        }
        code.append("lw\t$fp,\t4($sp)\n");
        code.append("lw\t$ra,\t0($sp)\n");
        code.append("addi\t$sp,\t$sp,\t");
        code.append((8 * 4) + 8).append("\n");
        code.append("jr\t$ra\n");
        return code.toString();
    }
}
