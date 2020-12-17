package mg.cnaps.services;

import java.util.List;

import mg.cnaps.models.Objetop;
import mg.cnaps.models.TecopMod;

public interface TecopService extends CRUDService<TecopMod> {
	List<TecopMod> findListTecopByidAcc (String id_acc);
	int getnbdonné (String flag_valide);
	List<TecopMod> getallbyflag_valide (String flag_valide,int page);
	List<TecopMod> getallbyId_op (String id_op);
	List<TecopMod> getallbyIdopflagvalide (String id_op,String flag_valide);
	void updateTecOp(TecopMod tecop);

	TecopMod getByIdandRgle(String id_op);
	Objetop findListetecop(Objetop obj);
	Objetop findListetecopbyprestation(Objetop obj);
	//List<TecopMod> list(String flag, String prestation);
	
	TecopMod findbyIdOpAndIdDemande(String idop, String idAcc);
	
	TecopMod findById_op(String numOp);
}
