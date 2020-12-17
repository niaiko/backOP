package com.mg.cnaps.kafka;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import mg.cnaps.models.ObjetRechercheSize;
import mg.cnaps.models.TecOpBenef;
import mg.cnaps.models.TecOpTempMod;
import mg.cnaps.models.TecbenefMod;
import mg.cnaps.models.TecopMod;
import mg.cnaps.repository.TecOpTempRepository;
import mg.cnaps.services.ReftrsmodepaieService;
import mg.cnaps.services.TecOpTempService;
import mg.cnaps.util.Test;


@Service
public class TecOpTempConsumer {

	private static final Logger LOGGER = LoggerFactory.getLogger(TecOpTempConsumer.class);

	ObjectMapper om=new ObjectMapper();
	
	@Autowired
	Producer producer;
	
	@Autowired
	TecOpTempService service;
	
	@Autowired
	ReftrsmodepaieService modePaieService;
	
	@Autowired
	TecOpTempRepository repository;
	
	@KafkaListener(topics="listTecoptempReq")
    public void listTecoptemp(ConsumerRecord<?,?> record){
		try
		{
			ObjetRechercheSize param = om.readValue(record.value().toString(), ObjetRechercheSize.class);
			ObjetRechercheSize findListetecoptemppage=service.findListetecoptemp(param);
			producer.send(record.key().toString(),"listTecoptempRes", om.writeValueAsString(findListetecoptemppage));
			LOGGER.info(om.writeValueAsString(findListetecoptemppage));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			producer.send(record.key().toString(),"listTecoptempRes", e.getMessage());
		}
    }
	
	@KafkaListener(topics="listTecOpTempReq")
    public void listTecOp(ConsumerRecord<?,?> record){
		try
		{
			Map<String, String> map = om.readValue(record.value().toString(), new TypeReference<Map<String, String>>(){});
			Page<TecOpTempMod> pages = service.getallbytecoptemp(map.get("prestation"),map.get("dr"), Integer.parseInt(map.get("page")), Integer.parseInt(map.get("size")));
			List<TecbenefMod> tecBenef = new ArrayList<TecbenefMod>();
			for(TecOpTempMod opTemp : pages.getContent()) {
				LOGGER.info("Op Temp : "+om.writeValueAsString(opTemp));
				TecbenefMod benef = new TecbenefMod();
				benef.setId_adresse(opTemp.getId_adresse());
				benef.setId_benef(opTemp.getId_benef());
				benef.setId_compte(opTemp.getId_compte());
				benef.setId_empl(opTemp.getId_empl());
				benef.setId_individu(opTemp.getId_individu());
				LOGGER.info("id demande "+om.writeValueAsString(opTemp.getId_demande()));
				LOGGER.info("id modepaiementtiers "+om.writeValueAsString(opTemp.getId_mode_paiement().longValue()));
				benef.setId_mode_paiement(modePaieService.findByIdAccAndIdModePaiementTiers(opTemp.getId_demande(), opTemp.getId_mode_paiement().longValue()));
				benef.setId_sucursale(opTemp.getId_sucursale());
				benef.setMontant(opTemp.getMontant());
				benef.setId_demande(opTemp.getId_demande());
				tecBenef.add(benef);
			}
			TecOpBenef ret = new TecOpBenef();
			TecopMod op = new TecopMod();
			Double somme = service.getSommeTecOpByPrestation(map.get("prestation"),map.get("dr"));
			op.setMontant(Test.roundup(somme, 100));
			op.setTecbenef(tecBenef);
			ret.setTecop(op);
			ret.setNbPage(pages.getTotalPages());
			ret.setPage(Integer.parseInt(map.get("page")));
			ret.setSize(Integer.parseInt(map.get("size")));
			producer.send(record.key().toString(),"listTecOpTempRes", om.writeValueAsString(ret));
			LOGGER.info(om.writeValueAsString(ret));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			producer.send(record.key().toString(),"listTecOpTempRes", e.getMessage());
		}
    }
	
	@KafkaListener(topics="saveTecoptempReq")
    public void saveTecoptemp(ConsumerRecord<?,?> record){
		try
		{
			LOGGER.info("avant mode de paiement debogage "+om.writeValueAsString(record.value().toString()));
			TecOpTempMod temp=om.readValue(record.value().toString(), TecOpTempMod.class);
			LOGGER.info("avant mode de paiement debogage "+om.writeValueAsString(record.value().toString()));
			service.save(temp);
			producer.send(record.key().toString(),"saveTecoptempRes", om.writeValueAsString(temp));
			LOGGER.info("mode de paiement debogage "+om.writeValueAsString(temp));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			producer.send(record.key().toString(),"saveTecoptempRes", e.getMessage());
		}
    }
}
