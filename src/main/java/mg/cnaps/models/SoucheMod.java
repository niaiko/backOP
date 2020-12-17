package mg.cnaps.models;

public class SoucheMod {

	private String motif;
	private String type;
	private String code_dr;
	private ParamBenefBanque param;
	
	public String getMotif() {
		return motif;
	}
	public void setMotif(String motif) {
		this.motif = motif;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCode_dr() {
		return code_dr;
	}
	public void setCode_dr(String code_dr) {
		this.code_dr = code_dr;
	}
	public ParamBenefBanque getParam() {
		return param;
	}
	public void setParam(ParamBenefBanque param) {
		this.param = param;
	}
}
