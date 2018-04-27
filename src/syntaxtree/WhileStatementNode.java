package syntaxtree;

/**
 * Represents an While statement in Pascal.
 * @author Gabriel Bergstrom
 */
public class WhileStatementNode extends StatementNode {
    private ExpressionNode test;
    private StatementNode doStatement;

    /**
     * Gets the test to be check on each iteration of the While loop
     * @return ExpressionNode that will evaluate to TRUE || FALSE
     */
    public ExpressionNode getTest() {
        return test;
    }

    /**
     * Sets the test to be check on each iteration of the While loop
     * @param test ExpressionNode that will evaluate to TRUE || FALSE
     */
    public void setTest(ExpressionNode test) {
        this.test = test;
    }

    /**
     * Gets the do statement of the While loop
     * @return the do statement of the While loop
     */
    public StatementNode getDoStatement() {
        return doStatement;
    }

    /**
     * Sets the do statement of the While loop
     * @param doStatement the StatementNode
     */
    public void setDoStatement(StatementNode doStatement) {
        this.doStatement = doStatement;
    }

    /**
     * Creates a String representation of this node and its children.
     * @param level The tree level at which this node resides.
     * @return A String representing this node.
     */
    @Override
    public String indentedToString(int level) {
        String answer = this.indentation( level);
        answer += "While\n";
        answer += this.test.indentedToString( level + 1);
        answer += this.indentation( level) +"Do:\n"+ this.doStatement.indentedToString( level + 1);
        return answer;
    }
}
