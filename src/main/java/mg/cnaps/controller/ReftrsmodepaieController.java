package mg.cnaps.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import mg.cnaps.models.ParamBenefBanque;
import mg.cnaps.models.ReftrsmodepaieMod;
import mg.cnaps.repository.ReftrsmodepaieRepository;
import mg.cnaps.services.ReftrsmodepaieService;

@RestController
public class ReftrsmodepaieController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReftrsmodepaieController.class);

	ObjectMapper om = new ObjectMapper();

	@Autowired
	ReftrsmodepaieService service;

	@Autowired
	ReftrsmodepaieRepository repository;

	@PostMapping("/updatemodepaiesouche")
	public ResponseEntity<Object> addTecop(@RequestBody ParamBenefBanque pb) {
		try {
			int taille = pb.getBenef().size();
			for (int i = 0; i < taille; i++) {
				Long modepaie = pb.getBenef().get(i).getIdModePaiementTiers();
				String idacc = pb.getBenef().get(i).getIdAcc();
				service.updatereftrsmodepaie(pb.getBenef().get(i).getModePaiementTiers().getNomInstitutionSortie(),
						pb.getBenef().get(i).getModePaiementTiers().getImputation(),
						pb.getBenef().get(i).getModePaiementTiers().getIdInstitution(),
						pb.getBenef().get(i).getModePaiementTiers().getNumCompteInstitution(), idacc, modepaie);
			}
			return new ResponseEntity<>(pb, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/updatemodepaiesouchepensionper")
	public ResponseEntity<Object> updatemodepaiesouchepensionper(@RequestBody ReftrsmodepaieMod sigcat) {
		try {
			service.updatereftrsmodepaiepen(sigcat.getCompte(), sigcat.getCle(), sigcat.getDateDebut(),
					sigcat.getDateFin(), sigcat.getCodeSwift(), sigcat.getDomiciliation(), sigcat.getIdTiers(),
					sigcat.getDefaut(), sigcat.getIdAgence(), sigcat.getIdModePaiement(), sigcat.getAbrevModePaiement(),
					sigcat.getCaisse(), sigcat.getNumero(), sigcat.getCodeBanque(), sigcat.getCodeAgence(),
					sigcat.getLibelleAgence(), sigcat.getLibelleBanque(), sigcat.getAbbrevBanque(), sigcat.getIdbenef(),
					sigcat.getAgenceotiv(), sigcat.getIdAcc(), sigcat.getIdModePaiementTiers());
			return new ResponseEntity<>(sigcat, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/getmodepaiebyidacc")
	public ResponseEntity<Object> getmodepaiebyidacc(@RequestBody String id) {
		try {
			LOGGER.info("petit idacc " + id);
			ReftrsmodepaieMod recupererListe = service.findmodepaieidAcc(id);
			return new ResponseEntity<>(recupererListe, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/getModePaieByIdAcc/{idAcc}")
	public ResponseEntity<Object> getmodepaiebyidAcc(@PathVariable String idAcc) {
		try {
			LOGGER.info("petit idacc " + idAcc);
			ReftrsmodepaieMod recupererListe = service.findmodepaieidAcc(idAcc);
			return new ResponseEntity<>(recupererListe, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/listtrsmodepaie")
	public ResponseEntity<Object> listtrsmodepaie() {
		try {
			List<ReftrsmodepaieMod> recupererListe = service.getAll();
			return new ResponseEntity<>(recupererListe, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/findmodepaieByIdTiersAndIdAcc")
	public ResponseEntity<Object> findmodepaieByIdTiersAndIdAcc(@RequestBody String rec) {
		try {
			Map<String, String> map = om.readValue(rec, new TypeReference<Map<String, String>>() {
			});
			ReftrsmodepaieMod recupererListe = service.findByIdTiersAndIdAcc(map.get("tiers"), map.get("idacc"));
			return new ResponseEntity<>(recupererListe, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/saveListeModePaiementTiersOpPeriodique")
	public ResponseEntity<Object> aj(@RequestBody ReftrsmodepaieMod mod) throws JsonProcessingException {
		LOGGER.info("REFFFFFFFFFFFFF " + om.writeValueAsString(mod));
		return new ResponseEntity<>(service.save(mod), HttpStatus.OK);
	}

	@PostMapping("/findmodepaieByIdTiersAndprestation")
	public ResponseEntity<Object> findmodepaieByIdTiersAndprestation(@RequestBody String rec) {
		try {
			Map<String, String> map = om.readValue(rec, new TypeReference<Map<String, String>>() {
			});
			List<ReftrsmodepaieMod> recupererListe = repository
					.getlistemodepaiement(Long.valueOf(map.get("idmdptiers")), map.get("prestation"));
			return new ResponseEntity<>(recupererListe, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/updatemodepaiementop")
	public ResponseEntity<Object> updatemodepaiementop(@RequestBody ReftrsmodepaieMod s) {
		try {
			service.updatemodepaiement(s.getCompte(), s.getCle(), s.getDateDebut(), s.getDateFin(), s.getCodeSwift(),
					s.getDomiciliation(), s.getIdTiers(), s.getDefaut(), s.getIdAgence(), s.getIdModePaiement(),
					s.getAbrevModePaiement(), s.getCaisse(), s.getNumero(), s.getCodeBanque(), s.getCodeAgence(),
					s.getLibelleAgence(), s.getLibelleBanque(), s.getAbbrevBanque(), s.getIdbenef(), s.getAgenceotiv(),
					s.getIdModePaiementTiers(), s.getNomInstitutionSortie(), s.getImputation(), s.getIdInstitution(),
					s.getNumCompteInstitution(), s.getCodeDr(), s.getIdDmdDepense(), s.getAgenceotiv(), s.getIdAcc());
			return new ResponseEntity<>(s, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
