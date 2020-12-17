package mg.cnaps.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import mg.cnaps.models.TecOpTempMod;

public interface TecOpTempRepository extends JpaRepository<TecOpTempMod,String> {

	
	@Query ("select u from TecOpTempMod u where u.id_prestation = ?1 and u.id_demande like '%' || ?2 and u.flag_op='N' and (u.montant < 1000000 or u.montant is null)")
	Page<TecOpTempMod> getallbytecoptemp (String prestation,String dr,Pageable page);
	
	@Query ("select u from TecOpTempMod u where u.id_prestation = ?1 and u.id_demande like '%' || ?2 and u.flag_op='N' and (u.montant > 1000000 or u.montant = 1000000)")
	Page<TecOpTempMod> getallbytecoptempsup (String prestation,String dr,Pageable page);
	
	@Query ("select u from TecOpTempMod u where u.id_prestation = ?1 and u.flag_op='N'")
	List<TecOpTempMod> getallbytecoptemplist (String prestation);
	
	@Query ("select sum(u.montant) from TecOpTempMod u where u.id_prestation = ?1 and u.id_demande like '%' || ?2 and u.flag_op='N'")
	Double getSommeTecOpByPrestation(String prestation,String dr);
	
	@Transactional
	@Modifying
	@Query("update TecOpTempMod s set s.flag_op = 'O' where s.id_prestation=?1 and s.id_demande like '%' || ?2 and s.flag_op='N'")
	void updateTecOptemp (String prestation,String dr);
}
