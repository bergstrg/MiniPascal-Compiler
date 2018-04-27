package syntaxtree;

import scanner.TokenType;

/**
 * Represents the PLUS, MINUS, and NOT operation in an expression.
 * Created by gabe on 2/27/18.
 */
public class UnaryOperationNode extends ExpressionNode {
    private ExpressionNode expression;
    private TokenType op;

    public UnaryOperationNode(TokenType type){
        this.op = type;
    }

    /**
     * Gets the current expression
     * @return the current expression
     */
    public ExpressionNode getExpression() {
        return expression;
    }

    /**
     * Sets the current expression
     * @param expression ExpressionNode with the current expression
     */
    public void setExpression(ExpressionNode expression) {
        this.expression = expression;
    }

    /**
     * Gets the current operation
     * @return the current operation
     */
    public TokenType getOp() {
        return op;
    }

    /**
     * Sets the current operation
     * @param type a TokenType of the operation: NOT || PLUS || MINUS
     */
    public void setOp(TokenType type) {
        this.op = type;
    }

    @Override
    public String indentedToString(int level) {
        String answer = this.indentation(level);
        answer += "Sign: " + this.op + ", Type: " + type + "\n";
        answer += expression.indentedToString(level + 1);
        return answer;
    }

}
