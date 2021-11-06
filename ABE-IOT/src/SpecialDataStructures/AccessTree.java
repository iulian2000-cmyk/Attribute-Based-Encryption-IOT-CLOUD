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
            String postfix = "AB+C+D+E+F+G+H+I+V*";
            char[] charArray = postfix.toCharArray();
            NodeAccessTree root = accessTree.constructTree(charArray);
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
     * @param postfix the postfix expresion of the logical expression of the access structure
     * @return NodeAccessTree
     */
    public NodeAccessTree constructTree(char[] postfix)
    {
        Stack<NodeAccessTree> st = new Stack<>();
        NodeAccessTree accessTree, subtree1, subtree2;
        for (char c : postfix) {
            if (!isOperator(c)) {
                accessTree = new NodeAccessTree(c);
                st.push(accessTree);
            } else {
                accessTree = new NodeAccessTree(c);
                subtree1 = st.pop();
                subtree2 = st.pop();
                accessTree.right = subtree1;
                accessTree.left = subtree2;
                st.push(accessTree);
            }
        }
        accessTree = st.peek();
        st.pop();
        return accessTree;
    }





}
