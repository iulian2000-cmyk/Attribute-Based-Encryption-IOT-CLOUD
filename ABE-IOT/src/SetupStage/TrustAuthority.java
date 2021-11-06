package SetupStage;




import SpecialDataStructures.CipherText;
import SpecialDataStructures.NodeAccessTree;
import Users.User;
import it.unisa.dia.gas.jpbc.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import SpecialDataStructures.Polynomial;



public class TrustAuthority {
    private static  Field G0;
    private static  Field Zr;
    private static  Element generator;
    private static Element h;
    private static Element pairing_result;
    private static Element alpha;
    private static Element beta;
    private static Pairing pairingFunction;


    private static double s;
    private static double s1;
    private static double s2;


    private static CipherText cipherText;



    public Field getG0() {
        return G0;
    }

    public Element getGenerator() {
        return generator;
    }

    public Element getH() {
        return h;
    }

    public Element getPairing_result() {
        return pairing_result;
    }

    public Element getAlpha() {
        return alpha;
    }

    public Element getBeta() {
        return beta;
    }

    public TrustAuthority(Field G, Element g, Field Zr1, Pairing pairing) {
        G0 = G;
        generator = g;
        Zr = Zr1;
        beta = Zr.newRandomElement();
        alpha = Zr.newRandomElement();
        pairingFunction = pairing;
        h = generator.pow(beta.toBigInteger());
        pairing_result = pairing.pairing(generator, generator);
        pairing_result = pairing_result.pow(alpha.toBigInteger());



    }

    /**
     * Returns the Public KEY (PK)
     * @return List<Object>
     */
    public List<Object> get_PUBLIC_KEY() {
        List<Object> PK = new ArrayList<>();
        PK.add(G0);
        PK.add(generator);
        PK.add(h);
        PK.add(pairing_result);
        return PK;
    }

    /**
     * Returns the Public KEY (MK)
     * @return List<Object>
     */
    public List<Object> get_MASTER_KEY() {
        List<Object> MK = new ArrayList<>();
        MK.add(beta);
        MK.add(generator.pow(alpha.toBigInteger()));

        return MK;
    }

    /**
     * Function which generates the SK
     * @param user the user for which the SK is generated
     * @throws Exception exception for an opperation
     */
    public static void generateSK(User user)
    {
        Element r = Zr.newRandomElement();
        Element D = generator.pow(alpha.add(r).div(beta).toBigInteger());
        user.setD(D);

        for(String attribute : user.getList_attributes()) {
            Element rj = Zr.newRandomElement();
            System.out.println("L[" + user.getList_attributes().indexOf(attribute) + "]=" + CalculateLj(rj, generator));
            System.out.println("R[" + user.getList_attributes().indexOf(attribute) + "]=" + CalculateDj(rj, r, generator, attribute));
        }
    }
    /**
     * This function generate the D_j , where j is an attribute  for a user
     * @param r - the random r from Zp chosen,
     * @param rj - the random rj from Zp chosen
     * @param attribute - the attribute,
     * @param generator - the generator of the field.
     */
    public static Element CalculateDj(Element rj,Element r,Element generator,String attribute)
    {
        Element power = rj;
        power.set(attribute.hashCode());
        power = power.pow(rj.toBigInteger());

        return generator.pow(r.toBigInteger()).mul(power.toBigInteger());
    }
    /**
     * This function generate the D_j , where j is an attribute  for a user
     * @param rj - the random rj from Zp chosen
     * @param generator - the generator of the field.
     */
    public static Element CalculateLj(Element rj,Element generator) {
        return generator.pow(rj.toBigInteger());
    }

    /**
     * Function which calculates the treshold values and those polynoms.
     * @param node the node for which the polynom is determined
     */

    public static void generateTreshHoldsAndPolynomsAnd_C_first_values(NodeAccessTree node,int indexNode) {
        if (node != null) {
            Random rand = new Random();
            Vector<Integer> coefficients = new Vector<>();
            Vector<Integer> powers = new Vector<>();

            if(node.value == '*'){
                    node.k_treshold = getNumberChildren(node) - 1;
                    int degree = Math.abs(node.k_treshold - 1);
                    //System.out.println("Degree :" + degree);
                    int numberCoefficients = rand.nextInt(0,10);
                    for(int index=0;index<numberCoefficients;index++)
                    {
                        coefficients.add(rand.nextInt(-20,20));
                    }
                    powers.add(degree);
                    for(int index=1;index<numberCoefficients-1;index++)
                    {
                        if(degree > 0) {
                            powers.add(rand.nextInt(0, degree - 1));
                        }else{
                            powers.add(0);
                        }
                    }
                    powers.add(0);
                    node.setPolynomial(new Polynomial(coefficients,powers,degree));
                    if(indexNode == 0) {
                        //System.out.println(coefficients + "---" + powers);
                        s = node.getPolynomial().evaluate(0);
                        s1 = node.getPolynomial().evaluate(1);
                        s2 = node.getPolynomial().evaluate(2);
                        //System.out.println("S:" + s + "\t S1 : " + s1 + "\t S2: " + s2 );
                    }
            }else{
                node.k_treshold = 0;
                int degree = Math.abs(node.k_treshold-1);
                //System.out.println("Degree : " + degree);
                int numberCoefficients = rand.nextInt(0,10);
                for(int index=0;index<numberCoefficients;index++)
                {
                    if(index != numberCoefficients-1){
                        coefficients.add(rand.nextInt(-20,20));
                    }else{
                        coefficients.add(node.getParent().getPolynomial().evaluate(indexNode));
                    }
                }
                for(int index=0;index<numberCoefficients;index++)
                {
                    if(index == numberCoefficients-1)
                    {
                        powers.add(0);
                    }
                    else {
                        powers.add(degree);
                    }
                }
                node.setPolynomial(new Polynomial(coefficients,powers,degree));
                //System.out.println(coefficients + "---" + powers + "---" + degree);
                if(node.value!='+') {
                    //System.out.println(node.value);
                    String atribute = switch (node.value) {
                        case 'A' -> "EMPLOYEE_PRODUCTION";
                        case 'B' -> "LEADER_PRODUCTION";
                        case 'C' -> "DIRECTOR_PRODUCTION";
                        case 'D' -> "DIRECTOR";
                        case 'E' -> "DIRECTOR_ECONOMIC";
                        case 'F' -> "ACCOUNTANT";
                        case 'G' -> "ADMINISTRATOR";
                        case 'H' -> "CHIEF_ACCOUNTANT";
                        case 'I' -> "SECURITY_ADMIN";
                        case 'J' -> "CEO";
                        case 'K' -> "ACCORD_SUPERIOR-YES";
                        default -> null;
                    };
                    double b = rand.nextDouble(1,9);
                    int a = rand.nextInt(1,9);
                    if(Math.pow((atribute.hashCode() % a + b),node.getPolynomial().evaluate(0)) == Double.POSITIVE_INFINITY ||
                            Math.pow((atribute.hashCode() % a + b),node.getPolynomial().evaluate(0)) == Double.NEGATIVE_INFINITY
                       )
                    {
                        while(Math.pow((atribute.hashCode() % a + b),node.getPolynomial().evaluate(0)) == Double.POSITIVE_INFINITY
                             || Math.pow((atribute.hashCode() % a + b),node.getPolynomial().evaluate(0)) == Double.NEGATIVE_INFINITY)
                        {
                            a = rand.nextInt(1,9);
                            b = rand.nextDouble(1,9);
                        }
                    }
                    System.out.println("C'y :" + Math.pow((atribute.hashCode() % a + b),node.getPolynomial().evaluate(0)));
                }
            }
            indexNode++;
            if(node.left !=null){
                node.left.setParent(node);
                generateTreshHoldsAndPolynomsAnd_C_first_values(node.left,indexNode);
            }
            indexNode++;
            if(node.right!=null){
                node.right.setParent(node);
                generateTreshHoldsAndPolynomsAnd_C_first_values(node.right,indexNode);
            }
        }
    }

    /**
     * Function which calculates the number of children
     * @param node the node for which the number of children is determined
     * @return int
     */
    public static int getNumberChildren(NodeAccessTree node) {
        if (node.left == null && node.right == null) {
            return 0;
        } else {
            if (node.left == null){
               return 1 +  getNumberChildren(node.right);
            }else{
                if(node.right == null)
                {
                    return 1+getNumberChildren(node.left);
                }else{
                    return 2 + getNumberChildren(node.left) + getNumberChildren(node.right);
                }
            }
        }
    }

    /**
     * Function which generates the C values
     * @param root the node for which the C values are generated
     */
    public void generateC_values(NodeAccessTree root)
    {
        if(root!=null)
        {
            if(root.value !='*' && root.value!='+')
            {
                System.out.println("C_y : " + generator.pow(BigInteger.valueOf(root.getPolynomial().evaluate(0))));
            }
            generateC_values(root.left);
            generateC_values(root.right);
        }
    }

    /**
     * Function which makes the AND-operator between trees .
     * @param A - first tree
     * @param B - second tree
     * @return NodeAccessTree
     */
    public NodeAccessTree getProductTree(NodeAccessTree A,NodeAccessTree B)
    {
        NodeAccessTree resultTree = new NodeAccessTree('*');
        resultTree.setLeft(A);
        resultTree.setRight(B);
        return resultTree;
    }

    /**
     * Gets the set of the leaf nodes for a tree
     * @param root the root of the tree
     */
    public void getLeafNodes(NodeAccessTree root,Vector<NodeAccessTree> leafNodes)
    {
        if(root!=null)
        {
            if(root.value!='*' && root.value!='+')
            {
                leafNodes.add(root);
            }
            getLeafNodes(root.left,leafNodes);
            getLeafNodes(root.right,leafNodes);
        }
    }

    public CipherText getCipherText(){
        return cipherText;
    }

    public void setCiphertexT(NodeAccessTree TreeESP, NodeAccessTree DO,String message)
    {
        Vector<NodeAccessTree> leafNodesTreeESP  = new Vector<>();
        getLeafNodes(TreeESP,leafNodesTreeESP);
        Vector<NodeAccessTree> leafNodesDO = new Vector<>();
        getLeafNodes(DO,leafNodesDO);

        Vector<NodeAccessTree> leafNodes = new Vector<>();
        leafNodes.addAll(leafNodesDO);
        leafNodes.addAll(leafNodesTreeESP);

        cipherText = new CipherText(getProductTree(TreeESP,DO),message,h.pow(BigInteger.valueOf((long)s)),
                pairingFunction.pairing(generator,generator).pow(alpha.toBigInteger()).pow(BigInteger.valueOf((long)s)),leafNodes);
    }


}
