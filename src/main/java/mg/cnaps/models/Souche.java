package mg.cnaps.models;

import java.io.Serializable;
import java.sql.Date;

public class Souche implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String idSouche;//
	
	private String idTitre;
	
	private String idRecap;
	
	private String idOp;
	
	private Date dateEdition;//
	
	private Integer idModePaiement;
	
	private double montant;
	
	private String compte; //compte 5 alaina via banque
	
	private Integer signature;

	public String getIdSouche() {
		return idSouche;
	}

	public String getIdRecap() {
		return idRecap;
	}


	public void setIdRecap(String idRecap) {
		this.idRecap = idRecap;
	}


	public void setIdSouche(String idSouche) {
		this.idSouche = idSouche;
	}

	public String getIdTitre() {
		return idTitre;
	}

	public void setIdTitre(String idTitre) {
		this.idTitre = idTitre;
	}

	public String getIdOp() {
		return idOp;
	}

	public void setIdOp(String idOp) {
		this.idOp = idOp;
	}

	public Date getDateEdition() {
		return dateEdition;
	}

	public void setDateEdition(Date dateEdition) {
		this.dateEdition = dateEdition;
	}

	public Integer getIdModePaiement() {
		return idModePaiement;
	}

	public void setIdModePaiement(Integer idModePaiement) {
		this.idModePaiement = idModePaiement;
	}

	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public String getCompte() {
		return compte;
	}

	public void setCompte(String compte) {
		this.compte = compte;
	}

	public Integer getSignature() {
		return signature;
	}

	public void setSignature(Integer signature) {
		this.signature = signature;
	}
	
	
}
