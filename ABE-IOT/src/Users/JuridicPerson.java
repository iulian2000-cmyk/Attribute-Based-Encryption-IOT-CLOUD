package Users;

import it.unisa.dia.gas.jpbc.Element;

import java.util.ArrayList;
import java.util.Date;

public class JuridicPerson extends User {
    private String officialName;
    private String Address;
    private String JuridicForm;
    private String UniqueCODE;
    private String Activity_COD;
    private Date dateBirth;


    /**
     * Getter for ID user
     * @return int
     */
    public  int getID_user() {
        return this.ID_user;
    }

    /**
     * Setter for ID user
     * @param ID_user_to_add user id
     */
    public void setID_user(int ID_user_to_add) {
        this.ID_user = ID_user_to_add;
    }

    public Element getR() {
        return super.r;
    }

    public  void setR(Element r_element) {
        super.r = r_element;
    }

    public JuridicPerson(int ID, String name, String Address, String juridicForm, String uniqueCODE, String activity_COD, Date dateBirth)
    {
        setID_user(ID);
        setOfficialName(name);
        setAddress(Address);
        setJuridicForm(juridicForm);
        setUniqueCODE(uniqueCODE);
        setActivity_COD(activity_COD);
        setDateBirth(dateBirth);

        //System.out.println(ID_user);

        this.list_attributes = new ArrayList<>();

        list_attributes.add(String.valueOf(ID));
        list_attributes.add(name);
        list_attributes.add(Address);
        list_attributes.add(juridicForm);
        list_attributes.add(uniqueCODE);
        list_attributes.add(activity_COD);
        list_attributes.add(dateBirth.toString());

    }
    /**
     * Getter for the name of the organization
     * @return String
     */
    public String getOfficialName() {
        return officialName;
    }
    /**
     * Setter for the name of the organization
     * @return void
     */
    public void setOfficialName(String officialName) {
        this.officialName = officialName;
    }
    /**
     * Getter for the address of the organization
     * @return String
     */
    public String getAddress() {
        return Address;
    }
    /**
     * Setter for the address of the organization
     * @return String
     */

    public void setAddress(String address) {
        Address = address;
    }

    /**
     * Getter for the juridic form of the organization
     * @return String
     */
    public String getJuridicForm() {
        return JuridicForm;
    }
    /**
     * Setter for the juridic of the organization
     * @return void
     */
    public void setJuridicForm(String juridicForm) {
        JuridicForm = juridicForm;
    }
    /**
     * Getter for the unique code  of the organization
     * @return String
     */

    public String getUniqueCODE() {
        return UniqueCODE;
    }
    /**
     * Setter for the unique code  of the organization
     * @return void
     */

    public void setUniqueCODE(String uniqueCODE) {
        UniqueCODE = uniqueCODE;
    }
    /**
     * Getter for the activity code  of the organization
     * @return String
     */


    public String getActivity_COD() {
        return Activity_COD;
    }
    /**
     * Setter for the activity code  of the organization
     * @return void
     */


    public void setActivity_COD(String activity_COD) {
        Activity_COD = activity_COD;
    }
    /**
     * Getter for the date birth of the organization
     * @return Date
     */
    public Date getDateBirth() {
        return dateBirth;
    }
    /**
     * Setter for the  date birth   of the organization
     * @return void
     */
    public void setDateBirth(Date dateBirth) {
        this.dateBirth = dateBirth;
    }


}
