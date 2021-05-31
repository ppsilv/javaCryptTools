package pdsilva.teste.des;
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

import pdsilva.criptografia.utils.*;

public class EncryptDecryptStringWithDES {

	private static Cipher ecipher;
	private static Cipher dcipher;
	private static SecretKey key;
	   private static KeySpec keySpec;
	   private static SecretKeyFactory secretKeyFactory;
	   private static SecretKey secretKey;
   
	
	   public static  SecretKey normalizaDesKey(byte[] key) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
		     keySpec = new DESKeySpec( key );
		     secretKeyFactory = SecretKeyFactory.getInstance( "DES" );
		     secretKey = secretKeyFactory.generateSecret( keySpec );
		     return secretKey;
	   }	

	
	public static void test() throws DecoderException, InvalidKeySpecException, InvalidAlgorithmParameterException {

		try {
			// generate secret key using DES algorithm
			byte[] p1chaveSessao = Hex.decodeHex("C8CB1A7F40FE89A8".toCharArray());
			Util.print(p1chaveSessao, 8);
			key = normalizaDesKey(p1chaveSessao);
			//KeyGenerator keygenerator = KeyGenerator.getInstance("DES");
		    //SecretKey key = keygenerator.generateKey();
			
			// desCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");	
			ecipher = Cipher.getInstance("DES/CBC/NOPadding");
			dcipher = Cipher.getInstance("DES/CBC/NOPadding");
			
			//byte[] iv = new byte[8]; //Hex.decodeHex("0000000000000000".toCharArray());
			byte[] iv = Hex.decodeHex("0000000000000000".toCharArray());
			//iv[0]=0;iv[1]=0;iv[2]=0;iv[3]=0;
			//iv[4]=0;iv[5]=0;iv[6]=0;iv[7]=0;
			
			IvParameterSpec ips = new IvParameterSpec(iv);
			//desCipher.init(Cipher.ENCRYPT_MODE, key, ips);
			// initialize the ciphers with the given key

			ecipher.init(Cipher.ENCRYPT_MODE, key, ips);
			byte[] e1 = ecipher.getIV();
			dcipher.init(Cipher.DECRYPT_MODE, key, ips);
			byte[] d1 = dcipher.getIV();
			System.out.print("Encriptado.IV..:  "); Util.print(e1, e1.length);
			System.out.print("Decriptado.IV..:  "); Util.print(d1, d1.length);
			
            byte[] abc = new byte[128];
		    abc = Hex.decodeHex("0000000085000000000000000840000000000008402003310114834F7C5800006925000004400080".toCharArray());

			//byte[] encrypted = encrypt("This is a classified message!");
			byte[] encrypted = encrypt( abc );
			System.out.println("Encriptado...:  "); Util.print(encrypted, encrypted.length);
			byte[] result = Util.copyByteArray(encrypted, 24, 8);
			System.out.println("Result...:  ");Util.print(result,8);
			byte[] blocoTagsLastByte = Hex.decodeHex("6925000004400080".toCharArray());
			byte[] abcd = Util.xorOperationOnArray(result,blocoTagsLastByte,8);
			System.out.println("Result xor...:  ");Util.print(abcd,8);

			byte[] decrypted = decrypt(encrypted);
			System.out.println("\nDEcriptado...:  "); Util.print(decrypted, decrypted.length);

		} catch (NoSuchAlgorithmException e) {
			System.out.println("No Such Algorithm:" + e.getMessage());
			return;
		} catch (NoSuchPaddingException e) {
			System.out.println("No Such Padding:" + e.getMessage());
			return;
		} catch (InvalidKeyException e) {
			System.out.println("Invalid Key:" + e.getMessage());
			return;
		}

	}

	public static byte[] encrypt(byte[] str) {

		try {
			byte[] enc = ecipher.doFinal(str);

			return enc;

		}

		catch (Exception e) {

			e.printStackTrace();

		}

		return null;

	}

	public static byte[] decrypt(byte[] dec) {
		try {

			byte[] result = dcipher.doFinal(dec);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}