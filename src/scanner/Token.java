package scanner;
/**
 * This class contains the definition of a Token object. A token represents one lexeme of the file being parsed. Each
 * token has a lexeme which contains a string with the actual contents and a Type which is a value from the enum
 * class Type.
 * @author Gabriel Bergstrom
 */
public class Token
{
    /**
     * Variables needed to create the token object
     */
    private String lexeme;
    private TokenType type;
    private Integer lineNumber;

    /**
     *
     * @param lex   The lexeme of the input
     * @param tType The type of the input
     * @param line  The line number of the input
     */
    public Token( String lex, TokenType tType, Integer line){
        this.lexeme = lex;
        this.type = tType;
        this.lineNumber = line;
    }

    /**
     * @return The lexeme for the current input
     */
    public String getLexeme(){
        return this.lexeme;
    }

    /**
     * @return The token type for the current input
     */
    public TokenType getType(){
        return this.type;
    }
    /**
     * @return The current line number of the input
     */
    public Integer getLine(){
        return this.lineNumber;
    }

    /**
     * @return A formatted string with the current token's lexeme, type, and line number
     */
    @Override
    public String toString() { return "Token:" + "\t" + "Lexeme:" + this.lexeme + "\t" + "Type:" + this.type + "\t"+"Line:" + this.lineNumber;}

    /**
     *
     * @param o
     * @return A boolean value regarding whether two tokens have equal types and lexemes.
     */
    @Override
    public boolean equals (Object o){
        if(!(o instanceof Token)){return false;}
        Token other = (Token) o;
        boolean answer = true;
        //Compares the lexeme
        if(!this.lexeme.equals(other.lexeme)){ answer = false;}
        //Compares the type
        if(this.type != other.type){answer = false;}
        return answer;
    }
}

