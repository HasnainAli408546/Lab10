package expressivo;

import java.util.Map;

/**
 * String-based commands provided by the expression system.
 */
public class Commands {

    /**
     * Differentiate an expression with respect to a variable.
     * 
     * @param expression the expression to differentiate
     * @param variable the variable to differentiate by, a case-sensitive nonempty string of letters.
     * @return expression's derivative with respect to variable. Must be a valid expression equal
     *         to the derivative, but doesn't need to be in simplest or canonical form.
     * @throws IllegalArgumentException if the expression or variable is invalid
     */
    public static String differentiate(String expression, String variable) {
        try {
            // Parse the input expression
            Expression expr = Expression.parse(expression);

            // Differentiate the expression with respect to the given variable
            Expression differentiatedExpr = expr.differentiate(variable);

            // Return the result as a string
            return differentiatedExpr.toString();
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("Invalid expression or variable for differentiation: " + e.getMessage());
        }
    }

    /**
     * Simplify an expression.
     * 
     * @param expression the expression to simplify
     * @param environment maps variables to values. Variables are required to be case-sensitive nonempty 
     *         strings of letters. The set of variables in environment is allowed to be different than the 
     *         set of variables actually found in expression. Values must be nonnegative numbers.
     * @return an expression equal to the input, but after substituting every variable v that appears in both
     *         the expression and the environment with its value, environment.get(v). If there are no
     *         variables left in this expression after substitution, it must be evaluated to a single number.
     *         Additional simplifications to the expression may be done at the implementor's discretion.
     * @throws IllegalArgumentException if the expression is invalid
     */
    public static String simplify(String expression, Map<String, Double> environment) {
        try {
            // Parse the input expression
            Expression expr = Expression.parse(expression);

            // Simplify the expression with the given environment
            Expression simplifiedExpr = expr.simplify(environment);

            // Return the result as a string
            return simplifiedExpr.toString();
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("Invalid expression or environment for simplification: " + e.getMessage());
        }
    }
}
