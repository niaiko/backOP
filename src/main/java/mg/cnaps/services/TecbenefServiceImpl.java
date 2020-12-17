package mg.cnaps.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import mg.cnaps.models.ObjetBenefprestation;
import mg.cnaps.models.TecbenefMod;
import mg.cnaps.models.Tecbenefdateop;
import mg.cnaps.repository.TecbenefRepository;

@Service
public class TecbenefServiceImpl implements  TecbenefService{

	@Autowired
	private TecbenefRepository tecbenefRepository;

	@Override
	public TecbenefMod save(TecbenefMod entity) {
		return tecbenefRepository.save(entity);
	}

	@Override
	public TecbenefMod getById(Serializable id) {
		Optional<TecbenefMod> opt = tecbenefRepository.findById((String) id);
		if(opt.isPresent())
			return opt.get();
		return null;
	}

	@Override
	public List<TecbenefMod> getAll() {
		return tecbenefRepository.findAll();
	}

	@Override
	public void delete(Serializable id) {
		tecbenefRepository.deleteById((String) id);
	}

	@Override
	public List<TecbenefMod> selectTecbenefId(String id_op) {
		// TODO Auto-generated method stub
		return tecbenefRepository.selectTecbenefId(id_op) ;
	}

	@Override
	public List<TecbenefMod> save(List<TecbenefMod> entity) {
		// TODO Auto-generated method stub
		return tecbenefRepository.saveAll(entity);
	}
	
	@Override
	public List<TecbenefMod> findListetecbenef()
	{
		List<TecbenefMod> liste=tecbenefRepository.getlistopcaisse();
		return liste;
	}

	@Override
	public void updateflagbenef(String flag, String op, String benef) {
		tecbenefRepository.updateflagbenef(flag, op, benef);
		
	}

	@Override
	public void updateflagindiv(String flag, String op, String benef) {
		tecbenefRepository.updateflagindiv(flag, op, benef);
		
	}

	@Override
	public void updateflagempl(String flag, String op, String benef) {
		tecbenefRepository.updateflagempl(flag, op, benef);
	}
	
	@Override
	public ObjetBenefprestation gethistoriquepaiement(ObjetBenefprestation obj)
	{
		Page<TecbenefMod> p=(tecbenefRepository.gethistoriquepaiement(obj.getMatricule(),obj.getPrestation(),PageRequest.of(obj.getPage()-1,obj.getSize())));
		List<Tecbenefdateop> list=new ArrayList<>();
		int taille=p.getContent().size();
		for(int i=0;i<taille;i++)
		{
			Tecbenefdateop tec=new Tecbenefdateop();
			tec.setFlag_valide(p.getContent().get(i).getFlag_valide());
			tec.setId_adresse(p.getContent().get(i).getId_adresse());
			tec.setId_benef(p.getContent().get(i).getId_benef());
			tec.setId_compte(p.getContent().get(i).getId_compte());
			tec.setId_demande(p.getContent().get(i).getId_demande());
			tec.setId_empl(p.getContent().get(i).getId_empl());
			tec.setId_individu(p.getContent().get(i).getId_individu());
			tec.setId_mode_paiement(p.getContent().get(i).getId_mode_paiement());
			tec.setId_op(p.getContent().get(i).getId_op());
			tec.setId_sucursale(p.getContent().get(i).getId_sucursale());
			tec.setMontant(p.getContent().get(i).getMontant());
			list.add(tec);
		}
		obj.setL(list);
		obj.setNpagetotal(p.getTotalPages());
		return obj;
	}

	@Override
	public List<TecbenefMod> gethistoriquepaiementpension(String matricule) {
		return tecbenefRepository.gethistoriquepaiementpension(matricule);
	}

}
