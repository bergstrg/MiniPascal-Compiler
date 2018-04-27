package syntaxtree;

import java.util.ArrayList;

/**
 * Represents a Procedure Statement
 * Created by gabe on 2/27/18.
 */
public class ProcedureStatementNode extends StatementNode {

    private String name;
    private ArrayList<ExpressionNode> arguments = new ArrayList<>();

    public ProcedureStatementNode(String name){
        this.name = name;
    }

    /**
     * Adds an ArrayList of ExpressionNodes for the function arguments
     * @param input
     */
    public void addAllExpressionNodes(ArrayList<ExpressionNode> input){
        arguments.addAll(input);
    }

    /**
     * Adds an argument ExpressionNode for the procedure
     * @param expression
     */
    public void addArguments(ExpressionNode expression){
        arguments.add(expression);
    }

    /**
     * Gets the name of the statement
     * @return the name of the statement
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the statement
     * @param name A String containg the name of the statement
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the ArrayList of ExpressionNodes from the procedure
     * @return ArrayList of ExpressionNodes
     */
    public ArrayList<ExpressionNode> getArguments() {
        return arguments;
    }

    /**
     * Creates a String representation of this node and its children.
     * @param level The tree level at which this node resides.
     * @return A String representing this node.
     */
    @Override
    public String indentedToString(int level) {
        String answer = this.indentation( level);
        answer += "Procedure:";
        answer += this.name + "\n";
        for (ExpressionNode exp : arguments) {
            answer += exp.indentedToString(level + 1);
        }
        return answer;
    }
}
