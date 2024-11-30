package expressivo;

import static org.junit.Assert.*;
import org.junit.Test;

public class ExpressionTest {


    // Tests for toString
    @Test
    public void testToStringVariables() {
        Expression var = new Variable("x");
        assertEquals("x", var.toString());
    }

    @Test
    public void testToStringNumbers() {
        Expression num = new Number(42);
        assertEquals("42.0", num.toString());

        Expression numDecimal = new Number(3.14);
        assertEquals("3.14", numDecimal.toString());
    }

    @Test
    public void testToStringNestedExpressions() {
        Expression nested = new Multiplication(
                new Variable("x"),
                new Addition(new Number(2), new Variable("y"))
        );
        assertEquals("(x * (2.0 + y))", nested.toString());
    }

    // Tests for equals
    @Test
    public void testEqualsSameStructure() {
        Expression expr1 = new Addition(new Variable("x"), new Number(5));
        Expression expr2 = new Addition(new Variable("x"), new Number(5));
        assertTrue(expr1.equals(expr2));
    }

    @Test
    public void testEqualsDifferentStructure() {
        Expression expr1 = new Addition(new Variable("x"), new Number(5));
        Expression expr2 = new Multiplication(new Variable("x"), new Number(5));
        assertFalse(expr1.equals(expr2));
    }

    @Test
    public void testEqualsDifferentOperands() {
        Expression expr1 = new Addition(new Variable("x"), new Number(5));
        Expression expr2 = new Addition(new Variable("y"), new Number(5));
        assertFalse(expr1.equals(expr2));
    }

    // Tests for hashCode
    @Test
    public void testHashCodeEquality() {
        Expression expr1 = new Addition(new Variable("x"), new Number(5));
        Expression expr2 = new Addition(new Variable("x"), new Number(5));
        assertEquals(expr1.hashCode(), expr2.hashCode());
    }

    @Test
    public void testHashCodeInequality() {
        Expression expr1 = new Addition(new Variable("x"), new Number(5));
        Expression expr2 = new Addition(new Variable("y"), new Number(5));
        assertNotEquals(expr1.hashCode(), expr2.hashCode());
    }

    // Tests for complex expressions
    @Test
    public void testComplexExpressionEquality() {
        Expression expr1 = new Multiplication(
                new Addition(new Variable("x"), new Number(3)),
                new Variable("y")
        );
        Expression expr2 = new Multiplication(
                new Addition(new Variable("x"), new Number(3)),
                new Variable("y")
        );
        assertTrue(expr1.equals(expr2));
        assertEquals(expr1.hashCode(), expr2.hashCode());
    }

    @Test
    public void testComplexExpressionInequality() {
        Expression expr1 = new Multiplication(
                new Addition(new Variable("x"), new Number(3)),
                new Variable("y")
        );
        Expression expr2 = new Multiplication(
                new Addition(new Variable("x"), new Number(4)),
                new Variable("y")
        );
        assertFalse(expr1.equals(expr2));
        assertNotEquals(expr1.hashCode(), expr2.hashCode());
    }

    @Test
    public void testComplexToString() {
        Expression expr = new Multiplication(
                new Addition(new Variable("x"), new Number(3)),
                new Addition(new Variable("y"), new Number(4))
        );
        assertEquals("((x + 3.0) * (y + 4.0))", expr.toString());
    }
}
