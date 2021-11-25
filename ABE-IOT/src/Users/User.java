package Users;

import ABDS_System.FileStored;
import it.unisa.dia.gas.jpbc.Element;
import java.util.List;
import java.util.Vector;


public class User {
    protected int ID_user;
    protected  Element D;
    protected  Element t;
    protected  Element blindedD;
    protected  Element r;
    protected  List<String> list_attributes ;
    protected String infixExpressionOfAccessPolicy;


    public  List<String> getList_attributes() {
        return list_attributes;
    }

    public void setList_attributes(List<String> list_attributes) {
        this.list_attributes = list_attributes;
    }


    public  Element getD() {
        return D;
    }

    public  void setD(Element d) {
        D = d;
    }

    public  Element getBlindedD() {
        return blindedD;
    }

    public  void setBlindedD(Element blinded_D) {
         blindedD = blinded_D;
    }

    public  Element getT() {
        return t;
    }

    public  void setT(Element T) {
        t = T;
    }

    public String getInfixExpressionOfAccessPolicy() {
        return infixExpressionOfAccessPolicy;
    }

    public void setInfixExpressionOfAccessPolicy(String infixExpressionOfAccessPolicy) {
        this.infixExpressionOfAccessPolicy = infixExpressionOfAccessPolicy;
    }
}
