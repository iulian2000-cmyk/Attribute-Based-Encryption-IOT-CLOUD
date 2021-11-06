package Users;

import SpecialDataStructures.ACCORD_SUPERIOR;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Individual extends User
{
    private String Name;
    private String Address;
    private String CNP;
    private Date DateBirth;
    private JuridicPerson placeJOB;
    private Position position;
    private static ACCORD_SUPERIOR accord_superior;

    public static ACCORD_SUPERIOR getAccord_superior() {
        return accord_superior;
    }

    public static void setAccord_superior(ACCORD_SUPERIOR accord_superior) {
        Individual.accord_superior = accord_superior;
    }

    public Individual(int ID, String name, String Address, String CNP, Date date, JuridicPerson job, Position pos,ACCORD_SUPERIOR accord_superior)
    {
        this.ID_user = ID;
        this.setName(name);
        this.setAddress(Address);
        this.setCNP(CNP);
        this.setDateBirth(date);
        this.setPlaceJOB(job);
        this.setPosition(position);
        this.setAccord_superior(accord_superior);
        this.list_attributes = new ArrayList<>();

        list_attributes.add(String.valueOf(ID));
        list_attributes.add(name);
        list_attributes.add(Address);
        list_attributes.add(CNP);
        list_attributes.add(date.toString());
        list_attributes.add(job.getOfficialName());
        list_attributes.add(pos.toString());
    }
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCNP() {
        return CNP;
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }

    public Date getDateBirth() {
        return DateBirth;
    }

    public void setDateBirth(Date dateBirth) {
        DateBirth = dateBirth;
    }

    public JuridicPerson getPlaceJOB() {
        return placeJOB;
    }

    public void setPlaceJOB(JuridicPerson placeJOB) {
        this.placeJOB = placeJOB;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }



}
