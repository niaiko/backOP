package mg.cnaps.models;

import java.util.List;

public class ObjetBenef {
	private Integer size;
	private Integer page;
	private List<TecbenefMod> l;
	private Integer npagetotal;
	
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
	public List<TecbenefMod> getL() {
		return l;
	}
	public void setL(List<TecbenefMod> l) {
		this.l = l;
	}
	public Integer getNpagetotal() {
		return npagetotal;
	}
	public void setNpagetotal(Integer npagetotal) {
		this.npagetotal = npagetotal;
	}
}
