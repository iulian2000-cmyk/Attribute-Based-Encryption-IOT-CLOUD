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
    private NodeAccessTree TreeESP;
    private NodeAccessTree TreeDO;
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

    public Ciphertext_CT(NodeAccessTree tree, String msg, Element C, Element pairingResult, Vector<NodeAccessTree> leafNodes, JuridicPerson juridicPerson,NodeAccessTree TreeESP,NodeAccessTree TreeDO)
    {
        AccessTree accessTree = new AccessTree(tree,juridicPerson);
        setTree(accessTree);
        setMessage(msg);
        setMessage(msg);
        setC(C);
        setLeafNodes(leafNodes);
        setTreeDO(TreeDO);
        setTreeESP(TreeESP);
        setPairingResult(pairingResult);
    }

    public NodeAccessTree getTreeESP() {
        return TreeESP;
    }

    public void setTreeESP(NodeAccessTree treeESP) {
        TreeESP = treeESP;
    }

    public NodeAccessTree getTreeDO() {
        return TreeDO;
    }

    public void setTreeDO(NodeAccessTree treeDO) {
        TreeDO = treeDO;
    }
}
