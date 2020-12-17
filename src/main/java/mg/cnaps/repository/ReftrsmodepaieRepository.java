package mg.cnaps.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import mg.cnaps.models.ModePaiePK;
import mg.cnaps.models.ReftrsmodepaieMod;

public interface ReftrsmodepaieRepository extends JpaRepository<ReftrsmodepaieMod,ModePaiePK> {
	
	@Query ("select u from ReftrsmodepaieMod u where u.idAcc = ?1")
	ReftrsmodepaieMod findmodepaieidAcc (String id_acc);
	
	ReftrsmodepaieMod findByIdTiersAndIdAcc(String idtiers, String idacc);

	ReftrsmodepaieMod findByIdAccAndIdModePaiementTiers(String idAcc, Long idModePaie);
	
	@Query(nativeQuery=true,value="SELECT * FROM \"RFM\".mode_paiement_tiers where id_mode_paiement_tiers=?1 and substring(id_acc,1,3)=?2")
	List<ReftrsmodepaieMod> getlistemodepaiement(Long idmdptiers, String prestation);
	
	@Transactional
	@Modifying
	@Query("update ReftrsmodepaieMod s set s.nomInstitutionSortie=?1,s.imputation=?2,s.idInstitution=?3,s.numCompteInstitution=?4 where s.idAcc = ?5 and s.idModePaiementTiers=?6")
	void updatereftrsmodepaie(String nomInstitutionSortie,String imputation,Integer idinstitution,String numcompteinstitution,String idacc,Long idpaiementtiers);
	
	@Transactional
	@Modifying
	@Query("update ReftrsmodepaieMod s set s.compte=?1,s.cle=?2,s.dateDebut=?3,s.dateFin=?4,s.codeSwift=?5,s.domiciliation=?6,s.idTiers=?7,s.defaut=?8,s.idAgence=?9,s.idModePaiement=?10,s.abrevModePaiement=?11,s.caisse=?12,s.numero=?13,s.codeBanque=?14,s.codeAgence=?15,s.libelleAgence=?16,s.libelleBanque=?17,s.abbrevBanque=?18,s.idbenef=?19,s.agenceotiv=?20 where s.idAcc = ?21 and s.idModePaiementTiers=?22")
	void updatereftrsmodepaiepen(String compte,String cle,Date dateDebut,Date dateFin,String codeSwift,String domiciliation,String idTiers,String defaut,Integer idAgence,Integer idModePaiement,String abrevModePaiement,String caisse,String numero,String codeBanque,String codeAgence,String libelleAgence,String libelleBanque,String abbrevBanque,String idbenef,String agenceotiv,String idacc,Long idpaiementtiers);
	
	@Transactional
	@Modifying
	@Query("update ReftrsmodepaieMod s set s.compte=?1,s.cle=?2,s.dateDebut=?3,s.dateFin=?4,s.codeSwift=?5,s.domiciliation=?6,s.idTiers=?7,s.defaut=?8,s.idAgence=?9,s.idModePaiement=?10,s.abrevModePaiement=?11,s.caisse=?12,s.numero=?13,s.codeBanque=?14,s.codeAgence=?15,s.libelleAgence=?16,s.libelleBanque=?17,s.abbrevBanque=?18,s.idbenef=?19,s.agenceotiv=?20,s.idModePaiementTiers=?21,s.nomInstitutionSortie=?22,s.imputation=?23,s.idInstitution=?24,s.numCompteInstitution=?25,s.codeDr=?26,s.idDmdDepense=?27,s.codeagenceotiv=?28 where s.idAcc = ?29")
	void updatemodepaiement(String compte,String cle,Date dateDebut,Date dateFin,String codeSwift,String domiciliation,String idTiers,String defaut,Integer idAgence,Integer idModePaiement,String abrevModePaiement,String caisse,String numero,String codeBanque,String codeAgence,String libelleAgence,String libelleBanque,String abbrevBanque,String idbenef,String agenceotiv,Long idpaiementtiers,String nominstitution,String imputation, Integer institution,String numinstitution,String codedr,String iddmddepense,String codeagenceotiv,String idacc);

}
