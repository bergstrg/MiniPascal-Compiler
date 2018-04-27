package parser;

/**
 * Created by gabe on 1/25/18.
 */
public class ParserMain {
    public static void main (String args[]){
        Parser p = new Parser("/Users/gabe/IdeaProjects/BergstromCompiler/src/Testing/simplest.pas", true);
        p.program();
        System.out.print("-------------------------------" + "\n");
        Parser p1 = new Parser("/Users/gabe/IdeaProjects/BergstromCompiler/src/Testing/simplestError.pas", true
        );
        p1.program();
    }
}
