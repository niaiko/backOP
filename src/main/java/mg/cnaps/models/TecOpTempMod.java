package mg.cnaps.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tec_op_temp")
public class TecOpTempMod {

	//D�claration 
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tec_op_temp_seq")
    @GenericGenerator(
            name = "tec_op_temp_seq",
            strategy = "mg.cnaps.models.IdIntegerGenerator",
            parameters = {
                @org.hibernate.annotations.Parameter(name = "sequence_name", value = "tec_op_temp_seq")
            }
        )
	@Column(name = "id_tec_temp")
	private Integer id_tec_temp;
	
	@Column(name = "id_prestation")
	private String id_prestation;
	
//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumns({
//        @JoinColumn(name = "id_mode_paiement", referencedColumnName = "id_mode_paiement_tiers",insertable=false, updatable=false),
//        @JoinColumn(name = "id_demande", referencedColumnName = "id_acc",insertable=false, updatable=false)
//	})
//	private ReftrsmodepaieMod mode_paiement;
	
	@Column(name = "id_adresse")
	private String id_adresse;
	
	@Column(name = "id_compte")
	private String id_compte;
	
	@Column(name = "id_individu")
	private String id_individu;
	
	@Column(name = "id_sucursale")
	private String id_sucursale;
	
	@Column(name = "id_empl")
	private String id_empl;
	
	@Column(name = "id_benef")
	private String id_benef;
	
	@Column(name = "montant")
	private Double montant;
	
	@Column(name = "flag_op")
	private String flag_op;
	
	@Column(name = "id_demande")
	private String id_demande;
	
	@Column(name = "id_mode_paiement")
	private Integer id_mode_paiement;

	public Integer getId_tec_temp() {
		return id_tec_temp;
	}

	public void setId_tec_temp(Integer id_tec_temp) {
		this.id_tec_temp = id_tec_temp;
	}

	public String getId_prestation() {
		return id_prestation;
	}

	public void setId_prestation(String id_prestation) {
		this.id_prestation = id_prestation;
	}

	public Integer getId_mode_paiement() {
		return id_mode_paiement;
	}

	public void setId_mode_paiement(Integer id_mode_paiement) {
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

	public String getFlag_op() {
		return flag_op;
	}

	public void setFlag_op(String flag_op) {
		this.flag_op = flag_op;
	}

	public String getId_demande() {
		return id_demande;
	}

	public void setId_demande(String id_demande) {
		this.id_demande = id_demande;
	}
}
