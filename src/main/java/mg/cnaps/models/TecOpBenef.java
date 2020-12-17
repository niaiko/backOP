package mg.cnaps.models;

public class TecOpBenef {

	private TecopMod tecop;
	
	private int page;

	private int size;

	private int nbPage;

	public TecopMod getTecop() {
		return tecop;
	}

	public void setTecop(TecopMod tecop) {
		this.tecop = tecop;
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
