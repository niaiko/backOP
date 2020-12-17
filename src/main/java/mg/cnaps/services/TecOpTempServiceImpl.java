package mg.cnaps.services;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import mg.cnaps.models.ObjetRechercheSize;
import mg.cnaps.models.TecOpTempMod;
import mg.cnaps.repository.TecOpTempRepository;

@Service
public class TecOpTempServiceImpl implements  TecOpTempService{

	@Autowired
	private TecOpTempRepository repository;
	
	@Override
	public TecOpTempMod save(TecOpTempMod entity) {
		return repository.save(entity);
	}

	@Override
	public List<TecOpTempMod> save(List<TecOpTempMod> entity) {
		// TODO Auto-generated method stub
		return repository.saveAll(entity);
	}

	@Override
	public TecOpTempMod getById(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TecOpTempMod> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Serializable id) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public ObjetRechercheSize findListetecoptemp(ObjetRechercheSize obj)
	{
		Page<TecOpTempMod> p=(repository.getallbytecoptemp(obj.getPrestation(),obj.getDr(),PageRequest.of(obj.getPage()-1,obj.getSize())));
		obj.setL(p.getContent());
		obj.setNpagetotal(p.getTotalPages());
		return obj;
	}

	@Override
	public Page<TecOpTempMod> getallbytecoptemp(String prestation,String dr, int page, int size) {
		return repository.getallbytecoptemp(prestation,dr, PageRequest.of(page - 1, size));
	}
	
	@Override
	public Page<TecOpTempMod> getallbytecoptempsup(String prestation,String dr, int page, int size) {
		return repository.getallbytecoptempsup(prestation,dr, PageRequest.of(page - 1, size));
	}
	
	@Override
	public List<TecOpTempMod> getallbytecoptemplist (String prestation)
	{
		return repository.getallbytecoptemplist(prestation);
	}

	@Override
	public Double getSommeTecOpByPrestation(String prestation,String dr) {
		return repository.getSommeTecOpByPrestation(prestation,dr);
	}

}
