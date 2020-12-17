package mg.cnaps.models;

import java.io.Serializable;

public class ParamTitreSouche implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Souche souche;
	private String banque;
	public Souche getSouche() {
		return souche;
	}
	public void setSouche(Souche souche) {
		this.souche = souche;
	}
	public String getBanque() {
		return banque;
	}
	public void setBanque(String banque) {
		this.banque = banque;
	}
	
	
	
}
