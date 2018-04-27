package compiler;

import codegeneration.CodeGeneration;
import parser.Parser;
import syntaxtree.ProgramNode;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


/**
 * This is the main for the mini-Pascal Compiler, where all of the components of the compiler (MyScanner, Parser, ect.)
 * will be built and put together to create the mini-Pascal Compiler.
 * A user will enter in the file path of the desired input:
 * If the file is a grammatically correct Pascal program and no errors occur then the Compile will state that it compiled successfully
 * and the newly created symbol table will be written to a file.
 * If the file is no good the Compiler will fail and it will state the failure with a semi helpful message of where the error occurred.
 * @author Gabriel Bergstrom
 */
public class CompilerMain {
    public static void main(String args[]){
        String input = args[0];
        Scanner scan = new Scanner(input);
        try {
            Parser parser = new Parser(scan.next(), true);
            ProgramNode tree = parser.program();
            CodeGeneration generation = new CodeGeneration(tree, parser.getSymbolTable());
            System.out.print("Did it successfully compile: YES");

            List<String> lines = Arrays.asList(tree.indentedToString(1));
            Path file = Paths.get("Syntax-Tree.txt");
            Files.write(file, lines, Charset.forName("UTF-8"));

            lines = Arrays.asList(parser.getSymbolTable().toString());
            file = Paths.get("SymbolTable.txt");
            Files.write(file, lines, Charset.forName("UTF-8"));

            lines = Arrays.asList(generation.writeCode());
            file = Paths.get("MIPS.asm");
            Files.write(file, lines, Charset.forName("UTF-8"));
        }
        catch (Exception e){
            System.out.print("Did it successfully compile: NO" +"\n");
            System.err.print("Compiler failed: "+ e.getMessage());
        }
    }
}
