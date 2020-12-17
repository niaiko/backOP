package mg.cnaps.models;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Historiquedroit {

	private String idHistoDroit;
	private String idDemande;
    private String matriculeBenef;
    private Double montant;
    private String type;
    private String periode;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date datePaiement;
    
    private String libelleType;
	public String getIdHistoDroit() {
		return idHistoDroit;
	}
	public void setIdHistoDroit(String idHistoDroit) {
		this.idHistoDroit = idHistoDroit;
	}
	public String getIdDemande() {
		return idDemande;
	}
	public void setIdDemande(String idDemande) {
		this.idDemande = idDemande;
	}
	public String getMatriculeBenef() {
		return matriculeBenef;
	}
	public void setMatriculeBenef(String matriculeBenef) {
		this.matriculeBenef = matriculeBenef;
	}
	public Double getMontant() {
		return montant;
	}
	public void setMontant(Double montant) {
		this.montant = montant;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPeriode() {
		return periode;
	}
	public void setPeriode(String periode) {
		this.periode = periode;
	}
	public Date getDatePaiement() {
		return datePaiement;
	}
	public void setDatePaiement(Date datePaiement) {
		this.datePaiement = datePaiement;
	}
	public String getLibelleType() {
		return libelleType;
	}
	public void setLibelleType(String libelleType) {
		this.libelleType = libelleType;
	}
}
