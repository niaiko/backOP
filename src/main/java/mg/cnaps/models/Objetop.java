package mg.cnaps.models;

import java.util.Date;
import java.util.List;

public class Objetop {

	private String prestation;
	private String flagvalide;
	private Date op;
	private Integer size;
	private Integer page;
	private List<TecopMod> l;
	private Integer npagetotal;
	public String getPrestation() {
		return prestation;
	}
	public void setPrestation(String prestation) {
		this.prestation = prestation;
	}
	public String getFlagvalide() {
		return flagvalide;
	}
	public void setFlagvalide(String flagvalide) {
		this.flagvalide = flagvalide;
	}
	public Date getOp() {
		return op;
	}
	public void setOp(Date op) {
		this.op = op;
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
	public List<TecopMod> getL() {
		return l;
	}
	public void setL(List<TecopMod> l) {
		this.l = l;
	}
	public Integer getNpagetotal() {
		return npagetotal;
	}
	public void setNpagetotal(Integer npagetotal) {
		this.npagetotal = npagetotal;
	}
}
