package ABDS_System;

public class BlockFile {

    private int ID_block;
    private String Encrypted_DATA;



    BlockFile(int ID,String DATA_encrypted)
    {
        setID_block(ID);
        setEncrypted_DATA(DATA_encrypted);
    }

    public int getID_block() {
        return ID_block;
    }

    public void setID_block(int ID_block) {
        this.ID_block = ID_block;
    }

    public String getEncrypted_DATA() {
        return Encrypted_DATA;
    }

    public void setEncrypted_DATA(String encrypted_DATA) {
        Encrypted_DATA = encrypted_DATA;
    }
}
