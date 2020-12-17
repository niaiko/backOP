package mg.cnaps.models;

import java.util.List;

public class ObjetRechercheSize {
	
	private String prestation;
	private String dr;
	private Integer size;
	private Integer page;
	private List<TecOpTempMod> l;
	private Integer npagetotal;
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
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public List<TecOpTempMod> getL() {
		return l;
	}
	public void setL(List<TecOpTempMod> l) {
		this.l = l;
	}
	public Integer getNpagetotal() {
		return npagetotal;
	}
	public void setNpagetotal(Integer npagetotal) {
		this.npagetotal = npagetotal;
	}
}
