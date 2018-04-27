package symboltable;

import scanner.TokenType;

import java.util.HashMap;
import java.util.Stack;

/**
 * This class is the SymbolTable class:
 * Initially this class will be used with the Parser class to store all the different ID types inputted by the Pascal program.
 * This class will be using a Stack data structure to store various Hashmaps that will contain symbol objects stored to be used
 * by the Parser. For now This class will be taking in ID's from the Parser and creating Symbol objects that will then be stored in a Hashmap.
 * Each symbol object will have the name of the ID, it's lexeme, an assigned enum KIND, and a type if necessary(real/int).
 * There are two types of functions: adding and is functions, that will be used with the Parser for adding to the SymbolTable and
 * checking inputs for what KIND they are once they have been assigned to the Hashmap.
 * As the compiler grows and different input types need to be stored more functions will be added for storage within the SymbolTable.
 *
 * @author Gabriel Bergstrom
 */
public class SymbolTable {

    // Instance Variables
    private Stack<HashMap<String, Symbol>>symbTable;

    // Constructors

    /**
     * Initializes the HashMap.
     */
    public SymbolTable() {
        symbTable = new Stack<>();
        symbTable.push(new HashMap<>());
    }

    // Methods

    /**
     * Takes in a String and creates/stores a Symbol object with the kind PROGRAM.
     */
     public boolean addProgram(String lex) {
         HashMap<String, Symbol> currentTable = symbTable.pop();
         if(!currentTable.containsKey(lex)){
           currentTable.put(lex, new Symbol(lex, Kind.PROGRAM));
           symbTable.push(currentTable);
           return true;
         }
         symbTable.push(currentTable);
         return false;
     }

    /**
     * Takes in a String and creates/stores a Symbol object with the kind VARIABLE.
     */
    public boolean addVariable(String lex, TokenType type) {
        HashMap<String, Symbol> currentTable = symbTable.pop();
        if(!currentTable.containsKey(lex)){
          currentTable.put(lex, new Symbol(lex, Kind.VARIABLE, type));
          symbTable.push(currentTable);
          return true;
        }
        symbTable.push(currentTable);
        return false;
    }

    /**
    * Takes in a String and creates/stores a Symbol object with the kind PROCEDURE.
    */
    public boolean addProcedure(String lex) {
      HashMap<String, Symbol> currentTable = symbTable.pop();
      if(!currentTable.containsKey(lex)){
        currentTable.put(lex, new Symbol(lex, Kind.PROCEDURE));
        symbTable.push(currentTable);
        return true;
      }
      symbTable.push(currentTable);
      return false;
    }

    /**
    * Takes in a String and creates/stores a Symbol object with the kind FUNCTION.
      */
      public boolean addFunction(String lex, TokenType type) {
        HashMap<String, Symbol> currentTable = symbTable.pop();
        if(!currentTable.containsKey(lex)){
          currentTable.put(lex, new Symbol(lex, Kind.FUNCTION));
          symbTable.push(currentTable);
          return true;
        }
        symbTable.push(currentTable);
        return false;
    }

    /**
     * @param lex a String input
     * @return if the String input lex is not of kind PROGRAM : false will be returned.
     */
    public boolean isProgram(String lex) {
        HashMap<String, Symbol> currentTable = symbTable.pop();
        if (currentTable.containsKey(lex)) {
            if (currentTable.get(lex).getKind() == Kind.PROGRAM) {
                symbTable.push(currentTable);
                return true;
            }
        }
        symbTable.push(currentTable);
        return false;
    }

    /**
     * @param lex a String input
     * @return if the String input lex is not of kind VARIABLE : false will be returned.
     */
    public boolean isVariable(String lex) {
        HashMap<String, Symbol> currentTable = symbTable.pop();
        if (currentTable.containsKey(lex)) {
            if (currentTable.get(lex).getKind() == Kind.VARIABLE) {
                symbTable.push(currentTable);
                return true;
            }
        }
        symbTable.push(currentTable);
        return false;
    }

    /**
     * @param lex a String input
     * @return if the String input lex is not of kind PROCEDURE : false will be returned.
     */
    public boolean isProcedure(String lex) {
        HashMap<String, Symbol> currentTable = symbTable.pop();
        if (currentTable.containsKey(lex)) {
            if (currentTable.get(lex).getKind() == Kind.PROCEDURE) {
                symbTable.push(currentTable);
                return true;
            }
        }
        symbTable.push(currentTable);
        return false;
    }

    /**
     * @param lex a String input
     * @return if the String input lex is not of kind FUNCTION : false will be returned.
     */
    public boolean isFunction(String lex) {
        HashMap<String, Symbol> currentTable = symbTable.pop();
        if (currentTable.containsKey(lex)) {
            if (currentTable.get(lex).getKind() == Kind.FUNCTION) {
                symbTable.push(currentTable);
                return true;
            }
        }
        symbTable.push(currentTable);
        return false;
    }

    public Symbol get(String name){
        for(int i = symbTable.size()-1 ; i>=0; i--){
            if(symbTable.get(i).containsKey(name)){
                return symbTable.get(i).get(name);
            }
        }
        return null;
    }
    /**
     * Get the Type of a specific symbol (REAL/INTEGER)
     *
     * @param name Name of symbol we are looking for
     * @return The Symbol found or null if not found
     */
    public TokenType getType(String name) {
        for (int i = symbTable.size() - 1; i >= 0; i--) {
            if (symbTable.elementAt(i).containsKey(name)) return symbTable.elementAt(i).get(name).getType();
        }
        return null;
    }

    /**
     * Set the type of a specific Symbol
     *
     * @param name Name of the Symbol
     * @param t    Type to set
     */
    public void setType(String name, TokenType t) {
        for (int i = symbTable.size() - 1; i >= 0; i--) {
            if (symbTable.elementAt(i).containsKey(name)) {
                symbTable.elementAt(i).get(name).setType(t);
                return;
            }
        }
    }

    /**
     * Returns a String representation of a SymbolTable
     *
     * @return a String representation of the object
     */
    @Override
    public String toString() {
        String text = "";
        for (HashMap<String, Symbol> idTable : symbTable) {
            text = idTable.toString().replaceAll("[=]", " = ").replaceAll("\n,", "\n");
            text += "\n";
        }
        return "SymbolTable { \n" + "Global Table = \n" + text + '}';
    }



    /**
     * A data structure to store each symbol in the symbol table. A symbol always has a name and a kind. Depending on
     * the type of symbol different constructors will be called. The different constructors take in different
     * information and store it in a symbol object. Class is protected so it can be accessed for testing.
     */
    public class Symbol {

        private String id;
        private Kind kind;
        private TokenType type;
        private String memoryAddress;

        /**
         * Creates a Symbol to store a program or procedure symbol. Programs store the id name and the kind.
         *
         * @param i id of the program
         * @param k Kind enum (PROGRAM)
         */
        public Symbol(String i, Kind k) {
            id = i;
            kind = k;
        }

        /**
         * Creates a symbol to store a variable or function symbol. Variables store the variable id, kind and type (int/real).
         *
         * @param i id of the variable
         * @param k Kind enum (VARIABLE)
         * @param t type of the variable (int/real)
         */
        public Symbol(String i, Kind k, TokenType t) {
            id = i;
            kind = k;
            type = t;
        }

        /**
         * Gets the kind of the symbol
         *
         * @return A Kind enum, either PROGRAM, ARRAY, VARIABLE or FUNCTION
         */
        public Kind getKind() {
            return kind;
        }

        /**
         * Gets the id of the symbol
         *
         * @return A String with the symbol id
         */
        public String getId() {
            return id;
        }

        /**
         * Gets the type of the symbol
         *
         * @return A Type enum of return type or variable type
         */
        public TokenType getType() {
            return type;
        }

        /**
         * Set the Type of a Symbol
         *
         * @param t Type to be set
         */
        public void setType(TokenType t) {
            type = t;
        }

        /**
         * Gets the memory address of the Symbol for MIPS Generation
         * @return the memory address of the Symbol for MIPS Generation
         */
        public String getMemoryAddress() {
            return memoryAddress;
        }

        /**
         *  Sets the memory address of the Symbol for MIPS Generation
         * @param memoryAddress the memory address of the Symbol
         */
        public void setMemoryAddress(String memoryAddress) {
            this.memoryAddress = memoryAddress;
        }
        /**
         * @return a string version of the object
         */
        @Override
        public String toString() {
            StringBuilder str = new StringBuilder("( id=");
            str.append(id);
            str.append(", Kind=");
            str.append(kind);
            str.append(", Type=");
            str.append(type);
            str.append(" )\t\n");
            return str.toString();
        }
    }
}
