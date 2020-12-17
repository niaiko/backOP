package mg.cnaps.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import mg.cnaps.models.ModePaiePK;
import mg.cnaps.models.Objetop;
import mg.cnaps.models.ParamSuppOp;
import mg.cnaps.models.ReftrsmodepaieMod;
import mg.cnaps.models.TecOpBenef;
import mg.cnaps.models.TecOpTempMod;
import mg.cnaps.models.TecbenefMod;
import mg.cnaps.models.TecopMod;
import mg.cnaps.services.ReftrsmodepaieService;
import mg.cnaps.services.TecOpTempService;
import mg.cnaps.services.TecbenefService;
import mg.cnaps.services.TecopService;
import mg.cnaps.util.Test;

@RestController
public class TecOpTempController {

	ObjectMapper om = new ObjectMapper();

	private static final Logger LOGGER = LoggerFactory.getLogger(TecOpTempController.class);

	@Autowired
	ReftrsmodepaieService service;

	@Autowired
	TecOpTempService tempservice;

	@Autowired
	TecopService opservice;

	@Autowired
	TecbenefService serv;

	@GetMapping("/getmodepaiebyidacc")
	public ResponseEntity<Object> getmodepaiementbyagent(@RequestParam(name = "idacc") String idacc) {
		ReftrsmodepaieMod recupererListe = service.findmodepaieidAcc(idacc);
		return new ResponseEntity<>(recupererListe, HttpStatus.OK);
	}

	@PostMapping("/getmodepaiebymodepaiepk")
	public ResponseEntity<Object> getmodepaiebymodepaiepk(@RequestBody ModePaiePK pk) {
		try {
			LOGGER.info("pk " + om.writeValueAsString(pk));
			ReftrsmodepaieMod recupererListe = service.getById(pk);
			if (recupererListe != null)
				return new ResponseEntity<>(recupererListe, HttpStatus.OK);
			return new ResponseEntity<>(new ReftrsmodepaieMod(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("/ajouttrsmodepaie")
	public ResponseEntity<Object> ajouttrsmodepaie(@RequestBody ReftrsmodepaieMod modepaie) {
		try {
			LOGGER.info("mdp " + om.writeValueAsString(modepaie));
			service.save(modepaie);
			return new ResponseEntity<>(modepaie, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("/listTecOpTemp")
	public ResponseEntity<Object> listTecOpTemp(@RequestBody String tec) {
		try {
			Map<String, String> map = om.readValue(tec, new TypeReference<Map<String, String>>() {
			});
			Page<TecOpTempMod> pages = tempservice.getallbytecoptemp(map.get("prestation"), map.get("dr"),
					Integer.parseInt(map.get("page")), Integer.parseInt(map.get("size")));
			LOGGER.info("Op Temp : " + om.writeValueAsString(pages));
			List<TecbenefMod> tecBenef = new ArrayList<TecbenefMod>();
			for (TecOpTempMod opTemp : pages.getContent()) {
				TecbenefMod benef = new TecbenefMod();
				benef.setId_adresse(opTemp.getId_adresse());
				benef.setId_benef(opTemp.getId_benef());
				benef.setId_compte(opTemp.getId_compte());
				benef.setId_empl(opTemp.getId_empl());
				benef.setId_individu(opTemp.getId_individu());
				benef.setId_mode_paiement(service.findByIdAccAndIdModePaiementTiers(opTemp.getId_demande(),
						opTemp.getId_mode_paiement().longValue()));
				System.out.println("MDPPPPP 01 " + opTemp.getId_mode_paiement().longValue());
				System.out.println("MDPPPPP 02 " + benef.getId_mode_paiement());
				benef.setId_sucursale(opTemp.getId_sucursale());
				benef.setMontant(opTemp.getMontant());
				benef.setId_demande(opTemp.getId_demande());
				tecBenef.add(benef);
			}
			LOGGER.info("Op Temp benef: " + om.writeValueAsString(tecBenef));
			TecOpBenef ret = new TecOpBenef();
			TecopMod op = new TecopMod();
			Double somme = tempservice.getSommeTecOpByPrestation(map.get("prestation"), map.get("dr"));
			LOGGER.info("Op Temp somme: " + om.writeValueAsString(somme));
			if (somme == null) {
				op.setMontant(0.0);
			} else if (somme == 0.0) {
				op.setMontant(somme);
			} else {
				String dstr = String.valueOf(somme).split("\\.")[0];
				int taille = dstr.length();
				if (dstr.equals(somme)) {
					dstr = String.valueOf(somme).split("\\,")[0];
				}
				if (dstr.substring(taille - 2, taille).equalsIgnoreCase("00")) {
					op.setMontant(somme);
				} else {
					op.setMontant(Test.roundup(somme, 100));
				}

			}

			op.setTecbenef(tecBenef);
			LOGGER.info("Op Temp op: " + om.writeValueAsString(op));
			ret.setTecop(op);
			ret.setNbPage(pages.getTotalPages());
			ret.setPage(Integer.parseInt(map.get("page")));
			ret.setSize(Integer.parseInt(map.get("size")));
			LOGGER.info("Op Temp ret: " + om.writeValueAsString(op));
			return new ResponseEntity<>(ret, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("/listTecOpTempSup")
	public ResponseEntity<Object> listTecOpTempsup(@RequestBody String tec) {
		try {
			Map<String, String> map = om.readValue(tec, new TypeReference<Map<String, String>>() {
			});
			Page<TecOpTempMod> pages = tempservice.getallbytecoptempsup(map.get("prestation"), map.get("dr"),
					Integer.parseInt(map.get("page")), Integer.parseInt(map.get("size")));
			List<TecOpBenef> tecBenef = new ArrayList<TecOpBenef>();
			for (TecOpTempMod opTemp : pages.getContent()) {
				TecOpBenef opbenef = new TecOpBenef();
				TecopMod op = new TecopMod();
				List<TecbenefMod> benef = new ArrayList<TecbenefMod>();
				TecbenefMod te = new TecbenefMod();
				te.setId_adresse(opTemp.getId_adresse());
				te.setId_benef(opTemp.getId_benef());
				te.setId_compte(opTemp.getId_compte());
				te.setId_empl(opTemp.getId_empl());
				te.setId_individu(opTemp.getId_individu());
				te.setId_mode_paiement(service.findByIdAccAndIdModePaiementTiers(opTemp.getId_demande(),
						opTemp.getId_mode_paiement().longValue()));
				te.setId_sucursale(opTemp.getId_sucursale());
				if (opTemp.getMontant() == null) {
					te.setMontant(0.0);
					op.setMontant(0.0);
				} else if (opTemp.getMontant() == 0.0) {
					te.setMontant(opTemp.getMontant());
					op.setMontant(opTemp.getMontant());
				} else {
					String dstr = String.valueOf(opTemp.getMontant()).split("\\.")[0];
					int taille = dstr.length();
					if (dstr.equals(opTemp.getMontant())) {
						dstr = String.valueOf(opTemp.getMontant()).split("\\,")[0];
					}
					if (dstr.substring(taille - 2, taille).equalsIgnoreCase("00")) {
						te.setMontant(opTemp.getMontant());
						op.setMontant(opTemp.getMontant());
					} else {
						te.setMontant(Test.roundup(opTemp.getMontant(), 100));
						op.setMontant(Test.roundup(opTemp.getMontant(), 100));
					}
				}
				te.setId_demande(opTemp.getId_demande());
				benef.add(te);
				op.setTecbenef(benef);
				opbenef.setTecop(op);
				tecBenef.add(opbenef);
			}
			ParamSuppOp par = new ParamSuppOp();

			par.setTecBenef(tecBenef);
			par.setNbPage(pages.getTotalPages());
			par.setPage(Integer.parseInt(map.get("page")));
			par.setSize(Integer.parseInt(map.get("size")));
			return new ResponseEntity<>(par, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("/listTecopbyprestation")
	public ResponseEntity<Object> listTecopbyprestation(@RequestBody Objetop param) {
		Objetop findListetecoppage = opservice.findListetecopbyprestation(param);
		for (int i = 0; i < findListetecoppage.getL().size(); i++) {
			List<TecbenefMod> tecbenef = serv.selectTecbenefId(findListetecoppage.getL().get(i).getId_op());
			findListetecoppage.getL().get(i).setTecbenef(tecbenef);
		}
		return new ResponseEntity<>(findListetecoppage, HttpStatus.OK);
	}

	@GetMapping("/listTecopById")
	public ResponseEntity<Object> listTecopById(@RequestParam(name = "idop") String idop) {
		TecopMod recupererListe = opservice.getById(idop);
		List<TecbenefMod> tecbenef = serv.selectTecbenefId(idop);
		recupererListe.setTecbenef(tecbenef);
		return new ResponseEntity<>(recupererListe, HttpStatus.OK);
	}

	@PostMapping("/saveTecoptemp")
	public ResponseEntity<Object> saveTecoptemp(@RequestBody TecOpTempMod temp) {
		try {
			LOGGER.info("avant mode de paiement debogage " + om.writeValueAsString(temp));
			LOGGER.info("avant mode de paiement debogage " + om.writeValueAsString(temp));
			if (temp.getId_mode_paiement() == 0)
				throw new Exception("vérifier Id Mode de paiement est NULL");

			tempservice.save(temp);
			return new ResponseEntity<>(temp, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
		}

	}
}