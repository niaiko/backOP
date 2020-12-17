package mg.cnaps.services;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import mg.cnaps.models.Objetop;
import mg.cnaps.models.TecopMod;
import mg.cnaps.repository.TecopRepository;

@Service
public class TecopServiceImpl implements  TecopService{
	
	public static int max=20;

	@Autowired
	private TecopRepository tecopRepository;

	@Override
	public TecopMod save(TecopMod entity) {
		return tecopRepository.save(entity);
	}

	@Override
	public TecopMod getById(Serializable id) {
		Optional<TecopMod> opt = tecopRepository.findById((String) id);
		if(opt.isPresent())
			return opt.get();
		return null;
	}

	@Override
	public List<TecopMod> getAll() {
		return tecopRepository.findAll();
	}

	@Override
	public void delete(Serializable id) {
		tecopRepository.deleteById((String) id);
	}

	@Override
	public List<TecopMod> findListTecopByidAcc(String id_acc) {
		return tecopRepository.findListTecopByidAcc(id_acc);
	}
	
	@Override
	public List<TecopMod> getallbyflag_valide (String flag_valide,int page)
	{
		return (tecopRepository.getallbyflag_valide(flag_valide,PageRequest.of(page-1,max)));
	}
	
	@Override
	public int getnbdonné (String flag_valide)
	{
		return (tecopRepository.getnbdonné(flag_valide)/20)+1;
	}
	
	@Override
	public List<TecopMod> getallbyId_op (String id_op)
	{
		return tecopRepository.getallbyId_op(id_op);
	}
	
	@Override
	public List<TecopMod> getallbyIdopflagvalide (String id_op,String flag_valide)
	{
		return tecopRepository.getallbyIdopflagvalide(id_op, flag_valide);
	}
	
	@Override
	public void updateTecOp(TecopMod tecop)
	{
		tecopRepository.updateTecOp(tecop);
	}

	@Override
	public List<TecopMod> save(List<TecopMod> entity) {
		return tecopRepository.saveAll(entity);
	}

	@Override
	public TecopMod getByIdandRgle(String id_op) {
		// TODO Auto-generated method stub
		return tecopRepository.getByIdandRgle(id_op);
	}

	@Override
	public Objetop findListetecop(Objetop obj)
	{
		Page<TecopMod> p=(tecopRepository.getallop(obj.getFlagvalide(),obj.getOp(),PageRequest.of(obj.getPage()-1,obj.getSize())));
		obj.setL(p.getContent());
		obj.setNpagetotal(p.getTotalPages());
		return obj;
	}
	
	@Override
	public Objetop findListetecopbyprestation(Objetop obj)
	{
		Page<TecopMod> p=(tecopRepository.getallop(obj.getFlagvalide(),obj.getOp(),obj.getPrestation(),PageRequest.of(obj.getPage()-1,obj.getSize())));
		obj.setL(p.getContent());
		obj.setNpagetotal(p.getTotalPages());
		return obj;
	}

	@Override
	public TecopMod findbyIdOpAndIdDemande(String idop, String idAcc) {
		// TODO Auto-generated method stub
		return tecopRepository.findbyIdOpAndIdDemande( idop,  idAcc);
	}

	@Override
	public TecopMod findById_op(String numOp) {
		// TODO Auto-generated method stub
		return tecopRepository.findById_op(numOp);
	}

	/*@Override
	public List<TecopMod> list(String flag, String prestation) {
		return tecopRepository.list(flag, prestation);
	}*/

}
