package mg.cnaps.models;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "tec_op")
public class TecopMod {
	
	//D�claration 
	@Id
	@Column(name = "id_op", unique = true , nullable = false )
	private String id_op;
	
	@Column(name = "montant",nullable = true)
	private Double montant;
	
	@Column(name = "flag_valide")
	private String flag_valide;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name = "date_op")
	private Date date_op;
	
	@Column(name = "observations")
	private String observations;
	
	@Column(name = "br_code")
	private String br_code;
	
	/*@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn(name="id_op" , referencedColumnName="id_op")
	private List<TecbenefMod> tecbenef;*/
	
	@Column(name = "id_acc")
	private String id_acc;
	
	@Column(name = "code_dr_service")
	private String code_dr_service;
	
	@Column(name = "periode")
	private String periode;
	
	@Transient
	private List<TecbenefMod> tecbenef;
	
	//Get & Set
	
	public String getId_op() {
		return id_op;
	}
	
	public List<TecbenefMod> getTecbenef() {
		return tecbenef;
	}

	public void setTecbenef(List<TecbenefMod> tecbenef) {
		this.tecbenef = tecbenef;
	}

	public void setId_op( String id_op) {
		this.id_op = id_op;
	}
	
	public Double getMontant() {
		return montant;
	}
	
	public void setMontant( Double montant) {
		this.montant = montant;
	}
	
	public String getFlag_valide() {
		return flag_valide;
	}
	
	public void setFlag_valide( String flag_valide) {
		this.flag_valide = flag_valide;
	}
	
	public Date getDate_op() {
		return date_op;
	}
	
	public void setDate_op( Date date_op) {
		this.date_op = date_op;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public String getBr_code() {
		return br_code;
	}

	public void setBr_code(String br_code) {
		this.br_code = br_code;
	}

	public String getId_acc() {
		return id_acc;
	}

	public void setId_acc(String id_acc) {
		this.id_acc = id_acc;
	}

	public String getCode_dr_service() {
		return code_dr_service;
	}

	public void setCode_dr_service(String code_dr_service) {
		this.code_dr_service = code_dr_service;
	}

	public String getPeriode() {
		return periode;
	}

	public void setPeriode(String periode) {
		this.periode = periode;
	}

}
