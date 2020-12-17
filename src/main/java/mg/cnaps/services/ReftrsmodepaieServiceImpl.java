package mg.cnaps.services;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mg.cnaps.models.ModePaiePK;
import mg.cnaps.models.ReftrsmodepaieMod;
import mg.cnaps.repository.ReftrsmodepaieRepository;

@Service
public class ReftrsmodepaieServiceImpl implements  ReftrsmodepaieService{

	@Autowired
	private ReftrsmodepaieRepository reftrsmodepaieRepository;

	@Override
	public ReftrsmodepaieMod save(ReftrsmodepaieMod entity) {
		return reftrsmodepaieRepository.save(entity);
	}

	@Override
	public ReftrsmodepaieMod getById(Serializable id) {
		Optional<ReftrsmodepaieMod> opt = reftrsmodepaieRepository.findById((ModePaiePK) id);
		if(opt.isPresent())
			return opt.get();
		return null;
	}

	@Override
	public List<ReftrsmodepaieMod> getAll() {
		return reftrsmodepaieRepository.findAll();
	}

	@Override
	public void delete(Serializable id) {
		reftrsmodepaieRepository.deleteById((ModePaiePK) id);
	}

	@Override
	public List<ReftrsmodepaieMod> save(List<ReftrsmodepaieMod> entity) {
		return null;
	}

	@Override
	public ReftrsmodepaieMod findmodepaieidAcc(String id_acc) {
		return reftrsmodepaieRepository.findmodepaieidAcc(id_acc);
	}

	@Override
	public ReftrsmodepaieMod findByIdAccAndIdModePaiementTiers(String idAcc, Long idModePaie) {
		return reftrsmodepaieRepository.findByIdAccAndIdModePaiementTiers(idAcc, idModePaie);
	}

	@Override
	public void updatereftrsmodepaie(String nomInstitutionSortie, String imputation, Integer idinstitution,String numcompteinstitution, String idacc, Long idpaiementtiers) {
		reftrsmodepaieRepository.updatereftrsmodepaie(nomInstitutionSortie, imputation, idinstitution, numcompteinstitution, idacc, idpaiementtiers);
		
	}

	@Override
	public ReftrsmodepaieMod findByIdTiersAndIdAcc(String idtiers, String idacc) {
		return reftrsmodepaieRepository.findByIdTiersAndIdAcc(idtiers, idacc);
	}

	@Override
	public void updatereftrsmodepaiepen(String compte, String cle, Date dateDebut, Date dateFin, String codeSwift,
			String domiciliation, String idTiers, String defaut, Integer idAgence, Integer idModePaiement,
			String abrevModePaiement, String caisse, String numero, String codeBanque, String codeAgence,
			String libelleAgence, String libelleBanque, String abbrevBanque, String idbenef, String agenceotiv,
			String idacc, Long idpaiementtiers) {
		reftrsmodepaieRepository.updatereftrsmodepaiepen(compte, cle, dateDebut, dateFin, codeSwift, domiciliation, idTiers, defaut, idAgence, idModePaiement, abrevModePaiement, caisse, numero, codeBanque, codeAgence, libelleAgence, libelleBanque, abbrevBanque, idbenef, agenceotiv, idacc, idpaiementtiers);
		
	}

	@Override
	public void updatemodepaiement(String compte, String cle, Date dateDebut, Date dateFin, String codeSwift,
			String domiciliation, String idTiers, String defaut, Integer idAgence, Integer idModePaiement,
			String abrevModePaiement, String caisse, String numero, String codeBanque, String codeAgence,
			String libelleAgence, String libelleBanque, String abbrevBanque, String idbenef, String agenceotiv,
			Long idpaiementtiers, String nominstitution, String imputation, Integer institution, String numinstitution,
			String codedr, String iddmddepense, String codeagenceotiv, String idacc) {
		reftrsmodepaieRepository.updatemodepaiement(compte, cle, dateDebut, dateFin, codeSwift, domiciliation, idTiers, defaut, idAgence, idModePaiement, abrevModePaiement, caisse, numero, codeBanque, codeAgence, libelleAgence, libelleBanque, abbrevBanque, idbenef, agenceotiv, idpaiementtiers, nominstitution, imputation, institution, numinstitution, codedr, iddmddepense, codeagenceotiv, idacc);
	}

}
