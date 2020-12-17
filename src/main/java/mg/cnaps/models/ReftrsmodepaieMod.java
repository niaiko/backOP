package mg.cnaps.models;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@IdClass(ModePaiePK.class)
@Table(name = "mode_paiement_tiers")
public class ReftrsmodepaieMod {

	// compte bancaire OTIV
	// D�claration
	@Id
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mode_paiement_tiers_seq")
//	@GenericGenerator(name = "mode_paiement_tiers_seq", strategy = "mg.cnaps.util.IdGenerator", parameters = {
//			@org.hibernate.annotations.Parameter(name = "sequence_name", value = "mode_paiement_tiers_seq") })
	@Column(name = "id_mode_paiement_tiers", unique = true, nullable = false)
	private Long idModePaiementTiers;

	@Column(name = "compte")
	private String compte;

	@Column(name = "cle")
	private String cle;

	@Column(name = "date_debut")
	private Date dateDebut;

	@Column(name = "date_fin")
	private Date dateFin;

	@Column(name = "code_swift")
	private String codeSwift;

	@Column(name = "domiciliation")
	private String domiciliation;

	@Column(name = "id_tiers")
	private String idTiers;

	@Column(name = "defaut")
	private String defaut;

	@Column(name = "id_agence")
	private Integer idAgence;

	@Column(name = "id_mode_paiement")
	private Integer idModePaiement;

	@Column(name = "abrev_mode_paiement")
	private String abrevModePaiement;

	@Column(name = "caisse")
	private String caisse;

	@Column(name = "numero")
	private String numero;

	@Column(name = "code_banque")
	private String codeBanque;

	@Id
	@Column(name = "id_acc")
	private String idAcc;

	@Column(name = "code_agence")
	private String codeAgence;

	@Column(name = "libelle_agence")
	private String libelleAgence;

	@Column(name = "libelle_banque")
	private String libelleBanque;

	@Column(name = "abbrev_banque")
	private String abbrevBanque;

	// compte à débiter
	@Column(name = "nom_institution_sortie")
	private String nomInstitutionSortie;

	@Column(name = "imputation")
	private String imputation;

	@Column(name = "id_institution")
	private Integer idInstitution;

	@Column(name = "num_compte_institution")
	private String numCompteInstitution;

	@Column(name = "code_dr")
	private String codeDr;

	@Column(name = "id_dmd_depense")
	private String idDmdDepense;

	// info beneficiaire agence caisse otiv

	@Column(name = "id_beneficiaire")
	private String idbenef;

	@Column(name = "libelle_agence_benef")
	private String agenceotiv;

	@Column(name = "codeagenceotiv")
	private String codeagenceotiv;

	public Long getIdModePaiementTiers() {
		return idModePaiementTiers;
	}

	public void setIdModePaiementTiers(Long idModePaiementTiers) {
		this.idModePaiementTiers = idModePaiementTiers;
	}

	public String getCompte() {
		return compte;
	}

	public void setCompte(String compte) {
		this.compte = compte;
	}

	public String getCle() {
		return cle;
	}

	public void setCle(String cle) {
		this.cle = cle;
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public String getCodeSwift() {
		return codeSwift;
	}

	public void setCodeSwift(String codeSwift) {
		this.codeSwift = codeSwift;
	}

	public String getDomiciliation() {
		return domiciliation;
	}

	public void setDomiciliation(String domiciliation) {
		this.domiciliation = domiciliation;
	}

	public String getIdTiers() {
		return idTiers;
	}

	public void setIdTiers(String idTiers) {
		this.idTiers = idTiers;
	}

	public String getDefaut() {
		return defaut;
	}

	public void setDefaut(String defaut) {
		this.defaut = defaut;
	}

	public Integer getIdAgence() {
		return idAgence;
	}

	public void setIdAgence(Integer idAgence) {
		this.idAgence = idAgence;
	}

	public Integer getIdModePaiement() {
		return idModePaiement;
	}

	public void setIdModePaiement(Integer idModePaiement) {
		this.idModePaiement = idModePaiement;
	}

	public String getCaisse() {
		return caisse;
	}

	public void setCaisse(String caisse) {
		this.caisse = caisse;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getCodeBanque() {
		return codeBanque;
	}

	public void setCodeBanque(String codeBanque) {
		this.codeBanque = codeBanque;
	}

	public String getIdAcc() {
		return idAcc;
	}

	public void setIdAcc(String idAcc) {
		this.idAcc = idAcc;
	}

	public String getCodeAgence() {
		return codeAgence;
	}

	public void setCodeAgence(String codeAgence) {
		this.codeAgence = codeAgence;
	}

	public String getLibelleAgence() {
		return libelleAgence;
	}

	public void setLibelleAgence(String libelleAgence) {
		this.libelleAgence = libelleAgence;
	}

	public String getLibelleBanque() {
		return libelleBanque;
	}

	public void setLibelleBanque(String libelleBanque) {
		this.libelleBanque = libelleBanque;
	}

	public String getAbbrevBanque() {
		return abbrevBanque;
	}

	public void setAbbrevBanque(String abbrevBanque) {
		this.abbrevBanque = abbrevBanque;
	}

	public String getNomInstitutionSortie() {
		return nomInstitutionSortie;
	}

	public void setNomInstitutionSortie(String nomInstitutionSortie) {
		this.nomInstitutionSortie = nomInstitutionSortie;
	}

	public String getImputation() {
		return imputation;
	}

	public void setImputation(String imputation) {
		this.imputation = imputation;
	}

	public Integer getIdInstitution() {
		return idInstitution;
	}

	public void setIdInstitution(Integer idInstitution) {
		this.idInstitution = idInstitution;
	}

	public String getNumCompteInstitution() {
		return numCompteInstitution;
	}

	public void setNumCompteInstitution(String numCompteInstitution) {
		this.numCompteInstitution = numCompteInstitution;
	}

	public String getCodeDr() {
		return codeDr;
	}

	public void setCodeDr(String codeDr) {
		this.codeDr = codeDr;
	}

	public String getAbrevModePaiement() {
		return abrevModePaiement;
	}

	public void setAbrevModePaiement(String abrevModePaiement) {
		this.abrevModePaiement = abrevModePaiement;
	}

	public String getIdDmdDepense() {
		return idDmdDepense;
	}

	public void setIdDmdDepense(String idDmdDepense) {
		this.idDmdDepense = idDmdDepense;
	}

	public String getIdbenef() {
		return idbenef;
	}

	public void setIdbenef(String idbenef) {
		this.idbenef = idbenef;
	}

	public String getAgenceotiv() {
		return agenceotiv;
	}

	public void setAgenceotiv(String agenceotiv) {
		this.agenceotiv = agenceotiv;
	}

	public String getCodeagenceotiv() {
		return codeagenceotiv;
	}

	public void setCodeagenceotiv(String codeagenceotiv) {
		this.codeagenceotiv = codeagenceotiv;

	}

}
