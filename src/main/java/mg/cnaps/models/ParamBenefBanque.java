package mg.cnaps.models;

import java.io.Serializable;
import java.util.List;

public class ParamBenefBanque implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<PerOpBeneficiaire> benef;
	private String codeDrService;
	private String periode;
	public List<PerOpBeneficiaire> getBenef() {
		return benef;
	}
	public void setBenef(List<PerOpBeneficiaire> benef) {
		this.benef = benef;
	}
	public String getCodeDrService() {
		return codeDrService;
	}
	public void setCodeDrService(String codeDrService) {
		this.codeDrService = codeDrService;
	}
	public String getPeriode() {
		return periode;
	}
	public void setPeriode(String periode) {
		this.periode = periode;
	}
}
