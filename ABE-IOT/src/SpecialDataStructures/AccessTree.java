package SpecialDataStructures;

import Users.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class AccessTree {
    private static NodeAccessTree root;
    private static JuridicPerson juridicPerson;

    public  AccessTree(){

    }
    public static NodeAccessTree getRoot() {
        return root;
    }
    public static void setRoot(NodeAccessTree root) {
        AccessTree.root = root;
    }


    public static void setJuridicPerson(JuridicPerson juridicPerson) {
        AccessTree.juridicPerson = juridicPerson;
    }

    public AccessTree(NodeAccessTree root, JuridicPerson juridicPerson)
    {
        setRoot(root);
        setJuridicPerson(juridicPerson);
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
            String infix = "((A+B+C+D+E+F+G+H+I+J)*K)";
            NodeAccessTree root = accessTree.constructTree(infix);
            accessTree = new AccessTree(root,user);
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

    public static boolean checkTree(Individual individual)
    {
        Position position = individual.getPosition();
        return (individual.getPlaceJOB() == juridicPerson) && (Individual.getAccord_superior() == ACCORD_SUPERIOR.YES) &&
                (position != Position.INDIVIDUAL);
    }

    /**
     * Function which check if the value of a node is an operator
     * @param c the value of a node , which can be an operand/operator
     * @return boolean
     */

    boolean isOperator(char c) {
        return c == '+' || c == '-'
                || c == '*' || c == '/'
                || c == '^';
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
}
