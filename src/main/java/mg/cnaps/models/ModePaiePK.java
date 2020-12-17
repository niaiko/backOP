package mg.cnaps.models;

import java.io.Serializable;

public class ModePaiePK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idModePaiementTiers;
	private String idAcc;
	public Long getIdModePaiementTiers() {
		return idModePaiementTiers;
	}
	public void setIdModePaiementTiers(Long idModePaiementTiers) {
		this.idModePaiementTiers = idModePaiementTiers;
	}
	public String getIdAcc() {
		return idAcc;
	}
	public void setIdAcc(String idAcc) {
		this.idAcc = idAcc;
	}
	public ModePaiePK(Long idModePaiementTiers, String idAcc) {
		super();
		this.idModePaiementTiers = idModePaiementTiers;
		this.idAcc = idAcc;
	}
	public ModePaiePK() {
		super();
		// TODO Auto-generated constructor stub
	}
}
