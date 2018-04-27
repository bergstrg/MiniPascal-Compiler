package scanner;
/**
 * This enum class contains all of the different Types a token can be. There is a type for every keyword and symbol as
 * well as types for ID, NUMBER, READ, and WRITE.
 * @author Gabriel Bergstrom
 */
public enum TokenType {
    //Types
    ID, NUMBER, READ, WRITE,

    //Keywords
    AND, ARRAY, BEGIN, DIV, DO, ELSE, END, FUNCTION, IF, INTEGER, MOD, NOT, OF, OR, PROCEDURE, PROGRAM, REAL, THEN, VAR, WHILE,

    //Symbols
    SEMI, COMMA, PERIOD, COLON, LBRACE, RBRACE, LPAREN, RPAREN, PLUS, MINUS, EQUAL, NOTEQ, LTHAN, LTHANEQ, GTHAN, GTHANEQ, ASTERISK, FSLASH, ASSIGN
}

