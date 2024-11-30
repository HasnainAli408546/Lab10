package expressivo;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

/**
 * An immutable data type representing a polynomial expression of:
 *   + and *
 *   nonnegative integers and floating-point numbers
 *   variables (case-sensitive nonempty strings of letters)
 * 
 * <p>PS3 instructions: this is a required ADT interface.
 * You MUST NOT change its name or package or the names or type signatures of existing methods.
 * You may, however, add additional methods, or strengthen the specs of existing methods.
 * Declare concrete variants of Expression in their own Java source files.
 */
public interface Expression {

    // Datatype definition:
    // Expression ::= Number(value:double)
    //              | Variable(name:String)
    //              | Addition(left:Expression, right:Expression)
    //              | Multiplication(left:Expression, right:Expression)
    
    /**
     * Parse an expression.
     * @param input expression to parse, as defined in the PS3 handout.
     *              Must consist of numbers, variables (letters only), + and *, and valid grouping with parentheses.
     *              Whitespace is allowed and ignored.
     * @return expression AST for the input
     * @throws IllegalArgumentException if the expression is invalid
     */
    public static Expression parse(String input) {
        // Perform input validation before parsing.
        validateInput(input);

        try {
            ExpressionLexer lexer = new ExpressionLexer(CharStreams.fromString(input));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            ExpressionParser parser = new ExpressionParser(tokens);

            // Parse the input using the generated grammar
            ExpressionBaseListener listener = new ExpressionASTBuilder();
            ParseTreeWalker walker = new ParseTreeWalker();
            walker.walk(listener, parser.root());

            return ((ExpressionASTBuilder) listener).getExpression();

        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid expression: " + input, e);
        }
    }

    /**
     * Validate the input expression for invalid characters and unbalanced parentheses.
     * 
     * @param input the input string to validate
     * @throws IllegalArgumentException if the input is invalid
     */
    private static void validateInput(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Expression cannot be null or empty");
        }

        // Check for invalid characters
        if (!input.matches("[a-zA-Z0-9+*()\\s]+")) {
            throw new IllegalArgumentException("Expression contains invalid characters: " + input);
        }

        // Check for balanced parentheses
        int balance = 0;
        for (char c : input.toCharArray()) {
            if (c == '(') {
                balance++;
            } else if (c == ')') {
                balance--;
                if (balance < 0) {
                    throw new IllegalArgumentException("Unbalanced parentheses in expression: " + input);
                }
            }
        }
        if (balance != 0) {
            throw new IllegalArgumentException("Unbalanced parentheses in expression: " + input);
        }
    }

    /**
     * Produce a parsable representation of this expression.
     * 
     * The resulting string must satisfy:
     *   For all e: Expression, e.equals(Expression.parse(e.toString())).
     *
     * This method must produce a representation that maintains the same
     * mathematical meaning, regardless of grouping or whitespace differences.
     * 
     * @return a parsable string representation of this expression.
     */
    @Override 
    public String toString();

    /**
     * Check if this expression is structurally equal to another object.
     * Two expressions are structurally equal if and only if:
     *   - They contain the same numbers, variables, and operators (+ and *).
     *   - These elements are in the same order, respecting parenthetical grouping.
     * 
     * @param thatObject any object
     * @return true if and only if this and thatObject are structurally-equal
     * Expressions, as defined in the PS3 handout.
     */
    @Override
    public boolean equals(Object thatObject);
    
    /**
     * Produce a hash code consistent with the equals() definition of structural equality.
     * 
     * For all e1,e2:Expression, if e1.equals(e2), then e1.hashCode() == e2.hashCode().
     * 
     * @return hash code value for this expression
     */
    @Override
    public int hashCode();

    // Additional instance methods for extensions (if required) can go here.
}
