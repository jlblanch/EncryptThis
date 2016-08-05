package capstone.encryption;

/**
 * Created by Jeremy Blanchard on 7/13/2016. This class takes a file as an input
 * stream which is saved to a byte array In addition to the encrypted file
 * creation, 2 additional files are created which are needed for decryption.
 *
 * All files and keys are generated and converted to a byte array which is then
 * output as files.
 */
//File IO imports
import capstone.fileio.FileIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

//Cryptography imports
import java.security.AlgorithmParameters;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * This class performs the file encryption. First a password is created by the
 * user which is then combined with a SecureRandom and then hashed.
 *
 * @author Jeremy Blanchard
 */
public class Encryption {

    static FileIO fio;
    private static final String ALG = "AES";
    private static final String CIPH = "AES/CBC/PKCS5Padding";
    private static final String KEYFAC = "PBKDF2WithHmacSHA1";

   

    /**
     *
     * @param file the file to be encrypted
     * @param password The users password
     * @return the output filename
     * @throws Exception
     */
    public static String encrypt(File file, String password) throws Exception {
        FileOutputStream outFile;
        // output file stream
        try (
                // file to be encrypted as input stream
                FileInputStream inFile = new FileInputStream(file)) {
            // output file stream
            outFile = new FileOutputStream(file + ".aes");

            
            

            //create salt            
            byte[] salt = new byte[8];
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.nextBytes(salt);

            // Write the salt to output file stream
            outFile.write(salt);

            //Generate Secret Key
            SecretKeyFactory factory = SecretKeyFactory
                    .getInstance(KEYFAC);
            KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536,
                    128);
            SecretKey secretKey = factory.generateSecret(keySpec);
            SecretKey secret = new SecretKeySpec(secretKey.getEncoded(), ALG);

            // Initialize the cipher
            Cipher cipher = Cipher.getInstance(CIPH);
            cipher.init(Cipher.ENCRYPT_MODE, secret);
            AlgorithmParameters params = cipher.getParameters();
            byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();

            //output the iv to the file output stream
            outFile.write(iv);

            //encrypt the input file
            byte[] input = new byte[64];
            int bytesRead;
            while ((bytesRead = inFile.read(input)) != -1) {
                byte[] output = cipher.update(input, 0, bytesRead);
                if (output != null) {
                    outFile.write(output);
                }
            }
            byte[] output = cipher.doFinal();
            if (output != null) {
                outFile.write(output);
            }
        }

        outFile.flush();
        outFile.close();

        

        return file + ".aes";

    }

}
