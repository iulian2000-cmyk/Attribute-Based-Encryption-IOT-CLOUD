package SpecialDataStructures;

import java.util.Vector;
import Users.JuridicPerson;
import it.unisa.dia.gas.jpbc.Element;

public class Ciphertext_CT {
    private  AccessTree tree;
    private  String message;
    private  Element C;
    private  Element pairingResult;
    private  Vector<NodeAccessTree> leafNodes;

    public  Vector<NodeAccessTree> getLeafNodes() {
        return leafNodes;
    }

    public  void setLeafNodes(Vector<NodeAccessTree> leafNodesTree) {
        leafNodes = leafNodesTree;
    }



    public  AccessTree getTree() {
        return tree;
    }

    public  void setTree(AccessTree accessTree) {
        tree = accessTree;
    }

    public  String getMessage() {
        return message;
    }

    public  void setMessage(String messageDecrypt) {
        message = messageDecrypt;
    }

    public  Element getC() {
        return C;
    }

    public  void setC(Element c) {
        C = c;
    }

    public  Element getPairingResult() {
        return pairingResult;
    }

    public  void setPairingResult(Element pairingFunction) {
         pairingResult = pairingFunction;
    }

    public Ciphertext_CT(NodeAccessTree tree, String msg, Element C, Element pairingResult, Vector<NodeAccessTree> leafNodes, JuridicPerson juridicPerson)
    {
        AccessTree accessTree = new AccessTree(tree,juridicPerson);
        setTree(accessTree);
        setMessage(msg);
        setMessage(msg);
        setC(C);
        setLeafNodes(leafNodes);
    }
}
