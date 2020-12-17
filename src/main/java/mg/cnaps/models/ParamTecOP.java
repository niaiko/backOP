package mg.cnaps.models;

public class ParamTecOP {

	private TecopMod tecop;
	private String prestation;
	private String dr;
	private Tef tef;
	
	
	
	public ParamTecOP(TecopMod tecop, String prestation, String dr,Tef tef) {
		super();
		this.tecop = tecop;
		this.prestation = prestation;
		this.dr = dr;
		this.tef=tef;
	}
	
	
	
	public ParamTecOP() {
		super();
		// TODO Auto-generated constructor stub
	}



	public TecopMod getTecop() {
		return tecop;
	}
	public void setTecop(TecopMod tecop) {
		this.tecop = tecop;
	}
	public String getPrestation() {
		return prestation;
	}
	public void setPrestation(String prestation) {
		this.prestation = prestation;
	}
	public String getDr() {
		return dr;
	}
	public void setDr(String dr) {
		this.dr = dr;
	}
	public Tef getTef() {
		return tef;
	}
	public void setTef(Tef tef) {
		this.tef = tef;

	}
	
	
}
