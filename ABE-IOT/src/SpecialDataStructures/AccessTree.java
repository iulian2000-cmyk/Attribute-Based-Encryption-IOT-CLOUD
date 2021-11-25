package SpecialDataStructures;

import Users.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class AccessTree {
    private static NodeAccessTree root;
    private static JuridicPerson data_owner;
    public  AccessTree(){

    }
    public static String infixExpression;
    public static NodeAccessTree getRoot() {
        return root;
    }
    public static void setRoot(NodeAccessTree root) {
        AccessTree.root = root;
    }
    public static void setData_owner(JuridicPerson DO) {
        data_owner = DO;
    }

    public AccessTree(NodeAccessTree root, JuridicPerson data_owner)
    {
        setRoot(root);
        setData_owner(data_owner);
    }


    /**
     * Function which builds the access trees for users.
     * @param users the list of users
     * @return List<AccessTree> a list with DO's access tree
     */
    public List<AccessTree> buildDOsTree(List<JuridicPerson> users)
    {
        List<AccessTree> trees = new ArrayList<>();
        for(JuridicPerson user : users)
        {
            AccessTree accessTree = new AccessTree();
            //String infix = "((A+B+C+D+E+F+G+H+I+J)*K)";
            String infix = user.getInfixExpressionOfAccessPolicy();
            setInfixExpression(infix);
            NodeAccessTree root = accessTree.constructTree(infix);
            accessTree = new AccessTree(root,user);
            accessTree.setInfixExpression(infixExpression);
            trees.add(accessTree);
        }
        return trees;
    }

    /**
     * Function which builds the ESP Tree
     * @param DO the list of data owners
     * @return NodeAccessTree
     */
    public NodeAccessTree getTree(List<AccessTree> DO)
    {
        NodeAccessTree resultTree = new NodeAccessTree('*');
        for (AccessTree accessTree : DO) {
            NodeAccessTree root = accessTree.getRoot();
            if (resultTree.getRight() == null) {
                resultTree.setRight(root);
            } else {
                if (resultTree.getLeft() == null) {
                    resultTree.setLeft(root);
                } else {
                    if (resultTree.getLeft() != null && resultTree.getRight() != null) {
                        NodeAccessTree newTree = new NodeAccessTree('*');
                        newTree.setLeft(resultTree);
                        newTree.setRight(root);
                        resultTree = newTree;
                    }
                }
            }
        }
        return resultTree;
    }

    /**
     * Function which checks if an individual satisfy the access tree
     * @param individual - the individual
     * @return boolean
     */
    public boolean checkTree(Individual individual) throws Exception
    {
        Position position = individual.getPosition();
        //return (individual.getPlaceJOB().getID_user() == data_owner.getID_user()) && (individual.getAccord_superior() == ACCORD_SUPERIOR.YES) && (position != Position.INDIVIDUAL);

        if( individual.getPlaceJOB().getID_user() == data_owner.getID_user()) {
            String copy_expression = infixExpression;
            StringBuilder expression = new StringBuilder(copy_expression);
            ExpressionEvaluator expressionEvaluator = new ExpressionEvaluator();
            //System.out.println(expression.toString());
            for (int index_character = 0; index_character < copy_expression.length(); index_character++) {
                if (copy_expression.charAt(index_character) != ')' && copy_expression.charAt(index_character) != '(' && copy_expression.charAt(index_character) != '+' && copy_expression.charAt(index_character) != '*') {
                    char ch_value = '0';
                    switch (copy_expression.charAt(index_character))
                    {
                        case 'A':
                            if(position == Position.EMPLOYEE_PRODUCTION)
                            {
                               ch_value = '1';
                            }else{
                                ch_value = '0';
                            }
                            break;
                        case 'B':
                            if(position == Position.LEADER_PRODUCTION)
                            {
                                ch_value = '1';
                            }else{
                                ch_value = '0';
                            }
                            break;
                        case 'C':
                            if(position == Position.DIRECTOR_PRODUCTION)
                            {
                                ch_value = '1';
                            }else{
                                ch_value = '0';
                            }
                            break;
                        case 'D':
                            if(position == Position.ACCOUNTANT)
                            {
                                ch_value = '1';
                            }else{
                                ch_value = '0';
                            }
                            break;
                        case 'E':
                            if(position == Position.CHIEF_ACCOUNTANT)
                            {
                                ch_value = '1';
                            }else{
                                ch_value = '0';
                            }
                            break;
                        case 'F':
                            if(position == Position.DIRECTOR_ECONOMIC)
                            {
                                ch_value = '1';
                            }else{
                                ch_value = '0';
                            }
                            break;
                        case 'G':
                            if(position == Position.DIRECTOR)
                            {
                                ch_value = '1';
                            }else{
                                ch_value = '0';
                            }
                            break;
                        case 'H':
                            if(position == Position.ADMINISTRATOR)
                            {
                                ch_value = '1';
                            }else{
                                ch_value = '0';
                            }
                            break;
                        case 'I':
                            if(position == Position.CEO)
                            {
                                ch_value = '1';
                            }else{
                                ch_value = '0';
                            }
                            break;
                        case 'J':
                            if(position == Position.SECURITY_ADMIN)
                            {
                                ch_value = '1';
                            }else{
                                ch_value = '0';
                            }
                            break;
                        case 'K':
                            if(individual.getAccord_superior() == ACCORD_SUPERIOR.YES)
                            {
                                ch_value = '1';
                            }else{
                                ch_value = '0';
                            }
                            break;
                    }
                    expression.setCharAt(index_character,ch_value);
                }
            }
            //System.out.println(expression.toString());
            if(expressionEvaluator.evaluate(expression.toString()) == 1)
            {
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }


    /**
     * Function which creates the access tree which is the same thing with the the expression tree
     * @param infix the infix expresion of the logical expression of the access structure
     * @return NodeAccessTree
     */
    public NodeAccessTree constructTree(String infix)
    {

        Stack<NodeAccessTree> stN = new Stack<>();
        Stack<Character> stC = new Stack<>();
        NodeAccessTree t, t1, t2;
        int []p = new int[123];
        p['+'] = p['-'] = 1;
        p['/'] = p['*'] = 2;
        p['^'] = 3;
        p[')'] = 0;

        for (int i = 0; i < infix.length(); i++)
        {
            if (infix.charAt(i) == '(') {

                // Push '(' in char stack
                stC.add(infix.charAt(i));
            }

            // Push the operands in node stack
            else if (Character.isAlphabetic(infix.charAt(i)))
            {
                t = new NodeAccessTree(infix.charAt(i));
                stN.add(t);
            }
            else if (p[infix.charAt(i)] > 0)
            {
                while (
                        !stC.isEmpty() && stC.peek() != '('
                                && ((infix.charAt(i) != '^' && p[stC.peek()] >= p[infix.charAt(i)])
                                || (infix.charAt(i) == '^'
                                && p[stC.peek()] > p[infix.charAt(i)])))
                {
                    t = new NodeAccessTree( stC.peek());
                    stC.pop();
                    t1 = stN.peek();
                    stN.pop();
                    t2 = stN.peek();
                    stN.pop();
                    t.left = t2;
                    t.right = t1;
                    stN.add(t);
                }
                stC.push(infix.charAt(i));
            }
            else if (infix.charAt(i) == ')') {
                while (!stC.isEmpty() && stC.peek() != '(')
                {
                    t = new NodeAccessTree(stC.peek());
                    stC.pop();
                    t1 = stN.peek();
                    stN.pop();
                    t2 = stN.peek();
                    stN.pop();
                    t.left = t2;
                    t.right = t1;
                    stN.add(t);
                }
                stC.pop();
            }
        }
        t = stN.peek();
        return t;
    }

    public static User getData_owner() {
        return data_owner;
    }

    public static String getInfixExpression() {
        return infixExpression;
    }

    public static void setInfixExpression(String infixExpression) {
        AccessTree.infixExpression = infixExpression;
    }
}
