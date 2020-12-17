package mg.cnaps.models;

import java.util.List;

public class ParamSuppOp {

	private List<TecOpBenef> tecBenef;
	
	private int page;

	private int size;

	private int nbPage;

	public List<TecOpBenef> getTecBenef() {
		return tecBenef;
	}

	public void setTecBenef(List<TecOpBenef> tecBenef) {
		this.tecBenef = tecBenef;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getNbPage() {
		return nbPage;
	}

	public void setNbPage(int nbPage) {
		this.nbPage = nbPage;
	} 
}
