package mg.cnaps.models;

import java.sql.Date;

public class PerOpBeneficiaire {
	private Integer opBenefId;
	
	private String opIdentifiant;
	
	private String opNumero;
	
	private String benefType;
	
	private String benefMatricule;

	private double opBenefMontant;
	
	private Date opDateInsert;
	
	private String opUserInsert;
	
	private Date opDateModif;
	
	private String opUserModif;
	
	private Long idModePaiementTiers;
	
	private String idAcc;
	
	private ReftrsmodepaieMod modePaiementTiers;
	
	public ReftrsmodepaieMod getModePaiementTiers() {
		return modePaiementTiers;
	}

	public void setModePaiementTiers(ReftrsmodepaieMod modePaiementTiers) {
		this.modePaiementTiers = modePaiementTiers;
	}

	public Long getIdModePaiementTiers() {
		return idModePaiementTiers;
	}

	public void setIdModePaiementTiers(Long idModePaiementTiers) {
		this.idModePaiementTiers = idModePaiementTiers;
	}

	public Integer getOpBenefId() {
		return opBenefId;
	}

	public void setOpBenefId(Integer opBenefId) {
		this.opBenefId = opBenefId;
	}

	public String getOpIdentifiant() {
		return opIdentifiant;
	}

	public void setOpIdentifiant(String opIdentifiant) {
		this.opIdentifiant = opIdentifiant;
	}

	public String getOpNumero() {
		return opNumero;
	}

	public void setOpNumero(String opNumero) {
		this.opNumero = opNumero;
	}

	public String getBenefType() {
		return benefType;
	}

	public void setBenefType(String benefType) {
		this.benefType = benefType;
	}

	public String getBenefMatricule() {
		return benefMatricule;
	}

	public void setBenefMatricule(String benefMatricule) {
		this.benefMatricule = benefMatricule;
	}

	public double getOpBenefMontant() {
		return opBenefMontant;
	}

	public void setOpBenefMontant(double opBenefMontant) {
		this.opBenefMontant = opBenefMontant;
	}

	public Date getOpDateInsert() {
		return opDateInsert;
	}

	public void setOpDateInsert(Date opDateInsert) {
		this.opDateInsert = opDateInsert;
	}

	public String getOpUserInsert() {
		return opUserInsert;
	}

	public void setOpUserInsert(String opUserInsert) {
		this.opUserInsert = opUserInsert;
	}

	public Date getOpDateModif() {
		return opDateModif;
	}

	public void setOpDateModif(Date opDateModif) {
		this.opDateModif = opDateModif;
	}

	public String getOpUserModif() {
		return opUserModif;
	}

	public void setOpUserModif(String opUserModif) {
		this.opUserModif = opUserModif;
	}

	public String getIdAcc() {
		return idAcc;
	}

	public void setIdAcc(String idAcc) {
		this.idAcc = idAcc;
	}
}
