package mg.cnaps.services;

import java.sql.Date;

import mg.cnaps.models.ReftrsmodepaieMod;

public interface ReftrsmodepaieService extends CRUDService<ReftrsmodepaieMod> {
	ReftrsmodepaieMod findmodepaieidAcc (String id_acc);
	
	ReftrsmodepaieMod findByIdTiersAndIdAcc(String idtiers, String idacc);
	
	ReftrsmodepaieMod findByIdAccAndIdModePaiementTiers(String idAcc, Long idModePaie);
	void updatereftrsmodepaie(String nomInstitutionSortie,String imputation,Integer idinstitution,String numcompteinstitution,String idacc,Long idpaiementtiers);
	void updatereftrsmodepaiepen(String compte,String cle,Date dateDebut,Date dateFin,String codeSwift,String domiciliation,String idTiers,String defaut,Integer idAgence,Integer idModePaiement,String abrevModePaiement,String caisse,String numero,String codeBanque,String codeAgence,String libelleAgence,String libelleBanque,String abbrevBanque,String idbenef,String agenceotiv,String idacc,Long idpaiementtiers);
	void updatemodepaiement(String compte,String cle,Date dateDebut,Date dateFin,String codeSwift,String domiciliation,String idTiers,String defaut,Integer idAgence,Integer idModePaiement,String abrevModePaiement,String caisse,String numero,String codeBanque,String codeAgence,String libelleAgence,String libelleBanque,String abbrevBanque,String idbenef,String agenceotiv,Long idpaiementtiers,String nominstitution,String imputation, Integer institution,String numinstitution,String codedr,String iddmddepense,String codeagenceotiv,String idacc);
}
