package info.androidhive.navigationdrawer.activity;

import android.util.Base64;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class EncryptDecrypt {
    private static final String ALGO="AES";
    private Byte[] keyValue;
    public static String key;
    public EncryptDecrypt(String key)
    {
        this.key=key;
    }

    public String decrypt(String output) throws Exception {
        SecretKeySpec key= generateKey();
        Cipher c= Cipher.getInstance(ALGO);
        c.init(Cipher.DECRYPT_MODE,key);
        byte[] deCodedValue=Base64.decode(output,Base64.DEFAULT);
        byte[] decVal=c.doFinal(deCodedValue);
        String decryptedData= new String(decVal);
        return decryptedData;
    }

    public String encrypt(String Data) throws Exception {
        SecretKeySpec key= generateKey();
        Cipher c= Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE,key);
        byte[] encVal= c.doFinal(Data.getBytes());
        String encryptedValue = Base64.encodeToString(encVal,Base64.DEFAULT);
        return encryptedValue;
    }

    private SecretKeySpec generateKey() throws Exception{
        final MessageDigest digest= MessageDigest.getInstance("SHA-256");
        byte[] bytes=key.getBytes("UTF-8");
        digest.update(bytes, 0, bytes.length);
        byte[] key= digest.digest();
        SecretKeySpec secretKey= new SecretKeySpec(key,"com.example.hamzaarif.myapplication.AES");
        return secretKey;
    }


}
