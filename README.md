####This application is a Mini-Pascal to MIPS assembly compiler written in Java 8 consisting of multiple modules that provide the functionality of the compiler.  
##Module Overview:  
1. #####   The Scanner: generated using JFlex, parses an input pascal file and creates tokens that have a lexeme and a token type for each input.
2. #####   The Parser: created using Java, checks to see if the inputted file is a grammatically correct Pascal program and generates a symbol table.
3. #####   The Symbol Table: created using Java, works with the Parser to put ID's into symbol objects which are stored in a Hash map to improve the efficiency of the Parser.
4. #####   The Compiler Main: created using Java, this is where all of the different pieces that make up the Compiler are brought together to form the mini-Pascal Compiler. Once successfully compiled this class will generate three new files: SymbolTable.txt, SyntaxTree.txt and MIPS.asm.
5. #####   The Syntax Tree: created using Java, works with the Parser to generate nodes of each key part of the Parsed Pascal program and creates a syntax tree.
6. #####   The Code Generation: takes the fully created syntax tree from the Parser and walks through the tree generating each part of the pascal program as MIPS assembly code which will then be written to a file with the CompilerMain class. 
