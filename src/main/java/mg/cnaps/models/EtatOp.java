package mg.cnaps.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "etat_op")
public class EtatOp {

	//D�claration 
	@Id
	@Column(name = "id_etat_op" )
	private String idetat;
	
	@Column(name = "libelle")
	private String libelle;

	public String getIdetat() {
		return idetat;
	}

	public void setIdetat(String idetat) {
		this.idetat = idetat;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
}
