
package syntaxtree;

import java.util.ArrayList;

/**
 * Represents a set of declarations in a Pascal program.
 * @author Erik Steinmetz
 */
public class DeclarationsNode extends SyntaxTreeNode {
    
    private ArrayList<VariableNode> vars = new ArrayList<VariableNode>();
    
    /**
     * Adds a variable to this declaration.
     * @param aVariable The variable node to add to this declaration.
     */
    public void addVariable( VariableNode aVariable) {
        vars.add( aVariable);
    }

    /**
     * Adds all variables declared in a declaration to this declaration instance.
     * @param declaration a DeclarationNode instance
     */
    public void addAllVariables(DeclarationsNode declaration){
        vars.addAll(declaration.vars);
    }

    /**
     * Gets all variables
     * @return all variables
     */
    public ArrayList<VariableNode> getVariables(){
        return vars;
    }
    /**
     * Creates a String representation of this declarations node and its children.
     * @param level The tree level at which this node resides.
     * @return A String representing this node.
     */
    @Override
    public String indentedToString( int level) {
        String answer = this.indentation( level);
        answer += "Declarations\n";
        for( VariableNode variable : vars) {
            answer += variable.indentedToString( level + 1);
        }
        return answer;
    }
}
