package expressivo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Console interface to the expression system.
 */
public class Main {

    /**
     * Read expression and command inputs from the console and output results.
     * An empty input terminates the program.
     * 
     * @param args unused
     * @throws IOException if there is an error reading the input
     */
    public static void main(String[] args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        Optional<Expression> currentExpression = Optional.empty(); // Tracks the current expression

        while (true) {
            System.out.print("> ");
            final String input = in.readLine();

            if (input.isEmpty()) {
                return; // exits the program
            }

            try {
                final String output;

                if (input.startsWith(DIFFERENTIATE_PREFIX)) {
                    // Handle differentiation command
                    final String variable = parseDifferentiate(input);
                    Expression differentiated = currentExpression
                            .orElseThrow(() -> new NoSuchElementException("No expression to differentiate"))
                            .differentiate(variable);
                    output = differentiated.toString();
                    currentExpression = Optional.of(differentiated);

                } else if (input.startsWith(SIMPLIFY_PREFIX)) {
                    // Handle simplification command
                    final Map<String, Double> environment = parseSimplify(input);
                    Expression simplified = currentExpression
                            .orElseThrow(() -> new NoSuchElementException("No expression to simplify"))
                            .simplify(environment);
                    output = simplified.toString();

                } else {
                    // Parse and store the new expression
                    Expression expression = Expression.parse(input);
                    output = expression.toString();
                    currentExpression = Optional.of(expression);
                }

                System.out.println(output);

            } catch (NoSuchElementException nse) {
                System.out.println("Error: must enter an expression before using this command.");
            } catch (RuntimeException re) {
                System.out.println("Error: " + re.getMessage());
            }
        }
    }

    private static final String DIFFERENTIATE_PREFIX = "!d/d";
    private static final String VARIABLE = "[A-Za-z]+";
    private static final String DIFFERENTIATE = DIFFERENTIATE_PREFIX + "(" + VARIABLE + ") *";

    /**
     * Parses the differentiation command to extract the variable.
     * 
     * @param input the input command
     * @return the variable to differentiate with respect to
     */
    private static String parseDifferentiate(final String input) {
        final Matcher commandMatcher = Pattern.compile(DIFFERENTIATE).matcher(input);
        if (!commandMatcher.matches()) {
            throw new CommandSyntaxException("usage: !d/d must be followed by a variable name.");
        }

        return commandMatcher.group(1);
    }

    private static final String SIMPLIFY_PREFIX = "!simplify";
    private static final String ASSIGNMENT = "(" + VARIABLE + ") *= *([^ ]+)";
    private static final String SIMPLIFY = SIMPLIFY_PREFIX + "( +" + ASSIGNMENT + ")* *";

    /**
     * Parses the simplify command to extract variable assignments.
     * 
     * @param input the input command
     * @return a map of variable-to-value assignments
     */
    private static Map<String, Double> parseSimplify(final String input) {
        final Matcher commandMatcher = Pattern.compile(SIMPLIFY).matcher(input);
        if (!commandMatcher.matches()) {
            throw new CommandSyntaxException("usage: !simplify var1=val1 var2=val2 ...");
        }

        final Map<String, Double> environment = new HashMap<>();
        final Matcher argumentMatcher = Pattern.compile(ASSIGNMENT).matcher(input);
        while (argumentMatcher.find()) {
            final String variable = argumentMatcher.group(1);
            final double value = Double.parseDouble(argumentMatcher.group(2));
            environment.put(variable, value);
        }

        return environment;
    }

    /**
     * Exception for invalid command syntax.
     */
    public static class CommandSyntaxException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public CommandSyntaxException(String message) {
            super(message);
        }
    }
}
