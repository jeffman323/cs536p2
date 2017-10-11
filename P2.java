import java.util.*;
import java.io.*;
import java_cup.runtime.*;  // defines Symbol

/**
 * This program is to be used to test the Scanner.
 * This version is set up to test all tokens, but more code is needed to test 
 * other aspects of the scanner (e.g., input that causes errors, character 
 * numbers, values associated with tokens)
 */
public class P2 {
    public static void main(String[] args) throws IOException {
                                           // exception may be thrown by yylex
        // test all tokens
        testAllTokens();
        CharNum.num = 1;
    
        // ADD CALLS TO OTHER TEST METHODS HERE
        //The tests to line and character numbers should works exactly the same with non-intliteral
        //tokens, we just use those for convenience of checking
        testLineNumbers();
        testCharNumbers();
        //test 3 bad strings and get a different error from each
        badStringTest();
        //test an int that is too big
        maxIntTest();
        //test the eof.txt file
        eoftest();

    }

    /**
     * testAllTokens
     *
     * Open and read from file allTokens.txt
     * For each token read, write the corresponding string to allTokens.out
     * If the input file contains all tokens, one per line, we can verify
     * correctness of the scanner by comparing the input and output files
     * (e.g., using a 'diff' command).
     */
    private static void testAllTokens() throws IOException {
        // open input and output files
        FileReader inFile = null;
        PrintWriter outFile = null;
        try {
            inFile = new FileReader("allTokens.in");
            outFile = new PrintWriter(new FileWriter("allTokens.out"));
        } catch (FileNotFoundException ex) {
            System.err.println("File allTokens.in not found.");
            System.exit(-1);
        } catch (IOException ex) {
            System.err.println("allTokens.out cannot be opened.");
            System.exit(-1);
        }

        // create and call the scanner
        Yylex scanner = new Yylex(inFile);
        Symbol token = scanner.next_token();
        while (token.sym != sym.EOF) {
            switch (token.sym) {
            case sym.BOOL:
                outFile.println("bool"); 
                break;
			case sym.INT:
                outFile.println("int");
                break;
            case sym.VOID:
                outFile.println("void");
                break;
            case sym.TRUE:
                outFile.println("true"); 
                break;
            case sym.FALSE:
                outFile.println("false"); 
                break;
            case sym.STRUCT:
                outFile.println("struct"); 
                break;
            case sym.CIN:
                outFile.println("cin"); 
                break;
            case sym.COUT:
                outFile.println("cout");
                break;				
            case sym.IF:
                outFile.println("if");
                break;
            case sym.ELSE:
                outFile.println("else");
                break;
            case sym.WHILE:
                outFile.println("while");
                break;
            case sym.RETURN:
                outFile.println("return");
                break;
            case sym.ID:
                outFile.println(((IdTokenVal)token.value).idVal);
                break;
            case sym.INTLITERAL:  
                outFile.println(((IntLitTokenVal)token.value).intVal);
                break;
            case sym.STRINGLITERAL: 
                outFile.println(((StrLitTokenVal)token.value).strVal);
                break;    
            case sym.LCURLY:
                outFile.println("{");
                break;
            case sym.RCURLY:
                outFile.println("}");
                break;
            case sym.LPAREN:
                outFile.println("(");
                break;
            case sym.RPAREN:
                outFile.println(")");
                break;
            case sym.SEMICOLON:
                outFile.println(";");
                break;
            case sym.COMMA:
                outFile.println(",");
                break;
            case sym.DOT:
                outFile.println(".");
                break;
            case sym.WRITE:
                outFile.println("<<");
                break;
            case sym.READ:
                outFile.println(">>");
                break;				
            case sym.PLUSPLUS:
                outFile.println("++");
                break;
            case sym.MINUSMINUS:
                outFile.println("--");
                break;	
            case sym.PLUS:
                outFile.println("+");
                break;
            case sym.MINUS:
                outFile.println("-");
                break;
            case sym.TIMES:
                outFile.println("*");
                break;
            case sym.DIVIDE:
                outFile.println("/");
                break;
            case sym.NOT:
                outFile.println("!");
                break;
            case sym.AND:
                outFile.println("&&");
                break;
            case sym.OR:
                outFile.println("||");
                break;
            case sym.EQUALS:
                outFile.println("==");
                break;
            case sym.NOTEQUALS:
                outFile.println("!=");
                break;
            case sym.LESS:
                outFile.println("<");
                break;
            case sym.GREATER:
                outFile.println(">");
                break;
            case sym.LESSEQ:
                outFile.println("<=");
                break;
            case sym.GREATEREQ:
                outFile.println(">=");
                break;
			case sym.ASSIGN:
                outFile.println("=");
                break;
			default:
				outFile.println("UNKNOWN TOKEN");
            } // end switch

            token = scanner.next_token();
        } // end while
        outFile.close();
    }
    
    /**
     * testLineNumbers
     *
     * Open and read from file lineNumbers.in
     * For each token read, check the line numbers
     */    
    private static void testLineNumbers() throws IOException {
        // open input and output files
        FileReader inFile = null;
        PrintWriter outFile = null;
        try {
            inFile = new FileReader("lineNumbers.in");
            outFile = new PrintWriter(new FileWriter("linenumerrors.out"));
        } catch (FileNotFoundException ex) {
            System.err.println("File lineNumbers.in not found.");
            System.exit(-1);
        } catch (IOException ex) {
            System.err.println("linenumerrors.out cannot be opened.");
            System.exit(-1);
        }

        // create and call the scanner
        Yylex scanner = new Yylex(inFile);
        Symbol token = scanner.next_token();
        
        /*
        iterate through each token, checking the line number they have stored against the line they're on.
        the line they're actually on is the same as the number that they are, so we check against intVal
        The exception to this is the last line, which has 6 as it's value instead, jsut to make sure we aren't 
        storing the same number for linenum and intval 
        */
        while (token.sym != sym.EOF) {
        	
        	if(((IntLitTokenVal)token.value).linenum != ((IntLitTokenVal)token.value).intVal) {
        		//System.out.println("wrong line number. intval = " + ((IntLitTokenVal)token.value).intVal + ", linenum = " + ((IntLitTokenVal)token.value).linenum);
        		outFile.println("wrong line number. intval = " + ((IntLitTokenVal)token.value).intVal + ", linenum = " + ((IntLitTokenVal)token.value).linenum);

        	}
        	token = scanner.next_token();
        	
        }
        outFile.close();
    }
    
    /**
     * testCharNumbers
     *
     * Open and read from file CharNumbers.in
     * For each token read, check the character numbers
     */    
    private static void testCharNumbers() throws IOException {
        // open input and output files
        FileReader inFile = null;
        PrintWriter outFile = null;
        try {
            inFile = new FileReader("charNumbers.in");
            outFile = new PrintWriter(new FileWriter("charnumerrors.out"));
        } catch (FileNotFoundException ex) {
            System.err.println("File charNumbers.in not found.");
            System.exit(-1);
        } catch (IOException ex) {
            System.err.println("charnumerrors.out cannot be opened.");
            System.exit(-1);
        }

        // create and call the scanner
        Yylex scanner = new Yylex(inFile);
        Symbol token = scanner.next_token();
        
        /*
        iterate through each token, checking the char number they have stored against the line they're on.
        the char they're actually at is the same as the number that they are, so we check against intVal
        There are two tokens that aren't the same intval, which should be reflected in the output file. 
        */
        while (token.sym != sym.EOF) {
        	
        	if(((IntLitTokenVal)token.value).charnum != ((IntLitTokenVal)token.value).intVal) {
        		//System.out.println("wrong char number. intval = " + ((IntLitTokenVal)token.value).intVal + ", charnum = " + ((IntLitTokenVal)token.value).charnum);
        		outFile.println("wrong char number. intval = " + ((IntLitTokenVal)token.value).intVal + ", charnum = " + ((IntLitTokenVal)token.value).charnum);
        	}
        	token = scanner.next_token();
        	
        }
        outFile.close();
    }
    
    
    /**
     * badStringTest
     *
     * Open and read from file badStrings.in
     * Should give us the 3 different errors for bad strings
     * 1) Bad strings
     * 2) Unterminated strings
     * 3) Bad unterminated strings
     */    
    private static void badStringTest() throws IOException {
    	System.out.println("");
    	// open input and output files
    	FileReader inFile = null;
        try {
            inFile = new FileReader("badStrings.in");
          
        } catch (FileNotFoundException ex) {
            System.err.println("File badStrings.in not found.");
            System.exit(-1);
        } 
        
        Yylex scanner = new Yylex(inFile);
        Symbol token = scanner.next_token();
        
    	System.out.println("^Three different string errors^\n");

        while (token.sym != sym.EOF) {
        	
        	token = scanner.next_token();
        	
        }
    }
    
    
    
    /**
     * maxIntTest
     *
     * Open and read from file maxInt.in
     * Should give us a warning for using a huge int
     */    
    private static void maxIntTest() throws IOException {
    	// open input and output files
    	FileReader inFile = null;
        try {
            inFile = new FileReader("maxInt.in");
          
        } catch (FileNotFoundException ex) {
            System.err.println("File maxInt.in not found.");
            System.exit(-1);
        } 
        
        Yylex scanner = new Yylex(inFile);
        Symbol token = scanner.next_token();
    	System.out.println("^One max_int error^\n");
       
    }
    
    /**
     * eoftest
     *
     * Open and read from file eof.txt
     * Just run through it and make sure we don't get any errors
     */    
    private static void eoftest() throws IOException {
    	//open input file
        FileReader inFile = null;
        try {
            inFile = new FileReader("eof.txt");
        } catch (FileNotFoundException ex) {
            System.err.println("File not found.");
            System.exit(-1);
        } 
        
        Yylex scanner = new Yylex(inFile);
        Symbol token = scanner.next_token();
        while (token.sym != sym.EOF) { 
        	if(token.sym == sym.EOF){
        		System.out.println("EOF correctly recognized");
        	}
        	token = scanner.next_token();
        }
        
    }   
}
