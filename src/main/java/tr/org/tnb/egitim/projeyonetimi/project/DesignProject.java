package tr.org.tnb.egitim.projeyonetimi.project;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public class DesignProject extends Project {
	private String designCompany;

	public String getDesignCompany() {
		return designCompany;
	}

	public void setDesignCompany(String designCompany) {
		this.designCompany = designCompany;
	}

	@Override
	@Transient
	public int getType() {
		return 1;
	}

}
