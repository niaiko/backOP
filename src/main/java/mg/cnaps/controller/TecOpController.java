package mg.cnaps.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import mg.cnaps.config.ReferenceUtil;
import mg.cnaps.models.Historiquedroit;
import mg.cnaps.models.ParamBenefBanque;
import mg.cnaps.models.ParamTecOP;
import mg.cnaps.models.PerOpBeneficiaire;
import mg.cnaps.models.SoucheMod;
import mg.cnaps.models.TecbenefMod;
import mg.cnaps.models.TecopMod;
import mg.cnaps.models.Tef;
import mg.cnaps.models.paramRappelPension;
import mg.cnaps.repository.TecOpTempRepository;
import mg.cnaps.repository.TecopRepository;
import mg.cnaps.services.ReftrsmodepaieService;
import mg.cnaps.services.TecOpTempService;
import mg.cnaps.services.TecbenefService;
import mg.cnaps.services.TecopService;
import mg.cnaps.util.RequestApi;

@RestController
public class TecOpController {

	ObjectMapper om = new ObjectMapper();
	private static final Logger log = LoggerFactory.getLogger(TecOpController.class);

	@Autowired
	TecopService service;

	@Autowired
	TecopRepository repository;

	@Autowired
	TecbenefService serv;

	@Autowired
	TecOpTempService tectemp;

	@Autowired
	TecOpTempRepository temprepository;

	@Autowired
	ReftrsmodepaieService mdpservice;

	@Autowired
	RequestApi request;

	@DeleteMapping("/delete/{idop}")
	public void delete(@PathVariable String idop) {
		repository.deleteById(idop);
	}

	@PostMapping("/addTecop")
	@Transactional(rollbackOn = Exception.class)
	public ResponseEntity<Object> addTecop(@RequestBody ParamTecOP param,
			@RequestHeader(value = "Authorization") String token) {
		try {
			java.util.Date date = Calendar.getInstance().getTime();
			String trs = ReferenceUtil.getReferenceDemande(param.getPrestation(), repository.getNextSeqOp2(),
					param.getDr());
			param.getTecop().setId_op(trs);
			param.getTecop().setDate_op(new Date(date.getTime()));
			for (int i = 0; i < param.getTecop().getTecbenef().size(); i++) {
				param.getTecop().getTecbenef().get(i).setId_op(trs);
			}
			service.save(param.getTecop());
			serv.save(param.getTecop().getTecbenef());
			param.getTef().setNumOp(param.getTecop().getId_op());
			temprepository.updateTecOptemp(param.getPrestation(), param.getDr());
			request.sendPost("http://cnapsDbTef:9915/saveTefTech", om.writeValueAsString(param.getTef()), token);
			/*
			 * producer.send(record.key().toString(),"addTecopRes",
			 * om.writeValueAsString(param));
			 * producer.send(record.key().toString(),"saveTecOpBIReq",
			 * om.writeValueAsString(param.getTecop()));
			 */

			ParamBenefBanque pb = new ParamBenefBanque();
			int taille = param.getTecop().getTecbenef().size();
			List<PerOpBeneficiaire> lstper = new ArrayList<>();
			for (int p = 0; p < taille; p++) {
				Historiquedroit hist = new Historiquedroit();
				hist.setDatePaiement(param.getTecop().getDate_op());
				hist.setIdDemande(param.getTecop().getTecbenef().get(p).getId_demande());
				hist.setMatriculeBenef(param.getTecop().getTecbenef().get(p).getId_benef());
				hist.setMontant(param.getTecop().getTecbenef().get(p).getMontant());
				hist.setPeriode(ReferenceUtil.convertDateToStringperiode(param.getTecop().getDate_op()));
				if (param.getTecop().getTecbenef().get(p).getId_demande().substring(0, 3).equalsIgnoreCase("414")) {
					hist.setType("DAPM");
					hist.setLibelleType("Demande d'accouchement pré-natale et de maternité");
				} else if (param.getTecop().getTecbenef().get(p).getId_demande().substring(0, 3)
						.equalsIgnoreCase("430")) {
					hist.setType("RFA");
					hist.setLibelleType("Remboursement de frais d'accouchement");
				} else if (param.getTecop().getTecbenef().get(p).getId_demande().substring(0, 3)
						.equalsIgnoreCase("411")) {
					hist.setType("AP");
					hist.setLibelleType("Allocation pré-natale");
				} else if (param.getTecop().getTecbenef().get(p).getId_demande().substring(0, 3)
						.equalsIgnoreCase("412")) {
					hist.setType("AM1");
					hist.setLibelleType("Allocation de maternité 1");
				} else if (param.getTecop().getTecbenef().get(p).getId_demande().substring(0, 3)
						.equalsIgnoreCase("413")) {
					hist.setType("AM2");
					hist.setLibelleType("Allocation de maternité 1");
				} else if (param.getTecop().getTecbenef().get(p).getId_demande().substring(0, 3)
						.equalsIgnoreCase("421")) {
					hist.setType("IJ1");
					hist.setLibelleType("Indemnité journalière 1");
				} else if (param.getTecop().getTecbenef().get(p).getId_demande().substring(0, 3)
						.equalsIgnoreCase("422")) {
					hist.setType("IJ2");
					hist.setLibelleType("Indemnité journalière 2");
				} else if (param.getTecop().getTecbenef().get(p).getId_demande().substring(0, 1)
						.equalsIgnoreCase("3")) {
					hist.setType("PENSION APPERIODIQUE");
					hist.setLibelleType("PENSION APPERIODIQUE");
				} else if (param.getTecop().getTecbenef().get(p).getId_demande().substring(0, 1)
						.equalsIgnoreCase("2")) {
					hist.setType("AT/MP APPERIODIQUE");
					hist.setLibelleType("Accident de travail/Maladie professionnelle appériodique");
				}
				request.sendPost("http://cnapsHistoGen:5140/savehistodroit", om.writeValueAsString(hist), token);
				PerOpBeneficiaire per = new PerOpBeneficiaire();
				per.setBenefMatricule(param.getTecop().getTecbenef().get(p).getId_benef());
				per.setIdModePaiementTiers(param.getTecop().getTecbenef().get(p).getId_mode_paiement()
						.getIdModePaiementTiers().longValue());
				per.setModePaiementTiers(param.getTecop().getTecbenef().get(p).getId_mode_paiement());
				per.setOpBenefMontant(param.getTecop().getTecbenef().get(p).getMontant());
				per.setOpNumero(param.getTecop().getTecbenef().get(p).getId_op());
				per.setIdAcc(param.getTecop().getTecbenef().get(p).getId_demande());
				lstper.add(per);
			}
			pb.setBenef(lstper);
			pb.setCodeDrService(param.getTecop().getCode_dr_service());
			SoucheMod souche = new SoucheMod();
			souche.setParam(pb);
			souche.setMotif("C0005");
			souche.setType("APE");
			request.sendPost("http://cnapsdbBanque:9501/findsouchetech", om.writeValueAsString(souche), token);
			return new ResponseEntity<>(param, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/addTecopAperiodique")
	@Transactional(rollbackOn = Exception.class)
	public ResponseEntity<Object> addTecopAperiodique(@RequestBody ParamTecOP param,
			@RequestHeader(value = "Authorization") String token) {
		try {
			java.util.Date date = Calendar.getInstance().getTime();
			String trs = ReferenceUtil.getReferenceDemande(param.getPrestation(), repository.getNextSeqOp2(),
					param.getDr());
			param.getTecop().setId_op(trs);
			param.getTecop().setDate_op(new Date(date.getTime()));
			for (int i = 0; i < param.getTecop().getTecbenef().size(); i++) {
				param.getTecop().getTecbenef().get(i).setId_op(trs);
			}
			service.save(param.getTecop());
			serv.save(param.getTecop().getTecbenef());
			param.getTef().setNumOp(param.getTecop().getId_op());
			temprepository.updateTecOptemp(param.getPrestation(), param.getDr());
			request.sendPost("http://cnapsDbTef:9915/saveTefTech", om.writeValueAsString(param.getTef()), token);
			/*
			 * producer.send(record.key().toString(),"addTecopRes",
			 * om.writeValueAsString(param));
			 * producer.send(record.key().toString(),"saveTecOpBIReq",
			 * om.writeValueAsString(param.getTecop()));
			 */

			ParamBenefBanque pb = new ParamBenefBanque();
			int taille = param.getTecop().getTecbenef().size();
			List<PerOpBeneficiaire> lstper = new ArrayList<>();
			for (int p = 0; p < taille; p++) {
				Historiquedroit hist = new Historiquedroit();
				hist.setDatePaiement(param.getTecop().getDate_op());
				hist.setIdDemande(param.getTecop().getTecbenef().get(p).getId_demande());
				hist.setMatriculeBenef(param.getTecop().getTecbenef().get(p).getId_benef());
				hist.setMontant(param.getTecop().getTecbenef().get(p).getMontant());
				hist.setPeriode(ReferenceUtil.convertDateToStringperiode(param.getTecop().getDate_op()));
				if (param.getTecop().getTecbenef().get(p).getId_demande().substring(0, 3).equalsIgnoreCase("414")) {
					hist.setType("DAPM");
					hist.setLibelleType("Demande d'accouchement pré-natale et de maternité");
				} else if (param.getTecop().getTecbenef().get(p).getId_demande().substring(0, 3)
						.equalsIgnoreCase("410")) {
					hist.setType("DAPM");
					hist.setLibelleType("Demande d'accouchement pré-natale et de maternité");
				} else if (param.getTecop().getTecbenef().get(p).getId_demande().substring(0, 3)
						.equalsIgnoreCase("430")) {
					hist.setType("RFA");
					hist.setLibelleType("Remboursement de frais d'accouchement");
				} else if (param.getTecop().getTecbenef().get(p).getId_demande().substring(0, 3)
						.equalsIgnoreCase("411")) {
					hist.setType("AP");
					hist.setLibelleType("Allocation pré-natale");
				} else if (param.getTecop().getTecbenef().get(p).getId_demande().substring(0, 3)
						.equalsIgnoreCase("412")) {
					hist.setType("AM1");
					hist.setLibelleType("Allocation de maternité 1");
				} else if (param.getTecop().getTecbenef().get(p).getId_demande().substring(0, 3)
						.equalsIgnoreCase("413")) {
					hist.setType("AM2");
					hist.setLibelleType("Allocation de maternité 1");
				} else if (param.getTecop().getTecbenef().get(p).getId_demande().substring(0, 3)
						.equalsIgnoreCase("421")) {
					hist.setType("IJ1");
					hist.setLibelleType("Indemnité journalière 1");
				} else if (param.getTecop().getTecbenef().get(p).getId_demande().substring(0, 3)
						.equalsIgnoreCase("422")) {
					hist.setType("IJ2");
					hist.setLibelleType("Indemnité journalière 2");
				} else if (param.getTecop().getTecbenef().get(p).getId_demande().substring(0, 1)
						.equalsIgnoreCase("3")) {
					hist.setType("PENSION APPERIODIQUE");
					hist.setLibelleType("PENSION APPERIODIQUE");
				} else if (param.getTecop().getTecbenef().get(p).getId_demande().substring(0, 1)
						.equalsIgnoreCase("2")) {
					hist.setType("AT/MP APPERIODIQUE");
					hist.setLibelleType("Accident de travail/Maladie professionnelle appériodique");
				} else {
					hist.setType("autre");
					hist.setLibelleType("autre");
				}
				request.sendPost("http://cnapsHistoGen:5140/savehistodroit", om.writeValueAsString(hist), token);
				PerOpBeneficiaire per = new PerOpBeneficiaire();
				per.setBenefMatricule(param.getTecop().getTecbenef().get(p).getId_benef());
				per.setIdModePaiementTiers(param.getTecop().getTecbenef().get(p).getId_mode_paiement()
						.getIdModePaiementTiers().longValue());
				per.setModePaiementTiers(param.getTecop().getTecbenef().get(p).getId_mode_paiement());
				per.setOpBenefMontant(param.getTecop().getTecbenef().get(p).getMontant());
				per.setOpNumero(param.getTecop().getTecbenef().get(p).getId_op());
				per.setIdAcc(param.getTecop().getTecbenef().get(p).getId_demande());
				lstper.add(per);
			}
			pb.setBenef(lstper);
			pb.setCodeDrService(param.getTecop().getCode_dr_service());
			SoucheMod souche = new SoucheMod();
			souche.setParam(pb);
			souche.setMotif("C0005");
			souche.setType("APE");
			log.info("DONNEEEEEEEEEEEE Souche ========= " + om.writeValueAsString(souche));
			request.sendPost("http://cnapsdbBanque:9501/findsouchetech", om.writeValueAsString(souche), token);
			return new ResponseEntity<>(param, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/addTecopperiodique")
	@Transactional(rollbackOn = Exception.class)
	public ResponseEntity<Object> addTecopperiodique(@RequestBody ParamTecOP param,
			@RequestHeader(value = "Authorization") String token) throws Exception {
		log.info("Parammmmmmmmmmmmm " + om.writeValueAsString(param));
		String trs = ReferenceUtil.getReferenceDemande(param.getPrestation(), repository.getNextSeqOp2(),
				param.getDr());
		param.getTecop().setId_op(trs);
		param.getTecop().setDate_op(Date.valueOf(LocalDate.now()));
		int size = param.getTecop().getTecbenef().size();
		String matr = "";
		for (int i = 0; i < size; i++) {
			param.getTecop().getTecbenef().get(i).setId_op(trs);
			log.info("ID OPPPPPPPPPPPP==== " + trs);
			String s = param.getTecop().getTecbenef().get(i).getId_demande();
			log.info("ID Demandeeeee ==== " + s);
			log.info("ID DEM AS SUBQTRING ==== " + s.substring(0, 2));
			if (param.getTef().getObjet().equalsIgnoreCase("extraction BAF")
					|| param.getTecop().getTecbenef().get(i).getId_demande().substring(0, 3).equalsIgnoreCase("400")) {
				matr = param.getTecop().getTecbenef().get(i).getId_empl();
			} else if (param.getTef().getObjet().equalsIgnoreCase("extraction ALSP")
					|| param.getTecop().getTecbenef().get(i).getId_demande().substring(0, 3).equalsIgnoreCase("401")) {
				matr = param.getTecop().getTecbenef().get(i).getId_individu();
			} else {
				matr = param.getTecop().getTecbenef().get(i).getId_benef();
			}
			System.out.println(
					"matricule : " + matr + " idAcc : " + param.getTecop().getTecbenef().get(i).getId_demande());
			param.getTecop().getTecbenef().get(i).setId_mode_paiement(
					mdpservice.findByIdTiersAndIdAcc(matr, param.getTecop().getTecbenef().get(i).getId_demande()));
			matr = "";
		}
		/*
		 * producer.send(record.key().toString(),"addTecopperiodiqueRes",
		 * om.writeValueAsString(param));
		 * producer.send(record.key().toString(),"saveTecOpBIReq",
		 * om.writeValueAsString(param.getTecop()));
		 */
		ParamBenefBanque pb = new ParamBenefBanque();
		int taille = param.getTecop().getTecbenef().size();
		List<PerOpBeneficiaire> lstper = new ArrayList<>();
		for (int p = 0; p < taille; p++) {
			// controle non null mode de paiement
			if (param.getTecop().getTecbenef().get(p).getId_mode_paiement() != null) {

				// save historique droit
				Historiquedroit hist = new Historiquedroit();
				hist.setDatePaiement(param.getTecop().getDate_op());
				hist.setIdDemande(param.getTecop().getTecbenef().get(p).getId_demande());
				hist.setMatriculeBenef(param.getTecop().getTecbenef().get(p).getId_benef());
				hist.setMontant(param.getTecop().getTecbenef().get(p).getMontant());
				hist.setPeriode(ReferenceUtil.convertDateToStringperiode(param.getTecop().getDate_op()));
				if (param.getTecop().getTecbenef().get(p).getId_demande().substring(0, 3).equalsIgnoreCase("400")) {
					hist.setType("AF");
					hist.setLibelleType("Allocation familiale");
				} else if (param.getTecop().getTecbenef().get(p).getId_demande().substring(0, 3)
						.equalsIgnoreCase("400")) {
					hist.setType("BAF");
					hist.setLibelleType("Bordereau d'allocation familiale");
				} else if (param.getTecop().getTecbenef().get(p).getId_demande().substring(0, 3)
						.equalsIgnoreCase("401")) {
					hist.setType("ALSP");
					hist.setLibelleType("Allocation spéciale");
				} else if (param.getTecop().getTecbenef().get(p).getId_demande().substring(0, 1)
						.equalsIgnoreCase("2")) {
					hist.setType("AT/MP PERIODIQUE");
					hist.setLibelleType("Accident de travail/Maladie professionnelle périodique");
				} else {
					hist.setType("autre");
					hist.setLibelleType("autre");
				}
				request.sendPost("http://cnapsHistoGen:5140/savehistodroit", om.writeValueAsString(hist), token);

				// ajout beneficiaire dans op
				PerOpBeneficiaire per = new PerOpBeneficiaire();
				if (param.getTef().getObjet().equalsIgnoreCase("extraction BAF")) {
					per.setBenefMatricule(param.getTecop().getTecbenef().get(p).getId_empl());
				} else if (param.getTef().getObjet().equalsIgnoreCase("extraction ALSP")) {
					per.setBenefMatricule(param.getTecop().getTecbenef().get(p).getId_individu());
				} else {
					per.setBenefMatricule(param.getTecop().getTecbenef().get(p).getId_benef());
				}
				per.setIdModePaiementTiers(param.getTecop().getTecbenef().get(p).getId_mode_paiement()
						.getIdModePaiementTiers().longValue());
				per.setModePaiementTiers(param.getTecop().getTecbenef().get(p).getId_mode_paiement());
				per.setOpBenefMontant(param.getTecop().getTecbenef().get(p).getMontant());
				per.setOpNumero(param.getTecop().getTecbenef().get(p).getId_op());
				per.setIdAcc(param.getTecop().getTecbenef().get(p).getId_demande());
				lstper.add(per);
			}
		}

		// controle si liste op dans souche non vide
		if (!lstper.isEmpty()) {
			service.save(param.getTecop());
			serv.save(param.getTecop().getTecbenef());
			param.getTef().setNumOp(param.getTecop().getId_op());
			temprepository.updateTecOptemp(param.getPrestation(), param.getDr());

			pb.setBenef(lstper);
			pb.setCodeDrService(param.getTecop().getCode_dr_service());
			SoucheMod souche = new SoucheMod();
			souche.setParam(pb);
			souche.setMotif("C0005");
			souche.setType("PER");
			souche.getParam().setPeriode(param.getTecop().getPeriode());
			log.info("Donner SOUCHE OD==== " + om.writeValueAsString(souche));
			log.info("Donner ParamBenefBanque==== " + om.writeValueAsString(pb));
			request.sendPost("http://cnapsDbTef:9915/saveTefTech", om.writeValueAsString(param.getTef()), token);
			request.sendPost("http://cnapsdbBanque:9501/findsouchetech", om.writeValueAsString(souche), token);
		}
		return new ResponseEntity<>(param, HttpStatus.OK);
	}

	@PostMapping("/addTecoppensionperiodique")
	@Transactional(rollbackOn = Exception.class)
	public ResponseEntity<Object> addTecoppensionperiodique(@RequestBody ParamTecOP param,
			@RequestHeader(value = "Authorization") String token) {
		try {
			temprepository.updateTecOptemp(param.getPrestation(), param.getDr());
			java.util.Date date = Calendar.getInstance().getTime();
			Map<Integer, TecopMod> map = new HashMap<Integer, TecopMod>();
			Map<Integer, Double> mapMontant = new HashMap<Integer, Double>();
			for (int i = 0; i < param.getTecop().getTecbenef().size(); i++) {
				param.getTecop().getTecbenef().get(i).setId_mode_paiement(
						mdpservice.findByIdTiersAndIdAcc(param.getTecop().getTecbenef().get(i).getId_benef(),
								param.getTecop().getTecbenef().get(i).getId_demande()));
				if (mapMontant
						.containsKey(param.getTecop().getTecbenef().get(i).getId_mode_paiement().getIdModePaiement())) {
					mapMontant.put(param.getTecop().getTecbenef().get(i).getId_mode_paiement().getIdModePaiement(),
							mapMontant.get(
									param.getTecop().getTecbenef().get(i).getId_mode_paiement().getIdModePaiement())
									+ param.getTecop().getTecbenef().get(i).getMontant());
				} else {
					mapMontant.put(param.getTecop().getTecbenef().get(i).getId_mode_paiement().getIdModePaiement(),
							param.getTecop().getTecbenef().get(i).getMontant());
				}
				if (map.containsKey(param.getTecop().getTecbenef().get(i).getId_mode_paiement().getIdModePaiement())) {
					param.getTecop().getTecbenef().get(i).setId_op(
							map.get(param.getTecop().getTecbenef().get(i).getId_mode_paiement().getIdModePaiement())
									.getId_op());
					map.get(param.getTecop().getTecbenef().get(i).getId_mode_paiement().getIdModePaiement())
							.getTecbenef().add(param.getTecop().getTecbenef().get(i));
					map.get(param.getTecop().getTecbenef().get(i).getId_mode_paiement().getIdModePaiement())
							.setMontant(mapMontant.get(
									param.getTecop().getTecbenef().get(i).getId_mode_paiement().getIdModePaiement()));

				} else {
					String trs = ReferenceUtil.getReferenceDemande(param.getPrestation(), repository.getNextSeqOp2(),
							param.getDr());
					TecopMod tecop = new TecopMod();
					tecop.setCode_dr_service(param.getTecop().getCode_dr_service());
					tecop.setDate_op(new Date(date.getTime()));
					tecop.setFlag_valide("O");
					tecop.setId_op(trs);
					tecop.setMontant(mapMontant
							.get(param.getTecop().getTecbenef().get(i).getId_mode_paiement().getIdModePaiement()));
					tecop.setObservations(param.getTecop().getObservations());
					tecop.setTecbenef(new ArrayList<TecbenefMod>());
					param.getTecop().getTecbenef().get(i).setId_op(tecop.getId_op());
					tecop.getTecbenef().add(param.getTecop().getTecbenef().get(i));
					map.put(param.getTecop().getTecbenef().get(i).getId_mode_paiement().getIdModePaiement(), tecop);

				}
			}
			for (TecopMod opTech : map.values()) {
				Tef tef = new Tef();
				tef.setCodeProjet(param.getTef().getCodeProjet());
				tef.setCodeService(param.getTef().getCodeService());
				tef.setCompteBenef(param.getTef().getCompteBenef());
				tef.setIdRubrique(param.getTef().getIdRubrique());
				tef.setObjet(param.getTef().getObjet());
				tef.setMontant(opTech.getMontant());
				tef.setNumOp(opTech.getId_op());
				request.sendPost("http://cnapsDbTef:9915/saveTefTech", om.writeValueAsString(tef), token);
				// om.writeValueAsString(opTech));

				ParamBenefBanque pb = new ParamBenefBanque();
				int taille = opTech.getTecbenef().size();
				List<PerOpBeneficiaire> lstper = new ArrayList<>();
				for (int p = 0; p < taille; p++) {
					Historiquedroit hist = new Historiquedroit();
					hist.setDatePaiement(param.getTecop().getDate_op());
					hist.setIdDemande(param.getTecop().getTecbenef().get(p).getId_demande());
					hist.setMatriculeBenef(param.getTecop().getTecbenef().get(p).getId_benef());
					hist.setMontant(param.getTecop().getTecbenef().get(p).getMontant());
					hist.setPeriode(ReferenceUtil.convertDateToStringperiode(param.getTecop().getDate_op()));
					hist.setType("PENSION PERIODIQUE");
					hist.setLibelleType("PENSION PERIODIQUE");
					request.sendPost("http://cnapsHistoGen:5140/savehistodroit", om.writeValueAsString(hist), token);
					PerOpBeneficiaire per = new PerOpBeneficiaire();
					per.setBenefMatricule(opTech.getTecbenef().get(p).getId_benef());
					per.setIdModePaiementTiers(
							opTech.getTecbenef().get(p).getId_mode_paiement().getIdModePaiementTiers().longValue());
					per.setModePaiementTiers(opTech.getTecbenef().get(p).getId_mode_paiement());
					per.setOpBenefMontant(opTech.getTecbenef().get(p).getMontant());
					per.setOpNumero(opTech.getTecbenef().get(p).getId_op());
					per.setIdAcc(opTech.getTecbenef().get(p).getId_demande());
					lstper.add(per);
				}
				pb.setBenef(lstper);
				pb.setCodeDrService(param.getTecop().getCode_dr_service());
				SoucheMod souche = new SoucheMod();
				souche.setParam(pb);
				souche.setMotif("C0005");
				souche.setType("PER");
				souche.getParam().setPeriode(param.getTecop().getPeriode());
				request.sendPost("http://cnapsdbBanque:9501/findsouchetechpensionper", om.writeValueAsString(souche),
						token);

				service.save(opTech);
				serv.save(opTech.getTecbenef());

			}
			return new ResponseEntity<>(map.values(), HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(value = "/listTecopById/{idOp}")
	public ResponseEntity<Object> listTecopByIdReq(@PathVariable String idOp) {
		try {
			TecopMod recupererListe = service.getById(idOp);
			List<TecbenefMod> tecbenef = serv.selectTecbenefId(idOp);
			recupererListe.setTecbenef(tecbenef);
			return new ResponseEntity<>(recupererListe, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
		}
	}

	// findDetailOPReq
	@Transactional(rollbackOn = Exception.class)
	@PostMapping(value = "/findDetailOP")
	public void findDetailOP(@PathVariable paramRappelPension param,
			@RequestHeader(value = "Authorization") String token) throws JsonProcessingException, Exception {
		TecopMod liste = service.findbyIdOpAndIdDemande(param.getIdOp(), param.getIdAcc());
		List<TecbenefMod> tecbenef = serv.selectTecbenefId(liste.getId_op());
		liste.setTecbenef(tecbenef);
		request.sendPost("http://cnapsPensionRappelRetenu:1236/insertrappelbyOP", om.writeValueAsString(liste), token);
	}
}
