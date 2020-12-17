package mg.cnaps.models;

import java.io.Serializable;

public class TecbenefModPk implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id_op;
	private String id_benef;
	private String id_demande;

	
	public String getId_op() {
		return id_op;
	}

	
	public String getId_benef() {
		return id_benef;
	}

	public String getId_demande() {
		return id_demande;
	}


	public TecbenefModPk() {
		// TODO Auto-generated constructor stub
	}

	public TecbenefModPk(String id_op, String id_benef,String id_demande) {
		this.id_op = id_op;
		this.id_benef = id_benef;
		this.id_demande=id_demande;
	}

	

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TecbenefModPk) {
			TecbenefModPk pk = (TecbenefModPk)obj;
            return (id_op == pk.id_op)  && id_benef.equals(pk.id_benef) && id_demande.equals(pk.id_demande);
        } else {
            return false;
        }
	}

	@Override
	public int hashCode() {
		return id_op.hashCode() + id_benef.hashCode() + id_demande.hashCode();
	}



}
