package ABDS_System;

import SetupStage.SystemSetup;
import SetupStage.TrustAuthority;
import SpecialDataStructures.ACCORD_SUPERIOR;
import Users.Individual;
import Users.JuridicPerson;
import Users.Position;

import java.util.Date;

public class TestABDS {
    public static void main(String args[]) throws Exception{
         JuridicPerson firstUser = new JuridicPerson(1, "Bitdefender", "Bucuresti", "SRL", "312313", "13231", new Date(2000, 1, 1),"((A+B+C+D+E+F+G+H+I+J)*K)");
         FileStored fileStored = new FileStored("/home/iulian/Public/FII/Licenta/book/book-license.pdf",firstUser,"storageSpace/book-license.pdf");
        Individual secondUser = new Individual(2,"Rusu Alexandru","Bucuresti","3123123132131231", new Date(2000, 2, 6), firstUser, Position.EMPLOYEE_PRODUCTION, ACCORD_SUPERIOR.YES);
         //fileStored.downloadFile("/home/iulian/Public/result.pdf");
        SystemSetup systemSetup = new SystemSetup();
        TrustAuthority TA = new TrustAuthority(systemSetup.getG1(), systemSetup.getGenerator_g(), systemSetup.getZr(), systemSetup.getPairing());


        TA.userDownloadFile(secondUser,fileStored,"/home/iulian/test.pdf");




    }
}
