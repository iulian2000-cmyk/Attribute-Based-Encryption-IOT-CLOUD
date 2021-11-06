package Users;

import it.unisa.dia.gas.jpbc.Element;
import java.util.List;
import java.util.Map;
import java.util.Vector;


abstract public class User {
    protected static int ID_user;
    protected static Element D;
    protected static List<String> list_attributes ;
    /**
     * Getter for ID user
     * @return int
     */
    public static int getID_user() {
        return ID_user;
    }

    /**
     * Setter for ID user
     * @param ID_user user id
     */
    public void setID_user(int ID_user) {
        this.ID_user = ID_user;
    }


    public static List<String> getList_attributes() {
        return list_attributes;
    }

    public void setList_attributes(List<String> list_attributes) {
        this.list_attributes = list_attributes;
    }


    public static Element getD() {
        return D;
    }

    public static void setD(Element d) {
        D = d;
    }
}
