package mg.cnaps.util;

import java.text.DecimalFormat;
import java.util.Calendar;

public class Test {
	public static double roundDown(double number, double place) {
		double result = number / place;
		result = Math.floor(result);
		result *= place;
		return result;
	}
	public static double roundup(double number, double place) {
		double result = number / place;
		result = Math.ceil(result);
		result *= place;
		return result;
	}
	public static void main(String[] args) {
		String numOp = "11211631523q1sd2q1";
		double montant = 20000;
		System.out.println("{\"numOp\":\""+numOp+"\",\"montant\":"+montant+"}");
		System.out.println(Integer.MAX_VALUE);
		Double d=851600.61;
		
		//String dstr=new DecimalFormat("#").format(d);
		String dstr=String.valueOf(d).split("\\.")[0];
		int taille=dstr.length();
		if(dstr.equals(d))
		{
			dstr=String.valueOf(d).split("\\,")[0];
		}
		if(dstr.substring(taille-2, taille).equalsIgnoreCase("00") || dstr.substring(taille-2, taille).equalsIgnoreCase("01"))
		{
			System.out.println("valeur"+dstr.substring(taille-2, taille));
			System.out.println("valeur"+d);
		}
		else
		{
			System.out.println("valeur"+dstr);
			System.out.println("arrondissement"+Test.roundup(d, 100));
		}
		
		System.out.println("dint"+new DecimalFormat("#").format(d));
		System.out.println("dstr"+new java.sql.Date(Calendar.getInstance().getTime().getTime()));
		System.out.println("taille"+taille);
		System.out.println("substring"+(dstr.substring(taille-2, taille)));
		double arr=100;
		String prestation="210";
		Double roundup=roundup(d, arr);
		Double rounddown=roundDown(d, 200000);
		System.out.println("arrondissement up"+roundup);
		System.out.println("arrondissement down"+rounddown.longValue());
		System.out.println("prestation"+prestation.substring(0, 1));
	}
}
