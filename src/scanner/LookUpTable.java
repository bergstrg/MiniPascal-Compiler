package scanner;
/**
 * @author Gabriel Bergstrom
 * This is a Hashmap that will act as the lookup table.
 * This class will be called whenever the scanner is checking if the current lexeme is a key word or symbol
 * and will assign a token type accordingly
 * */
import java.util.HashMap;
public class LookUpTable extends HashMap<String, TokenType>
{
    public LookUpTable(){
        this.put("and", TokenType.AND);
        this.put("array", TokenType.ARRAY);
        this.put("begin", TokenType.BEGIN);
        this.put("div", TokenType.DIV);
        this.put("do", TokenType.DO);
        this.put("else", TokenType.ELSE);
        this.put("end", TokenType.END);
        this.put("function",TokenType.FUNCTION);
        this.put("if", TokenType.IF);
        this.put("integer", TokenType.INTEGER);
        this.put("mod", TokenType.MOD);
        this.put("not", TokenType.NOT);
        this.put("of", TokenType.OF);
        this.put("or", TokenType.OR);
        this.put("procedure", TokenType.PROCEDURE);
        this.put("program", TokenType.PROGRAM);
        this.put("real", TokenType.REAL);
        this.put("then", TokenType.THEN);
        this.put("var", TokenType.VAR);
        this.put("while", TokenType.WHILE);
        this.put(";", TokenType.SEMI);
        this.put(",", TokenType.COMMA);
        this.put(".", TokenType.PERIOD);
        this.put(":", TokenType.COLON);
        this.put("[", TokenType.LBRACE);
        this.put("]", TokenType.RBRACE);
        this.put("(", TokenType.LPAREN);
        this.put(")", TokenType.RPAREN);
        this.put("+", TokenType.PLUS);
        this.put("-", TokenType.MINUS);
        this.put("=", TokenType.EQUAL);
        this.put("<>",TokenType.NOTEQ);
        this.put("<", TokenType.LTHAN);
        this.put("<=",TokenType.LTHANEQ);
        this.put(">", TokenType.GTHAN);
        this.put(">=",TokenType.GTHANEQ);
        this.put("*", TokenType.ASTERISK);
        this.put("/", TokenType.FSLASH);
        this.put(":=",TokenType.ASSIGN);
        this.put("read",TokenType.READ);
        this.put("write",TokenType.WRITE);
    }
}
