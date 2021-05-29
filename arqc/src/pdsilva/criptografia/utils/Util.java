/**
 * 
 */
package pdsilva.criptografia.utils;

/**
 * @author pdsilva
 *
 */
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class Util {
	   public static final Logger slf4j = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
	   public static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getGlobal();

	private Util() {
		/**
		 * This method is intentionally empty.
		 */
		
	}
    /**
     * DES Keys use the LSB as the odd parity bit.  This method can
     * be used enforce correct parity.
     *
     * @param bytes the byte array to set the odd parity on.
     */
    public static void adjustDESParity (byte[] bytes) {
        for (var i = 0; i < bytes.length; i++) {
            int b = bytes[i];
            bytes[i] = (byte)((b & 0xfe) | ((((b >> 1) ^ (b >> 2) ^ (b >> 3) ^ (b >> 4) ^ (b >> 5) ^ (b >> 6) ^ (b >> 7)) ^ 0x01) & 0x01));
        }
    }

    /**
     * DES Keys use the LSB as the odd parity bit.  This method checks
     * whether the parity is adjusted or not
     *
     * @param bytes the byte[] to be checked
     * @return true if parity is adjusted else returns false
     */
    public static boolean isDESParityAdjusted (byte[] bytes) {
        byte[] correct = bytes.clone();
        adjustDESParity(correct);
        print(bytes,16);
        print(correct,16);
        return  Arrays.equals(bytes, correct);
    }
    
    public static byte[] parityAdjusted (byte[] bytes) {
        byte[] correct = bytes.clone();
        adjustDESParity(correct);
        LOGGER.info("Key........:  ");print(bytes,16);
        LOGGER.info("Key par.adj:  ");print(correct,16);
        return  correct;
    }

    public static void print(byte[] bytes, int size) {
    	for(var i=0; i < size; i++) {
    		System.out.printf("%02X", bytes[i]);
    	}
    	
    }
    
    public static byte[] notOperationOnArray(byte[] arr, int size) {
    	var result = new byte[32];
    	for(var i=0;i<size;i++) {
    		result[i] = (byte) ( arr[i] ^ 0xFF );
    	}
    	return result;
    }
    public static byte[] xorOperationOnArray(byte[] arr, byte[] arr1, int size) {
    	var result = new byte[32];
    	for(var i=0;i<size;i++) {
    		result[i] = (byte) ( arr[i] ^ arr1[i] );
    	}
    	return result;
    }
    public static String toHexString(String str) {
        var sb = new StringBuilder();
        char[] ch = str.toCharArray();
        for(var i = 0; i < ch.length; i++) {
           var hexString = Integer.toHexString(ch[i]);
           sb.append(hexString);
        }
        var result = sb.toString();
        LOGGER.info(result);
        return result;
     }
    
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        var hexChars = new char[bytes.length * 2];
        for (var j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
    public static byte[] montaBlocoDiversificador( byte[] tag9f36  ) throws DecoderException, IOException {
    	 final byte[] cf0 = Hex.decodeHex("F0".toCharArray());
    	 final byte[] c0f = Hex.decodeHex("0F".toCharArray());
    	 final byte[] cpad= Hex.decodeHex("0000000000".toCharArray());
    	   var blocoStrem = new ByteArrayOutputStream( );
    	   blocoStrem.write( tag9f36 ); //ATC
    	   blocoStrem.write( cf0 );
    	   blocoStrem.write( cpad );
    	   blocoStrem.write( tag9f36 ); //ATC
    	   blocoStrem.write( c0f );
    	   blocoStrem.write( cpad );
    	   byte[] blocoDiversificador = blocoStrem.toByteArray( );
    	   print(blocoDiversificador,blocoDiversificador.length);
    	   return blocoDiversificador;
    }

    public static byte[] concatenateTwoByteArray(byte[] barrayOne,int size1, byte[] barraytwo,int size2) {
    	var result = new byte[size1 + size2];
    	System.arraycopy(barrayOne, 0, result, 0, size1);
    	System.arraycopy(barraytwo, 0, result, size1, size2);
    	return result;
    }    
    
    public static byte[] copyByteArray(byte[] arr, int pos, int size) {
    	var result= new byte[size];
    	for(int i = pos, j = 0;i< (size+pos);i++,j++) {
    		result[j] = arr[i];
    	}
    	return result;
    }
    
    public static byte[] normalizaChave3des(byte[] chaveParAdj) {
    	   var segParteChaveDiversificada = Arrays.copyOf(chaveParAdj, 8);
    	   LOGGER.info("Seg.part.: ");Util.print( segParteChaveDiversificada, 8 );
    	   var chaveDiversificadaFinal = Util.concatenateTwoByteArray(chaveParAdj,16,segParteChaveDiversificada,8);
    	   LOGGER.info("Chave normalizada.: ");Util.print( chaveDiversificadaFinal, 24);
		return chaveDiversificadaFinal;    	
    }
}