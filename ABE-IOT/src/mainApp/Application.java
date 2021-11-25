package mainApp;



import SetupStage.SystemSetup;
import SetupStage.TrustAuthority;
import SpecialDataStructures.ACCORD_SUPERIOR;
import SpecialDataStructures.AccessTree;
import SpecialDataStructures.NodeAccessTree;
import Users.Individual;
import Users.JuridicPerson;
import Users.Position;
import Users.User;
import java.io.*;
import java.util.*;




public class Application {
    public static void main(String[] args) throws Exception {
        System.out.print(
                "        d88888888b    d8888.      88888888db         ***      ******     **********        \n" +
                        "        88'     88    YP  8P8     8Y                           **   **       ***          \n" +
                        "        88AAAAAA8bo   88888888    8YYYYYYYY          ***      **    **       ***            \n" +
                        "        88      Y8b   88   88     8b                 ***       **   **       ***            \n" +
                        "        88      db    8D 8b       88yyyyyyyy         ****       ***          ***               \n\n");


        System.out.print("                                                                             I. SYSTEM SETUP AND KEY GENERATION                                                                                                        \n");


        SystemSetup systemSetup = new SystemSetup();
        TrustAuthority TA = new TrustAuthority(systemSetup.getG1(), systemSetup.getGenerator_g(), systemSetup.getZr(), systemSetup.getPairing());
        System.out.println("\ng=" + TA.getGenerator() + "\n");
        System.out.println("alpha=" + TA.getAlpha() + "\n");
        System.out.println("beta=" + TA.getBeta() + "\n");
        System.out.println("p=" + TA.getG0().getOrder() + "\n");
        System.out.println("e(g,g)^alpha=" + TA.getPairing_result() + "\n");
        System.out.println("h=" + TA.getH() + "\n");


        System.out.println("SK FOR A USER : \n ");
        JuridicPerson firstUser = new JuridicPerson(1, "Bitdefender", "Bucuresti", "SRL", "312313", "13231", new Date(2000, 1, 1),"((A+B+C+D+E+F+G+H+I+J)*K)");
        Individual secondUser = new Individual(2,"Rusu Alexandru","Bucuresti","3123123132131231", new Date(2000, 2, 6), firstUser,Position.EMPLOYEE_PRODUCTION,ACCORD_SUPERIOR.YES);
        JuridicPerson thirdUser = new JuridicPerson(3, "Endava", "Iasi", "SA", "1323131", "123131", new Date(1990, 3, 3),"((A+B+C+D+E+F+G+H+I+J)*K)");
        User  forthUser = new Individual(4,"Rusu Alexandru","Bucuresti","3123123132131231", new Date(2000, 2, 6), thirdUser,Position.EMPLOYEE_PRODUCTION,ACCORD_SUPERIOR.YES);

        TA.generateSK(secondUser);
        System.out.println("\n                                                                          II . ENCRYPTION USING PP-CP-ABE SCHEME                                                                                                    ");

        System.out.println("\n  1. RUN ENCRYPTION FOR ESP ACCESS TREE ");
        List<JuridicPerson> users = new ArrayList<>();
        users.add(firstUser);
        users.add(thirdUser);

        AccessTree accessTree = new AccessTree();
        List<AccessTree> accessTrees =  accessTree.buildDOsTree(users);
        NodeAccessTree TreeESP = accessTree.getTree(accessTrees);
        //TA.inOrder(TreeESP);


        TA.generateTrashHoldsAndPolynomialsAnd_C_first_values(TreeESP,0);
        TA.generateC_values(TreeESP);

        System.out.println("\n 2. RUN ENCRYPTION FOR DO's ACCESS TREE ");
        accessTrees.clear();
        users.clear();

        users.add(firstUser);
        accessTrees = accessTree.buildDOsTree(users);
        NodeAccessTree TreeDO = accessTree.getTree(accessTrees);
        TA.generateTrashHoldsAndPolynomialsAnd_C_first_values(TreeDO,0);
        TA.generateC_values(TreeDO);

        //NodeAccessTree mainAccessTree = TA.getProductTree(TreeDO,TreeESP);
        Vector<NodeAccessTree> Y_DO = new Vector<>();
        TA.getLeafNodes(TreeDO,Y_DO);
        TA.getLeafNodes(TreeESP,Y_DO);

        TA.setCiphertext(TreeESP,TreeDO,"Bine ati venit !", firstUser);

        System.out.println("\n                                                                          III . DECRYPTION USING PP-CP-ABE SCHEME                                                                                                    ");

        TA.decryptMessage(TA.getCipherText(),(Individual) secondUser);
        TA.decryptMessage(TA.getCipherText(),(Individual) forthUser);


    }
}
