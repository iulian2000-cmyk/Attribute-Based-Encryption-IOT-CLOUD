package ABDS_System;

import Users.User;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;


public class FileStored {

    private String pathToFile;
    private User ownerFile;
    private List<BlockFile> blocksFile;
    private static SecretKeySpec secretKey;
    private static byte[] key;
    private static String path;

    public static String getPath() {
        return path;
    }

    public static void setPath(String path) {
        FileStored.path = path;
    }

    FileStored(String pathToFile, User ownerFile, String pathSavedFile)
    {
        setOwnerFile(ownerFile);
        setPathToFile(pathToFile);
        setPath(pathSavedFile);
        uploadFile(pathSavedFile);
    }


    public String getPathToFile() {
        return pathToFile;
    }

    public void setPathToFile(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    public User getOwnerFile() {
        return ownerFile;
    }

    public void setOwnerFile(User ownerFile) {
        this.ownerFile = ownerFile;
    }

    public List<BlockFile> getBlocksFile() {
        return blocksFile;
    }

    public void setBlocksFile(List<BlockFile> blocksFile) {
        this.blocksFile = blocksFile;
    }

    private void uploadFile(String pathSavedFile)
    {
        try (
                InputStream inputStream = new FileInputStream(pathToFile);
                OutputStream outputStream = new FileOutputStream(pathSavedFile);
        ) {
            int ID_block=0;
            int byteRead;
            blocksFile = new ArrayList<>();
            while ((byteRead = inputStream.read()) != -1) {
                   // Here the encrypted block by block take place .
                BlockFile blockFile = new BlockFile(ID_block,EncryptBlock(String.valueOf(byteRead),pathToFile));
                outputStream.write(blockFile.getEncrypted_DATA().getBytes());
                blocksFile.add(blockFile);
                ID_block++;
            }
            outputStream.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void downloadFile(String uploadLocation) throws Exception
    {
        InputStream inputStream = new FileInputStream(pathToFile);
        OutputStream outputStream = new FileOutputStream(uploadLocation);
        for(BlockFile blockFile : blocksFile)
        {
            outputStream.write(Integer.valueOf(decryptBlock(blockFile.getEncrypted_DATA(),pathToFile)));
        }
        outputStream.close();
    }
    public static String EncryptBlock(String strToEncrypt, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        }
        catch (Exception e)
        {
            System.out.println("Eroare la criptarea blocului : " + e.toString());
        }
        return null;
    }

    public static void setKey(String myKey)
    {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    public static String decryptBlock(String strToDecrypt, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        }
        catch (Exception e)
        {
            System.out.println("Eroare la decriptarea blocului:" + e.toString());
        }
        return null;
    }
}
