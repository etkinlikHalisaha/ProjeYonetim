package tr.org.tnb.egitim.projeyonetimi.project;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public class QualityProject extends Project {
	private Integer qualityDegree;

	public Integer getQualityDegree() {
		return qualityDegree;
	}

	public void setQualityDegree(Integer qualityDegree) {
		this.qualityDegree = qualityDegree;
	}

	@Override
	@Transient
	public int getType() {
		return 2;
	}

	
}
