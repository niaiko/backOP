package mg.cnaps.models;

public class CaisseMod {

	private String type;
	private String beneficiaire;
	private String codeDr;
	private String numcaisse;
	private String numop;
	private Double montant;
	private String iddemande;
	private String observations;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBeneficiaire() {
		return beneficiaire;
	}
	public void setBeneficiaire(String beneficiaire) {
		this.beneficiaire = beneficiaire;
	}
	public String getCodeDr() {
		return codeDr;
	}
	public void setCodeDr(String codeDr) {
		this.codeDr = codeDr;
	}
	public String getNumcaisse() {
		return numcaisse;
	}
	public void setNumcaisse(String numcaisse) {
		this.numcaisse = numcaisse;
	}
	public String getNumop() {
		return numop;
	}
	public void setNumop(String numop) {
		this.numop = numop;
	}
	public Double getMontant() {
		return montant;
	}
	public void setMontant(Double montant) {
		this.montant = montant;
	}
	public String getIddemande() {
		return iddemande;
	}
	public void setIddemande(String iddemande) {
		this.iddemande = iddemande;
	}
	public String getObservations() {
		return observations;
	}
	public void setObservations(String observations) {
		this.observations = observations;
	}
}
