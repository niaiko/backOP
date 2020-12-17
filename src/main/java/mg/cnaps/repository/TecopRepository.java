package mg.cnaps.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import mg.cnaps.models.TecopMod;

public interface TecopRepository extends JpaRepository<TecopMod,String> {
	@Query ("select u from TecopMod u where u.id_acc = ?1")
	List<TecopMod> findListTecopByidAcc (String id_acc);
	
	@Query ("select u from TecopMod u where u.flag_valide = ?1")
	List<TecopMod> getallbyflag_valide (String flag_valide,Pageable page);
	
	@Query ("select count(u) from TecopMod u where u.flag_valide = ?1")
	int getnbdonné (String flag_valide);
	
	@Query ("select u from TecopMod u where u.id_op = ?1")
	List<TecopMod> getallbyId_op (String id_op);
	
	@Query ("select u from TecopMod u where u.id_op = ?1 and u.flag_valide = ?2")
	List<TecopMod> getallbyIdopflagvalide (String id_op,String flag_valide);
	
	@Query ("select u from TecopMod u where u.id_op = ?1")
	TecopMod findById_op(String numOp);
	
	@Transactional
	@Modifying
	@Query("update TecopMod s set s.id_op = :#{#tecop.id_op},s.id_acc = :#{#tecop.id_acc},s.montant = :#{#tecop.montant},s.flag_valide = :#{#tecop.flag_valide},s.date_op = :#{#tecop.date_op},s.observations = :#{#tecop.observations},s.br_code = :#{#tecop.br_code}")
	void updateTecOp (@Param("tecop")TecopMod tecop);
	
	@Query(value = "SELECT nextval('\"RFM\".tec_op_seq')", nativeQuery = true)
	Long getNextSeqOp2();
	
	@Query ("select u from TecopMod u where u.id_op = ?1 and u.flag_valide='R'")
	TecopMod getByIdandRgle(String id_op);
	
	@Query("select u from TecopMod u where ((u.flag_valide = ?1) or cast(?1 as string)is null) and ((u.date_op = ?2) or cast(?2 as string) is null)")
	Page<TecopMod> getallop(String flag,Date op,Pageable page);
	
	@Query(value="SELECT * FROM \"RFM\".tec_op where (flag_valide = ?1 or ?1 is null) and ((date_op = ?2) or cast(?2 as varchar) is null) and substring(id_op,1,3)=?3 ",nativeQuery=true)
	Page<TecopMod> getallop(String flag,Date op,String prestation,Pageable page);
	
	/*@Query(nativeQuery=true,value="SELECT * from \"RFM\".tec_op where substring(id_op from 1 for 3)=?2 and flag_valide=?1")
	Page<TecopMod> list(String flag, String prestation,Pageable page);*/
	
	//@Query("select b from TecopMod b join b.tecbenef tecbenef where tecbenef.id_op = ?1 and tecbenef.id_demande = ?2 ")
	@Query(nativeQuery=true, value="SELECT o.* FROM \"RFM\".tec_op o join \"RFM\".tec_benef b on o.id_op=b.id_op where b.id_op=?1 and b.id_demande=?2")
	TecopMod findbyIdOpAndIdDemande(String idop, String idAcc);
}
