package edu.icet.crm.util;

import org.mindrot.jbcrypt.BCrypt;

public class EncryptPassword {
    private static EncryptPassword encryptPassword;
    private EncryptPassword(){}
    public static EncryptPassword getInstance(){
        return encryptPassword==null?encryptPassword=new EncryptPassword() : encryptPassword;
    }
    public String hashingPassword(String plainPassword){
        return BCrypt.hashpw(plainPassword,BCrypt.gensalt());
    }

    public boolean checkPassword(String plainPassword,String hashedPassword){
        return BCrypt.checkpw(plainPassword,hashedPassword);
    }
}
