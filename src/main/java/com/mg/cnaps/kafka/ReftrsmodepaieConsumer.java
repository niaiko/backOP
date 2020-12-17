package com.mg.cnaps.kafka;

import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import mg.cnaps.models.ModePaiePK;
import mg.cnaps.models.ParamBenefBanque;
import mg.cnaps.models.ReftrsmodepaieMod;
import mg.cnaps.models.RenteMortelMod;
import mg.cnaps.repository.ReftrsmodepaieRepository;
import mg.cnaps.services.ReftrsmodepaieService;

@Service
public class ReftrsmodepaieConsumer {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReftrsmodepaieConsumer.class);

	ObjectMapper om=new ObjectMapper();
	
	@Autowired
	Producer producer;
	
	@Autowired
	ReftrsmodepaieService service;
	
	@Autowired
	ReftrsmodepaieRepository repository;

	@KafkaListener(topics="listtrsmodepaieReq")
    public void listtrsmodepaie(ConsumerRecord<?,?> record) throws Exception{
		try
		{
			List<ReftrsmodepaieMod> recupererListe = service.getAll();
			producer.send(record.key().toString(),"listtrsmodepaieRes", om.writeValueAsString(recupererListe));
		}
		catch(Exception e)
		{
			producer.send(record.key().toString(),"listtrsmodepaieRes", e.getMessage());
		}
    }
	
	@KafkaListener(topics="getmodepaiebyidaccReq")
    public void getmodepaiebyidacc(ConsumerRecord<?,?> record) throws Exception{
		try
		{
			LOGGER.info("petit idacc "+record.value().toString());
			ReftrsmodepaieMod recupererListe = service.findmodepaieidAcc(record.value().toString());
			producer.send(record.key().toString(),"getmodepaiebyidaccRes", om.writeValueAsString(recupererListe));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			producer.send(record.key().toString(),"getmodepaiebyidaccRes", e.getMessage());
		}
    }
	
	@KafkaListener(topics="findmodepaieByIdTiersAndIdAccReq")
    public void findmodepaieByIdTiersAndIdAcc(ConsumerRecord<?,?> record) throws Exception{
		try
		{
			Map<String, String> map = om.readValue(record.value().toString(), new TypeReference<Map<String, String>>(){});
			ReftrsmodepaieMod recupererListe = service.findByIdTiersAndIdAcc(map.get("tiers"),map.get("idacc"));
			producer.send(record.key().toString(),"findmodepaieByIdTiersAndIdAccRes", om.writeValueAsString(recupererListe));
		}
		catch(Exception e)
		{
			producer.send(record.key().toString(),"findmodepaieByIdTiersAndIdAccRes", e.getMessage());
		}
    }
	
	@KafkaListener(topics="findmodepaieByIdTiersAndprestationReq")
    public void findmodepaieByIdTiersAndprestation(ConsumerRecord<?,?> record) throws Exception{
		try
		{
			Map<String, String> map = om.readValue(record.value().toString(), new TypeReference<Map<String, String>>(){});
			List<ReftrsmodepaieMod> recupererListe = repository.getlistemodepaiement(Long.valueOf(map.get("idmdptiers")),map.get("prestation"));
			producer.send(record.key().toString(),"findmodepaieByIdTiersAndprestationRes", om.writeValueAsString(recupererListe));
		}
		catch(Exception e)
		{
			producer.send(record.key().toString(),"findmodepaieByIdTiersAndprestationRes", e.getMessage());
		}
    }
	
	@KafkaListener(topics="findmodepaieByIdTiersAndIdAccRenteMortelleReq")
    public void findmodepaieByIdTiersAndIdAccr(ConsumerRecord<?,?> record) throws Exception{
		try
		{
			LOGGER.info("zavatra alefan Jerry "+record.value().toString());
			List<RenteMortelMod> f=om.readValue(record.value().toString(), new TypeReference<List<RenteMortelMod>>() {});
			int taille=f.size();
			for(int i=0;i<taille;i++)
			{
				ReftrsmodepaieMod recupererListe = service.findByIdTiersAndIdAcc(f.get(i).getMatriculBenef(),f.get(i).getIdAcc());
				f.get(i).setIdModePaiementTiers(recupererListe.getIdModePaiementTiers());
			}
			LOGGER.info("zavatra averiko "+om.writeValueAsString(f));
			producer.send(record.key().toString(),"findmodepaieByIdTiersAndIdAccRenteMortelleRes", om.writeValueAsString(f));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			producer.send(record.key().toString(),"findmodepaieByIdTiersAndIdAccRenteMortelleRes", e.getMessage());
		}
    }
	
	@KafkaListener(topics="getmodepaiebymodepaiepkReq")
    public void getmodepaiebymodepaiepk(ConsumerRecord<?,?> record) throws Exception{
		try
		{
			ModePaiePK pk=om.readValue(record.value().toString(), ModePaiePK.class);
			ReftrsmodepaieMod recupererListe = service.getById(pk);
			producer.send(record.key().toString(),"getmodepaiebymodepaiepkRes", om.writeValueAsString(recupererListe));
		}
		catch(Exception e)
		{
			producer.send(record.key().toString(),"getmodepaiebymodepaiepkRes", e.getMessage());
		}
    }
	
	
	@KafkaListener(topics="ajouttrsmodepaieReq")
    public void ajout(ConsumerRecord<?,?> record) throws Exception{
		try
		{
			LOGGER.info("ajout trs mode paie "+record.value().toString());
			ReftrsmodepaieMod modepaie = om.readValue(record.value().toString(), ReftrsmodepaieMod.class);
			service.save(modepaie);
			producer.send(record.key().toString(),"ajouttrsmodepaieRes", om.writeValueAsString(modepaie));
			LOGGER.info("retour "+om.writeValueAsString(modepaie));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			producer.send(record.key().toString(),"ajouttrsmodepaieRes", e.getMessage());
		}
    }
	
	@KafkaListener(topics="deletetrsmodepaieReq")
    public void delete(ConsumerRecord<?,?> record) throws Exception{
		try
		{
			service.delete(record.value().toString());
			producer.send(record.key().toString(),"deletetrsmodepaieRes","{\"succes\":\"true\"}");
		}
		catch(Exception e)
		{
			producer.send(record.key().toString(),"deletetrsmodepaieRes", e.getMessage());
		}
    }
	
	@KafkaListener(topics="updatetrsmodepaieReq")
    public void update(ConsumerRecord<?,?> record) throws Exception{
		try
		{
			ReftrsmodepaieMod sigcat = om.readValue(record.value().toString(), ReftrsmodepaieMod.class);
			service.save(sigcat);
			producer.send(record.key().toString(),"updatetrsmodepaieRes", "{\"succes\":\"true\"}");
		}
		catch(Exception e)
		{
			producer.send(record.key().toString(),"updatetrsmodepaieRes", e.getMessage());
		}
    }
	
	@KafkaListener(topics="updatemodepaiesoucheReq")
    public void updatemodepaiesouche(ConsumerRecord<?,?> record) throws Exception{
		try
		{
			ParamBenefBanque pb=om.readValue(record.value().toString(), ParamBenefBanque.class);
			int taille=pb.getBenef().size();
			for(int i=0;i<taille;i++)
			{
				Long modepaie=pb.getBenef().get(i).getIdModePaiementTiers();
				String idacc=pb.getBenef().get(i).getIdAcc();
				service.updatereftrsmodepaie(pb.getBenef().get(i).getModePaiementTiers().getNomInstitutionSortie(), pb.getBenef().get(i).getModePaiementTiers().getImputation(), pb.getBenef().get(i).getModePaiementTiers().getIdInstitution(), pb.getBenef().get(i).getModePaiementTiers().getNumCompteInstitution(), idacc, modepaie);
			}
			producer.send(record.key().toString(),"updatemodepaiesoucheRes", om.writeValueAsString(pb));
			LOGGER.info("vita update "+pb);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			producer.send(record.key().toString(),"updatemodepaiesoucheRes", e.getMessage());
		}
    }
	
	@KafkaListener(topics="updatemodepaieccpvirmdsoucheReq")
    public void updatemodepaieccpvir(ConsumerRecord<?,?> record) throws Exception{
		try
		{
			ParamBenefBanque pb=om.readValue(record.value().toString(), ParamBenefBanque.class);
			int taille=pb.getBenef().size();
			for(int i=0;i<taille;i++)
			{
				Long modepaie=pb.getBenef().get(i).getIdModePaiementTiers();
				String idacc=pb.getBenef().get(i).getIdAcc();
				service.updatemodepaiement(pb.getBenef().get(i).getModePaiementTiers().getCompte(), pb.getBenef().get(i).getModePaiementTiers().getCle(), pb.getBenef().get(i).getModePaiementTiers().getDateDebut(), pb.getBenef().get(i).getModePaiementTiers().getDateFin(), pb.getBenef().get(i).getModePaiementTiers().getCodeSwift(), pb.getBenef().get(i).getModePaiementTiers().getDomiciliation(), pb.getBenef().get(i).getModePaiementTiers().getIdTiers(), pb.getBenef().get(i).getModePaiementTiers().getDefaut(), pb.getBenef().get(i).getModePaiementTiers().getIdAgence(), pb.getBenef().get(i).getModePaiementTiers().getIdModePaiement(), pb.getBenef().get(i).getModePaiementTiers().getAbrevModePaiement(), pb.getBenef().get(i).getModePaiementTiers().getCaisse(), pb.getBenef().get(i).getModePaiementTiers().getNumero(), pb.getBenef().get(i).getModePaiementTiers().getCodeBanque(), pb.getBenef().get(i).getModePaiementTiers().getCodeAgence(), pb.getBenef().get(i).getModePaiementTiers().getLibelleAgence(), pb.getBenef().get(i).getModePaiementTiers().getLibelleBanque(), pb.getBenef().get(i).getModePaiementTiers().getAbbrevBanque(), pb.getBenef().get(i).getModePaiementTiers().getIdbenef(), pb.getBenef().get(i).getModePaiementTiers().getAgenceotiv(), modepaie, pb.getBenef().get(i).getModePaiementTiers().getNomInstitutionSortie(), pb.getBenef().get(i).getModePaiementTiers().getImputation(), pb.getBenef().get(i).getModePaiementTiers().getIdInstitution(), pb.getBenef().get(i).getModePaiementTiers().getNumCompteInstitution(), pb.getBenef().get(i).getModePaiementTiers().getCodeDr(), pb.getBenef().get(i).getModePaiementTiers().getIdDmdDepense(), pb.getBenef().get(i).getModePaiementTiers().getAgenceotiv(), idacc);
			}
			producer.send(record.key().toString(),"updatemodepaieccpvirmdsoucheRes", om.writeValueAsString(pb));
			LOGGER.info("vita update "+pb);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			producer.send(record.key().toString(),"updatemodepaieccpvirmdsoucheRes", e.getMessage());
		}
    }
	
	@KafkaListener(topics="updatemodepaiesouchepensionperReq")
    public void updatemodepaiesouchepensionper(ConsumerRecord<?,?> record) throws Exception{
		try
		{
			ReftrsmodepaieMod sigcat = om.readValue(record.value().toString(), ReftrsmodepaieMod.class);
			service.updatereftrsmodepaiepen(sigcat.getCompte(), sigcat.getCle(), sigcat.getDateDebut(), sigcat.getDateFin(), sigcat.getCodeSwift(), sigcat.getDomiciliation(), sigcat.getIdTiers(), sigcat.getDefaut(), sigcat.getIdAgence(), sigcat.getIdModePaiement(), sigcat.getAbrevModePaiement(), sigcat.getCaisse(), sigcat.getNumero(), sigcat.getCodeBanque(), sigcat.getCodeAgence(), sigcat.getLibelleAgence(), sigcat.getLibelleBanque(), sigcat.getAbbrevBanque(), sigcat.getIdbenef(), sigcat.getAgenceotiv(), sigcat.getIdAcc(), sigcat.getIdModePaiementTiers());
			producer.send(record.key().toString(),"updatemodepaiesouchepensionperRes", om.writeValueAsString(sigcat));
			LOGGER.info("vita update "+sigcat);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			producer.send(record.key().toString(),"updatemodepaiesouchepensionperRes", e.getMessage());
		}
    }
	
	@KafkaListener(topics="updatemodepaiementopReq")
    public void updatemodepaiement(ConsumerRecord<?,?> record) throws Exception{
		try
		{
			ReftrsmodepaieMod s = om.readValue(record.value().toString(), ReftrsmodepaieMod.class);
			service.updatemodepaiement(s.getCompte(), s.getCle(), s.getDateDebut(), s.getDateFin(), s.getCodeSwift(), s.getDomiciliation(), s.getIdTiers(), s.getDefaut(), s.getIdAgence(), s.getIdModePaiement(), s.getAbrevModePaiement(), s.getCaisse(), s.getNumero(), s.getCodeBanque(), s.getCodeAgence(), s.getLibelleAgence(), s.getLibelleBanque(), s.getAbbrevBanque(), s.getIdbenef(), s.getAgenceotiv(), s.getIdModePaiementTiers(), s.getNomInstitutionSortie(), s.getImputation(), s.getIdInstitution(), s.getNumCompteInstitution(), s.getCodeDr(), s.getIdDmdDepense(), s.getAgenceotiv(), s.getIdAcc());
			producer.send(record.key().toString(),"updatemodepaiementopRes", om.writeValueAsString(s));
			LOGGER.info("vita update "+s);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			producer.send(record.key().toString(),"updatemodepaiementopRes", e.getMessage());
		}
    }
}
