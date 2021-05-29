package pdsilva.criptografia.utils;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class Cdes {
	private static Cipher ecipher;
	private static Cipher dcipher;
	private static SecretKey key;
	private static IvParameterSpec ips ;
	
	private Cdes () {
		try {
			ecipher = Cipher.getInstance("DES/CBC/NOPadding");
			dcipher = Cipher.getInstance("DES/CBC/NOPadding");
			byte[] iv;
			iv = Hex.decodeHex("0000000000000000".toCharArray());
			ips = new IvParameterSpec(iv);			
		} catch (NoSuchAlgorithmException | NoSuchPaddingException  | DecoderException e) {
			e.printStackTrace();
		} 
	}
	
	public static Cdes  newInstance() {
		return new Cdes();
	}
	public static SecretKey normalizaDesKey(byte[] key)
			throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
		KeySpec keySpec = new DESKeySpec(key);
		var secretKeyFactory = SecretKeyFactory.getInstance("DES");
		return secretKeyFactory.generateSecret(keySpec);
	}

	public static byte[] encrypt(byte[] str,byte[] keyIn ) {

		try {
			key = normalizaDesKey(keyIn);
			ecipher.init(Cipher.ENCRYPT_MODE, key, ips);
			return ecipher.doFinal(str);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return new byte[1];
	}

	public static byte[] decrypt(byte[] dec,byte[] keyIn ) {
		try {
			key = normalizaDesKey(keyIn);
			dcipher.init(Cipher.DECRYPT_MODE, key, ips);
			return dcipher.doFinal(dec);
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new byte[1];
	}

}
