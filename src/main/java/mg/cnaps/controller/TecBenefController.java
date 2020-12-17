package mg.cnaps.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import mg.cnaps.models.CaisseMod;
import mg.cnaps.models.ParamBenefBanque;
import mg.cnaps.models.TecopMod;
import mg.cnaps.models.UpdateOp;
import mg.cnaps.services.TecbenefService;
import mg.cnaps.services.TecopService;
import mg.cnaps.util.RequestApi;

@RestController
public class TecBenefController {
	private static final Logger log = LoggerFactory.getLogger(TecBenefController.class);

	ObjectMapper om=new ObjectMapper();
	
	@Autowired
	TecopService opservice;
	
	@Autowired
	TecbenefService service;
	
	@Autowired
	RequestApi request;
	
	@PostMapping("/listsouchecaissebyflag")
	public ResponseEntity<Object> listsouchecaissebyflag(@RequestBody ParamBenefBanque caissebenef, @RequestHeader(value="Authorization") String token) {
		try
		{
			log.info("rrrrrrc "+om.writeValueAsString(caissebenef));
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
						TecopMod op = opservice.findById_op(caissebenef.getBenef().get(i).getOpNumero());
						if(op != null)
							if(op.getObservations() != null || op.getObservations().compareTo("") != 0)
								caisse.setObservations(op.getObservations());
						/*if(opservice.getById(caissebenef.getBenef().get(i).getOpNumero()) != null)
							if(opservice.getById(caissebenef.getBenef().get(i).getOpNumero()).getObservations() != null)
								caisse.setObservations(opservice.getById(caissebenef.getBenef().get(i).getOpNumero()).getObservations());*/
						benefcaisse.add(caisse);
					}
					return new ResponseEntity<>(request.sendPost("http://cnapsdbCaisse:4587/ajoutdecaissement", om.writeValueAsString(benefcaisse), token)
							, HttpStatus.OK);
				}
				
			}
			return new ResponseEntity<>("message", HttpStatus.OK);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	//updateTecbenefflag
	@PostMapping("/updateTecbenefflag")
	public void updateTecbenefflag(@RequestBody UpdateOp sigcat) throws JsonProcessingException {
		System.out.println("update "+om.writeValueAsString(sigcat));
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
	}
	
}
