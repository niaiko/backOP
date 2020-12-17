package mg.cnaps.models;

public class ListTecOPByFlagValideMod {
	
	private int page;
	private String flagValide;
	
	
	public ListTecOPByFlagValideMod(int page, String flagValide) {
		super();
		this.page = page;
		this.flagValide = flagValide;
	}
	
	public ListTecOPByFlagValideMod() {
		super();
	}

	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public String getFlagValide() {
		return flagValide;
	}
	public void setFlagValide(String flagValide) {
		this.flagValide = flagValide;
	}
}
