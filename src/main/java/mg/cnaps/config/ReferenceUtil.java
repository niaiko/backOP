package mg.cnaps.config;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ReferenceUtil {
	
	
	
	public  static String convertDateToString(Date daty) {
		DateFormat df = new SimpleDateFormat("yyMMdd");
		return df.format(daty);
	}
	
	public  static String convertDateToStringperiode(Date daty) {
		DateFormat df = new SimpleDateFormat("yyyyMM");
		return df.format(daty);
	}
	
	public static  String getReferenceDemande(String prestation,long sequence,String dr){
		Date daty = new Date();
		String datyy = convertDateToString(daty);
		return prestation+datyy+String.format("%04d",sequence )+String.format("%04d",Integer.parseInt(dr));
	}
	
	
	public static String getDateNow(){
		DateFormat df = new SimpleDateFormat("dd/MM/yy");
		Date daty = new Date();
		
		return df.format(daty);
	}
	
	
	
}
