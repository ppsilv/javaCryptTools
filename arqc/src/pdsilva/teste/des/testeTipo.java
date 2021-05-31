package pdsilva.teste.des;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.nio.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

import pdsilva.criptografia.utils.*;
import pdsilva.de55.MontaDE55;

public class testeTipo {

	public testeTipo() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws DecoderException, IOException {
		MontaDE55 de55 = new MontaDE55.Builder()
				.withTransactionCurrencyCode      ("0840")        
				.withAplicationInterchangeProfile ("5800")        
				.withTerminalVerificationResult   ("0000000000")  
				.withTransactionDate              ("200423")      
				.withTransactionType              ("00")          
				.withTrnAmount                    ("000000001500")
				.withIssuerApplicationData        ("0114250000044000DAC10000000000000000")
				.withTerminalCountryCode          ("0840")        
				.withAtc                          ("0001")        
				.withUnpredictableNumber          ("354551AE")    
				.withAmountCashBack               ("000000000000")
				.withDedicatedFileName            ("A0000000041010")
				.withCryptogramInformationData    ("80")
				.withTerminalCapabilities         ("E0E8E8")
				.withCardholderVerificationMethodResults("410302")
				.build();
		System.out.println(de55.toString());

		try {
			EncryptDecryptStringWithDES.test();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DecoderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
