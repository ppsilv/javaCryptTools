package pdsilva.de55;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import pdsilva.criptografia.utils.Util;

public class MontaDE55 {
	 private final String TrnAmount;              
	 private final String AmountCashBack;  
	 private final String DedicatedFileName;
	 private final String TerminalCountryCode;          
	 private final String TerminalVerificationResult;  
	 private final String TransactionCurrencyCode;     
	 private final String TransactionDate;             
	 private final String TransactionType;             
	 private final String UnpredictableNumber;         
	 private final String AplicationInterchangeProfile;
	 private final String Atc;                         
	 private final String IssuerApplicationData;       
	 private final String CryptogramInformationData;
	 private final String TerminalCapabilities;
	 private final String CardholderVerificationMethodResults;
	 
	 public MontaDE55(Builder build) {
	        //public MontaDE55 build(){
	            //Here we create the actual bank account object, which is always in a fully initialised state when it's returned.
	        	//MontaDE55 de55 = new MontaDE55();  //Since the builder is in the BankAccount class, we can invoke its private constructor.		de55.trnAmount                    = this.trnAmount                   ;
	        	
	    		this.TrnAmount              	  = build.TrnAmount              ;
	    		this.AmountCashBack               = build.AmountCashBack              ;
	    		this.DedicatedFileName            = build.DedicatedFileName;
	    		this.TerminalCountryCode          = build.TerminalCountryCode          ;
	    		this.TerminalVerificationResult   = build.TerminalVerificationResult  ;
	    		this.TransactionCurrencyCode      = build.TransactionCurrencyCode     ;
	    		this.TransactionDate              = build.TransactionDate             ;
	    		this.TransactionType              = build.TransactionType             ;
	    		this.UnpredictableNumber          = build.UnpredictableNumber         ;
	    		this.AplicationInterchangeProfile = build.AplicationInterchangeProfile;
	    		this.Atc                          = build.Atc                         ;
	    		this.IssuerApplicationData        = build.IssuerApplicationData       ;
	   		    this.CryptogramInformationData    = build.CryptogramInformationData ;
			    this.TerminalCapabilities         = build.TerminalCapabilities ;
			    this.CardholderVerificationMethodResults= build.CardholderVerificationMethodResults   ;
				
	        //}	
	 }
    @Override
    public String toString() {
    	String  len = String.valueOf(IssuerApplicationData.length() / 2) ;
    	
		String result = null;
		 
		result ="5F2A"  + Util.getStringHexSize(TransactionCurrencyCode.length()/2) + TransactionCurrencyCode 
				+"82"   + Util.getStringHexSize(AplicationInterchangeProfile.length()/2) + AplicationInterchangeProfile
				+"84"   + Util.getStringHexSize(DedicatedFileName.length()/2) + DedicatedFileName
				+"95"   + Util.getStringHexSize(TerminalVerificationResult.length()/2) + TerminalVerificationResult  
				+"9A"   + Util.getStringHexSize(TransactionDate.length()/2) + TransactionDate             
				+"9C"   + Util.getStringHexSize(TransactionType.length()/2) + TransactionType             
				+"9F02" + Util.getStringHexSize(TrnAmount.length()/2) + TrnAmount                   
				+"9F10" + Util.getStringHexSize(IssuerApplicationData.length()/2) + IssuerApplicationData 
				+"9F1A" + Util.getStringHexSize(TerminalCountryCode.length()/2) + TerminalCountryCode         
				+"9F27" + Util.getStringHexSize(CryptogramInformationData.length()/2) + CryptogramInformationData 
				+"9F33" + Util.getStringHexSize(TerminalCapabilities.length()/2) + TerminalCapabilities
				+"9F34" + Util.getStringHexSize(CardholderVerificationMethodResults.length()/2) + CardholderVerificationMethodResults             
		+"9F36" + Util.getStringHexSize(Atc.length()/2) + Atc                         
		+"9F37" + Util.getStringHexSize(UnpredictableNumber.length()/2) + UnpredictableNumber;         
		//+"9F03" + Util.getStringHexSize(AmountCashBack.length()/2) + AmountCashBack
		return result;
    }	 
	 public static class Builder{
		 private String TrnAmount;              
		 private String AmountCashBack;            
		 private String DedicatedFileName;
		 private String TerminalCountryCode;          
		 private String TerminalVerificationResult;  
		 private String TransactionCurrencyCode;     
		 private String TransactionDate;             
		 private String TransactionType;             
		 private String UnpredictableNumber;         
		 private String AplicationInterchangeProfile;
		 private String Atc;                         
		 private String IssuerApplicationData;       
		 private String CryptogramInformationData;
		 private String TerminalCapabilities;
		 private String CardholderVerificationMethodResults;
		 
		 public Builder withTrnAmount(String TrnAmount){
	            this.TrnAmount = TrnAmount;
	            return this;
	        }
	        public Builder withAmountCashBack(String AmountCashBack){
	            this.AmountCashBack = AmountCashBack;
	            return this;
	        }
	        public Builder withDedicatedFileName(String DedicatedFileName){
	            this.DedicatedFileName = DedicatedFileName;
	            return this;
	        }
	        public Builder withTerminalCountryCode(String TerminalCountryCode){
	            this.TerminalCountryCode = TerminalCountryCode;
	            return this;
	        }
	        public Builder withTerminalVerificationResult(String TerminalVerificationResult){
	            this.TerminalVerificationResult = TerminalVerificationResult;
	            return this;
	        }
	        public Builder withTransactionCurrencyCode(String TransactionCurrencyCode){
	            this.TransactionCurrencyCode = TransactionCurrencyCode;
	            return this;
	        }
	        public Builder withTransactionDate(String TransactionDate){
	            this.TransactionDate = TransactionDate;
	            return this;
	        }
	        public Builder withTransactionType(String TransactionType){
	            this.TransactionType = TransactionType;
	            return this;
	        }
	        public Builder withUnpredictableNumber(String UnpredictableNumber){
	            this.UnpredictableNumber = UnpredictableNumber;
	            return this;
	        }
	        public Builder withAplicationInterchangeProfile(String AplicationInterchangeProfile){
	            this.AplicationInterchangeProfile = AplicationInterchangeProfile;
	            return this;
	        }
	        public Builder withAtc(String Atc){
	            this.Atc = Atc;
	            return this;
	        }
	        public Builder withIssuerApplicationData(String IssuerApplicationData){
	            this.IssuerApplicationData = IssuerApplicationData;
	            return this;
	        }
	        public MontaDE55 build() {
	        	MontaDE55 user =  new MontaDE55(this);
	            return user;
	        }	        
			 public Builder withCryptogramInformationData(String CryptogramInformationData){
		            this.CryptogramInformationData = CryptogramInformationData;
		            return this;
			 }
			 public Builder withTerminalCapabilities(String TerminalCapabilities){
		            this.TerminalCapabilities = TerminalCapabilities;
		            return this;
			 }
			 public Builder withCardholderVerificationMethodResults(String CardholderVerificationMethodResults){
		            this.CardholderVerificationMethodResults = CardholderVerificationMethodResults;
		            return this;
			 }
	        
	 }
}
