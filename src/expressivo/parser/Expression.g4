grammar Expression;
import Configuration;

/*
 * Grammar for parsing mathematical expressions with addition, multiplication,
 * parentheses, variables, and numbers.
 */

/* Start rule for parsing the entire input */
root : sum EOF;

/* Rule for summation expressions (addition) */
sum : product ('+' product)*;

/* Rule for product expressions (multiplication) */
product : primitive ('*' primitive)*;

/* Rule for primitives: numbers, variables, or parenthesized expressions */
primitive : NUMBER | VARIABLE | '(' sum ')';

/* Terminal for numbers: integers or floating-point numbers */
NUMBER : [0-9]+ ('.' [0-9]+)?;

/* Terminal for variables: nonempty strings of letters */
VARIABLE : [a-zA-Z]+;

/* Ignore spaces around tokens */
SPACES : [ \t\r\n]+ -> skip;
