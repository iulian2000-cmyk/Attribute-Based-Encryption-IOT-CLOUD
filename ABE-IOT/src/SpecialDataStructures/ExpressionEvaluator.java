package SpecialDataStructures;

import java.util.Stack;

public class ExpressionEvaluator {
    public static int evaluate(String expression)
    {
        char[] words = expression.toCharArray();
        Stack<Integer> values = new Stack<Integer>();
        Stack<Character> operators = new Stack<Character>();

        for (int i = 0; i < words.length; i++)
        {
            if (words[i] == ' ')
                continue;
            if (words[i] >= '0' &&
                    words[i] <= '9')
            {
                StringBuffer sbuf = new StringBuffer();
                while (i < words.length && words[i] >= '0' && words[i] <= '9')
                    sbuf.append(words[i++]);
                values.push(Integer.parseInt(sbuf.toString()));
                i--;
            }

            else if (words[i] == '(')
                operators.push(words[i]);
            else if (words[i] == ')')
            {
                while (operators.peek() != '(')
                    values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
                operators.pop();
            }
            else if (words[i] == '+' || words[i] == '-' || words[i] == '*' || words[i] == '/')
            {
                while (!operators.empty() && hasPrecedence(words[i], operators.peek()))
                    values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
                operators.push(words[i]);
            }
        }
        while (!operators.empty())
            values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
        return values.pop();
    }
    public static boolean hasPrecedence( char operator_1, char operator_2)
    {
        if (operator_2 == '(' || operator_2 == ')')
            return false;
        if ((operator_1 == '*' || operator_1 == '/') && (operator_2 == '+' || operator_2 == '-'))
            return false;
        else
            return true;
    }
    public static int applyOperator(char operator, int b, int a)
    {
        switch (operator)
        {
            case '+':
                return a + b;
            case '*':
                return a * b;
        }
        return 0;
    }
}
