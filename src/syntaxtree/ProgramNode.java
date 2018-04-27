
package syntaxtree;

/**
 * Represents a Pascal Program
 * @author Erik Steinmetz
 */
public class ProgramNode extends SyntaxTreeNode {

    /**
     * Instance variables
     */
    protected String name;
    protected DeclarationsNode variables;
    protected SubProgramDeclarationsNode functions;
    protected CompoundStatementNode main;

    /**
     * Creates a new program node with all instance variables initialized to null
     */
    public ProgramNode() {
        name = null;
        variables = null;
        functions = null;
        main = null;
    }
    
    public ProgramNode( String aName) {
        this.name = aName;
    }

    public DeclarationsNode getVariables() {
        return variables;
    }

    public void setVariables(DeclarationsNode variables) {
        this.variables = variables;
    }

    public SubProgramDeclarationsNode getFunctions() {
        return functions;
    }

    public void setFunctions(SubProgramDeclarationsNode functions) {
        this.functions = functions;
    }

    public CompoundStatementNode getMain() {
        return main;
    }

    public void setMain(CompoundStatementNode main) {
        this.main = main;
    }
    
    /**
     * Creates a String representation of this program node and its children.
     * @param level The tree level at which this node resides.
     * @return A String representing this node.
     */
    @Override
    public String indentedToString( int level) {
        String answer = this.indentation( level);
        answer += "Program: " + name + "\n";
        answer += variables.indentedToString( level + 1);
        answer += functions.indentedToString( level + 1);
        answer += main.indentedToString( level + 1);
        return answer;
    }
}
