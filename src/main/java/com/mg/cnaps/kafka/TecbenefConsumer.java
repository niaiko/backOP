package com.mg.cnaps.kafka;

import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import mg.cnaps.models.CaisseMod;
import mg.cnaps.models.ObjetBenefprestation;
import mg.cnaps.models.ParamBenefBanque;
import mg.cnaps.models.TecbenefMod;
import mg.cnaps.models.UpdateOp;
import mg.cnaps.services.TecbenefService;
import mg.cnaps.services.TecopService;

@Service
public class TecbenefConsumer {

	private static final Logger LOGGER = LoggerFactory.getLogger(TecbenefConsumer.class);

	ObjectMapper om=new ObjectMapper();
	
	@Autowired
	Producer producer;
	
	@Autowired
	TecbenefService service;
	
	@Autowired
	TecopService opservice;

	@KafkaListener(topics="listTecbenefReq")
    public void listTecbenef(ConsumerRecord<?,?> record) throws Exception{
		try
		{
			List<TecbenefMod> recupererListe = service.getAll();
			producer.send(record.key().toString(),"listTecbenefRes", om.writeValueAsString(recupererListe));
		}
		catch(Exception e)
		{
			producer.send(record.key().toString(),"listTecbenefRes", e.getMessage());
		}
    }
	
	@KafkaListener(topics="listTecbenefcaissebyflagReq")
    public void listTecoptemp(ConsumerRecord<?,?> record){
		try
		{
			List<TecbenefMod> findListetecoptemppage=service.findListetecbenef();
			List<CaisseMod> benefcaisse = new ArrayList<CaisseMod>();
			int taille=findListetecoptemppage.size();
			for(int i=0;i<taille;i++)
			{
				CaisseMod caisse=new CaisseMod();
				caisse.setType("TECH");
				caisse.setBeneficiaire(findListetecoptemppage.get(i).getId_benef());
				caisse.setCodeDr(findListetecoptemppage.get(i).getId_mode_paiement().getCodeDr());
				caisse.setIddemande(findListetecoptemppage.get(i).getId_demande());
				caisse.setMontant(findListetecoptemppage.get(i).getMontant());
				caisse.setNumcaisse(findListetecoptemppage.get(i).getId_mode_paiement().getCodeAgence());
				caisse.setNumop(findListetecoptemppage.get(i).getId_op());
				caisse.setObservations(opservice.getById(findListetecoptemppage.get(i).getId_op()).getObservations());
				benefcaisse.add(caisse);
			}
			producer.send(record.key().toString(),"ajoutdecaissementReq", om.writeValueAsString(benefcaisse));
			LOGGER.info(om.writeValueAsString(findListetecoptemppage));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			producer.send(record.key().toString(),"listTecbenefcaissebyflagRes", e.getMessage());
		}
    }
	
	@KafkaListener(topics="listsouchecaissebyflagReq")
    public void listsouche(ConsumerRecord<?,?> record){
		try
		{
			ParamBenefBanque caissebenef=om.readValue(record.value().toString(), ParamBenefBanque.class);
			if(caissebenef!=null)
			{
				if(caissebenef.getBenef()!=null)
				{
					int taille=caissebenef.getBenef().size();
					List<CaisseMod> benefcaisse = new ArrayList<CaisseMod>();
					for(int i=0;i<taille;i++)
					{
						CaisseMod caisse=new CaisseMod();
						caisse.setType("TECH");
						caisse.setBeneficiaire(caissebenef.getBenef().get(i).getBenefMatricule());
						caisse.setCodeDr(caissebenef.getBenef().get(i).getModePaiementTiers().getCodeDr());
						caisse.setIddemande(caissebenef.getBenef().get(i).getIdAcc());
						caisse.setMontant(caissebenef.getBenef().get(i).getOpBenefMontant());
						caisse.setNumcaisse(caissebenef.getBenef().get(i).getModePaiementTiers().getCodeAgence());
						caisse.setNumop(caissebenef.getBenef().get(i).getOpNumero());
						caisse.setObservations(opservice.getById(caissebenef.getBenef().get(i).getOpNumero()).getObservations());
						benefcaisse.add(caisse);
					}
					producer.send(record.key().toString(),"ajoutdecaissementReq", om.writeValueAsString(benefcaisse));
					LOGGER.info(om.writeValueAsString(caissebenef));
				}
				
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			producer.send(record.key().toString(),"listsouchecaissebyflagRes", e.getMessage());
		}
    }
	
	@KafkaListener(topics="listTecbenefByIdOpReq")
    public void listTecbenefByIdOp(ConsumerRecord<?,?> record) throws Exception{
		try
		{
			List<TecbenefMod> recupererListe = service.selectTecbenefId(record.value().toString());
			producer.send(record.key().toString(),"listTecbenefByIdOpRes", om.writeValueAsString(recupererListe));
		}
		catch(Exception e)
		{
			producer.send(record.key().toString(),"listTecbenefByIdOpRes", e.getMessage());
		}
    }
	
	
	@KafkaListener(topics="ajoutTecbenefReq")
    public void ajout(ConsumerRecord<?,?> record) throws Exception{
		try
		{
			TecbenefMod sigClss = om.readValue(record.value().toString(), TecbenefMod.class);
			service.save(sigClss);
			producer.send(record.key().toString(),"ajoutTecbenefRes", "{\"succes\":\"true\"}");
		}
		catch(Exception e)
		{
			producer.send(record.key().toString(),"ajoutTecbenefRes", e.getMessage());
		}
    }
	
	@KafkaListener(topics="deleteTecbenefReq")
    public void delete(ConsumerRecord<?,?> record) throws Exception{
		try
		{
			service.delete(record.value().toString());
			producer.send(record.key().toString(),"deleteTecbenefRes","{\"succes\":\"true\"}");
		}
		catch(Exception e)
		{
			producer.send(record.key().toString(),"deleteTecbenefRes", e.getMessage());
		}
    }
	
	@KafkaListener(topics="updateTecbenefReq")
    public void update(ConsumerRecord<?,?> record) throws Exception{
		try
		{
			TecbenefMod sigcat = null;
			sigcat = om.readValue(record.value().toString(), TecbenefMod.class);
			service.save(sigcat);
			producer.send(record.key().toString(),"updateTecbenefRes", "{\"succes\":\"true\"}");
		}
		catch(Exception e)
		{
			producer.send(record.key().toString(),"updateTecbenefRes", e.getMessage());
		}
    }
	
	@KafkaListener(topics="updateTecbenefflagReq")
    public void updateflag(ConsumerRecord<?,?> record) throws Exception{
		try
		{
			UpdateOp sigcat = om.readValue(record.value().toString(), UpdateOp.class);
			//400
			if(sigcat.getId_op().substring(0, 3).equalsIgnoreCase("400"))
			{
				service.updateflagempl(sigcat.getFlagop(), sigcat.getId_op(), sigcat.getId_benef());
			}
			//401
			else if(sigcat.getId_op().substring(0, 3).equalsIgnoreCase("401"))
			{
				service.updateflagindiv(sigcat.getFlagop(), sigcat.getId_op(), sigcat.getId_benef());
			}
			else
			{
				service.updateflagbenef(sigcat.getFlagop(), sigcat.getId_op(), sigcat.getId_benef());
			}
			producer.send(record.key().toString(),"updateTecbenefflagRes", "{\"succes\":\"true\"}");
		}
		catch(Exception e)
		{
			producer.send(record.key().toString(),"updateTecbenefflagRes", e.getMessage());
		}
	}
	
	@KafkaListener(topics="gethistoriquepaiementReq")
    public void gethistoriquepaiement(ConsumerRecord<?,?> record) throws Exception{
		try
		{
			ObjetBenefprestation param = om.readValue(record.value().toString(), ObjetBenefprestation.class);
			ObjetBenefprestation findListetecoptemppage=service.gethistoriquepaiement(param);
			int taille=findListetecoptemppage.getL().size();
			for(int i=0;i<taille;i++)
			{
				findListetecoptemppage.getL().get(i).setDate_op(opservice.getById(findListetecoptemppage.getL().get(i).getId_op()).getDate_op());
			}
			producer.send(record.key().toString(),"gethistoriquepaiementRes", om.writeValueAsString(findListetecoptemppage));
		}
		catch(Exception e)
		{
			producer.send(record.key().toString(),"gethistoriquepaiementRes", e.getMessage());
		}
    }
	
	@KafkaListener(topics="gethistoriquepaiementpensionReq")
    public void gethistoriquepaiementpen(ConsumerRecord<?,?> record) throws Exception{
		try
		{
			LOGGER.info("argument matricule "+record.value().toString());
			List<TecbenefMod> liste=service.gethistoriquepaiementpension(record.value().toString());
			producer.send(record.key().toString(),"gethistoriquepaiementpensionRes", om.writeValueAsString(liste));
			LOGGER.info("retour "+om.writeValueAsString(liste));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			producer.send(record.key().toString(),"gethistoriquepaiementpensionRes", e.getMessage());
		}
    }
}
