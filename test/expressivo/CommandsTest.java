package expressivo;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Tests for the static methods of Commands.
 */
public class CommandsTest {

    // Testing strategy:
    // 1. Commands.differentiate():
    //    - Differentiate constant expressions (e.g., "5", "0").
    //    - Differentiate single-variable expressions (e.g., "x", "y").
    //    - Differentiate multi-term expressions with addition (e.g., "x + 2").
    //    - Differentiate multi-term expressions with multiplication (e.g., "x * 3").
    //    - Differentiate nested expressions (e.g., "(x + 1) * 2").
    //    - Edge cases: invalid variables or expressions.

    // 2. Commands.simplify():
    //    - Simplify constant expressions (e.g., "5", "0").
    //    - Simplify single-variable expressions with mapping (e.g., "x" -> x=3).
    //    - Simplify expressions with partial mappings (e.g., "x + y", x=2).
    //    - Simplify multi-term expressions (e.g., "x + 2 + y", x=1, y=2).
    //    - Simplify expressions to a constant (e.g., "x * 3", x=2).
    //    - Edge cases: no mappings, empty mappings, invalid expressions.

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    /** Tests for Commands.differentiate() */
    @Test
    public void testDifferentiateConstants() {
        assertEquals("0", Commands.differentiate("5", "x")); // Constant expression
        assertEquals("0", Commands.differentiate("0", "x")); // Zero is also a constant
    }

    @Test
    public void testDifferentiateSingleVariable() {
        assertEquals("1", Commands.differentiate("x", "x")); // Variable w.r.t. itself
        assertEquals("0", Commands.differentiate("y", "x")); // Different variable
    }

    @Test
    public void testDifferentiateAddition() {
        assertEquals("1 + 0", Commands.differentiate("x + 2", "x")); // Differentiate sum
    }

    @Test
    public void testDifferentiateMultiplication() {
        assertEquals("1 * 3 + x * 0", Commands.differentiate("x * 3", "x")); // Differentiate product
    }

    @Test
    public void testDifferentiateNestedExpressions() {
        assertEquals("(1 + 0) * 2 + (x + 1) * 0", Commands.differentiate("(x + 1) * 2", "x")); // Nested
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDifferentiateInvalidExpression() {
        Commands.differentiate("5 +", "x"); // Invalid expression
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDifferentiateInvalidVariable() {
        Commands.differentiate("x + 2", ""); // Invalid variable
    }

    /** Tests for Commands.simplify() */
    @Test
    public void testSimplifyConstantExpression() {
        assertEquals("5", Commands.simplify("5", new HashMap<>())); // Constant remains the same
    }

    @Test
    public void testSimplifySingleVariable() {
        Map<String, Double> environment = new HashMap<>();
        environment.put("x", 3.0);
        assertEquals("3.0", Commands.simplify("x", environment)); // Variable substitution
    }

    @Test
    public void testSimplifyPartialMapping() {
        Map<String, Double> environment = new HashMap<>();
        environment.put("x", 2.0);
        assertEquals("2.0 + y", Commands.simplify("x + y", environment)); // Partial substitution
    }

    @Test
    public void testSimplifyMultiTermExpression() {
        Map<String, Double> environment = new HashMap<>();
        environment.put("x", 1.0);
        environment.put("y", 2.0);
        assertEquals("3.0 + 2", Commands.simplify("x + 2 + y", environment)); // Full substitution
    }

    @Test
    public void testSimplifyToConstant() {
        Map<String, Double> environment = new HashMap<>();
        environment.put("x", 2.0);
        assertEquals("6.0", Commands.simplify("x * 3", environment)); // Evaluate to constant
    }

    @Test
    public void testSimplifyNoMappings() {
        assertEquals("x + 2", Commands.simplify("x + 2", new HashMap<>())); // No substitutions
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSimplifyInvalidExpression() {
        Commands.simplify("x +", new HashMap<>()); // Invalid expression
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSimplifyInvalidEnvironment() {
        Map<String, Double> environment = new HashMap<>();
        environment.put("", 3.0); // Invalid variable in environment
        Commands.simplify("x + 2", environment);
    }
}
