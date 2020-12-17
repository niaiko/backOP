package mg.cnaps.services;

import java.util.List;

import org.springframework.data.domain.Page;

import mg.cnaps.models.ObjetRechercheSize;
import mg.cnaps.models.TecOpTempMod;

public interface TecOpTempService extends CRUDService<TecOpTempMod> {

	ObjetRechercheSize findListetecoptemp(ObjetRechercheSize obj);
	
	Page<TecOpTempMod> getallbytecoptemp (String prestation,String dr,int page, int size);
	
	Page<TecOpTempMod> getallbytecoptempsup(String prestation,String dr, int page, int size);
	
	Double getSommeTecOpByPrestation(String prestation,String dr);
	
	List<TecOpTempMod> getallbytecoptemplist (String prestation);
}
