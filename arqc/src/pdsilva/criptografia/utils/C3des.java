package pdsilva.criptografia.utils;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;





/**
 * Class that supplies a criptography of triple type DES.
 * @author Rodrigo Lazoti
 * @since 05/07/2009
 */
public class C3des {
	private static final String DESTYPE = "DESede";
   private Cipher cipher;
   private byte[] encryptKey;
   private KeySpec keySpec;
   private SecretKeyFactory secretKeyFactory;
   private SecretKey secretKey;

   public static final Logger slf4j = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
   public static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getGlobal();
    
   /**
   * Method that create a new instance of class.
   * @return
   * @throws InvalidKeyException
   * @throws UnsupportedEncodingException
   * @throws NoSuchAlgorithmException
   * @throws NoSuchPaddingException
   * @throws InvalidKeySpecException
   */
   public static C3des newInstance() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException {
     return new C3des();
   }

   /**
   * Default Constructor.
   * @throws UnsupportedEncodingException
   * @throws NoSuchAlgorithmException
   * @throws NoSuchPaddingException
   * @throws InvalidKeyException
   * @throws InvalidKeySpecException
   */
   private C3des() throws  NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
	     cipher = Cipher.getInstance( DESTYPE );
	     var key = "http://www.rodrigolazoti.com.br";
	     encryptKey = key.getBytes( StandardCharsets.UTF_8 );
	     LOGGER.log(Level.INFO,"Tam {0}", encryptKey.length);
	     keySpec = new DESedeKeySpec( encryptKey );
	     secretKeyFactory = SecretKeyFactory.getInstance( DESTYPE );
	     secretKey = secretKeyFactory.generateSecret( keySpec );
   }

   /**
   * Method that encrypts a value.
   * @param value
   * @return
   * @throws InvalidKeyException
   * @throws IllegalBlockSizeException
   * @throws BadPaddingException
   * @throws UnsupportedEncodingException
   */
   public  byte[] encrypt( String value ) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
     cipher.init( Cipher.ENCRYPT_MODE, secretKey );
     return cipher.doFinal( value.getBytes( StandardCharsets.UTF_8) );
   }

   /**
   * Methot that decrypts a value.
   * @param value
   * @return
   * @throws InvalidKeyException
   * @throws IllegalBlockSizeException
   * @throws BadPaddingException
   * @throws IOException
   */
   public byte[] decrypt( String value ) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
     cipher.init( Cipher.DECRYPT_MODE, secretKey );
     return cipher.doFinal( value.getBytes(StandardCharsets.UTF_8) );
   }
   public byte[] decrypt( byte[] value ) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
	     cipher.init( Cipher.DECRYPT_MODE, secretKey );
	     return cipher.doFinal( value );
   }
   public  SecretKey normalizaKey(byte[] key) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
	     keySpec = new DESedeKeySpec( key );
	     secretKeyFactory = SecretKeyFactory.getInstance( DESTYPE );
	     return secretKeyFactory.generateSecret( keySpec );
   }
   public  byte[] encrypt( String value, byte[] key ) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeySpecException {
	   secretKey= normalizaKey(key);
	   cipher.init( Cipher.ENCRYPT_MODE, secretKey );
	   return cipher.doFinal( value.getBytes( StandardCharsets.UTF_8 ) );
   }
   public  byte[] encrypt( byte[] value, byte[] key ) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeySpecException {
	   secretKey= normalizaKey(key);
	   cipher.init( Cipher.ENCRYPT_MODE, secretKey );
	   return cipher.doFinal( value );
   }
   public byte[] decrypt( String value, byte[] key ) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeySpecException {
	    secretKey= normalizaKey(key);
		cipher.init( Cipher.DECRYPT_MODE, secretKey );
		return cipher.doFinal( value.getBytes(StandardCharsets.UTF_8) );
   }
   public byte[] decrypt( byte[] value ,byte[] key) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeySpecException {
	    secretKey= normalizaKey(key);
		cipher.init( Cipher.DECRYPT_MODE, secretKey );
		return cipher.doFinal( value );
   }
}