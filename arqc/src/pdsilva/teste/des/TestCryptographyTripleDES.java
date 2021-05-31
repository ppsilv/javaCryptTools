package pdsilva.teste.des;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import pdsilva.criptografia.utils.*;

public class TestCryptographyTripleDES {

  public static void main( String[] args ) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException, IOException, DecoderException, InvalidAlgorithmParameterException {

   C3des cryptography = C3des.newInstance();
   Cdes cryptographyDes = Cdes.newInstance();
   /*
   String value = "Rodrigo Lazoti";
   System.out.println( "Valor utilizado => " + value );
   byte[] encryptedValue = cryptography.encrypt( value );
   System.out.println( "Valor criptografado => " + encryptedValue );
   Util.print(encryptedValue, 16);
   
   byte[] decryptedValue = cryptography.decrypt( encryptedValue );
   System.out.println( "Valor descriptografado => " + decryptedValue );
   String str = new String(decryptedValue);
   Util.print(decryptedValue, 14);
   System.out.println( "Valor descriptografado => " + str );

   byte[] key = outputStream.toByteArray( );
   byte[] encryptedValue1 = cryptography.encrypt( value, key );
   System.out.println( "Valor criptografado => " + encryptedValue1 );
   Util.print(encryptedValue1, 16);   
   //e4 fb 47 23 cf c4 0f 10 f5 8a 00 89 1a c1 fe d6
   */
     
  
  
   //PAN 5555315497560938
   //Bloco pan sem o primeiro byte com 0 no ultimo byte
   //Bloco 5531549756093800
   byte[] bloco = new byte[] {0x55,0x31,0x54,(byte)0x97,0x56,0x09,0x38,0x0};
   //Faz um NOR com o bloco
   byte[] bloconor = Util.notOperationOnArray(bloco,8);
   Util.print( bloconor, 8 );
   //Bloconor AACEAB68A9F6C7FF
   ByteArrayOutputStream blocoStrem = new ByteArrayOutputStream( );
   blocoStrem.write( bloco );
   blocoStrem.write( bloconor );
   byte[] blocoresult = blocoStrem.toByteArray( );
   Util.print( blocoresult, 16 );
   //Resultado da concatenação do bloco com o bloconor
   //5531549756093800AACEAB68A9F6C7FF
   //Realizar encrypt 3DES do resultado do bloco, com chave IMKac.(MDK bptools)
   //chave IMKac 9E15204313F7318ACB79B90BD986AD29
   byte [] IMKac = new byte[24];
   IMKac = Hex.decodeHex("9E15204313F7318ACB79B90BD986AD299E15204313F7318A".toCharArray());
   //byte [] IMKac = new byte[] {(byte)0x9E,0x15,0x20,0x43,0x13,(byte)0xF7,0x31,(byte)0x8A,(byte)0xCB,0x79,(byte)0xB9,0x0B,(byte)0xD9,(byte)0x86,(byte)0xAD,0x29, (byte)0x9E,0x15,0x20,0x43,0x13,(byte)0xF7,0x31,(byte)0x8A};
   Util.print( IMKac, 24 );
   byte [] encryptedValue1 = cryptography.encrypt( blocoresult, IMKac );
   Util.print( encryptedValue1, 16 );
   //Resultado da criptografia 83DCCD115BCA1D617E857B20DB52BEF8
   // Ajustar a paridade. 
   // Resultado: 83DCCD105BCB1C617F857A20DA52BFF8 (Chave diversificada)  
   byte[] chaveDiversificadaParAdj = new byte[16];
   chaveDiversificadaParAdj = Util.parityAdjusted(encryptedValue1);
   /*
   byte[] segParteChaveDiversificada = new byte[8];
   segParteChaveDiversificada = Arrays.copyOf(chaveDiversificadaParAdj, 8);
   Util.print( segParteChaveDiversificada, 8 );
   byte[] chaveDiversificadaFinal = new byte[24];
   //chaveDiversificadaFinal = streamchaveDiversificadaFinal.toByteArray( );
   chaveDiversificadaFinal = Util.concatenateTwoByteArray(chaveDiversificadaParAdj,16,segParteChaveDiversificada,8);
   Util.print( chaveDiversificadaFinal, 24);
   */
   byte[] chaveDiversificadaFinal = Util.normalizaChave3des(chaveDiversificadaParAdj);
   
   //Monta bloco diversificador
   byte[] tag9f36 = {0,0x69};
   byte[] blocoDiversificador = Util.montaBlocoDiversificador(tag9f36);
   //Aplicar 3des no blocodiversificador com a chave diversificada
   byte[] chaveSessao = new byte[16];
   chaveSessao = cryptography.encrypt( blocoDiversificador, chaveDiversificadaFinal );
   Util.print( chaveSessao, 16 );
   byte[] chaveSessaoParAdj = new byte[16];
   chaveSessaoParAdj = Util.parityAdjusted(chaveSessao);

   //Geração do Criptograma (ARQC) TAG 9F26 do bit55 
   //Montar bloco de tags (somente os valores das tags do bit55: 9F02, 9F03, 9F1A, 95, 5F2A, 9A, 9C, 9F37, 82, 9F36, CVR(PARTE DA TAG 9F10)
   
   
   byte[] p1chaveSessao = new byte[8];
   p1chaveSessao = Hex.decodeHex("C8CB1A7F40FE89A8".toCharArray()); //BA020B4543946201
   byte[] abc = new byte[128];
   abc = Hex.decodeHex("0000000085000000000000000840000000000008402003310114834F7C580000".toCharArray());
   byte [] quasela = cryptographyDes.encrypt( abc, p1chaveSessao );
   Util.print( quasela, quasela.length );
   byte [] resultBlocoTags = Util.copyByteArray(quasela, 24, 8);
   Util.print( resultBlocoTags, resultBlocoTags.length );
   
   
   //Xor do ultimo byte das concatenação das tags com o resultado da criptografia DES dos 5 primeiros bytes da concatenação das tags
   byte[] blocoTagsXor = Hex.decodeHex("6925000004400080".toCharArray());
   byte[] blocoTagsXorResult = Util.xorOperationOnArray(resultBlocoTags, blocoTagsXor, 8);
   System.out.println("Xor.:  "); Util.print( blocoTagsXorResult, 8 );
  
   byte[] chaveSessaoParAdjFinal = Util.normalizaChave3des(chaveSessaoParAdj);
   
   byte[] arqc = cryptography.encrypt( blocoTagsXorResult, chaveSessaoParAdjFinal );
   System.out.println("Arqc.:  "); Util.print( arqc, 8 );
   
 }

}