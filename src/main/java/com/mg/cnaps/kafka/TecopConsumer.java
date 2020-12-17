package com.mg.cnaps.kafka;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import mg.cnaps.config.ConvertionLettre;
import mg.cnaps.config.ReferenceUtil;
import mg.cnaps.models.Historiquedroit;
import mg.cnaps.models.ListTecOPByFlagValideMod;
import mg.cnaps.models.Objetop;
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

@Service
public class TecopConsumer {

	private static final Logger LOGGER = LoggerFactory.getLogger(TecopConsumer.class);

	ObjectMapper om=new ObjectMapper();
	
	@Autowired
	Producer producer;
	
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
	
	@KafkaListener(topics="listTecopbyflagdateReq")
    public void listTecoptemp(ConsumerRecord<?,?> record){
		try
		{
			Objetop param = om.readValue(record.value().toString(), Objetop.class);
			Objetop findListetecoptemppage=service.findListetecop(param);
			for(int i=0;i<findListetecoptemppage.getL().size();i++)
			{
				List<TecbenefMod> tecbenef=serv.selectTecbenefId(findListetecoptemppage.getL().get(i).getId_op());
				findListetecoptemppage.getL().get(i).setTecbenef(tecbenef);
			}
			producer.send(record.key().toString(),"listTecopbyflagdateRes", om.writeValueAsString(findListetecoptemppage));
			LOGGER.info(om.writeValueAsString(findListetecoptemppage));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			producer.send(record.key().toString(),"listTecopbyflagdateRes", e.getMessage());
		}
    }
	
	/*@KafkaListener(topics="listTecopbyflagprestationReq")
    public void list(ConsumerRecord<?,?> record){
		try
		{
			Map<String, String> map = om.readValue(record.value().toString(), new TypeReference<Map<String, String>>(){});
			List<TecopMod> list=service.list(map.get("flag"), map.get("prestation"));
			producer.send(record.key().toString(),"listTecopbyflagprestationRes", om.writeValueAsString(list));
			LOGGER.info(om.writeValueAsString(list));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			producer.send(record.key().toString(),"listTecopbyflagprestationRes", e.getMessage());
		}
    }*/
	
	@KafkaListener(topics="listTecopbyprestationReq")
    public void listTecopbyprestation(ConsumerRecord<?,?> record){
		try
		{
			Objetop param = om.readValue(record.value().toString(), Objetop.class);
			Objetop findListetecoppage=service.findListetecopbyprestation(param);
			for(int i=0;i<findListetecoppage.getL().size();i++)
			{
				List<TecbenefMod> tecbenef=serv.selectTecbenefId(findListetecoppage.getL().get(i).getId_op());
				findListetecoppage.getL().get(i).setTecbenef(tecbenef);
			}
			producer.send(record.key().toString(),"listTecopbyprestationRes", om.writeValueAsString(findListetecoppage));
			LOGGER.info(om.writeValueAsString(findListetecoppage));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			producer.send(record.key().toString(),"listTecopbyflagdateRes", e.getMessage());
		}
    }

	@KafkaListener(topics="listTecopReq")
    public void listTecop(ConsumerRecord<?,?> record) throws Exception{
		try
		{
			List<TecopMod> recupererListe = service.getAll();
			producer.send(record.key().toString(),"listTecopRes", om.writeValueAsString(recupererListe));
		}
		catch(Exception e)
		{
			producer.send(record.key().toString(),"listTecopRes", e.getMessage());
		}
    }
	
	@KafkaListener(topics="ConvertionLettreReq")
    public void convertionLettre(ConsumerRecord<?,?> record) throws Exception{
		try
		{
			double tr = Double.parseDouble(record.value().toString());
			producer.send(record.key().toString(),"ConvertionLettreRes", om.writeValueAsString(ConvertionLettre.getLettre(tr)));
		}
		catch(Exception e)
		{
			producer.send(record.key().toString(),"ConvertionLettreRes", e.getMessage());
		}
    }
	
	@KafkaListener(topics="changerEtatOpToRreq")
    public void updateOpRegle(ConsumerRecord<?,?> record) throws Exception{
		try
		{
			
			TecopMod recupererListe = service.getById(record.value().toString());
			recupererListe.setFlag_valide("R");
			List<TecbenefMod> tecbenef=serv.selectTecbenefId(record.value().toString());
			recupererListe.setTecbenef(tecbenef);
			LOGGER.info(om.writeValueAsString(recupererListe));
			service.save(recupererListe);
			serv.save(tecbenef);
			producer.send(record.key().toString(),"changerEtatOpToRres", "{\"success\":true}");
		}
		catch(Exception e)
		{
			producer.send(record.key().toString(),"changerEtatOpToRres", e.getMessage());
		}
    }
	
	@KafkaListener(topics="listTecopByIdReq")
    public void listTecopById(ConsumerRecord<?,?> record) throws Exception{
		try
		{
			
			TecopMod recupererListe = service.getById(record.value().toString());
			List<TecbenefMod> tecbenef=serv.selectTecbenefId(record.value().toString());
			recupererListe.setTecbenef(tecbenef);
			producer.send(record.key().toString(),"listTecopByIdRes", om.writeValueAsString(recupererListe));
		}
		catch(Exception e)
		{
			producer.send(record.key().toString(),"listTecopByIdRes", e.getMessage());
		}
    }
	
	@KafkaListener(topics="listTecOpRegleByIdReq")
    public void listTecopByIdregle(ConsumerRecord<?,?> record) throws Exception{
		try
		{
			TecopMod recupererListe = service.getByIdandRgle(record.value().toString());
			List<TecbenefMod> tecbenef=serv.selectTecbenefId(record.value().toString());
			recupererListe.setTecbenef(tecbenef);
			producer.send(record.key().toString(),"listTecOpRegleByIdRes", om.writeValueAsString(recupererListe));
		}
		catch(Exception e)
		{
			producer.send(record.key().toString(),"listTecOpRegleByIdRes", e.getMessage());
		}
    }
	
	@KafkaListener(topics="getallbyIdOpReq")
    public void getallbyIdOp(ConsumerRecord<?,?> record) throws Exception{
		try
		{
			List<TecopMod> recupererListe = service.getallbyId_op(record.value().toString());
			for(int i=0;i<recupererListe.size();i++)
			{
				List<TecbenefMod> tecbenef=serv.selectTecbenefId(record.value().toString());
				recupererListe.get(i).setTecbenef(tecbenef);
			}
			producer.send(record.key().toString(),"getallbyIdOpRes", om.writeValueAsString(recupererListe));
		}
		catch(Exception e)
		{
			producer.send(record.key().toString(),"getallbyIdOpRes", e.getMessage());
		}
    }
	
	@KafkaListener(topics="listTecopByIdAccReq")
    public void listTecopByIdAcc(ConsumerRecord<?,?> record) throws Exception{
		try
		{
			List<TecopMod> recupererListe = service.findListTecopByidAcc(record.value().toString());
			for(int i=0;i<recupererListe.size();i++)
			{
				List<TecbenefMod> tecbenef=serv.selectTecbenefId(recupererListe.get(i).getId_op());
				recupererListe.get(i).setTecbenef(tecbenef);
			}
			producer.send(record.key().toString(),"listTecopByIdAccRes", om.writeValueAsString(recupererListe));
		}
		catch(Exception e)
		{
			producer.send(record.key().toString(),"listTecopByIdAccRes", e.getMessage());
		}
    }
	
	@KafkaListener(topics="getnbPageReq")
    public void getnbdonné(ConsumerRecord<?,?> record) throws Exception{
		try
		{
			int nb = service.getnbdonné(record.value().toString());
			producer.send(record.key().toString(),"getnbPageRes", om.writeValueAsString(nb));
			LOGGER.info(om.writeValueAsString(nb));
		}
		catch(Exception e)
		{
			producer.send(record.key().toString(),"getnbPageRes", e.getMessage());
		}
    }
	
	@KafkaListener(topics="getallbyflagValideReq")
    public void getallbyflagValide(ConsumerRecord<?,?> record) throws Exception{
		try
		{
			LOGGER.info(record.value().toString());
			ListTecOPByFlagValideMod param = om.readValue(record.value().toString(), ListTecOPByFlagValideMod.class);
			LOGGER.info(param.getFlagValide());
			LOGGER.info(String.valueOf(param.getPage()));
			List<TecopMod> recupererListe = service.getallbyflag_valide(param.getFlagValide(),param.getPage());
			for(int i=0;i<recupererListe.size();i++)
			{
				List<TecbenefMod> tecbenef=serv.selectTecbenefId(recupererListe.get(i).getId_op());
				recupererListe.get(i).setTecbenef(tecbenef);
			}
			producer.send(record.key().toString(),"getallbyflagValideRes", om.writeValueAsString(recupererListe));
			LOGGER.info(om.writeValueAsString(recupererListe));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			producer.send(record.key().toString(),"getallbyflagValideRes", e.getMessage());
		}
    }
	
	@KafkaListener(topics="getallbyIdopflagvalideReq")
    public void getallbyIdopflagvalide(ConsumerRecord<?,?> record) throws Exception{
		try
		{
			String str=record.value().toString();
			LOGGER.info(str);
			String[]strTable = str.split(",");
			List<TecopMod> recupererListe = service.getallbyIdopflagvalide(strTable[0],strTable[1]);
			for(int i=0;i<recupererListe.size();i++)
			{
				List<TecbenefMod> tecbenef=serv.selectTecbenefId(recupererListe.get(i).getId_op());
				recupererListe.get(i).setTecbenef(tecbenef);
			}
			producer.send(record.key().toString(),"getallbyIdopflagvalideRes", om.writeValueAsString(recupererListe));
			LOGGER.info(om.writeValueAsString(recupererListe));
		}
		catch(Exception e)
		{
			producer.send(record.key().toString(),"getallbyIdopflagvalideRes", e.getMessage());
		}
    }
	
	@KafkaListener(topics="addTecopReq")
    public void add(ConsumerRecord<?,?> record) throws Exception{
		try
		{
			ParamTecOP param = om.readValue(record.value().toString(), ParamTecOP.class);
			
			java.util.Date date=Calendar.getInstance().getTime();
			String trs=ReferenceUtil.getReferenceDemande(param.getPrestation(), repository.getNextSeqOp2(), param.getDr());
			param.getTecop().setId_op(trs);
			param.getTecop().setDate_op(new Date(date.getTime()));
			LOGGER.info("id op "+trs);
			for(int i=0;i<param.getTecop().getTecbenef().size();i++)
			{
				param.getTecop().getTecbenef().get(i).setId_op(trs);
				LOGGER.info("id op "+param.getTecop().getTecbenef().get(i).getId_op());
			}
			service.save(param.getTecop());
			LOGGER.info("Ravo ooooooooooooo "+om.writeValueAsString(param.getTecop().getTecbenef()));
			serv.save(param.getTecop().getTecbenef());
			param.getTef().setNumOp(param.getTecop().getId_op());
			temprepository.updateTecOptemp(param.getPrestation(),param.getDr());
			producer.send(record.key().toString(),"saveTefTechReq", om.writeValueAsString(param.getTef()));
			producer.send(record.key().toString(),"addTecopRes", om.writeValueAsString(param));
			producer.send(record.key().toString(),"saveTecOpBIReq", om.writeValueAsString(param.getTecop()));
			
			ParamBenefBanque pb=new ParamBenefBanque();
			int taille=param.getTecop().getTecbenef().size();
			List<PerOpBeneficiaire> lstper=new ArrayList<>();
			for(int p=0;p<taille;p++)
			{
				Historiquedroit hist=new Historiquedroit();
				hist.setDatePaiement(param.getTecop().getDate_op());
				hist.setIdDemande(param.getTecop().getTecbenef().get(p).getId_demande());
				hist.setMatriculeBenef(param.getTecop().getTecbenef().get(p).getId_benef());
				hist.setMontant(param.getTecop().getTecbenef().get(p).getMontant());
				hist.setPeriode(ReferenceUtil.convertDateToStringperiode(param.getTecop().getDate_op()));
				if(param.getTecop().getTecbenef().get(p).getId_demande().substring(0, 3).equalsIgnoreCase("414"))
				{
					hist.setType("DAPM");
					hist.setLibelleType("Demande d'accouchement pré-natale et de maternité");
				}
				else if(param.getTecop().getTecbenef().get(p).getId_demande().substring(0, 3).equalsIgnoreCase("430"))
				{
					hist.setType("RFA");
					hist.setLibelleType("Remboursement de frais d'accouchement");
				}
				else if(param.getTecop().getTecbenef().get(p).getId_demande().substring(0, 3).equalsIgnoreCase("411"))
				{
					hist.setType("AP");
					hist.setLibelleType("Allocation pré-natale");
				}
				else if(param.getTecop().getTecbenef().get(p).getId_demande().substring(0, 3).equalsIgnoreCase("412"))
				{
					hist.setType("AM1");
					hist.setLibelleType("Allocation de maternité 1");
				}
				else if(param.getTecop().getTecbenef().get(p).getId_demande().substring(0, 3).equalsIgnoreCase("413"))
				{
					hist.setType("AM2");
					hist.setLibelleType("Allocation de maternité 1");
				}
				else if(param.getTecop().getTecbenef().get(p).getId_demande().substring(0, 3).equalsIgnoreCase("421"))
				{
					hist.setType("IJ1");
					hist.setLibelleType("Indemnité journalière 1");
				}
				else if(param.getTecop().getTecbenef().get(p).getId_demande().substring(0, 3).equalsIgnoreCase("422"))
				{
					hist.setType("IJ2");
					hist.setLibelleType("Indemnité journalière 2");
				}
				else if(param.getTecop().getTecbenef().get(p).getId_demande().substring(0, 1).equalsIgnoreCase("3"))
				{
					hist.setType("PENSION APPERIODIQUE");
					hist.setLibelleType("PENSION APPERIODIQUE");
				}
				else if(param.getTecop().getTecbenef().get(p).getId_demande().substring(0, 1).equalsIgnoreCase("2"))
				{
					hist.setType("AT/MP APPERIODIQUE");
					hist.setLibelleType("Accident de travail/Maladie professionnelle appériodique");
				}
				producer.send(record.key().toString(),"savehistodroitReq", om.writeValueAsString(hist));
				PerOpBeneficiaire per=new PerOpBeneficiaire();
				per.setBenefMatricule(param.getTecop().getTecbenef().get(p).getId_benef());
				per.setIdModePaiementTiers(param.getTecop().getTecbenef().get(p).getId_mode_paiement().getIdModePaiementTiers().longValue());
				per.setModePaiementTiers(param.getTecop().getTecbenef().get(p).getId_mode_paiement());
				per.setOpBenefMontant(param.getTecop().getTecbenef().get(p).getMontant());
				per.setOpNumero(param.getTecop().getTecbenef().get(p).getId_op());
				per.setIdAcc(param.getTecop().getTecbenef().get(p).getId_demande());
				lstper.add(per);
			}
			pb.setBenef(lstper);
			pb.setCodeDrService(param.getTecop().getCode_dr_service());
			SoucheMod souche=new SoucheMod();
			souche.setParam(pb);
			souche.setMotif("C0005");
			souche.setType("APE");
			producer.send(record.key().toString(),"findsouchetechreq", om.writeValueAsString(souche));
			
		}
		catch(Exception e)
		{
			LOGGER.info(e.getMessage());
			e.printStackTrace();
			producer.send(record.key().toString(),"addTecopRes", e.getMessage());
		}
    }
	
	@KafkaListener(topics="addTecopperiodiqueReq")
    public void addt(ConsumerRecord<?,?> record) throws Exception{
		try
		{
			LOGGER.info("Pety "+record.value().toString());
			ParamTecOP param = om.readValue(record.value().toString(), ParamTecOP.class);
			
			java.util.Date date=Calendar.getInstance().getTime();
			String trs=ReferenceUtil.getReferenceDemande(param.getPrestation(), repository.getNextSeqOp2(), param.getDr());
			param.getTecop().setId_op(trs);
			param.getTecop().setDate_op(new Date(date.getTime()));
			for(int i=0;i<param.getTecop().getTecbenef().size();i++)
			{
				LOGGER.info("Pety individu"+om.writeValueAsString(param.getTecop().getTecbenef().get(i).getId_individu()));
				LOGGER.info("Pety id demande"+om.writeValueAsString(param.getTecop().getTecbenef().get(i).getId_demande()));
				param.getTecop().getTecbenef().get(i).setId_op(trs);
				param.getTecop().getTecbenef().get(i).setId_mode_paiement(mdpservice.findByIdTiersAndIdAcc(param.getTecop().getTecbenef().get(i).getId_individu(), param.getTecop().getTecbenef().get(i).getId_demande()));
				LOGGER.info("Ravo ooooooooooooo "+om.writeValueAsString(param.getTecop().getTecbenef().get(i).getId_mode_paiement()));
			}
			service.save(param.getTecop());
			LOGGER.info("Ravo ooooooooooooo "+om.writeValueAsString(param.getTecop().getTecbenef()));
			serv.save(param.getTecop().getTecbenef());
			param.getTef().setNumOp(param.getTecop().getId_op());
			temprepository.updateTecOptemp(param.getPrestation(),param.getDr());
			producer.send(record.key().toString(),"saveTefTechReq", om.writeValueAsString(param.getTef()));
			producer.send(record.key().toString(),"addTecopperiodiqueRes", om.writeValueAsString(param));
			producer.send(record.key().toString(),"saveTecOpBIReq", om.writeValueAsString(param.getTecop()));
			LOGGER.info("BI  "+om.writeValueAsString(param.getTecop().getTecbenef()));
			ParamBenefBanque pb=new ParamBenefBanque();
			int taille=param.getTecop().getTecbenef().size();
			List<PerOpBeneficiaire> lstper=new ArrayList<>();
			for(int p=0;p<taille;p++)
			{
				Historiquedroit hist=new Historiquedroit();
				hist.setDatePaiement(param.getTecop().getDate_op());
				hist.setIdDemande(param.getTecop().getTecbenef().get(p).getId_demande());
				hist.setMatriculeBenef(param.getTecop().getTecbenef().get(p).getId_benef());
				hist.setMontant(param.getTecop().getTecbenef().get(p).getMontant());
				hist.setPeriode(ReferenceUtil.convertDateToStringperiode(param.getTecop().getDate_op()));
				if(param.getTecop().getTecbenef().get(p).getId_demande().substring(0, 3).equalsIgnoreCase("400"))
				{
					hist.setType("AF");
					hist.setLibelleType("Allocation familiale");
				}
				else if(param.getTecop().getTecbenef().get(p).getId_demande().substring(0, 3).equalsIgnoreCase("400"))
				{
					hist.setType("BAF");
					hist.setLibelleType("Bordereau d'allocation familiale");
				}
				else if(param.getTecop().getTecbenef().get(p).getId_demande().substring(0, 3).equalsIgnoreCase("401"))
				{
					hist.setType("ALSP");
					hist.setLibelleType("Allocation spéciale");
				}
				else if(param.getTecop().getTecbenef().get(p).getId_demande().substring(0, 1).equalsIgnoreCase("2"))
				{
					hist.setType("AT/MP PERIODIQUE");
					hist.setLibelleType("Accident de travail/Maladie professionnelle périodique");
				}
				producer.send(record.key().toString(),"savehistodroitReq", om.writeValueAsString(hist));
				PerOpBeneficiaire per=new PerOpBeneficiaire();
				if(param.getTef().getObjet().compareToIgnoreCase("extraction BAF")==0)
				{
					per.setBenefMatricule(param.getTecop().getTecbenef().get(p).getId_empl());
				}
				else if(param.getTef().getObjet().compareToIgnoreCase("extraction ALSP")==0)
				{
					per.setBenefMatricule(param.getTecop().getTecbenef().get(p).getId_individu());
				}
				else
				{
					per.setBenefMatricule(param.getTecop().getTecbenef().get(p).getId_benef());
				}
				LOGGER.info("beneficiaire "+om.writeValueAsString(param.getTecop().getTecbenef().get(p)));
				per.setIdModePaiementTiers(param.getTecop().getTecbenef().get(p).getId_mode_paiement().getIdModePaiementTiers().longValue());
				per.setModePaiementTiers(param.getTecop().getTecbenef().get(p).getId_mode_paiement());
				per.setOpBenefMontant(param.getTecop().getTecbenef().get(p).getMontant());
				per.setOpNumero(param.getTecop().getTecbenef().get(p).getId_op());
				per.setIdAcc(param.getTecop().getTecbenef().get(p).getId_demande());
				lstper.add(per);
			}
			pb.setBenef(lstper);
			pb.setCodeDrService(param.getTecop().getCode_dr_service());
			SoucheMod souche=new SoucheMod();
			souche.setParam(pb);
			souche.setMotif("C0005");
			souche.setType("PER");
			souche.getParam().setPeriode(param.getTecop().getPeriode());
			producer.send(record.key().toString(),"findsouchetechreq", om.writeValueAsString(souche));
			LOGGER.info("souche "+om.writeValueAsString(param.getTecop().getTecbenef()));
			
		}
		catch(Exception e)
		{
			LOGGER.info(e.getMessage());
			e.printStackTrace();
			producer.send(record.key().toString(),"addTecopperiodiqueRes", e.getMessage());
		}
    }
	
	@KafkaListener(topics="addTecoppensionperiodiqueReq")
    public void addperiodiquepension(ConsumerRecord<?,?> record) throws Exception{
		try
		{
			LOGGER.info("Pety "+record.value().toString());
			ParamTecOP param = om.readValue(record.value().toString(), ParamTecOP.class);
			temprepository.updateTecOptemp(param.getPrestation(),param.getDr());
			java.util.Date date=Calendar.getInstance().getTime();
			Map<Integer, TecopMod> map = new HashMap<Integer, TecopMod>();
			Map<Integer, Double> mapMontant = new HashMap<Integer, Double>();
			for(int i=0;i<param.getTecop().getTecbenef().size();i++)
			{
				param.getTecop().getTecbenef().get(i).setId_mode_paiement(mdpservice.findByIdTiersAndIdAcc(param.getTecop().getTecbenef().get(i).getId_benef(), param.getTecop().getTecbenef().get(i).getId_demande()));
				if(mapMontant.containsKey(param.getTecop().getTecbenef().get(i).getId_mode_paiement().getIdModePaiement()))
				{
					mapMontant.put(param.getTecop().getTecbenef().get(i).getId_mode_paiement().getIdModePaiement(), mapMontant.get(param.getTecop().getTecbenef().get(i).getId_mode_paiement().getIdModePaiement()) + param.getTecop().getTecbenef().get(i).getMontant());
				}
				else 
				{
					mapMontant.put(param.getTecop().getTecbenef().get(i).getId_mode_paiement().getIdModePaiement(), param.getTecop().getTecbenef().get(i).getMontant());
				}
				if(map.containsKey(param.getTecop().getTecbenef().get(i).getId_mode_paiement().getIdModePaiement()))
				{
					param.getTecop().getTecbenef().get(i).setId_op(map.get(param.getTecop().getTecbenef().get(i).getId_mode_paiement().getIdModePaiement()).getId_op());
					map.get(param.getTecop().getTecbenef().get(i).getId_mode_paiement().getIdModePaiement()).getTecbenef().add(param.getTecop().getTecbenef().get(i));
					map.get(param.getTecop().getTecbenef().get(i).getId_mode_paiement().getIdModePaiement()).setMontant(mapMontant.get(param.getTecop().getTecbenef().get(i).getId_mode_paiement().getIdModePaiement()));
					
				}
				else 
				{
					String trs=ReferenceUtil.getReferenceDemande(param.getPrestation(), repository.getNextSeqOp2(), param.getDr());
					TecopMod tecop = new TecopMod();
					tecop.setCode_dr_service(param.getTecop().getCode_dr_service());
					tecop.setDate_op(new Date(date.getTime()));
					tecop.setFlag_valide("O");
					tecop.setId_op(trs);
					tecop.setMontant(mapMontant.get(param.getTecop().getTecbenef().get(i).getId_mode_paiement().getIdModePaiement()));
					tecop.setObservations(param.getTecop().getObservations());
					tecop.setTecbenef(new ArrayList<TecbenefMod>());
					param.getTecop().getTecbenef().get(i).setId_op(tecop.getId_op());
					tecop.getTecbenef().add(param.getTecop().getTecbenef().get(i));
					map.put(param.getTecop().getTecbenef().get(i).getId_mode_paiement().getIdModePaiement(), tecop);
					
				}
			}
			for(TecopMod opTech : map.values()) {
				Tef tef = new Tef();
				tef.setCodeProjet(param.getTef().getCodeProjet());
				tef.setCodeService(param.getTef().getCodeService());
				tef.setCompteBenef(param.getTef().getCompteBenef());
				tef.setIdRubrique(param.getTef().getIdRubrique());
				tef.setObjet(param.getTef().getObjet());
				tef.setMontant(opTech.getMontant());
				tef.setNumOp(opTech.getId_op());
				producer.send(record.key().toString(),"saveTefTechReq", om.writeValueAsString(tef));
				producer.send(record.key().toString(),"saveTecOpBIReq", om.writeValueAsString(opTech));
				service.save(opTech);
				serv.save(opTech.getTecbenef());
				
				LOGGER.info("BI  "+om.writeValueAsString(param.getTecop().getTecbenef()));
				ParamBenefBanque pb=new ParamBenefBanque();
				int taille=opTech.getTecbenef().size();
				List<PerOpBeneficiaire> lstper=new ArrayList<>();
				for(int p=0;p<taille;p++)
				{
					Historiquedroit hist=new Historiquedroit();
					hist.setDatePaiement(param.getTecop().getDate_op());
					hist.setIdDemande(param.getTecop().getTecbenef().get(p).getId_demande());
					hist.setMatriculeBenef(param.getTecop().getTecbenef().get(p).getId_benef());
					hist.setMontant(param.getTecop().getTecbenef().get(p).getMontant());
					hist.setPeriode(ReferenceUtil.convertDateToStringperiode(param.getTecop().getDate_op()));
					hist.setType("PENSION PERIODIQUE");
					hist.setLibelleType("PENSION PERIODIQUE");
					PerOpBeneficiaire per=new PerOpBeneficiaire();
					per.setBenefMatricule(opTech.getTecbenef().get(p).getId_benef());
					per.setIdModePaiementTiers(opTech.getTecbenef().get(p).getId_mode_paiement().getIdModePaiementTiers().longValue());
					per.setModePaiementTiers(opTech.getTecbenef().get(p).getId_mode_paiement());
					per.setOpBenefMontant(opTech.getTecbenef().get(p).getMontant());
					per.setOpNumero(opTech.getTecbenef().get(p).getId_op());
					per.setIdAcc(opTech.getTecbenef().get(p).getId_demande());
					lstper.add(per);
				}
				pb.setBenef(lstper);
				pb.setCodeDrService(param.getTecop().getCode_dr_service());
				SoucheMod souche=new SoucheMod();
				souche.setParam(pb);
				souche.setMotif("C0005");
				souche.setType("PER");
				souche.getParam().setPeriode(param.getTecop().getPeriode());
				producer.send(record.key().toString(),"findsouchetechpensionperreq", om.writeValueAsString(souche));
				LOGGER.info("souche "+om.writeValueAsString(opTech.getTecbenef()));
				producer.send(record.key().toString(),"addTecoppensionperiodiqueRes", om.writeValueAsString(map.values()));
			}
		}
		catch(Exception e)
		{
			LOGGER.info(e.getMessage());
			e.printStackTrace();
			producer.send(record.key().toString(),"addTecoppensionperiodiqueRes", e.getMessage());
		}
	}
	
	@KafkaListener(topics="deleteTecopReq")
    public void delete(ConsumerRecord<?,?> record) throws Exception{
		try
		{
			service.delete(record.value().toString());
			producer.send(record.key().toString(),"deleteTecopRes","{\"succes\":\"true\"}");
		}
		catch(Exception e)
		{
			producer.send(record.key().toString(),"deleteTecopRes", e.getMessage());
		}
    }
	
	@KafkaListener(topics="updateTecopReq")
    public void update(ConsumerRecord<?,?> record) throws Exception{
		try
		{
			TecopMod sigcat = om.readValue(record.value().toString(), TecopMod.class);
			service.save(sigcat);
			List<TecbenefMod> tecbenef=serv.selectTecbenefId(sigcat.getId_op());
			serv.save(tecbenef);
			producer.send(record.key().toString(),"updateTecopRes", "{\"succes\":\"true\"}");
		}
		catch(Exception e)
		{
			producer.send(record.key().toString(),"updateTecopRes", e.getMessage());
		}
    }
	
	@KafkaListener(topics="findDetailOPReq")
    public void findDetailOP(ConsumerRecord<?,?> record) throws Exception{
		try
		{
			LOGGER.info("idOP and idAcc "+record.value().toString());
			paramRappelPension param = om.readValue(record.value().toString(), paramRappelPension.class);
			TecopMod liste=service.findbyIdOpAndIdDemande(param.getIdOp(), param.getIdAcc());
			List<TecbenefMod> tecbenef=serv.selectTecbenefId(liste.getId_op());
			liste.setTecbenef(tecbenef);
			producer.send(record.key().toString(),"insertrappelbyOPReq", om.writeValueAsString(liste));
			LOGGER.info("retour "+om.writeValueAsString(liste));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			producer.send(record.key().toString(),"findDetailOPRes", e.getMessage());
		}
    }
}
