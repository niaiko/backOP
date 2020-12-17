package mg.cnaps.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import mg.cnaps.models.TecbenefMod;

public interface TecbenefRepository extends JpaRepository<TecbenefMod,String> {

	@Query("select b from TecbenefMod b where b.id_op = ?1")
	List<TecbenefMod> selectTecbenefId(String id_op);
	
	@Query("select b from TecbenefMod b where b.id_mode_paiement.idModePaiement = 1 and b.flag_valide='R' ")
	List<TecbenefMod> getlistopcaisse();
	
	@Transactional
	@Modifying
	@Query("update TecbenefMod s set s.flag_valide = ?1 where s.id_op=?2 and id_benef=?3")
	void updateflagbenef (String flag, String op,String benef);
	
	@Transactional
	@Modifying
	@Query("update TecbenefMod s set s.flag_valide = ?1 where s.id_op=?2 and id_individu=?3")
	void updateflagindiv (String flag, String op,String benef);
	
	@Transactional
	@Modifying
	@Query("update TecbenefMod s set s.flag_valide = ?1 where s.id_op=?2 and id_empl=?3")
	void updateflagempl (String flag, String op,String benef);
	
	@Query(nativeQuery=true,value="SELECT * FROM \"RFM\".tec_benef where (id_benef=?1 or ?1 is null) and (substring(id_op,1,3)=?2 or ?2 is null)")
	Page<TecbenefMod> gethistoriquepaiement(String matricule, String prestation, Pageable page);
	
	@Query(nativeQuery=true,value="SELECT * FROM \"RFM\".tec_benef where (id_benef=?1 or ?1 is null) and (substring(id_op,1,1)='3')")
	List<TecbenefMod> gethistoriquepaiementpension(String matricule);

}
