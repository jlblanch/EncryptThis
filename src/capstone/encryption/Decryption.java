/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone.encryption;
import capstone.fileio.FileIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
/**
 *
 * @author Jeremy Blanchard
 */
public class Decryption {
    static FileIO fio;
    private static final String ALG = "AES";
    private static final String CIPH = "AES/CBC/PKCS5Padding";
    private static final String KEYFAC = "PBKDF2WithHmacSHA1";
    
    
       
    /**
     * 
     * @param file
     * @param password
     * @return
     * @throws Exception 
     */
    public static String decrypt(File file, String password) throws Exception {
	
        byte[] salt = new byte[8];
        
        // reading the salt
        // user should have secure mechanism to transfer the
        // salt, iv and password to the recipient
        FileInputStream fis = new FileInputStream(file);
        fis.read(salt);
        /*
        try ( 
                FileInputStream saltFis = new FileInputStream("salt.enc")) {
            salt = new byte[8];
            saltFis.read(salt);
        }
        */
        byte[] iv = new byte[16];
        
        // reading the iv
        fis.read(iv);
        /*
        try ( 
                FileInputStream ivFis = new FileInputStream("iv.enc")) {
            iv = new byte[16];
            ivFis.read(iv);
        }
        */

	SecretKeyFactory factory = SecretKeyFactory
			.getInstance(KEYFAC);
	KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536,
			128);
	SecretKey tmp = factory.generateSecret(keySpec);
	SecretKey secret = new SecretKeySpec(tmp.getEncoded(), ALG);

	// file decryption
        
	Cipher cipher = Cipher.getInstance(CIPH);
	cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));
        String fName;
        FileOutputStream fos;
        
        
        
            fName = file.toString().substring(0, file.toString().length()-4);
            fos = new FileOutputStream(fName);
            byte[] in = new byte[64];
            int read;
            while ((read = fis.read(in)) != -1) {
                byte[] output = cipher.update(in, 0, read);
                if (output != null)
                    fos.write(output);
            }   
            byte[] output = cipher.doFinal();
            if (output != null)
                fos.write(output);
        
	fos.flush();
	fos.close();
	
        return fName;
    }

}
