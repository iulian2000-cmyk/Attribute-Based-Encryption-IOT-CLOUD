package SpecialDataStructures;

import java.util.Vector;
import it.unisa.dia.gas.jpbc.Element;

public class CipherText {
    private static NodeAccessTree tree;
    private static String message;
    private static Element C;
    private static Element pairingResult;

    public static Vector<NodeAccessTree> getLeafNodes() {
        return leafNodes;
    }

    public static void setLeafNodes(Vector<NodeAccessTree> leafNodes) {
        CipherText.leafNodes = leafNodes;
    }

    private static Vector<NodeAccessTree> leafNodes;

    public static NodeAccessTree getTree() {
        return tree;
    }

    public static void setTree(NodeAccessTree tree) {
        CipherText.tree = tree;
    }

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        CipherText.message = message;
    }

    public static Element getC() {
        return C;
    }

    public static void setC(Element c) {
        C = c;
    }

    public static Element getPairingResult() {
        return pairingResult;
    }

    public static void setPairingResult(Element pairingResult) {
        CipherText.pairingResult = pairingResult;
    }

    public CipherText(NodeAccessTree tree,String msg,Element C,Element pairingResult,Vector<NodeAccessTree> leafNodes)
    {
        setTree(tree);
        setMessage(msg);
        setMessage(msg);
        setC(C);
        setLeafNodes(leafNodes);
    }
}
