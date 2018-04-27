package parser;

import scanner.MyScanner;
import scanner.Token;
import scanner.TokenType;
import symboltable.SymbolTable;
import syntaxtree.*;

import java.io.*;
import java.util.ArrayList;

/**
 * The parser is whats recognizes whether an input string of tokens from the
 * myScanner is an expression. If all of these functions return without errors then
 * the input file contains acceptable expressions.
 *
 * @author Gabriel Bergstrom
 */
public class Parser {

    //  Instance variables
    private Token lookahead;
    private MyScanner myScanner;
    private SymbolTable symbTable;
    private String lexeme;
    private TokenType type;

    //    Constructors
    public Parser(String text, boolean isFilename) {
        if (isFilename) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(text);
            } catch (FileNotFoundException ex) {
                errorHandling("File not found");
            }
            InputStreamReader isr = new InputStreamReader(fis);
            myScanner = new MyScanner(isr);
        } else {
            myScanner = new MyScanner(new StringReader(text));
        }
        try {
            lookahead = myScanner.nextToken();
        } catch (IOException ex) {
            errorHandling("Scan error");
        }
        symbTable = new SymbolTable();
    }

    /**
     * Get the symbol table from the parse
     *
     * @return The symbol table populated by the parser
     */
    public SymbolTable getSymbolTable() {
        return symbTable;
    }

    /**
     * Matches the expected token with the input.
     * If the current inputted token matches expected then the current token is consumed
     * and the myScanner will move on to the next token.
     * If they do not match then an error message appears and the parser is stopped
     *
     * @param expected
     */
    public void match(TokenType expected) {
        if (this.lookahead.getType() == expected) {
            try {
                this.lookahead = this.myScanner.nextToken();
                if (this.lookahead == null) {
                    this.lookahead = new Token("End of File", null, null);
                }
            } catch (IOException e) {
                errorHandling("MyScanner Exception");
            }
        } else {
            errorHandling("Match expected: " + expected + "\t" +
                    "Match found: " + this.lookahead.getType() + " ");
        }

    }

    /**
     * Used for error handling / messaging within the parser that stop the Compiler process.
     *
     * @param error The error message
     */
    public void errorHandling(String error) {
        throw new RuntimeException(error + "occurred at line: " + myScanner.yylength() + "\n");
    }
    /**
    * Used for throwing errors that don't stop the proccess of the Compiler process.
    *
    * @param error The error message
    */
    public void minorErrorHandling(String error){
      System.out.println("Error: "+error +" Occurred at line: " + myScanner.yylength());
    }

    /**
     * Uses the rule for the program non-terminal symbol in the expression grammar.
     */
    public ProgramNode program() {
        match(TokenType.PROGRAM);
        this.lexeme = this.lookahead.getLexeme();
        match(TokenType.ID);
        if(!symbTable.addProgram(lexeme)){
          minorErrorHandling("Program name: "+ lexeme +" already exists");
        }
        ProgramNode program = new ProgramNode(this.lexeme);
        match(TokenType.SEMI);
        program.setVariables(declarations());
        program.setFunctions(subprogramDeclarations());
        program.setMain(compoundStatement());
        match(TokenType.PERIOD);

        return program;
    }

    /**
     * Uses the rule for the identifier_list non-terminal in the expression grammer.
     */
    public ArrayList<String> identiferList() {
        ArrayList<String> idList = new ArrayList<>();
        this.lexeme = this.lookahead.getLexeme();
        this.type = this.lookahead.getType();
        idList.add(this.lexeme);
        match(TokenType.ID);
        if(!symbTable.addVariable(lexeme, type)){
          minorErrorHandling("Variable name: "+lexeme+" already exists");
        }
        if (this.lookahead.getType() == TokenType.COMMA) {
            match(TokenType.COMMA);
            idList.addAll(identiferList());
        }
        return idList;
    }

    /**
     * Uses the rule for the declarations non-terminal symbol in the expression grammar.
     */
    public DeclarationsNode declarations() {
        DeclarationsNode declarations = new DeclarationsNode();
        if (this.lookahead.getType() == TokenType.VAR) {
            match(TokenType.VAR);
            ArrayList<String> idList = identiferList();
            match((TokenType.COLON));
            TokenType t = type(idList);
            for (String id : idList) {
                declarations.addVariable(new VariableNode(id, t));
            }
            match(TokenType.SEMI);
            declarations.addAllVariables(declarations());
        } else {
            /* do nothing */
        }
        return declarations;
    }

    /**
     * Uses the rule for the type non-terminal symbol in the expression grammar.
     */
    public TokenType type(ArrayList<String> idList) {
        TokenType t = null;
        if (this.lookahead.getType() == TokenType.ARRAY) {
            match(TokenType.ARRAY);
            match(TokenType.LBRACE);
            match(TokenType.NUMBER);
            match(TokenType.COLON);
            match(TokenType.NUMBER);
            match(TokenType.RBRACE);
            match(TokenType.OF);
            t = standardType();
        } else if (this.lookahead.getType() == TokenType.REAL || this.lookahead.getType() == TokenType.INTEGER) {
            t = standardType();
            for (String anID : idList) {
              if(!symbTable.addVariable(anID, t)){
                  minorErrorHandling("Variable name: "+ anID +" already exists");
              }
            }
        }
        return t;
    }

    /**
     * Uses the rule for the standard_type non-terminal symbol in the expression grammar.
     */
    public TokenType standardType() {
        TokenType t = null;
        if (this.lookahead.getType() == TokenType.INTEGER) {
            t = TokenType.INTEGER;
            match(TokenType.INTEGER);
        } else if (this.lookahead.getType() == TokenType.REAL) {
            t = TokenType.REAL;
            match(TokenType.REAL);
        } else {
            errorHandling("standard_type Error: ");
        }
        return t;
    }

    /**
     * Uses the rule for the subprogram_declarations non-terminal symbol in the expression grammar.
     */
    public SubProgramDeclarationsNode subprogramDeclarations() {
        SubProgramDeclarationsNode subpDecs = new SubProgramDeclarationsNode();
        if (this.lookahead.getType() == TokenType.FUNCTION) {
            subpDecs.addSubProgramDeclaration(subprogramDeclaration());
            match(TokenType.SEMI);
            subpDecs.addall(subprogramDeclarations().getProcs());
        } else if (this.lookahead.getType() == TokenType.PROCEDURE) {
            subprogramDeclaration();
            match(TokenType.SEMI);
            subpDecs.addall(subprogramDeclarations().getProcs());
        } else {
            /* do nothing */
        }
        return subpDecs;
    }

    /**
     * Uses the rule for the subprogram_declaration non-terminal symbol in the expression grammar.
     */
    public SubProgramNode subprogramDeclaration() {
        SubProgramNode subP = subprogramHead();
        subP.setVariables(declarations());
        subP.setFunctions(subprogramDeclarations());
        subP.setMain(compoundStatement());
        return subP;
    }

    /**
     * Uses the rule for the subprogram_head non-terminal symbol in the expression grammar.
     */
    public SubProgramNode subprogramHead() {
        SubProgramNode spNode = null;
        if (this.lookahead.getType() == TokenType.FUNCTION) {
            match(TokenType.FUNCTION);
            spNode = new SubProgramNode(this.lookahead.getLexeme());
            this.lexeme = this.lookahead.getLexeme();
            this.type = this.lookahead.getType();
            match(TokenType.ID);
            if(!symbTable.addFunction(lexeme, type)){
              errorHandling("Function name: "+lexeme+" already exists");
            }
            ArrayList<VariableNode> args = arguments();
            ArrayList<TokenType> argsTypes = new ArrayList<>();

//            for (VariableNode var : args) {
//                TokenType t = var.getType();
//                symbTable.get(lexeme).addArguments(t);
//            }
//            symbTable.get(this.lexeme).setArguments(argsTypes);
            match(TokenType.COLON);
            TokenType t = standardType();
            args.add(new VariableNode(this.lexeme, t));
            spNode.setArguments(args);
            symbTable.setType(this.lexeme, t);
            if(!symbTable.addVariable(this.lexeme, t)){
                minorErrorHandling("Variable name: "+lexeme+" already exists");
            }
            match(TokenType.SEMI);
        } else {
            match(TokenType.PROCEDURE);
            this.lexeme = this.lookahead.getLexeme();
            spNode = new SubProgramNode(this.lexeme);
            match(TokenType.ID);
            if(!symbTable.addProcedure(lexeme)){
                minorErrorHandling("Procedure name: "+lexeme+" already exists");
            }
            match(TokenType.SEMI);
        }
        return spNode;
    }

    /**
     * Uses the rule for the arguments non-terminal symbol in the expression grammar.
     */
    public ArrayList<VariableNode> arguments() {
        ArrayList<VariableNode> args = new ArrayList<>();
        if (this.lookahead.getType() == TokenType.LPAREN) {
            match(TokenType.LPAREN);
            args = parameterList();
            match(TokenType.RPAREN);
        } else {
            /*DO NOTHING*/
        }
        return args;
    }

    /**
     * Uses the rule for the parameter_list non-terminal symbol in the expression grammar.
     */
    public ArrayList<VariableNode> parameterList() {
        ArrayList<String> idList = identiferList();
        ArrayList<VariableNode> args = new ArrayList<>();
        match(TokenType.COLON);
        TokenType t = type(idList);
        for (String id : idList) {
            args.add(new VariableNode(id, t));
        }
        if (this.lookahead.getType() == TokenType.SEMI) {
            match(TokenType.SEMI);
            args.addAll(parameterList());
        }
        return args;
    }

    /**
     * Uses the rule for the compound_statement non-terminal symbol in the expression grammar.
     */
    public CompoundStatementNode compoundStatement() {
        match(TokenType.BEGIN);
        CompoundStatementNode compNode = optionalStatements();
        match(TokenType.END);
        return compNode;
    }

    /**
     * Uses the rule for the optional_statements non-terminal symbol in the expression grammar.
     */
    public CompoundStatementNode optionalStatements() {
        CompoundStatementNode compNode = new CompoundStatementNode();
        if (isStatement()) {
            compNode.addAllStatements(statementList());
        } else {
           /*DO NOTHING*/
        }
        return compNode;
    }

    /**
     * Uses the rule for the statements_list non-terminal symbol in the expression grammar.
     */
    public ArrayList<StatementNode> statementList() {
        ArrayList<StatementNode> nodes = new ArrayList<>();
        nodes.add(statement());
        if (this.lookahead.getType() == TokenType.SEMI) {
            match(TokenType.SEMI);
            nodes.addAll(statementList());
        }
        return nodes;
    }

    /**
     * Uses the rule for the statement non-terminal symbol in the expression grammar.
     */
    public StatementNode statement() {
        StatementNode statement = null;
        this.lexeme = this.lookahead.getLexeme();
        if (this.lookahead.getType() == TokenType.ID) {
            if (symbTable.isVariable(this.lexeme)) {
                AssignmentStatementNode assign = new AssignmentStatementNode();
                VariableNode variableNode = variable();
                assign.setLvalue(variableNode);
                match(TokenType.ASSIGN);
                ExpressionNode expressNode = expression();
                assign.setExpression(expressNode);
                if(!variableNode.getType().equals(expressNode.getType())){
                    minorErrorHandling("Type mismatch has occurred for: "+ variableNode.getName() + " ");
                }
                return assign;
            } else if (symbTable.isProcedure(this.lexeme)) {
                return procedureStatement();
            } else {
                errorHandling("Statement Error: ID is not kind VARIABLE or PROCEDURE ");
            }
        } else if (this.lookahead.getType() == TokenType.BEGIN) {
            statement = compoundStatement();
        } else if (this.lookahead.getType() == TokenType.IF) {
            IfStatementNode ifStateNode = new IfStatementNode();
            match(TokenType.IF);
            ifStateNode.setTest(expression());
            match(TokenType.THEN);
            ifStateNode.setThenStatement(statement());
            match(TokenType.ELSE);
            ifStateNode.setElseStatement(statement());
            return ifStateNode;
        } else if (this.lookahead.getType() == TokenType.WHILE) {
            WhileStatementNode whileState = new WhileStatementNode();
            match(TokenType.WHILE);
            whileState.setTest(expression());
            match(TokenType.DO);
            whileState.setDoStatement(statement());
            return whileState;
        } else {
            errorHandling("Statement Error: ");
        }
        return statement;
    }

    /**
     * Uses the rule for the variable non-terminal symbol in the expression grammar.
     */
    public VariableNode variable() {
        String variableName = this.lookahead.getLexeme();
        this.lexeme = this.lookahead.getLexeme();
        this.type = this.lookahead.getType();
        match(TokenType.ID);
        if(!symbTable.addVariable(lexeme, type)){
            minorErrorHandling("Variable name: "+lexeme+" already exists");
        }
        VariableNode variable = new VariableNode(variableName);
        variable.setType(symbTable.getType(variableName));
        if (this.lookahead.getType() == TokenType.LBRACE) {
            match(TokenType.LBRACE);
            expression();
            match(TokenType.RBRACE);
        }
        return variable;
    }

    /**
     * Uses the rule for the procedure_statement non-terminal symbol in the expression grammar.
     */
    public ProcedureStatementNode procedureStatement() {
        ProcedureStatementNode procState = new ProcedureStatementNode(this.lookahead.getLexeme());
        this.lexeme = this.lookahead.getLexeme();
        match(TokenType.ID);
        if(!symbTable.addProcedure(lexeme)){
            minorErrorHandling("Procedure name already exists");
        }
        if (this.lookahead.getType() == TokenType.LPAREN) {
            match(TokenType.LPAREN);
            ArrayList<ExpressionNode> expressionList = expressionList();
            procState.addAllExpressionNodes(expressionList);
            match(TokenType.RPAREN);
        }
        return procState;
    }

    /**
     * Uses the rule for the expression_list non-terminal symbol in the expression grammar.
     */
    public ArrayList<ExpressionNode> expressionList() {
        ArrayList<ExpressionNode> expressNode = new ArrayList<>();
        expressNode.add(expression());
        if (this.lookahead.getType() == TokenType.COMMA) {
            match(TokenType.COMMA);
            expressNode.addAll(expressionList());
        }
        return expressNode;
    }

    /**
     * Uses the rule for the expression non-terminal symbol in the expression grammar.
     */
    public ExpressionNode expression() {
        ExpressionNode expressLeft = simpleExpression();
        TokenType typeLeft = expressLeft.getType();
        if (isRelop()) {
            OperationNode operation = new OperationNode(this.lookahead.getType());
            if (typeLeft.equals(TokenType.REAL)) {
                operation.setType(TokenType.REAL);
            } else {
                operation.setType(TokenType.INTEGER);
            }
            operation.setLeft(expressLeft);
            relop();
            operation.setRight(simpleExpression());
            return operation;
        }
        return expressLeft;
    }

    /**
     * Uses the rule for the simple_expression non-terminal symbol in the expression grammar.
     */
    public ExpressionNode simpleExpression() {
        ExpressionNode expressNode = null;
        if (isSimpleExpression()) {
            expressNode = term();
            expressNode = simplePart(expressNode);
        } else if (this.lookahead.getType() == TokenType.PLUS || this.lookahead.getType() == TokenType.MINUS) {
            UnaryOperationNode unaryOperationNode = sign();
            expressNode = term();
            unaryOperationNode.setType(expressNode.getType());
            unaryOperationNode.setExpression(simplePart(expressNode));
            return unaryOperationNode;
        } else {
            errorHandling("Simple_Expression error: ");
        }
        return expressNode;
    }

    /**
     * Uses the rule for the simple_part non-terminal symbol in the expression grammar.
     */
    public ExpressionNode simplePart(ExpressionNode positionLeft) {
        if (isAddop()) {
            OperationNode operation = new OperationNode(this.lookahead.getType());
            addop();
            ExpressionNode expressRight = term();
            operation.setLeft(positionLeft);
            operation.setRight(simplePart(expressRight));
            return operation;
        }
        return positionLeft;
    }

    /**
     * Uses the rule for the term non-terminal symbol in the expression grammar.
     */
    public ExpressionNode term() {
        ExpressionNode expressLeft = factor();
        return termPart(expressLeft);
    }

    /**
     * Uses the rule for the term_part non-terminal symbol in the expression grammar.
     */
    private ExpressionNode termPart(ExpressionNode expressLeft) {
        if (isMulop()) {
            OperationNode operation = new OperationNode(this.lookahead.getType());
            mulop();
            ExpressionNode expressRight = factor();
            operation.setLeft(expressLeft);
            operation.setRight(termPart(expressRight));
            return operation;
        } else {
            /*DO NOTHING*/
        }
        return expressLeft;
    }

    /**
     * Uses the rule for the factor non-terminal symbol in the expression grammar.
     */
    public ExpressionNode factor() {
        ExpressionNode expressNode = null;
        if (this.lookahead.getType() == TokenType.ID) {
            this.lexeme = this.lookahead.getLexeme();
            match(TokenType.ID);
            TokenType t = symbTable.getType(this.lexeme);
            if (this.lookahead.getType() == TokenType.LBRACE) {
                match(TokenType.LBRACE);
                expression();
                match(TokenType.RBRACE);
            } else if (this.lookahead.getType() == TokenType.LPAREN) {
                match(TokenType.LPAREN);
                expressionList();
                match(TokenType.RPAREN);
            } else {
                VariableNode variable = new VariableNode(this.lexeme);
                variable.setType(t);
                return variable;
            }
        } else if (this.lookahead.getType() == TokenType.NUMBER) {
            TokenType type;
            String number = this.lookahead.getLexeme();
            if (number.contains(".")) {
                type = TokenType.REAL;
            } else {
                type = TokenType.INTEGER;
            }
            VariableNode variable = new VariableNode(number);
            variable.setType(type);
            match(TokenType.NUMBER);
            return variable;
        } else if (this.lookahead.getType() == TokenType.LPAREN) {
            match(TokenType.LPAREN);
            expressNode = expression();
            match(TokenType.RPAREN);
        } else if (this.lookahead.getType() == TokenType.NOT) {
            UnaryOperationNode sign = new UnaryOperationNode(TokenType.NOT);
            match(TokenType.NOT);
            expressNode = factor();
            sign.setExpression(expressNode);
            sign.setType(expressNode.getType());
            return sign;
        } else {
            errorHandling("Factor error: ");
        }
        return expressNode;
    }

    /**
     * Uses the rule for the sign non-terminal symbol in the expression grammar.
     */
    public UnaryOperationNode sign() {
        UnaryOperationNode unaryOperationNode = null;
        if (this.lookahead.getType() == TokenType.PLUS) {
            unaryOperationNode = new UnaryOperationNode(TokenType.PLUS);
            match(TokenType.PLUS);
        } else if (this.lookahead.getType() == TokenType.MINUS) {
            unaryOperationNode = new UnaryOperationNode(TokenType.MINUS);
            match(TokenType.MINUS);
        } else {
            errorHandling("Sign error: ");
        }
        return unaryOperationNode;
    }

    /**
     * Utility function that checks if the next token is an addop.
     *
     * @return If the next token is not an addop it will return false.
     */
    private boolean isAddop() {
        if (this.lookahead.getType() == TokenType.PLUS) {
            return true;
        } else if (this.lookahead.getType() == TokenType.MINUS) {
            return true;
        } else if (this.lookahead.getType() == TokenType.OR) {
            return true;
        } else {
            /*DO NOTHING*/
            return false;
        }
    }

    /**
     * Utility function that checks which addop the next token is and then calls the match function.
     */
    private void addop() {
        if (this.lookahead.getType() == TokenType.PLUS) {
            match(TokenType.PLUS);
        } else if (this.lookahead.getType() == TokenType.MINUS) {
            match(TokenType.MINUS);
        } else if (this.lookahead.getType() == TokenType.OR) {
            match(TokenType.OR);
        } else {
            /*DO NOTHING*/
        }
    }

    /**
     * Utility function that checks if the next token is an addop.
     *
     * @return If the next token is not an mulop it will return false.
     */
    private boolean isMulop() {
        if (this.lookahead.getType() == TokenType.ASTERISK) {
            return true;
        } else if (this.lookahead.getType() == TokenType.FSLASH) {
            return true;
        } else if (this.lookahead.getType() == TokenType.DIV) {
            return true;
        } else if (this.lookahead.getType() == TokenType.MOD) {
            return true;
        } else if (this.lookahead.getType() == TokenType.AND) {
            return true;
        } else {
            /*DO NOTHING*/
            return false;
        }
    }

    /**
     * Utility function that checks which mulop the next token is and then calls the match function.
     */
    private void mulop() {
        if (this.lookahead.getType() == TokenType.ASTERISK) {
            match(TokenType.ASTERISK);
        } else if (this.lookahead.getType() == TokenType.FSLASH) {
            match(TokenType.FSLASH);
        } else if (this.lookahead.getType() == TokenType.DIV) {
            match(TokenType.DIV);
        } else if (this.lookahead.getType() == TokenType.MOD) {
            match(TokenType.MOD);
        } else if (this.lookahead.getType() == TokenType.AND) {
            match(TokenType.AND);
        } else {
            /*DO NOTHING*/
        }
    }

    /**
     * Utility function that checks if the next token is an addop.
     *
     * @return If the next token is not an mulop it will return false.
     */
    private boolean isRelop() {
        if (this.lookahead.getType() == TokenType.EQUAL) {
            return true;
        } else if (this.lookahead.getType() == TokenType.NOTEQ) {
            return true;
        } else if (this.lookahead.getType() == TokenType.LTHAN) {
            return true;
        } else if (this.lookahead.getType() == TokenType.LTHANEQ) {
            return true;
        } else if (this.lookahead.getType() == TokenType.GTHANEQ) {
            return true;
        } else if (this.lookahead.getType() == TokenType.GTHAN) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Utility function that checks which mulop the next token is and then calls the match function.
     */
    private void relop() {
        if (this.lookahead.getType() == TokenType.EQUAL) {
            match(TokenType.EQUAL);
        } else if (this.lookahead.getType() == TokenType.NOTEQ) {
            match(TokenType.NOTEQ);
        } else if (this.lookahead.getType() == TokenType.LTHAN) {
            match(TokenType.LTHAN);
        } else if (this.lookahead.getType() == TokenType.LTHANEQ) {
            match(TokenType.LTHANEQ);
        } else if (this.lookahead.getType() == TokenType.GTHANEQ) {
            match(TokenType.GTHANEQ);
        } else if (this.lookahead.getType() == TokenType.GTHAN) {
            match(TokenType.GTHAN);
        } else {
            errorHandling("Relop error: ");
        }
    }

    /**
     * Checks if the current lexeme is a statement
     *
     * @return True if the current lexeme is a statement
     */
    private boolean isStatement() {
        if (symbTable.isVariable(this.lookahead.getLexeme())) {
            return true;
        } else if (symbTable.isProcedure(this.lookahead.getLexeme())) {
            return true;
        } else if (this.lookahead.getType() == TokenType.BEGIN) {
            return true;
        } else if (this.lookahead.getType() == TokenType.IF) {
            return true;
        } else if (this.lookahead.getType() == TokenType.WHILE) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if the current lexeme is a simple_expression
     *
     * @return True if the current lexeme is a simple_expression
     */
    private boolean isSimpleExpression() {
        if (this.lookahead.getType() == TokenType.ID || this.lookahead.getType() == TokenType.NUMBER || this.lookahead.getType() == TokenType.LPAREN || this.lookahead.getType() == TokenType.NOT) {
            return true;
        } else {
            return false;
        }
    }
}
