package mg.cnaps.models;

public class Tef {
	private String numTef;
	private String numOp;
	private Double montant;
	private String codeProjet;
	private String codeService;
	private String idRubrique;
	private String compteBenef;
	private String objet;
	
	public String getObjet() {
		return objet;
	}

	public void setObjet(String objet) {
		this.objet = objet;
	}

	public String getCompteBenef() {
		return compteBenef;
	}

	public void setCompteBenef(String compteBenef) {
		this.compteBenef = compteBenef;
	}

	public String getCodeProjet() {
		return codeProjet;
	}

	public void setCodeProjet(String codeProjet) {
		this.codeProjet = codeProjet;
	}

	public String getCodeService() {
		return codeService;
	}

	public void setCodeService(String codeService) {
		this.codeService = codeService;
	}

	public String getIdRubrique() {
		return idRubrique;
	}

	public void setIdRubrique(String idRubrique) {
		this.idRubrique = idRubrique;
	}

	public String getNumTef() {
		return numTef;
	}

	public void setNumTef(String numTef) {
		this.numTef = numTef;
	}

	public String getNumOp() {
		return numOp;
	}

	public void setNumOp(String numOp) {
		this.numOp = numOp;
	}

	public Double getMontant() {
		return montant;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
	}
}
