/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */

grammar Configuration;

// This puts a package statement at the top of the output Java files.
@header {
package expressivo.parser;
// Do not edit this .java file! Edit the grammar in Expression.g4 and re-run Antlr.
}

// This adds code to the generated parser.
@members {
    // This method makes the lexer or parser stop running if it encounters
    // invalid input and throw a ParseCancellationException.
    public void reportErrorsAsExceptions() {
        // To prevent any reports to standard error, add this line:
        // removeErrorListeners();
        
        addErrorListener(new BaseErrorListener() {
            public void syntaxError(Recognizer<?, ?> recognizer,
                                     Object offendingSymbol,
                                     int line, int charPositionInLine,
                                     String msg, RecognitionException e) {
                throw new ParseCancellationException(msg, e);
            }
        });
    }
}

// Lexer rules
T__0: '(';
T__1: ')';
T__2: '+';
T__3: '*';
NUMBER: [0-9]+;
SPACES: [ \t\r\n]+ -> skip;  // Skip spaces

// Parser rules
root: sum EOF;
sum: product (T__2 product)*;
product: primitive (T__3 primitive)*;
primitive: NUMBER | T__0 sum T__1;

// Here we also add extra custom methods, if needed
