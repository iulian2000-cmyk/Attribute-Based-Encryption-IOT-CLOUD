package SpecialDataStructures;





public class NodeAccessTree {

    public char value;
    public int k_treshold;
    public NodeAccessTree left,right;
    public static Polynomial polynomial;
    NodeAccessTree parent;

    public NodeAccessTree(char value){
        this.value = value;
    }

    public NodeAccessTree getParent() {
        return parent;
    }

    public void setParent(NodeAccessTree parent) {
        this.parent = parent;
    }

    public  Polynomial getPolynomial() {
        return polynomial;
    }

    public void setPolynomial(Polynomial polynomialToSet) {
        polynomial = polynomialToSet;
    }

    public  NodeAccessTree getLeft() {
        return left;
    }

    public void setLeft(NodeAccessTree left) {
        this.left = left;
    }

    public  NodeAccessTree getRight() {
        return right;
    }

    public void setRight(NodeAccessTree right) {
        this.right = right;
    }



}

