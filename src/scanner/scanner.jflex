/**
 * This is the JFlex lexer definition for a Mini-Pascal myScanner.
 * This class is where the rules for handling inputs for the Scanner class are created.
 * @author Gabriel Bergstrom
 */

/* Declarations */
package myScanner;
import java.util.HashMap;
%%
%{
  LookUpTable look = new LookUpTable();
%}
%public               /* make output class public */
%class  Scanner       /* Names the produced java file */
%unicode              /* Defines the set of characters the myScanner will work on*/
%function nextToken   /* Renames the yylex() function */
%type   Token         /* Defines the return type of the scanning function */
%line                 /* Keeps track of the current line number */
%eofval{
  return null;
%eofval}
/* Patterns */

other             = .
letter            = [A-Za-z]
digit             = [0-9]
digits            = {digit}{digit}*
optional_fraction = (\.{digits})?
optional_exponent = ((E[\+\-]?){digits})?
number            = {digits}{optional_fraction}{optional_exponent}
id                = {letter}({letter} | {digit})*
symbol            = [=<>+\-*/;,.\[\]():]
symbols           = {symbol}|:=|<=|>=|<>
commentContent    = [^\{\}]
comment           = \{{commentContent}*\}
whitespace        = [ \n\t\r\f]

%%
/* Lexical Rules */

{id}     {
             /* Checks if the lexeme is a keyword and sets the type accordingly or sets the type to ID */
             TokenType type = look.get(yytext());
             if(type != null)
               return (new Token(yytext(), type, yyline));
             Token t = new Token(yytext(), TokenType.ID, yyline);
             return t;
            }
{number}     {
              /* Sets the type to number */
              Token t = new Token(yytext(),TokenType.NUMBER, yyline);
              return t;
            }
{symbols}    {
              /* Sets the type to symbol */
              Token t = new Token(yytext(), look.get(yytext()), yyline);
              return t;
            }
{whitespace}  {  /* Ignore Whitespace */

              }

{other}    {
              /*Prints out illegal syntax and line number when illegal syntax is found*/
             System.out.println("Illegal syntax found:" +"\t"+ yytext() +"\t"+"Line:" + yyline);

             //This will stop the program and throw an error on illegal syntax
           }
