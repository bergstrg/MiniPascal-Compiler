package syntaxtree;

import scanner.TokenType;

import java.util.ArrayList;

/**
 * Represents a Subprogram
 * Created by gabe on 2/22/18.
 */
public class SubProgramNode extends ProgramNode {

    private TokenType returnType;//Holds the return type for the subprogram: Function:REAL||INTEGER / Procedure:NULL
    private ArrayList<VariableNode>arguments;//Holds all the subprogram function arguments.

    public SubProgramNode(String aName){
        this.name = aName;
    }

    /**
     * Gets the name of the function/procedure
     * @return the name of the function/procedure
     */
    public String getName(){
        return name;
    }

    /**
     * Gets the main function of the subprogram
     * @return a CompoundStatement Node as the main function of the subprogram
     */
    public CompoundStatementNode getMain(){
        return main;
    }

    /**
     * Gets the variables set in the subprogram
     * @return the variables set in the subprogram
     */
    public DeclarationsNode getVariables() {
        return variables;
    }

    /**
     * Gets the functions set in the subprogram
     * @return the functions set in the subprogram
     */
    public SubProgramDeclarationsNode getFunctions() {
        return functions;
    }

    /**
     * Gets the arguments of the subprogram
     * @return an arraylist containing all arguments of the subprogram
     */
    public ArrayList<VariableNode> getArguments(){
        return arguments;
    }
    /**
     * Sets the main function of the subprogram
     * @param main a CompoundStatement Node as the main function of the subprogram
     */
    public void setMain(CompoundStatementNode main){
        this.main = main;
    }

    /**
     * Sets the variables set in the subprogram
     * @param variables the variables set in the subprogram
     */
    public void setVariables(DeclarationsNode variables) {
        this.variables = variables;
    }

    /**
     * Sets the functions of the subprogram
     * @param functions the functions of the subprogram
     */
    public void setFunctions(SubProgramDeclarationsNode functions) {
        this.functions = functions;
    }

    /**
     * Sets the return type of the function
     * @param type the return type of the function(REAL || INTEGER)
     */
    public void setReturnType(TokenType type){
        this.returnType = type;
    }

    /**
     * Sets the arraylist of arguments of the subprogram
     * @param arguments the arraylist of arguments of the subprogram
     */
    public void setArguments(ArrayList<VariableNode> arguments) {
        this.arguments = arguments;
    }

    /**
     * Creates a String representation of this program node and its children.
     * @param level The tree level at which this node resides.
     * @return A String representing this node.
     */
    @Override
    public String indentedToString( int level) {
        String answer = this.indentation( level);
        answer += "SubProgram: " + name;
        answer += ", Return: " + returnType +"\n";
        answer += (this.indentation(level)+"Arguments: ");
        if(!arguments.equals(null)){
            for (int i = 0; i < arguments.size(); i++) {
                if (i != arguments.size() - 1){
                    answer += arguments.get(i).getName() + "[" + arguments.get(i).getType() + "], "+"\n";
                }
                else {
                    answer += arguments.get(i).getName() + "[" + arguments.get(i).getType() + "]"+"\n";
                }
            }
        }
        answer += variables.indentedToString( level + 1);
        answer += functions.indentedToString( level + 1);
        answer += main.indentedToString( level + 1);
        return answer;
    }
}
