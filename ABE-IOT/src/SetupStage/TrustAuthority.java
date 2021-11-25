package SetupStage;


import ABDS_System.FileStored;
import SpecialDataStructures.AccessTree;
import SpecialDataStructures.Ciphertext_CT;
import SpecialDataStructures.NodeAccessTree;
import Users.Individual;
import Users.JuridicPerson;
import Users.User;
import it.unisa.dia.gas.jpbc.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import SpecialDataStructures.Polynomial;


public class TrustAuthority {
    private   Field G0;
    private   Field Zr;
    private   Element generator;
    private  Element h;
    private  Element pairing_result;
    private  Element alpha;
    private  Element beta;
    private  Pairing pairingFunction;
    private  double s;
    private  double s1;
    private  double s2;


    private  Ciphertext_CT cipherText;



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

    /**
     * Constructor
     * @param G - the G field
     * @param g - generator of g
     * @param Zr1 - the field Zp
     * @param pairing - the pairing function
     */
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
     * The procedure which makes a user download a file .
     * @param  individual - the individual which execute the download procedure
     * @param file - the file which will be downloaded .
     * @return void
     */
    public void userDownloadFile(Individual individual, FileStored file,String location) throws Exception
    {
        if ( individual == file.getOwnerFile() )
        {
            file.downloadFile(location);
        }else{
            AccessTree accessTree = new AccessTree();
            String infix = file.getOwnerFile().getInfixExpressionOfAccessPolicy();
            NodeAccessTree root = accessTree.constructTree(infix);
            accessTree = new AccessTree(root, (JuridicPerson) file.getOwnerFile());
            accessTree.setInfixExpression(infix);
            if(accessTree.checkTree(individual)){
                System.out.println("The individual can download the file !");
                file.downloadFile(location);
            }else
            {
                System.out.println("The individual can't access the file !");
            }
        }

    }
    /**
     * Function which generates the SK
     * @param user the user for which the SK is generated
     */
    public  void generateSK(Individual user)
    {
        Element r = Zr.newRandomElement();
        Element D = generator.pow(alpha.add(r).div(beta).toBigInteger());
        user.setD(D);

        Element compareElement = r;
        compareElement.set(0);
        while(r.isEqual(compareElement)){
            r = Zr.newRandomElement();
        }
        user.setR(r);
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
    public  Element CalculateDj(Element rj,Element r,Element generator,String attribute)
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
    public  Element CalculateLj(Element rj,Element generator) {
        return generator.pow(rj.toBigInteger());
    }

    /**
     * Function which calculates the treshold values and those polynoms.
     * @param node the node for which the polynom is determined
     */

    public  void generateTrashHoldsAndPolynomialsAnd_C_first_values(NodeAccessTree node, int indexNode) {
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
                        s = node.getPolynomial().evaluate(0);
                         s1 = node.getPolynomial().evaluate(1);
                        s2 = node.getPolynomial().evaluate(2);
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
                    String attribute = switch (node.value) {
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
                        default -> "NO";
                    };
                    node.setC_(Math.pow(attribute.hashCode(),node.getPolynomial().evaluate(0)));
                    System.out.println("C'_" + attribute + "  :=" + node.getC_());
                }
            }
            indexNode++;
            if(node.left !=null){
                node.left.setParent(node);
                generateTrashHoldsAndPolynomialsAnd_C_first_values(node.left,indexNode);
            }
            indexNode++;
            if(node.right!=null){
                node.right.setParent(node);
                generateTrashHoldsAndPolynomialsAnd_C_first_values(node.right,indexNode);
            }
        }
    }

    /**
     * Function which calculates the number of children
     * @param node the node for which the number of children is determined
     * @return int
     */
    public  int getNumberChildren(NodeAccessTree node) {
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
                String attribute = switch (root.value) {
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
                //System.out.println("C_" +attribute + ":" + generator.pow(BigInteger.valueOf(root.getPolynomial().evaluate(0))));
                root.setC(generator.pow(BigInteger.valueOf(root.getPolynomial().evaluate(0))));
                System.out.println("C_" +attribute + "  :=" + root.getC());
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
        resultTree.setIndex(0);
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

    public Ciphertext_CT getCipherText(){
        return cipherText;
    }

    /**
     * Setter
     * @param TreeESP - the access tree of the ESP service
     * @param message - message
     * @param DO_user - data owner
     */
    public void setCiphertext(NodeAccessTree TreeESP, NodeAccessTree TreeDO, String message, User DO_user)
    {
        Vector<NodeAccessTree> leafNodesTreeESP  = new Vector<>();
        getLeafNodes(TreeESP,leafNodesTreeESP);
        Vector<NodeAccessTree> leafNodesDO = new Vector<>();
        getLeafNodes(TreeDO,leafNodesDO);

        Vector<NodeAccessTree> leafNodes = new Vector<>();
        leafNodes.addAll(leafNodesDO);
        leafNodes.addAll(leafNodesTreeESP);

        cipherText = new Ciphertext_CT(getProductTree(TreeESP,TreeDO),message,h.pow(BigInteger.valueOf((long)s)),
                pairingFunction.pairing(generator,generator).pow(alpha.toBigInteger()).pow(BigInteger.valueOf((long)s)),leafNodes,(JuridicPerson) DO_user,
                TreeESP,TreeDO);
    }

    /**
     * This function generates the blinded key
     * @param user the user for each the blinded key is generated.
     */
    public void generateBlindedSK(Individual user)
    {
        Element t = Zr.newRandomElement();
        user.setT(t);
        Element result_adding = alpha.add(user.getR());
        result_adding = result_adding.div(beta);
        result_adding = result_adding.mul(t);
        user.setBlindedD(generator.pow(result_adding.toBigInteger()));
    }


    /**
     * This function calculates the F_y values for each leaf node .
     * @param leafNodes the set of leaf nodes
     * @param r the r-selected for our user in the setup stage .
     */
    public void calculateFy_values(Vector<NodeAccessTree> leafNodes,Element r)
    {
        for(NodeAccessTree node : leafNodes)
        {
            //System.out.println(pairingFunction.pairing(generator.pow(r.toBigInteger()),generator.pow(BigInteger.valueOf(node.getPolynomial().evaluate(0)))))    ;
            node.setF_y(pairingFunction.pairing(generator,generator).pow(r.duplicate().mul(BigInteger.valueOf(node.getPolynomial().evaluate(0))).toBigInteger()));
            System.out.println("Fy_" + node.value + " :=" + pairingFunction.pairing(generator,generator).pow(r.mul(BigInteger.valueOf(node.getPolynomial().evaluate(0))).toBigInteger()));
        }
    }

    /**
     * This function gets the set of child nodes for a node X .
     * @param nodeAccessTree - the node for which the set of  child nodes are determined .
     * @param nodes the set of nodes
     */
    public void generateChildNodesSet(NodeAccessTree nodeAccessTree,Vector<NodeAccessTree> nodes)
    {
        if(nodeAccessTree!=null)
        {
            if(nodeAccessTree.right!=null && nodeAccessTree.left!=null)
            {
                nodes.add(nodeAccessTree.right);
                nodes.add(nodeAccessTree.left);
                generateChildNodesSet(nodeAccessTree.right,nodes);
                generateChildNodesSet(nodeAccessTree.left,nodes);
            }
        }
    }

    /**
     * This function calculates Fx for a node.
     * @param nodeAccessTree the node itself
     * @param r the r-randomly chosen
     */

    public void calculateFx_values(NodeAccessTree nodeAccessTree,Element r)
    {
        if(nodeAccessTree!=null)
        {
            Vector<NodeAccessTree> childNodes = new Vector<>();
            generateChildNodesSet(nodeAccessTree,childNodes);
            for (NodeAccessTree nodeChild : childNodes)
            {

                System.out.println("Fx_" + nodeChild.value + ":=" + pairingFunction.pairing(generator,generator).pow(r.duplicate().mul(BigInteger.valueOf(nodeChild.getPolynomial().evaluate(0))).toBigInteger()));
            }
            if(nodeAccessTree.right!=null && nodeAccessTree.left!=null)
            {
                calculateFx_values(nodeAccessTree.right,r.duplicate());
                calculateFx_values(nodeAccessTree.left,r.duplicate());
            }
        }
    }

    /**
     * This function decrypt the message .
     * @param CT the ciphertext
     * @param DR the data requester
     */
    public void decryptMessage(Ciphertext_CT CT,Individual DR ) throws Exception
    {
        if(CT.getTree().checkTree(DR)){
            //System.out.println("Access tree satisfied");
            generateBlindedSK(DR);
            Element r = DR.getR();

            System.out.println("\n 1.Calculate F_y values for each leaf node  \n");

            calculateFy_values(CT.getLeafNodes(),r);

            System.out.println("\n 2.Calculate F_x values for each leaf node  \n");
            calculateFx_values(CT.getTree().getRoot(),r);
            System.out.println("A :=" + pairingFunction.pairing(generator,generator).pow(r.duplicate().mul(CT.getTreeDO().getPolynomial().evaluate(0)).toBigInteger()));
            Element A = pairingFunction.pairing(generator,generator).pow(r.duplicate().mul(CT.getTreeDO().getPolynomial().evaluate(0)).toBigInteger());

            Element power_1 = DR.getT().mul(r.duplicate().mul(BigInteger.valueOf(CT.getTreeDO().getPolynomial().evaluate(0))));
            Element power_2 = DR.getT().mul(alpha.mul(BigInteger.valueOf(CT.getTreeDO().getPolynomial().evaluate(0))));
            System.out.println("B :=" + pairingFunction.pairing(generator,generator).pow(power_1.toBigInteger()).mul(pairingFunction.pairing(generator,generator).pow(power_2.toBigInteger())));

            Element B = pairingFunction.pairing(generator,generator).pow(power_1.toBigInteger()).mul(pairingFunction.pairing(generator,generator).pow(power_2.toBigInteger()));

            Element B_ = pairingFunction.pairing(generator,generator).pow(r.duplicate().mul(BigInteger.valueOf(CT.getTreeDO().getPolynomial().evaluate(0))).toBigInteger()).mul(pairingFunction.pairing(generator,generator).pow(alpha.mul(BigInteger.valueOf(CT.getTreeDO().getPolynomial().evaluate(0))).toBigInteger()));
            System.out.println("B\' :=" + B_);

            if(B_.div(A).isEqual(CT.getPairingResult()))
            {
                System.out.println("Message decrypted := " + CT.getMessage());
            }
        }else{
            System.out.println("Access tree not satisfied ");
        }
    }


}
