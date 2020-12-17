package mg.cnaps.models;

import java.util.List;

public class ObjetBenefprestation {
	private String prestation;
	private String matricule;
	private Integer size;
	private Integer page;
	private List<Tecbenefdateop> l;
	private Integer npagetotal;
	
	public String getPrestation() {
		return prestation;
	}
	public void setPrestation(String prestation) {
		this.prestation = prestation;
	}
	public String getMatricule() {
		return matricule;
	}
	public void setMatricule(String matricule) {
		this.matricule = matricule;
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
	public List<Tecbenefdateop> getL() {
		return l;
	}
	public void setL(List<Tecbenefdateop> l) {
		this.l = l;
	}
	public Integer getNpagetotal() {
		return npagetotal;
	}
	public void setNpagetotal(Integer npagetotal) {
		this.npagetotal = npagetotal;
	}
}
