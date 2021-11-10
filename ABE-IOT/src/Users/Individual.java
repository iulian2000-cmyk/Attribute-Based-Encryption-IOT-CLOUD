package Users;

import SpecialDataStructures.ACCORD_SUPERIOR;

import java.util.ArrayList;
import java.util.Date;


public class Individual extends User
{
    private  String Name;
    private  String Address;
    private  String CNP;
    private  Date DateBirth;
    private  JuridicPerson placeJOB;
    private  Position position;
    private  ACCORD_SUPERIOR accord_superior;

    public  ACCORD_SUPERIOR getAccord_superior() {
        return accord_superior;
    }

    public  void setAccord_superior(ACCORD_SUPERIOR accord_superior_firm) {
       accord_superior = accord_superior_firm;

    }

    /**
     * Setter for ID user
     * @param ID_user_to_add user id
     */
    public void setID_user(int ID_user_to_add) {
        this.ID_user = ID_user_to_add;
    }
    public Individual(int ID, String name, String Address, String CNP, Date date, JuridicPerson job, Position pos,ACCORD_SUPERIOR accord_superior)
    {
        setID_user(ID);
        setName(name);
        setAddress(Address);
        setCNP(CNP);
        setDateBirth(date);
        setPlaceJOB(job);
        setPosition(pos);
        setAccord_superior(accord_superior);



        //System.out.println(ID_user);
        list_attributes = new ArrayList<>();

        list_attributes.add(String.valueOf(ID));
        list_attributes.add(name);
        list_attributes.add(Address);
        list_attributes.add(CNP);
        list_attributes.add(date.toString());
        list_attributes.add(job.getOfficialName());
        list_attributes.add(pos.toString());
    }
    public  String getName() {
        return Name;
    }

    public  void setName(String name) {
        Name = name;
    }

    public  String getAddress() {
        return Address;
    }

    public  void setAddress(String address) {
        Address = address;
    }

    public  String getCNP() {
        return CNP;
    }

    public  void setCNP(String CNP_user) {
        CNP = CNP_user;
    }
    /**
     * Getter for ID user
     * @return int
     */
    public  int getID_user() {
        return super.ID_user;
    }
    public  Date getDateBirth() {
        return DateBirth;
    }

    public  void setDateBirth(Date dateBirth) {
        DateBirth = dateBirth;
    }

    public  JuridicPerson getPlaceJOB() {
        return placeJOB;
    }

    public  void setPlaceJOB(JuridicPerson placeJOB_user) {
        placeJOB = placeJOB_user;
    }

    public  Position getPosition() {
        return position;
    }

    public  void setPosition(Position position_firm) {
        position = position_firm;
    }



}
