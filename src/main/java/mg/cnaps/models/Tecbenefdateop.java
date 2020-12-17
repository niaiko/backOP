package mg.cnaps.models;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Tecbenefdateop {

	//Declaration 
	private String id_op;
	private ReftrsmodepaieMod id_mode_paiement;
	private String id_adresse;
	private String id_compte;
	private String id_individu;
	private String id_sucursale;
	private String id_empl;
	private String id_benef;
	private Double montant;
	private String id_demande;
	private String flag_valide;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date date_op;
	
	public String getId_op() {
		return id_op;
	}
	public void setId_op(String id_op) {
		this.id_op = id_op;
	}
	public ReftrsmodepaieMod getId_mode_paiement() {
		return id_mode_paiement;
	}
	public void setId_mode_paiement(ReftrsmodepaieMod id_mode_paiement) {
		this.id_mode_paiement = id_mode_paiement;
	}
	public String getId_adresse() {
		return id_adresse;
	}
	public void setId_adresse(String id_adresse) {
		this.id_adresse = id_adresse;
	}
	public String getId_compte() {
		return id_compte;
	}
	public void setId_compte(String id_compte) {
		this.id_compte = id_compte;
	}
	public String getId_individu() {
		return id_individu;
	}
	public void setId_individu(String id_individu) {
		this.id_individu = id_individu;
	}
	public String getId_sucursale() {
		return id_sucursale;
	}
	public void setId_sucursale(String id_sucursale) {
		this.id_sucursale = id_sucursale;
	}
	public String getId_empl() {
		return id_empl;
	}
	public void setId_empl(String id_empl) {
		this.id_empl = id_empl;
	}
	public String getId_benef() {
		return id_benef;
	}
	public void setId_benef(String id_benef) {
		this.id_benef = id_benef;
	}
	public Double getMontant() {
		return montant;
	}
	public void setMontant(Double montant) {
		this.montant = montant;
	}
	public String getId_demande() {
		return id_demande;
	}
	public void setId_demande(String id_demande) {
		this.id_demande = id_demande;
	}
	public String getFlag_valide() {
		return flag_valide;
	}
	public void setFlag_valide(String flag_valide) {
		this.flag_valide = flag_valide;
	}
	public Date getDate_op() {
		return date_op;
	}
	public void setDate_op(Date date_op) {
		this.date_op = date_op;
	}
}
