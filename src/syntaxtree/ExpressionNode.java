package syntaxtree;

import scanner.TokenType;

/**
 * General representation of any expression.
 * @author Gabriel Bergstrom
 */
public abstract class ExpressionNode extends SyntaxTreeNode {

    /** The name of the variable associated with this node. */
    TokenType type;

    /**
     * Creates an ExpressionNode with no type
     */
    public ExpressionNode(){
        this.type = null;
    }

    /**
     * Creates an ExpressionNode with an inputted type
     * @param t an inputted type
     */
    public ExpressionNode(TokenType t){
        this.type = t;
    }

    /**
     * Gets the type of the ExpressionNode
     * @return the type of the ExpressionNode: INTEGER OR REAL
     */
    public TokenType getType() {
        return type;
    }

    /**
     * Sets the type of the EXpressionNode
     * @param type the type of the ExpressionNode: INTEGER OR REAL
     */
    public void setType(TokenType type) {
        this.type = type;
    }
}
