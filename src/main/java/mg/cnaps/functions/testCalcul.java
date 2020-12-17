package mg.cnaps.functions;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class testCalcul {


	public Double calc(Integer t, Integer g){
		return (double) (t+g);
	}

	public testCalcul() {
		// TODO Auto-generated constructor stub
	}

}
