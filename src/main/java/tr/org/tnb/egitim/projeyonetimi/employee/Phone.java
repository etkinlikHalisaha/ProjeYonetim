package tr.org.tnb.egitim.projeyonetimi.employee;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;

@Embeddable
public class Phone {
	private String number;
	private PhoneType type;

	public Phone() {
	}

	public Phone(String number, PhoneType type) {
		this.number = number;
		this.type = type;
	}

	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(length = 1)
	public PhoneType getType() {
		return type;
	}
	public void setType(PhoneType type) {
		this.type = type;
	}
	
	@Transient
	public String getPhoneNumber() {
		return this.type.getLabel() + "-" + this.number;
	}
	
	
}
