package mg.cnaps.services;

import java.util.List;

import mg.cnaps.models.ObjetBenefprestation;
import mg.cnaps.models.TecbenefMod;

public interface TecbenefService extends CRUDService<TecbenefMod> {
	List<TecbenefMod> selectTecbenefId(String id_op);
	List<TecbenefMod> findListetecbenef();
	
	void updateflagbenef (String flag, String op,String benef);
	void updateflagindiv (String flag, String op,String benef);
	void updateflagempl (String flag, String op,String benef);
	ObjetBenefprestation gethistoriquepaiement(ObjetBenefprestation obj);
	List<TecbenefMod> gethistoriquepaiementpension(String matricule);
}
